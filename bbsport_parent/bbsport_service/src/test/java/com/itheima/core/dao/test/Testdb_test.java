package com.itheima.core.dao.test;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.core.bean.Testdb;
import com.itheima.core.dao.TestTbDao;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class Testdb_test {

			@Autowired
			private TestTbDao testTbDao;
		@Test
			public void testTbDao(){
			Testdb testdb = new Testdb();
				testdb.setName("kobe");
				testdb.setAge(36);
			testTbDao.addTestDb(testdb);
			}
	
	
}
