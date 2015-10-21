package com.itheima.core.service.product;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itheima.common.core.bean.product.Type;
import com.itheima.common.core.bean.product.TypeQuery;
import com.itheima.core.dao.product.TypeDao;

@Service("typeService")
public class TypeServiceImpl implements TypeService {

	@Autowired
	private TypeDao typeDao;
	@Override
	public List<Type> selectListByQuery() {
		TypeQuery query= new TypeQuery();
		query.createCriteria().andParentIdNotEqualTo(0l).andIsDisplayEqualTo(true);
		
		return  typeDao.selectByExample(query);
	}

}
