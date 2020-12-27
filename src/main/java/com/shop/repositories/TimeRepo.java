package com.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.shop.models.VoteTime;

@Repository
public interface TimeRepo extends JpaRepository<VoteTime, Integer> {
	
}
