package com.itheima.core.controller;

import java.io.StringWriter;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.itheima.common.core.bean.cart.BuyerCart;
import com.itheima.common.core.bean.cart.BuyerItem;
import com.itheima.common.core.bean.product.Sku;
import com.itheima.common.core.web.Constants;
import com.itheima.common.utils.RequestUtils;
import com.itheima.core.service.cart.CartService;
import com.itheima.core.service.product.SkuService;
import com.itheima.core.service.user.SessionProvider;

/**
 *		购物车 去购物车页面 清空购物车 删除购物车里一款商品 +- 
 * @author Iverson
 *
 */
@Controller
public class BuyerCartController {

	@Autowired
	private SkuService skuService;
	
	@Autowired
	private SessionProvider sessionProvider;

	private CartService cartService;
	
	@RequestMapping(value="/shopping/buyerCart.shtml")
	public String buyerCart(Long skuId,Integer amount,Model model,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
						//声明购物车
		BuyerCart buyerCart = null;
		// 对象转JSON JSON转对象	用到的类
		ObjectMapper om = new ObjectMapper();
			//不要转null
		om.setSerializationInclusion(Include.NON_NULL);
				//购物车对象
		//1.获取Cookie中的购物车
		Cookie[] cookies=request.getCookies();
		if(null !=cookies &&cookies.length > 0){
			for (Cookie cookie : cookies) {
				if (Constants.BUYER_CART.equals(cookie.getName())) {
					buyerCart=om.readValue(cookie.getValue(),BuyerCart.class);
					break;
				}
			}
		}
		// 2:没有--》创建一个购物车
		if(null == buyerCart){
			buyerCart=new BuyerCart();
		}
			//判断skuId添加当前款
		if(null != skuId){
					//当前商品追加到购物车
			Sku sku = new Sku();
			sku.setId(skuId);
			BuyerItem buyerItem = new BuyerItem();
			buyerItem.setSku(sku);
			buyerItem.setAmount(amount);
				//购物车添加购物项
			buyerCart.addItem(buyerItem);
		}
			//获取session中的用户
		String username=sessionProvider.getAttributeCode(RequestUtils.getCSESSIONID(request, response));
		//登录状态
		if(null != username){
			//登录
			cartService.insertBuyerCartToRedis(buyerCart,username);
					//清理购物车k
			Cookie cookie = new Cookie(Constants.BUYER_CART,null);
					//设置时间
			cookie.setMaxAge(0);
					//设置路径
			cookie.setPath("/");
				
			response.addCookie(cookie);
					//取出所有购物车中商品
			buyerCart=cartService.selectBuyerCartFromRedis(username);
			
		}else{
			// 非登陆
			// 3:当前商品追加到购物车中 再把购物车对象添加到Cookie中 覆盖原来的Cookie里购物车
			if(null != skuId){
					//再把购物车对象添加到Cookie中		覆盖原来的cookie里购物车
						//JSON	串
				StringWriter w = new StringWriter();
				om.writeValue(w, buyerCart);
				Cookie cookie = new Cookie(Constants.BUYER_CART,w.toString());
							//设置时间
				cookie.setMaxAge(Integer.MAX_VALUE);
						//设置路径
				cookie.setPath("/");
				response.addCookie(cookie);
			}
					//把购物车对象排序
			Collections.sort(buyerCart.getItems(),new Comparator<BuyerItem>() {

				@Override
				public int compare(BuyerItem o1, BuyerItem o2) {
							//判断		1 0 -1
					return -1;
				}
			});
			
			
		}
		
		// 4:购物车装满
		List<BuyerItem> items = buyerCart.getItems();
		//5:装满的购物车回显到购物车页面
		if(items.size() > 0){
			for (BuyerItem item : items) {
				Sku sku=cartService.selectSkuById(item.getSku().getId());
				item.setSku(sku);
			}
		}
			//5.装满的购物车回显到购物车页面
			model.addAttribute("buyerCart",buyerCart);
		
			return "product/cart";
	}
	
			//结算
	@RequestMapping(value="/shopping/delProduct.shtml")
	public String delProduct(HttpServletRequest request,HttpServletResponse response,Model model ){
		/*
		 * a) 判断用户是否登陆（必须登陆） 如果：没有 返回登陆页面 1：之前访问页面 2：直接下一个页面（案例） Springmvc拦截器中
		 * b) 购物车中无商品（浏览器后退）
		 * 
		 * c) 购物车中商品必须有库存（如果没有：购物车页面刷新、提示无货、如果有货、放行进入下一个页面）
		 */
					//判断		是否登录
		String username = sessionProvider.getAttributeCode(RequestUtils.getCSESSIONID(request, response));
		
		BuyerCart buyerCart = cartService.selectBuyerCartFromRedis(username);
		Boolean flag = true;

		if(buyerCart.getItems().size() > 0){
					//有商品
			for (BuyerItem item : buyerCart.getItems()) {
						//判断是否有库存		mysql
				Integer stock = cartService.selectSkuStockById(item.getSku().getId());
				if(item.getAmount() > stock){
							//无货
					item.setIsHave(false);
					flag = false ;
				}
			}
			
			if(!flag){
						//4.购物车装满
				List<BuyerItem> items = buyerCart.getItems();
				if(items.size() > 0){
					for (BuyerItem buyerItem : items) {
						Sku sku = cartService.selectSkuById(buyerItem.getSku().getId());
						buyerItem.setSku(sku);
					}
				}
					//5.装满购物车回显到购物车页面
				model.addAttribute("buyerCart", buyerCart);
						//购物车页面刷新
				return "product/cart";
			}
			
		}else{
						//		无商品
			return "redirect:/shopping/buyerCart.shtml";
		}
		
		return "product/productOrder";
	}
	
				@RequestMapping(value = "/shopping/delProduct.shtml")
		public String delProduct(Long skuId,HttpServletRequest request, HttpServletResponse response) throws Exception{
					
					return "redirect:/shopping/buyerCart.shtml";
				}
	
	
				
	
}
