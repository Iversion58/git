package cn.itcast.hessian.client;

import java.net.MalformedURLException;
import java.util.List;

import org.junit.Test;

import cn.itcast.hessian.pojo.User;
import cn.itcast.hessian.service.HelloService;

import com.caucho.hessian.client.HessianProxyFactory;

/**
 * 
 * <p>Title: HessianTest</p>
 * <p>Description:hessian测试 </p>
 * <p>Company: www.itcast.com</p> 
 * @author	传智.燕青
 * @date	2015-9-20
 * @version 1.0
 */
public class HessianTest {
	
	@Test
	public void testHessian() throws MalformedURLException{
		//创建客户端代理工厂
		HessianProxyFactory factory = new HessianProxyFactory();
		
		//创建代理对象 ，基于服务端接口的代理对象
		HelloService helloService = (HelloService) factory
				.create(HelloService.class,
						"http://localhost:8080/hessian_server0630/hessian/helloService");
		//调用远程接口
		String sayHello = helloService.sayHello("张三");
		System.err.println(sayHello);
		
		List<User> list = helloService.findAllUsers();
		
		System.out.println(list);
		
	}

}
