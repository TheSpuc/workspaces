package vareAbstract;

public class ElArtikel extends Vare{
	
	private int gennemsnitligEl;
	
	public ElArtikel(double nettopris, String navn, String beskrivelse, int gennemsnitligEl){
		super(nettopris, navn, beskrivelse);
		this.gennemsnitligEl = gennemsnitligEl;
	}

	public int getGennemsnitligEl() {
		return gennemsnitligEl;
	}

	public void setGennemsnitligEl(int gennemsnitligEl) {
		this.gennemsnitligEl = gennemsnitligEl;
	}
	
	public double bruttoPris(){
		double brutto = super.getNettopris();
		if((brutto*1.30)-brutto <= 3){
			brutto+=3;
		}else brutto*=1.30;
		return brutto;
	}
}
