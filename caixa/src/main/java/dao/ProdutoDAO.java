package dao;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Produto;

public class ProdutoDAO {

	public void addProduto(Produto produto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
	
		session.persist(produto);

		transaction.commit();

		session.close();
	}

	public List<Produto> listarProdutos() {

		Session session = HibernateUtil.getSessionFactory().openSession();

		List<Produto> produto = session.createQuery("from Produto", Produto.class).getResultList();

		session.close();

		return produto;

	}

	public Produto buscarPorId(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();

		Produto produto = session.find(Produto.class, id);

		session.close();

		return produto;

	}
	
	public void attProduto(Produto produto) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		session.merge(produto);
		
		transaction.commit();
		
		session.close();
		
	}
	
	public void deletarProduto(int id) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		Transaction transaction = session.beginTransaction();
		
		Produto produto = buscarPorId(id);
		
		session.remove(produto);
		
		transaction.commit();
		
		session.close();
	}

}
