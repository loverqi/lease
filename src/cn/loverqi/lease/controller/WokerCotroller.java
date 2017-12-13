package cn.loverqi.lease.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.cache.RedisCatch;
import cn.loverqi.lease.cache.Token;
import cn.loverqi.lease.pojo.Woker;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.ShopService;
import cn.loverqi.lease.service.WokerService;
import cn.loverqi.lease.util.MD5Util;
import cn.loverqi.lease.util.ObjectToMapUtil;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：WokerCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-26
 * 类描述： 员工管理和登陆的控制器
 */
@RestController
@RequestMapping("woker")
public class WokerCotroller {

	@Autowired
	private RedisCatch redisCatch;
	@Autowired
	private WokerService wokerService;
	@Autowired
	private ShopService shopService;

	/**
	 * 登陆的方法
	 */
	@RequestMapping(value = "/login.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate login(String username, String password) {
		RequestDate requestDate = new RequestDate();

		Woker findWokerByUse = wokerService.findWokerByUse(username);
		if (findWokerByUse != null) {

			boolean findWokerByPsw = wokerService.findWokerByPsw(username, MD5Util.getMD5Str(password));
			if (findWokerByPsw) {
				requestDate.setCode(0);
				requestDate.setMessage("登陆成功");
				Token token = redisCatch.getToken();
				token.setAttribute("username", username);
				redisCatch.setToken(token);
				boolean state = findWokerByUse.getState().equals(0);
				requestDate.setOnecData("isSystem", state);
				Integer shopId = findWokerByUse.getShopId();
				requestDate.setOnecData("shopId", shopId != null && !shopId.equals(0) ? shopId : null);
				requestDate.setToken(token.getTokenId());
			} else {
				requestDate.setCode(2001);
				requestDate.setMessage("密码错误");
			}
		} else {
			setNotFindCode(requestDate);
		}

		return requestDate;
	}

	/**
	 * 退出登陆的方法
	 */
	@RequestMapping(value = "/unlogin.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate unlogin(String token) {

		RequestDate requestDate = new RequestDate();
		if (redisCatch.existsToken(token)) {
			Token tokenObj = redisCatch.getToken(token);
			tokenObj.setMaxInactiveInterval(0);
			redisCatch.setToken(tokenObj);
		}
		requestDate.setCodeAndMessage(true);

		return requestDate;
	}

	/**
	 * 员工用户信息请求(仅system可查询)
	 */
	@RequestMapping(value = "/userstart.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate userstart(Integer shopId, String username) {
		RequestDate requestDate = new RequestDate();

		List<Woker> list = wokerService.findWokers(shopId, username);

		requestDate.setCode(0);
		requestDate.setMessage("success");
		requestDate.setData(getUsesInfo(list));

		return requestDate;
	}

	/**
	 * 新建员工账户/修改员工账户信息
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {

		Woker woker = RequestParamsToMapUtil.getMapObj(mapsToMap, Woker.class);

		boolean createOrUpdateWoker = wokerService.createOrUpdateWoker(woker);

		RequestDate requestDate = new RequestDate();
		requestDate.setCodeAndMessage(createOrUpdateWoker);

		return requestDate;
	}

	/**
	 * 密码修改（员工自己操作，包括system账户）
	 */
	@RequestMapping(value = "/updatePwd.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate updatePwd(String username, String password, String newPassword) {
		RequestDate requestDate = new RequestDate();

		if (wokerService.findWokerByUse(username) != null) {
			boolean findWokerByPsw = wokerService.findWokerByPsw(username, MD5Util.getMD5Str(password));
			if (findWokerByPsw) {
				boolean updateWokerPsw = wokerService.updateWokerPsw(username, MD5Util.getMD5Str(newPassword));
				requestDate.setCodeAndMessage(updateWokerPsw);
			} else {
				requestDate.setCode(2001);
				requestDate.setMessage("密码错误");
			}
		} else {
			setNotFindCode(requestDate);
		}

		return requestDate;
	}

	/**
	 * 删除员工（system）
	 */
	@RequestMapping(value = "/delete.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate delete(String username) {
		RequestDate requestDate = new RequestDate();
		if (wokerService.findWokerByUse(username) != null) {
			boolean deleteWoker = wokerService.deleteWoker(username);
			requestDate.setCodeAndMessage(deleteWoker);
		} else {
			setNotFindCode(requestDate);
		}

		return requestDate;
	}

	// 用户不存在时
	private void setNotFindCode(RequestDate requestDate) {
		requestDate.setCode(1005);
		requestDate.setMessage("用户不存在");
	}

	/*
	 * 将shop对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getUsesInfo(List<Woker> wokers) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (Woker woker : wokers) {
			map = ObjectToMapUtil.getFieldVlaue(woker);
			map.remove("password");
			map.remove("state");
			if (woker.getShopId() != null) {
				map.put("shopName", shopService.findTheOne(woker.getShopId()).getName());
			} else {
				map.put("shopName", "管理员用户");
			}
			lists.add(map);
		}
		return lists;
	}
}
