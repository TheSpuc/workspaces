package vareAbstract;

/**
 * Static int for the moms, then it's much easier to remake the moms information.
 * @author Mark Medum Bundgaard
 *
 */
public class Vare {
	
	private double nettopris;
	private String navn;
	private String beskrivelse;
	
	public Vare(double nettopris, String navn, String beskrivelse){
		this.nettopris = nettopris;
		this.navn = navn;
		this.beskrivelse = beskrivelse;
	}

	public double getNettopris() {
		return nettopris;
	}

	public void setNettopris(double nettopris) {
		this.nettopris = nettopris;
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public String getBeskrivelse() {
		return beskrivelse;
	}

	public void setBeskrivelse(String beskrivelse) {
		this.beskrivelse = beskrivelse;
	}
	
	public double bruttoPris(){
		return nettopris*=1.25;
	}
	
	public String toString(){
		return getNavn() + ", " + bruttoPris(); 
	}
}
