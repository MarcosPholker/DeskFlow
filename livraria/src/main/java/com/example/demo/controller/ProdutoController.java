package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Produto;
import com.example.demo.dto.ProdutoDTO;
import com.example.demo.service.ProdutoService;

import jakarta.validation.Valid;

@RestController
public class ProdutoController {
	
	private ProdutoService produtoService;

	public ProdutoController(ProdutoService produtoService) {
		this.produtoService = produtoService;
	}
	
	@PostMapping("/produtos")
	public ResponseEntity<Produto> addProduto(@Valid @RequestBody ProdutoDTO produtoDTO) {
		Produto produto = produtoService.addProduto(produtoDTO);
		return ResponseEntity.status(201).body(produto);
	}
	
	@GetMapping("/produtos")
	public List<Produto> listarProdutos() {
		return produtoService.listarProduto();
	}
	
	@PutMapping("/produtos/{id}")
	public ResponseEntity<Produto> atualizarProduto(@PathVariable Long id,@Valid @RequestBody ProdutoDTO produtoDTO) {
		Produto attProduto = produtoService.atualizarProduto(id, produtoDTO);
		if(attProduto == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(attProduto);
	}
	
	@DeleteMapping("/produtos/{id}")
	public ResponseEntity<Void> deletarProduto(@PathVariable Long id) {
		 Long deletado = produtoService.deletarProduto(id);
		 if(deletado == null) {
			 return ResponseEntity.notFound().build();
		 }
		 return ResponseEntity.noContent().build();
	}
	
	@GetMapping("/produtos/{id}")
	public ResponseEntity<Produto> encontrarProduto(@PathVariable Long id) {
		Produto produtoid = produtoService.encontrarProduto(id);
		if(produtoid == null) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok(produtoid);
	}
	
}
