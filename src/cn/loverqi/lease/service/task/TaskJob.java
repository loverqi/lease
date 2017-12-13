package cn.loverqi.lease.service.task;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.pojo.Grade;
import cn.loverqi.lease.pojo.Leasemain;
import cn.loverqi.lease.pojo.Price;
import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.service.GradeService;
import cn.loverqi.lease.service.LeaseMainService;
import cn.loverqi.lease.service.PriceService;
import cn.loverqi.lease.service.UserService;

/**
 * 设置自动执行任务的接口
 * */
@Service
public class TaskJob {

	@Autowired
	private UserService userService;
	@Autowired
	private GradeService gradeService;
	@Autowired
	private LeaseMainService leaseMainService;
	@Autowired
	private PriceService priceService;

	/**
	 * 每天自动执行的任务
	 * */
	public void doTesk() {
		// 生成流水记录并执行黑名单的的方法
		updateBlack();

		// 整理用户级别
		updateUserLieve();
	}

	// 整理用户的级别信息
	private void updateUserLieve() {
		List<User> users = userService.findUsers();
		List<Grade> grades = gradeService.findListDesc();

		for (User user : users) {
			int newLeve = getTheLeve(user, grades);
			if (!user.getLevel().equals(0) && newLeve != user.getLevel()) {
				user.setLevel(newLeve);
				userService.createOrUpdateUser(user);
			}

		}
	}

	// 生成流水记录并执行黑名单的的方法
	private void updateBlack() {
		List<Leasemain> leasegoods = leaseMainService.getLeasegoods();
		for (Leasemain leasemain : leasegoods) {
			if (leasemain.getState().equals(0)) {// 需要验证是否存在黑名单的用户，并归档

				boolean checkBlack = checkBlack(leasemain);
				if (checkBlack) {// 是黑名单情况
					// 更新用户为黑名单用户
					User user = userService
							.findUserByUse(leasemain.getCardId());
					user.setLevel(0);
					user.setCost(0.0);
					user.setState(1);
					userService.createOrUpdateUser(user);
					// 更新订单状态未黑名单
					leasemain.setState(2);
					leaseMainService.update(leasemain);
				}

			}
		}
	}

	// 获取用户当前的级别信息
	private int getTheLeve(User user, List<Grade> grades) {
		if (user.getLevel().equals(0)) {
			return 0;
		}
		int lev = 1;
		for (Grade grade : grades) {
			if (user.getCost() >= grade.getConsumption()) {
				lev = grade.getGradeId();
				break;
			}
		}

		return lev == 0 ? 1 : lev;
	}

	// 验证是否是黑名单
	private boolean checkBlack(Leasemain leasemain) {
		boolean ifTrue = false;
		long timeLong = (System.currentTimeMillis() - leasemain.getStartTime()
				.getTime()) / (60 * 1000);
		Map<String, List<Price>> map = priceService.findList(
				leasemain.getGoodsId(), leasemain.getBorrowId());

		List<Price> secondList = map.get("second");
		if (secondList != null && secondList.size() > 0) {
			ifTrue = secondList.get(secondList.size() - 1).getDuration() < timeLong;
		} else {
			List<Price> firstList = map.get("first");
			if (firstList != null && firstList.size() > 0) {
				ifTrue = firstList.get(firstList.size() - 1).getDuration() < timeLong;
			}
		}

		return ifTrue;
	}
}
