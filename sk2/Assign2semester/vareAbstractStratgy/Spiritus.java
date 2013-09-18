package vareAbstractStratgy;

public class Spiritus extends Vare {
	
	private int alkoholprocent;
	
	public Spiritus(double nettopris, String navn, String beskrivelse, int alkoholprocent){
		super(nettopris, navn, beskrivelse);
		this.alkoholprocent = alkoholprocent;
		setMoms(new SpiritusMoms(alkoholprocent));
	}

	public int getAlkoholprocent() {
		return alkoholprocent;
	}

	public void setAlkoholprocent(int alkoholprocent) {
		this.alkoholprocent = alkoholprocent;
	}
	
}
