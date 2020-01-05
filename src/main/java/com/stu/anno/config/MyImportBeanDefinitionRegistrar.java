package com.stu.anno.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

import com.stu.anno.pojo.RainBow;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

	//AnnotationMetadata importingClassMetadata 当前类的注释信息
	//BeanDefinitionRegistry registry 注册类BeanDefinition
	//需要添加到容器中的Bean	[调用 BeanDefinitionRegistry.registerBeanDefinition(...) 手工注册]
	@Override
	public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

		//importingClassMetadata.
		
		boolean d1 = registry.containsBeanDefinition("com.stu.anno.pojo.Import_red");
		boolean d2 = registry.containsBeanDefinition("com.stu.anno.pojo.Import_blue");
		
		if(d1 && d2) {
			RootBeanDefinition beanDefinition = new RootBeanDefinition(RainBow.class);
			registry.registerBeanDefinition("rainBow",beanDefinition);
		}
	}

}
