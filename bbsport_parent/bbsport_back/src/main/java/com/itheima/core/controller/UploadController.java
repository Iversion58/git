package com.itheima.core.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.itheima.common.core.web.Constants;
import com.itheima.core.service.product.UploadService;

@Controller
public class UploadController {

	@Autowired
	private UploadService uploadService;
	
	@RequestMapping(value="/upload/uploadPic.do")
	public void uploadPic(@RequestParam(required=false)MultipartFile pic,HttpServletResponse response) throws Exception{
		System.out.println("图片的文件----->"+pic.getOriginalFilename());
		//图片本身属性
		/*		
		 		byte[] bytes = pic.getBytes();
				long size = pic.getSize();
				*/
				
		String path = uploadService.uploadPic(pic.getBytes(), pic.getOriginalFilename(), pic.getSize());
				//把保存 fastDFS		java接口分布式文件系统
		
			JSONObject jo=new JSONObject();
			jo.put("path", path);
			jo.put("url", Constants.IMG_URL+path);
			System.out.println("fastDFS---->"+path);
			System.out.println("img----src---->"+Constants.IMG_URL+path);
			//设置成json
			response.setContentType("application/json;charset=utf-8");
			response.getWriter().write(jo.toString());
	}
}
