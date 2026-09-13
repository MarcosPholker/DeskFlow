package com.helpdesk.api.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.api.dto.ClienteDTO;
import com.helpdesk.api.dto.LoginDTO;
import com.helpdesk.api.model.Cliente;
import com.helpdesk.api.services.ClienteService;

@RestController
public class ClienteController {

	private ClienteService clienteService;
	
	
	public ClienteController(ClienteService clienteService) {
		this.clienteService = clienteService;
	}

	@PostMapping("/login")
	public String login(@RequestBody LoginDTO loginDTO) {
<<<<<<< HEAD
		clienteService.Login(loginDTO.getEmail(), loginDTO.getPassword());
		return "Usuario logado com sucesso!";
=======
		return clienteService.Login(loginDTO.getEmail(), loginDTO.getPassword());
>>>>>>> branch 'master' of https://github.com:443/MarcosPholker/DeskFlow.git
	}
	
	@PostMapping("/cadastro")
	public Cliente novoCliente(@RequestBody ClienteDTO clienteDTO) {
		return clienteService.cadastradarCliente(clienteDTO);
	}
}
