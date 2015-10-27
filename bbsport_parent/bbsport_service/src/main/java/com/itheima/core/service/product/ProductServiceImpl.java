package com.itheima.core.service.product;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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

import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.core.bean.product.Color;
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
import com.itheima.core.service.staticpage.StaticPageService;

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
	
		@Autowired
		private StaticPageService staticPageService;
		@Autowired
		private SkuService skuService;
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
		
		SolrInputDocument doc=new SolrInputDocument();
		//商品id
		doc.setField("id", id);
	
		doc.setField("name_ik", product.getName());
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
		doc.setField("brandId",id);
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
					//数据
			Map<String, Object> root =new HashMap<String, Object>();
		
				//商品		对象			图片
			Product pp = selectProductById(id);
			root.put("product", pp);
			
				//库存		颜色
			List<Sku> ss=skuService.selectSkuListByProductIdWithSock(id);
			root.put("skus", ss);
			
			Set<Color> colors = new HashSet<Color>();
			
			for (Sku sku : skus) {
				colors.add(sku.getColor());
			}
			root.put("colors", colors);
		
					//TODO			3.静态化	
			staticPageService.	index(root, id);
		}
			
			
		
	
		
	}




	@Override
	//查询  返回分页对象   查询数据库 SOlr服务器
		public Pagination selectPaginationbyQueryFromSolr(Integer pageNo,String keyword,Long brandId,String price){
			
		ProductQuery productQuery = new ProductQuery();
					//设置当前页和每页条数
			productQuery.setPageNo(Pagination.cpn(pageNo));
			productQuery.setPageSize(Product.PAGESIZE);
				//接收 pagination的url路径
			StringBuilder params = new StringBuilder();
			
			SolrQuery solrQuery = new SolrQuery();
					//关键词
			solrQuery.set("q", "name_ik : "+keyword);
			
			params.append("keyword=").append(keyword);
		
				//过滤条件
			if(null !=brandId){
				solrQuery.addFilterQuery("brandId:"+brandId);
				params.append("&brandId=").append(brandId);
			}
				//0-79 600
			if(null!=price){
				String[] p=price.split("-");
			if(p.length ==2){
				Float startP = new Float(p[0]);
				Float endP= new Float(p[1]);
					solrQuery.addFilterQuery("price:["+startP+" TO "+endP+"]");
			}else{
				Float startP = new Float(p[0]);
				solrQuery.addFilterQuery("price:["+startP+" TO *]");
			}
			params.append("&price=").append(price);
			}
			
			//排序
			solrQuery.addSort("price",ORDER.asc);
				//高亮
			//1：开启高亮
			solrQuery.setHighlight(true);
				//2.设置高亮字段	
			solrQuery.addHighlightField("name_ik");
			//3.设置高亮简单的前缀 及后缀
			solrQuery.setHighlightSimplePre("<span style='color:red'>");
			solrQuery.setHighlightSimplePost("</span>");
			
					//分页	开始行		每页数
			solrQuery.setStart(productQuery.getStartRow());
			solrQuery.setRows(productQuery.getPageSize());
			
					//结果集
			SolrDocumentList docs = null;
			QueryResponse response= null;
		
			try {
				response = solrServer.query(solrQuery);
				
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
			String id=(String) doc.get("id");
			product.setId(Long.parseLong(id));
			Map<String, Map<String, List<String>>> highlighting = response.getHighlighting();
			Map<String, List<String>> map = highlighting.get(id);
			List<String> list = map.get("name_ik");
				//商品名称
			product.setName(list.get(0));
				//价格
			Sku sku=new Sku();
			sku.setPrice((Float)doc.get("price"));
			product.setSku(sku);
				//图片url
			String url=(String) doc.get("url");
			Img img=new Img();
			img.setUrl(url);
			product.setImg(img);
					//品牌ID
			product.setBrandId(Long.valueOf((Integer)doc.get("brandId")));
					//时间
					//结果集
			products.add(product);
		}
				//结果集
		pagination.setList(products);
				//页面展示
		String url="/product/display/list.shtml";
		pagination.pageView(url, params.toString());
		
			return pagination;
		}




	@Override
	public List<Brand> selectBrandListFormRedis() {

				Set<String> keys = jedis.keys("brand:*");
				
				List<Brand> brands=new ArrayList<Brand>();
				
				for (String key : keys) {
					Brand brand =new Brand();
						//id
					brand.setId(Long.parseLong(jedis.hget(key,"id")));
						//名称
					brand.setName(jedis.hget(key, "name"));
					brands.add(brand);
				}
		
		return brands;
	}

			//从redis中取品牌名称			通过品牌Id
	public String selectBrandNameById(Long brandId){
		return jedis.hget("brand:"+brandId,"name");
	}




	@Override
	public Product selectProductById(Long id) {
					//商品
		Product product=productDao.selectByPrimaryKey(id);
		ImgQuery imgQuery=new ImgQuery();
		imgQuery.createCriteria().andProductIdEqualTo(product.getId()).andIsDefEqualTo(true);
		List<Img> imgs=imgDao.selectByExample(imgQuery);
		product.setImg(imgs.get(0));
		
		return product;
	}
	
	
	
}
