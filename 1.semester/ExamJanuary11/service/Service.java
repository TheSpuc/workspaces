package service;

import java.util.ArrayList;

import dao.Dao;
import model.Abonnement;
import model.Kasse;
import model.Kunde;
import model.Stoerrelse;

public class Service {

	
	public static Kunde createKunde(int id, String navn){
		Kunde k = new Kunde(id, navn);
		Dao.addKunde(k);
		return k;
	}
	
	public static Kasse createKasse(String kategori, double pris){
		Kasse k = new Kasse(kategori, pris);
		Dao.addKasse(k);
		return k;
	}
	
	public static void updateKunde(Kunde kunde, int id, String navn){
		kunde.setId(id);
		kunde.setNavn(navn);
	}
	
	public static void updateKasse(Kasse kasse, String kategori, double pris){
		kasse.setKategori(kategori);
		kasse.setPris(pris);
	}
	
	public static ArrayList<Kunde> getKunder(){
		return Dao.getKunder();
	}
	
	public static ArrayList<Kasse> getKasser(){
		return Dao.getKasser();
	}
	
	public static ArrayList<Kunde> sortedList(){
		ArrayList<Kunde> runList = Dao.getKunder();
		
		int run = runList.size();
		for(int i=0; i<run-1; i++){
			int min = i;
			for(int j=i+1; j<run; j++){ 
				if(runList.get(j).compareTo(runList.get(min)) < 0){ 
					min = j;
				}
			}
			Kunde temp = runList.get(min);
			runList.set(min, runList.get(i));
			runList.set(i, temp);
		}
		return runList;
	}
	
	public static ArrayList<Kunde> findKunderMedAbonnenment(String kategori, boolean ugentlig){
		ArrayList<Kunde> resultList = new ArrayList<>();
		ArrayList<Kunde> kunder = Dao.getKunder();
		for(Kunde k : kunder){
			ArrayList<Abonnement> abonnenmenter = k.getAbonnenmenter();
			for(Abonnement a : abonnenmenter){
				if(a.isUgentlig() == ugentlig && a.getKasse().getKategori().equals(kategori)){
					resultList.add(k);
				}
			}
		}
		return resultList;
	}
	
	public static void createSomeObjects(){
		Kasse k1 = createKasse("Frugt", 100);
		Kasse k2 = createKasse("Fisk", 200);
		Kasse k3 = createKasse("Groent", 100);
		
		Kunde kun1 = createKunde(1, "Henrik");
		Kunde kun2 = createKunde(2, "Mark");
		
		kun1.createAbonnement(false, Stoerrelse.FAMILY);
		kun1.createAbonnement(true, Stoerrelse.SINGLE);
		kun2.createAbonnement(false, Stoerrelse.DOUBLE);
		kun2.createAbonnement(true, Stoerrelse.DOUBLE);
	}
}
