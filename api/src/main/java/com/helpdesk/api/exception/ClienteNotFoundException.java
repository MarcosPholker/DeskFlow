package com.helpdesk.api.exception;

public class ClienteNotFoundException extends RuntimeException {
	public ClienteNotFoundException(String message) {
		super(message);
	}
}
