package cn.itcast.yycg.base.dao.impl;

import static org.junit.Assert.*;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.business.system.entity.Basicinfo;
import cn.itcast.yycg.business.system.entity.SysUser;

//加载spring容器
//使用junit和spring整合单元测试注解完成
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath:spring/applicationContext.xml",
		"classpath:spring/applicationContext-*.xml" })
public class BaseDaoImplTest {
	
	//注入baseDao
	@Resource(name="sysUserDao")
	private BaseDao<SysUser> sysUserDao;

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testFindById() throws Exception {
		SysUser sysUser = sysUserDao.findById("202");
		System.out.println(sysUser);
	}
	
	//测试QBC查询
	@Test
	public void testFindListByCriteria()throws Exception{
		
		//构造 一个DetachedCriteria
		//构造DetachedCriteria传入entity的class
//		DetachedCriteria.forClass(clazz)
		//将上边的方法封装到BaseDao中，因为BaseDao中有entity的class
		DetachedCriteria detachedCriteria = sysUserDao.createCriteria();
		//拼接查询条件，usercode等于某个值的条件
		detachedCriteria.add(Restrictions.eq("usercode", "jcpzc"));
		
		//上边的拼接detachedCriteria的代码将来会放在service
		//查询sysUser列表
		List<SysUser> list = sysUserDao.findListByCriteria(detachedCriteria);
		System.out.println(list);
	}

}
