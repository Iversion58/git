package com.itheima.core.service.product;

public interface UploadService {
	//	上传图片
	public String uploadPic(byte[] pic,String name,Long size) throws Exception;
}
