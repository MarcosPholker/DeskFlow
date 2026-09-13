package com.helpdesk.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ClienteDTO;
import com.helpdesk.api.exception.EmailCadastroException;
import com.helpdesk.api.model.Cliente;
import com.helpdesk.api.repositories.ClienteRepositories;
import com.helpdesk.api.security.TokenService;


@Service
public class ClienteService {
	@Autowired
	private final TokenService tokenService;
	private final ClienteRepositories clienteRepositories;
	private final BCryptPasswordEncoder passwordEncoder;

	
	public ClienteService(ClienteRepositories clienteRepositories, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
		super();
		this.clienteRepositories = clienteRepositories;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	public Cliente cadastradarCliente(ClienteDTO clienteDTO) {

	    System.out.println("NOME: " + clienteDTO.getName());
	    System.out.println("EMAIL RECEBIDO: " + clienteDTO.getEmail());

	    Optional<Cliente> cliente =
	            clienteRepositories.findByEmail(clienteDTO.getEmail());

	    if (cliente.isPresent()) {
	        throw new EmailCadastroException("E-mail já cadastrado");
	    }

	    String password = passwordEncoder.encode(clienteDTO.getPassword());

	    Cliente novoCliente = new Cliente(
	            null,
	            clienteDTO.getName(),
	            clienteDTO.getEmail(),
	            password
	    );

	    return clienteRepositories.save(novoCliente);
	}
	
	public String Login(String email, String password) {
		
		
		Optional<Cliente> cliente = clienteRepositories.findByEmail(email);
		
		if(cliente.isEmpty()) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		if(!passwordEncoder.matches(password, cliente.get().getPassword())) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		return tokenService.gerarToken(email);
		
	}

}
