package cn.loverqi.lease.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.LeasemainMapper;
import cn.loverqi.lease.pojo.Flowing;
import cn.loverqi.lease.pojo.Goods;
import cn.loverqi.lease.pojo.Grade;
import cn.loverqi.lease.pojo.Leasemain;
import cn.loverqi.lease.pojo.LeasemainExample;
import cn.loverqi.lease.pojo.LeasemainExample.Criteria;
import cn.loverqi.lease.pojo.Price;
import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.service.FlowingService;
import cn.loverqi.lease.service.GoodsService;
import cn.loverqi.lease.service.GradeService;
import cn.loverqi.lease.service.LeaseMainService;
import cn.loverqi.lease.service.PriceService;
import cn.loverqi.lease.service.UserService;
import cn.loverqi.lease.util.DateFormatUtil;
import cn.loverqi.lease.util.MathUtil;

/**
 * 项目名称：lease
 * 类名称：LeaseMainServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
@Service
public class LeaseMainServiceImpl implements LeaseMainService {

	@Autowired
	private LeasemainMapper leasemainMapper;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private UserService userService;
	@Autowired
	private PriceService priceService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private FlowingService flowingService;

	/* 
	 * lease()方法的实现,lease 0 成功 -1系统异常 1商品不存在 2商品未归还 3用户不存在 4用户黑名单
	 */
	@Override
	public int lease(String cardId, String barCode, String shopId) {
		// 商品是否入库
		Goods goods = goodsService.find(barCode.trim());
		if (goods == null) {
			return 1;
		}
		// 商品是在在借
		Leasemain leasemain = get(goods);
		if (leasemain != null) {
			return 2;
		}
		// 用户是否黑名单
		User user = userService.findUserByUse(cardId.trim());
		if (user == null) {
			return 3;
		} else if (user.getState().equals(1)) {
			return 4;
		}
		// leasemain.set

		// 创建订单
		Leasemain newLeasemain = new Leasemain();
		newLeasemain.setGoodsId(goods.getGoodsId());
		newLeasemain.setCardId(user.getCardId());
		newLeasemain.setStartTime(new Date());
		newLeasemain.setBorrowId(Integer.parseInt(shopId.trim()));

		int insertSelective = leasemainMapper.insertSelective(newLeasemain);

		return insertSelective > 0 ? 0 : -1;
	}

	/* 
	 * repay()方法的实现
	 */
	@Override
	public Leasemain repay(String goodsId, String shopId) {
		int resCode = 0;
		Leasemain leasemain = null;
		if (goodsId != null & !"".equals(goodsId.trim())) {
			Goods goods = goodsService.find(goodsId);
			LeasemainExample example = new LeasemainExample();
			example.createCriteria().andGoodsIdEqualTo(goods.getGoodsId()).andStateLessThan(3).andStateNotEqualTo(1);
			List<Leasemain> list = leasemainMapper.selectByExample(example);

			if (list != null && list.size() > 0) {
				leasemain = list.get(0);
				leasemain.setAlsoId(Integer.parseInt(shopId.trim()));
				leasemain.setEndTime(new Date());
				leasemain.setConsuming((leasemain.getEndTime().getTime() - leasemain.getStartTime().getTime()) / (60 * 1000));
				leasemain.setCost(amount(leasemain));

				// 更新用户余额和消费额度
				User user = userService.findUserByUse(leasemain.getCardId());
				user.setBalance(user.getBalance() - leasemain.getCost());
				userService.updateUserRecharge(user.getCardId(), user.getBalance(), false, leasemain.getBorrowId());
				if (user.getBalance() >= 0) {
					leasemain.setState(4); // 订单完成，并归档完成
				} else {
					leasemain.setState(1); // 有欠款
					leasemain.setRemark("欠款金额：￥" + -1 * user.getBalance());
				}
				resCode = leasemainMapper.updateByPrimaryKeySelective(leasemain);

				// 更新消费流水
				Flowing flowing = new Flowing();
				flowing.setCost(leasemain.getCost());
				flowing.setState(1);
				flowing.setCardId(leasemain.getCardId());
				flowing.setCreateDate(new Date());
				flowing.setGoodsId(leasemain.getGoodsId());
				flowing.setShopId(leasemain.getBorrowId());
				flowing.setRemark("会员卡余额消费");
				flowingService.create(flowing);

				// 用户余额不足，更新欠费流水
				if (user.getBalance() < 0) {
					flowing.setCost(user.getBalance() * -1);
					flowing.setState(2);
					flowing.setGoodsId(leasemain.getGoodsId());
					flowing.setCardId(leasemain.getCardId());
					flowing.setShopId(null);
					flowing.setCreateDate(new Date());
					flowing.setRemark("用户欠费情况");
					flowing.setId(null);
					flowingService.create(flowing);
				}

			}
		}

		return resCode > 0 ? leasemain : null;
	}

	/* 
	 * amount()方法的实现
	 */
	@Override
	public double amount(Leasemain leasemain) {
		Map<String, List<Price>> map = priceService.findList(leasemain.getGoodsId(), leasemain.getBorrowId());
		long timeLong = leasemain.getConsuming();
		double costMoney = 0;

		List<Price> firstList = map.get("first");
		List<Price> secondList = map.get("second");

		// 计算第一阶段需要的费用
		Price price = getPrice(firstList, timeLong);
		if (price != null) {
			costMoney += price.getPrice();
			timeLong -= price.getDuration();
		}

		// 计算第二阶段需要的费用,在没有进行黑名单清算之前暂且将已经是黑名单的用户归还
		if (timeLong > 0 && secondList != null && secondList.size() > 0) {
			costMoney += secondList.get(0).getPrice() * MathUtil.ceil(timeLong, secondList.get(0).getPattern());
		}

		User user = userService.findUserByUse(leasemain.getCardId());
		Grade grade = gradeService.getLevelObjByid(user.getLevel());

		double thisCost = costMoney * grade.getDiscounts();

		user.setCost(user.getCost() + thisCost);
		userService.createOrUpdateUser(user);

		return new BigDecimal(thisCost).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	// 获取到当前的所匹配的价格模板
	private Price getPrice(List<Price> firstList, long timeLong) {
		Price rePrice = null;
		for (Price price : firstList) {
			if (price.getDuration() >= timeLong) {
				rePrice = price;
				break;
			}
		}
		if (rePrice == null && firstList != null && firstList.size() > 0) {
			rePrice = firstList.get(firstList.size() - 1);
		}

		return rePrice;
	}

	/* 
	 * getLeasegoods()方法的实现
	 */
	@Override
	public List<Leasemain> getLeasegoods(Map<String, Object> mapsToMap) {
		List<Leasemain> list = null;
		LeasemainExample example = new LeasemainExample();
		Criteria createCriteria = example.createCriteria();

		Object state = mapsToMap.get("state");
		if (state != null && !"".equals(state.toString().trim())) {
			createCriteria.andStateEqualTo(Integer.parseInt((String) state));
		}
		Object cardId = mapsToMap.get("cardId");
		if (cardId != null && !"".equals(cardId.toString().trim())) {
			createCriteria.andCardIdEqualTo(cardId.toString());
		}
		Object shopId = mapsToMap.get("shopId");
		if (shopId != null && !"".equals(shopId.toString().trim())) {
			createCriteria.andBorrowIdEqualTo(Integer.parseInt((String) shopId));
		}
		Object goodsId = mapsToMap.get("goodsId");
		if (shopId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object startTime = mapsToMap.get("startTime");
		if (startTime != null && !"".equals(startTime.toString().trim())) {
			createCriteria.andStartTimeGreaterThanOrEqualTo(DateFormatUtil.convert(startTime.toString()));
		}
		Object endTime = mapsToMap.get("endTime");
		if (endTime != null && !"".equals(endTime.toString().trim())) {
			createCriteria.andEndTimeLessThanOrEqualTo(DateFormatUtil.convert(endTime.toString()));
		}

		Object page = mapsToMap.get("page");
		Object pageSize = mapsToMap.get("pageSize");

		if (page != null && pageSize != null) {
			int pageI = Integer.parseInt((String) page) - 1;
			int pageSizeI = Integer.parseInt((String) pageSize);
			pageI = pageI < 0 ? 0 : pageI;
			pageSizeI = pageSizeI < 0 ? 0 : pageSizeI;
			RowBounds rowBounds = new RowBounds(pageI * pageSizeI, pageSizeI == 0 ? Integer.MAX_VALUE : pageSizeI);
			list = leasemainMapper.selectByExampleWithRowbounds(example, rowBounds);
		} else {
			list = leasemainMapper.selectByExample(example);
		}

		return list;
	}

	/* 
	 * get()方法的实现
	 */
	@Override
	public Leasemain get(String barCode) {
		if (barCode != null && !"".equals(barCode)) {
			Goods goods = goodsService.find(barCode);
			return get(goods);
		}

		return null;
	}

	/* 
	 * get()方法的实现
	 */
	@Override
	public Leasemain get(Goods goods) {
		Leasemain leasemain = null;
		if (goods != null) {
			LeasemainExample leasemainExample = new LeasemainExample();
			leasemainExample.createCriteria().andGoodsIdEqualTo(goods.getGoodsId()).andStateLessThan(3).andStateNotEqualTo(1);
			List<Leasemain> lists = leasemainMapper.selectByExample(leasemainExample);
			if (lists != null && lists.size() > 0) {
				leasemain = lists.get(0);
			}
		}

		return leasemain;
	}

	/* 
	 * getLeasegoods()方法的实现
	 */
	@Override
	public List<Leasemain> getLeasegoods() {
		LeasemainExample example = new LeasemainExample();
		example.createCriteria().andStateLessThan(4);
		return leasemainMapper.selectByExample(example);
	}

	/* 
	 * update()方法的实现
	 */
	@Override
	public boolean update(Leasemain leasemain) {
		return leasemainMapper.updateByPrimaryKey(leasemain) > 0;
	}

	/* 
	 * getLeasegoodsCount()方法的实现
	 */
	@Override
	public int getLeasegoodsCount(Map<String, Object> mapsToMap) {
		LeasemainExample example = new LeasemainExample();
		Criteria createCriteria = example.createCriteria();

		Object state = mapsToMap.get("state");
		if (state != null && !"".equals(state.toString().trim())) {
			createCriteria.andStateEqualTo(Integer.parseInt((String) state));
		}
		Object cardId = mapsToMap.get("cardId");
		if (cardId != null && !"".equals(cardId.toString().trim())) {
			createCriteria.andCardIdEqualTo(cardId.toString());
		}
		Object shopId = mapsToMap.get("shopId");
		if (shopId != null && !"".equals(shopId.toString().trim())) {
			createCriteria.andBorrowIdEqualTo(Integer.parseInt((String) shopId));
		}
		Object goodsId = mapsToMap.get("goodsId");
		if (shopId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object startTime = mapsToMap.get("startTime");
		if (startTime != null && !"".equals(startTime.toString().trim())) {
			createCriteria.andStartTimeGreaterThanOrEqualTo(DateFormatUtil.convert(startTime.toString()));
		}
		Object endTime = mapsToMap.get("endTime");
		if (endTime != null && !"".equals(endTime.toString().trim())) {
			createCriteria.andEndTimeLessThanOrEqualTo(DateFormatUtil.convert(endTime.toString()));
		}

		return leasemainMapper.countByExample(example);
	}
}
