package cn.itcast.activiti.first;

/**
 * 
 * <p>Title: Order</p>
 * <p>Description:采购单信息 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-24
 * @version 1.0
 */
public class Order implements java.io.Serializable {

	//采购单的名称
	private String name;
	//采购单金额
	private Float price;
	
	//采购单审核状态
	private String checkResult;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Float getPrice() {
		return price;
	}

	public void setPrice(Float price) {
		this.price = price;
	}

	public String getCheckResult() {
		return checkResult;
	}

	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	
	
}
