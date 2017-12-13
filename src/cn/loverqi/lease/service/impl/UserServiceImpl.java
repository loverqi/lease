package cn.loverqi.lease.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.UserMapper;
import cn.loverqi.lease.pojo.Flowing;
import cn.loverqi.lease.pojo.User;
import cn.loverqi.lease.pojo.UserExample;
import cn.loverqi.lease.pojo.UserExample.Criteria;
import cn.loverqi.lease.service.FlowingService;
import cn.loverqi.lease.service.UserService;

/**
 * 项目名称：lease
 * 类名称：UserServiceImpl
 * 创建人：loverqi
 * 创建时间：2017-8-30
 * 类描述：UserService的实现类
 */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserMapper userMapper;
	@Autowired
	private FlowingService flowingService;

	/*
	 * createOrUpdateUser()方法的实现
	 */
	@Override
	public boolean createOrUpdateUser(User user) {
		int update = 0;
		update = userMapper.updateByPrimaryKeySelective(user);
		if (update <= 0) {
			user.setLevel(1);
			update = userMapper.insertSelective(user);
		}
		return update > 0;
	}

	/*
	 * updateUserRecharge()方法的实现
	 */
	@Override
	public boolean updateUserRecharge(String cardId, double balance,
			boolean isReset, Integer shopId) {
		Flowing flowing = new Flowing();
		if (isReset) {
			// 写入用户充值流水
			flowing.setCost(balance);
			flowing.setShopId(shopId);
			flowing.setState(0);
			flowing.setCardId(cardId.trim());
			flowing.setCreateDate(new Date());
			flowing.setRemark("用户充值记录");
			flowingService.create(flowing);

			balance = this.findUserByUse(cardId.trim()).getBalance() + balance;
		} else {
			flowing.setCost(balance);
			flowing.setShopId(shopId);
			flowing.setState(0);
			flowing.setCardId(cardId.trim());
			flowing.setCreateDate(new Date());
			flowing.setRemark("用户余额修改");
			flowingService.create(flowing);
		}
		User user = findUserByUse(cardId);
		user.setBalance(balance);
		if (user.getState().equals(1) && user.getLevel().equals(0)) {
			user.setState(0);
			user.setLevel(1);
		}
		return userMapper.updateByPrimaryKeySelective(user) > 0;
	}

	/*
	 * findUsers()方法的实现
	 */
	@Override
	public List<User> findUsers(Map<String, Object> examplesMap) {
		List<User> users = null;
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();

		Object cardId = examplesMap.get("cardId");
		if (cardId != null && !"".equals(cardId.toString().trim())) {
			createCriteria.andCardIdLike("%" + cardId + "%");
		}
		Object phone = examplesMap.get("phone");
		if (phone != null && !"".equals(phone.toString().trim())) {
			createCriteria.andPhoneLike("%" + phone + "%");
		}
		Object name = examplesMap.get("name");
		if (name != null && !"".equals(name.toString().trim())) {
			createCriteria.andNameLike("%" + name + "%");
		}
		Object level = examplesMap.get("level");
		if (level != null && !"".equals(level.toString().trim())) {
			createCriteria.andLevelEqualTo(Integer.parseInt((String) level));
		}

		Object page = examplesMap.get("page");
		Object pageSize = examplesMap.get("pageSize");
		if (page != null && pageSize != null) {
			int pageI = Integer.parseInt((String) page) - 1;
			int pageSizeI = Integer.parseInt((String) pageSize);
			pageI = pageI < 0 ? 0 : pageI;
			pageSizeI = pageSizeI < 0 ? 0 : pageSizeI;
			RowBounds rowBounds = new RowBounds(pageI * pageSizeI,
					pageSizeI == 0 ? Integer.MAX_VALUE : pageSizeI);
			users = userMapper.selectByExampleWithRowbounds(example, rowBounds);
		} else {
			users = userMapper.selectByExample(example);
		}

		return users;
	}

	@Override
	public int findUsersCount(Map<String, Object> examplesMap) {
		UserExample example = new UserExample();
		Criteria createCriteria = example.createCriteria();

		Object cardId = examplesMap.get("cardId");
		if (cardId != null && !"".equals(cardId.toString().trim())) {
			createCriteria.andCardIdLike("%" + cardId + "%");
		}
		Object phone = examplesMap.get("phone");
		if (phone != null && !"".equals(phone.toString().trim())) {
			createCriteria.andPhoneLike("%" + phone + "%");
		}
		Object name = examplesMap.get("name");
		if (name != null && !"".equals(name.toString().trim())) {
			createCriteria.andNameLike("%" + name + "%");
		}
		Object level = examplesMap.get("level");
		if (level != null && !"".equals(level.toString().trim())) {
			createCriteria.andLevelEqualTo(Integer.parseInt((String) level));
		}

		return userMapper.countByExample(example);
	}

	/*
	 * findUserByUse()方法的实现
	 */
	@Override
	public User findUserByUse(String cardId) {
		if (cardId == null || "".equals(cardId.trim())) {
			return null;
		}
		UserExample example = new UserExample();
		example.createCriteria().andCardIdEqualTo(cardId.trim())
				.andStateGreaterThanOrEqualTo(0);
		List<User> users = userMapper.selectByExample(example);
		return users.size() > 0 ? users.get(0) : null;
	}

	/*
	 * findUsers()方法的实现
	 */
	@Override
	public List<User> findUsers() {
		return userMapper.selectByExample(null);
	}

}
