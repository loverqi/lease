package cn.loverqi.lease.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cn.loverqi.lease.cache.RedisCatch;
import cn.loverqi.lease.cache.Token;
import cn.loverqi.lease.pojo.Woker;
import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.service.WokerService;
import cn.loverqi.lease.util.SendMsgUtil;

/**
 * 项目名称：lease
 * 类名称：PermissionsLoginInterceptor 
 * 创建人：loverqi
 * 创建时间：2017-8-30
 * 类描述： 
 */
public class PermissionsLoginInterceptor implements HandlerInterceptor {

	@Autowired
	private RedisCatch redisCatch;
	@Autowired
	private WokerService wokerService;

	private List<String> lists = new ArrayList<String>();

	public PermissionsLoginInterceptor() {
		lists.add("/lease/shop/delete.action".replaceAll("/", ""));
		lists.add("/lease/shop/update.action".replaceAll("/", ""));
		lists.add("/lease/woker/userstart.action".replaceAll("/", ""));
		lists.add("/lease/woker/delete.action".replaceAll("/", ""));
		lists.add("/lease/woker/update.action".replaceAll("/", ""));
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		String requestURI = request.getRequestURI().replaceAll("/", "");
		if (lists.indexOf(requestURI) >= 0) {
			String tokenId = request.getHeader("token");
			// 如果头里没有token，则去请求参数获取
			if (tokenId == null || "".equals(tokenId.trim())) {
				tokenId = request.getParameter("token");
			}
			Token token = redisCatch.getToken(tokenId);
			String username = (String) token.getAttribute("username");

			Woker woker = wokerService.findWokerByUse(username);
			if (null == woker || woker.getState() != 0) {
				RequestDate requestDate = new RequestDate();
				requestDate.setCode(1002);
				requestDate.setMessage("权限不足");
				SendMsgUtil.sendJsonMessage(response, requestDate);
				return false;
			}

			redisCatch.setToken(token);
		}

		return true;
	}

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {

	}

}
