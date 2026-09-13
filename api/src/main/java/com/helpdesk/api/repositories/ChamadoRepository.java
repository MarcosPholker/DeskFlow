package com.helpdesk.api.repositories;

<<<<<<< HEAD
import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
=======
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
	
	List<Chamado> findAllByClienteId(Long clienteId);
>>>>>>> branch 'master' of https://github.com:443/MarcosPholker/DeskFlow.git
}
