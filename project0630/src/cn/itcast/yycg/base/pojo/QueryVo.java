package cn.itcast.yycg.base.pojo;

public abstract class QueryVo {
	// 当前页码
	private int page;

	// 每页显示个数
	private int rows;

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}
	
	

}
