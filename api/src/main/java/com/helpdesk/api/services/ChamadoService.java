package com.helpdesk.api.services;

<<<<<<< HEAD
import java.util.List;

import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ChamadoDTO;
import com.helpdesk.api.enums.StatusChamado;
import com.helpdesk.api.exception.ChamadoNotFoundException;
import com.helpdesk.api.exception.ClienteNotFoundException;
import com.helpdesk.api.model.Chamado;
import com.helpdesk.api.model.Cliente;
import com.helpdesk.api.repositories.ChamadoRepository;
import com.helpdesk.api.repositories.ClienteRepositories;

@Service
public class ChamadoService {
	private ChamadoRepository chamadoRepository;
	private ClienteRepositories clienteRepositories;

	public ChamadoService(ChamadoRepository chamadoRepository, ClienteRepositories clienteRepositories) {
		this.chamadoRepository = chamadoRepository;
		this.clienteRepositories = clienteRepositories;
	}

	public Chamado novoChamado(ChamadoDTO chamadoDTO) {
		Cliente cliente = clienteRepositories.findById(chamadoDTO.getIdCliente())
		        .orElseThrow(() -> new ClienteNotFoundException("Cliente não encontrado, não foi possivel realizar o chamado"));
		
		Chamado chamado = new Chamado(null, chamadoDTO.getTitulo(), chamadoDTO.getDescricao(), StatusChamado.ABERTO, cliente);
		return chamadoRepository.save(chamado);
	}
	
	public List<Chamado> listarChamado() {
		return chamadoRepository.findAll();
=======
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.support.Repositories;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ChamadoDTO;
import com.helpdesk.api.enums.StatusChamado;
import com.helpdesk.api.exception.ChamadoNotFoundException;
import com.helpdesk.api.exception.ClienteNotFoundException;
import com.helpdesk.api.exception.EmailCadastroException;
import com.helpdesk.api.model.Chamado;
import com.helpdesk.api.model.Cliente;
import com.helpdesk.api.repositories.ChamadoRepository;
import com.helpdesk.api.repositories.ClienteRepositories;

@Service
public class ChamadoService {
	private ChamadoRepository chamadoRepository;
	private ClienteRepositories clienteRepositories;

	public ChamadoService(ChamadoRepository chamadoRepository, ClienteRepositories clienteRepositories) {
		this.chamadoRepository = chamadoRepository;
		this.clienteRepositories = clienteRepositories;
	}

	public Chamado novoChamado(ChamadoDTO chamadoDTO) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Cliente cliente = clienteRepositories.findByEmail(email).orElseThrow(()-> new EmailCadastroException("email nao encontrado"));
		
		Chamado chamado = new Chamado(null, chamadoDTO.getTitulo(), chamadoDTO.getDescricao(), StatusChamado.ABERTO, cliente);
		return chamadoRepository.save(chamado);
	}
	
	public List<Chamado> listarChamado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Cliente cliente = clienteRepositories.findByEmail(email).orElseThrow(()-> new EmailCadastroException("email nao encontrado"));
		return chamadoRepository.findAllByClienteId(cliente.getId());
>>>>>>> branch 'master' of https://github.com:443/MarcosPholker/DeskFlow.git
	}
	
	
	public void deletarChamado(Long id) {
		Chamado chamado = chamadoRepository.findById(id).orElseThrow(()-> new ChamadoNotFoundException("chamado não encontrado"));
		chamadoRepository.delete(chamado);
	}
	
	public void alterarStatus(Long id, String status) {

	    Chamado chamado = chamadoRepository.findById(id)
	            .orElseThrow(() -> new ChamadoNotFoundException(
	                    "Status não alterado, chamado não encontrado"));

	    StatusChamado novoStatus = StatusChamado.valueOf(status);

	    chamado.setStatus(novoStatus);
	    chamadoRepository.save(chamado);
	}
}
