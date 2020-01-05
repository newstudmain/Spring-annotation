package com.stu.anno.pojo;

import org.springframework.beans.factory.annotation.Value;


public class Person {

	@Value("张三")
	private String name;
	@Value("#{20-2}")
	private int age;
	@Value("${person.nickname}")
	private String nickname;
	
	public Person() {}
	
	public Person(String name, int age) {
		super();
		this.name = name;
		this.age = age;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	@Override
	public String toString() {
		return "Person [name=" + name + ", age=" + age + ", nickname=" + nickname + "]";
	}

}
