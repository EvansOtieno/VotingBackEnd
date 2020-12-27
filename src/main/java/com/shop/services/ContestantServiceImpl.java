package com.shop.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.models.Contestant;
import com.shop.repositories.ContestantRepo;
import com.shop.repositories.UserRepo;

@Service
public class ContestantServiceImpl implements ContestantService {

	@Autowired
	ContestantRepo cr;
	@Autowired
	UserRepo ur;

	@Override
	public List<Contestant> findAll() {
		List<Contestant> conts = cr.findAll();
		return conts;
	}

	@Override
	public Contestant save(Contestant cont) {
		cr.save(cont);
		return cont;
	}

	@Override
	public Contestant findById(int id) {
		if (cr.findById(id).isPresent()) {
			return cr.findById(id).get();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		cr.deleteById(id);
	}
	@Override
	public void updatevotes(int id) {
		cr.updatevotes(id);
	}

	@Override
	public List<Contestant> results() {
		List<Contestant> conts=cr.findByOrderByVotesDesc();
		return conts;
	}

}
