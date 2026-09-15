package com.helpdesk.api.services;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.helpdesk.api.dto.ChamadoDTO;
import com.helpdesk.api.enums.StatusChamado;
import com.helpdesk.api.exception.ChamadoNotFoundException;
import com.helpdesk.api.exception.EmailCadastroException;
import com.helpdesk.api.model.Chamado;
import com.helpdesk.api.model.Usuario;
import com.helpdesk.api.repositories.ChamadoRepository;
import com.helpdesk.api.repositories.UsuarioRepositories;

@Service
public class ChamadoService {
	private ChamadoRepository chamadoRepository;
	private UsuarioRepositories usuarioRepositories;

	public ChamadoService(ChamadoRepository chamadoRepository, UsuarioRepositories usuarioRepositories) {
		this.chamadoRepository = chamadoRepository;
		this.usuarioRepositories = usuarioRepositories;
	}

	public Chamado chamadoPorId(Long id) {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepositories.findByEmail(email)
				.orElseThrow(() -> new EmailCadastroException("email nao encontrado"));

		Chamado chamado = chamadoRepository.findById(id)
				.orElseThrow(() -> new ChamadoNotFoundException("id do chamado nao encontrado"));

		if (!usuario.getId().equals(chamado.getUsuario().getId())) {
			throw new ChamadoNotFoundException("chamado nao encontrado");
		}
		return chamadoRepository.findById(id).orElseThrow(() -> new ChamadoNotFoundException("chamado nao encontrado"));
	}

	public Chamado novoChamado(ChamadoDTO chamadoDTO) {

    Authentication authentication =
            SecurityContextHolder.getContext().getAuthentication();

    String email = authentication.getName();

	    Usuario usuario = usuarioRepositories.findByEmail(email)
            .orElseThrow(() ->
                    new EmailCadastroException("email nao encontrado"));

    long quantidadeAbertos =
	            chamadoRepository.countByUsuarioIdAndStatus(
	                    usuario.getId(),
                    StatusChamado.ABERTO
            );

    if (quantidadeAbertos >= 1) {
        throw new IllegalArgumentException(
				"O usuario atingiu o limite máximo de 1 chamado aberto."
        );
    }

    Chamado chamado = new Chamado(
            null,
            chamadoDTO.getTitulo(),
            chamadoDTO.getDescricao(),
            StatusChamado.ABERTO,
	            usuario
    );

    return chamadoRepository.save(chamado);
}

	public List<Chamado> listarChamado() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String email = authentication.getName();
		Usuario usuario = usuarioRepositories.findByEmail(email)
				.orElseThrow(() -> new EmailCadastroException("email nao encontrado"));
		return chamadoRepository.findAllByUsuarioIdOrderByIdDesc(usuario.getId());
	}

	public void alterarStatus(Long id, String status) {

		Chamado chamado = chamadoRepository.findById(id)
				.orElseThrow(() -> new ChamadoNotFoundException(
						"Status não alterado, chamado não encontrado"));

		StatusChamado novoStatus = StatusChamado.valueOf(status);

		if (chamado.getStatus() == StatusChamado.FECHADO && novoStatus == StatusChamado.ABERTO) {
			throw new IllegalArgumentException("Não é possível reabrir um chamado fechado.");
		}

		if (chamado.getStatus() == StatusChamado.ABERTO && novoStatus == StatusChamado.FECHADO) {
			chamado.setStatus(novoStatus);
			chamadoRepository.save(chamado);
			return;
		}

		if (chamado.getStatus() == StatusChamado.FECHADO && novoStatus == StatusChamado.FECHADO) {
			return;
		}

		throw new IllegalArgumentException("A transição de status permitida é apenas ABERTO -> FECHADO.");
	}
}
