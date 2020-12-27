package com.shop.services;

import java.util.List;

import com.shop.models.Contestant;

public interface ContestantService {

	List<Contestant> findAll();
	List<Contestant> results();
	Contestant save(Contestant cont);
	Contestant findById(int id);
	void delete(int id);
	void updatevotes(int id);
	
}
