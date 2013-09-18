package groupsAssociationClass;

import java.util.GregorianCalendar;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class AssociationClassTest {  // Association class
	public static void main(String[] args) {
		Group g1 = new Group("Foo");
		Group g2 = new Group("Bar");
		Group g3 = new Group("Baz");

		Person p1 = new Person("Jack");
		Person p2 = new Person("Jane");
		Person p3 = new Person("John");

		Join j1 = new Join(p1, g1, new GregorianCalendar(2011, 0, 14, 8, 30).getTime());
		Join j2 = new Join(p2, g1, new GregorianCalendar(2011, 1, 15, 9, 31).getTime());
		Join j3 = new Join(p2, g2, new GregorianCalendar(2011, 2, 16, 10, 32).getTime());
		Join j4 = new Join(p3, g3, new GregorianCalendar(2011, 3, 17, 11, 33).getTime());
		
		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Groups");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(p1);
		em.persist(p2);
		em.persist(p3);
		em.persist(g1);
		em.persist(g2);
		em.persist(g3);
		em.persist(j1);
		em.persist(j2);
		em.persist(j3);
		em.persist(j4);
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}