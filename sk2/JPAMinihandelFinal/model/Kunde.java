package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OrderColumn;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
public class Kunde {
	@Id
	private String navn;

	@Temporal(TemporalType.DATE)
	private Date foedt;

	@OneToMany
	@JoinColumn
	@OrderColumn
	private List<Ordre> ordrer;
	
	
	public Kunde(){
		ordrer = new ArrayList<Ordre>();
	}
	public Kunde(String navn, Date foedt) {
		this.navn = navn;
		this.foedt = foedt;
		ordrer = new ArrayList<Ordre>();
	}

	public String getNavn() {
		return navn;
	}

	public void setNavn(String navn) {
		this.navn = navn;
	}

	public Date getFoedt() {
		return foedt;
	}

	public void addOrdre(Ordre ordre) {
		ordrer.add(ordre);
	}

	public void removeOrdre(Ordre ordre) {
		ordrer.remove(ordre);
	}

	public List<Ordre> getOrdrer() {
		return ordrer;
	}

	@Override
	public String toString() {
		return navn;
	}
}
