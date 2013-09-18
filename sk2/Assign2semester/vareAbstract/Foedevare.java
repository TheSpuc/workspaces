package vareAbstract;

public class Foedevare extends Vare {
	
	private int holdbarhedsperiode;
	
	public Foedevare(double nettopris, String navn, String beskrivelse, int holdbarhedsperiode){
		super(nettopris, navn, beskrivelse);
		this.holdbarhedsperiode = holdbarhedsperiode;
	}

	public int getHoldbarhedsperiode() {
		return holdbarhedsperiode;
	}

	public void setHoldbarhedsperiode(int holdbarhedsperiode) {
		this.holdbarhedsperiode = holdbarhedsperiode;
	}
	
	public double bruttoPris(){
		double netto = super.getNettopris();
		return netto*=1.05;
	}
}
