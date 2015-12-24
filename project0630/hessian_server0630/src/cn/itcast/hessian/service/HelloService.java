package cn.itcast.hessian.service;

import java.util.List;

import cn.itcast.hessian.pojo.User;

public interface HelloService {
	
	public String sayHello(String name);
	//查询用户列表
	public List<User> findAllUsers();

}
