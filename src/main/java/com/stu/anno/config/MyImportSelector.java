package com.stu.anno.config;

import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.type.AnnotationMetadata;


//自定义逻辑需要导入的组件
public class MyImportSelector implements ImportSelector{
	
	//返回值：导入到容器中的方法全类名
	@Override
	public String[] selectImports(AnnotationMetadata importingClassMetadata) {
		
		return new String[] {"com.stu.anno.pojo.Import_color","com.stu.anno.pojo.Import_blue"};
	}

}
