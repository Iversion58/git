package com.itheima.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.common.core.bean.product.Color;
import com.itheima.common.core.bean.product.Product;
import com.itheima.common.core.bean.product.Type;
import com.itheima.core.service.product.BrandService;
import com.itheima.core.service.product.ColorService;
import com.itheima.core.service.product.ProductService;
import com.itheima.core.service.product.TypeService;

import cn.itcast.common.page.Pagination;

@Controller
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BrandService brandService;

	@Autowired
	private TypeService typeService;
	
	@Autowired
	private ColorService colorService;
	
	
	
	@RequestMapping("/product/list.do")
	public String list(String name,Boolean isShow,Long brandId,Integer pageNo,Model model){

		List<Brand> brands = brandService.selectListByQuery(null, 1);
		model.addAttribute("brands", brands);
		Pagination pagination= productService.selectPaginationByQuery(name,isShow,brandId,pageNo);
		if(isShow==null)
			model.addAttribute("isShow", false);
		model.addAttribute("isShow", isShow);
		model.addAttribute("name", name);
		model.addAttribute("brandId", brandId);
		model.addAttribute("pagination", pagination);
		return "/product/list";
	}
	
	@RequestMapping("/product/toAdd.do")
	public String toAdd(Model model){
		//类型
		List<Type> types= typeService.selectListByQuery();
		model.addAttribute("types", types);
		//品牌
		List<Brand> brands = brandService.selectListByQuery(null, 1);
		model.addAttribute("brands", brands);
		//颜色
		List<Color> colors= colorService.selectListByQuery();
		model.addAttribute("colors", colors);
		
		return "/product/add";
	}
	
	@RequestMapping("/product/add.do")
	public String add(Product product){
		
		productService.insertProduct(product);
		
		
		return "redirect:/product/list.do";
	}
	
	
}
