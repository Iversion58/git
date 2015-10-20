package com.itheima.core.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itheima.common.core.bean.Testdb;
import com.itheima.core.dao.TestTbDao;

@Service("testTbService")
@Transactional
public class TestTbServiceImpl implements TestTbService {

			@Autowired
			private TestTbDao testTbDao;
	
	@Override
	public void addTestDb(Testdb testdb) {
			testTbDao.addTestDb(testdb);
	}

	@Override
	public List<Testdb> selectTestDbList() {
		return testTbDao.selectTestDbList();
	}

}
