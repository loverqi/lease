package cn.loverqi.lease.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.loverqi.lease.cache.RedisCatch;
import cn.loverqi.lease.cache.Token;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.util.SendMsgUtil;

/**
 * 项目名称：lease 类名称：LoginInterceptor 创建人：loverqi 创建时间：2017-8-30 类描述：
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisCatch redisCatch;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestURI = request.getRequestURI();
		if (!requestURI.contains("login")) {
			String tokenId = request.getHeader("token");
			// 如果头里没有token，则去请求参数获取
			if (tokenId == null || "".equals(tokenId.trim())) {
				tokenId = request.getParameter("token");
			}
			RequestDate requestDate = new RequestDate();
			if (null == tokenId) {
				requestDate.setCode(1003);
				requestDate.setMessage("参数异常 ");
				SendMsgUtil.sendJsonMessage(response, requestDate);
				return false;
			} else if (!redisCatch.existsToken(tokenId)) {
				requestDate.setCode(1001);
				requestDate.setMessage("用户未登陆");
				SendMsgUtil.sendJsonMessage(response, requestDate);
				return false;
			} else {
				// 更新用户登陆时间
				Token token = redisCatch.getToken(tokenId);
				redisCatch.setToken(token);
			}
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse response, Object arg2, ModelAndView arg3)
			throws Exception {
	}

}
