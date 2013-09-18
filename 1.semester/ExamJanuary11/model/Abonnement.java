package model;

public class Abonnement {
	
	private Kasse kasse;
	private boolean ugentlig;
	private Stoerrelse stoerrelse;
	
	Abonnement(boolean ugentlig, Stoerrelse stoerrelse){
		this.ugentlig = ugentlig;
		this.stoerrelse = stoerrelse;
		kasse = null;
	}
	
	public boolean isUgentlig(){
		return ugentlig;
	}
	
	public Stoerrelse getStoerrelse(){
		return stoerrelse;
	}
	
	public void setUgentlig(boolean ugentlig){
		this.ugentlig = ugentlig;
	}
	
	public void setStoerrelse(Stoerrelse stoerrelse){
		this.stoerrelse = stoerrelse;
	}
	
	public void setKasse(Kasse kasse){
		if(this.kasse != kasse){
			this.kasse = kasse;
		}
	}
	
	public void removeKasse(){
		this.kasse = null;
	}
	
	public Kasse getKasse(){
		return kasse;
	}
	
	public double getKassePris(){
		double pris = 0;
		if(kasse != null){
			if(stoerrelse.equals(Stoerrelse.FAMILY)){
				pris = kasse.getPris()*2.5;
			}else if(stoerrelse.equals(Stoerrelse.DOUBLE)){
				pris = kasse.getPris()*1.75;
			}else if(stoerrelse.equals(Stoerrelse.SINGLE)){
				pris = kasse.getPris();
			}
		}
		return pris;
	}
	
}
