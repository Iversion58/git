package com.itheima.core.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.common.core.bean.user.Buyer;
import com.itheima.core.dao.user.BuyerDao;

@Service("buyerService")
public class BuyerServiceImpl implements BuyerService {

	
	@Autowired
	private BuyerDao buyerDao;
	@Override
	public Buyer selectBuyerByUsername(String username) {
		return buyerDao.selectByPrimaryKey(username);
	}

}
