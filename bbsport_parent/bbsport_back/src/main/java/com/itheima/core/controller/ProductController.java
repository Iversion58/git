package com.itheima.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.core.service.product.BrandService;
import com.itheima.core.service.product.ProductService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;
	@RequestMapping("/product/list.do")
	public String list(String name,Boolean isShow,Long brandId,Integer pageNo,Model model){

		List<Brand> brands = brandService.selectListByQuery(null, 1);
		model.addAttribute("brands", brands);
		Pagination pagination= productService.selectPaginationByQuery(name,isShow,brandId,pageNo);
		model.addAttribute("isShow", isShow);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("pagination", pagination);
		return "/product/list";
	}
	
	@RequestMapping("/product/toAdd.do")
	public String toAdd(){
		return "/product/add";
	}
	
}
