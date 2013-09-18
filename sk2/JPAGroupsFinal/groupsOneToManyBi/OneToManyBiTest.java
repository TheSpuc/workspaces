package groupsOneToManyBi;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class OneToManyBiTest { // OneToMany (bidirectional)
	public static void main(String[] args) {
		Group g1 = new Group("Foo");
		Group g2 = new Group("Bar");
		Group g3 = new Group("Baz");

		Person p1 = new Person("Jack");
		Person p2 = new Person("Jane");
		Person p3 = new Person("John");

		g1.addPerson(p1);
		p2.setGroup(g1);
		g2.addPerson(p3);
		
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
		em.getTransaction().commit();

		em.close();
		emf.close();
	}
}