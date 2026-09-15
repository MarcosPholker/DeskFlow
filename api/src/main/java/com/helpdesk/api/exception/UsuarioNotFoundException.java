package com.helpdesk.api.exception;

public class UsuarioNotFoundException extends RuntimeException {
	public UsuarioNotFoundException(String message) {
		super(message);
	}
}
