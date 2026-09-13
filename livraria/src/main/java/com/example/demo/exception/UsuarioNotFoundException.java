package com.example.demo.exception;

public class UsuarioNotFoundException extends RuntimeException {
	public UsuarioNotFoundException(Long id) {
		super ("usuario nao encontrado " + id);
	}
}
