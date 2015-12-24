package cn.itcast.yycg.business.cgd.service;

import java.util.List;

import cn.itcast.yycg.business.cgd.entity.Yycgd;
import cn.itcast.yycg.business.cgd.entity.Yycgdmx;
import cn.itcast.yycg.business.cgd.pojo.YycgdCustom;
import cn.itcast.yycg.business.cgd.pojo.YycgdQueryVo;
import cn.itcast.yycg.business.cgd.pojo.YycgdmxCustom;
import cn.itcast.yycg.business.ypml.entity.Ypxx;

/**
 * 
 * <p>Title: CgdService</p>
 * <p>Description:采购单管理 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-21
 * @version 1.0
 */
public interface CgdService {
	
	//1、添加采购单基本信息
	/**
	 * 
	 * <p>Title: insertYycgd</p>
	 * <p>Description: </p>
	 * @param useryyid 医院id
	 * @param yycgdCustom 采购单信息
	 * @throws Exception
	 */
	public Long insertYycgd(String useryyid,YycgdCustom yycgdCustom) throws Exception;
	//2、添加采购单明细信息
	/**
	 * 
	 * <p>Title: insertYycgdmx</p>
	 * <p>Description: </p>
	 * @param yycgdid 采购单id
	 * @param yycgdmxCustom
	 * @throws Exception
	 */
	public void insertYycgdmx(Long yycgdid,YycgdmxCustom yycgdmxCustom) throws Exception;
//	3、根据采购单id查询采购单信息
	public Yycgd findYycgdById(Long id) throws Exception;
//	4、更新采购单信息
	public void updateYycgd(YycgdCustom yycgdCustom) throws Exception;
//	5、更新采购明细信息
	public void updateYycgdmx(String yycgdid,String ypxxid,YycgdmxCustom yycgdmxCustom) throws Exception;
//	6、提交采购单
	public void saveYycgdSubmitState(Long yycgdid)throws Exception;
	
	//根据采购单 id查询采购药品明细列表
	/**
	 * 
	 * <p>Title: findYycgdmxListByYycgdid</p>
	 * <p>Description: </p>
	 * @param yycgdid 采购单id
	 * @param yycgdQueryVo 采购药品明细的查询条件
	 * @param firstResult 
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<Yycgdmx> findYycgdmxListByYycgdid(Long yycgdid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception;

	//根据采购单 id查询采购药品明细列表总数
	public Long findYycgdmxCountByYycgdid(Long yycgdid,YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//查询药品目录
	public List<Ypxx> findYpxxList(YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)throws Exception;
	public Long findYpxxListCount(YycgdQueryVo yycgdQueryVo)throws Exception;
	
	//采购采购药品明细
	/**
	 * 
	 * <p>Title: insertYycgdmx</p>
	 * <p>Description: </p>
	 * @param yycgdid 采购单id
	 * @param ypxxids 采购药品id（多个）
	 * @throws Exception
	 */
	public void insertYycgdmx(Long yycgdid,List<String> ypxxids)throws Exception;
	
	//采购单列表
	/**
	 * 
	 * <p>Title: findYycgdList</p>
	 * <p>Description: </p>
	 * @param useryyid 卫生室 的id
	 * @param yycgdQueryVo 查询条件
	 * @param firstResult 
	 * @param maxResuls
	 * @return
	 * @throws Exception
	 */
	public List<Yycgd> findYycgdList(String useryyid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception;

	public Long findYycgdListCount(String useryyid,YycgdQueryVo yycgdQueryVo)throws Exception;
	
	//根据采购单id和药品id查询采购单明细记录
	public Yycgdmx findYycgdmxByUnique(Long yycgdid,String ypxxid)throws Exception;
	
	//保存采购药品信息
	public void saveYycgdmxList(Long yycgdid,List<YycgdmxCustom> yycgdmxCustoms) throws Exception;

	//审核采购单列表
	/**
	 * 
	 * <p>Title: findYycgdCheckList</p>
	 * <p>Description: </p>
	 * @param userjdid 卫生院id
	 * @param yycgdQueryVo 查询条件
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	
	public List<Yycgd> findYycgdCheckList(String userjdid,YycgdQueryVo yycgdQueryVo,int firstResult,int maxResults)throws Exception;
	public Long findYycgdCheckListCount(String userjdid,YycgdQueryVo yycgdQueryVo)throws Exception;
	
	//审核采购提交
	/**
	 * 
	 * <p>Title: saveYycgdCheckState</p>
	 * <p>Description: </p>
	 * @param yycgdid 采购单id
	 * @param checkResult 审核结果 1：表示通过，0表示不通过
	 * @param yycgdmxCustom 审核意见信息
	 * @throws Exception
	 */
	public void saveYycgdCheckState(Long yycgdid, String checkResult,
			YycgdCustom yycgdCustom) throws Exception;
	
	//采购单受理列表
	/**
	 * 
	 * <p>Title: findYycgdDisposeList</p>
	 * <p>Description: </p>
	 * @param usergysid 供货商id
	 * @param yycgdQueryVo 查询条件
	 * @param firstResult
	 * @param maxResults
	 * @return
	 * @throws Exception
	 */
	public List<Yycgd> findYycgdDisposeList(String usergysid,
			YycgdQueryVo yycgdQueryVo, int firstResult, int maxResults)
			throws Exception;

	public Long findYycgdDisposeListCount(String usergysid,
			YycgdQueryVo yycgdQueryVo) throws Exception;
	
	//保存发货状态
	/**
	 * 
	 * <p>Title: saveSendState</p>
	 * <p>Description: </p>
	 * @param yycgdid 采购单id
	 * @param yycgdmxCustoms 包括药品信息id和发货状态
	 * @throws Exception
	 */
	public void saveSendState(Long yycgdid,List<YycgdmxCustom> yycgdmxCustoms)throws Exception;

	//保存受理状态
	public void saveDisposeState(Long yycgdid)throws Exception;
}
