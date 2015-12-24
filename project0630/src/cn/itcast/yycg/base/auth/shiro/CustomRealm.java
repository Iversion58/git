package cn.itcast.yycg.base.auth.shiro;

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
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.base.pojo.Menu;
import cn.itcast.yycg.base.service.ServiceFacade;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysUser;

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
	
	@Autowired
	private ServiceFacade serviceFacade;
	
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
		SysUser sysUser = null;
		try {
			sysUser = serviceFacade.getUserService().findSysUserByUsercode(usercode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(sysUser == null){
			return null;//返回null表账号不存在
		}
		
		
		//根据账号从数据库查询到正确的密码
		String pwd = sysUser.getPwd();
		ActiveUser activeUser = null;
		try {
			//构建一个activeUser对象
			//构造 一个ActiveUser身份信息
			activeUser = new ActiveUser();
			activeUser.setId(sysUser.getId());//用户的id主键，不是账号
			activeUser.setUsercode(sysUser.getUsercode());
			activeUser.setUsername(sysUser.getUsername());
			activeUser.setGroupid(sysUser.getGroupid());//用户类型id，对应dictinfo字典表的id
			activeUser.setGroupname(sysUser.getDictinfoByGroupid().getInfo());
			//添加单位id
			//得到用户类型的业务代码  0:系统管理员,1：卫生局 2:卫生院 3：卫生室 4:供货商
			String groupid_code  =sysUser.getDictinfoByGroupid().getDictcode();
			if(groupid_code.equals("1") || groupid_code.equals("2")){
				//得到了监督单位id
				String userjdid = sysUser.getUserjdid();
				activeUser.setSysid(userjdid);
			}else if(groupid_code.equals("3")){
				//如果是卫生室 得医院的id
				String useryyid = sysUser.getUseryyid();
				activeUser.setSysid(useryyid);
			}else if(groupid_code.equals("4")){
				//如果是供货商 得供货商的id
				String usergysid = sysUser.getUsergysid();
				activeUser.setSysid(usergysid);
			}
			
			
			// 根据用户id查询用户授权的操作url
			List<SysPermission> sysPermissionsByUserid = serviceFacade
					.getSystemService().findSysPermissionByUserid(sysUser.getId());
		//将用户的操作url（权限）存储在session的对象中
			activeUser.setSysPermissionList(sysPermissionsByUserid);
			//根据用户id查询用户操作菜单 
			Menu menu = serviceFacade.getSystemService().findSysMenuByUserid(sysUser.getId());
			activeUser.setMenu(menu);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//向ModularRealmAuthenticator返回身份信息，由shiro自己认证
		//第一个参数：身份、第二个：密码、第三个：realmName
		SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(
				activeUser, pwd, getName());

		return simpleAuthenticationInfo;
	}
	
	//授权时shiro授权器调用此方法，获取用户权限信息
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		
		//获取认证提交的身份
		ActiveUser activeUser = (ActiveUser) principalCollection.getPrimaryPrincipal();
		//拿着账号从数据库查询
//		SysUser sysUser = null;
//		try {
//			sysUser = serviceFacade.getUserService().findSysUserByUsercode(usercode);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		//根据账号查询数据库获取权限标识符（规范：资源:权限：实例 ）
		//根据用户id主键查询操作权限标识
		String userid = activeUser.getId();
		List<SysPermission> sysPermissions = null;
		try {
			sysPermissions = serviceFacade.getSystemService().findSysPermissionByUserid(userid);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//遍历sysPermissions， 取vachr1中的权限标识
		List<String> permissionsList = new ArrayList<String>();
		for(SysPermission sysPermission:sysPermissions){
			if(sysPermission.getVchar1()!=null){
				permissionsList.add(sysPermission.getVchar1());
			}
			
			
		}
		//返回授权身份信息
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		simpleAuthorizationInfo.addStringPermissions(permissionsList);
		
		return simpleAuthorizationInfo;
	}



}
