package cn.loverqi.lease.service;

import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.Goods;

/**
 * 项目名称：lease
 * 类名称：GoodsService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface GoodsService {

	/**
	 * 根据条件更新员工信息的方法
	 */
	public Integer createOrUpdateGoods(Goods goods);

	/**
	 * 更改用户余额的方法
	 */
	public boolean delete(Integer goodsId);

	/**
	 * 查询用户的方法
	 */
	public List<Goods> findList(Map<String, Object> examplesMap);
	
	public int findListCount(Map<String, Object> examplesMap);

	/**
	 * 查询用户的方法
	 */
	public Goods find(Integer goodsId);
	
	/**
	 * 查询用户的方法
	 */
	public Goods find(String bar);
	
	/**
	 * 查询用户的方法
	 */
	public List<Integer> findIdList(List<Integer> category);

}
