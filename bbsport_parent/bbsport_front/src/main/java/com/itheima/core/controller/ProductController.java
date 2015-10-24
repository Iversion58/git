package com.itheima.core.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.core.service.product.ProductService;
import com.itheima.core.service.product.SkuService;

import cn.itcast.common.page.Pagination;

/**
 * 前台商品
 * @author lx
 *
 */
@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SkuService skuService;

	//去列表页面
	@RequestMapping(value = "/product/display/list.shtml")
	public String list(Integer pageNo,String keyword,Long brandId,String price,Model model){
					//查询品牌结果集
		List<Brand> brands=productService.selectBrandListFormRedis();
		model.addAttribute("brands",brands);
			
				//标识
		Boolean flag=false;
		Map<String,String> map = new HashMap<String,String>();
				
				//判断
		if(null != brandId){
			flag =true;
			model.addAttribute("brandId", brandId);
			
			map.put("品牌",	productService.selectBrandNameById(brandId));
		
		}
		if(null !=price){
			flag=true;
			model.addAttribute("price", price);
			map.put("价格", price);
		}
		model.addAttribute("flag", flag);
		model.addAttribute("map", map);
		
		
		Pagination pagination = productService.selectPaginationbyQueryFromSolr(pageNo, keyword,brandId,price);
		
		model.addAttribute("pagination", pagination);
		
		
		return "product/product";
	}
}
