package cn.itcast.yycg.business.system.service.impl;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.yycg.business.system.entity.SysUser;
import cn.itcast.yycg.business.system.service.UserService;

//加载spring容器
//使用junit和spring整合单元测试注解完成
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/applicationContext-*.xml" })
public class UserServiceImplTest {
	
	@Autowired
	private UserService userService;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindSysUserById() throws Exception {
		SysUser sysUser = userService.findSysUserById("202");
		System.out.println(sysUser);
	}
	
	//测试事务控制
	@Test
	public void testSaveSysUser() throws Exception {
		userService.saveSysUser();
	}

}
