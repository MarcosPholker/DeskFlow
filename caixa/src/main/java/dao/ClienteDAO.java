package dao;


import java.util.List;

import org.hibernate.Session;

import org.hibernate.Transaction;
import model.Cliente;


public class ClienteDAO {
	
	public void inserirCliente(Cliente cliente) {
		Session session = HibernateUtil
                .getSessionFactory()
                .openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.persist(cliente);

        transaction.commit();

        session.close();
	}
	
	public void deletarCliente(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Cliente cliente = session.find(Cliente.class, id);
		
		if(cliente != null) {
			session.remove(cliente);
		}
		
		transaction.commit();
		session.close();
	}
	
	public List<Cliente> listarClientes() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<Cliente> clientes = session.createQuery("from Cliente", Cliente.class).getResultList();
		
		session.close();
		
		return clientes;
	}
	
	public Cliente buscarPorId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Cliente cliente = session.find(Cliente.class, id);
		
		session.close();
		
		return cliente;
	}
	
	public void attCliente (Cliente cliente) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		session.merge(cliente);
		
		transaction.commit();
		session.close();
		
	}
	
	
	
}
