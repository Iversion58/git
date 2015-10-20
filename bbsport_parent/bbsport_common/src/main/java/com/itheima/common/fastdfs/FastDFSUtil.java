package com.itheima.common.fastdfs;

import org.apache.commons.io.FilenameUtils;
import org.csource.common.NameValuePair;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient1;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.springframework.core.io.ClassPathResource;

public class FastDFSUtil {
		//上传图片		返回图片路径
	public static String uploadPic(byte[] pic, String name, Long size) throws Exception{
				//全局设置ip	端口		参数		fdfs_client.conf
		 ClassPathResource resource =new ClassPathResource("fdfs_client.conf");
		 		//设置成功
		 ClientGlobal.init(resource.getClassLoader().getResource("fdfs_client.conf").getPath());
	
		 			//ip		22122
		 TrackerClient trackerClient=new TrackerClient();
		 				//创建连接			小弟ip		端口号
		 TrackerServer trackerServer=trackerClient.getConnection();
		 
		 StorageServer storageServer=null;
		 
		 StorageClient1 storageClient=new StorageClient1(trackerServer, storageServer);
		 		//.jsp		.png
		 String ext=FilenameUtils.getExtension(name);
		//meta_list  名称  、大小 、扩展名
		NameValuePair[]  meta_list=new NameValuePair[3];
		 meta_list[0]=new NameValuePair("filename",name);
		 meta_list[1]=new NameValuePair("filenext",ext);
		 meta_list[2]=new NameValuePair("filesize",String.valueOf(size));
		//上传图片
			//http://192.168.200.128/group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg
			//   group1/M00/00/01/wKjIgFWOYc6APpjAAAD-qk29i78248.jpg  保存数据库
		 String path = storageClient.upload_file1(pic, ext, meta_list);
		 return path;
	}

}
