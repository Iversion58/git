package com.itheima.core.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.common.core.bean.Testdb;
import com.itheima.core.service.TestTbService;
import com.itheima.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:application-context.xml"})
public class BrandServiceTest {

	@Autowired
	private BrandService brandService;
	
	@Test
	public void test(){
		Pagination pagination = brandService.selectPaginationByQuery(2,null,null);
	System.out.println(pagination.getList());
	}
}
