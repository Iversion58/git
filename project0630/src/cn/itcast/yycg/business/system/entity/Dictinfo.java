package cn.itcast.yycg.business.system.entity;

// Generated 2015-9-14 10:55:26 by Hibernate Tools 4.0.0

import java.util.HashSet;
import java.util.Set;

/**
 * Dictinfo generated by hbm2java
 */
public class Dictinfo implements java.io.Serializable {

	private String id;
	private String dictcode;
	private String typecode;
	private String info;
	private String remark;
	private String updatetime;
	private Integer dictsort;
	private String isenable;
	private String valuetype;
//	private Set sysUsersForGroupid = new HashSet(0);
//	private Set sysUsersForUserstate = new HashSet(0);

	public Dictinfo() {
	}
/*
	public Dictinfo(String id, String typecode, String info, String isenable) {
		this.id = id;
		this.typecode = typecode;
		this.info = info;
		this.isenable = isenable;
	}

	public Dictinfo(String id, String dictcode, String typecode, String info,
			String remark, String updatetime, Integer dictsort,
			String isenable, String valuetype, Set sysUsersForGroupid,
			Set sysUsersForUserstate) {
		this.id = id;
		this.dictcode = dictcode;
		this.typecode = typecode;
		this.info = info;
		this.remark = remark;
		this.updatetime = updatetime;
		this.dictsort = dictsort;
		this.isenable = isenable;
		this.valuetype = valuetype;
		this.sysUsersForGroupid = sysUsersForGroupid;
		this.sysUsersForUserstate = sysUsersForUserstate;
	}*/

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDictcode() {
		return this.dictcode;
	}

	public void setDictcode(String dictcode) {
		this.dictcode = dictcode;
	}

	public String getTypecode() {
		return this.typecode;
	}

	public void setTypecode(String typecode) {
		this.typecode = typecode;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getUpdatetime() {
		return this.updatetime;
	}

	public void setUpdatetime(String updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getDictsort() {
		return this.dictsort;
	}

	public void setDictsort(Integer dictsort) {
		this.dictsort = dictsort;
	}

	public String getIsenable() {
		return this.isenable;
	}

	public void setIsenable(String isenable) {
		this.isenable = isenable;
	}

	public String getValuetype() {
		return this.valuetype;
	}

	public void setValuetype(String valuetype) {
		this.valuetype = valuetype;
	}

	/*public Set getSysUsersForGroupid() {
		return this.sysUsersForGroupid;
	}

	public void setSysUsersForGroupid(Set sysUsersForGroupid) {
		this.sysUsersForGroupid = sysUsersForGroupid;
	}

	public Set getSysUsersForUserstate() {
		return this.sysUsersForUserstate;
	}

	public void setSysUsersForUserstate(Set sysUsersForUserstate) {
		this.sysUsersForUserstate = sysUsersForUserstate;
	}*/

}