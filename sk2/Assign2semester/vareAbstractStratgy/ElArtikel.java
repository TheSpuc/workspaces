package vareAbstractStratgy;

public class ElArtikel extends Vare{
	
	private int gennemsnitligEl;
	
	public ElArtikel(double nettopris, String navn, String beskrivelse, int gennemsnitligEl){
		super(nettopris, navn, beskrivelse);
		this.gennemsnitligEl = gennemsnitligEl;
		setMoms(new ElMoms(nettopris));
	}

	public int getGennemsnitligEl() {
		return gennemsnitligEl;
	}

	public void setGennemsnitligEl(int gennemsnitligEl) {
		this.gennemsnitligEl = gennemsnitligEl;
	}
	
}
