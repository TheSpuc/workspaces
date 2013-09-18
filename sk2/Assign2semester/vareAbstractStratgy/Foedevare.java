package vareAbstractStratgy;

public class Foedevare extends Vare {
	
	private int holdbarhedsperiode;
	
	public Foedevare(double nettopris, String navn, String beskrivelse, int holdbarhedsperiode){
		super(nettopris, navn, beskrivelse);
		this.holdbarhedsperiode = holdbarhedsperiode;
		setMoms(new FoedevareMoms());
	}

	public int getHoldbarhedsperiode() {
		return holdbarhedsperiode;
	}

	public void setHoldbarhedsperiode(int holdbarhedsperiode) {
		this.holdbarhedsperiode = holdbarhedsperiode;
	}
	
}
