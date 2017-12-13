package cn.loverqi.lease.pojo;

import java.io.Serializable;

/**
 * 项目名称：lease
 * 类名称：Dictionary 
 * 创建人：loverqi
 * 创建时间：2017-8-31
 * 类描述： 
 */
public class Dictionary extends DictionaryKey implements Serializable{
    private static final long serialVersionUID = -8429742851055020299L;
	private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }
}