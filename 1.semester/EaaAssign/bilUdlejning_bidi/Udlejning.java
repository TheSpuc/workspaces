package bilUdlejning_bidi;

import java.util.ArrayList;

public class Udlejning {

	private int nr;
	private int antalDage;
	private String dato;
	private ArrayList<Bil> biler;

	public Udlejning(int nr, String dato, int dage){
		this.nr = nr;
		this.antalDage = dage;
		this.dato = dato;
		biler = new ArrayList<>();
	}
	
	public void addBil(Bil bi){
		if(!biler.contains(bi)){
			biler.add(bi);
			bi.addUdlejning(this);
		}
	}
	
	public void removeBil(Bil bi){
		if(biler.contains(bi)){
			biler.remove(bi);
			bi.removeUdlejning(this);
		}
	}

	public ArrayList<Bil> getBiler(){
		return new ArrayList<Bil>(biler);
	}
	
	public String toString(){
		return nr+"";
	}
	
	public int getPris(){
		int pris = 0;
		for(Bil bi: biler){
			pris += bi.getPrisPrDag();
		}
		return pris*antalDage;
	}

	public void setAntalDage(int dage){
		antalDage = dage;
	}

	public int getAntalDage(){
		return antalDage;
	}

}
