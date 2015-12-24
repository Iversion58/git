package cn.itcast.yycg.base.auth.fitler;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.util.ResourcesUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * <p>Title: LoginInterceptor</p>
 * <p>Description: 用户身份校验拦截器</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-17
 * @version 1.0
 */
public class LoginInterceptor  extends AbstractInterceptor {
	
	//定义常量 
	public static final String ACTIVEUSER = "activeUser";

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		//得到用户请求的url
		String requestURI = request.getRequestURI();
		
		//校验用户请求是否是公开 地址
		//从配置文件中获取公开地址
		List<String> openUrls = ResourcesUtil.gekeyList(Config.ANONYMOUS_ACTIONS);
		//如果请求地址是公开地址继续访问
		for(String openUrl:openUrls){
			if(requestURI.indexOf(openUrl)>=0){
				//继续访问
				return invocation.invoke();
			}
		}
		
		//如果访问的不是公开 地址，校验身份信息
		//web应用身份信息存在session中
		HttpSession session = request.getSession();
		//身份在session中的key是activeUser
		ActiveUser activeUser = (ActiveUser) session.getAttribute(ACTIVEUSER);
		if(activeUser!=null){
			//如果session有身份信息继续访问
			return invocation.invoke();
		}
		
		//跳转登陆页面
		return "loginpage";
	}

}
