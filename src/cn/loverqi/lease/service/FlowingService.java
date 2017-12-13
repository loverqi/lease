package cn.loverqi.lease.service;

import java.util.List;
import java.util.Map;

import cn.loverqi.lease.pojo.Flowing;

/**
 * 项目名称：lease
 * 类名称：FlowingService 
 * 创建人：loverqi
 * 创建时间：2017-9-1
 * 类描述： 
 */
public interface FlowingService {

	/**
	 * 根据条件请求消息列表的方法
	 */
	public List<Flowing> findList(Map<String, Object> mapsToMap);
	
	public int findListCount(Map<String, Object> mapsToMap);

	/**
	 * 根据主键请求消息的方法
	 */
	public Flowing find(Integer flowingId);
	
	/**
	 * 创建流水记录
	 * */
	public Integer create(Flowing flowing);

}
