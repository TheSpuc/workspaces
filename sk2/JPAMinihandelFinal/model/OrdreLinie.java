package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class OrdreLinie {
	@Id
	@GeneratedValue
	private int id;
	private int linieNr;

	private int antalStk;

	@ManyToOne
	private Vare vare;

	OrdreLinie(){
		
	}
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

	public Vare getVare() {
		return vare;
	}

	@Override
	public String toString() {
		return "L" + linieNr;
	}
}
