package com.stu.anno.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.type.AnnotatedTypeMetadata;

import com.stu.anno.pojo.ColorFactoryBean;
import com.stu.anno.pojo.Import_color;
import com.stu.anno.pojo.Import_red;
import com.stu.anno.pojo.Person;

@Configuration
@Conditional({WindowsCondition.class})
@Import({Import_color.class,Import_red.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class})
public class mian_config_scope {

	@Bean("person")
	@Scope("singleton")
	@Lazy
	public Person person() {
		System.out.println("mian_config_scope...person()");
		return new Person("name_scope",02);
	}
	
	@Bean("zhangsan")
	public Person person01() {
		System.out.println("mian_config_scope...person01()");
		return new Person("zhangsan",001);
	}
	
	@Bean("lisi")
	public Person person02() {
		System.out.println("mian_config_scope...person02()");
		return new Person("lisi",002);
	}
	
	@Bean("bill")
	//@Conditional({WindowsCondition.class})
	public Person person03() {
		return new Person("bill",60);
	}
	
	@Conditional({LinuxCondition.class})
	@Bean("linus")
	public Person person04() {
		return new Person("linus",50);
	}
	
	@Bean
	public ColorFactoryBean colorFactoryBean() {
		return new ColorFactoryBean();
	}
	/*
	 * 	Object bean1 = configApplicationContext.getBean("colorFactoryBean");
		Object bean2 = configApplicationContext.getBean("colorFactoryBean");
		Object bean3 = configApplicationContext.getBean("&colorFactoryBean");//获取工厂类本身
		System.out.println(bean1.getClass());
		System.out.println(bean2.getClass());
		System.out.println(bean3.getClass());
		System.out.println(bean1 == bean2);
	 * ------
	 *  ColorFactoryBean...getObject()
		ColorFactoryBean...getObject()
		class com.stu.anno.pojo.Color
		class com.stu.anno.pojo.Color
		class com.stu.anno.pojo.ColorFactoryBean
		false
	 * */
}
/*
 * @Scope()
 * 
 * prototype	多实例: ioc容器启动不会调用方法创建对象放到ioc容器中，每次获取时才会创建对象
 * singleton	单例(default)： ioc容器启动会调用方法创建对象放到ioc容器中
 * request		同一次请求
 * session		同一次会话
 * 
 * 懒加载
 * 		singleton	单例(default)： ioc容器启动会调用方法创建对象放到ioc容器中
 * 		让容器启动会不调用方法创建对象放到ioc容器中
 * 		
		mian_config_scope
		person
		mian_config_scope...person()
		person [name=name_scope, age=2]
		true
 * 
 * 按照一定的条件，满足条件注册Bean
		@Conditional({}) 按照一定的条件进行判断，满足条件给容器中注册bean
							public @interface Conditional {
								Class<? extends Condition>[] value();
							}
							-----
				  			public interface Condition {
								boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata);
							}
		在类上使用表示满足条件会执行这个类，如果不满足则类中所有方法都不会加载
		在方法上使用表示，满足条件会执行这个方法
 * 
 * 
 * 给容器注册组建：
 * 		1. 包扫描 + 组件扫描 @Controller/@service/@component/@Repository
 * 		2. @Bean [导入第三方包组件]
 * 		3. @Import [快速导入组件]
			   ImportSelector [返回需要导入的全类名数组]
			   ImportBeanDefinitionRegistrar [手动注册Bean到容器中]
 *		4.FactoryBean [工厂Bean]
 *				默认获取的是工厂Bean的getObject()创建的对象
 *				&工厂Bean /获取工厂类本身
*/
