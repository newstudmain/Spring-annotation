package com.stu.anno.pojo;

import org.springframework.beans.factory.FactoryBean;

public class ColorFactoryBean implements FactoryBean<Color>{

	@Override
	public Color getObject() throws Exception {
		System.out.println("ColorFactoryBean...getObject()");
		return new Color();
	}

	@Override
	public Class getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}

	//是否单例？ 默认 false
	@Override
	public boolean isSingleton() {
		return false;
	}

}
