package com.itheima.core.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.product.Sku;
import com.itheima.core.service.product.SkuService;

@Controller
public class SkuController {

	@Autowired
	private SkuService skuService;
	
	@RequestMapping("/sku/list.do")
	public String list(Long productId,Model model){
		List<Sku> skus=skuService.selectListByQuery(productId);
		model.addAttribute("skus", skus);
	return "sku/list";	
	}
	
	@RequestMapping("/sku/add.do")
	public void add(@RequestBody Sku sku,HttpServletResponse response) throws Exception{
		skuService.upateSku(sku);
		JSONObject jo=new JSONObject();
		jo.put("message", "保存成功");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
		
	}
	/*
	 *$.post(url,data,fn,type)方式
	@RequestMapping("/sku/add.do")
	
	public void add(Sku sku,HttpServletResponse response) throws Exception {
		
		skuService.upateSku(sku);
		JSONObject jo=new JSONObject();
		jo.put("message", "保存成功");
		response.setContentType("application/json;charset=utf-8");
		response.getWriter().write(jo.toString());
	}
	*/
}
