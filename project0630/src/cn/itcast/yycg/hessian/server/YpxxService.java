package cn.itcast.yycg.hessian.server;

import java.util.List;

/**
 * 
 * <p>Title: YpxxService</p>
 * <p>Description:药品信息维护服务接口 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-8-24
 * @version 1.0
 */
public interface YpxxService {

	//省级药品目录查询不分页
	public List<Ypxx> findYpxxList()throws Exception;
}
