package cn.itcast.yycg.business.ypml.web.action;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.itcast.yycg.base.config.Config;
import cn.itcast.yycg.base.web.action.BaseAction;
import cn.itcast.yycg.base.web.result.ResultUtil;
import cn.itcast.yycg.base.web.result.SubmitResultInfo;
import cn.itcast.yycg.business.system.entity.Basicinfo;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.ypml.entity.Ypxx;
import cn.itcast.yycg.business.ypml.pojo.YpxxQueryVo;
import cn.itcast.yycg.util.ExcelExportSXXSSF;
import cn.itcast.yycg.util.HxlsOptRowsInterface;
import cn.itcast.yycg.util.HxlsRead;
import cn.itcast.yycg.util.UUIDBuild;


@Controller
@Scope("prototype")
public class YpxxAction extends BaseAction<YpxxQueryVo> {
	
	@Autowired
	private HxlsOptRowsInterface ypxxImportService;
	
	//注入spring容器中配置hessian的代理对象
	@Autowired
	private cn.itcast.yycg.hessian.server.YpxxService ypxxRemoteService;
	
	//导出页面
	public String exportypxx()throws Exception{
		
		YpxxQueryVo ypxxQueryVo = getModel();
		
		//药品类型
		List<Dictinfo> yplbList = serviceFacade.getSystemService().findDictinfoListByTypecode("001");
		
		//交易状态
		List<Dictinfo> ypjyztList = serviceFacade.getSystemService().findDictinfoListByTypecode("003");
		//将类型和交易状态 放到值栈
		ypxxQueryVo.setYpjyztList(ypjyztList);
		ypxxQueryVo.setYplbList(yplbList);
		
		return "exportypxx";
	}
	//导出提交
	public String exportypxxsubmit()throws Exception{
		YpxxQueryVo ypxxQueryVo = getModel();
		
		Basicinfo basicinfo = serviceFacade.getSystemService().findBasicinfoById("00301");
		//导出文件存放的路径，并且是虚拟目录指向的路径
//		String filePath = "f:/develop/upload/temp/";
		//从系统参数表中查询
		String filePath = basicinfo.getValue();
		//导出文件的前缀
		String filePrefix="ypxx";
		//-1表示关闭自动刷新，手动控制写磁盘的时机，其它数据表示多少数据在内存保存，超过的则写入磁盘
		int flushRows=100;
		
		
		List<Ypxx> ypxxList = serviceFacade.getYpxxService().findYpxxList(ypxxQueryVo);
		
		//注意：fieldCodes和fieldNames个数必须相同且属性和title顺序一一对应，这样title和内容才一一对应
		//指导导出数据的title
		List<String> fieldNames=new ArrayList<String>();
		fieldNames.add("流水号");
		fieldNames.add("通用名");
		fieldNames.add("剂型");
		fieldNames.add("规格");
		fieldNames.add("转换系数");
		fieldNames.add("生产企业名称");
		fieldNames.add("商品名称");
		fieldNames.add("中标价格");
		fieldNames.add("交易状态");
		
		//告诉导出类数据list中对象的属性，让ExcelExportSXXSSF通过反射获取对象的值
		List<String> fieldCodes=new ArrayList<String>();
		fieldCodes.add("bm");//药品流水号
		fieldCodes.add("mc");//通用名
		fieldCodes.add("jx");//剂型
		fieldCodes.add("gg");//规格
		fieldCodes.add("zhxs");//转换系数
		fieldCodes.add("scqymc");//生产企业名称
		fieldCodes.add("spmc");//商品名称
		fieldCodes.add("zbjg");//价格
		fieldCodes.add("dictinfoByJyzt.info");//交易状态，显示中文名称
		
		
		//开始导出，执行一些workbook及sheet等对象的初始创建
		ExcelExportSXXSSF excelExportSXXSSF = ExcelExportSXXSSF.start(filePath, "/upload/", filePrefix, fieldNames, fieldCodes, flushRows);
		
				//执行导出
		excelExportSXXSSF.writeDatasByObject(ypxxList);
		//输出文件，返回下载文件的http地址
		String webpath = excelExportSXXSSF.exportFile();
		
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 313, new Object[]{ypxxList.size(),webpath}));
		this.setProcessResult(submitResultInfo);
//		System.out.println(webpath);
		
		return "exportypxxsubmit";
	}
	
	//导入页面
	public String importypxx()throws Exception{
		
		
		
		return "importypxx";
	}
	
	//导入提交
	public String importypxxsubmit()throws Exception{
		YpxxQueryVo ypxxQueryVo = getModel();
		//导入的文件,此文件的扩展名是tmp
		File ypxximportfile = ypxxQueryVo.getYpxximportfile();
		//导入的文件名
		String fileName = ypxxQueryVo.getYpxximportfileFileName();
		//获取正确的文件扩展名
		//定义新文件
		//新文件的路径
		String newFilePath ="F:\\develop\\upload\\temp\\"+UUIDBuild.getUUID()+fileName.substring(fileName.lastIndexOf("."));
		File destFile = new File(newFilePath);
		//将tmp临时拷贝到新文件中
		FileUtils.copyFile(ypxximportfile, destFile);
		
		
		
		String filePath = ypxximportfile.getAbsolutePath();
		
		HxlsRead xls2csv = null;
		try {
			//第一个参数就是导入的文件
			//第二个参数就是导入文件中哪个sheet
			//第三个参数导入接口的实现类对象
			xls2csv = new HxlsRead(newFilePath,1,ypxxImportService);
			xls2csv.process();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//导入成功数量
		long optRows_success = xls2csv.getOptRows_success();
		//导入失败数量
		long optRows_failure = xls2csv.getOptRows_failure();
		
		//得到失败原因
		List<String> failmsgs = xls2csv.getFailmsgs();
		
		//第一种方案，将失败原因传到页面让用户看
		//第二种方案，将失败的记录同失败原因一块儿再导出一个文件，让用户去下载查看 
		//包含失败原因的文件在原来模板文件中添加一列（失败原因）
		//好处，用户下载失败原因的文件，参考失败原因去修改记录，重新再导入此文件
		//失败记录
		List<List<String>> failrows = xls2csv.getFailrows();
		
		//使用导出的api封装类，将失败原因和失败记录信息导出一个excel。
		
		
		SubmitResultInfo submitResultInfo = ResultUtil.createSubmitResult(ResultUtil.createSuccess(Config.MESSAGE, 314, new Object[]{optRows_success,optRows_failure}));
		this.setProcessResult(submitResultInfo);
		return "importypxxsubmit";
	}
	
	//同步的操作页面
	public String rsyncypxx()throws Exception{
		
		return "rsyncypxx";
	}
	
	//同步的提交方法
	public String rsyncypxxsubmit()throws Exception{
		
		//从spring容器中获取远程service的代理对象
		
		//调用代理对象的方法执行同步，得到同步到的药品信息列表
		List<cn.itcast.yycg.hessian.server.Ypxx> ypxxList = ypxxRemoteService.findYpxxList();
		
		//调用本系统的service向ypxx表添加记录
		//...
		
		return "rsyncypxxsubmit";
	}
	
}
