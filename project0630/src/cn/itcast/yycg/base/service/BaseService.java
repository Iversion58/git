package cn.itcast.yycg.base.service;

import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <p>Title: BaseService</p>
 * <p>Description:基础service </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
public abstract class BaseService {

	@Autowired
	protected ServiceFacade serviceFacade;
	
	
}
