package com.itheima.common.core.bean.product;

import java.io.Serializable;

public class BrandQuery implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	//品牌ID  bigint
			private Long id;
			//品牌名称
			private String name;
			//描述
			private String description;
			//图片URL
			private String imgUrl;
			//排序  越大越靠前   
			private Integer sort;
			//是否可用   0 不可用 1 可用
			private Integer isDisplay;//is_display
			public Long getId() {
				return id;
			}
			public void setId(Long id) {
				this.id = id;
			}
			public String getName() {
				return name;
			}
			public void setName(String name) {
				this.name = name;
			}
			public String getDescription() {
				return description;
			}
			public void setDescription(String description) {
				this.description = description;
			}
			public String getImgUrl() {
				return imgUrl;
			}
			public void setImgUrl(String imgUrl) {
				this.imgUrl = imgUrl;
			}
			public Integer getSort() {
				return sort;
			}
			public void setSort(Integer sort) {
				this.sort = sort;
			}
			public Integer getIsDisplay() {
				return isDisplay;
			}
			public void setIsDisplay(Integer isDisplay) {
				this.isDisplay = isDisplay;
			}
			
				/**
				 * 分页
				 */
			private static final int PAGE_SIZ=10;
			//当前页
			private int pageNo=1;
			
			//每页条数
			private int pageSize=PAGE_SIZ;

			private int startRow;
			public int getPageNo() {
				return pageNo;
			}
			public void setPageNo(int pageNo) {
				this.pageNo = pageNo;
				startRow=(pageNo-1)*pageSize;
			}
			public int getPageSize() {
				return pageSize;
			}
			public void setPageSize(int pageSize) {
				this.pageSize = pageSize;
				startRow=(pageNo-1)*pageSize;
			}
			public int getStartRow() {
				return startRow;
			}
			public void setStartRow(int startRow) {
				this.startRow = startRow;
			}
			
			

}
