package com.itheima.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.core.service.product.ProductService;

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

	//去列表页面
	@RequestMapping(value = "/product/display/list.shtml")
	public String list(Integer pageNo,String keyword,Model model){
		
		Pagination pagination = productService.selectPaginationbyQueryFromSolr(pageNo, keyword);
		
		model.addAttribute("pagination", pagination);
		
		
		return "product/product";
	}
}
