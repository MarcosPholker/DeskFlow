package com.helpdesk.api.services;

import java.util.Optional;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ClienteDTO;
import com.helpdesk.api.exception.EmailCadastroException;
import com.helpdesk.api.model.Cliente;
import com.helpdesk.api.repositories.ClienteRepositories;


@Service
public class ClienteService {
	private final ClienteRepositories clienteRepositories;
	private final BCryptPasswordEncoder passwordEncoder;

	

	public ClienteService(ClienteRepositories clienteRepositories, BCryptPasswordEncoder passwordEncoder) {
		super();
		this.clienteRepositories = clienteRepositories;
		this.passwordEncoder = passwordEncoder;
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
	
	public Cliente Login(String email, String password) {
		
		
		Optional<Cliente> cliente = clienteRepositories.findByEmail(email);
		
		if(!cliente.get().getEmail().equals(email)) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		if(!passwordEncoder.matches(password, cliente.get().getPassword())) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		return cliente.get();
		
	}

}
