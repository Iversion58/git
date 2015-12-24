package cn.itcast.yycg.business.system.service;

import java.util.List;

import cn.itcast.yycg.base.pojo.Menu;
import cn.itcast.yycg.business.system.entity.Basicinfo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysArea;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysRole;
import cn.itcast.yycg.business.system.entity.Usergysarea;

/**
 * 
 * <p>Title: SystemService</p>
 * <p>Description:系统管理service </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-14
 * @version 1.0
 */
public interface SystemService {
	//根据地区id查询区域信息
	public SysArea findSysAreaById(String id) throws Exception;
	//根据地区id查询供货商供货地区
	public Usergysarea findUsergysareaByAreaid(String areaid) throws Exception;
	//根据id查询basicinfo记录
	public Basicinfo findBasicinfoById(String id)throws Exception;
	//根据typecode查询dictinfo列表
	public List<Dictinfo> findDictinfoListByTypecode(String typecode) throws Exception;
	//根据主键得到dictinfo
	public Dictinfo findDictinfoById(String id) throws Exception;
	//根据业务代码得到数据字典一条记录
	public Dictinfo findDictinfoByDictcodeAndTypecode(String dictcode,String typecode)throws Exception;
	
	//查询系统角色列表
	public List<SysRole> findSysRoleList()throws Exception;
	
	//查询所有权限包括 菜单 和操作url
	public List<SysPermission> findSysPermissionListAll()throws Exception;
	
	//根据角色id查询权限
	public List<SysPermission> findSysPermissionAllListByRoleid(String roleid) throws Exception;
	
	//根据角色id查询权限，只查询操作url（子结点），用于传到页面默认选中checkbox
	public List<SysPermission> findSysPermissionListByRoleid(String roleid) throws Exception;
	
	//角色授权
	public void saveRoleAuthorize(String roleid,String permissionids) throws Exception;
	
	//根据用户id查询用户操作url
	public List<SysPermission> findSysPermissionByUserid(String userid) throws Exception;
	//根据用户id查询用户操作菜单 
	public Menu findSysMenuByUserid(String userid) throws Exception;
}
 