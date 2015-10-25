package com.itheima.core.service.product;

import java.util.List;

import com.itheima.common.core.bean.product.Brand;
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

	public void updateIsShow(Long[] ids);

	public Pagination selectPaginationbyQueryFromSolr(Integer pageNo, String keyword,Long brandId,String price);

	public List<Brand> selectBrandListFormRedis();

	public String selectBrandNameById(Long brandId);

	public Product selectProductById(Long id);

}
