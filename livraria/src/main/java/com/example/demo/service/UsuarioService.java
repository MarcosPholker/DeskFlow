package com.example.demo.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.config.SecurityConfig;
import com.example.demo.dto.UsuarioDTO;
import com.example.demo.exception.EmailAlreadyExistsException;
import com.example.demo.exception.UsuarioNotFoundException;
import com.example.demo.model.Usuario;
import com.example.demo.repository.UsuarioRepository;

@Service
public class UsuarioService {

	private UsuarioRepository usuarioRepository;
	private PasswordEncoder passwordEncoder;

	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}

	public Usuario alterarCadastro(UsuarioDTO usuarioDTO) {
		Usuario usuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());

		if (usuario != null) {
			usuario.setNome(usuarioDTO.getNome());
			usuario.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
			
			return usuarioRepository.save(usuario);
		}

		return null;
	}

	public Usuario cadastrarUsuario(UsuarioDTO usuarioDTO) {
		Usuario novoUsuario = usuarioRepository.findByEmail(usuarioDTO.getEmail());
		if (novoUsuario != null) {
			throw new EmailAlreadyExistsException();
		}
		String senhaCriptografada = passwordEncoder.encode(usuarioDTO.getSenha());
		novoUsuario = new Usuario(null, usuarioDTO.getNome(), usuarioDTO.getEmail(), senhaCriptografada);
		return usuarioRepository.save(novoUsuario);

	}

	public Usuario buscarUsuarioId(Long id) {
		return usuarioRepository.findById(id).orElseThrow(() -> new UsuarioNotFoundException(id));
	}

	public List<Usuario> listarUsuarios() {
		return usuarioRepository.findAll();
	}

}
