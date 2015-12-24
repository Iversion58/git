package cn.itcast.yycg.base.web.action;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.springframework.beans.factory.annotation.Autowired;

import cn.itcast.yycg.base.annotation.ActionModelName;
import cn.itcast.yycg.base.service.ServiceFacade;
import cn.itcast.yycg.base.web.result.ProcessResult;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public abstract class BaseAction<T> extends ActionSupport implements ModelDriven<T>,
		ServletRequestAware, ServletResponseAware {
	
	 //处理结果
	private ProcessResult processResult;
	
	
	
	public ProcessResult getProcessResult() {
		return processResult;
	}
	
	/**
	 * 
	 * <p>Title: setProcessResult</p>
	 * <p>Description:此方法在BaseAction的子类中调用，设置返回页面的结果对象 </p>
	 * @param processResult
	 */
	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	//一个请求过来，spring对actoin进行实例化，调用无参的构造方法
	//在无参的构造方法中对子类指定的T进行实例化
	public BaseAction(){
		try {
			// 获取父类,就是BaseAction<T>
			Type genericSuperclass = this.getClass().getGenericSuperclass();
			if (!(genericSuperclass instanceof ParameterizedType)) {//如果父类不是一个泛型类型说明当前是cglib代理对象
				//取出genericSuperclass的父类
				genericSuperclass = this.getClass().getSuperclass().getGenericSuperclass();
			}
			
			// 将genericSuperclass转为泛型类型
			if (genericSuperclass instanceof ParameterizedType) {
				ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;

				// parameterizedType里边存储了子类中指定的具体类型
				// 从parameterizedType里边获取具体
				// T类型，就是ModelDriven指定的类型即模型对象类型即QueryVo的类型
				Type entityType = parameterizedType.getActualTypeArguments()[0];
				Class<T> entityclass = (Class<T>) entityType;
				// entityclass就是queryVo类型
				// 对entityclass通过反射进行实例化

				model = entityclass.newInstance();

			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//模型对象
	private T model;
	//此方法由框架中modelDriven拦截器调用，取出action中@ActionModelName指定的类的实例
	@Override
	public T getModel() {
		
		return model;
	}
	
	//定义一个拦截器SetModelInterceptor在modelDriven拦截器执行之间去执行，通过反射实例 action中@ActionModelName指定的类
	//此方法由SetModelInterceptor拦截器调用
//	public void setModel(T object){
//		this.object = object;
//	}
	// 注入serviceFacade
	@Autowired
	protected ServiceFacade serviceFacade;
	
	// 定义request和response
	protected HttpServletRequest request;
	protected HttpServletResponse response;

	@Override
	public void setServletResponse(HttpServletResponse response) {
		this.response = response;

	}

	@Override
	public void setServletRequest(HttpServletRequest request) {
		this.request = request;

	}

	
	//将此方法定义模型对象的初化方法，将object转为正确的queryVo类型
//	public abstract void initQueryVo();

}
