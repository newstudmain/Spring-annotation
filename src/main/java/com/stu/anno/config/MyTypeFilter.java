package com.stu.anno.config;

import java.io.IOException;

import org.springframework.core.io.Resource;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.ClassMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

public class MyTypeFilter implements TypeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		// TODO Auto-generated method stub
		
		//metadataReader 读取当前正在扫描类的信息
		//metadataReaderFactory 可以获取其他任何类的信息
		
		//当前类注解信息
		AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
		//当前类信息
		ClassMetadata classMetadata = metadataReader.getClassMetadata();
		//当前类资源(路径)
		Resource resource = metadataReader.getResource();
		
		return false;
	}

}
