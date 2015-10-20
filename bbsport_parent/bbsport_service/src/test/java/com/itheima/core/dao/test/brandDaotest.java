package com.itheima.core.dao.test;


import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.itheima.common.core.bean.Testdb;
import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.junt.SpringJunitTest;
import com.itheima.core.dao.TestTbDao;
import com.itheima.core.dao.product.BrandDao;

public class brandDaotest extends SpringJunitTest{

			@Autowired
			private BrandDao brandDao;
		@Test
			public void selestByListTest(){
			List<Brand> list = brandDao.selectListByQuery(null);
			System.out.println(list);
		}
	
		@Test
		public void deleteByIdsTest(){
			Integer[] ids={24,25,26};
				brandDao.deleteByIds(ids);
		}
		@Test
		public void selectByIdTest(){
				Integer id=2;
			Brand brand	=brandDao.selectBrandById(id);
			brand.setDescription("啊啊啊啊啊");
			brandDao.updateBrand(brand);
			brand	=brandDao.selectBrandById(id);
			System.out.println(brand);
		}

}
