package com.stu.anno.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

import com.stu.anno.pojo.Car;
import com.stu.anno.pojo.Cat;
import com.stu.anno.pojo.Dog;

/*
 * Bean 的生命周期
 * 		Bean --> 初始化 --> 销毁
 * 管理Bean 的生命周期
 * 		自定义初始化和销毁过程
 *
 * 构造(对象创建)	
 * 		单例：在容器启动时创建对象
 * 		多例：每次获取时创建对象	
 * 
 * postProcessBeforeInitialization
 * 初始化： 对象创建完成，并赋值好，调用初始化方法
 * postProcessAfterInitialization
 * 
 * 销毁： 容器销毁的时候
 * 
 * 	1. 通过 @Bean指定  init-method / destroy-method
 * 	2. 实现两个接口 InitializingBean(初始逻辑)/ DisposableBean(销毁逻辑)
 * 	3. 使用JSR250 规范注解 @PostConstruct(Bean创建完成，属性赋值完成后)/ @PreDestroy(容器销毁Bean之前通知销毁)
 * 	4. 通过实现 (Bean的后置处理器) 接口 ，重写方法：
		 postProcessBeforeInitialization(Object bean, String beanName) /在初始化方法调用之前调用
		 postProcessAfterInitialization(Object bean, String beanName) /在初始化方法调用之后调用
			 ==>原理：
					 遍历得到容器中所有的BeanPostProcessor 
					 挨个执行 postProcessBeforeInitialization
					 一旦返回null，跳出for循环，不会执行后面的BeanPostProcessor
			 
						 populateBean() 给bean进行属性赋值
							initializeBean(){
							    applyBeanPostProcessorsBeforeInitialization();
							    invokeInitMethods();    // 执行自定义初始化
							    applyBeanPostProcessorsAfterInitialization();
							}
			bean赋值、注入其他组件、@Autowired注解、生命周期注解、@Async ……都是 BeanPostProcessor实现的
				
 * 
 * 
 * */

@Configuration
@ComponentScan("com.stu.anno.pojo")
public class main_config_LifeCycle {
	
//	@Bean(name = "car",initMethod = "init",destroyMethod = "destroy")
//	public Car car() {
//		return new Car();
//	}
	//单实例Bean在容器关闭时自动销毁
	/*
	 *  Car...constructor()...
		Car...init()...
		configApplicationContext...
		org.springframework.context.annotation.internalConfigurationAnnotationProcessor
		org.springframework.context.annotation.internalAutowiredAnnotationProcessor
		org.springframework.context.annotation.internalRequiredAnnotationProcessor
		org.springframework.context.annotation.internalCommonAnnotationProcessor
		org.springframework.context.event.internalEventListenerProcessor
		org.springframework.context.event.internalEventListenerFactory
		main_config_LifeCycle
		car
		Car...destroy()...
	 * */
	
//	@Bean(name = "car1",initMethod = "init",destroyMethod = "destroy")
//	@Scope("prototype")
//	public Car car1() {
//		return new Car();
//	}
	//多实例Bean不进行销毁，需手动销毁。容器启动时不自动创建对象
	/*
	 *  configApplicationContext...
		org.springframework.context.annotation.internalConfigurationAnnotationProcessor
		org.springframework.context.annotation.internalAutowiredAnnotationProcessor
		org.springframework.context.annotation.internalRequiredAnnotationProcessor
		org.springframework.context.annotation.internalCommonAnnotationProcessor
		org.springframework.context.event.internalEventListenerProcessor
		org.springframework.context.event.internalEventListenerFactory
		main_config_LifeCycle
		car1
	 * */
	
//	@Bean
//	public Cat Cat() {
//		return new Cat();
//	}
	/*
	 *  Cat...constructor()...
		Cat...afterPropertiesSet()...
		configApplicationContext...
		org.springframework.context.annotation.internalConfigurationAnnotationProcessor
		org.springframework.context.annotation.internalAutowiredAnnotationProcessor
		org.springframework.context.annotation.internalRequiredAnnotationProcessor
		org.springframework.context.annotation.internalCommonAnnotationProcessor
		org.springframework.context.event.internalEventListenerProcessor
		org.springframework.context.event.internalEventListenerFactory
		main_config_LifeCycle
		Cat
		Cat...destroy()...
	 * */
	
	public Dog dog() {
		return new Dog();
	}
	/*
	 *  Dog...constructor()...
		Dog...init_cus()...
		configApplicationContext...
		org.springframework.context.annotation.internalConfigurationAnnotationProcessor
		org.springframework.context.annotation.internalAutowiredAnnotationProcessor
		org.springframework.context.annotation.internalRequiredAnnotationProcessor
		org.springframework.context.annotation.internalCommonAnnotationProcessor
		org.springframework.context.event.internalEventListenerProcessor
		org.springframework.context.event.internalEventListenerFactory
		main_config_LifeCycle
		dog
		Dog...destroy_cus...
	 * */
}


