package cn.itcast.yycg.business.system.pojo;

import java.util.List;

import cn.itcast.yycg.base.pojo.QueryVo;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysRole;

/**
 * 
 * <p>Title: SysRoleQueryVo</p>
 * <p>Description: 角色授权包装对象</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-17
 * @version 1.0
 */
public class SysRoleQueryVo extends QueryVo {
	
	//角色下的权限id
	private String sysPermissionidsAllByRoleid;
	
	//角色下的权限url
	private String sysPermissionidsByRoleid;
	
	//所有权限结点
	private List<SysPermission> sysPermissionsAllList;
	
	//页面提交的权限结点id
	private String sysPermissionIds;
		

	public String getSysPermissionIds() {
		return sysPermissionIds;
	}

	public void setSysPermissionIds(String sysPermissionIds) {
		this.sysPermissionIds = sysPermissionIds;
	}

	public List<SysPermission> getSysPermissionsAllList() {
		return sysPermissionsAllList;
	}

	public void setSysPermissionsAllList(List<SysPermission> sysPermissionsAllList) {
		this.sysPermissionsAllList = sysPermissionsAllList;
	}

	public String getSysPermissionidsAllByRoleid() {
		return sysPermissionidsAllByRoleid;
	}

	public void setSysPermissionidsAllByRoleid(String sysPermissionidsAllByRoleid) {
		this.sysPermissionidsAllByRoleid = sysPermissionidsAllByRoleid;
	}

	public String getSysPermissionidsByRoleid() {
		return sysPermissionidsByRoleid;
	}

	public void setSysPermissionidsByRoleid(String sysPermissionidsByRoleid) {
		this.sysPermissionidsByRoleid = sysPermissionidsByRoleid;
	}

	private SysRoleCustom sysRoleCustom;

	public SysRoleCustom getSysRoleCustom() {
		return sysRoleCustom;
	}

	public void setSysRoleCustom(SysRoleCustom sysRoleCustom) {
		this.sysRoleCustom = sysRoleCustom;
	}
	
	
}
