package cn.loverqi.lease.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Shop;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.ShopService;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：ShopCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 管理店铺的类
 */
@RestController
@RequestMapping("shop")
public class ShopCotroller {

	@Autowired
	private ShopService shopService;

	/**
	 * 新建店铺/修改店铺信息
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		Shop shop = RequestParamsToMapUtil.getMapObj(mapsToMap, Shop.class);
		RequestDate requestDate = new RequestDate();

		Object shopId = mapsToMap.get("shopId");
		if (shopId != null && Integer.parseInt(shopId.toString()) == 0) {
			requestDate.setCode(1003);
			requestDate.setMessage("该店铺不支持编辑");
		} else {
			Integer createOrUpdateShop = shopService.createOrUpdateShop(shop);
			if (createOrUpdateShop != null) {
				requestDate.setOnecData("shopId", createOrUpdateShop);
				requestDate.setCodeAndMessage(true);
			} else {
				requestDate.setCodeAndMessage(false);
			}
		}
		return requestDate;
	}

	/**
	 * 删除店铺（system）
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate delete(Integer shopId) {
		RequestDate requestDate = new RequestDate();
		if (shopId.equals(0)) {
			requestDate.setCode(1003);
			requestDate.setMessage("该店铺不支持删除");
		} else {
			boolean delete = shopService.delete(shopId);
			requestDate.setCodeAndMessage(delete);
		}

		return requestDate;
	}

	/**
	 * 店铺信息请求
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(Integer shopId, String name) {
		RequestDate requestDate = new RequestDate();

		List<Shop> findList = shopService.findList(shopId, name);
		if (findList != null && findList.size() > 0) {
			requestDate.setData(findList);
			requestDate.setCodeAndMessage(true);
		} else {
			requestDate.setCodeAndMessage(false);
		}

		return requestDate;
	}
}
