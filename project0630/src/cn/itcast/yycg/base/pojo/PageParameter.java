/***************************************
 * 
 * 分页查询参数类
 * 
 * **************************************/
package cn.itcast.yycg.base.pojo;

public class PageParameter {

	// 当前页码
	private int PageQuery_currPage;

	// 总页数
	private int PageQuery_Psize;

	// 总记录数
	private long PageQuery_infoCount;

	// 每页显示个数
	private int PageQuery_pageSize = 30;
	
	// 开始坐标
	private int PageQuery_star = 0;
	
	// 结束坐标
    private int PageQuery_end = 30;

	public static final int PageQuery_pageSize_common = 30;

	/*
	 * 将初始参数传入处理 infoCount:记录总数，pageSize:每页显示个数，currPage:当前页码
	 */
	public PageParameter(int currPage,int pageSize,long infoCount) {

		this.PageQuery_infoCount = infoCount;
		this.PageQuery_pageSize = pageSize;
		this.PageQuery_currPage = currPage;
		
		float Psize_l = infoCount / (float) (this.PageQuery_pageSize);
		if (PageQuery_currPage < 2) {
			PageQuery_currPage = 1;
			PageQuery_star = 0;
		} else if (PageQuery_currPage > Psize_l) {
			if(Psize_l==0){
				
				PageQuery_currPage=1;
			}else{
				PageQuery_currPage = (int) Math.ceil(Psize_l);
			}
			
			PageQuery_star = (PageQuery_currPage - 1) * this.PageQuery_pageSize;
		} else {
			PageQuery_star = (PageQuery_currPage - 1) * this.PageQuery_pageSize;
		}
		PageQuery_Psize = (int) Math.ceil(Psize_l);
		this.PageQuery_end = PageQuery_currPage*this.PageQuery_pageSize;
	}
	
	public int getPageQuery_currPage() {
		return PageQuery_currPage;
	}

	public void setPageQuery_currPage(int pageQuery_currPage) {
		PageQuery_currPage = pageQuery_currPage;
	}

	public int getPageQuery_Psize() {
		return PageQuery_Psize;
	}

	public void setPageQuery_Psize(int pageQuery_Psize) {
		PageQuery_Psize = pageQuery_Psize;
	}

	public long getPageQuery_infoCount() {
		return PageQuery_infoCount;
	}

	public void setPageQuery_infoCount(int pageQuery_infoCount) {
		PageQuery_infoCount = pageQuery_infoCount;
	}

	public int getPageQuery_pageSize() {
		return PageQuery_pageSize;
	}

	public void setPageQuery_pageSize(int pageQuery_pageSize) {
		PageQuery_pageSize = pageQuery_pageSize;
	}

	public int getPageQuery_star() {
		return PageQuery_star;
	}

	public void setPageQuery_star(int pageQuery_star) {
		PageQuery_star = pageQuery_star;
	}

	public PageParameter getPageQuery() {
		return this;
	}

	public int getPageQuery_end() {
		return PageQuery_end;
	}

}
