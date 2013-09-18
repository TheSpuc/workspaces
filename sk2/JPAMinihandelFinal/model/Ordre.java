package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;

@Entity
public class Ordre {
	@Id
	private int nummer;

	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn
	@OrderColumn
	private List<OrdreLinie> ordrelinier;

	public Ordre(){
		ordrelinier = new ArrayList<OrdreLinie>();
	}
	public Ordre(int nummer, Kunde kunde) {
		this.nummer = nummer;
		ordrelinier = new ArrayList<OrdreLinie>();
		kunde.addOrdre(this);
	}

	public int getNummer() {
		return nummer;

	}

	public void createOrdreLinie(int linieNr, int antalStk, Vare vare) {
		OrdreLinie ol = new OrdreLinie(linieNr, antalStk, vare);
		ordrelinier.add(ol);
	}

	public List<OrdreLinie> getOrdreLinier() {
		return ordrelinier;
	}

	@Override
	public String toString() {
		return "O" + nummer;
	}
}
