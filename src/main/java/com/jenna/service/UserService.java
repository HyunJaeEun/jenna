package com.jenna.service;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jenna.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO createUser(UserDTO user); //abstract method
	
	UserDTO getUserByUserId(String userId);
	
	UserDTO getUser(String email);
	
	UserDTO updateUser(String userId, UserDTO userDTO);
	
	void deleteUser(String id);
}
