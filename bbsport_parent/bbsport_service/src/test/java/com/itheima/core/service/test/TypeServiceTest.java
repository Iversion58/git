package com.itheima.core.service.test;

import java.util.List;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.itheima.common.core.bean.product.Color;
import com.itheima.common.core.bean.product.Type;
import com.itheima.common.junt.SpringJunitTest;
import com.itheima.core.dao.product.ColorDao;
import com.itheima.core.service.product.ColorService;
import com.itheima.core.service.product.TypeService;

public class TypeServiceTest extends SpringJunitTest{

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private ColorService colorService;
	@Test
	public void selectTypeListTest(){
		List<Type> list = typeService.selectListByQuery();
		System.out.println(list);
	}
	
	@Test
	public void selectColorListTest(){
		List<Color> list = colorService.selectListByQuery();
		System.out.println(list);
	}
}
