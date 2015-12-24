package cn.itcast.yycg.business.cgd.web.action;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.pojo.ActiveUser;
import cn.itcast.yycg.base.pojo.PageParameter;
import cn.itcast.yycg.base.web.action.BaseAction;
import cn.itcast.yycg.base.web.result.ResultInfo;
import cn.itcast.yycg.base.web.result.ResultUtil;
import cn.itcast.yycg.business.cgd.entity.Yycgd;
import cn.itcast.yycg.business.cgd.entity.Yycgdmx;
import cn.itcast.yycg.business.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.business.cgd.pojo.YycgdQueryVo;
import cn.itcast.yycg.business.cgd.pojo.YycgdmxCustom;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.ypml.entity.Ypxx;

@Controller
@Scope("prototype")
public class CgdAction extends BaseAction<YycgdQueryVo> {
	
	//创建采购单基本信息
	public String create(){
		
		//生成一个默认的采购单名称（卫生室名称+当前日期+“采购单”）
		//String yycgdname="";
		return "create";
	}
	//创建采购单基本信息提交，此方法只有医院用户执行
	public String createsubmit() throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		
		//useryyid医院id从当前用户身份信息中获取
		//当前操作用户
		Subject subject = SecurityUtils.getSubject();
		//当前操作用户的身份信息
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//医院id
		String useryyid = activeUser.getSysid();
		Long yycgdid = serviceFacade.getCgdService().insertYycgd(useryyid, yycgdQueryVo.getYycgdCustom());
		
		//通过submitResultInfo中的ReaultInfo中sysdata属性将采购单id传到页面
		ResultInfo resultInfo = ResultUtil.createSuccess(Config.MESSAGE, 906, null);
		resultInfo.getSysdata().put("yycgdid", yycgdid);
		
		//操作成功
		this.setProcessResult(ResultUtil.createSubmitResult(resultInfo));
		
		
		
		return "createsubmit";
	}
	
	//修改采购单页面，将页面中需要的数据查询到
	public String edit()throws Exception{
		
		YycgdQueryVo yycgdQueryVo = getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		
		Long yycgdid = yycgdCustom.getId();
		
		//需要在页面显示采购单基本信息
		Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);

		//将yycgd对象放入值栈
		YycgdCustom yycgdCustom_page = new YycgdCustom();
		BeanUtils.copyProperties(yycgd, yycgdCustom_page);
		yycgdQueryVo.setYycgdCustom(yycgdCustom_page);
		
		return "edit";
	}
	
	//提交采购单
	public String submit()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//得到传入采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		//执行提交
		serviceFacade.getCgdService().saveYycgdSubmitState(yycgdid);
		//操作成功
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil
				.createSuccess(Config.MESSAGE, 906, null)));

		return "submit";
	}
	
	//修改采购单页面，将页面中需要的数据查询到
	public String yycgdmxlist_result()throws Exception{
		
		YycgdQueryVo yycgdQueryVo = getModel();
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		
		Long yycgdid = yycgdCustom.getId();
		

		//显示采购单下边采购药品明细
		//采购明细列表的总数
		Long total = serviceFacade.getCgdService().findYycgdmxCountByYycgdid(yycgdid, yycgdQueryVo);
		//计算分页参数
		PageParameter pageParameter = new PageParameter(yycgdQueryVo.getPage(), yycgdQueryVo.getRows(), total);
		
		//查询采购药品明细列表
		List<Yycgdmx> yycgdmxList = serviceFacade.getCgdService().findYycgdmxListByYycgdid(yycgdid,
				yycgdQueryVo, pageParameter.getPageQuery_star(),
				pageParameter.getPageQuery_pageSize());

		
		//创建datagridResultInfo
		this.setProcessResult(ResultUtil.createDataGridResultInfo(yycgdQueryVo.getPage(), total, yycgdmxList));

		return "yycgdmxlist_result";
	}
	
	//添加采购药品页面
	public String addyycgdmx()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		
		//药品类型
		List<Dictinfo> yplbList = serviceFacade.getSystemService().findDictinfoListByTypecode("001");
		
		//交易状态
		List<Dictinfo> ypjyztList = serviceFacade.getSystemService().findDictinfoListByTypecode("003");
		
		yycgdQueryVo.setYplbList(yplbList);
		yycgdQueryVo.setYpjyztList(ypjyztList);
		return "addyycgdmx";
	}
	
	//添加采购药品查询列表json
	public String addyycgdmx_ypxxlist()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//列表的总数
		Long total = serviceFacade.getCgdService().findYpxxListCount(yycgdQueryVo);
		//计算分页参数
		PageParameter pageParameter = new PageParameter(yycgdQueryVo.getPage(), yycgdQueryVo.getRows(), total);
		
		//查询采购药品明细列表
		List<Ypxx> ypxxList = serviceFacade.getCgdService().findYpxxList(
				yycgdQueryVo, pageParameter.getPageQuery_star(),
				pageParameter.getPageQuery_pageSize());

		
		//创建datagridResultInfo
		this.setProcessResult(ResultUtil.createDataGridResultInfo(yycgdQueryVo.getPage(), total, ypxxList));	
		
		return "addyycgdmx_ypxxlist";
	}
	
	//添加采购药品
	public String addyycgdmxsubmit()throws Exception{
		
		YycgdQueryVo yycgdQueryVo = getModel();
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();//采购单id
		//页面提交的多个药品id
		String ypxxids = yycgdQueryVo.getYpxxids();
		String[] split = ypxxids.split(",");
		//将/页面提交的多个药品id格式为List<String>
		List<String> ypxxidList = new ArrayList<String>();
		
		ypxxidList = Arrays.asList(split);
		
		serviceFacade.getCgdService().insertYycgdmx(yycgdid, ypxxidList);
		
		//操作成功
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		
		return "addyycgdmxsubmit";
	}
	
	
	//采购单列表查询页面
	public String list()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//采购单状态
		List<Dictinfo> cgdztlist = serviceFacade.getSystemService().findDictinfoListByTypecode("010");
		
		yycgdQueryVo.setCgdztlist(cgdztlist);
		return "list";
	}
	
	//采购单列表json
	public String list_result()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		
		//获取当前用户身份
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//从用户身份中获取医院的id
		String useryyid = activeUser.getSysid();
		//列表的总数
		Long total = serviceFacade.getCgdService().findYycgdListCount(useryyid, yycgdQueryVo);
		//计算分页参数
		PageParameter pageParameter = new PageParameter(yycgdQueryVo.getPage(), yycgdQueryVo.getRows(), total);
		
		//查询采购药品明细列表
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findYycgdList(useryyid, 
				yycgdQueryVo, pageParameter.getPageQuery_star(),
				pageParameter.getPageQuery_pageSize());

		//创建datagridResultInfo
		this.setProcessResult(ResultUtil.createDataGridResultInfo(yycgdQueryVo.getPage(), total, yycgdList));	
		
		return "list_result";
	}
	
	//保存采购量的提交方法
	public String saveyycgdmxsubmit()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		//得到页面中所有药品信息id和采购量
		List<YycgdmxCustom> yycgdmxs = yycgdQueryVo.getYycgdmxs();
		
		//得到页面提交选中行的序号
		String indexs = yycgdQueryVo.getIndexs();
		String[] indexs_split = indexs.split(",");
		
		//最终要处理的数据
		List<YycgdmxCustom> disposeData = new ArrayList<YycgdmxCustom>();
		
		//遍历选中行的序号，根据序号从yycgdmxs 取出要处理的数据
		for(int i=0;i<indexs_split.length;i++){
			//取出了序号
			int index = Integer.parseInt(indexs_split[i]);
			//根据序号取出要处理的数据
			YycgdmxCustom yycgdmxCustom = yycgdmxs.get(index);
			disposeData.add(yycgdmxCustom);
		}
		//保存采购药品明细
		serviceFacade.getCgdService().saveYycgdmxList(yycgdid, disposeData);
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "saveyycgdmxsubmit";
	}
	
	//将来只有卫生院来操作此方法
	//采购单审核列表查询页面
	public String checklist()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//采购单状态
		List<Dictinfo> cgdztlist = serviceFacade.getSystemService().findDictinfoListByTypecode("010");
		
		yycgdQueryVo.setCgdztlist(cgdztlist);
		return "checklist";
	}
	
	//采购单审核列表json
	public String checklist_result()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		
		//获取当前用户身份
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//从用户身份中获取监督单位id
		String userjdid = activeUser.getSysid();
		//列表的总数
		Long total = serviceFacade.getCgdService().findYycgdCheckListCount(userjdid, yycgdQueryVo);
		//计算分页参数
		PageParameter pageParameter = new PageParameter(yycgdQueryVo.getPage(), yycgdQueryVo.getRows(), total);
		
		//查询采购药品明细列表
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findYycgdCheckList(userjdid, 
				yycgdQueryVo, pageParameter.getPageQuery_star(),
				pageParameter.getPageQuery_pageSize());

		//创建datagridResultInfo
		this.setProcessResult(ResultUtil.createDataGridResultInfo(yycgdQueryVo.getPage(), total, yycgdList));	
		
		return "checklist_result";
	}
	
	//审核页面,会传入采购单id
	public String check()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//查询采购单信息
		//采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		
		//需要在页面显示采购单基本信息
		Yycgd yycgd = serviceFacade.getCgdService().findYycgdById(yycgdid);

		//将yycgd对象放入值栈
		YycgdCustom yycgdCustom_page = new YycgdCustom();
		BeanUtils.copyProperties(yycgd, yycgdCustom_page);
		yycgdQueryVo.setYycgdCustom(yycgdCustom_page);
		return "check";
	}
	//审核提交
	//传入
	public String checksubmit()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		//取出审核结果及审核意见
		YycgdCustom yycgdCustom = yycgdQueryVo.getYycgdCustom();
		
		serviceFacade.getCgdService().saveYycgdCheckState(yycgdid, yycgdCustom.getCheckResult(), yycgdCustom);
		//操作成功
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "checksubmit";
	}
	
	//采购单受理列表
	public String disposelist()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//采购单状态
		List<Dictinfo> cgdztlist = serviceFacade.getSystemService().findDictinfoListByTypecode("010");
		
		yycgdQueryVo.setCgdztlist(cgdztlist);
		return "disposelist";
	}
	
	//采购单受理列表json
	public String disposelist_result()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		
		//获取当前用户身份
		Subject subject = SecurityUtils.getSubject();
		ActiveUser activeUser = (ActiveUser) subject.getPrincipal();
		//从用户身份中获取供货商id
		String usergysid = activeUser.getSysid();
		//列表的总数
		Long total = serviceFacade.getCgdService().findYycgdDisposeListCount(usergysid, yycgdQueryVo);
		//计算分页参数
		PageParameter pageParameter = new PageParameter(yycgdQueryVo.getPage(), yycgdQueryVo.getRows(), total);
		
		//查询采购药品明细列表
		List<Yycgd> yycgdList = serviceFacade.getCgdService().findYycgdDisposeList(usergysid, 
				yycgdQueryVo, pageParameter.getPageQuery_star(),
				pageParameter.getPageQuery_pageSize());

		//创建datagridResultInfo
		this.setProcessResult(ResultUtil.createDataGridResultInfo(yycgdQueryVo.getPage(), total, yycgdList));	
		
		return "disposelist_result";
	}
	
	//受理采购单
	public String dispose()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//和采购单修改页面差不多
		//调用修改采购单方法，取出页面需要参数
		this.edit();
		
		//采购状态
		List<Dictinfo> cgztList = serviceFacade.getSystemService().findDictinfoListByTypecode("011");
		yycgdQueryVo.setCgztList(cgztList);
		
		return "dispose";
	}
	
	//保存采购药品明细的发货状态
	public String savesendstate()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//页面提交的采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
		
		//接收页面提交所有采购药品明细的发货状态（页面显示的所有下拉框和药品信息id的hidden对象）
		List<YycgdmxCustom> yycgdmxs = yycgdQueryVo.getYycgdmxs();
		
		
		
		//定义一个要处理的采购药品列表
		List<YycgdmxCustom> yycgdmxs_dispose =new ArrayList<YycgdmxCustom>();
		
		//接收页面提交要处理的记录序号
		String indexs = yycgdQueryVo.getIndexs();
		String[] split = indexs.split(",");
		for(int i=0;i<split.length;i++){
			//得到记录的序号
			Integer index = Integer.parseInt(split[i]);
			//得到要处理的药品id和发货状态对象
			YycgdmxCustom yycgdmxCustom = yycgdmxs.get(index);
			//放入要处理的列表中
			yycgdmxs_dispose.add(yycgdmxCustom);
		}
		//只处理页面checkbox选中的记录
		serviceFacade.getCgdService().saveSendState(yycgdid, yycgdmxs_dispose);
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "savesendstate";
	}
	
	//受理提交
	public String disposesubmit()throws Exception{
		YycgdQueryVo yycgdQueryVo = getModel();
		//页面提交的采购单id
		Long yycgdid = yycgdQueryVo.getYycgdCustom().getId();
				
		serviceFacade.getCgdService().saveDisposeState(yycgdid);
		this.setProcessResult(ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 906, null)));
		return "disposesubmit";
	}
	
}
