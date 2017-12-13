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

import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.GradeService;
import cn.loverqi.lease.service.UserService;
import cn.loverqi.lease.util.ObjectToMapUtil;
import cn.loverqi.lease.util.RequestParamsToMapUtil;

/**
 * 项目名称：lease
 * 类名称：UserCotroller 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 用户管理的cotroller
 */
@RestController
@RequestMapping("user")
public class UserCotroller {

	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;

	/**
	 * 新建用户/修改用户信息
	 */
	@RequestMapping(value = "/update.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate update(@RequestParam Map<String, Object> mapsToMap) {
		User user = RequestParamsToMapUtil.getMapObj(mapsToMap, User.class);
		RequestDate requestDate = new RequestDate();

		boolean createOrUpdateUser = userService.createOrUpdateUser(user);

		requestDate.setCodeAndMessage(createOrUpdateUser);

		return requestDate;
	}

	/**
	 * 用户充值/用户余额修改
	 */
	@RequestMapping(value = "/recharge.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate recharge(String cardId, double balance, Integer state, Integer shopId) {
		RequestDate requestDate = new RequestDate();
		boolean updateUserRecharge = userService.updateUserRecharge(cardId, balance, state == null || state.equals(0), shopId);

		requestDate.setCodeAndMessage(updateUserRecharge);

		return requestDate;
	}

	/**
	 * 根据条件模糊查询用户
	 */
	@RequestMapping(value = "/find.action", method = { RequestMethod.POST, RequestMethod.GET })
	public RequestDate find(@RequestParam Map<String, Object> mapsToMap) {
		RequestDate requestDate = new RequestDate();
		List<User> users = userService.findUsers(mapsToMap);
		Map<String, Object> resposeMap = new HashMap<String, Object>();
		
		resposeMap.put("body", getUsesInfo(users));
		resposeMap.put("count", userService.findUsersCount(mapsToMap));

		requestDate.setCodeAndMessage(users != null);
		requestDate.setData(resposeMap);

		return requestDate;
	}

	/*
	 * 将user对象封装为需要的格式
	 * */
	private List<Map<String, Object>> getUsesInfo(List<User> users) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = null;
		for (User user : users) {
			map = ObjectToMapUtil.getFieldVlaue(user);
			map.put("level", gradeService.getLevelByid(user.getLevel()));
			lists.add(map);
		}
		return lists;
	}
}
