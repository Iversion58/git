package cn.itcast.yycg.base.web.interceptor;

import org.springframework.beans.BeanUtils;
import org.springframework.core.annotation.AnnotationUtils;

import cn.itcast.yycg.base.annotation.ActionModelName;
import cn.itcast.yycg.base.web.action.BaseAction;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class SetModelInterceptor extends AbstractInterceptor  {

	@Override
	public String intercept(ActionInvocation actionInvocation) throws Exception {
		//得到当前执行action实例
		BaseAction baseAction = (BaseAction) actionInvocation.getAction();
		
		//得到action中ActionModelName注解指定类路径
		//找到action类中写的注解ActionModelName
		ActionModelName actionModelName = AnnotationUtils.findAnnotation(baseAction.getClass(), ActionModelName.class);
		if(actionModelName!=null ){
			//获取注解中指定的值，模型对象的类路径
			String queryVoClassName = actionModelName.value();
			//通过反射创建模型对象实例
			Object modelObj = Class.forName(queryVoClassName).newInstance();
			//调用BaseAction下的setModel方法设置模型对象
			//baseAction.setModel(modelObj);
			//调用初始化模型对象方法
			//baseAction.initQueryVo();
		}
		return actionInvocation.invoke();
	}

}
