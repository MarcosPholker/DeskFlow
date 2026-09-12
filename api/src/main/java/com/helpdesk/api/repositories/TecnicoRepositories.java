package com.helpdesk.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.model.Tecnico;

public interface TecnicoRepositories extends JpaRepository<Tecnico, Long> {
	
}
