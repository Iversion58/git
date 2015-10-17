package com.itheima.core.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.core.bean.Testdb;
import com.itheima.core.service.TestTbService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class Testdb_test {

	@Autowired
	private TestTbService testTbService;
	
	@Test
	public void test(){
		List<Testdb> list = testTbService.selectTestDbList();
		System.out.println(list);
	}
}
