package com.helpdesk.api.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.model.Cliente;

public interface ClienteRepositories extends JpaRepository<Cliente, Long> {
	Optional<Cliente> findByEmail(String email);
}
