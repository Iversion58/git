package cn.itcast.yycg.business.system.service.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import com.sun.org.apache.regexp.internal.recompile;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.base.pojo.Menu;
import cn.itcast.yycg.base.service.BaseService;
import cn.itcast.yycg.base.service.ServiceFacade;
import cn.itcast.yycg.business.system.entity.Basicinfo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysArea;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysRole;
import cn.itcast.yycg.business.system.entity.SysUser;
import cn.itcast.yycg.business.system.entity.Usergysarea;
import cn.itcast.yycg.business.system.service.SystemService;

@Service
public class SystemServiceImpl extends BaseService implements SystemService {
	
	//注入dictinfoDao
	@Resource(name="dictinfoDao")
	private BaseDao<Dictinfo> dictinfoDao;
	
	//注入sysRoleDao
	@Resource(name="sysRoleDao")
	private BaseDao<SysRole> sysRoleDao;
	
	//注入sysPermissionDao
	@Resource(name="sysPermissionDao")
	private BaseDao<SysPermission> sysPermissionDao;
	
	//注入basicinfoDao
	@Resource(name="basicinfoDao")
	private BaseDao<Basicinfo> basicinfoDao;
	
	@Resource(name="sysAreaDao")
	private BaseDao<SysArea> sysAreaDao;
	
	@Resource(name="usergysareaDao")
	private BaseDao<Usergysarea> usergysareaDao;
	
	//根据地区id查询供货商供货地区
	public Usergysarea findUsergysareaByAreaid(String areaid) throws Exception{
		return usergysareaDao.findById(areaid);
	}

	//根据地区id查询区域信息
	public SysArea findSysAreaById(String id) throws Exception{
		return sysAreaDao.findById(id);
	}
	//根据id查询basicinfo记录
	public Basicinfo findBasicinfoById(String id)throws Exception{
		return basicinfoDao.findById(id);
	}
	@Override
	public List<Dictinfo> findDictinfoListByTypecode(String typecode)
			throws Exception {
		DetachedCriteria detachedCriteria = dictinfoDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("typecode", typecode));
		//调用dictinfoDao查询
		return dictinfoDao.findListByCriteria(detachedCriteria);
	}

	@Override
	public Dictinfo findDictinfoById(String id) throws Exception {
		
		return dictinfoDao.findById(id);
	}

	@Override
	public Dictinfo findDictinfoByDictcodeAndTypecode(String dictcode,
			String typecode) throws Exception {
		DetachedCriteria detachedCriteria = dictinfoDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("dictcode", dictcode));
		detachedCriteria.add(Restrictions.eq("typecode", typecode));
		return dictinfoDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public List<SysRole> findSysRoleList() throws Exception {
		return sysRoleDao.findListByCriteria(sysRoleDao.createCriteria());
	}

	@Override
	public List<SysPermission> findSysPermissionListAll() throws Exception {
		DetachedCriteria detachedCriteria = sysPermissionDao.createCriteria();
		//按照showorder字段进行排序
		detachedCriteria.addOrder(Order.asc("showorder"));
		return sysPermissionDao.findListByCriteria(detachedCriteria);
		
	}

	@Override
	public List<SysPermission> findSysPermissionAllListByRoleid(String roleid)
			throws Exception {
		//根据角色id查询出角色对象
		SysRole sysRole = sysRoleDao.findById(roleid);
		
		//调用角色对象的方法获取角色下的权限
		//角色下的菜单 
		Set sysRoleMenus = sysRole.getSysRoleMenus();
		//角色下的操作url
		Set sysRolePermissions = sysRole.getSysRolePermissions();
		
		List<SysPermission> sysPermissionlist = new ArrayList<SysPermission>();
		sysPermissionlist.addAll(sysRoleMenus);
		sysPermissionlist.addAll(sysRolePermissions);
		return sysPermissionlist;
	}

	@Override
	public List<SysPermission> findSysPermissionListByRoleid(String roleid)
			throws Exception {
		//根据角色id查询出角色对象
		SysRole sysRole = sysRoleDao.findById(roleid);
		
		//调用角色对象的方法获取角色下的权限
		//角色下的操作url
		Set sysRolePermissions = sysRole.getSysRolePermissions();
		
		List<SysPermission> sysPermissionlist = new ArrayList<SysPermission>();
		sysPermissionlist.addAll(sysRolePermissions);
		return sysPermissionlist;
	}

	@Override
	public void saveRoleAuthorize(String roleid, String permissionids)
			throws Exception {
		//根据roleid查询出角色对象
		SysRole sysRole = sysRoleDao.findById(roleid);
		
		//根据角色删除sys_role_menu和sys_role_permission
		sysRole.getSysRoleMenus().clear();
		sysRole.getSysRolePermissions().clear();
		
		if(permissionids!=null){
			//遍历结点进行添加
			String[] permissionidsArray = permissionids.split(",");
			for(String permissionid:permissionidsArray){
				//根据权限id查询权限对象
				SysPermission sysPermission = sysPermissionDao.findById(permissionid);
				String ismenu = sysPermission.getIsmenu();
				//判断是否菜单 
				if(ismenu.equals("1")){
					//如果是菜单 ，向sys_role_menu中添加记录
					sysRole.getSysRoleMenus().add(sysPermission);
				}else{
					sysRole.getSysRolePermissions().add(sysPermission);
				}
			}
		}
		
		
	}
	public List<SysPermission> findSysPermissionByUserid(String userid) throws Exception{
		
		//根据用户id查询出用户
		SysUser sysUser = serviceFacade.getUserService().findSysUserById(userid);
		
		//取出用户的角色
		Set sysRolesByUserid = sysUser.getSysRoles();
		
		//操作url集合
		List<SysPermission> sysPermissionList = new ArrayList<SysPermission>();
		
		//根据角色查询角色下分配操作url
		Iterator iterator = sysRolesByUserid.iterator();
		while(iterator.hasNext()){
			//角色
			SysRole sysRole = (SysRole) iterator.next();
			//取出角色下的操作url
			Set sysRolePermissions = sysRole.getSysRolePermissions();
			sysPermissionList.addAll(sysRolePermissions);
		}
		
		return sysPermissionList;
	}

	@Override
	public Menu findSysMenuByUserid(String userid) throws Exception {
		
		//根据用户id查询出用户
	    SysUser sysUser = serviceFacade.getUserService().findSysUserById(userid);
	    //菜单 set
	    //定义treeSet保证菜单 的唯一和按一定的顺序排序
	    TreeSet<SysPermission> menuSets = new TreeSet<SysPermission>(new Comparator<SysPermission>() {

			@Override
			public int compare(SysPermission o1, SysPermission o2) {
				//按升序sysPermission中showorder字段升序
				return o1.getShoworder().compareTo(o2.getShoworder());
				//如果按降序
				//return o2.getShoworder().compareTo(o1.getShoworder());
			}
		});
		//取出用户的角色
		Set sysRolesByUserid = sysUser.getSysRoles();
		//根据角色查询角色下分配操作菜单 
		Iterator iterator = sysRolesByUserid.iterator();
		while(iterator.hasNext()){
			//角色
			SysRole sysRole = (SysRole) iterator.next();
			//取出角色下的操作菜单 
			Set sysMenus = sysRole.getSysRoleMenus();
			//将所有角色下的操作菜单 合并在treeset中 
			menuSets.addAll(sysMenus);
		}
		
		
		Menu menu =new Menu();
		//定义一级菜单 list
		List<Menu> firstMenu = new ArrayList<Menu>();
		//定义二级菜单list
		List<Menu> secondMenu = null;
		//定义一个标记一级菜单 
		String firstMenu_id = "";
		//对所有菜单 （包括一级和二级）进行遍历
		Iterator<SysPermission> iterator2 = menuSets.iterator();
		Menu firstMenuObj = null;//一级菜单 对象
		Menu secondMenuObj = null;//二级菜单 对象
		while(iterator2.hasNext()){
			SysPermission sysPermission_l = iterator2.next();
			//在这里进行遍历，逐个将一级菜单 添加到firstMenu中，如果是二级菜单 在firstMenu中添加二级菜单 
			String plevel = sysPermission_l.getPlevel();//菜单 等级，1表示一级菜单 ，2表示二级菜单 
			String menuid = sysPermission_l.getId();//菜单 id
			if(plevel.equals("1") && !menuid.equals(firstMenu_id) ){
				firstMenu_id = menuid;//打上标记
				firstMenuObj =  new Menu();
				firstMenuObj.setMenuid(menuid);
				firstMenuObj.setMenuname(sysPermission_l.getName());
				firstMenuObj.setUrl(sysPermission_l.getUrl());
				firstMenu.add(firstMenuObj);
				
				//向一级菜单中添加二级菜单列表
				secondMenu = new ArrayList<Menu>();
				firstMenuObj.setMenus(secondMenu);
			}
			if(plevel.equals("2")){
				secondMenuObj = new Menu();
				secondMenuObj.setMenuid(menuid);
				secondMenuObj.setMenuname(sysPermission_l.getName());
				secondMenuObj.setUrl(sysPermission_l.getUrl());
				//将二级菜单添加到二级菜单的list中
				secondMenu.add(secondMenuObj);
			}
			
		}
		//将一级菜单放在menu对象中
		menu.setMenus(firstMenu);
		
		
		return menu;
	}

}
