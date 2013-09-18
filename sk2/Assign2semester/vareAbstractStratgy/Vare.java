package vareAbstractStratgy;

/**
 * Static int for the moms, then it's much easier to remake the moms information.
 * @author Mark Medum Bundgaard
 *
 */
public class Vare {
	
	
	private Moms moms;
	private double nettopris;
	private String navn;
	private String beskrivelse;
	
	public Vare(double nettopris, String navn, String beskrivelse){
		this.nettopris = nettopris;
		this.navn = navn;
		this.beskrivelse = beskrivelse;
		setMoms(new VareMoms());
	}
	
	public void setMoms(Moms moms){
		this.moms = moms;
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
		double currentMoms = moms.beregnMoms();
		if(currentMoms == 3.0){
			nettopris += currentMoms;
		}else nettopris *= currentMoms;
		System.out.println("Brutto: " + nettopris);
		return nettopris;
	}
	
	public String toString(){
		return getNavn() + ", moms: " + moms.beregnMoms();
	}
}
