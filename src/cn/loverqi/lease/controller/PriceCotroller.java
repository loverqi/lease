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
import cn.loverqi.lease.pojo.Price;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.GoodsService;
import cn.loverqi.lease.service.PriceService;
import cn.loverqi.lease.service.ShopService;
import cn.loverqi.lease.util.ObjectToMapUtil;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：PriceCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 商品价格的维护
 */
@RestController
@RequestMapping("price")
public class PriceCotroller {

	@Autowired
	private PriceService priceService;
	@Autowired
	private GoodsService goodsService;
	@Autowired
	private ShopService shopService;

	/**
	 * 产品价格查询
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(String bar, Integer shopId) {
		RequestDate requestDate = new RequestDate();
		if (bar != null && bar.length() > 2) {
			Goods goods = goodsService.find(bar.trim().substring(2, bar.length()));
			if (goods != null) {
				Map<String, List<Price>> map = priceService.findList(goods.getGoodsId(), shopId);
				requestDate.setCodeAndMessage(map != null);

				Map<String, Object> resposMap = new HashMap<String, Object>();
				resposMap.put("first", getPriceInfo(map.get("first")));
				resposMap.put("second", getPriceInfo(map.get("second")));

				requestDate.setData(resposMap);
			} else {
				requestDate.setCode(1004);
				requestDate.setMessage("商品不存在");
			}
		} else {
			requestDate.setCodeAndMessage(false);
		}

		return requestDate;
	}

	/**
	 * 产品价格维护
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();
		String bar = mapsToMap.get("bar") == null ? null : mapsToMap.get("bar").toString();
		Goods goods = goodsService.find(bar != null && bar.length() > 2 ? bar.trim().substring(2, bar.length()) : null);
		String shopId = mapsToMap.get("shopId").toString();
		if (goods == null && mapsToMap.get("priceId").toString() == null) {
			requestDate.setCode(1004);
			requestDate.setMessage("商品不存在");
		} else if (shopService.findTheOne(Integer.parseInt(shopId.trim())) == null) {
			requestDate.setCode(1003);
			requestDate.setMessage("店铺未开启");
		} else if (Integer.parseInt(shopId.trim().toString()) == 0) {
			requestDate.setCode(1003);
			requestDate.setMessage("店铺不支持添加商品");
		} else {
			mapsToMap.put("goodsId", goods == null ? null : goods.getGoodsId());
			Price price = RequestParamsToMapUtil.getMapObj(mapsToMap, Price.class);

			Integer createOrUpdatePrice = priceService.createOrUpdatePrice(price);
			if (createOrUpdatePrice != null) {
				requestDate.setOnecData("priceId", createOrUpdatePrice);
				requestDate.setCodeAndMessage(true);
			} else {
				requestDate.setCode(1000);
				requestDate.setMessage("价格添加失败，请查看参数。");
			}
		}

		return requestDate;
	}

	/**
	 * 产品价格删除
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate delete(Integer priceId) {
		RequestDate requestDate = new RequestDate();

		boolean delete = priceService.delete(priceId);

		requestDate.setCodeAndMessage(delete);

		return requestDate;
	}

	/*
	 * 将价格对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getPriceInfo(List<Price> prices) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Price price : prices) {
			map = ObjectToMapUtil.getFieldVlaue(price);
			map.remove("goodsId");
			lists.add(map);
		}
		return lists;
	}

}
