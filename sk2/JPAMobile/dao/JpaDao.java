package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import model.Mobile;

public class JpaDao{

	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobileTest");
	private static EntityManager em = emf.createEntityManager();
	private static EntityTransaction tx = em.getTransaction();

	private static JpaDao dao;

	public static JpaDao getDAO() {
		if (dao == null)
			dao = new JpaDao();
		return dao;
	}

	private JpaDao () {
	}

	public static List<Mobile> getAllMobile() {
		return em.createQuery("SELECT m FROM Mobile m",Mobile.class).getResultList();
	}

	public static  void store(Mobile mobile) {
		tx.begin();
		em.persist(mobile);
		tx.commit();
	}

	public static void remove(Mobile mobile) {
		tx.begin();
		em.remove(mobile);
		tx.commit();
	}

	public static void close() {
		em.clear();
	}

}
