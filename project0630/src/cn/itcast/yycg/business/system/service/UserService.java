package cn.itcast.yycg.business.system.service;

import java.util.List;

import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.business.system.entity.SysUser;
import cn.itcast.yycg.business.system.entity.Usergys;
import cn.itcast.yycg.business.system.entity.Userjd;
import cn.itcast.yycg.business.system.entity.Useryy;
import cn.itcast.yycg.business.system.pojo.SysUserCustom;
import cn.itcast.yycg.business.system.pojo.SysUserQueryVo;

/**
 * 
 * <p>Title: UserService</p>
 * <p>Description: 测试service</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
public interface UserService {
	
	//要根据业务需求定义service接口，service接口由action调用 
	//根据用户id查询用户信息
	public SysUser findSysUserById(String id) throws Exception;
	
	//测试事务控制
	public void saveSysUser()throws Exception;
	
	//用户列表分页查询
	/**
	 * 
	 * <p>
	 * Title: findSysUserList
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param sysUserQueryVo
	 *            输入查询条件
	 * @param firstResult
	 *            查询列表开始下标
	 * @param maxResults
	 *            每页显示个数
	 * @return 查询结果集
	 * @throws Exception
	 */
	public List<SysUser> findSysUserList(SysUserQueryVo sysUserQueryVo,
			int firstResult, int maxResults) throws Exception;

	// 用户列表结果集总数
	public long findSysUserCount(SysUserQueryVo sysUserQueryVo)
			throws Exception;
	
	//根据用户账号查询用户信息
	public SysUser findSysUserByUsercode(String usercode)throws Exception;
	//根据单位名称查询监督单位
	public Userjd findUserjdByMc(String mc) throws Exception;
	//根据单位名称查询医院
	public Useryy findUseryyByMc(String mc) throws Exception;
	//根据单位名称查询供货商
	public Usergys findUsergysByMc(String mc) throws Exception;
	
	//根据乡镇 级别的区域查询下边的卫生室 
	public List<Useryy> findUseryyByAreaid(String areaid) throws Exception;
	
	//根据 单位id查询单位 信息
	public Useryy findUseryyById(String id) throws Exception;
	public Usergys findUsergysById(String id) throws Exception;
	public Userjd findUserjdById(String id) throws Exception;
	//添加用户信息
	//建议添加用户使用扩展对象SysUserCustom
	public void insertSysUser(SysUserCustom sysUserCustom)throws Exception;
	
	//根据用户id删除用户信息
	public void deleteSysUser(String id) throws Exception;

	
	//认证方法
	public ActiveUser checkSysUser(String usercode,String password)throws Exception;
	
	

}
