package cn.itcast.yycg.base.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Inherited//解决cglib代理取不到注解问题
@Target(value={ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ActionModelName {

	//属性
	String value() default "";
}

