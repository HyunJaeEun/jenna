package com.jenna.io.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jenna.io.entity.UserEntity;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {

	 UserEntity findByUserId(String userId);
	 
	 UserEntity findByEmail(String email);
	 
}
