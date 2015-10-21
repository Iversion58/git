package com.itheima.core.service.product;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.product.Img;
import com.itheima.common.core.bean.product.ImgQuery;
import com.itheima.common.core.bean.product.Product;
import com.itheima.common.core.bean.product.ProductQuery;
import com.itheima.common.core.bean.product.ProductQuery.Criteria;
import com.itheima.common.core.bean.product.Sku;
import com.itheima.core.dao.product.ImgDao;
import com.itheima.core.dao.product.ProductDao;
import com.itheima.core.dao.product.SkuDao;

import cn.itcast.common.page.Pagination;
import redis.clients.jedis.Jedis;

@Service("productService")
@Transactional
public class ProductServiceImpl implements ProductService{

		@Autowired
		private ProductDao productDao;
		@Autowired
		private ImgDao imgDao;
		@Autowired
		private SkuDao skuDao;
	
		@Autowired
		private Jedis jedis;
		
		/**
		 * 查询
		 */
	@Override
	public Pagination selectPaginationByQuery(String name, Boolean isShow, Long brandId, Integer pageNo) {
	
		ProductQuery productQuery=new ProductQuery();
		StringBuilder params=new StringBuilder();
		//当前页
		productQuery.setPageNo(Pagination.cpn(pageNo));
		//每条页数
		productQuery.setPageSize(3);
		//排序
		productQuery.setOrderByClause("id desc");
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
		productQuery.setPageNo(pagination.getPageNo());
		List<Product> list = productDao.selectByExample(productQuery);
		for (Product product : list) {
			ImgQuery imgQuery=new ImgQuery();
			imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
				//查询图片对象
			List<Img> imgs = imgDao.selectByExample(imgQuery);
			product.setImg(imgs.get(0));
		}
		
		pagination.setList(list);
		
		String url="/product/list.do";
		pagination.pageView(url, params.toString());
		
		return pagination;
	}


	
	
	/**
	 * 保存product
	 */
	@Override
	public void insertProduct(Product product) {

		Long id = jedis.incr("pno");
		product.setId(id);
				//创建时间
		product.setCreateTime(new Date());
			//默认下架
		product.setIsShow(false);
			//不删除
		product.setIsDel(true);
		productDao.insertSelective(product);
		//保存图片
		Img img = product.getImg();
				//设置默认
		img.setIsDef(true);
		img.setProductId(product.getId());

		//保存颜色和尺寸
		for(String color:product.getColors().split(",")){
			for(String size:product.getSizes().split(",")){
						Sku sku=new Sku();
						//商品ID
						sku.setProductId(product.getId());
						//颜色
						sku.setColorId(Long.parseLong(color));
							//尺寸
						sku.setSize(size);
							//运费
						sku.setDeliveFee(10f);
						//市场价
						sku.setMarketPrice(0f);
							//价格
						sku.setPrice(0f);
							//库存
						sku.setStock(0);
							//购买限制
						sku.setUpperLimit(0);
						sku.setCreateTime(new Date());
						skuDao.insertSelective(sku);
			}
			
			
		}
		imgDao.insertSelective(img);		
		
		
	}

}
