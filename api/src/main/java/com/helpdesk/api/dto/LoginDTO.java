package com.helpdesk.api.dto;

public class LoginDTO {
	private String email;
	private String password;
	
	public LoginDTO() {
	}
	
<<<<<<< HEAD
	public LoginDTO(String name, String email, String password) {
=======
	public LoginDTO(String email, String password) {
>>>>>>> branch 'master' of https://github.com:443/MarcosPholker/DeskFlow.git
		this.email = email;
		this.password = password;
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
