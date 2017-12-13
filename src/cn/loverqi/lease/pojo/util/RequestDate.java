package cn.loverqi.lease.pojo.util;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称：lease
 * 类名称：RequestDate 
 * 创建人：loverqi
 * 创建时间：2017-8-26
 * 类描述： 请求数据的实体类
 */
public class RequestDate implements Serializable {

	private static final long serialVersionUID = -4988435676311767997L;
	private int code = 1000;
	private String message;
	private String token;
	private Object data;
	private Map<String, Object> map = null;

	private Map<String, Object> getMap() {
		return map;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getData() {
		return data;
	}

	public void setData(Object data) {
		this.data = data;
	}

	public void setOnecData(String key, Object obj) {
		this.getMap().put(key, obj);
		this.data = this.getMap();
	}

	public RequestDate() {
		map = new HashMap<String, Object>();
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setCodeAndMessage(boolean codeStatic) {
		if (codeStatic) {
			this.setCode(0);
			this.setMessage("操作成功");
		} else {
			this.setCode(1003);
			this.setMessage("参数异常");
		}
	}
}
