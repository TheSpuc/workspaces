package model;

import java.util.ArrayList;

public class Kunde implements Comparable<Kunde> {

	private int id;
	private String navn;
	private ArrayList<Abonnement> abonnementer;

	public Kunde(int id, String navn){
		this.id = id;
		this.navn = navn;
		abonnementer = new ArrayList<>();
	}

	public void setNavn(String navn){
		this.navn = navn;
	}

	public String getNavn(){
		return navn;
	}

	public void setId(int id){
		this.id = id;
	}

	public int getId(){
		return id;
	}

	public ArrayList<Abonnement> getAbonnenmenter(){
		return new ArrayList<>(abonnementer);
	}

	public Abonnement createAbonnement(boolean ugentlig, Stoerrelse stoerrelse){
		Abonnement a = new Abonnement(ugentlig, stoerrelse);
		abonnementer.add(a);
		return a;
	}

	public void removeAbonnenment(Abonnement abonnement){
		if(abonnementer.contains(abonnement)){
			abonnementer.remove(abonnement);
		}
	}

	public double getMaanedsPris(){
		double pris = 0;
		for(Abonnement a : abonnementer){
			if(a.isUgentlig()){
				pris += a.getKassePris()*4;
			}else pris += a.getKassePris()*2;
		}
		return pris;
	}

	public boolean harUgeAbonnenment(String kategori){
		boolean found = false;
		int index = 0;
		while(index < abonnementer.size() && !found){
			Kasse k = abonnementer.get(index).getKasse();
			if(k != null){
				if(k.getKategori().equals(kategori)){
					found = true;
				}else index++;
			}
		}
		return found;
	}

	public int compareTo(Kunde k){
		return getId() - k.getId();
	}
	
	public String toString(){
		return "" +id;
	}

}
