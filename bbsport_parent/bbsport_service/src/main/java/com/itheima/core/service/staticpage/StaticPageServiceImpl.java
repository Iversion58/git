package com.itheima.core.service.staticpage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.web.context.ServletContextAware;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import com.alibaba.dubbo.remoting.exchange.Request;
import com.itheima.common.core.bean.product.Product;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class StaticPageServiceImpl implements StaticPageService , ServletContextAware{
	/**
	 * 
	 */
	private Configuration conf;
	private ServletContext servletContext;

	public void setFreeMarkerConfigurer(FreeMarkerConfigurer freeMarkerConfigurer) {
		this.conf = freeMarkerConfigurer.getConfiguration();
	}



	@Override
	public void index(Map<String, Object> root, Long id) {
		//Freemarker程序
				//模板 +  数据模型 = 输出
				// "/html/product/" + id + ".html"     (商品ID)
		String path=getPath("/html/product/"+id + ".html");
				File f = new File(path);
					//		"/html/product/"
		File parentFile=f.getParentFile();
		if(parentFile.exists()){
			parentFile.mkdirs();
		}
		Writer out = null;
		
		try {
			Template template = conf.getTemplate(path);
			out= new OutputStreamWriter(new FileOutputStream(f),"UTF-8");
			template.process(root, out);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			if(null != out){
				try {
					out.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}


			//获取全路径
	private String getPath(String path) {
		return servletContext.getRealPath(path);
	}


	@Override
	public void setServletContext(ServletContext servletContext) {
					this.servletContext=servletContext;
	}





}
