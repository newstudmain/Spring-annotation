
package com.stu.anno.pojo;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Component;

@Component
public class Dog {

	public Dog() {
		System.out.println("Dog...constructor()...");
	}
	
	@PostConstruct
	public void init_cus() {
		System.out.println("Dog...init_cus()...");
	}
	
	@PreDestroy
	public void destroy_cus() {
		System.out.println("Dog...destroy_cus...");
	}
}
