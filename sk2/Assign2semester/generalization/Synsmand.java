package generalization;

public class Synsmand extends Mekaniker {

	private int antalSyn;
	
	public Synsmand(String navn, String adresse, String aarEksamen, int loen, int antalSyn){
		super(navn, adresse, aarEksamen, loen);
		this.antalSyn = antalSyn;
	}

	public int getAntalSyn() {
		return antalSyn;
	}

	public void setAntalSyn(int antalSyn) {
		this.antalSyn = antalSyn;
	}
	
	public int beregnLoen(){
		return super.beregnLoen() + getAntalSyn()*29;
	}
}
