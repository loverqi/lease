package cn.loverqi.lease.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.FlowingMapper;
import cn.loverqi.lease.pojo.Flowing;
import cn.loverqi.lease.pojo.FlowingExample;
import cn.loverqi.lease.pojo.FlowingExample.Criteria;
import cn.loverqi.lease.service.CategoryService;
import cn.loverqi.lease.service.FlowingService;
import cn.loverqi.lease.service.GoodsService;
import cn.loverqi.lease.util.DateFormatUtil;

/**
 * 项目名称：lease
 * 类名称：FlowingServiceImpl 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
@Service
public class FlowingServiceImpl implements FlowingService {

	@Autowired
	private FlowingMapper flowingMapper;
	@Autowired
	private CategoryService categoryService;
	@Autowired
	private GoodsService goodsService;

	/* 
	 * findList()方法的实现
	 */
	@Override
	public List<Flowing> findList(Map<String, Object> mapsToMap) {
		List<Flowing> list = null;
		FlowingExample example = new FlowingExample();
		Criteria createCriteria = example.createCriteria();

		Object state = mapsToMap.get("state");
		if (state != null && !"".equals(state.toString().trim())) {
			createCriteria.andStateEqualTo(Integer.parseInt((String) state));
		}
		Object categoryName = mapsToMap.get("categoryName");
		if (categoryName != null && !"".equals(categoryName.toString().trim())) {
			List<Integer> idLists = categoryService.findIdListByName(((String) categoryName).trim());
			if (idLists != null && idLists.size() == 0) {
				idLists.add(-1);
			}
			List<Integer> findIdList = goodsService.findIdList(idLists);
			if (findIdList != null && findIdList.size() == 0) {
				findIdList.add(-1);
			}
			createCriteria.andGoodsIdIn(findIdList);
		}
		Object goodsId = mapsToMap.get("goodsId");
		if (goodsId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object shopId = mapsToMap.get("shopId");
		if (shopId != null && !"".equals(shopId.toString().trim())) {
			createCriteria.andShopIdEqualTo(Integer.parseInt((String) shopId));
		}
		Object startTime = mapsToMap.get("startTime");
		if (startTime != null && !"".equals(startTime.toString().trim())) {
			createCriteria.andCreateDateGreaterThanOrEqualTo(DateFormatUtil.convert(startTime.toString()));
		}
		Object endTime = mapsToMap.get("endTime");
		if (endTime != null && !"".equals(endTime.toString().trim())) {
			createCriteria.andCreateDateLessThanOrEqualTo(DateFormatUtil.convert(endTime.toString()));
		}

		Object page = mapsToMap.get("page");
		Object pageSize = mapsToMap.get("pageSize");

		if (page != null && pageSize != null) {
			int pageI = Integer.parseInt((String) page) - 1;
			int pageSizeI = Integer.parseInt((String) pageSize);
			pageI = pageI < 0 ? 0 : pageI;
			pageSizeI = pageSizeI < 0 ? 0 : pageSizeI;
			RowBounds rowBounds = new RowBounds(pageI * pageSizeI, pageSizeI == 0 ? Integer.MAX_VALUE : pageSizeI);
			list = flowingMapper.selectByExampleWithRowbounds(example, rowBounds);
		} else {
			list = flowingMapper.selectByExample(example);
		}

		return list;
	}

	/* 
	 * find()方法的实现
	 */
	@Override
	public Flowing find(Integer flowingId) {
		return flowingMapper.selectByPrimaryKey(flowingId);
	}

	/* 
	 * create()方法的实现
	 */
	@Override
	public Integer create(Flowing flowing) {
		int insert = flowingMapper.insert(flowing);
		return insert > 0 ? flowing.getId() : null;
	}

	/* 
	 * findListCount()方法的实现
	 */
	@Override
	public int findListCount(Map<String, Object> mapsToMap) {
		FlowingExample example = new FlowingExample();
		Criteria createCriteria = example.createCriteria();

		Object state = mapsToMap.get("state");
		if (state != null && !"".equals(state.toString().trim())) {
			createCriteria.andStateEqualTo(Integer.parseInt((String) state));
		}
		Object categoryName = mapsToMap.get("categoryName");
		if (categoryName != null && !"".equals(categoryName.toString().trim())) {
			List<Integer> idLists = categoryService.findIdListByName(((String) categoryName).trim());
			if (idLists != null && idLists.size() == 0) {
				idLists.add(-1);
			}
			List<Integer> findIdList = goodsService.findIdList(idLists);
			if (findIdList != null && findIdList.size() == 0) {
				findIdList.add(-1);
			}
			createCriteria.andGoodsIdIn(findIdList);
		}
		Object goodsId = mapsToMap.get("goodsId");
		if (goodsId != null && !"".equals(goodsId.toString().trim())) {
			createCriteria.andGoodsIdEqualTo(Integer.parseInt((String) goodsId));
		}
		Object shopId = mapsToMap.get("shopId");
		if (shopId != null && !"".equals(shopId.toString().trim())) {
			createCriteria.andShopIdEqualTo(Integer.parseInt((String) shopId));
		}
		Object startTime = mapsToMap.get("startTime");
		if (startTime != null && !"".equals(startTime.toString().trim())) {
			createCriteria.andCreateDateGreaterThanOrEqualTo(DateFormatUtil.convert(startTime.toString()));
		}
		Object endTime = mapsToMap.get("endTime");
		if (endTime != null && !"".equals(endTime.toString().trim())) {
			createCriteria.andCreateDateLessThanOrEqualTo(DateFormatUtil.convert(endTime.toString()));
		}


		return flowingMapper.countByExample(example);
	}

}
