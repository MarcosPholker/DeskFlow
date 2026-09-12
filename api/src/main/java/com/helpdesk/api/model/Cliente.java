package com.helpdesk.api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Cliente {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank @NotEmpty
	private String name;
	@NotBlank @NotEmpty
	private String email;
	@NotBlank @NotEmpty
	private String password;
	
	@OneToMany(mappedBy = "cliente")
	private List<Chamado> chamado;
	
	
	public Cliente() {
	}
	
	
	public Cliente(Long id, @NotBlank @NotEmpty String name, @NotBlank @NotEmpty String email,
			@NotBlank @NotEmpty String password) {
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}


	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
	

}
