package com.helpdesk.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.api.dto.UsuarioDTO;
import com.helpdesk.api.dto.LoginDTO;
import com.helpdesk.api.model.Usuario;
import com.helpdesk.api.services.UsuarioService;

@RestController
public class UsuarioController {

	private UsuarioService usuarioService;
	
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginDTO loginDTO) {
		return usuarioService.Login(loginDTO.getEmail(), loginDTO.getPassword());
	}
	
	@PostMapping("/cadastro")
	public Usuario novoUsuario(@RequestBody UsuarioDTO usuarioDTO) {
		return usuarioService.cadastradarUsuario(usuarioDTO);
	}
}
