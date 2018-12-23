package com.jenna.service.impl;

import java.util.ArrayList;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.jenna.exceptions.UserServiceException;
import com.jenna.io.entity.UserEntity;
import com.jenna.io.repositories.UserRepository;
import com.jenna.service.UserService;
import com.jenna.shared.Utils;
import com.jenna.shared.dto.UserDTO;
import com.jenna.ui.model.response.ErrorMessages;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	@Autowired
	Utils utils;
	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;


	@Override
	public UserDTO getUserByUserId(String userId) {
	UserEntity storedUserDetails = userRepository.findByUserId(userId);
	    
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(storedUserDetails, returnValue);
		      
		return returnValue;
	}

	@Override
	public UserDTO createUser(UserDTO user) {
		 if(userRepository.findByEmail(user.getEmail()) != null)
	         throw new RuntimeException("Record already exists.");
	      
	      UserEntity userEntity = new UserEntity();
	      BeanUtils.copyProperties(user, userEntity); // fields should match each other
	      
	      String publidUserId = utils.generateUserId(30);
	      userEntity.setUserId(publidUserId);
	      userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(user.getPassword()));
	      
	      UserEntity storedUserDetails = userRepository.save(userEntity);
	      
	      UserDTO returnValue = new UserDTO();
	      BeanUtils.copyProperties(storedUserDetails, returnValue);
	      
	      return returnValue;
	}

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
		
		// if it is not null, it returns user object which is spring object and implements userDetails
		return new User(userEntity.getEmail(), userEntity.getEncryptedPassword(), new ArrayList<>());
	}

	@Override
	public UserDTO getUser(String email) {
		UserEntity userEntity = userRepository.findByEmail(email);
		if (userEntity == null) throw new UsernameNotFoundException(email);
	    
		UserDTO returnValue = new UserDTO();
		BeanUtils.copyProperties(userEntity, returnValue);
		      
		return returnValue;
	}

	@Override
	public UserDTO updateUser(String userId, UserDTO userDTO) {
		
		UserEntity userEntity = userRepository.findByUserId(userId);
		
		 if(userEntity == null)
	         throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
	     
	      
		userEntity.setFirstName(userDTO.getFirstName());
		userEntity.setLastName(userDTO.getLastName());
		
	      
	      UserEntity updateUserDetails = userRepository.save(userEntity);
	      
	      UserDTO returnValue = new UserDTO();
	      BeanUtils.copyProperties(updateUserDetails, returnValue);
	      
	      return returnValue;
	}

	@Override
	public void deleteUser(String id) {
		
		UserEntity userEntity = userRepository.findByUserId(id);
		if(userEntity == null)
	         throw new UserServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
		
		userRepository.delete(userEntity);
	}
	
	
}
 