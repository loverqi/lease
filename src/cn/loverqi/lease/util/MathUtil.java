package cn.loverqi.lease.util;

/**
 * 项目名称：lease
 * 类名称：MathUtil 
 * 创建人：loverqi
 * 创建时间：2017-9-25
 * 类描述： 
 */
public class MathUtil {
	
	private MathUtil(){
		throw new AssertionError();
	}

	/**
	 * 采用进一法的方式进行数据计算
	 */
	public static int ceil(double numLarge, double numSmall) {
		return (int) Math.ceil(numLarge / numSmall);
	}

}
