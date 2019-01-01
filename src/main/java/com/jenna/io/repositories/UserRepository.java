package com.jenna.io.repositories;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.jenna.io.entity.UserEntity;

@Repository
public interface UserRepository extends PagingAndSortingRepository<UserEntity, Long>{

	 UserEntity findByUserId(String userId);
	 
	 UserEntity findByEmail(String email);	 
	 
	 UserEntity findByEmailVerificationToken(String token); //naming rule : 1.findBy로 시작한다 
	 														// 				2. userEntity에있는 컬럼name과 일치해야함
	 
}
