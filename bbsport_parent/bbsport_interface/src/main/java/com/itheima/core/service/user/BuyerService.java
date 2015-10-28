package com.itheima.core.service.user;

import com.itheima.common.core.bean.user.Buyer;

public interface BuyerService {
			/**
			 * 用于用户查询
			 * @param username
			 * @return
			 */
	public Buyer selectBuyerByUsername(String username);
	
}
