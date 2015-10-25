package com.itheima.core.service.test;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.common.core.bean.Testdb;
import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.junt.SpringJunitTest;
import com.itheima.core.service.TestTbService;
import com.itheima.core.service.product.BrandService;
import com.itheima.core.service.product.ProductService;

import cn.itcast.common.page.Pagination;

public class BrandServiceTest extends SpringJunitTest{

	@Autowired
	private BrandService brandService;
	
	@Autowired
	private ProductService productService;

	@Test
	public void test1(){
		
		List<Brand> redis = productService.selectBrandListFormRedis();
		System.out.println(redis);
	}
	
	@Test
	public void test(){
		Pagination pagination = brandService.selectPaginationByQuery(2,null,null);
	System.out.println(pagination.getList());
	}
}
