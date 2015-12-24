package cn.itcast.yycg.base.web.action;

import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.web.result.ResultUtil;

/**
 * 
 * <p>
 * Title: LoginAction
 * </p>
 * <p>
 * Description: 认证的action
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-9-17
 * @version 1.0
 */
@Controller
@Scope("prototype")
public class ShrioLoginAction extends BaseAction {

	// 登陆
	public String login() throws Exception {
		// shiro在认证过程中出现错误后将异常类路径通过request返回
		String exceptionClassName = (String) request
				.getAttribute("shiroLoginFailure");
		
		if (exceptionClassName != null) {
			if (UnknownAccountException.class.getName().equals(
					exceptionClassName)) {
				// 账号不存在
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						101, null));
			} else if (IncorrectCredentialsException.class.getName().equals(
					exceptionClassName)) {
				// 用户名或密码 错误
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						114, null));
			} else if ("randomCodeError".equals(exceptionClassName)) {
				// 验证码错误
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						113, null));
			} else {
				// 最终在异常处理器生成未知错误
				ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE,
						900, null));
			}
		}
		//跳转登陆页面
		return "loginshow";

	}

}
