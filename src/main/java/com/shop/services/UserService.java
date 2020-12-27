package com.shop.services;

import java.util.List;

import com.shop.models.Users;

public interface UserService {
	List<Users> findAll();
	Users save(Users user);
	Users findById(int id);
	Users findByEmail(String email);
	void delete(int id);
	Boolean existsByEmail(String email);
	void reset();
}
