package com.itheima.core.service.product;

import java.util.List;

import com.itheima.common.core.bean.product.Sku;

public interface SkuService {

	List<Sku> selectListByQuery(Long productId);

	public void upateSku(Sku sku);

}
