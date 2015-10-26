package com.itheima.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class LoginController {

	
	@RequestMapping(value="/shopping/login.aspx")
	public String login(){
		
		return "bury/login";
	}
}
