package cn.itcast.hessian.service.impl;

import java.util.ArrayList;
import java.util.List;

import cn.itcast.hessian.pojo.User;
import cn.itcast.hessian.service.HelloService;

public class HelloServiceImpl implements HelloService {

	@Override
	public String sayHello(String name) {
		
		return name;
	}

	@Override
	public List<User> findAllUsers() {
		
		//构造 一个用户列表
		List<User> list = new ArrayList<User>();
		
		User user = new User();
		user.setUsername("张三");
		list.add(user);
		return list;
	}

}
