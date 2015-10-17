package com.itheima.core.dao;

import java.util.List;

import com.itheima.core.bean.Testdb;

public interface TestTbDao {
	
				//保存
			public void addTestDb(Testdb testdb);
			//查询
			public List<Testdb> selectTestDbList();
}
