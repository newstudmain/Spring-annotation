package com.stu.anno.config;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

public class LinuxCondition implements Condition{
	/**
	 * 判断是否是linux系统
	 * ConditionContext:判断条件能使用的上下文（环境）
	 * AnnotatedTypeMetadata:注释信息
	 */

	@Override
	public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
		String osName = context.getEnvironment().getProperty("os.name");
		if(osName.contains("Linux")) {
			return true;
		}
		return false;
	}
}
