package model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pedido {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	
	@ManyToOne
	private Cliente cliente;
	
	@OneToMany(mappedBy = "pedido")
	private List<ItemPedido> itemPedido;
	
	
	public Pedido() {
	}
	
	


	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}




	public List<ItemPedido> getItemPedido() {
		return itemPedido;
	}




	public void setItemPedido(List<ItemPedido> itemPedido) {
		this.itemPedido = itemPedido;
	}




	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}




	@Override
	public String toString() {
		return "cliente: " + cliente + "\n"+"item do pedido: " + itemPedido;
	}

	


	
}
