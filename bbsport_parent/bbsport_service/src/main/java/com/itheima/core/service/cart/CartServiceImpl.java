package com.itheima.core.service.cart;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.common.core.bean.cart.BuyerCart;
import com.itheima.common.core.bean.cart.BuyerItem;
import com.itheima.common.core.bean.product.Color;
import com.itheima.common.core.bean.product.Img;
import com.itheima.common.core.bean.product.ImgQuery;
import com.itheima.common.core.bean.product.ImgQuery.Criteria;
import com.itheima.common.core.bean.product.Product;
import com.itheima.common.core.bean.product.Sku;
import com.itheima.core.dao.product.ColorDao;
import com.itheima.core.dao.product.ImgDao;
import com.itheima.core.dao.product.ProductDao;
import com.itheima.core.dao.product.SkuDao;

import redis.clients.jedis.Jedis;

@Service("cartService")
public class CartServiceImpl implements CartService{

	@Autowired
	private SkuDao skuDao;
	
	@Autowired
	private ColorDao colorDao;
	
	@Autowired
	private ProductDao productDao;

	@Autowired
	private ImgDao imgDao;
	
	@Autowired
	private Jedis jedis;
	@Override
	public Sku selectSkuById(Long id) {
						//sku对象
				Sku sku = skuDao.selectByPrimaryKey(id);
						//设置颜色
				sku.setColor( colorDao.selectByPrimaryKey(sku.getColorId()));
				Product product = productDao.selectByPrimaryKey(sku.getProductId());
				ImgQuery imgQuery = new ImgQuery();
				imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
				List<Img> imgs = imgDao.selectByExample(imgQuery);
				product.setImg(imgs.get(0));
				sku.setProduct(product);
		return sku;
	}

	@Override
	public void insertBuyerCartToRedis(BuyerCart buyerCart, String username) {
					//遍历
		for (BuyerItem buyerItem : buyerCart.getItems()) {
				//判断添加的商品是否在redis服务器已经存在了
			if(jedis.hexists("buyerItem:"+username, String.valueOf(buyerItem.getSku().getId()))){
						//存在就追加数量
				jedis.hincrBy("buyerItem:"+username, String.valueOf(buyerItem.getSku().getId()),buyerItem.getAmount() );
			}else {
							//购物车
				jedis.lpush("buyerCart:"+username,String.valueOf(buyerItem.getSku().getId()) );
							//购物项
				jedis.hset("buyerItem:"+username, String.valueOf(buyerItem.getSku().getId()),String.valueOf( buyerItem.getAmount()));
			}
			
		}
	}

			//取出所有商品从redis
	public BuyerCart selectBuyerCartFromRedis(String username){
	
				BuyerCart buyerCart = new BuyerCart();
						//获redis		遍历所有商品
				List<String> skuIds = jedis.lrange("buyerCart:"+username, 0, -1);
				
				for (String skuId : skuIds) {
					Sku sku = new Sku();
				}
		
		return buyerCart;
	}

	@Override
	public Integer selectSkuStockById(Long id) {
		return skuDao.selectByPrimaryKey(id).getStock();
	}
	
	
	
	
}
