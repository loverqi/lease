package cn.loverqi.lease.conversion;

import org.springframework.core.convert.converter.Converter;

/**
 * 项目名称：lease 
 * 类名称：DateConveter 
 * 创建人：loverqi 
 * 创建时间：2017-8-27 
 * 类描述： 转换日期类型的数据 S :
 * 页面传递过来的类型 T ： 转换后的类型
 */
public class IntegerConveter implements Converter<String, Integer> {

	private String datePattern = null;

	public String getDatePattern() {
		return datePattern;
	}

	public void setDatePattern(String datePattern) {
		this.datePattern = datePattern;
	}

	@Override
	public Integer convert(String source) {
		try {
			if (null != source) {
				return "null".equals(source) ? null : Integer.parseInt(source);
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return null;
	}
}
