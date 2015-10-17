package com.itheima.core.service;

import java.util.List;

import com.itheima.core.bean.Testdb;

public interface TestTbService {

	// 保存
	public void addTestDb(Testdb testdb);

	// 查询
	public List<Testdb> selectTestDbList();
}
