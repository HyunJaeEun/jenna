package com.jenna.ui.model.request;

public class UserDetailsRequestModel { // http request를 통해 들어온 json객체를 java 객체로 변환해주는 역할 
	
	private String firstName;
	private String	lastName ;
	private String	email ;
	private String	password;
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
}
