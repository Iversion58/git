package cn.itcast.shiro.realm;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * 
 * <p>Title: CustomRealm</p>
 * <p>Description: 自定义realm</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-18
 * @version 1.0
 */
public class CustomRealm extends AuthorizingRealm {
	
	//指定该 realm的名称是什么
	@Override
	public String getName(){
		
		return "customRealm";
	}
	
	//指定支持用户名密码认证的token
	@Override
	public boolean supports(AuthenticationToken token) {
		
		return token instanceof UsernamePasswordToken;
	}

	//认证时shiro认证器 调用此方法，获取用户身份信息
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(
			AuthenticationToken token) throws AuthenticationException {
		
		//先获取token的用户身份（账号），用户输入的账号
		String usercode = (String) token.getPrincipal();
		
		//拿着账号从数据库查询
		//....代表查询了
		//数据库中的账号是zhang，如果不等于表示没有查询账号，说明用户认证输入账号在数据库不存在
		if(!usercode.equals("zhang")){
			return null;//返回null表账号不存在
		}
		//执行到这里说明账号在了
		//校验用户输入的密码和数据库中密码是否一致，shiro框架校验密码的正确
		
		//根据账号从数据库查询到正确的密码
		String pwd = "123";//表示数据库中正确密码
		
		//向ModularRealmAuthenticator返回身份信息，由shiro自己认证
		//第一个参数：身份、第二个：密码、第三个：realmName
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				usercode, pwd, getName());

		return simpleAuthenticationInfo;
	}
	
	//授权时shiro授权器调用此方法，获取用户权限信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		//获取认证提交的身份
		String usercode = (String) principalCollection.getPrimaryPrincipal();
		
		//根据账号查询数据库获取权限标识符（规范：资源:权限：实例 ）
		List<String> permissions = new ArrayList<String>();
		
		permissions.add("user:create");
		permissions.add("user:update");
		
		//返回授权身份信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissions);
		
		return simpleAuthorizationInfo;
	}



}
