package com.itheima.common.core.bean.cart;

import java.io.Serializable;

import com.itheima.common.core.bean.product.Sku;

/**
 * 购物项
 * @author Iverson
 *
 */

public class BuyerItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Sku sku;

	//数量
	private Integer amount =1;
	//是否有货
	private Boolean isHave = true;
	
	public Sku getSku() {
		return sku;
	}

	public void setSku(Sku sku) {
		this.sku = sku;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Boolean getIsHave() {
		return isHave;
	}

	public void setIsHave(Boolean isHave) {
		this.isHave = isHave;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + ((isHave == null) ? 0 : isHave.hashCode());
		result = prime * result + ((sku == null) ? 0 : sku.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BuyerItem other = (BuyerItem) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (isHave == null) {
			if (other.isHave != null)
				return false;
		} else if (!isHave.equals(other.isHave))
			return false;
		if (sku == null) {
			if (other.sku != null)
				return false;
		} else if (!sku.getId().equals(other.sku.getId()))
			return false;
		return true;
	}
	
	
	
}
