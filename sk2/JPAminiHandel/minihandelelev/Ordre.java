package minihandelelev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Ordre {
	
	@Id
	@GeneratedValue
	private int nummer;
	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn
	private List<OrdreLinie> ordrelinier;

	public Ordre(){
		
	}
	
	public Ordre(Kunde kunde) {
		ordrelinier = new ArrayList<OrdreLinie>();
		kunde.addOrdre(this);
	}

	public int getNummer() {
		return nummer;

	}

	public void createOrdreLinie(int antalStk, Vare vare) {
		OrdreLinie ol = new OrdreLinie(antalStk, vare);
		ordrelinier.add(ol);
	}

	public Iterator<OrdreLinie> getOrdreLinier() {
		return ordrelinier.iterator();
	}

	public double samletPris(){
		double result = 0;
		for(OrdreLinie o : ordrelinier){
			result += o.samletPris();
		}
		return result;
	}
}
