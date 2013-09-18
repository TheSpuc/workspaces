package generalization;

public class Mekaniker extends Ansat{

	private String aarEksamen;
	
	public Mekaniker(String navn, String adresse, String aarEksamen, int loen){
		super(navn, adresse, loen);
		this.aarEksamen = aarEksamen;
	}

	public String getAarEksamen() {
		return aarEksamen;
	}

	public void setAarEksamen(String aarEksamen) {
		this.aarEksamen = aarEksamen;
	}

	public int beregnLoen(){
		return super.getLoen()*37;
	}
}
