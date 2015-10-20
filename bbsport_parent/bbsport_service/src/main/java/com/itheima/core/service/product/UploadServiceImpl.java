package com.itheima.core.service.product;

import org.springframework.stereotype.Service;

import com.itheima.common.fastdfs.FastDFSUtil;

@Service("uploadService")

public class UploadServiceImpl implements UploadService{
	
	//上传图片
	@Override
	public String uploadPic(byte[] pic, String name, Long size) throws Exception {
		// TODO Auto-generated method stub
		return FastDFSUtil.uploadPic(pic,name,size);
	}
	

	
}
