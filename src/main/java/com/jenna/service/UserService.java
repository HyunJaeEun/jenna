package com.jenna.service;

import java.util.List;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.jenna.shared.dto.UserDTO;

public interface UserService extends UserDetailsService {

	UserDTO createUser(UserDTO user); //abstract method
	
	UserDTO getUserByUserId(String userId);
	
	UserDTO getUser(String email);
	
	UserDTO updateUser(String userId, UserDTO userDTO);
	
	void deleteUser(String id);

	List<UserDTO> getUsers(int page, int limit);

	boolean verifyEmailToken(String token);
}
