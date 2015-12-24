package cn.itcast.yycg.business.system.entity;

// Generated 2015-9-14 10:55:26 by Hibernate Tools 4.0.0

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * SysUser generated by hbm2java
 */
public class SysUser implements java.io.Serializable {

	private String id;
	private Userjd userjd;
	private String userjdid;//监督单位id
	private Useryy useryy;
	private String useryyid;
	private Usergys usergys;
	private String usergysid;
	private Dictinfo dictinfoByGroupid;
	private String groupid;
	private Dictinfo dictinfoByUserstate;
	private String userstate;
	private String usercode;
	private String username;
	private String pwd;
	private String contact;
	private String addr;
	private String email;
	private String remark;
	private Date createtime;
	private String sex;
	private String phone;
	private String movephone;
	private String fax;
	private String lastupdate;
	private String vchar1;
	private String vchar2;
	private String vchar3;
	private String vchar4;
	private String vchar5;
	
	private Set sysRoles = new HashSet(0);

	public String getUserjdid() {
		return userjdid;
	}

	public void setUserjdid(String userjdid) {
		this.userjdid = userjdid;
	}

	public String getUseryyid() {
		return useryyid;
	}

	public void setUseryyid(String useryyid) {
		this.useryyid = useryyid;
	}



	public String getUsergysid() {
		return usergysid;
	}

	public void setUsergysid(String usergysid) {
		this.usergysid = usergysid;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getUserstate() {
		return userstate;
	}

	public void setUserstate(String userstate) {
		this.userstate = userstate;
	}

	public Set getSysRoles() {
		return sysRoles;
	}

	public void setSysRoles(Set sysRoles) {
		this.sysRoles = sysRoles;
	}

	public SysUser() {
	}

	public SysUser(String id, Dictinfo dictinfoByGroupid,
			Dictinfo dictinfoByUserstate, String usercode, String username,
			String pwd) {
		this.id = id;
		this.dictinfoByGroupid = dictinfoByGroupid;
		this.dictinfoByUserstate = dictinfoByUserstate;
		this.usercode = usercode;
		this.username = username;
		this.pwd = pwd;
	}

	public SysUser(String id, Userjd userjd, Useryy useryy, Usergys usergys,
			Dictinfo dictinfoByGroupid, Dictinfo dictinfoByUserstate,
			String usercode, String username, String pwd, String contact,
			String addr, String email, String remark, Date createtime,
			String sex, String phone, String movephone, String fax,
			String lastupdate, String vchar1, String vchar2, String vchar3,
			String vchar4, String vchar5) {
		this.id = id;
		this.userjd = userjd;
		this.useryy = useryy;
		this.usergys = usergys;
		this.dictinfoByGroupid = dictinfoByGroupid;
		this.dictinfoByUserstate = dictinfoByUserstate;
		this.usercode = usercode;
		this.username = username;
		this.pwd = pwd;
		this.contact = contact;
		this.addr = addr;
		this.email = email;
		this.remark = remark;
		this.createtime = createtime;
		this.sex = sex;
		this.phone = phone;
		this.movephone = movephone;
		this.fax = fax;
		this.lastupdate = lastupdate;
		this.vchar1 = vchar1;
		this.vchar2 = vchar2;
		this.vchar3 = vchar3;
		this.vchar4 = vchar4;
		this.vchar5 = vchar5;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Userjd getUserjd() {
		return this.userjd;
	}

	public void setUserjd(Userjd userjd) {
		this.userjd = userjd;
	}

	public Useryy getUseryy() {
		return this.useryy;
	}

	public void setUseryy(Useryy useryy) {
		this.useryy = useryy;
	}

	public Usergys getUsergys() {
		return this.usergys;
	}

	public void setUsergys(Usergys usergys) {
		this.usergys = usergys;
	}

	public Dictinfo getDictinfoByGroupid() {
		return this.dictinfoByGroupid;
	}

	public void setDictinfoByGroupid(Dictinfo dictinfoByGroupid) {
		this.dictinfoByGroupid = dictinfoByGroupid;
	}

	public Dictinfo getDictinfoByUserstate() {
		return this.dictinfoByUserstate;
	}

	public void setDictinfoByUserstate(Dictinfo dictinfoByUserstate) {
		this.dictinfoByUserstate = dictinfoByUserstate;
	}

	public String getUsercode() {
		return this.usercode;
	}

	public void setUsercode(String usercode) {
		this.usercode = usercode;
	}

	public String getUsername() {
		return this.username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return this.pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddr() {
		return this.addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Date getCreatetime() {
		return this.createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getSex() {
		return this.sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getMovephone() {
		return this.movephone;
	}

	public void setMovephone(String movephone) {
		this.movephone = movephone;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getLastupdate() {
		return this.lastupdate;
	}

	public void setLastupdate(String lastupdate) {
		this.lastupdate = lastupdate;
	}

	public String getVchar1() {
		return this.vchar1;
	}

	public void setVchar1(String vchar1) {
		this.vchar1 = vchar1;
	}

	public String getVchar2() {
		return this.vchar2;
	}

	public void setVchar2(String vchar2) {
		this.vchar2 = vchar2;
	}

	public String getVchar3() {
		return this.vchar3;
	}

	public void setVchar3(String vchar3) {
		this.vchar3 = vchar3;
	}

	public String getVchar4() {
		return this.vchar4;
	}

	public void setVchar4(String vchar4) {
		this.vchar4 = vchar4;
	}

	public String getVchar5() {
		return this.vchar5;
	}

	public void setVchar5(String vchar5) {
		this.vchar5 = vchar5;
	}

}
