package cn.itcast.yycg.business.system.pojo;

import cn.itcast.yycg.business.system.entity.SysUser;

/**
 * 
 * <p>Title: SysUserCustom</p>
 * <p>Description:用户扩展对象 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
public class SysUserCustom extends SysUser{
	
	//可以扩展用户信息
	//单位名称
	private String sysmc;

	public String getSysmc() {
		return sysmc;
	}

	public void setSysmc(String sysmc) {
		this.sysmc = sysmc;
	}
	
	
}
