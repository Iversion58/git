package com.itheima.core.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.itheima.common.core.bean.Testdb;
import com.itheima.core.service.TestTbService;

@Controller
@RequestMapping(value = "/control")
public class CenterController {

	
	@RequestMapping(value="/index.do")
	public String index(){
		return "index";
	}

	@RequestMapping(value="top.do")
	public String top(){
		return "top";
	}
	

	@RequestMapping(value="main.do")
	public String main(){
		return "main";
	}
	
	@RequestMapping(value="head.do")
	public String head(){
		return "head";
	}
	

	@RequestMapping(value="left.do")
	public String left(){
		return "left";
	}

	@RequestMapping(value="right.do")
	public String right(){
		return "right";
	}
	
	
	
	
	
}
