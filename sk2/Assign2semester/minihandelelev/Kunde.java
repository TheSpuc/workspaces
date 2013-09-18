package minihandelelev;

import java.util.Date;
import java.util.ArrayList;
import java.util.Iterator;

public class Kunde {
	private String navn;

	private Date foedt;

	private ArrayList<Ordre> ordrer;
	
	private Rabat rabat;

	public Kunde(String navn, Date foedt, Rabat rabat) {
		setRabat(rabat);
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
	
	public Rabat getRabat(){
		return rabat;
	}
	
	public void setRabat(Rabat rabat){
		if(this.rabat != rabat){
			this.rabat = rabat;
		}
	}
	
	public double samletKoeb(){
		double result = 0;
		for(Ordre o : ordrer){
			double samletPris = o.samletPris();
			result += samletPris-rabat.rabatBeloeb(samletPris);
		}
		return result;
	}

}
