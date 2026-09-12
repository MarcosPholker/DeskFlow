package com.helpdesk.api.dto;

public class TecnicoDTO {
	
	private String name;
	private String email;
	private String password;
	
	
	
	public TecnicoDTO() {
	}
	public TecnicoDTO(String name, String email, String password) {
		this.name = name;
		this.email = email;
		this.password = password;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return password;
	}
	public void setSenha(String senha) {
		this.password = senha;
	}
	
	
}
