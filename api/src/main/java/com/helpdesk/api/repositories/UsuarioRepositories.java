package com.helpdesk.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.model.Usuario;

public interface UsuarioRepositories extends JpaRepository<Usuario, Long> {
	Optional<Usuario> findByEmail(String email);
}
