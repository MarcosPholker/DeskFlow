package com.helpdesk.api.services;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ChamadoDTO;
import com.helpdesk.api.enums.StatusChamado;
import com.helpdesk.api.exception.ChamadoNotFoundException;
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
	
	public Chamado chamadoPorId(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Cliente cliente = clienteRepositories.findByEmail(email).orElseThrow(() -> new EmailCadastroException("email nao encontrado"));
		
		Chamado chamado = chamadoRepository.findById(id).orElseThrow(() -> new ChamadoNotFoundException("id do chamado nao encontrado"));
		
		if(!cliente.getId().equals(chamado.getCliente().getId())) {
			throw new ChamadoNotFoundException("chamado nao encontrado");
		}
		return chamadoRepository.findById(id).orElseThrow(() -> new ChamadoNotFoundException("chamado nao encontrado"));
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
