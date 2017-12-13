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

import cn.loverqi.lease.pojo.Flowing;
import cn.loverqi.lease.pojo.Leasemain;
import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.FlowingService;
import cn.loverqi.lease.service.GoodsService;
import cn.loverqi.lease.service.LeaseMainService;
import cn.loverqi.lease.service.ShopService;
import cn.loverqi.lease.service.UserService;
import cn.loverqi.lease.util.FlowingCotrollerUtil;
import cn.loverqi.lease.util.ObjectToMapUtil;

/**
 * 项目名称：lease
 * 类名称：FlowingCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 流水信息的请求
 */
@RestController
@RequestMapping("flowing")
public class FlowingCotroller {

	@Autowired
	private FlowingService flowingService;
	@Autowired
	private LeaseMainService leaseMainService;
	@Autowired
	private UserService userService;
	@Autowired
	private ShopService shopService;
	@Autowired
	private GoodsService goodsService;

	/**
	 * 请求租赁历史（根据条件）
	 */
	@RequestMapping(value = "/leasegoods.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate leasegoods(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();

		List<Leasemain> leasegoods = leaseMainService.getLeasegoods(mapsToMap);
		Map<String, Object> resposeMap = new HashMap<String, Object>();
		
		resposeMap.put("body", getLeasemainInfo(leasegoods));
		resposeMap.put("count", leaseMainService.getLeasegoodsCount(mapsToMap));
		
		requestDate.setCodeAndMessage(leasegoods != null);
		requestDate.setData(resposeMap);

		return requestDate;
	}

	/**
	 * 请求流水记录（根据条件）
	 */
	@RequestMapping(value = "/findFlowing.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate findFlowing(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();

		List<Flowing> list = flowingService.findList(mapsToMap);
		Map<String, Object> resposeMap = new HashMap<String, Object>();
		
		resposeMap.put("body", getFlowingInfo(list));
		resposeMap.put("count", flowingService.findListCount(mapsToMap));
		requestDate.setCodeAndMessage(list != null);
		requestDate.setData(resposeMap);

		return requestDate;
	}

	/**
	 * 请求流水合计信息（根据条件）
	 */
	@RequestMapping(value = "/findFlowings.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate findFlowings(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();

		List<Flowing> list = flowingService.findList(mapsToMap);
		if (list != null) {
			requestDate.setCodeAndMessage(true);

			requestDate.setData(FlowingCotrollerUtil.getConutToMap(list));

		} else {
			requestDate.setCodeAndMessage(false);
		}

		return requestDate;
	}

	/*
	 * 将流水对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getFlowingInfo(List<Flowing> flowings) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Flowing flowing : flowings) {
			map = ObjectToMapUtil.getFieldVlaue(flowing);
			map.remove("shopId");
			map.remove("goodsId");
			map.put("name", userService.findUserByUse(flowing.getCardId()).getName());
			if (flowing.getShopId() != null) {
				map.put("shopName", shopService.findTheOne(flowing.getShopId()).getName());
			} else {
				map.put("shopName", "--");
			}
			if (flowing.getGoodsId() != null) {
				map.put("goodsName", goodsService.find(flowing.getGoodsId()).getGoodsName());
			} else {
				map.put("goodsName", "--");
			}
			lists.add(map);
		}
		return lists;
	}

	/*
	 * 将流水对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getLeasemainInfo(List<Leasemain> leasemains) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Leasemain leasemain : leasemains) {
			map = ObjectToMapUtil.getFieldVlaue(leasemain);
			map.remove("borrowId");
			map.remove("alsoId");
			if (leasemain.getCardId() != null) {
				User user = userService.findUserByUse(leasemain.getCardId());
				map.put("name", user.getName());
				map.put("phone", user.getPhone());
			} else {
				map.put("name", "--");
				map.put("phone", "--");
			}
			if (leasemain.getBorrowId() != null) {
				map.put("borrowName", shopService.findTheOne(leasemain.getBorrowId()).getName());
			} else {
				map.put("borrowName", "--");
			}
			if (leasemain.getAlsoId() != null) {
				map.put("alsoName", shopService.findTheOne(leasemain.getAlsoId()).getName());
			} else {
				map.put("alsoName", "--");
			}
			if (leasemain.getGoodsId() != null) {
				map.put("goodsName", goodsService.find(leasemain.getGoodsId()).getGoodsName());
			} else {
				map.put("goodsName", "--");
			}
			lists.add(map);
		}
		return lists;
	}

}
