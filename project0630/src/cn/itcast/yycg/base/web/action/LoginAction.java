package cn.itcast.yycg.base.web.action;

import javax.servlet.http.HttpSession;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.auth.fitler.LoginInterceptor;
import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.pojo.ActiveUser;
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
public class LoginAction extends BaseAction {

	// usercode账号
	private String usercode;

	// 密码
	private String password;
	
	//验证码
	private String validateCode;
	
	

	public String getUsercode() {
		return usercode;
	}



	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}



	public String getPassword() {
		return password;
	}



	public void setPassword(String password) {
		this.password = password;
	}



	public String getValidateCode() {
		return validateCode;
	}



	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}



	// 登陆
	public String login() throws Exception {
		HttpSession session = request.getSession();
		
		//校验验证码
		//从session取出验证正确值
		String validateCode_session = (String) session.getAttribute("validateCode");
		if(validateCode == null){
			//抛出异常
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 112, null));
			
		}
        //和页面输入的验证码进行匹配
		if(validateCode != null && validateCode_session != null && !validateCode.equals(validateCode_session)){
			//抛出异常
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 113, null));
		}

		// 调用service进行认证
		ActiveUser activeUser = serviceFacade.getUserService().checkSysUser(
				usercode, password);
		// 认证通过，将身份信息存在session
		
		session.setAttribute(LoginInterceptor.ACTIVEUSER, activeUser);
		// 向页面返回成功提示
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil
				.createSuccess(Config.MESSAGE, 107,
						new Object[] { activeUser.getUsername() })));
		return "login";
	}

	// 退出
	public String logout()throws Exception{
		//session失效，建议此方法
		request.getSession().invalidate();
		return "logout";
	}

}
