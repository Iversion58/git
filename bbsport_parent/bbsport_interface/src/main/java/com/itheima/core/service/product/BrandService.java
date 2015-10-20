package com.itheima.core.service.product;

import java.util.List;

import com.itheima.common.core.bean.product.Brand;

import cn.itcast.common.page.Pagination;

public interface BrandService {
	
	public List<Brand> selectListByQuery(String name,Integer isDisplay);
				
	public Pagination selectPaginationByQuery(Integer pageNo,String name,Integer isDisplay);
	
	public void insertBrand(Brand brand);

	public void deleteByIds(Integer[] ids);

	public Brand selectBrandById(Integer id);

	public void updateBrand(Brand brand);
}
