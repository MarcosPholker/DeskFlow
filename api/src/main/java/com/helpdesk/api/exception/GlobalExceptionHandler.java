package com.helpdesk.api.exception;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> tratarErrosValidacao(
	        MethodArgumentNotValidException ex) {

	    Map<String, String> erros = new HashMap<>();

	    ex.getBindingResult().getFieldErrors().forEach(erro -> {
	        erros.put(erro.getField(), erro.getDefaultMessage());
	    });

	    return ResponseEntity.badRequest().body(erros);
	}

	@ExceptionHandler(EmailCadastroException.class)
	public ResponseEntity<Map<String, String>> emailJaCadastrado(EmailCadastroException ex) {

		Map<String, String> erro = new HashMap<>();

		erro.put("email", ex.getMessage());

		return ResponseEntity.badRequest().body(erro);
	}

	@ExceptionHandler(ChamadoNotFoundException.class)
	public ResponseEntity<Map<String, String>> chamadoNaoEncontrado(ChamadoNotFoundException ex) {

	    Map<String, String> erro = new HashMap<>();
	    erro.put("erro", ex.getMessage());

	    return ResponseEntity.status(404).body(erro);
	}
	
	@ExceptionHandler(UsuarioNotFoundException.class)
	public ResponseEntity<Map<String, String>> usuarioNotFoundException(UsuarioNotFoundException ex) {

	    Map<String, String> erro = new HashMap<>();
	    erro.put("erro", ex.getMessage());

	    return ResponseEntity.status(404).body(erro);
	}

	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> illegalArgumentException(IllegalArgumentException ex) {
	    Map<String, String> erro = new HashMap<>();
	    erro.put("erro", ex.getMessage());
	    return ResponseEntity.badRequest().body(erro);
	}

}