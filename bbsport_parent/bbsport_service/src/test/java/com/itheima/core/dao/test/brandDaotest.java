package com.itheima.core.dao.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.common.core.bean.Testdb;
import com.itheima.common.core.bean.product.Brand;
import com.itheima.core.dao.TestTbDao;
import com.itheima.core.dao.product.BrandDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class brandDaotest {

			@Autowired
			private BrandDao brandDao;
		@Test
			public void selestByListTest(){
			List<Brand> list = brandDao.selectListByQuery(null);
			System.out.println(list);
		}
	
	
}
