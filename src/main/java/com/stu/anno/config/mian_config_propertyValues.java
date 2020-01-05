package com.stu.anno.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.ConfigurableEnvironment;

import com.stu.anno.pojo.Person;

//使用@PropertySource读取外部配置文件
@PropertySource(value= {"classpath:/person.properties"})
@Configuration
public class mian_config_propertyValues {

	@Bean
	public Person person() {
		return new Person();
	}
}

/*
 * @Value 标签 
	  	基本数值
		可以写SPEL 表达式，例如： #{}
		可以写${} ,取出配置文件中的值（在运行环境变量里面的值）
		
 * public class Person {

	@Value("张三")
	private String name;
	@Value("#{20-2}")
	private int age;
 *	@Value("${person.nickname}")
	private String nickname;
 *
 *使用getEnvironment()：
 *		ConfigurableEnvironment environment = configApplicationContext.getEnvironment();
		String property = environment.getProperty("person.nickname");
		System.out.println(">>>\n"+"getProperty(\"person.nickname\"):"+property);
 * */
