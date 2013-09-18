package vareAbstract;

public class Spiritus extends Vare {
	
	private int alkoholprocent;
	
	public Spiritus(double nettopris, String navn, String beskrivelse, int alkoholprocent){
		super(nettopris, navn, beskrivelse);
		this.alkoholprocent = alkoholprocent;
	}

	public int getAlkoholprocent() {
		return alkoholprocent;
	}

	public void setAlkoholprocent(int alkoholprocent) {
		this.alkoholprocent = alkoholprocent;
	}
	
	public double bruttoPris(){
		double brutto = super.getNettopris();
		if(getAlkoholprocent() > 40){
			brutto*=2.20;
		}else brutto*=1.80;
		return brutto;
	}
	
}
