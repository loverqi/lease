package cn.loverqi.lease.service;

import java.util.List;

import cn.loverqi.lease.pojo.Woker;

/**
 * 项目名称：lease
 * 类名称：WokerService 
 * 创建人：loverqi
 * 创建时间：2017-8-29
 * 类描述：与员工有关的操作 
 */
public interface WokerService {

	/**
	 * 用户登陆有关的操作
	 */
	public boolean findWokerByPsw(String username, String password);

	/**
	 * 查询用户是否存在
	 */
	public Woker findWokerByUse(String username);

	/**
	 * 获取所有的用户列表的方法
	 */
	public List<Woker> findWokers(Integer shopId, String username);

	/**
	 * 根据条件更新员工信息的方法
	 */
	public boolean createOrUpdateWoker(Woker woker);

	/**
	 * 更新员工密码的方法
	 */
	public boolean updateWokerPsw(String username, String password);

	/**
	 * 停用某个员工
	 */
	public boolean deleteWoker(String username);

}
