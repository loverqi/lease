package cn.loverqi.lease.util;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/**
 * 项目名称：lease
 * 类名称：RequestParamsToMapUtil 
 * 创建人：loverqi
 * 创建时间：2017-8-29
 * 类描述： 将request参数封装为对象的方法
 */
public class RequestParamsToMapUtil {
	
	private RequestParamsToMapUtil(){
		throw new AssertionError();
	}

	/**
	 * 将请求查收格式化
	 */
	public static Map<String, String> getMapsToMap(Map<String, String[]> properties) {
		Map<String, String> returnMap = new HashMap<String, String>();
		String name = "";
		String value = "";
		for (Map.Entry<String, String[]> entry : properties.entrySet()) {
			name = entry.getKey();
			String[] values = entry.getValue();
			if (null == values) {
				value = "";
			} else if (values.length > 1) {
				for (int i = 0; i < values.length; i++) { // 用于请求参数中有多个相同名称
					value = values[i] + ",";
				}
				value = value.substring(0, value.length() - 1);
			} else {
				value = values[0];// 用于请求参数中请求参数名唯一
			}
			returnMap.put(name, value);

		}
		return returnMap;
	}

	/**
	 * 将map装入对象
	 */
	public static <T> T getMapObj(Map<String, Object> objMap, Class<T> clazz) {

		Field[] fields = clazz.getDeclaredFields();
		Map<String, Object> objMapHs = new HashMap<String, Object>();

		// 循环遍历字段，获取字段相应的属性值
		for (Field field : fields) {
			// 假设不为空。设置可见性，然后返回
			field.setAccessible(true);
			String name = field.getName();
			Object value = objMap.get(name);
			if (value != null) {
				if ("password".equals(name)) {
					value = MD5Util.getMD5Str(value.toString());
				}
				objMapHs.put(name, value);
			}
		}

		T obj = (T) BeanUtils.instantiateClass(clazz);

		BeanWrapper beanWrapper = new BeanWrapperImpl(obj);
		beanWrapper.setPropertyValues(objMapHs);

		return obj;
	}
}