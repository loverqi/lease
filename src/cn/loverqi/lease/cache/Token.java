package cn.loverqi.lease.cache;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.junit.Test;

/**
 * 项目名称：lease
 * 类名称：Token 
 * 创建人：loverqi
 * 创建时间：2017-8-30
 * 类描述： token的实体类，再redis维护缓存对象时使用
 */
public class Token implements Serializable {

	private static final long serialVersionUID = -4560809731143680172L;

	// 静态字段生存时间状态值
	public static final int ACQUIESCENCE = -2;
	// 默认生存时间默认值, 整个系统有效，默认为2小时
	public static long DEFAULTINACTIVEINTERVAL = 2 * 60 * 60;

	private String tokenId = null;
	private Map<String, Serializable> attributes = null;
	private long inactiveInterval = -2;

	// 返回Token的ID。该ID由服务器自动创建，不会重复
	public synchronized String getTokenId() {
		if (tokenId == null || "".equals(tokenId.trim())) {
			tokenId = UUID.randomUUID().toString().replaceAll("-", "");
		}
		return tokenId;
	}

	// 设置Token属性。value必须是实现了Serializable接口的bean对象
	public synchronized void setAttribute(String attribute, Serializable value) {
		if (attributes == null || attributes.isEmpty()) {
			attributes = new HashMap<String, Serializable>();
		}
		attributes.put(attribute, value);
	}

	// 判断是否存在某个属性
	public boolean containsAttribute(String attribute) {
		return attributes != null && attributes.isEmpty() && attributes.containsKey(attribute) && attributes.get(attribute) != null;
	}

	// 返回Token属性
	public Object getAttribute(String attribute) {
		if (attributes == null || attributes.isEmpty()) {
			return null;
		}
		return attributes.get(attribute);
	}

	// 返回Token中存在的属性名
	public Set<String> getAttributeNames() {
		if (attributes == null || attributes.isEmpty()) {
			return null;
		}
		return attributes.keySet();
	}

	// 移除Token属性
	public void removeAttribute(String attribute) {
		attributes.remove(attribute);
	}

	// 移除所有的属性
	public void removeAll() {
		attributes.clear();
	}

	// 返回Token的超时时间。单位为秒。-2为默认失效时间，每次请求都更新为两个小时，-1为不失效，0为立即失效，
	public long getMaxInactiveInterval() {
		return inactiveInterval;
	}

	// 设置Token的超时时间。单位为秒
	public void setMaxInactiveInterval(long inactiveInterval) {
		this.inactiveInterval = inactiveInterval;
	}
	
	
	@Test
	public void name(){
		System.out.println("你好。".length());
		
	}

}
