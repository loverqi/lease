package cn.loverqi.lease.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Goods;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.CategoryService;
import cn.loverqi.lease.service.GoodsService;
import cn.loverqi.lease.util.ObjectToMapUtil;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：CategoryCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 与产品类别有关的操作
 */
@RestController
@RequestMapping("goods")
public class GoodsCotroller {

	@Autowired
	private GoodsService goodsService;
	@Autowired
	private CategoryService categoryService;

	/**
	 * 产品列表的查询（根据条件）
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();

		List<Goods> list = goodsService.findList(mapsToMap);
		Map<String, Object> resposeMap = new HashMap<String, Object>();
		
		resposeMap.put("body", getGoodsInfo(list));
		resposeMap.put("count", goodsService.findListCount(mapsToMap));
		
		requestDate.setCodeAndMessage(list != null && list.size() >= 0);
		requestDate.setData(resposeMap);

		return requestDate;
	}

	/**
	 * 产品录入/产品信息修改
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		Goods goods = RequestParamsToMapUtil.getMapObj(mapsToMap, Goods.class);
		RequestDate requestDate = new RequestDate();

		Integer createOrUpdateGoods = goodsService.createOrUpdateGoods(goods);
		if (createOrUpdateGoods != null) {
			requestDate.setCodeAndMessage(true);
			requestDate.setOnecData("goodsId", createOrUpdateGoods);
		} else {
			requestDate.setCodeAndMessage(false);
		}

		return requestDate;
	}

	/**
	 * 产品删除
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate delete(Integer goodsId) {
		RequestDate requestDate = new RequestDate();

		boolean delete = goodsService.delete(goodsId);
		requestDate.setCodeAndMessage(delete);

		return requestDate;
	}

	/*
	 * 将商品对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getGoodsInfo(List<Goods> goodss) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Goods goods : goodss) {
			map = ObjectToMapUtil.getFieldVlaue(goods);
			map.remove("categoryId");
			map.put("categoryName", categoryService.findTheOne(goods.getCategoryId()).getCategoryName());
			lists.add(map);
		}
		return lists;
	}

}
