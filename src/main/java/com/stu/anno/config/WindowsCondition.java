package com.stu.anno.config;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class WindowsCondition implements Condition{
	/**
	 * 判断是否是Windows系统
	 * ConditionContext:判断条件能使用的上下文（环境）
	 * AnnotatedTypeMetadata:注释信息
	 */

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		
		//1、能获取到ioc使用的beanfactory
		ConfigurableListableBeanFactory beanFactory = context.getBeanFactory();
		//2、获取类加载器
		ClassLoader classLoader = context.getClassLoader();
		//3、获取当前环境信息
		Environment environment = context.getEnvironment();
		//4、获取到bean定义的注册类
		BeanDefinitionRegistry registry = context.getRegistry();
		
		String osName = environment.getProperty("os.name");
		if(osName.contains("Windows")) {
			return true;
		}
		return false;
	}

}
