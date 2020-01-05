package com.stu.anno.dao;

import org.springframework.stereotype.Repository;

@Repository
public class BookDao {

	public int lable = 1;

	public int getLabel() {
		return lable;
	}

	public void setLabel(int lable) {
		this.lable = lable;
	}

	@Override
	public String toString() {
		return "BookDao [lable=" + lable + "]";
	}
	
	
}
