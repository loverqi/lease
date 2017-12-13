package cn.loverqi.lease.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.PriceMapper;
import cn.loverqi.lease.pojo.Price;
import cn.loverqi.lease.pojo.PriceExample;
import cn.loverqi.lease.service.PriceService;

/**
 * 项目名称：lease
 * 类名称：PriceServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class PriceServiceImpl implements PriceService {

	@Autowired
	private PriceMapper priceMapper;

	/* 
	 * createOrUpdatePrice()方法的实现
	 */
	@Override
	public Integer createOrUpdatePrice(Price price) {
		int update = priceMapper.updateByPrimaryKeySelective(price);
		if (update <= 0) {
			update = priceMapper.insertSelective(price);
		}
		return update > 0 ? price.getPriceId() : null;
	}

	/* 
	 * delete()方法的实现
	 */
	@Override
	public boolean delete(Integer priceId) {
		return priceMapper.deleteByPrimaryKey(priceId) > 0;
	}

	/* 
	 * findList()方法的实现
	 */
	@Override
	public Map<String, List<Price>> findList(Integer goodsId, Integer shopId) {
		Map<String, List<Price>> map = new HashMap<String, List<Price>>();
		PriceExample example = new PriceExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId).andShopIdEqualTo(shopId).andPatternIsNull();
		example.setOrderByClause("duration asc");

		map.put("first", priceMapper.selectByExample(example));

		example = new PriceExample();
		example.createCriteria().andGoodsIdEqualTo(goodsId).andShopIdEqualTo(shopId).andPatternIsNotNull();
		example.setOrderByClause("duration asc");

		List<Price> second = priceMapper.selectByExample(example);
		map.put("second", second != null && second.size() > 1 ? second.subList(0, 1) : second);

		return map;
	}

	/* 
	 * findTheOne()方法的实现
	 */
	@Override
	public Price findTheOne(Integer priceId) {
		return priceMapper.selectByPrimaryKey(priceId);
	}

}
