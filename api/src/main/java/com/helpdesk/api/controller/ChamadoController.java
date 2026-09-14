package com.helpdesk.api.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.helpdesk.api.dto.ChamadoDTO;
import com.helpdesk.api.model.Chamado;
import com.helpdesk.api.services.ChamadoService;

import jakarta.validation.Valid;

@RestController
public class ChamadoController {
	
	private ChamadoService chamadoService;
	
	
	public ChamadoController(ChamadoService chamadoService) {
		this.chamadoService = chamadoService;
	}
	
	@GetMapping("/chamado/{id}")
	public ResponseEntity<Chamado> chamadoPorId(@PathVariable Long id){
		return ResponseEntity.ok(chamadoService.chamadoPorId(id));
	}

	@GetMapping("/chamado/listar")
	public ResponseEntity<List<Chamado>> listarChamados() {
		List<Chamado> chamados = chamadoService.listarChamado();
		return ResponseEntity.ok().body(chamados);
	}

	@PostMapping("/chamado")
	public ResponseEntity<Chamado> novoChamado(@Valid @RequestBody ChamadoDTO chamadoDTO) {
		chamadoService.novoChamado(chamadoDTO);
		return ResponseEntity.status(201).build();
	}
	
	@DeleteMapping("/chamado/delete/{id}")
	public ResponseEntity<String> deletarChamado(@PathVariable Long id) {
	    chamadoService.deletarChamado(id);
	    return ResponseEntity.ok("chamado deletado com sucesso!");
	}
	
	@PutMapping("/chamado/alterar/{id}")
	public ResponseEntity<String> alterarStatus(@PathVariable Long id,@RequestParam String status) {
		chamadoService.alterarStatus(id, status);
		return ResponseEntity.ok("status alterado com sucesso para: " + status);
	}
}
