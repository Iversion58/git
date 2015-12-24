package cn.itcast.yycg.business.cgd.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.base.service.BaseService;
import cn.itcast.yycg.business.cgd.entity.Yycgd;
import cn.itcast.yycg.business.cgd.entity.Yycgdmx;
import cn.itcast.yycg.business.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.business.cgd.pojo.YycgdQueryVo;
import cn.itcast.yycg.business.cgd.pojo.YycgdmxCustom;
import cn.itcast.yycg.business.cgd.service.CgdService;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.system.entity.SysArea;
import cn.itcast.yycg.business.system.entity.Usergysarea;
import cn.itcast.yycg.business.system.entity.Userjd;
import cn.itcast.yycg.business.system.entity.Useryy;
import cn.itcast.yycg.business.ypml.entity.Ypxx;

@Service
public class CgdServiceImpl extends BaseService implements CgdService {
	
	//注入dao
	@Resource(name="yycgdDao")
	private BaseDao<Yycgd> yycgdDao;
	
	@Resource(name="yycgdmxDao")
	private BaseDao<Yycgdmx> yycgdmxDao;
	
	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;


	@Override
	public Long insertYycgd(String useryyid, YycgdCustom yycgdCustom)
			throws Exception {
		//校验yycgdCustom不为空以及yycgdCustom里边重要属性不为空
		//...
		
		//定义yycgd
		Yycgd yycgd = new Yycgd();
		BeanUtils.copyProperties(yycgdCustom, yycgd);
		
		//根据医院的id查询医院的信息
		
		Useryy useryy = serviceFacade.getUserService().findUseryyById(useryyid);
		if(useryy==null){
			//抛出异常，请确定要创建采购单卫生室 
			//...
		}
		yycgd.setUseryyid(useryyid);//医院id
		yycgd.setCjtime(new Date());//创建时间
		//采购单的状态初始为1
		//根据业务代码1获取数据字典id
		Dictinfo dictinfoByZt = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("1", "010");
		String zt = dictinfoByZt.getId();//采购单状态对应的数据字典id
		yycgd.setZt(zt);
		//取出供货商id
		//从useryy中获取dq区域，医院的地区
		String dq = useryy.getDq();
		//取出医院地区的上级地区（乡镇）
		SysArea sysAreaByUseryyid = serviceFacade.getSystemService().findSysAreaById(dq);
		
		//取出上级地区
		
		String parentid = sysAreaByUseryyid.getParentid();
		
		//从供货商供货地区表中查询地区对应的供货商
		Usergysarea usergysarea = serviceFacade.getSystemService().findUsergysareaByAreaid(parentid);
		
		//供货商id
		String usergysid = usergysarea.getUsergysid();
		
		yycgd.setUsergysid(usergysid);
		
		yycgdDao.insert(yycgd);
		//返回新采购单id
		return yycgd.getId();
	}

	@Override
	public void insertYycgdmx(Long yycgdid, YycgdmxCustom yycgdmxCustom)
			throws Exception {
		
		//根据采购单id获取采购单信息
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		
		//创建采购单明细对象
		Yycgdmx yycgdmx = new Yycgdmx();
		BeanUtils.copyProperties(yycgdmxCustom, yycgdmx);
		yycgdmx.setYycgdid(yycgdid);//采购单id
		//校验药品信息是否为空
		String ypxxid = yycgdmxCustom.getYpxxid();//药品信息id
		//根据药品信息id查询药品信息
		Ypxx ypxx = serviceFacade.getYpxxService().findYpxxById(ypxxid);
		if(ypxx == null){
			//抛出异常，添加的药品信息不存在...
			
		}
		Float zbjg = ypxx.getZbjg();
		
		yycgdmx.setYpxxid(ypxxid);//药品信息id
		yycgdmx.setZbjg(zbjg);//中标价格
		yycgdmx.setJyjg(zbjg);//本系统中标价格和交易价格一样
		//采购量
		Integer cgl = yycgdmxCustom.getCgl();
		
		//计算采购金额
		Float cgje = zbjg*cgl;
		yycgdmx.setCgje(cgje);
		//初始状态为未受理
		String cgzt = "1";
		//根据业务代码得到数据字典id
		Dictinfo dictinfoByCgzt = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode(cgzt, "011");
		yycgdmx.setCgzt(dictinfoByCgzt.getId());
		//向采购单中添加明细
		yycgd.getYycgdmxes().add(yycgdmx);
		
	}

	@Override
	public Yycgd findYycgdById(Long id) throws Exception {
		return yycgdDao.findById(id);
		
	}

	@Override
	public void updateYycgd(YycgdCustom yycgdCustom) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateYycgdmx(String yycgdid, String ypxxid,
			YycgdmxCustom yycgdmxCustom) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void saveYycgdSubmitState(Long yycgdid) throws Exception {
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		
		//校验采购单下有没有添加药品，如果没有提示“请求采购药品，再进行提交”
		Set yycgdmxes = yycgd.getYycgdmxes();
		if(yycgdmxes.size() == 0){
			//如果没有提示“请求采购药品，再进行提交”
		}
		//还要校验采购药品明细中采购量、采购金额是否输入
		/*for(yycgdmxes){
			
		}*/
		
		//设置采购单状态为2
		//得到2对应的数据字典id
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("2", "010");
		String zt = dictinfo.getId();
		yycgd.setZt(zt);
		//提交时间
		yycgd.setTjtime(new Date());
		yycgdDao.update(yycgd);
	}

	//拼接明细查询条件
	public void findYycgdmxListCondition(Long yycgdid,YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria){
		//指定从哪个采购单查询
		//拼接查询条件采购单id
		detachedCriteria.add(Restrictions.eq("yycgdid", yycgdid));
		//拼接页面请求的查询
		if(yycgdQueryVo!=null){
			//...
		}
		
	}
	@Override
	public List<Yycgdmx> findYycgdmxListByYycgdid(Long yycgdid,YycgdQueryVo yycgdQueryVo,
			int firstResult, int maxResults) throws Exception {
		DetachedCriteria detachedCriteria = yycgdmxDao.createCriteria();
		//拼接查询条件
		this.findYycgdmxListCondition(yycgdid, yycgdQueryVo, detachedCriteria);
		
		return yycgdmxDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findYycgdmxCountByYycgdid(Long yycgdid,YycgdQueryVo yycgdQueryVo) throws Exception {
		DetachedCriteria detachedCriteria = yycgdmxDao.createCriteria();
		//拼接查询条件
		this.findYycgdmxListCondition(yycgdid, yycgdQueryVo, detachedCriteria);
		
		return yycgdmxDao.findListCountByCriteria(detachedCriteria);
	}

	//拼接药品查询条件
	public void findYpxxListCondition(YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria){
		
	}
	@Override
	public List<Ypxx> findYpxxList(YycgdQueryVo yycgdQueryVo, int firstResult,
			int maxResults) throws Exception {
		DetachedCriteria detachedCriteria = ypxxDao.createCriteria();
		this.findYpxxListCondition(yycgdQueryVo, detachedCriteria);
		return ypxxDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findYpxxListCount(YycgdQueryVo yycgdQueryVo) throws Exception {
		DetachedCriteria detachedCriteria = ypxxDao.createCriteria();
		this.findYpxxListCondition(yycgdQueryVo, detachedCriteria);
		return ypxxDao.findListCountByCriteria(detachedCriteria);
	}

	@Override
	public void insertYycgdmx(Long yycgdid, List<String> ypxxids)
			throws Exception {
		
		//根据采购单id查询采购信息
		Yycgd yycgd = this.findYycgdById(yycgdid);
		if(yycgd == null){
			//校验，找不到采购单。。
		}
		
		for(String ypxxid:ypxxids){
			
			//将每个药品全部添加到明细表
			//根据药品信息id查询药品信息
			Ypxx ypxx = ypxxDao.findById(ypxxid);
			if(ypxx==null){
				//抛出异常，添加的药品在系统中不存在 
				//...
				
			}
			
			Yycgdmx yycgdmx = new Yycgdmx();
			yycgdmx.setYycgdid(yycgdid);
			yycgdmx.setYpxxid(ypxxid);
			Float zbjg = ypxx.getZbjg();//中标价格
			yycgdmx.setZbjg(zbjg);
			Float jyjg = zbjg;//交易价格等于中标价格
			yycgdmx.setJyjg(jyjg);
			Integer cgl = 0;//采购量默认为0
			yycgdmx.setCgl(cgl);
			Float cgje = jyjg*cgl;//采购金额
			yycgdmx.setCgje(cgje);
			//采购药品状态默认为1未受理
			//根据业务代码1获取数据字典id
			Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("1", "011");
			String cgzt = dictinfo.getId();//采购药品状态
			yycgdmx.setCgzt(cgzt);
			
			yycgd.getYycgdmxes().add(yycgdmx);
//			yycgdmxDao.insert(yycgdmx);
		}
		
		
	}

	//提交采购单列表的查询条件
	private void findYycgdListCondition(String useryyid,YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria){
		//卫生室 只查询自己创建的采购单
		detachedCriteria.add(Restrictions.eq("useryyid", useryyid));
		
		//拼接用户输入的查询条件
		if(yycgdQueryVo!=null){
			//....
		}
		
	}
	@Override
	public List<Yycgd> findYycgdList(String useryyid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception {
		
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		this.findYycgdListCondition(useryyid, yycgdQueryVo, detachedCriteria);
		
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findYycgdListCount(String useryyid, YycgdQueryVo yycgdQueryVo)
			throws Exception {
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		this.findYycgdListCondition(useryyid, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListCountByCriteria(detachedCriteria);
	}

	
	
	@Override
	public void saveYycgdmxList(Long yycgdid, List<YycgdmxCustom> yycgdmxCustoms)
			throws Exception {
		
		//根据采购单id查询采购单信息
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		for(YycgdmxCustom yycgdmxCustom:yycgdmxCustoms){
			//药品信息id
			String ypxxid = yycgdmxCustom.getYpxxid();
			//根据采购单id和药品信息id取出采购药品明细
			Yycgdmx yycgdmx = this.findYycgdmxByUnique(yycgdid, ypxxid);
			
			//获取采购量
			Integer cgl = yycgdmxCustom.getCgl();//页面输入的采购量
			Float jyjg = yycgdmx.getJyjg();//交易价格
			//计算采购金额
			Float cgje = jyjg*cgl;
			
			yycgdmx.setCgl(cgl);
			yycgdmx.setCgje(cgje);
			yycgdmxDao.update(yycgdmx);
						
		}
		
	}

	@Override
	public Yycgdmx findYycgdmxByUnique(Long yycgdid, String ypxxid)
			throws Exception {
		DetachedCriteria detachedCriteria = yycgdmxDao.createCriteria();
		detachedCriteria.add(Restrictions.eq("yycgdid", yycgdid));
		detachedCriteria.add(Restrictions.eq("ypxxid", ypxxid));
		return yycgdmxDao.findSingleObjByCriteria(detachedCriteria);
	}

	
	//查询某个卫生院下边卫生室 创建的所有采购单
	//拼接查询条件
	private void findYycgdCheckListCondition(String userjdid,
			YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria) throws Exception{
		
		//根据userjdid查询卫生院信息
		Userjd userjd = serviceFacade.getUserService().findUserjdById(userjdid);
		//卫生院管理的地区
		String dq = userjd.getDq();
		//卫生院管理所有卫生室 
		List<Useryy> useryyList = serviceFacade.getUserService().findUseryyByAreaid(dq);
		//包含 useryyid的集合
		List<String> uesryyidList = new ArrayList<String>();
		for(Useryy useryy:useryyList){
			uesryyidList.add(useryy.getId());
		}
		
		
		//拼接sql select * from yycgd t where t.useryyid in ('1f8b098b-067e-11e3-8a3c-0019d2ce5116','')
		detachedCriteria.add(Restrictions.in("useryyid", uesryyidList));
		
		//拼接sql and zt='01002'
		//根据业务代码：2已提交未审核 查询数据字典id
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("2","010");
		String zt = dictinfo.getId();
		detachedCriteria.add(Restrictions.eq("zt", zt));
		
	}
	@Override
	public List<Yycgd> findYycgdCheckList(String userjdid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception {
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		//拼接查询条件
		this.findYycgdCheckListCondition(userjdid, yycgdQueryVo, detachedCriteria);
		
		
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}
	@Override
	public Long findYycgdCheckListCount(String userjdid,
			YycgdQueryVo yycgdQueryVo)
			throws Exception {
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		//拼接查询条件
		this.findYycgdCheckListCondition(userjdid, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListCountByCriteria(detachedCriteria);
	}

	@Override
	public void saveYycgdCheckState(Long yycgdid, String checkResult,
			YycgdCustom yycgdCustom) throws Exception {
		//校验checkResult的合法性，不能为空，必须是1或0
		//..
		
		//根据采购单id查询采购单
		Yycgd yycgd = this.findYycgdById(yycgdid);
		
		//解析checkResult
		if(checkResult.equals("1")){//审核通过
			//更新状态
			//业务代码为3
			//根据业务代码取数据字典
			Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("3", "010");
			String zt = dictinfo.getId();
			yycgd.setZt(zt);
			
		}else if(checkResult.equals("0")){//审核不通过
			//更新状态
			//业务代码为4
			//根据业务代码取数据字典
			Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("4", "010");
			String zt = dictinfo.getId();
			yycgd.setZt(zt);
		}
		//更新审核意见 
		yycgd.setShyj(yycgdCustom.getShyj());
		yycgd.setShtime(new Date());
		
		yycgdDao.update(yycgd);
		
	}

	//供货商受理采购单列表
	//拼接查询条件 
	private void findYycgdDisposeCondition(String usergysid,
			YycgdQueryVo yycgdQueryVo,DetachedCriteria detachedCriteria)throws Exception{
		
		//拼接的sql  where t.usergysid = '??' and t.zt = '01003'
		//限定供货商只查询自己的采购单
		detachedCriteria.add(Restrictions.eq("usergysid", usergysid));
		//限定只查询审核通过的采购单
		//审核通过状态的业务代码是3，根据3查询数据字典
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("3", "010");
		String zt = dictinfo.getId();
		detachedCriteria.add(Restrictions.eq("zt", zt));
	}
	@Override
	public List<Yycgd> findYycgdDisposeList(String usergysid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception {
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		this.findYycgdDisposeCondition(usergysid, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListPageByCriteria(detachedCriteria, firstResult, maxResults);
	}

	@Override
	public Long findYycgdDisposeListCount(String usergysid,
			YycgdQueryVo yycgdQueryVo) throws Exception {
		DetachedCriteria detachedCriteria = yycgdDao.createCriteria();
		this.findYycgdDisposeCondition(usergysid, yycgdQueryVo, detachedCriteria);
		return yycgdDao.findListCountByCriteria(detachedCriteria);
	}

	@Override
	public void saveSendState(Long yycgdid, List<YycgdmxCustom> yycgdmxCustoms)
			throws Exception {
		//遍历yycgdmxCustoms，保存每个药品的发货状态
		for(YycgdmxCustom yycgdmxCustom:yycgdmxCustoms){
			//药品信息id
			String ypxxid = yycgdmxCustom.getYpxxid();
			//发货状态
			String cgzt = yycgdmxCustom.getCgzt();
			
			//根据采购单id和药品信息id更新发货状态
			//根据采购单id和药品信息id从数据库查询记录
			Yycgdmx yycgdmx_update = this.findYycgdmxByUnique(yycgdid, ypxxid);
			yycgdmx_update.setCgzt(cgzt);
			yycgdmxDao.update(yycgdmx_update);
		}
		
		
	}
	//保存受理状态
	public void saveDisposeState(Long yycgdid)throws Exception{
		//根据yycgdid查询采购单
		Yycgd yycgd = yycgdDao.findById(yycgdid);
		
		//校验采购单下是否有药品明细
		//...
		//校验采购单下药品明细是否设置采购量、采购金额。。
		//...
		
		//受理完成状态的业务代码是5
		//根据5得到数据字典id
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode("5", "010");
		String zt = dictinfo.getId();
		yycgd.setZt(zt);
		yycgdDao.update(yycgd);
	}

}
