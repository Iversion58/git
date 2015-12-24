package cn.itcast.yycg.base.Exception;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.UnauthorizedException;
import org.apache.struts2.ServletActionContext;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.web.action.BaseAction;
import cn.itcast.yycg.base.web.result.ExceptionResultInfo;
import cn.itcast.yycg.base.web.result.ResultUtil;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class ExceptionInterceptor extends AbstractInterceptor {

	/** serialVersionUID*/
	private static final long serialVersionUID = -2847684096200503822L;

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		
		HttpServletRequest request = ServletActionContext.getRequest();
		BaseAction baseAction = (BaseAction) actionInvocation.getAction();
		//action返回result
		String result = null;
		try {
			
			result = actionInvocation.invoke();
		} catch (Exception e) {
			//输出原理信息
			e.printStackTrace();
			
			//解析异常
			ExceptionResultInfo exceptionResultInfo = null;
			//如果是系统自定义的异常
			if(e instanceof ExceptionResultInfo){
				exceptionResultInfo = (ExceptionResultInfo) e;
			}else if(e instanceof UnauthorizedException){
				exceptionResultInfo = new ExceptionResultInfo(ResultUtil.createFail(Config.MESSAGE, 105, null));
			}else{
				//如果不是系统自定义异常，重新构造 一个未知识错误异常
				exceptionResultInfo = new ExceptionResultInfo(ResultUtil.createFail(Config.MESSAGE, 900, null));
				
			}
			//如果请求是ajax请求，将exceptionResultInfo转json
			//思路：所有ajax请求头信息有：X-Requested-With:	XMLHttpRequest
			if("XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))){
				//将exceptionResultInfo转json输出
				//exceptionResultInfo放到值栈
				baseAction.setProcessResult(exceptionResultInfo);
				result = "error_json";
			}else{
				baseAction.setProcessResult(exceptionResultInfo);
				//如果不是ajax请求，返回到error.jsp页面
				result = "error_jsp";
			}
			
		}
		//如果上边没 有异常，返回action正常处理结果
		return result;
	}

}
