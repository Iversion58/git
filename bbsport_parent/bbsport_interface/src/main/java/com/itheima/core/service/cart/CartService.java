package com.itheima.core.service.cart;

import com.itheima.common.core.bean.cart.BuyerCart;
import com.itheima.common.core.bean.product.Sku;

public interface CartService {
	/**
	 * 查询sku对象
	 * @param id
	 * @return
	 */
	public Sku selectSkuById(Long id);
	/**
	 * 放购物车中数据到Redis服务器
	 * @param buyerCart
	 * @param username
	 */
	public void insertBuyerCartToRedis(BuyerCart buyerCart, String username);

	/**
	 * 取出所有商品从redis
	 * @param username
	 * @return
	 */
	public BuyerCart selectBuyerCartFromRedis(String username);
	public Integer selectSkuStockById(Long id);
}
