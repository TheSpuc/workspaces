package bilUdlejning_bidi;

import java.util.ArrayList;


public class Bil {
	
	private String nr;
	private int prisPrDag;
	private int anskafAar;
	private ArrayList<Udlejning> udlejninger;

	public Bil(String nr, int aar){
		this.nr = nr;
		anskafAar = aar;
		udlejninger = new ArrayList<>();
	}
	
	public void addUdlejning(Udlejning ud){
		if(!udlejninger.contains(ud)){
			udlejninger.add(ud);
			ud.addBil(this);
		}
	}
	
	public void removeUdlejning(Udlejning ud){
		if(udlejninger.contains(ud)){
			udlejninger.remove(ud);
			ud.removeBil(this);
		}
	}
	
	public ArrayList<Udlejning> getUdlejninger(){
		return new ArrayList<Udlejning>(udlejninger);
	}
	
	public int antalDageUdlejet(){
		int antalDage = 0;
		for(Udlejning ud: udlejninger){
			antalDage += ud.getAntalDage();
		}
		return antalDage;
	}
	
	public String toString(){
		return nr+"";
	}
	
	public void setPrisPrDay(int pris){
		prisPrDag = pris;
	}
	
	public int getPrisPrDag(){
		return prisPrDag;
	}
	
	public String getNr(){
		return nr;
	}
	
	public int getAar(){
		return anskafAar;
	}
	
}
