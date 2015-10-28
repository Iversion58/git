package com.itheima.core.web.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.itheima.common.utils.RequestUtils;
import com.itheima.core.service.user.SessionProvider;
/**
 * 自定义拦截器      前端控制器  处理器映射器  处理器适配器   处理器 Controller（handler)
 * @author Iverson
 *
 */
public class SpringmvcInterceptor implements HandlerInterceptor {

	@Autowired
	private SessionProvider sessionProvider;
				/**
				 * handler之前
				 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String username = sessionProvider.getAttributeForUsername(RequestUtils.getCSESSIONID(request, response));
		if(username != null){
			request.setAttribute("isLogin", true);
		} 
		request.setAttribute("isLogin", false);
		
		return true;
	}
			//handler之后
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}
			//页面渲染之后
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
