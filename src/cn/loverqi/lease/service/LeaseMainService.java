package cn.loverqi.lease.service;

import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.Goods;
import cn.loverqi.lease.pojo.Leasemain;

/**
 * 项目名称：lease
 * 类名称：LeaseMainService 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public interface LeaseMainService {

	/**
	 * 借阅
	 */
	public int lease(String cardId, String barCode, String shopId);

	/**
	 * 归还
	 */
	public Leasemain repay(String goodsId, String shopId);

	/**
	 * 计算金额
	 */
	public double amount(Leasemain leasemain);
	
	/**
	 * 更新状态
	 */
	public boolean update(Leasemain leasemain);

	/**
	 * get
	 */
	public Leasemain get(String barCode);

	/**
	 * get
	 */
	public Leasemain get(Goods goods);

	/**
	 * 获取租赁历史表
	 */
	public List<Leasemain> getLeasegoods(Map<String, Object> mapsToMap);
	
	public int getLeasegoodsCount(Map<String, Object> mapsToMap);

	/**
	 * 获取租赁历史表
	 */
	public List<Leasemain> getLeasegoods();

}
