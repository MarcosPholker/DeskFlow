package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class ProdutoDTO {

	@NotBlank(message = "o nome nao pode ficar vazio")
    private String nome;
	@NotNull(message = "o valor não pode ser negativo")
	@Positive(message = "o valor precisa ser maior que zero")
    private Double preco;

    public ProdutoDTO() {	
    }

    public ProdutoDTO(String nome, Double preco) {
        this.nome = nome;
        this.preco = preco;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getPreco() {
        return preco;
    }

    public void setPreco(Double preco) {
        this.preco = preco;
    }
}