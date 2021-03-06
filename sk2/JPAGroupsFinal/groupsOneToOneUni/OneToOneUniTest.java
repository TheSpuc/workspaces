package groupsOneToOneUni;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OneToOneUniTest { // OneToOne (unidirectional)
	public static void main(String[] args) {
		Group g1 = new Group("Foo");
		Group g2 = new Group("Bar");

		Person p1 = new Person("Jack");
		Person p2 = new Person("Jane");

		p1.setGroup(g1);
		p2.setGroup(g2);

		EntityManagerFactory emf = 
			Persistence.createEntityManagerFactory("Groups");
		EntityManager em = emf.createEntityManager();

		em.getTransaction().begin();
		em.persist(p1);
		em.persist(p2);
		em.persist(g1);
		em.persist(g2);
		em.getTransaction().commit();
       
        em.close();
        emf.close();
	}
}