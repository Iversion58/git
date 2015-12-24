package cn.itcast.yycg.base.auth.fitler;

import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.struts2.ServletActionContext;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.util.ResourcesUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

/**
 * 
 * <p>Title: LoginInterceptor</p>
 * <p>Description: 用户授权拦截器</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-17
 * @version 1.0
 */
public class PermissionInterceptor  extends AbstractInterceptor {
	
	private static final String REFUSE = "refuse";
	

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
		
		//判断用户请求是否公共地址
		List<String> commonUrls = ResourcesUtil.gekeyList(Config.COMMON_ACTIONS);
		for(String commonUrl:commonUrls){
			if(requestURI.indexOf(commonUrl)>=0){
				//继续访问
				return invocation.invoke();
			}
		}
		
		//获取用户授权的操作url
		//从session中获取
		HttpSession session = request.getSession();
		//身份在session中的key是activeUser
		ActiveUser activeUser = (ActiveUser) session.getAttribute(LoginInterceptor.ACTIVEUSER);
		//获取用户授权的操作url
		List<SysPermission> sysPermissionList = activeUser.getSysPermissionList();
		
		for(SysPermission sysPermission:sysPermissionList){
			String permission_url = sysPermission.getUrl();
			//如果用户请求的url在授权的操作url范围中，继续访问
			if(requestURI.indexOf(permission_url)>=0){
				//继续访问
				return invocation.invoke();
			}
		}
		
		
		//跳转登陆页面
		return REFUSE;
	}

}
