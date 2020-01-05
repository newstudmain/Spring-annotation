package com.stu.anno.config;

import java.beans.PropertyVetoException;

import javax.sql.DataSource;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.stu.anno.dao.BookDao;
import com.stu.anno.pojo.Boss;
import com.stu.anno.pojo.Car;

@PropertySource("classpath:/datasource.properties")
@Configuration
@ComponentScan({"com.stu.anno.controller","com.stu.anno.service","com.stu.anno.dao","com.stu.anno.pojo"})
public class main_config_AutoWired {
	
	@Primary
	@Bean("bookDao2")
	public BookDao bookdao() {
		BookDao bookDao = new BookDao();
		bookDao.setLabel(2);
		return bookDao;
	}
	
	@Bean
	public Car car() {
		return new Car();
	}
	
//	@Bean
//	public Boss boss() {
//		return new Boss(car());
//	}

	
    @Value("${db.username}")//取出properties中的值
    private String username;

    private String driveClassName;

    private StringValueResolver resolver;

    //值解析器
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.resolver = resolver;
        this.driveClassName = this.resolver.resolveStringValue("${db.driveClassName}");
    }
	
    @Profile("test")
    @Bean("dataSourceTest")
    public DataSource dataSourceTest(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setDriverClass(driveClassName);
        return dataSource;
    }

    @Profile("dev")
    @Bean("dataSourceDev")
    public DataSource dataSourceDev(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
        dataSource.setDriverClass(driveClassName);
        return dataSource;
    }

    @Profile("prod")
    @Bean("dataSourceProd")
    public DataSource dataSourceProd(@Value("${db.password}") String password) throws PropertyVetoException {
        ComboPooledDataSource dataSource = new ComboPooledDataSource();
        dataSource.setUser(username);
        dataSource.setPassword(password);
        dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prod");
        dataSource.setDriverClass(driveClassName);
        return dataSource;
    }

	
}

/*
 * 自动装配
 * 		Spring 利用依赖注入（DI），完成对IOC容器中各个依赖关系赋值
 * 
	  		1. @Autowired :自动注入
	  			默认优先按照 类型 去容器中找对应的组件，applicationContext.getBean(BookRepository.class)，找到就赋值。
	  				public class BookService {
						@Autowired
						private BookDao bookDao;
					}
					//
		  				BookService...com.stu.anno.dao.BookDao@69e1dd28
						IOC...com.stu.anno.dao.BookDao@69e1dd28

				如果找到多个相同类型的组件，再将属性名称作为组件的id 去容器中查找applicationContext.getBean("bookRepository")
						@Bean("bookdao2")
						public BookDao bookdao() {
							BookDao bookDao = new BookDao();
							bookDao.setLabel(2);
							return bookDao;
						}
					//
						报错：expected single matching bean but found 2: bookDao,bookdao2	
						原因：@Autowired private BookDao /bookdao/(bookDao);
					//
					 	BookService...BookDao [lable=1]
					
				使用 @Qualifier("bookRepository") 指定装配组件
						@Qualifier("bookDao2")
						@Autowired
						private BookDao bookDao;
					//
					 	BookService...BookDao [lable=2]
					 	
				自动装配默认一定要将属性赋值好，没有就会报错。
						//@Repository
						public class BookDao
						---
						//@Bean("bookDao2")
						public BookDao bookdao()
					//
						报错:
						nested exception is org.springframework.beans.factory.NoSuchBeanDefinitionException: 
						No qualifying bean of type 'com.stu.anno.dao.BookDao' available: 
						expected at least 1 bean which qualifies as autowire candidate. Dependency annotations: 
						{@org.springframework.beans.factory.annotation.Qualifier(value=bookDao2), 
						@org.springframework.beans.factory.annotation.Autowired(required=true)}
						
				可以使用@Autowired(required = false)来配置非必须的注入，有则注入，没有就算了。
						@Qualifier("bookDao2")
						@Autowired(required = false)
						private BookDao bookDao;
					//
						BookService...null
						
				@Primary 让Spring进行自动装配的时候，默认选择需要装配的bean，也可以继续使用@Qualifier 指定需要装配的bean的名称
						//@Qualifier("bookDao2")
						@Autowired(required = false)
						private BookDao bookDao;
						---
						@Primary
						@Bean("bookDao2")
						public BookDao bookdao()
					//
					 	BookService...BookDao [lable=2]
				
				@Qualifier 的权重大于 @Primary，如果指定了@Qualifier 则 @Primary失效
					

 * 			2. @Resource & @Inject :自动注入
				Spring 还支持使用 @Resource（JSR250）和 @Inject（JSR330）[Java规范的注解]
				
				AutowiredAnnotationBeanPostProcessor 完成解析自动装配功能
				
				@Resource
						可以和 @Autowired 一样实现自动注入功能，默认是按照组件名称进行装配的。
						没有能支持 @Primary 功能，没有支持 @Autowired(required = false)
							//@Qualifier("bookDao2")
							//@Autowired(required = false)
							@Resource
							private BookDao bookDao;
							---
							@Primary
							@Bean("bookDao2")
							public BookDao bookdao()
						//
						 	BookService...BookDao [lable=1]
						 	
				@Inject
						@Inject 需要导入 javax.inject 依赖才能使用，和 @Autowired 功能一样，但没有 required=false 属性值设定。
							//@Qualifier("bookDao2")
							//@Autowired(required = false)
							//@Resource
							@Inject
							private BookDao bookDao;
							---
							@Primary
							@Bean("bookDao2")
							public BookDao bookdao()
						//
							BookService...BookDao [lable=2]
							
				区别
						@Autowired 是Spring定义的，@Resource 和@Inject 都是Java的规范
							
 * 			3. 自动装配-方法、构造器位置的自动装配
 
				@Autowired 标注在方法上
						标注在方法上，Spring 容器创建当前对象，就会调用方法，完成赋值。方法使用的参数，自定义类型的值从IOC容器中获取
							@Autowired
							public void setCar(Car car) {
								this.car = car;
							}
						//
						    setCar(Car car)...Boss [car=com.stu.anno.pojo.Car@3d5c822d]
							IOC...Boss [car=com.stu.anno.pojo.Car@3d5c822d]
							
				@Autowired 标注在构造器
						默认加在IOC容器中的组件，容器启动会调用无参构造器创建对象，再进行赋值操作
						构造器也是从IOC容器中获取，如果组件只有一个有参构造器，这个有参构造器的 @Autowired 可以省略，也是自动从IOC容器中获取			
							@Autowired
							public Boss(Car car) {
							    this.car = car;
							    System.out.println("Boss 的有参构造器...");
							}
						//
						 	Boss 的有参构造器...
						 	setCar(Car car)...Boss [car=com.stu.anno.pojo.Car@71238fc2]
							IOC...Boss [car=com.stu.anno.pojo.Car@71238fc2]
							
				@Autowired 标注在参数上
						参数也是从IOC容器中获取
						@Bean+方法参数；参数从容器中获取，可以省略@Autowired
							public Boss(@Autowired Car car) {
							    this.car = car;
							    System.out.println("Boss 的有参构造器...");
							}
						//
						 	Boss 的有参构造器...
						 	setCar(Car car)...Boss [car=com.stu.anno.pojo.Car@71238fc2]
							IOC...Boss [car=com.stu.anno.pojo.Car@71238fc2]	
							
						@Bean
						public Color color(@Autowired Car car) {
						    return new Color(car);
						}	
						
 * 			4. Aware注入Spring底层组件&原理
 
 				Aware 接口，提供了类似回调函数的功能
				自定义组件想要使用Spring 容器底层的一些组件（Application Context，Bean Factory）
				自定义组件需要实现 xxxAware 接口；在创建对象的时候，会调用接口规定的方法注入相关组件
				
				XXXAware 的功能使用  XXXProcessor 处理的.
				ApplicationContextAware >> ApplicationContextAwareProcessor(implements BeanPostProcessor)
				
				Aware接口
					 ApplicationContextAware	//自动注入IOC容器
					 ApplicationEventPublisherAware		//注入事件派发器
					 BeanClassLoaderAware		//类加载器
					 BeanFactoryAware		//Bean工厂
					 BeanNameAware		//Bean名字
					 BootstrapContextAware		//
					 EmbeddedValueResolverAware		//Embedded值解析器
					 EnvironmentAware		//环境
					 ImportAware	//导入相关的
					 LoadTimeWeaverAware	//导入相关的
					 MessageSourceAware		//国际化
					 NotificationPublisherAware		//发送通知的支持
					 ResourceLoaderAware		//资源加载器
					 SchedulerContextAware
					 ServletConfigAware
					 ServletContextAware
				
					@Component
					public class Red implements ApplicationContextAware,BeanNameAware,EmbeddedValueResolverAware{
					
					    private ApplicationContext applicationContext;
					
					    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
					        System.out.println("传入的IOC " + applicationContext);
					        this.applicationContext = applicationContext;
					    }
					
					    public void setBeanName(String name) {
					        System.out.println("当前bean的名字" + name);
					    }
					
					    public void setEmbeddedValueResolver(StringValueResolver resolver) {
					        String value = resolver.resolveStringValue("你好${os.name} 我是#{20*20}");
					        System.out.println("解析的字符串：" + value);
					    }
					
					}
				//
					当前bean的名字red
					解析的字符串：你好Windows 10 我是400
					传入的IOC org.springframework.context.annotation.AnnotationConfigApplicationContext@27fe3806: startup date [Sat Jan 04 20:29:22 CST 2020]; root of context hierarchy
 
 * 			5. 自动装配-@Profile环境搭建
 
 				Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能。
				开发环境、测试环境、正式环境	 /数据源切换
				
				 添加 数据源和jdbc驱动 pom 依赖
					<dependency>
					    <groupId>com.mchange</groupId>
					    <artifactId>c3p0</artifactId>
					    <version>0.9.5.2</version>
					</dependency>
					
					<dependency>
					    <groupId>com.oracle.jdbc</groupId>
					    <artifactId>ojdbc8</artifactId>
					    <version>12.2.0.1</version>
					</dependency>
					
				//
					dataSourceTest
					dataSourceDev
					dataSourceProd

				@Profile:指定组件在哪个环境中才会被注册到容器中，不指定，任何环境下都能注册到容器中。(可以标在类上)
					加了环境标识的Bean，只有这个环境被激活的时候才能进注册到容器中，默认是default
					    @Profile("test")
					    @Bean("dataSourceTest")
					    public DataSource dataSourceTest
					    ---
					    @Profile("dev")
					    @Bean("dataSourceDev")
					    public DataSource dataSourceDev
					    ---
					    @Profile("prod")
					    @Bean("dataSourceProd")
					    public DataSource dataSourceProd
					//
						没有注册Bean
				    ==》
					    @Profile("default")
					    @Bean("dataSourceTest")
					    public DataSource dataSourceTest
					    ---
					    @Profile("dev")
					    @Bean("dataSourceDev")
					    public DataSource dataSourceDev
					    ---
					    @Profile("prod")
					    @Bean("dataSourceProd")
					    public DataSource dataSourceProd
					//
						dataSourceTest
						
				环境切换：
						1. 命令行动态参数
								在虚拟机参数位置加载 -Dspring.profiles.active=test
							//
							 	dataSourceTest
						2. 代码
								public AnnotationConfigApplicationContext(Class<?>... annotatedClasses) {
									this();
									register(annotatedClasses);
									refresh();
								}
								==>
								AnnotationConfigApplicationContext proConfigApplicationContext = new AnnotationConfigApplicationContext();
								proConfigApplicationContext.getEnvironment().setActiveProfiles("test","dev");
								proConfigApplicationContext.register(main_config_AutoWired.class);
								proConfigApplicationContext.refresh();
							//
								dataSourceTest
								dataSourceDev
 * */


















