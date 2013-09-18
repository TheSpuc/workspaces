package minihandelelev;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Kunde {
	
	@Id
	@GeneratedValue
	private int id;
	private String navn;
	private String foedt;
	@OneToMany
	@JoinColumn
	private List<Ordre> ordrer;

	public Kunde(){
		
	}
	
	public Kunde(String navn, String foedt) {
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

	public String getFoedt() {
		return foedt;
	}

	public void addOrdre(Ordre ordre) {
		if (!ordrer.contains(ordre)) {
			ordrer.add(ordre);
		}
	}

	public void removeOrdre(Ordre ordre) {
		ordrer.remove(ordre);
	}

	public Iterator<Ordre> getOrdrer() {
		return ordrer.iterator();
	}
	
//	public Rabat getRabat(){
//		return rabat;
//	}
//	
//	public void setRabat(Rabat rabat){
//		if(this.rabat != rabat){
//			this.rabat = rabat;
//		}
//	}
//	
//	public double samletKoeb(){
//		double result = 0;
//		for(Ordre o : ordrer){
//			double samletPris = o.samletPris();
//			result += samletPris-rabat.rabatBeloeb(samletPris);
//		}
//		return result;
//	}

}
