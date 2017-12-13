package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：lease
 * 类名称：DictionaryKey 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public class DictionaryKey implements Serializable{
	private static final long serialVersionUID = -5069576916937671537L;

	private Integer dictionaryId;

	private Integer id;

	public Integer getDictionaryId() {
		return dictionaryId;
	}

	public void setDictionaryId(Integer dictionaryId) {
		this.dictionaryId = dictionaryId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public DictionaryKey() {
	}

	public DictionaryKey(Integer dictionaryId, Integer id) {
		this.dictionaryId = dictionaryId;
		this.id = id;
	}
	
	
}