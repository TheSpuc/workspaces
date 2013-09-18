package minihandelelev;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Test {

	public static void main(String[] args) {
		Vare v1 = new Vare("soem", 0.25);
		Vare v2 = new Vare("skruer", 0.50);
		Vare v3 = new Vare("moetrik", 1.00);
		Vare v4 = new Vare("hammer", 53.75);
		Vare v5 = new Vare("sav", 256.50);
		
		Kunde k1 = new Kunde("Jane Jensen", "1964-05-23");
		Kunde k2 = new Kunde("Hans Kirk", "1954-12-12");
		
//		Jane Jensen afgiver 2 ordrer
//		���	8 s��m og 1 hammer
//		���	2 save og 100 skruer
		
		Ordre o1 = new Ordre(k1);
		o1.createOrdreLinie(8, v1);
		o1.createOrdreLinie(1, v4);
		
		Ordre o2 = new Ordre(k1);
		o2.createOrdreLinie(2, v5);
		o2.createOrdreLinie(100, v2);

//	Hans Kirk afgiver 4 ordrer
//		���	10 m��trikker, 1 hammer og 1 sav
//		���	50 skruer og 1000 s��m
//		���	10 skruer og 5 m��trikker
//		���	1 sav 
		
		Ordre o3 = new Ordre(k2);
		o3.createOrdreLinie(10, v3);
		o3.createOrdreLinie(1, v4);
		o3.createOrdreLinie(1, v5);
		
		Ordre o4 = new Ordre(k2);
		o4.createOrdreLinie(50, v2);
		o4.createOrdreLinie(1000, v1);
		
		Ordre o5 = new Ordre(k2);
		o5.createOrdreLinie(10, v2);
		o5.createOrdreLinie(5, v3);
		
		Ordre o6 = new Ordre(k2);
		o6.createOrdreLinie(1, v5);

		EntityManagerFactory emf = Persistence.createEntityManagerFactory("Minihandel");
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		em.persist(k1);
		em.persist(k2);
		em.persist(o1);
		em.persist(o2);
		em.persist(o3);
		em.persist(o4);
		em.persist(o5);
		em.persist(o6);
		em.persist(v1);
		em.persist(v2);
		em.persist(v3);
		em.persist(v4);
		em.persist(v5);
		em.getTransaction().commit();


		em.close();
		emf.close();
		
		
	}
}