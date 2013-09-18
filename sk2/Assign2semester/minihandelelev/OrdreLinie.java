package minihandelelev;

public class OrdreLinie {
	private int linieNr;

	private int antalStk;

	private Vare vare;

	OrdreLinie(int linieNr, int antalStk, Vare vare) {
		this.linieNr = linieNr;
		this.antalStk = antalStk;
		this.vare = vare;

	}

	public int getLinieNr() {
		return linieNr;
	}

	public int getAntalStk() {
		return antalStk;
	}
	public Vare getVare(){
		return vare;
	}
	
	public double samletPris(){
		return vare.getStkPris()*getAntalStk();
	}

}
