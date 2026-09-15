package com.helpdesk.api.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.helpdesk.api.enums.StatusChamado;
import com.helpdesk.api.model.Chamado;

public interface ChamadoRepository extends JpaRepository<Chamado, Long> {
	
	List<Chamado> findAllByUsuarioIdOrderByIdDesc(Long usuarioId);
	List<Chamado> findAllByUsuarioId(Long usuarioId);
	Long countByUsuarioIdAndStatus(Long usuarioId, StatusChamado status);
}
