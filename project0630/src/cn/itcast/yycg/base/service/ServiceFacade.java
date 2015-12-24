package cn.itcast.yycg.base.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.business.cgd.service.CgdService;
import cn.itcast.yycg.business.system.service.SystemService;
import cn.itcast.yycg.business.system.service.UserService;
import cn.itcast.yycg.business.ypml.service.impl.YpxxService;

/**
 * 
 * <p>Title: ServiceFacade</p>
 * <p>Description:系统service门面 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-13
 * @version 1.0
 */
@Service
public class ServiceFacade {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private YpxxService ypxxService;
	
	//采购单
	@Autowired
	private CgdService cgdService;
	
	
	//可以扩展很多service

	public CgdService getCgdService() {
		return cgdService;
	}

	public UserService getUserService() {
		return userService;
	}

	public SystemService getSystemService() {
		return systemService;
	}

	public YpxxService getYpxxService() {
		return ypxxService;
	}
	
	

}
