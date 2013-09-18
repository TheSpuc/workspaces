package dao;

import java.util.ArrayList;

import model.Bil;
import model.Kunde;


public class Dao {
	private static ArrayList<Kunde> kunder = new ArrayList<Kunde>();
	private static ArrayList<Bil> biler = new ArrayList<Bil>();

	/**
	 * Gemmer kunden Requires: kunden er ikke gemt
	 */
	public static void addKunde(Kunde kunde) {
		if (!kunder.contains(kunde))
			kunder.add(kunde);
	}

	/**
	 * Fjerner den gemte kunde Requires: kunden er gemt
	 */
	public static void removeKunde(Kunde kunde) {
		if (kunder.contains(kunde))
			kunder.remove(kunde);
	}

	/**
	 * Returner en liste af kunder
	 */
	public static ArrayList<Kunde> getAlleKunder() {
		return new ArrayList<Kunde>(kunder);
	}

	/**
	 * Gemmer bilen Requires: bilen er ikke gemt
	 */
	public static void addBil(Bil bil) {
		if (!biler.contains(bil))
			biler.add(bil);
	}

	/**
	 * Fjerner den gemte bil Requires: bilen er gemt
	 */
	public static void removeBil(Bil bil) {
		if (biler.contains(bil))
			biler.remove(bil);
	}

	/**
	 * Returner en liste af biler
	 */
	public static ArrayList<Bil> getAlleBiler() {
		return new ArrayList<Bil>(biler);
	}
	
	public static void createAndStoreSomeObjects() {
		Bil bil = new Bil(12345,"Ford");
		Dao.addBil(bil);
		bil = new Bil(11123, "Opel");
		Dao.addBil(bil);
		bil = new Bil(22245, "Fiat");
		Dao.addBil(bil);
		
		Kunde kunde = new Kunde("Hans",123);
		Dao.addKunde(kunde);
		kunde = new Kunde("Helle",234);
		Dao.addKunde(kunde);
		kunde = new Kunde("Knud",4556);
		Dao.addKunde(kunde);
		
	}
	
}
