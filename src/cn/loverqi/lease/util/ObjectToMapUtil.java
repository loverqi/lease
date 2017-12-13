package cn.loverqi.lease.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称：lease
 * 类名称：RequestParamsToMapUtil 
 * 创建人：loverqi
 * 创建时间：2017-9-9
 * 类描述： 将对象转换为map的工具类
 */
public class ObjectToMapUtil {
	
	private ObjectToMapUtil(){
		throw new AssertionError();
	}

	/**
	 * 将对象转换成map的方法
	 */
	public static Map<String, Object> getFieldVlaue(Object obj) {
		Map<String, Object> mapValue = new HashMap<String, Object>();
		try {
			Class<?> clazz = obj.getClass();
			Field[] fields = clazz.getDeclaredFields();

			for (Field field : fields) {
				// 假设不为空。设置可见性，然后返回
				field.setAccessible(true);
				String name = field.getName();
				if ("serialVersionUID".equals(name)) {
					continue;
				}
				String strGet = "get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length());
				Method methodGet = clazz.getDeclaredMethod(strGet);
				Object object = methodGet.invoke(obj);
				mapValue.put(name, object);
			}
		} catch (Exception e) {
			return mapValue;
		}

		return mapValue;
	}

	/**
	 * 将对象转换成map的方法
	 */
	public static List<Map<String, Object>> getFieldVlaues(List<Object> objs) {
		List<Map<String, Object>> lists = new ArrayList<Map<String, Object>>();
		for (Object object : objs) {
			lists.add(getFieldVlaue(object));
		}

		return lists;
	}
}