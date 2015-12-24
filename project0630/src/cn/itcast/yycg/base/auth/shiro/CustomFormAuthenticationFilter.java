package cn.itcast.yycg.base.auth.shiro;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * <p>Title: CustomFormAuthenticationFilter</p>
 * <p>Description:自定义基于表单认证过虑器，拦截提交用户名和密码生成token </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-18
 * @version 1.0
 */
public class CustomFormAuthenticationFilter extends FormAuthenticationFilter  {

	  private static final Logger log = LoggerFactory.getLogger(FormAuthenticationFilter.class);

	  //认证成功走此方法
	protected boolean onLoginSuccess(AuthenticationToken token,
			Subject subject, ServletRequest request, ServletResponse response)
			throws Exception {
		 HttpServletRequest httpServletRequest = (HttpServletRequest) request; 
		//issueSuccessRedirect(request, response);
		//处理如果是ajax请求将成功信息返回json，否则 
		// 非ajax请求
		if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
				.getHeader("X-Requested-With"))) {
			//重定向到applicationContext-shiro.xml中指定successUrl
			return super.onLoginSuccess(token, subject, request, response);
		} else {
			//ajax请求
			response.setCharacterEncoding("utf-8");
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write("{\"resultInfo\":{\"type\":\"1\",\"messageCode\":\"906\",\"message\":\"登陆成功\"}}");

			//false不进行连接重定向
			return false;
		}

//		return false;
	}
	//所有认证走此方法
	 protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
		 HttpServletRequest httpServletRequest = (HttpServletRequest) request;   
		 if (isLoginRequest(request, response)) {
	            if (isLoginSubmission(request, response)) {//如果登陆提交
	                if (log.isTraceEnabled()) {
	                    log.trace("Login submission detected.  Attempting to execute login.");
	                }
	                // 从session获取正确验证码
	        		System.out.println(httpServletRequest.getRequestURI());
	        		HttpSession session = httpServletRequest.getSession();
	        		// 取出session的验证码（正确的验证码）
	        		String validateCode = (String) session.getAttribute("validateCode");

	        		// 取出页面的验证码
	        		// 输入的验证和session中的验证进行对比
	        		String randomcode = httpServletRequest.getParameter("validateCode");
	        		if (randomcode != null && validateCode != null
	        				&& !randomcode.equals(validateCode)) {
	        			// 如果校验失败，将验证码错误失败信息，通过shiroLoginFailure设置到request中
	        			//注意：统一使用shiroLoginFailure作为在request中存储的key
	        			httpServletRequest.setAttribute("shiroLoginFailure",
	        					"randomCodeError");
	        			//重定向到登陆页面，重写向applicationContext-shiro.xml指定的loginUrl
	        			return true;
	        		}
	                return executeLogin(request, response);
	            } else {
	                if (log.isTraceEnabled()) {
	                    log.trace("Login page view.");
	                }
	               
	                if (!"XMLHttpRequest".equalsIgnoreCase(httpServletRequest
	        				.getHeader("X-Requested-With"))) {
	                	//重定向到登陆页面，重写向applicationContext-shiro.xml指定的loginUrl
	        			return true;
	        		} else {
	        			response.setCharacterEncoding("utf-8");
	        			response.setContentType("application/json;charset=utf-8");
	        			response.getWriter().write("{\"resultInfo\":{\"type\":\"1\",\"messageCode\":\"106\",\"message\":\"您的操作需要登陆后继续\"}}");
	        			return false;
	        		}

//	                return true;
	            }
	        } else {
	            if (log.isTraceEnabled()) {
	                log.trace("Attempting to access a path which requires authentication.  Forwarding to the " +
	                        "Authentication url [" + getLoginUrl() + "]");
	            }

	            saveRequestAndRedirectToLogin(request, response);
	            return false;
	        }
	    }
}
