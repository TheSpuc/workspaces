package minihandelelev;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Entity
public class OrdreLinie {
	
	@Id
	@GeneratedValue
	private int linieNr;
	private int antalStk;
	@ManyToOne
	private Vare vare;
	
	public OrdreLinie(){
		
	}
	
	OrdreLinie(int antalStk, Vare vare) {
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
