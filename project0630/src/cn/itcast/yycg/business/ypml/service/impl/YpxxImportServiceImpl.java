package cn.itcast.yycg.business.ypml.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.base.service.BaseService;
import cn.itcast.yycg.business.system.entity.Dictinfo;
import cn.itcast.yycg.business.ypml.entity.Ypxx;
import cn.itcast.yycg.util.HxlsOptRowsInterface;

@Service
public class YpxxImportServiceImpl extends BaseService implements HxlsOptRowsInterface {
	//注入dao
	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;
	
	
	@Override
	public String saveOptRows(int sheetIndex, int curRow, List<String> rowlist)
			throws Exception {
		
		//rowlist中有一条记录，将导入的数据按从左到右将单元格的数据放在list中
		
		//进行数据校验，如果不合法返回失败原因。。
		//比如校验价格的合法性
		//比如校验唯一索引
		
		//通用名
		String mc = rowlist.get(0);
		//剂型
		String jx = rowlist.get(1);
		//规格
		String gg = rowlist.get(2);
		//转换系数
		String zhxs = rowlist.get(3);
		//中标价格
		Float zbjg = Float.parseFloat(rowlist.get(4));
		//生产企业
		String scqymc = rowlist.get(5);
		//商品名
		String spmc = rowlist.get(6);
		
		//交易状态(1：正常,2：暂停)
		String jyzt_code = rowlist.get(7);
		//将业务代码转成数据字典id
		Dictinfo dictinfo = serviceFacade.getSystemService().findDictinfoByDictcodeAndTypecode(jyzt_code, "003");
		//交易状态对应的数据字典id
		String jyzt = dictinfo.getId();
		
		Ypxx ypxx = new Ypxx();
		ypxx.setMc(mc);
		ypxx.setJx(jx);
		ypxx.setGg(gg);
		ypxx.setZhxs(zhxs);
		ypxx.setZbjg(zbjg);
		ypxx.setScqymc(scqymc);
		ypxx.setSpmc(spmc);
		ypxx.setJyzt(jyzt);
		
		ypxxDao.insert(ypxx);
		
		
		return "success";
	}

}
