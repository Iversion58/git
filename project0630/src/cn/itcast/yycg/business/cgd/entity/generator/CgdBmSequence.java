package cn.itcast.yycg.business.cgd.entity.generator;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.engine.SessionImplementor;
import org.hibernate.id.SequenceGenerator;

import cn.itcast.yycg.util.DateUtil;

public class CgdBmSequence extends SequenceGenerator {


	@Override
	public Serializable generate(SessionImplementor session, Object obj) {
		//查询oralce的序列得到一个流水号
		//select yycgdbm.nextval from dual 
		//原来代码
//		return super.generate(session, obj);
		//oralce的序列得到一个流水号
		Serializable generate = super.generate(session, obj);
		String sequence = String.valueOf(generate);
		//采购单号等于4位年+序列号
		String year = String.valueOf(DateUtil.getYear(new Date()));
		sequence = year+sequence;
		
		return Long.parseLong(sequence);
	}

}
