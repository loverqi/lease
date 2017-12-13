package cn.loverqi.lease.service;

import java.util.List;

import cn.loverqi.lease.pojo.Shop;

/**
 * 项目名称：lease
 * 类名称：ShopService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface ShopService {

	/**
	 * 修改店铺信息或者创建店铺
	 */
	public Integer createOrUpdateShop(Shop shop);

	/**
	 * 删除店铺
	 */
	public boolean delete(Integer shopId);

	/**
	 * 店铺的模糊查询方法
	 */
	public List<Shop> findList(Integer shopId, String name);

	/**
	 * 店铺的精确查询方法
	 */
	public Shop findTheOne(Integer shopId);

}
