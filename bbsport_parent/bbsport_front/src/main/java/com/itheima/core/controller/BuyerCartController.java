package com.itheima.core.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * @author Iverson
 *
 */
@Controller
public class BuyerCartController {

	@RequestMapping(value="/shopping/buyerCart.shtml")
	public String buyerCart(Long skuId,Integer amount){
		return "product/cart";
	}
	
}
