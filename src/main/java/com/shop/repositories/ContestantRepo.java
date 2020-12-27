package com.shop.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.models.Contestant;


@Repository
public interface ContestantRepo extends JpaRepository<Contestant, Integer> {
   @Transactional
	@Modifying
	@Query(value = "update Contestant u set u.votes = u.votes + 1 where u.id = ?", 
	  nativeQuery = true)
	int updatevotes(int id);
	
   List<Contestant> findByOrderByVotesDesc();
}
