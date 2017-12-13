package cn.loverqi.lease.service;

import java.util.List;

import cn.loverqi.lease.pojo.Dictionary;

/**
 * 项目名称：lease
 * 类名称：DictionaryeService 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 与字典的获取有关的接口
 */
public interface DictionaryeService {

	/**
	 * 获取字典值字段所有获取字典值的方法
	 */
	public Dictionary findDictionartById(Integer dictionaryId, Integer id);

	/**
	 * 获取字典值字段当前字典值
	 */
	public List<Dictionary> findDictionarts(Integer dictionaryId);

}
