package cn.loverqi.lease.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.loverqi.lease.mapper.DictionaryMapper;
import cn.loverqi.lease.pojo.Dictionary;
import cn.loverqi.lease.pojo.DictionaryExample;
import cn.loverqi.lease.pojo.DictionaryKey;
import cn.loverqi.lease.service.DictionaryeService;

/**
 * 项目名称：lease
 * 类名称：DictionaryeServiceimpl 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
@Service
public class DictionaryeServiceImpl implements DictionaryeService {

	@Autowired
	private DictionaryMapper dictionaryMapper;

	/* 
	 * findDictionartById()方法的实现
	 */
	@Override
	public Dictionary findDictionartById(Integer dictionaryId, Integer id) {
		return dictionaryMapper.selectByPrimaryKey(new DictionaryKey(dictionaryId, id));
	}

	/* 
	 * findDictionarts()方法的实现
	 */
	@Override
	public List<Dictionary> findDictionarts(Integer dictionaryId) {

		DictionaryExample example = new DictionaryExample();
		example.createCriteria().andDictionaryIdEqualTo(dictionaryId);
		return dictionaryMapper.selectByExample(example);
	}

}
