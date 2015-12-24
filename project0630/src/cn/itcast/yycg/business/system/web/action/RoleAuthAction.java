package cn.itcast.yycg.business.system.web.action;

import java.text.Normalizer.Form;
import java.util.List;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.web.action.BaseAction;
import cn.itcast.yycg.base.web.result.DataGridResultInfo;
import cn.itcast.yycg.base.web.result.ResultUtil;
import cn.itcast.yycg.business.system.entity.SysPermission;
import cn.itcast.yycg.business.system.entity.SysRole;
import cn.itcast.yycg.business.system.pojo.SysRoleQueryVo;

/**
 * 
 * <p>
 * Title: UserAction
 * </p>
 * <p>
 * Description:角色授权action
 * </p>
 * <p>
 * Company: www.itcast.com
 * </p>
 * 
 * @author 传智.燕青
 * @date 2015-9-13
 * @version 1.0
 */

@Controller
@Scope("prototype")
public class RoleAuthAction extends BaseAction<SysRoleQueryVo> {

	// 角色列表页面
	public String rolelist() throws Exception {

		return "rolelist";
	}

	// 角色列表json结果
	public String rolelist_result() throws Exception {
		// 调用service查询角色列表
		List<SysRole> rolelist = serviceFacade.getSystemService()
				.findSysRoleList();
		// 构造 一个datagridResultInfo
		DataGridResultInfo dataGridResultInfo = ResultUtil
				.createDataGridResultInfo(1, rolelist.size(), rolelist);
		// 将dataGridResultInfo放在值栈值
		this.setProcessResult(dataGridResultInfo);
		return "rolelist_result";
	}
	
	//角色授权页面
	public String roleauthorize()throws Exception{
		SysRoleQueryVo sysRoleQueryVo = getModel();
		//接收页面传入角色id
		String roleid = sysRoleQueryVo.getSysRoleCustom().getId();
		
		//调用service查询角色下的权限，用于在页面存在hidden中，当用户不选择树时候直接提交
		List<SysPermission> sysPermissionsAllByRole = serviceFacade.getSystemService().findSysPermissionAllListByRoleid(roleid);
		String sysPermissionidsAllByRoleid ="";
		//要将权限以逗号分隔方式进行拼接
		for(SysPermission sysPermission:sysPermissionsAllByRole){
			sysPermissionidsAllByRoleid +=sysPermission.getId()+",";
		}
		
		sysRoleQueryVo.setSysPermissionidsAllByRoleid(sysPermissionidsAllByRoleid);
		
		//调用service将角色下的权限url（只包括子结点），用于在页面默认选中结点
		List<SysPermission> sysPermissionsByRole = serviceFacade.getSystemService().findSysPermissionListByRoleid(roleid);
		String sysPermissionidsByRoleid ="";
		//要将权限以逗号分隔方式进行拼接
		for(SysPermission sysPermission:sysPermissionsByRole){
			sysPermissionidsByRoleid +=sysPermission.getId()+",";
		}
		sysRoleQueryVo.setSysPermissionidsByRoleid(sysPermissionidsByRoleid);
		
		return "roleauthorize";
	}
	
	//取出所有结点，以json输出
	public String permissionallByJson()throws Exception{
		SysRoleQueryVo sysRoleQueryVo = getModel();
		//取出所有结点
		List<SysPermission> sysPermissionsAllList = serviceFacade.getSystemService().findSysPermissionListAll();
		sysRoleQueryVo.setSysPermissionsAllList(sysPermissionsAllList);
		return "permissionallByJson";
	}
	
	//角色授权提交
	public String roleauthorizesubmit()throws Exception{
		SysRoleQueryVo sysRoleQueryVo = getModel();
		//得到提交角色id
		String roleid = sysRoleQueryVo.getSysRoleCustom().getId();
		//得到提交的结点
		String sysPermissionIds = sysRoleQueryVo.getSysPermissionIds();
		//调用service
		serviceFacade.getSystemService().saveRoleAuthorize(roleid, sysPermissionIds);
		//提示成功
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "roleauthorizesubmit";
	}
}
