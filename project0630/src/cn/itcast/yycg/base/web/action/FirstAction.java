package cn.itcast.yycg.base.web.action;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;

import cn.itcast.yycg.base.auth.fitler.LoginInterceptor;
import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.base.pojo.Menu;

/**
 * 
 * <p>Title: FirstAction</p>
 * <p>Description: 首页</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
public class FirstAction extends BaseAction {

	/*@Override
	public void initQueryVo() {
		
		
	}*/
	
	//首页
	public  String first()throws Exception{
		//从shiro中获取 session
		Subject subject = SecurityUtils.getSubject();
		
		ActiveUser  activeUser = (ActiveUser) subject.getPrincipal();
		
		request.setAttribute("activeUser", activeUser);
		
		return "first";
	}
	
	//欢迎
	public String welcome()throws Exception{
		//将常用功能，提示加载出来
		
		return "welcome";
	}
	
	private Menu menu;
	
	
	public Menu getMenu() {
		return menu;
	}

	//菜单 
	public String menu()throws Exception{
		//从session中取出用户身份
//		HttpSession session = request.getSession();
//		ActiveUser activeUser = (ActiveUser) session.getAttribute(LoginInterceptor.ACTIVEUSER);
		//修改为从shiro中获取用户身份
		Subject subject = SecurityUtils.getSubject();
		
		ActiveUser  activeUser = (ActiveUser) subject.getPrincipal();
		//从用户身份中获取菜单 ，将menu放在值栈，转json
		menu = activeUser.getMenu();
		
		return "menu";
	}

}
