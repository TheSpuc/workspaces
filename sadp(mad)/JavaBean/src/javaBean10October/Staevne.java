package javaBean10October;

import java.util.HashSet;
import java.util.Set;

public class Staevne {

	private String titel;
	public Set<Kamp> kampe;
	
	public Staevne(String titel){
		this.titel = titel;
		kampe = new HashSet<>(); 
	}
	
	public Set<Kamp> getKampe(){
		return new HashSet<>(kampe);
	}
	
	public void addKamp(Kamp kamp){
		kampe.add(kamp);
	}
	
	public void removeKamp(Kamp kamp){
		kampe.remove(kamp);
	}
	
	public String getTitel(){
		return titel;
	}
	
	public void setTitel(String titel){
		this.titel = titel;
	}
	
	@Override
	public String toString(){
		return titel;
	}
}
