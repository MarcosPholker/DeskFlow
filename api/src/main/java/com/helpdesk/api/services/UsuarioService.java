package com.helpdesk.api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.UsuarioDTO;
import com.helpdesk.api.exception.EmailCadastroException;
import com.helpdesk.api.model.Usuario;
import com.helpdesk.api.repositories.UsuarioRepositories;
import com.helpdesk.api.security.TokenService;


@Service
public class UsuarioService {
	@Autowired
	private final TokenService tokenService;
	private final UsuarioRepositories usuarioRepositories;
	private final BCryptPasswordEncoder passwordEncoder;

	
	public UsuarioService(UsuarioRepositories usuarioRepositories, BCryptPasswordEncoder passwordEncoder, TokenService tokenService) {
		super();
		this.usuarioRepositories = usuarioRepositories;
		this.passwordEncoder = passwordEncoder;
		this.tokenService = tokenService;
	}

	public Usuario cadastradarUsuario(UsuarioDTO usuarioDTO) {

	    System.out.println("NOME: " + usuarioDTO.getName());
	    System.out.println("EMAIL RECEBIDO: " + usuarioDTO.getEmail());

	    Optional<Usuario> usuario =
	            usuarioRepositories.findByEmail(usuarioDTO.getEmail());

	    if (usuario.isPresent()) {
	        throw new EmailCadastroException("E-mail já cadastrado");
	    }

	    String password = passwordEncoder.encode(usuarioDTO.getPassword());

	    Usuario novoUsuario = new Usuario(
	            null,
	            usuarioDTO.getName(),
	            usuarioDTO.getEmail(),
	            password
	    );

	    return usuarioRepositories.save(novoUsuario);
	}
	
	public String Login(String email, String password) {
		
		
		Optional<Usuario> usuario = usuarioRepositories.findByEmail(email);
		
		if(usuario.isEmpty()) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		if(!passwordEncoder.matches(password, usuario.get().getPassword())) {
			throw new EmailCadastroException("email ou senha invalidos");
		}
		
		return tokenService.gerarToken(email);
		
	}

}
