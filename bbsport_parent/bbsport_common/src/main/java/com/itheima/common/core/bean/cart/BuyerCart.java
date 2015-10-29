package com.itheima.common.core.bean.cart;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jca.endpoint.GenericMessageEndpointFactory;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
	 * 		购物车对象
	 * @author Iverson
	 *
	 */
public class BuyerCart implements Serializable{

/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private  Float MEMBERS_PRICE = 79f;
	
	
				//购物项
	private List<BuyerItem> items=new ArrayList<>();

	
				//添加商品
	public void addItem(BuyerItem buyerItem){
		items.add(buyerItem);
	}
	
	
	public Float getMEMBERS_PRICE() {
		return MEMBERS_PRICE;
	}

	public void setMEMBERS_PRICE(Float mEMBERS_PRICE) {
		MEMBERS_PRICE = mEMBERS_PRICE;
	}

	public List<BuyerItem> getItems() {
		return items;
	}

	public void setItems(List<BuyerItem> items) {
		this.items = items;
	}
	
	
	//小计
	//商品数量
	@JsonIgnore
	public Integer getProductAmount(){
			Integer result = 0 ;
		for (BuyerItem item : items) {
			result +=item.getAmount() ;
		}
		return result;
	}
	//商品金额
	@JsonIgnore
	public Float getProductPrice(){
		Float result = 0f;
		for (BuyerItem item : items) {
			result += item.getAmount()*item.getSku().getPrice();
		}
		
		return result;
	}
	//运费
	@JsonIgnore
	public Float getFee(){
			Float result = 0f;
			if(getProductPrice()<MEMBERS_PRICE){
				result = 5f;
			}
				return result;
	}
	
		//应付金额
	public Float getTotalPrice(){
			return getProductPrice()+getFee();
	}
	
	
}
