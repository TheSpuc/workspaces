package TogPlads;

public class Tog implements Comparable<Tog> {

	private String rute;
	private int nr;
	private Plads[] pladser;

	public Tog(String rute, int nr, int antalPladser){
		this.rute = rute;
		this.nr = nr;
		createPlads(antalPladser);
	}

	public String getRute(){
		return rute;
	}

	public int getNr(){
		return nr;
	}

	private void createPlads(int antalPladser){
		pladser = new Plads[antalPladser];
		for(int i=0; i<antalPladser; i++){
			Plads pl = new Plads(i+1, this);
			pladser[i] = pl;
		}
	}
	
	public void deletePlads(int nr){
		boolean isFound = false;
		int index = 0;
		while(index < pladser.length && !isFound){
			if(pladser[index].getNr()== (nr)){
				isFound = true;
				pladser[index].unsetTog();
				pladser[index] = null;
			}
			index++;
		}
	}
	
	public void setReserveret(int nr, boolean reserveret){
		pladser[nr-1].setReserveret(reserveret);
	}
	
	public boolean isReserveret(int nr){
		return pladser[nr-1].isReserveret();
	}
	
	public int ikkeReserveret(){
		int total = 0;
		for(int i=0; i<pladser.length; i++){
			if(!pladser[i].isReserveret()){
				total++;
			}
		}
		return total;
	}
	
	public int compareTo(Tog tog){
		int temp = rute.compareTo(tog.getRute());
		if(temp == 0){
			temp = ((Integer)nr).compareTo(tog.getNr());
		}
		return temp;
	}
	
	public String toString(){
		return rute + ", " + nr;
	}
}
