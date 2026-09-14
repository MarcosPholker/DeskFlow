package com.helpdesk.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.helpdesk.api.enums.StatusChamado;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;

@Entity
public class Chamado {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotEmpty
	private String titulo;
	@NotEmpty
	private String descricao;
	@Enumerated(EnumType.STRING)
	private StatusChamado status;
	

	@ManyToOne
	private Cliente cliente;

	
	
	
	public Chamado() {
	}

	public Chamado(Long id, @NotEmpty String titulo, @NotEmpty String descricao, StatusChamado status,
			Cliente cliente) {
		this.id = id;
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.cliente = cliente;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	//@JsonIgnore
	public Cliente getCliente() {
		return cliente;
	}
	
	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

}
