package dao;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.Pedido;

public class PedidoDAO {

	public void inserirDados(Pedido pedido) {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		session.persist(pedido);
		transaction.commit();
		
		session.close();
	}
}
