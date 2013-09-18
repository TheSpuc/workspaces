package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;



public class MiniHandelOpretDB {

	/**
	 * @param args
	 */

	public static void main(String[] args) {

		EntityManagerFactory emf = Persistence
				.createEntityManagerFactory("Minihandel");
		EntityManager em = emf.createEntityManager();
		
		Vare soem = new Vare(1,"soem",0.25);
		Vare skrue = new Vare(2,"skruer",0.5);
		Vare moetrik = new Vare(3,"moetrik",1.0);
		Vare hammer = new Vare(4,"hammer",53.75);
		Vare sav = new Vare(5,"sav",256.5);
		
		em.getTransaction().begin();
		em.persist(soem);
		em.persist(skrue);
		em.persist(moetrik);
		em.persist(hammer);
		em.persist(sav);
		em.getTransaction().commit();

		em.getTransaction().begin();
		Kunde jane = new Kunde("JaneJensen", new GregorianCalendar(1964,23,5).getTime());
		Kunde hans = new Kunde("Hans Kirk", new GregorianCalendar(1953,12,12).getTime());
		em.persist(jane);
		em.persist(hans);
		em.getTransaction().commit();


		Ordre jo1 = new Ordre(1,jane);
		jane.addOrdre(jo1);
		jo1.createOrdreLinie(1,8,soem);
		jo1.createOrdreLinie(2,1,hammer);
		em.getTransaction().begin();
		em.persist(jo1);
		em.getTransaction().commit();
	
		Ordre jo2 = new Ordre(2,jane);
		jane.addOrdre(jo2);
		jo2.createOrdreLinie(1,2,sav);
		jo2.createOrdreLinie(2,100,skrue);
		em.getTransaction().begin();
		em.persist(jo2);
		em.getTransaction().commit();

		Ordre ho1 = new Ordre(3,hans);
		hans.addOrdre(ho1);
		ho1.createOrdreLinie(1,10,moetrik);
		ho1.createOrdreLinie(2,1,hammer);
		ho1.createOrdreLinie(3,1,sav);
		em.getTransaction().begin();
		em.persist(ho1);
		em.getTransaction().commit();

	
		Ordre ho2 = new Ordre(4,hans);
		hans.addOrdre(ho2);
		ho2.createOrdreLinie(1,10,skrue);
		ho2.createOrdreLinie(2,1000,soem);
		em.getTransaction().begin();
		em.persist(ho2);
		em.getTransaction().commit();

	
		Ordre ho3 = new Ordre(5,hans);
		hans.addOrdre(ho3);
		ho3.createOrdreLinie(1,10,skrue);
		ho3.createOrdreLinie(2,5,moetrik);
		em.getTransaction().begin();
		em.persist(ho3);
		em.getTransaction().commit();

	
		Ordre ho4 = new Ordre(6,hans);		
		hans.addOrdre(ho4);
		ho4.createOrdreLinie(1,1,sav);
		em.getTransaction().begin();
		em.persist(ho4);
		em.getTransaction().commit();
		System.out.println("Database med indhold oprettet");
		em.close();
		emf.close();
		
	
	}

}
