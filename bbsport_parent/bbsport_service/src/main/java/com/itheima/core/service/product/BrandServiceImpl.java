package com.itheima.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.core.bean.product.BrandQuery;
import com.itheima.core.dao.product.BrandDao;

import cn.itcast.common.page.Pagination;

@Service("brandService")
@Transactional
public class BrandServiceImpl implements BrandService {

	@Autowired
	private BrandDao brandDao;

	@Override
	public List<Brand> selectListByQuery(String name, Integer isDisplay) {
		BrandQuery brandQuery = new BrandQuery();
		if (name != null) {
			brandQuery.setName(name);
		}
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
		}
		List<Brand> list = brandDao.selectListByQuery(brandQuery);

		return list;
	}

	public Pagination selectPaginationByQuery(Integer pageNo,String name,Integer isDisplay) {
				//拼接页面uri
		StringBuilder params = new StringBuilder();

		BrandQuery brandQuery = new BrandQuery();
		if (name != null) {
					//回显数据
			brandQuery.setName(name);
				//页码的uri
			params.append("name=").append(name);
		}
		if (isDisplay != null) {
			brandQuery.setIsDisplay(isDisplay);
			params.append("&isDisplay=").append(isDisplay);
		}else{
			params.append("&isDisplay=").append(1);
		}
				//设置当前页
			brandQuery.setPageNo(Pagination.cpn(pageNo));
				//每页数
			brandQuery.setPageSize(3);
		// 实例化分页对象
		Pagination pagination = new Pagination(brandQuery.getPageNo(), brandQuery.getPageSize(),
				brandDao.selectCount(brandQuery));

		pagination.setList(brandDao.selectListByQuery(brandQuery));
		String url="/brand/list.do";
		pagination.pageView(url, params.toString());
		return pagination;
	}

	@Override
	public void insertBrand(Brand brand) {
			brandDao.insertBrand(brand);
	}

}
