package cn.loverqi.lease.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * 项目名称：lease
 * 类名称：SendMsgUtil 
 * 创建人：loverqi
 * 创建时间：2017-8-29
 * 类描述： 用于拦截器返回数据的工具方法
 */
public class SendMsgUtil {
	
	private SendMsgUtil(){
		throw new AssertionError();
	}

	public static void sendJsonMessage(HttpServletResponse response, Object responseObject) {

		JSONObject jsonObject = new JSONObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(jsonObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}
}
