package cn.loverqi.lease.exception;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import cn.loverqi.lease.pojo.util.RequestDate;
import cn.loverqi.lease.util.SendMsgUtil;

/**
 * 项目名称：lease
 * 类名称：CustomExceptionResolver 
 * 创建人：loverqi
 * 创建时间：2017-9-6
 * 类描述： 
 */
public class ExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception e) {

		ModelAndView mav = new ModelAndView();
		
		RequestDate requestDate = new RequestDate();
		requestDate .setCode(1000);
		requestDate.setMessage("请正确输入各项参数");
		SendMsgUtil.sendJsonMessage(response, requestDate);
		
		return mav;
	}

}
