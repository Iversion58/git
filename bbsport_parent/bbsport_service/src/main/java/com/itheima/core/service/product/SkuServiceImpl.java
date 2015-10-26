package com.itheima.core.service.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.product.Color;
import com.itheima.common.core.bean.product.Sku;
import com.itheima.common.core.bean.product.SkuQuery;
import com.itheima.core.dao.product.ColorDao;
import com.itheima.core.dao.product.SkuDao;

@Service("skuService")
@Transactional
public class SkuServiceImpl implements SkuService {

	@Autowired
	private SkuDao skuDao;
	@Autowired
	private ColorDao colorDao;
	
	@Override
	public List<Sku> selectListByQuery(Long productId) {
			SkuQuery query=new SkuQuery();
			query.createCriteria().andProductIdEqualTo(productId);
			List<Sku> list = skuDao.selectByExample(query);
			for (Sku sku : list) {
				Color color = colorDao.selectByPrimaryKey(sku.getColorId());
				sku.setColor(color);
			}
		
		return list;
	}

	@Override
	public void upateSku(Sku sku) {
		sku.setUpdateTime(new Date());
			skuDao.updateByPrimaryKeySelective(sku);
	}

	@Override
	public List<Sku> selectSkuListByProductIdWithSock(Long id) {
		SkuQuery skuQuery = new SkuQuery();
		skuQuery.createCriteria().andProductIdEqualTo(id).andStockGreaterThan(0);
		List<Sku> skus=skuDao.selectByExample(skuQuery);
		for (Sku sku : skus) {
			sku.setColor(colorDao.selectByPrimaryKey(sku.getColorId()));
		}

		return skus;
	}
	
	public void updateSkuById(Sku sku){
		
		skuDao.updateByPrimaryKeySelective(sku);
	}
	
	
}
