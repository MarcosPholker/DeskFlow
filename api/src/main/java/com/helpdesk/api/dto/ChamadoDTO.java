package com.helpdesk.api.dto;

import com.helpdesk.api.enums.StatusChamado;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ChamadoDTO {

	@NotBlank
	private String titulo;
	@NotBlank
	private String descricao;
	@Enumerated(EnumType.STRING)
	private StatusChamado status;

	private Long idCliente;

	public ChamadoDTO(@NotBlank String titulo, @NotBlank String descricao, StatusChamado status,
			@NotNull Long idCliente) {
		super();
		this.titulo = titulo;
		this.descricao = descricao;
		this.status = status;
		this.idCliente = idCliente;
	}

	public StatusChamado getStatus() {
		return status;
	}

	public void setStatus(StatusChamado status) {
		this.status = status;
	}

	public ChamadoDTO() {
	}

	public Long getIdCliente() {
		return idCliente;
	}

	public void setIdCliente(Long idCliente) {
		this.idCliente = idCliente;
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
