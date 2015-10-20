package com.itheima.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.product.Product;
import com.itheima.common.core.bean.product.ProductQuery;
import com.itheima.common.core.bean.product.ProductQuery.Criteria;
import com.itheima.core.dao.product.ProductDao;

import cn.itcast.common.page.Pagination;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService{

		@Autowired
		private ProductDao productDao;
	
	@Override
	public Pagination selectPaginationByQuery(String name, Boolean isShow, Long brandId, Integer pageNo) {
	
		ProductQuery productQuery=new ProductQuery();
		StringBuilder params=new StringBuilder();
		Criteria criteria = productQuery.createCriteria();
			if(null != name){
				criteria.andNameLike("%"+name+"%" );
				params.append("name=").append(name);
			}
			if(null != isShow){
				criteria.andIsShowEqualTo(isShow);
				params.append("&isShow=").append(isShow);
			}else{
				criteria.andIsShowEqualTo(false);
			}
		if(null != brandId){
			criteria.andBrandIdEqualTo(brandId);
			params.append("&brandId").append(brandId);
		}
		Pagination pagination=new Pagination(
				productQuery.getPageNo(),
				productQuery.getPageSize(),
				productDao.countByExample(productQuery)
				);
		
		pagination.setList(productDao.selectByExample(productQuery));
		
		String url="/product/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}

}
