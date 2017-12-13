package cn.loverqi.lease.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 项目名称：lease
 * 类名称：DateFormat 
 * 创建人：loverqi
 * 创建时间：2017-9-12
 * 类描述： 日期类型转类
 */
public class DateFormatUtil {

	private static String datePattern = "yyyy-MM-dd HH:mm:ss";
	
	private DateFormatUtil(){
		throw new AssertionError();
	}

	public static String getDatePattern() {
		return datePattern;
	}

	public static void setDatePattern(String datePattern) {
		DateFormatUtil.datePattern = datePattern;
	}

	/**
	 * 日期类型转换
	 */
	public static Date convert(String source, String datePattern) {
		try {
			if (null != source) {
				DateFormat df = new SimpleDateFormat(datePattern);
				return df.parse(source.replaceAll("\"", ""));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

	/**
	 * 日期类型转换
	 */
	public static Date convert(String source) {
		try {
			if (null != source) {
				SimpleDateFormat df = new SimpleDateFormat(datePattern);
				return df.parse(source.replaceAll("\"", ""));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}

}
