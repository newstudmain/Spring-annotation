package com.stu.anno.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.ComponentScans;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import com.stu.anno.pojo.Person;

//配置类==配置文件
@Configuration	//告诉Spring这是一个配置类
@ComponentScans(value = {
		@ComponentScan(value = "com.stu",excludeFilters =  
			{	//excludeFilters 	 参数Filter[]		过滤类型FilterType		具体值value	[按照规则排除某些包的扫描]
				@Filter( type = FilterType.ANNOTATION,	classes = {Controller.class} )
				/*	main_config
					bookDao
					bookService
					person_bean
				 * */
			}
			
		)
})
public class main_config {

	@Bean(value = "person_bean")	//给容器中注册一个Bean，类型为返回值类型，id默认为方法名
	public Person person_id1() {
		return new Person("lishi",15);
	}
	
//	@Bean	//给容器中注册一个Bean，类型为返回值类型，id默认为方法名
//	public Person person_id2() {
//		return new Person("lishi",15);
//	}
}

/* 按照规则扫描
 * 		排除：
 * 			excludeFilters 	 参数Filter[]		过滤类型FilterType(排除规则)		具体值value
 * 		包含：
 *  		includeFilters 
 *  						@ComponentScans(value = {
								@ComponentScan(value = "com.stu",includeFilters =  
									{	//excludeFilters 	 参数Filter[]		过滤类型FilterType		具体值value	[按照规则排除某些包的扫描]
										@Filter( type = FilterType.ANNOTATION,	value = {Controller.class},us )
										*	main_config
											bookDao
											bookService
											person_bean
										 *
									}
									,useDefaultFilters = "false"	//禁用默认规则
								)
							)
 *  
 * 过滤类型FilterType(排除规则)	
	  		ANNOTATION：注解类型
			ASSIGNABLE_TYPE：ANNOTATION：指定的类型，包括子类，实现类
			ASPECTJ：按照Aspectj的表达式，基本上不会用到
			REGEX：按照正则表达式
			CUSTOM：自定义规则 
				MyTypeFilter 
 * */


