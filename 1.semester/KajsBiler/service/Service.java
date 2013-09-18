
package service;

import java.util.ArrayList;

import model.Kunde;
import model.Bil;


import dao.Dao;

public class Service {
	
	public static Kunde createKunde(String navn, int cpr){
		Kunde kunde = new Kunde(navn, cpr);
		Dao.addKunde(kunde);
		return kunde;
	}
	
	public static Bil createBil(int regnr, String maerke){
		Bil bil = new Bil(regnr, maerke);
		Dao.addBil(bil);
		return bil;
	}
	
	public static void updateKunde(Kunde kunde, int cpr, String navn){
		kunde.setCpr(cpr);
		kunde.setNavn(navn);	
	}
	
	public static ArrayList<Kunde> getAlleKunder(){
		return Dao.getAlleKunder();
	}

	
}
 