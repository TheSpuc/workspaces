package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import model.Design;
import model.Mobile;


public class Main {
	
	private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("MobileTest");
	private static EntityManager em = emf.createEntityManager();
	
	public static void main(String[] args){
		Mobile m1 = new Mobile(1234, "Dell", "fun", null, Design.BIG);
		Mobile m2 = new Mobile(5678, "Sony", "Serious", null, Design.BIG);
		Mobile m3 = new Mobile(9101, "Apple", "hardcore", "101", Design.BIG);
		
//		em.getTransaction().begin();
//		em.persist(m1);
//		em.persist(m2);
//		em.persist(m3);
//		em.getTransaction().commit();
		
//        System.out.println(em.createQuery("SELECT m FROM Mobile m",Mobile.class).getResultList());
        
        getPhones("abekat OR 1=1");
        
        em.close();
        emf.close();
	}
	
	private static void getPhones(String query){
		List<Mobile> phones = em.createQuery("SELECT m FROM Mobile m WHERE m.model = ?1", Mobile.class).setParameter(1, query).getResultList();
		for(Mobile m : phones){
			System.out.println(m);
		}
	}
}
