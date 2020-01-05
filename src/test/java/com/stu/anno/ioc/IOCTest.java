package com.stu.anno.ioc;

import org.junit.jupiter.api.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stu.anno.config.main_config;
import com.stu.anno.config.main_config_AutoWired;
import com.stu.anno.config.main_config_LifeCycle;
import com.stu.anno.config.mian_config_propertyValues;
import com.stu.anno.config.mian_config_scope;
import com.stu.anno.pojo.Boss;
import com.stu.anno.pojo.Car;
import com.stu.anno.pojo.Person;
import com.stu.anno.service.BookService;

public class IOCTest {
	
	@SuppressWarnings("resource")
	
	public void test1() {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(main_config.class);
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
	}
	/*
	 * 	main_config
		bookController
		bookDao
		bookService
		person_bean
	 * */
	
	@SuppressWarnings("resource")
	public void test2() {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(mian_config_scope.class);
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		
		Person p1 = configApplicationContext.getBean("person",Person.class);
		Person p2 = configApplicationContext.getBean("person",Person.class);
		
		System.out.println(p1);
		System.out.println(p1==p2);
		
	}
	
	@SuppressWarnings("resource")
	
	public void test3() {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(mian_config_scope.class);
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		System.out.println("-----");
		Object bean1 = configApplicationContext.getBean("colorFactoryBean");
		Object bean2 = configApplicationContext.getBean("colorFactoryBean");
		Object bean3 = configApplicationContext.getBean("&colorFactoryBean");
		System.out.println(bean1.getClass());
		System.out.println(bean2.getClass());
		System.out.println(bean3.getClass());
		System.out.println(bean1 == bean2);
		
//		String os = configApplicationContext.getEnvironment().getProperty("os.name");
//		System.out.println(os);//Windows 10

	}
	
	@SuppressWarnings("resource")
	public void test4() {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(main_config_LifeCycle.class);
		
		System.out.println("configApplicationContext...");
		
		//Car car = configApplicationContext.getBean("car1",Car.class);
		
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		
		configApplicationContext.close();
	}
	
	@SuppressWarnings("resource")
	public void test5() {
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(mian_config_propertyValues.class);
		
		System.out.println("configApplicationContext...");
		
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		
		System.out.println("======\n"+ configApplicationContext.getBean("person"));
		
		ConfigurableEnvironment environment = configApplicationContext.getEnvironment();
		String property = environment.getProperty("person.nickname");
		System.out.println(">>>\n"+"getProperty(\"person.nickname\"):"+property);
		
		configApplicationContext.close();
	}
	
	@SuppressWarnings("resource")
	@Test
	public void test6() {
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext configApplicationContext = new AnnotationConfigApplicationContext(main_config_AutoWired.class);
		
		AnnotationConfigApplicationContext proConfigApplicationContext = new AnnotationConfigApplicationContext();
		proConfigApplicationContext.getEnvironment().setActiveProfiles("test","dev");
		proConfigApplicationContext.register(main_config_AutoWired.class);
		proConfigApplicationContext.refresh();
		
		System.out.println("configApplicationContext...");
		
		String[] names = configApplicationContext.getBeanDefinitionNames();
		for (String name : names) {
			System.out.println(name);
		}
		
		String[] pronames = proConfigApplicationContext.getBeanNamesForType(ComboPooledDataSource.class);
		System.out.println("prof..e");
		for (String name : pronames) {
			System.out.println(name);
		}
		System.out.println("prof..n");

		BookService bookService = configApplicationContext.getBean(BookService.class);
		bookService.print();
		
		Boss boss = configApplicationContext.getBean(Boss.class);
		System.out.println("setCar(Car car)..."+boss);
		
		Boss iboss = configApplicationContext.getBean(Boss.class);
		System.out.println("IOC..."+iboss);
		
		//configApplicationContext.close();
	}
}





















