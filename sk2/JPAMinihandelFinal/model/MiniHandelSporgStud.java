package model;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

public class MiniHandelSporgStud {

	/**
	 * @param args
	 */

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) {

		emf = Persistence.createEntityManagerFactory("Minihandel");
		em = emf.createEntityManager();

//		System.out.println(findAlleKunder());
//		System.out.println(findVare(2));
		
//		System.out.println(findStyk());
//		System.out.println(findFlereVare());
//		System.out.println(sorteretListe());
		System.out.println(sortertIgenListe());
		
		em.close();
		emf.close();
	}

	public static Vare findVare(int nr) {
		Vare vare = null;
		String jplQuery = "SELECT v FROM Vare v WHERE v.nummer = :nr";
		Query query = em.createQuery(jplQuery, Vare.class);
		query.setParameter("nr", nr);
		List<Vare> list = query.getResultList();
		if (list.size() > 0)
			vare = list.get(0);
		return vare;
	}

	

	public static List<Kunde> findAlleKunder() {
		List<Kunde> kunder = em.createQuery("SELECT k FROM Kunde k",
				Kunde.class).getResultList();
		return kunder;
	}
	
	/**
	 * Vare over 100 kroner
	 */
	public static List<Vare> findStyk(){
		List<Vare> varer = em.createQuery("SELECT v FROM Vare v WHERE v.stkPris > 100",Vare.class).getResultList();
		return varer;
	}
	
	/**
	 * Mere end 10 styk på lager og over 50 kroner
	 */
	public static List<Vare> findFlereVare(){
		List<Vare> varer = em.createQuery("SELECT v FROM Vare v WHERE v.stkPris > 50 AND v.antalPaaLager > 10", Vare.class).getResultList();
		return varer;
	}
	
	/**
	 * Sorteret liste med navne på vare, der koster mere end hundre
	 */
	public static List<Vare> sorteretListe(){
		List<Vare> varer = em.createQuery("SELECT v FROM Vare v WHERE v.stkPris > 100 ORDER BY v.navn ASC", Vare.class).getResultList();
		return varer;
	}	
	
	/**
	 * list sorteret efter pris
	 */
	public static List<Vare> sortertIgenListe(){
		List<Vare> varer = em.createQuery("SELECT v FROM Vare v ORDER BY v.stkPris", Vare.class).getResultList();
		return varer;
	}
}
