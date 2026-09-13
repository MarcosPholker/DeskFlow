package com.example.demo.service;

import com.example.demo.dto.ProdutoDTO;
import com.example.demo.exception.ProdutoNotFoundException;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.demo.model.Produto;
import com.example.demo.repository.ProdutoRepository;

@Service
public class ProdutoService {
	
	private ProdutoRepository produtoRepository;
	
	public ProdutoService(ProdutoRepository produtoRepository) {
		this.produtoRepository = produtoRepository;
	}

	public List<Produto> listarProduto() {
		return produtoRepository.findAll();
	}
	
	public Produto atualizarProduto(Long id, ProdutoDTO produtoDTO){
	 	Produto produto = produtoRepository.findById(id).orElseThrow(()-> new ProdutoNotFoundException(id));
	 	
	 	produto.setNome(produtoDTO.getNome());
	 	produto.setPreco(produtoDTO.getPreco());
	 	return produtoRepository.save(produto);
	}
	
	public Long deletarProduto(Long id) {
		
		produtoRepository.findById(id).orElseThrow(()-> new ProdutoNotFoundException(id));
		
		produtoRepository.deleteById(id);
		return id;
	}

	public Produto addProduto(ProdutoDTO produtoDTO) {
		Produto produto = new Produto(null, produtoDTO.getNome(), produtoDTO.getPreco());
		return produtoRepository.save(produto);
	}

	public Produto encontrarProduto(Long id) {
		return produtoRepository.findById(id).orElseThrow(() -> new ProdutoNotFoundException(id));
	}
}