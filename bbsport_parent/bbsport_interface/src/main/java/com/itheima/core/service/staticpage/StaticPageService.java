package com.itheima.core.service.staticpage;

import java.util.Map;

public interface StaticPageService {

	/**
	 *		静态化页面 
	 * @param root		数据模型
	 * @param id			商品id
	 */
	public void index(Map<String,Object> root,Long id);
	
	
}
