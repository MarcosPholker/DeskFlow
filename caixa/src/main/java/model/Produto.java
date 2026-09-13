package model;


import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Produto {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	private Double price;
	private int stock = 50;
	
	@OneToMany(mappedBy = "produto")
	private List<ItemPedido> itemPedido;
	
	public Produto() {
	}
	
	

	public List<ItemPedido> getItemPedido() {
		return itemPedido;
	}



	public void setItemPedido(List<ItemPedido> itemPedido) {
		this.itemPedido = itemPedido;
	}



	public Produto(String name, Double price, int stock) {
		this.name = name;
		this.price = price;
		this.stock = this.stock - stock;
	}
	
	public int getId() {
		return id;
	}

	
	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	


	public Double valorTotal(Double price) {
		return price += this.price;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	public Double getPrice() {
		return price;
	}
	public void setPrice(Double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	} 

	@Override
	public String toString() {
		return "Nome do Produto id:" + id + " " + name + ", price: " + price + ", stock: " + stock + ", valorTotal: " + valorTotal(price);
	}
	
	
	
}
