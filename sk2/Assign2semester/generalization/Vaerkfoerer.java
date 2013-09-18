package generalization;

public class Vaerkfoerer extends Ansat {

	private String aarVaerk;
	private int tillaeg;
	
	
	public Vaerkfoerer(String navn, String adresse, String aarEksamen, int loen, String aarVaerk, int tillaeg){
		super(navn, adresse, loen);
		this.aarVaerk = aarVaerk;
		this.tillaeg = tillaeg;
	}


	public String getAarVaerk() {
		return aarVaerk;
	}


	public void setAarVaerk(String aarVaerk) {
		this.aarVaerk = aarVaerk;
	}


	public int getTillaeg() {
		return tillaeg;
	}


	public void setTillaeg(int tillaeg) {
		this.tillaeg = tillaeg;
	}
	
	public int beregnLoen(){
		return super.getLoen()*37 + tillaeg;
	}
	
	
}
