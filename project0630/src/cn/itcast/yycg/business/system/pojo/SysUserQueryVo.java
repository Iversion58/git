package cn.itcast.yycg.business.system.pojo;

import java.util.List;

import cn.itcast.yycg.base.pojo.QueryVo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysUser;

/**
 * 
 * <p>Title: SysUserQueryVo</p>
 * <p>Description: 包装对象</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
public class SysUserQueryVo extends QueryVo {

	//将用户信息包装进来，不推荐直接将entity下的pojo包装进来,推荐定义一个扩展对象
	private SysUserCustom sysUserCustom;
	
	//定义list，用于将数据列表传到页面
	private List<SysUser> sysUserList;
	
	//添加userGroupList方法
	private List<Dictinfo> userGroupList;
	
	
	//可以包装其它对象
	//比如接收订单信息
//	private OrderCustom orderCustom;

	public List<Dictinfo> getUserGroupList() {
		return userGroupList;
	}

	public void setUserGroupList(List<Dictinfo> userGroupList) {
		this.userGroupList = userGroupList;
	}

	public SysUserCustom getSysUserCustom() {
		return sysUserCustom;
	}

	public void setSysUserCustom(SysUserCustom sysUserCustom) {
		this.sysUserCustom = sysUserCustom;
	}

	public List<SysUser> getSysUserList() {
		return sysUserList;
	}

	public void setSysUserList(List<SysUser> sysUserList) {
		this.sysUserList = sysUserList;
	}
	
	
}
