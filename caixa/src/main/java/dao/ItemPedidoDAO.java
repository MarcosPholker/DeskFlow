package dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import model.ItemPedido;

public class ItemPedidoDAO {
	public void addItemPedido(List<ItemPedido> itemPedido) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction tz = session.beginTransaction();
		
		for(ItemPedido p : itemPedido) {
			session.persist(p);
		}
		
		tz.commit();
		session.close();
		
	}
}
