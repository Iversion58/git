package com.itheima.core.service.product;

import com.itheima.common.core.bean.product.Product;

import cn.itcast.common.page.Pagination;

public interface ProductService {

	/**
	 * 分页查询
	 * @param name
	 * @param isShow
	 * @param brandId
	 * @param pageNo
	 * @return
	 */
	
	public Pagination selectPaginationByQuery(String name, Boolean isShow, Long brandId, Integer pageNo);

	public void insertProduct(Product product);

}
