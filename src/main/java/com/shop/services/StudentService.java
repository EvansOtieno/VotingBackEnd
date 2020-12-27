package com.shop.services;

import java.util.List;

import com.shop.models.Student;

public interface StudentService {

	List<Student> findAll();
	Student save(Student std);
	Student findById(int id);
	Student findByEmail(String email);
	void updatevotingstatus(int id);
	int count();
	void delete(int id);
}
