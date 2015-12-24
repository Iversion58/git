package cn.itcast.yycg.business.cgd.pojo;

import java.util.List;

import cn.itcast.yycg.base.pojo.QueryVo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.ypml.pojo.YpxxCustom;

public class YycgdQueryVo extends QueryVo {
	
	private YycgdCustom yycgdCustom;
	
	private YycgdmxCustom yycgdmxCustom;
	
	//药品信息
	private YpxxCustom ypxxCustom;
	
	//采购单状态
	private List<Dictinfo> cgdztlist;
	
	//采购状态
	private List<Dictinfo> cgztList;
	
	//页面提交的采购药品明细list
	private List<YycgdmxCustom> yycgdmxs;
	
	//选中行的序号
	private String indexs;
	
	
	
	public List<Dictinfo> getCgztList() {
		return cgztList;
	}

	public void setCgztList(List<Dictinfo> cgztList) {
		this.cgztList = cgztList;
	}

	public String getIndexs() {
		return indexs;
	}

	public void setIndexs(String indexs) {
		this.indexs = indexs;
	}

	public List<YycgdmxCustom> getYycgdmxs() {
		return yycgdmxs;
	}

	public void setYycgdmxs(List<YycgdmxCustom> yycgdmxs) {
		this.yycgdmxs = yycgdmxs;
	}

	public List<Dictinfo> getCgdztlist() {
		return cgdztlist;
	}

	public void setCgdztlist(List<Dictinfo> cgdztlist) {
		this.cgdztlist = cgdztlist;
	}

	//页面提交药品信息id，以逗号分隔的串
	private String ypxxids;
	
	
	public String getYpxxids() {
		return ypxxids;
	}

	public void setYpxxids(String ypxxids) {
		this.ypxxids = ypxxids;
	}

	//药品类别 
	private List<Dictinfo> yplbList;
	
	//交易状态
	private List<Dictinfo> ypjyztList;
	

	public List<Dictinfo> getYplbList() {
		return yplbList;
	}

	public void setYplbList(List<Dictinfo> yplbList) {
		this.yplbList = yplbList;
	}

	public List<Dictinfo> getYpjyztList() {
		return ypjyztList;
	}

	public void setYpjyztList(List<Dictinfo> ypjyztList) {
		this.ypjyztList = ypjyztList;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}

	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}

	public YycgdCustom getYycgdCustom() {
		return yycgdCustom;
	}

	public void setYycgdCustom(YycgdCustom yycgdCustom) {
		this.yycgdCustom = yycgdCustom;
	}

	public YycgdmxCustom getYycgdmxCustom() {
		return yycgdmxCustom;
	}

	public void setYycgdmxCustom(YycgdmxCustom yycgdmxCustom) {
		this.yycgdmxCustom = yycgdmxCustom;
	}
	
	
 }
