package com.itheima.core.dao.product;

import java.util.List;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.core.bean.product.BrandQuery;

import cn.itcast.common.page.Pagination;

public interface BrandDao {
			//查询
		public List<Brand> selectListByQuery(BrandQuery brandQuery);
		//查询总条件（符合条件)
		public Integer selectCount(BrandQuery brandQuery);
		
		//保存
		public void insertBrand(Brand brand);
		public void deleteByIds(Integer[] ids);
		public Brand selectBrandById(Integer id);
		public void updateBrand(Brand brand);
		
}
