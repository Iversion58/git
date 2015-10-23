package com.itheima.core.service.product;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.product.Img;
import com.itheima.common.core.bean.product.ImgQuery;
import com.itheima.common.core.bean.product.Product;
import com.itheima.common.core.bean.product.ProductQuery;
import com.itheima.common.core.bean.product.ProductQuery.Criteria;
import com.itheima.common.core.bean.product.Sku;
import com.itheima.common.core.bean.product.SkuQuery;
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
		
		@Autowired
		private SolrServer solrServer;
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

	@Override
	public void updateIsShow(Long[] ids) {
					//修改上架状态
		for (Long id : ids) {
			Product product=new Product();
			product.setIsShow(true);
			product.setId(id);
			productDao.updateByPrimaryKeySelective(product);
			
			SolrInputDocument doc=new SolrInputDocument();
			//商品id
			doc.setField("id", id);
			//商品名称
			Product p=productDao.selectByPrimaryKey(id);
			doc.setField("name_ik", p.getName());
			//价格
			SkuQuery skuQuery=new SkuQuery();
			skuQuery.createCriteria().andProductIdEqualTo(id);
			skuQuery.setOrderByClause("price asc");
			skuQuery.setPageNo(1);
			skuQuery.setPageSize(1);
			skuQuery.setFields("price");
			List<Sku> skus = skuDao.selectByExample(skuQuery);
			// select price from bbs_sku where product_id = 277 order by price asc limit 0,1
			doc.setField("price", skus.get(0).getPrice());
			//图片url
			ImgQuery imgQuery=new ImgQuery();
			imgQuery.createCriteria().andProductIdEqualTo(id).andIsDefEqualTo(true);
			List<Img> imgs = imgDao.selectByExample(imgQuery);
			doc.setField("url",imgs.get(0).getUrl());
			//品牌
			doc.setField("brandId",p.getBrandId());
				//时间
			doc.setField("last_modified",new Date());
		
			try {
				solrServer.add(doc);
				solrServer.commit();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
			
			
		
		//TODO			3.静态化
		
	}




	@Override
	//查询  返回分页对象   查询数据库 SOlr服务器
		public Pagination selectPaginationbyQueryFromSolr(Integer pageNo,String keyword){
			ProductQuery productQuery = new ProductQuery();
			//当前页
			productQuery.setPageNo(Pagination.cpn(pageNo));
			//每页数
			productQuery.setPageSize(Product.PAGESIZE);
			//Solr服务器
			SolrQuery solrQuery = new SolrQuery();
			
			StringBuilder params = new StringBuilder();
			
			//关键词
			solrQuery.set("q", "name_ik:" + keyword);
			
			params.append("keyword=").append(keyword);
			//过滤条件
			//排序
			solrQuery.addSort("price", ORDER.asc);
			//高亮
			//分页  开始行  每页数
			solrQuery.setStart(productQuery.getStartRow());
			solrQuery.setRows(Product.PAGESIZE);
			
			//结果集
			SolrDocumentList docs = null;
			try {
				QueryResponse response = solrServer.query(solrQuery);
				//结果集
				docs = response.getResults();
				
			} catch (SolrServerException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//分页对象
			Pagination pagination = new Pagination(
					productQuery.getPageNo(),
					productQuery.getPageSize(),
					(int)docs.getNumFound()
					);
			//创建商品结果集
			List<Product> products = new ArrayList<Product>();
			for (SolrDocument doc : docs) {
				Product product = new Product();
				//商品ID
				String id = (String) doc.get("id");
				product.setId(Long.parseLong(id));
				//商品名称
				String name = (String) doc.get("name_ik");
				product.setName(name);
				//价格
				Float price = (Float) doc.get("price");
				Sku sku = new Sku();
				sku.setPrice(price);
				product.setSku(sku);
				//图片Url
				String url = (String) doc.get("url");
				Img img = new Img();
				img.setUrl(url);
				product.setImg(img);
				//品牌ID 
				Integer brandId = (Integer) doc.get("brandId");
				product.setBrandId(Long.parseLong(String.valueOf(brandId)));
				//时间
				//结果集
				products.add(product);
				
			}
			//结果集
			pagination.setList(products);
			//页面展示
			String url = "/product/display/list.shtml";
			pagination.pageView(url, params.toString());
			
			return pagination;
		}

}
