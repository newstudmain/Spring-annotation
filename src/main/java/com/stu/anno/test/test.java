package com.stu.anno.test;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.stu.anno.config.main_config;
import com.stu.anno.pojo.Person;

public class test {

	public static void main(String[] args) {
//		ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("beans.xml");
//		Person person = applicationContext.getBean("person", Person.class);
//		System.out.println(person);
		
		@SuppressWarnings("resource")
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(main_config.class);
		Person person = applicationContext.getBean(Person.class);
		System.out.println(person);
		
		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println(name);//person_bean
		}
		
		
	}
}
