package cn.loverqi.lease.service;

import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.Price;

/**
 * 项目名称：lease
 * 类名称：PriceService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public interface PriceService {

	/**
	 * 修改价格信息或者创建修改
	 */
	public Integer createOrUpdatePrice(Price price);

	/**
	 * 删除价格
	 */
	public boolean delete(Integer priceId);

	/**
	 * 查询商品的所有价格
	 */
	public Map<String, List<Price>> findList(Integer goodsId, Integer shopId);

	/**
	 * 价格的精确查询方法
	 */
	public Price findTheOne(Integer priceId);

}
