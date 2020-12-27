package com.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.models.Student;
import com.shop.repositories.StudentRepo;

@Service
public class StudentServiceImpl implements StudentService {

	@Autowired
	StudentRepo sr;
	
	@Override 
	public List<Student> findAll() {
		return sr.findAll();
	}

	@Override
	public Student save(Student std) {
		sr.save(std);
		return std;
	}

	@Override
	public void delete(int id) {
		sr.deleteById(id);
	}
	
	@Override
	public Student findById(int id) {
		if(sr.findById(id).isPresent()) {
			return sr.findById(id).get();
		}
		return null;
	}

	@Override
	public Student findByEmail(String email) {
		return sr.findByEmail(email);
	}

	@Override
	public void updatevotingstatus(int id) {
		sr.updatestatus(id);
		
	}
	
	@Override
	public int count() {
		return (int)sr.count();
	}

}
