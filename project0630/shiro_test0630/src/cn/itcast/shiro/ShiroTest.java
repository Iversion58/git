package cn.itcast.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * 
 * <p>
 * Title: ShiroTest
 * </p>
 * <p>
 * Description: shiro的测试类
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-9-18
 * @version 1.0
 */
public class ShiroTest {

	// 测试认证
	@Test
	public void testAuthenticate() throws Exception {

		// 构造securityManager环境
		// 通过ini文件构造环境
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
				"classpath:shiro/shiro-token.ini");
		// 通过工厂构造 一个securityManager
		SecurityManager securityManager = factory.getInstance();
		// 将factory设置到shiro环境中，一个系统只有一个
		SecurityUtils.setSecurityManager(securityManager);
		// shiro环境中取出subject，当前执行认证主体
		Subject subject = SecurityUtils.getSubject();

		// 认证要调用subject的认证方法
		// 构造 一个token（包括账号和密码）
		// token的账号和密码用户输入的，可能是错误 的
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
				"zhang", "123dd");

		try {
			subject.login(usernamePasswordToken);
		} catch (Exception e) {
			// 如果认证失败，抛出异常
			e.printStackTrace();
		}

		// 取出认证状态
		// 是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

		// 退出
		subject.logout();

		// 退出 之后，认证状态为不通过
		authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

	}

	// 测试认证使用自定义realm
	@Test
	public void testAuthenticateByrealm() throws Exception {

		// 构造securityManager环境
		// 通过ini文件构造环境
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
				"classpath:shiro/shiro-realm.ini");
		// 通过工厂构造 一个securityManager
		SecurityManager securityManager = factory.getInstance();
		// 将factory设置到shiro环境中，一个系统只有一个
		SecurityUtils.setSecurityManager(securityManager);
		// shiro环境中取出subject，当前执行认证主体
		Subject subject = SecurityUtils.getSubject();

		// 认证要调用subject的认证方法
		// 构造 一个token（包括账号和密码）
		// token的账号和密码用户输入的，可能是错误 的
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
				"zhangd", "123dd");

		try {
			subject.login(usernamePasswordToken);
		} catch (Exception e) {
			// 如果认证失败，抛出异常
			e.printStackTrace();
		}

		// 取出认证状态
		// 是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

		// 退出
		subject.logout();

		// 退出 之后，认证状态为不通过
		authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

	}

	// 测试授权
	@Test
	public void testAuthorize() throws Exception {

		// 构造securityManager环境
		// 通过ini文件构造环境
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
				"classpath:shiro/shiro-permission.ini");
		// 通过工厂构造 一个securityManager
		SecurityManager securityManager = factory.getInstance();
		// 将factory设置到shiro环境中，一个系统只有一个
		SecurityUtils.setSecurityManager(securityManager);
		// shiro环境中取出subject，当前执行认证主体
		Subject subject = SecurityUtils.getSubject();

		// 认证要调用subject的认证方法
		// 构造 一个token（包括账号和密码）
		// token的账号和密码用户输入的，可能是错误 的
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
				"zhang", "123");

		try {
			// 认证
			subject.login(usernamePasswordToken);

			// 认证通过进行授权方法

			// 基于角色授权
			boolean hasRole = subject.hasRole("role3");
			System.out.println("是否有role1角色" + hasRole);

			// 基于资源 的授权
			// 根据权限标识判断，用户是否有这个权限
			boolean permitted = subject.isPermitted("user:update");
			System.out.println("是否有user:create权限：" + permitted);

		} catch (Exception e) {
			// 如果认证失败，抛出异常
			e.printStackTrace();
		}

		// 取出认证状态
		// 是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

		// 退出
		subject.logout();

		// 退出 之后，认证状态为不通过
		authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

	}

	// 测试授权
	@Test
	public void testAuthorizeByRealm() throws Exception {

		// 构造securityManager环境
		// 通过ini文件构造环境
		IniSecurityManagerFactory factory = new IniSecurityManagerFactory(
				"classpath:shiro/shiro-realm.ini");
		// 通过工厂构造 一个securityManager
		SecurityManager securityManager = factory.getInstance();
		// 将factory设置到shiro环境中，一个系统只有一个
		SecurityUtils.setSecurityManager(securityManager);
		// shiro环境中取出subject，当前执行认证主体
		Subject subject = SecurityUtils.getSubject();

		// 认证要调用subject的认证方法
		// 构造 一个token（包括账号和密码）
		// token的账号和密码用户输入的，可能是错误 的
		UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(
				"zhang", "123");

		try {
			// 认证
			subject.login(usernamePasswordToken);

			// 认证通过进行授权方法

			// 基于角色授权
			boolean hasRole = subject.hasRole("role3");
			System.out.println("是否有role1角色" + hasRole);

			// 基于资源 的授权
			// 根据权限标识判断，用户是否有这个权限
			boolean permitted = subject.isPermitted("user:update");
			System.out.println("是否有user:create权限：" + permitted);

		} catch (Exception e) {
			// 如果认证失败，抛出异常
			e.printStackTrace();
		}

		// 取出认证状态
		// 是否认证通过
		boolean authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

		// 退出
		subject.logout();

		// 退出 之后，认证状态为不通过
		authenticated = subject.isAuthenticated();
		System.out.println("认证结果：" + authenticated);

	}
}
