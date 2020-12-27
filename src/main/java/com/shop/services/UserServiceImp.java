package com.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.models.Users;
import com.shop.repositories.UserRepo;

@Service
public class UserServiceImp implements UserService {

	@Autowired
	UserRepo ur;
	@Override
	public Users save(Users user) {
		ur.save(user);
		return user;
	}
	public List<Users> findAll() {
		return ur.findAll();
	}
	@Override
	public Users findById(int id) {
		if(ur.findById(id).isPresent()) {
			return ur.findById(id).get();
		}
		return null;
	}
	@Override
	public void delete(int id) {
		ur.deleteById(id);
		
	}
	@Override
	public void reset() {
		ur.reset();
		
	}
	@Override
	public Users findByEmail(String email) {
		return ur.findByEmail(email);
	}
	@Override
	public Boolean existsByEmail(String email) {
		return ur.existsByEmail(email);
	}

}
