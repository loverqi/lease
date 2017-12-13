package cn.loverqi.lease.service;

import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.User;


/**
 * 项目名称：lease
 * 类名称：WokerService 
 * 创建人：loverqi
 * 创建时间：2017-8-29
 * 类描述：与员工有关的操作 
 */
public interface UserService {

	/**
	 * 根据条件更新员工信息的方法
	 */
	public boolean createOrUpdateUser(User user);

	/**
	 * 更改用户余额的方法
	 */
	public boolean updateUserRecharge(String cardId, double balance, boolean isReset, Integer shopId);

	/**
	 * 查询用户的方法
	 */
	public List<User> findUsers(Map<String, Object> examplesMap);
	
	public int findUsersCount(Map<String, Object> examplesMap);
	
	/**
	 * 查询用户的方法
	 */
	public List<User> findUsers();

	/**
	 * 查询用户的方法
	 */
	public User findUserByUse(String cardId);

}
