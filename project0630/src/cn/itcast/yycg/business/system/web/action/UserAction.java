package cn.itcast.yycg.business.system.web.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.annotation.ActionModelName;
import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.pojo.PageParameter;
import cn.itcast.yycg.base.web.action.BaseAction;
import cn.itcast.yycg.base.web.result.DataGridResultInfo;
import cn.itcast.yycg.base.web.result.ResultInfo;
import cn.itcast.yycg.base.web.result.ResultUtil;
import cn.itcast.yycg.base.web.result.SubmitResultInfo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysUser;
import cn.itcast.yycg.business.system.pojo.SysUserCustom;
import cn.itcast.yycg.business.system.pojo.SysUserQueryVo;

/**
 * 
 * <p>Title: UserAction</p>
 * <p>Description:用户管理action </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */

//实现ModelDriven不用将请求的参数全部以成员变量方式定义在action中
//@ActionModelName("cn.itcast.yycg.business.system.pojo.SysUserQueryVo")
@Controller
@Scope("prototype")
public class UserAction  extends BaseAction<SysUserQueryVo> {

	//action中统一使用包装对象作为模型对象，作用是接收请求参数，将结果转到页面
//	private SysUserQueryVo sysUserQueryVo;

	private String name;
	
	private SysUser user;
	
	//根据用户 id查询用户信息
	public String queryuser()throws Exception{
		
		//可以将object转型为具体 的queryVo的代码封装到baseAction中。
//		SysUserQueryVo sysUserQueryVo = (SysUserQueryVo) this.getModel();
		
		SysUserQueryVo sysUserQueryVo = this.getModel();
		//从model对象中获取请求参数用户id
		String id = sysUserQueryVo.getSysUserCustom().getId();
		//调用service查询用户信息
		SysUser sysUser = serviceFacade.getUserService().findSysUserById(id);
		SysUserCustom sysUserCustom = new SysUserCustom();
		//将sysUser的全部属性拷贝到sysUserCustom中
		BeanUtils.copyProperties(sysUser, sysUserCustom);
		
		sysUserQueryVo.setSysUserCustom(sysUserCustom);
		//通过sysUserQueryVo将查询到用户信息传到页面
		//调用sysUserQueryVo的setSysUserCustom方法,
		//将sysUser对象信息在页面展示，将sysUser放在request域 中
//		request.setAttribute("sysUser", sysUser);

		
		return "queryuser";
	}

	//此方法由setModelInterceptor调用
//	@Override
//	public void initQueryVo() {
//		sysUserQueryVo = (SysUserQueryVo) this.getModel();
//	}

	private SysUserQueryVo sysUserQueryVo;
	
	private Map<String, Object> datagrid =new HashMap<String, Object>();
	
	
	
	public Map<String, Object> getDatagrid() {
		return datagrid;
	}
	public SysUserQueryVo getSysUserQueryVo() {
		return sysUserQueryVo;
	}
	//测试json输出方法
	public String outjson()throws Exception{
		//请求参数到sysUserQueryVo中sysUserCustom对象中
		
		sysUserQueryVo = getModel();
		
		List<SysUser> list = new ArrayList<SysUser>();
		
		SysUser sysUser = new SysUser();
		sysUser.setUsername("张三");
		list.add(sysUser);
		
		sysUserQueryVo.setSysUserList(list);
		
		datagrid.put("total", 100);
		datagrid.put("rows", list);
		
		//将sysUserCustom对象输出json
		return "outjson";
	}
	//查询用户列表页面
	@RequiresPermissions("user:list")
	public String sysuserlist()throws Exception{

		SysUserQueryVo sysUserQueryVo = getModel();
		//调用serivce查询一些页面所用到初始数据
		//用户类型
		List<Dictinfo> userGroupList = serviceFacade.getSystemService().findDictinfoListByTypecode("s01");
		//将userGroupList设置到QueryVo中
		sysUserQueryVo.setUserGroupList(userGroupList);
		
		return "sysuserlist";
	}
	
	//查询用户列表，返回json结果集
	@RequiresPermissions("user:list")
	public String sysuserlist_result()throws Exception{
		//拼接页面传入查询条件
		SysUserQueryVo sysUserQueryVo = getModel();
		//调用service先查询符合条件 记录总数
		long total = serviceFacade.getUserService().findSysUserCount(sysUserQueryVo);
		
		//datagrid 分页查询时传入两个参数page当前页码，rows每页显示个数
		int page = sysUserQueryVo.getPage();
		int rows = sysUserQueryVo.getRows();
		//计算分页下标
		PageParameter pageParameter = new PageParameter(page, rows, total);
		int firstResult = pageParameter.getPageQuery_star();
		int maxResults = rows;
		//分页查询符合条件结果集
		List<SysUser> list = serviceFacade.getUserService().findSysUserList(sysUserQueryVo, firstResult, maxResults);
		//将list返回json，使用struts的json插件将list转为json输出
		//struts的json插件原理，在struts.xml中配置将传到页面的哪个pojo对象转json
		//解决将list放在sysUserQueryVo
//		sysUserQueryVo.setSysUserList(list);
		
		//输出json格式符合datagrid的要求，包括两个key：total，rows
//		datagrid.put("total", total);
//		datagrid.put("rows", list);
		
		//使用统一的结果类型DatagridResultInfo
//		DataGridResultInfo dataGridResultInfo = new DataGridResultInfo();
//		dataGridResultInfo.setTotal(total);
//		dataGridResultInfo.setRows(list);
		//以上三行代码使用工具类
		DataGridResultInfo dataGridResultInfo = ResultUtil.createDataGridResultInfo(page, total, list);
		//将dataGridResultInfo设置到值栈中
		this.setProcessResult(dataGridResultInfo);
		
		return "sysuserlist_result";
	}
	
	//用户添加页面
	@RequiresPermissions("user:add")
	public String insert()throws Exception{
		
		SysUserQueryVo sysUserQueryVo = getModel();
		//准备页面需要数据
		//从数据字典查询用户类型
		List<Dictinfo> userGroupList = serviceFacade.getSystemService().findDictinfoListByTypecode("s01");
		//将userGroupList设置到模型对象中
		sysUserQueryVo.setUserGroupList(userGroupList);
		
		return "insert";
	}
	
	//提交结果
//	private Map<String, Object> processResult = new HashMap<String,Object>();
//	
//	public Map<String, Object> getProcessResult() {
//		return processResult;
//	}
	//用户添加提交
	@RequiresPermissions("user:add")
	public String insertsubmit()throws Exception{ 
		//接收提交用户信息
		SysUserQueryVo sysUserQueryVo = getModel();
		serviceFacade.getUserService().insertSysUser(sysUserQueryVo.getSysUserCustom());
		
		//系统统一将提交结果信息转成json在页面输出
		//构造 一个结果信息，至少两个属性：提交结果类型（成功、失败），提交结果信息(如果失败要有失败信息，如果成功就是成功提示)
		//可以使用map
		//操作成功
//		processResult.put("type", "1");//1表示成功
//		processResult.put("message", "操作成功！");
		
		//使用统一提交结果类型
//		ResultInfo resultInfo = new ResultInfo();
//		resultInfo.setType(ResultInfo.TYPE_RESULT_SUCCESS);//设置为操作成功
//		resultInfo.setMessage("操作成功！");//属于编码，不利于系统维护
//		SubmitResultInfo submitResultInfo = new SubmitResultInfo(resultInfo);
		
		//将上边的四行代码改为使用ResultUtil创建，从资源文件中读取提示信息
		//创建一条成功的提示信息
		SubmitResultInfo submitResultInfo = ResultUtil
				.createSubmitResult(ResultUtil.createSuccess(
						Config.MESSAGE, 906, null));

		//将submitResultInfo放入值栈，将来要转json输出
		this.setProcessResult(submitResultInfo);
		
		return "insertsubmit";
	}
	
	//删除用户
	public String delete()throws Exception{
		SysUserQueryVo sysUserQueryVo = getModel();
		serviceFacade.getUserService().deleteSysUser(sysUserQueryVo.getSysUserCustom().getId());
		//返回操作成功
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "delete";
	}

}
