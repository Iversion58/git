package cn.itcast.yycg.business.system.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.base.pojo.Menu;
import cn.itcast.yycg.base.service.BaseService;
import cn.itcast.yycg.base.web.result.ExceptionResultInfo;
import cn.itcast.yycg.base.web.result.ResultUtil;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysRole;
import cn.itcast.yycg.business.system.entity.SysUser;
import cn.itcast.yycg.business.system.entity.Usergys;
import cn.itcast.yycg.business.system.entity.Userjd;
import cn.itcast.yycg.business.system.entity.Useryy;
import cn.itcast.yycg.business.system.pojo.SysUserCustom;
import cn.itcast.yycg.business.system.pojo.SysUserQueryVo;
import cn.itcast.yycg.business.system.service.UserService;
import cn.itcast.yycg.util.MD5;

@Service
public class UserServiceImpl extends BaseService implements UserService {
	
	//注入dao
	@Resource(name="sysUserDao")
	private BaseDao<SysUser> sysUserDao;
	
	@Resource(name="userjdDao")
	private BaseDao<Userjd> userjdDao;
	
	@Resource(name="useryyDao")
	private BaseDao<Useryy> useryyDao;
	
	@Resource(name="usergysDao")
	private BaseDao<Usergys> usergysDao;
	
	@Resource(name="sysRoleDao")
	private BaseDao<SysRole> sysRoleDao;

	@Override
	public SysUser findSysUserById(String id) throws Exception {
		return sysUserDao.findById(id);
	}

	@Override
	public void saveSysUser() throws Exception {
		//spring通过aop进行事务，在方法执行之前要创建session会话，在会话中开启事务
		
		//调用一个dao方法，使用session会话
		SysUser sysUser = sysUserDao.findById("202");
		sysUser.setUsername("JJ镇平庄村卫生室 test");
		sysUserDao.update(sysUser);
		//调用一个dao方法，使用相同 的session会话
		
		//在执行service方法之间有异常抛出，spring进行事务控制会事务回滚，如果回滚上边的更新是不成功的
		int i=1/0;
		//所有dao方法使用一个session会话，保证两个方法在一个事务中
		

	}

	//用户列表查询条件拼接方法
	private void findSysUserCondition(DetachedCriteria detachedCriteria,SysUserQueryVo sysUserQueryVo)throws Exception {
		//在这里拼接查询条件
		if(sysUserQueryVo!=null){
			//获取页面的查询条件
			SysUserCustom sysUserCustom = sysUserQueryVo.getSysUserCustom();
			if(sysUserCustom!=null){
				
				//如果输入usercode，拼接usercode的条件
				if(sysUserCustom.getUsercode()!=null ){
					detachedCriteria.add(Restrictions.eq("usercode", sysUserCustom.getUsercode()));
				}
				//拼接用户名称
				if(sysUserCustom.getUsername()!=null){
					//用户名称使用模糊查询
					detachedCriteria.add(Restrictions.like("username", "%"+sysUserCustom.getUsername()+"%"));
				}
			}
		}
	}
	@Override
	public List<SysUser> findSysUserList(SysUserQueryVo sysUserQueryVo,
			int firstResult, int maxResults) throws Exception {
		
		DetachedCriteria detachedCriteria = sysUserDao.createCriteria();
		
		//解析sysUserQueryVo的数据拼接查询条件
		findSysUserCondition(detachedCriteria,sysUserQueryVo);
		
		return sysUserDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public long findSysUserCount(SysUserQueryVo sysUserQueryVo)
			throws Exception {
		DetachedCriteria detachedCriteria = sysUserDao.createCriteria();
		
		//解析sysUserQueryVo的数据拼接查询条件
		findSysUserCondition(detachedCriteria,sysUserQueryVo);
		
		return sysUserDao.findListCountByCriteria(detachedCriteria);
	}

	@Override
	public void insertSysUser(SysUserCustom sysUserCustom) throws Exception {
		
		//将sysUserCustom扩展对象转成entity(hibernate映射文件中类型)
		SysUser sysUser = new SysUser();
		BeanUtils.copyProperties(sysUserCustom, sysUser);
		
		//校验账号是重复
		//思路根据账号查询sys_user，如果有表示重复
		SysUser sysUser_1 = this.findSysUserByUsercode(sysUserCustom.getUsercode());
		if(sysUser_1!=null){//说明账号在数据库中存在了
			//抛出一个异常
//			throw new ExceptionResultInfo(resultInfo);
			//上边的代码使用工具类
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 115, null));
		}
		
		//针对sysUserCustom的信息进行处理
		//对密码 进行加密
		//页面输入密码 (明文)
		String pwd = sysUserCustom.getPwd();
		//加密后就是密文
		String pwd_md5 = new MD5().getMD5ofStr(pwd);
		sysUser.setPwd(pwd_md5);
		
		//得到输入用户类型（数据字典id）
		//用户类型外键id，比如：s0103
		String groupid_dictinfoid = sysUserCustom.getGroupid();
		sysUser.setGroupid(groupid_dictinfoid);
		
		
		//根据字典id得到用户类型业务代码
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoById(groupid_dictinfoid);
		//用户类型的代码  0:系统管理员,1：卫生局 2:卫生院 3：卫生室 4:供货商
		String groupid = dictinfo.getDictcode();
		
		//根据输入单位名称得到单位id
		String useryyid = null;
		String userjdid = null;
		String usergysid = null;
		
		//从输入数据中获取单位名称
		String sysmc = sysUserCustom.getSysmc();
		//如果groupid等于0表示系统管理员 不用根据单位名称查询单位id
		if(groupid.equals("1") || groupid.equals("2")){
			//从监督单位查询
			Userjd userjd = this.findUserjdByMc(sysmc);
			//得到单位id（即sys_user表的外键）
			userjdid = userjd.getId();
		}else if(groupid.equals("3")){
			//从医院表查询
			Useryy useryy = this.findUseryyByMc(sysmc);
			useryyid = useryy.getId();
		}else if(groupid.equals("4")){
			//从供货商 查询
			Usergys usergys = this.findUsergysByMc(sysmc);
			usergysid = usergys.getId();
		}
		

		
		//如果用户是医院，设置到useryyid中
		//判断时候要使用业务代码
		if(groupid.equals("1")){//卫生局
			sysUser.setUserjdid(userjdid);
		}else if(groupid.equals("2")){//卫生院
			sysUser.setUserjdid(userjdid);
		}else if(groupid.equals("3")){//卫生室 
			sysUser.setUseryyid(useryyid);
		}else if(groupid.equals("4")){//供货商
			sysUser.setUsergysid(usergysid);
		}
		//调用sysUserDao插入用户
		sysUserDao.insert(sysUser);
		//此时sysUser为持久态
		//=============在这里给用户授权===================
		//向sys_user_role表添加一条记录（记录了用户和角色关系）
		String userid = sysUser.getId();//用户id
		String roleid = sysUser.getGroupid();//角色id即用户类型
		//根据角色id查询角色信息
		SysRole sysRole = sysRoleDao.findById(roleid);
		
		//向sys_user_role表添加一条记录
		sysUser.getSysRoles().add(sysRole);
		//spring事务控制，在方法结束自动调用session的commit方法。
	}

	@Override
	public Userjd findUserjdByMc(String mc) throws Exception {
		DetachedCriteria detachedCriteria = userjdDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return userjdDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public Useryy findUseryyByMc(String mc) throws Exception {
		DetachedCriteria detachedCriteria = useryyDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return useryyDao.findSingleObjByCriteria(detachedCriteria);
	}

	@Override
	public Usergys findUsergysByMc(String mc) throws Exception {
		DetachedCriteria detachedCriteria = usergysDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("mc", mc));
		return usergysDao.findSingleObjByCriteria(detachedCriteria);
	}

	//根据乡镇 级别的区域查询下边的卫生室 
	public List<Useryy> findUseryyByAreaid(String areaid) throws Exception{
		
		DetachedCriteria detachedCriteria = useryyDao.createCriteria();
		//拼接sql select * from useryy where dq like '1.1.%'
		detachedCriteria.add(Restrictions.like("dq", areaid+"%"));
		
		return useryyDao.findListByCriteria(detachedCriteria);
		
	}
		
	@Override
	public SysUser findSysUserByUsercode(String usercode) throws Exception {
		DetachedCriteria detachedCriteria = sysUserDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("usercode", usercode));
		return sysUserDao.findSingleObjByCriteria(detachedCriteria);
	}
	
	//根据 单位id查询单位 信息
	public Useryy findUseryyById(String id) throws Exception{
		return useryyDao.findById(id);
	}
	public Usergys findUsergysById(String id) throws Exception{
		return usergysDao.findById(id);
		
	}
	public Userjd findUserjdById(String id) throws Exception{
		return userjdDao.findById(id);
		
	}

	@Override
	public void deleteSysUser(String id) throws Exception {
		//根据id查询用户信息
		SysUser sysUser = sysUserDao.findById(id);
		
		//判断如果删除的不用户不存在，提示用户
		if(sysUser==null){
			//抛出异常
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 212, null));
		}
		
		sysUserDao.delete(sysUser);
		
	}
	
	

	@Override
	public ActiveUser checkSysUser(String usercode, String password)
			throws Exception {
		
		// 在service中统一在最前边对关键参数进行校验
		if(usercode == null || password == null){
			//...提示参数错误，抛出异常
			//...
		}
		
		//根据账号查询用户
		SysUser sysUser = this.findSysUserByUsercode(usercode);
		if(sysUser == null){
			//抛出异常（用户不存在）
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 101, null));
		}
		//校验密码
		//数据库中存在正确密码，是md5
		String password_md5 = sysUser.getPwd();
		//页面密码加密
		String password_md5_page=new MD5().getMD5ofStr(password);
		if(!password_md5.equalsIgnoreCase(password_md5_page)){
			//抛出异常（用户不存在）
			ResultUtil.throwExcepion(ResultUtil.createFail(Config.MESSAGE, 114, null));
		}
		
		//构造 一个ActiveUser身份信息
		ActiveUser activeUser = new ActiveUser();
		activeUser.setId(sysUser.getId());//用户的id主键，不是账号
		activeUser.setUsercode(sysUser.getUsercode());
		activeUser.setUsername(sysUser.getUsername());
		activeUser.setGroupid(sysUser.getGroupid());//用户类型id，对应dictinfo字典表的id
		activeUser.setGroupname(sysUser.getDictinfoByGroupid().getInfo());
		
		// 根据用户id查询用户授权的操作url
		List<SysPermission> sysPermissionsByUserid = serviceFacade
				.getSystemService().findSysPermissionByUserid(sysUser.getId());
	//将用户的操作url（权限）存储在session的对象中
		activeUser.setSysPermissionList(sysPermissionsByUserid);
		
		//根据用户id查询用户操作菜单 
		Menu menu = serviceFacade.getSystemService().findSysMenuByUserid(sysUser.getId());
		activeUser.setMenu(menu);
		
		return activeUser;
	}

}
