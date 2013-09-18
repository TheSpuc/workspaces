package minihandelelev;

import java.util.ArrayList;
import java.util.Iterator;


public class Ordre {
	private int nummer;

	private ArrayList<OrdreLinie> ordrelinier;

	public Ordre(int nummer, Kunde kunde) {
		this.nummer = nummer;
		ordrelinier = new ArrayList<OrdreLinie>();
		kunde.addOrdre(this);
	}

	public int getNummer() {
		return nummer;

	}

	public void createOrdreLinie(int linieNr, int antalStk, Vare vare) {
		OrdreLinie ol = new OrdreLinie(linieNr, antalStk, vare);
		ordrelinier.add(ol);
	}

	public Iterator<OrdreLinie> getOrdreLinier() {
		return ordrelinier.iterator();
	}

	public double samletPris(){
		double result = 0;
		for(OrdreLinie o : ordrelinier){
			result += o.samletPris();
		}
		return result;
	}
}
