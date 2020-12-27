package com.shop.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shop.models.VoteTime;
import com.shop.repositories.TimeRepo;

@Service
public class TimeImpl implements Time {

	@Autowired
	TimeRepo tr;
	@Override
	public VoteTime find() {
		if (tr.findById(1).isPresent()) {
			return tr.findById(1).get();
		}
		return new VoteTime();
	}

	@Override
	public VoteTime save(VoteTime vt) {
		tr.save(vt);

		return vt;
	}

}
