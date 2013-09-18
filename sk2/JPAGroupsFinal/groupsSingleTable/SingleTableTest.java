package groupsSingleTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class SingleTableTest { // SingleTable
	public static void main(String[] args) {
		Group g1 = new Group("Foo");
		Group g2 = new Team("Bar", "Test");

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Groups");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(g1);
		em.persist(g2);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}