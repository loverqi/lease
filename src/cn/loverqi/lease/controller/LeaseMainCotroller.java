package cn.loverqi.lease.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.loverqi.lease.pojo.Leasemain;
import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.GradeService;
import cn.loverqi.lease.service.LeaseMainService;
import cn.loverqi.lease.service.UserService;

/**
 * 项目名称：lease 类名称：LeaseMainCotroller 创建人：loverqi 创建时间：2017-8-27 类描述：
 * 该项目的主操作管理，主要是与租赁有关的各项操作
 */
@RestController
@RequestMapping("leasemain")
public class LeaseMainCotroller {

	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private LeaseMainService leaseMainService;

	/**
	 * 租赁前用户信息请求
	 */
	@RequestMapping(value = "/userstart.action", method = { RequestMethod.POST,
			RequestMethod.GET })
	public RequestDate userstart(String cardId) {
		RequestDate requestDate = new RequestDate();

		User user = userService.findUserByUse(cardId);
		if (user != null) {
			requestDate.setData(getShortUsesInfo(user, false));
			requestDate.setCodeAndMessage(true);
		} else {
			requestDate.setCode(1005);
			requestDate.setMessage("用户不存在");
		}

		return requestDate;
	}

	/**
	 * 请求租赁/租赁结束
	 */
	@RequestMapping(value = "/leasegoods.action", method = {
			RequestMethod.POST, RequestMethod.GET })
	public RequestDate leasegoods(String cardId, String barCode, String shopId) {
		RequestDate requestDate = new RequestDate();
		boolean ifSuccess = false;
		boolean ifAlso = false;
		double cost = 0;

		if (barCode != null && shopId != null && !shopId.equals("0")) {
			barCode = barCode.trim();
			Leasemain leasemain = null;

			if (barCode.length() > 2) {
				String state = barCode.substring(0, 2);
				if ("00".equals(state)) {
					// 借
					if (cardId != null) {
						ifAlso = false;
						cardId = cardId.trim();
						int getCode = leaseMainService.lease(cardId,
								barCode.substring(2, barCode.length()), shopId);
						switch (getCode) {
						case 0:
							ifSuccess = true;
							break;
						case 1:
							ifSuccess = false;
							requestDate.setCode(1004);
							requestDate.setMessage("商品不存在");
							break;
						case 2:
							ifSuccess = false;
							requestDate.setCode(4001);
							requestDate.setMessage("商品不在库");
							break;
						case 3:
							ifSuccess = false;
							requestDate.setCode(1005);
							requestDate.setMessage("未刷卡或用户不存在");
							break;
						case 4:
							ifSuccess = false;
							requestDate.setCode(4002);
							requestDate.setMessage("黑名单用户");
							break;

						default:
							ifSuccess = false;
							requestDate.setCode(1000);
							requestDate.setMessage("系统异常");
							break;
						}

					}
				} else if ("01".equals(state)) {
					// 还
					leasemain = leaseMainService.repay(
							barCode.substring(2, barCode.length()), shopId);
					ifSuccess = leasemain != null;
					if (ifSuccess) {
						cost = leasemain.getCost();
					}
					ifAlso = true;
				} else {
					requestDate.setCode(1000);
					requestDate.setMessage("条码扫描失败请重试");
				}

				// 最后进行数据的修改
				if (ifSuccess) {
					requestDate.setCodeAndMessage(true);
					requestDate.setOnecData("next", ifAlso);
					requestDate.setOnecData("ifAlso", ifAlso);
					if (ifAlso) {
						requestDate.setOnecData("cost", cost);
						if (leasemain != null) {
							requestDate.setOnecData("user", getShortUsesInfo(userService.findUserByUse(leasemain.getCardId()), true));
						}
					} else {
						requestDate.setOnecData("barCode", barCode);
					}
				} else if (requestDate.getCode() == 0) {
					requestDate.setCodeAndMessage(false);
				}
			} else {
				requestDate.setCode(1000);
				requestDate.setMessage("条码扫描失败请重试");
			}

		} else {
			requestDate.setCode(1000);
			requestDate.setMessage("该商店无法租赁");
		}

		return requestDate;
	}

	/*
	 * 将user对象封装为需要的格式
	 */
	private Map<String, Object> getShortUsesInfo(User user, boolean ifAlso) {
		Map<String, Object> map = null;

		if (user != null) {
			map = new HashMap<String, Object>();
			map.put("cardId", user.getCardId());
			map.put("name", user.getName());
			map.put("balance", user.getBalance());
			map.put("ifAlso", ifAlso ? 1 : 0);
			if (user.getLevel() != null) {
				map.put("level", gradeService.getLevelByid(user.getLevel()));
			} else {
				map.put("level", "未配置");
			}
		}

		return map;
	}

}
