package com.itheima.core.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.product.Brand;
import com.itheima.core.service.product.BrandService;

import cn.itcast.common.page.Pagination;

@Controller
public class BrandController{
	@Autowired
	private BrandService brandService;
	
	
	@RequestMapping(value = "/brand/list.do")
				public String list(Integer pageNo,String name,Integer isDisplay,Model model){
						if(name != null){
							model.addAttribute("name", name);
						}
						if(isDisplay!=null){
							model.addAttribute("isDisplay", isDisplay);
						}else{
							model.addAttribute("isDisplay", 1);
						}
		
//						List<Brand> brands = brandService.selectListByQuery(name, isDisplay);
						Pagination pagination = brandService.selectPaginationByQuery(pageNo, name, isDisplay);
						model.addAttribute("pagination",pagination);
					return "/brand/list";
				}
	
		@RequestMapping(value="/brand/toAdd.do")
		public String toAdd(){
			
			return "/brand/add";
		}
		
		
		@RequestMapping(value="/brand/add.do")
		public String add(Brand brand){
			brandService.insertBrand(brand);
			return "redirect:/brand/list.do";
		}
		
		@RequestMapping("/brand/deleteByIds")
		public String deleteById(Integer[] ids,String name,Integer isDisplay,Integer pageNo,Model model){
			
			brandService.deleteByIds(ids);
			if(null!=name){
				model.addAttribute("name",name);
			}
			if(null!=isDisplay){
				model.addAttribute("isDisplay", isDisplay);
			}
			if(null!=pageNo){
				model.addAttribute("pageNo", pageNo);
			}
			return "redirect:/brand/list.do";
		}
		
		@RequestMapping("/brand/toEdit.do")
		public String toEdit(Integer id,Model model){
			Brand brand =brandService.selectBrandById(id);
			model.addAttribute("brand", brand);
			return "/brand/edit";
		}
		
		@RequestMapping("/brand/edit.do")
		public String edit(Brand brand){
			
			brandService.updateBrand(brand);
			
			return "redirect:/brand/list.do";
		}
		
}
