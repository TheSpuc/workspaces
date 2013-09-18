package bilUdlejning_unidi;


public class Bil {
	
	private String nr;
	private int prisPrDag;
	private int anskafAar;

	public Bil(String nr, int aar){
		this.nr = nr;
		anskafAar = aar;
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
