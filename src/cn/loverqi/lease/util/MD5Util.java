package cn.loverqi.lease.util;

import java.security.MessageDigest;

/**
 * 项目名称：lease 类名称：MD5Util 创建人：loverqi 创建时间：2017-8-29 类描述：
 */
public class MD5Util {
	
	private MD5Util(){
		throw new AssertionError();
	}

	public static String getMD5Str(String str) {

		if (str == null || "" == str.trim()) {
			return null;
		}

		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
			messageDigest.reset();
			messageDigest.update(str.trim().getBytes("UTF-8"));
		} catch (Exception e) {
		}

		byte[] byteArray = messageDigest.digest();

		StringBuffer md5StrBuff = new StringBuffer();

		for (int i = 0; i < byteArray.length; i++) {
			if (Integer.toHexString(0xFF & byteArray[i]).length() == 1) {
				md5StrBuff.append("0").append(Integer.toHexString(0xFF & byteArray[i]));
			} else {
				md5StrBuff.append(Integer.toHexString(0xFF & byteArray[i]));
			}
		}

		return md5StrBuff.toString();
	}
}