package cn.itcast.yycg.business.system.dao.impl;

import javax.annotation.Resource;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.business.system.entity.SysUser;


//使用junit和spring整合单元测试注解完成
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:spring/applicationContext.xml"})
public class SysUserDaoImplTest {
	
	//由于上边使用注解加载了spring容器，这里直接可以使用注解将spring容器中的bean实例注入到此类中
	//@Autowired //基于类型注入，注意注入在类型在spring容器中只有一个
	//private SysUserDao sysUserDao;
	@Resource(name="sysUserDao")
	private BaseDao<SysUser> sysUserDao;


	//此方法在每一个测试方法执行之间进行，因为有@Before标识 
	@Before
	public void setUp() throws Exception {
		//如果使用此方法，要加载环境配置，比如spring环境容器获取
	}

	@Test
	public void testFindSysUserById() throws Exception {
		//调用 sysUserDao的方法
//		SysUser sysUser = sysUserDao.findSysUserById("202");
		
		SysUser sysUser = sysUserDao.findById("202");
		
		System.out.println(sysUser);
	}

}
