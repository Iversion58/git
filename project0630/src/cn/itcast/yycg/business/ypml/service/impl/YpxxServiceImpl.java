package cn.itcast.yycg.business.ypml.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import cn.itcast.yycg.base.dao.BaseDao;
import cn.itcast.yycg.base.service.BaseService;
import cn.itcast.yycg.business.ypml.entity.Ypxx;
import cn.itcast.yycg.business.ypml.pojo.YpxxCustom;
import cn.itcast.yycg.business.ypml.pojo.YpxxQueryVo;

@Service
public class YpxxServiceImpl extends BaseService implements YpxxService{
	
	//注入dao
	@Resource(name="ypxxDao")
	private BaseDao<Ypxx> ypxxDao;
	
	//根据药品信息id查询药品信息
	public Ypxx findYpxxById(String id) throws Exception{
		
		return ypxxDao.findById(id);
	}
		

	//拼接查询条件
	private void findYpxxCondition(YpxxQueryVo ypxxQueryVo,DetachedCriteria detachedCriteria){
		
		if(ypxxQueryVo!=null){
			YpxxCustom ypxxCustom = ypxxQueryVo.getYpxxCustom();
			if(ypxxCustom!=null){
				if(ypxxCustom.getJyzt()!=null){
					//交易状态拼接
					detachedCriteria.add(Restrictions.eq("jyzt", ypxxCustom.getJyzt()));
				}
			}
		}
	}
	@Override
	public List<Ypxx> findYpxxList(YpxxQueryVo ypxxQueryVo) throws Exception {
		DetachedCriteria detachedCriteria = ypxxDao.createCriteria();
		//拼接查询条件
		findYpxxCondition(ypxxQueryVo,detachedCriteria);
		return ypxxDao.findListByCriteria(detachedCriteria);
	}

}
