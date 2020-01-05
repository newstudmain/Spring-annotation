package com.stu.anno.service;

import javax.annotation.Resource;
import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.stu.anno.dao.BookDao;

@Service
public class BookService {
	
	//@Qualifier("bookDao2")
	//@Autowired(required = false)
	//@Resource
	@Inject
	private BookDao bookDao;
	
	public void print() {
		System.out.println("BookService..."+bookDao);
	}

}
