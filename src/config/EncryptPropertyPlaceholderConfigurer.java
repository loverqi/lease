package config;

import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import cn.loverqi.lease.util.DESUtil;

/**
 * 项目名称：lease
 * 类名称：EncryptPropertyPlaceholderConfigurer 
 * 创建人：loverqi
 * 创建时间：2017-8-27
 * 类描述： 配置文件加密储存
 */
public class EncryptPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private String[] encryptPropNames = { "jdbc.username", "jdbc.password", "redis.password" };

	@Override
	protected String convertProperty(String propertyName, String propertyValue) {

		// 如果在加密属性名单中发现该属性
		if (isEncryptProp(propertyName)) {
			String decryptValue = DESUtil.getDecryptString(propertyValue);
			return decryptValue;
		} else {
			return propertyValue;
		}

	}

	private boolean isEncryptProp(String propertyName) {
		for (String encryptName : encryptPropNames) {
			if (encryptName.equals(propertyName)) {
				return true;
			}
		}
		return false;
	}
}