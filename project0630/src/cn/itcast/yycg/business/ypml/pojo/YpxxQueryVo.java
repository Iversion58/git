package cn.itcast.yycg.business.ypml.pojo;

import java.io.File;
import java.util.List;

import cn.itcast.yycg.base.pojo.QueryVo;
import cn.itcast.yycg.business.system.entity.Dictinfo;

public class YpxxQueryVo extends QueryVo {

	private YpxxCustom ypxxCustom;

	
	//药品类型
	private List<Dictinfo> yplbList;
	
	//交易状态
	private List<Dictinfo> ypjyztList;
	
	//导入文件
	private File ypxximportfile;
	
	//导入文件名称
	private String ypxximportfileFileName;
	
	
	
	public File getYpxximportfile() {
		return ypxximportfile;
	}

	public void setYpxximportfile(File ypxximportfile) {
		this.ypxximportfile = ypxximportfile;
	}

	public String getYpxximportfileFileName() {
		return ypxximportfileFileName;
	}

	public void setYpxximportfileFileName(String ypxximportfileFileName) {
		this.ypxximportfileFileName = ypxximportfileFileName;
	}

	public YpxxCustom getYpxxCustom() {
		return ypxxCustom;
	}

	public void setYpxxCustom(YpxxCustom ypxxCustom) {
		this.ypxxCustom = ypxxCustom;
	}

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
	
	
}
