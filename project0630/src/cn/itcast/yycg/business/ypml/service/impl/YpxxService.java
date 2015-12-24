package cn.itcast.yycg.business.ypml.service.impl;

import java.util.List;

import cn.itcast.yycg.business.ypml.entity.Ypxx;
import cn.itcast.yycg.business.ypml.pojo.YpxxQueryVo;


/**
 * 
 * <p>Title: YpxxService</p>
 * <p>Description: 药品信息维护</p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-20
 * @version 1.0
 */
public interface YpxxService {
	//根据药品信息id查询药品信息
	public Ypxx findYpxxById(String id) throws Exception;
	//根据查询条件查询药品目录表
	public List<Ypxx> findYpxxList(YpxxQueryVo ypxxQueryVo)throws Exception;
}
