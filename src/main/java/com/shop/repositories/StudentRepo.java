package com.shop.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.shop.models.Student;

@Repository
public interface StudentRepo extends JpaRepository<Student, Integer> {

	void deleteByEmail(String email);

	Student findByEmail(String email);

	@Transactional
	@Modifying
	@Query(value = "update Student u set u.voted = true  where u.id = ?", nativeQuery = true)
	void updatestatus(int id);
    
}
