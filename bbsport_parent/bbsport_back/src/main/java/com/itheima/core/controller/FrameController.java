package com.itheima.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping(value="/control")
@Controller
public class FrameController {

	@RequestMapping(value = "/frame/product_main.do")
	public String product_main(){
		
		return "frame/product_main";
	}
	@RequestMapping(value = "/frame/product_left.do")
	public String product_left(){
		
		return "frame/product_left";
	}
	
}
