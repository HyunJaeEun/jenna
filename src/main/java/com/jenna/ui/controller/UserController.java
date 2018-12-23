package com.jenna.ui.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jenna.exceptions.UserServiceException;
import com.jenna.service.UserService;
import com.jenna.shared.dto.UserDTO;
import com.jenna.ui.model.request.UserDetailsRequestModel;
import com.jenna.ui.model.response.ErrorMessages;
import com.jenna.ui.model.response.OperationStatusModel;
import com.jenna.ui.model.response.RequestOperationStatus;
import com.jenna.ui.model.response.UserRest;

@RestController
@RequestMapping(path = "users") // http://localhost:8080/users
public class UserController {

	@Autowired
	UserService userService;

	@GetMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } // default가
																													// xml로
																													// ...
																													// 정의
																													// 한
																													// 순서대로
																													// 적용됨..
	)
	public UserRest getUser(@PathVariable String id) {

		UserRest returnValue = new UserRest();

		UserDTO storedUser = userService.getUserByUserId(id);
		BeanUtils.copyProperties(storedUser, returnValue);

		return returnValue;
	}

	@PostMapping(consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, // 받아들이는 타입 HTTP
																									// REQUEST
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } // 응답하는 타입 HTTP RESPONSE
	)
	public UserRest createUser(@RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();

		if (userDetails.getFirstName().isEmpty())
			throw new UserServiceException(ErrorMessages.MISSING_REQUIRED_FIELD.getErrorMessage());

		UserDTO userDto = new UserDTO();
		BeanUtils.copyProperties(userDetails, userDto); // userDetails에서 받아온 정보를 userDTO로 넘겨줍니다.

		UserDTO createdUser = userService.createUser(userDto);
		BeanUtils.copyProperties(createdUser, returnValue);

		return returnValue;
	}

	@PutMapping(path = "/{id}", consumes = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE }, // 받아들이는
																													// 타입
																													// HTTP
																													// REQUEST
			produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } // 응답하는 타입 HTTP RESPONSE
	)
	public UserRest updateUser(@PathVariable String id, @RequestBody UserDetailsRequestModel userDetails) {

		UserRest returnValue = new UserRest();
		UserDTO userDTO = new UserDTO();

		BeanUtils.copyProperties(userDetails, userDTO);

		UserDTO updatedUser = userService.updateUser(id, userDTO);
		BeanUtils.copyProperties(updatedUser, returnValue);

		return returnValue;
	}

	@DeleteMapping(path = "/{id}", produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE } // 응답하는
																													// 타입
																													// HTTP
																													// RESPONSE
	)
	public OperationStatusModel deleteUser(@PathVariable String id) {
		OperationStatusModel returnValue = new OperationStatusModel();
		
		returnValue.setOperationName(RequestOperationName.DELETE.name());

		userService.deleteUser(id);

		returnValue.setOperationResult(RequestOperationStatus.SUCCESS.name());
		return returnValue;
	}
}
