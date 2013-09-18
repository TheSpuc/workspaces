package model;

import java.util.ArrayList;

/**
 * Models a customer
 * @author Mark Medum Bundgaard
 *
 */

public class Kunde {
	
	private String navn;
	private String adresse;
	private boolean rabat;
	ArrayList<Bestilling> bestillinger;
	
	/**
	 * Constructor for the class Kunde
	 * @param navn
	 * @param adresse
	 * @param rabat
	 */
	public Kunde(String navn, String adresse, boolean rabat){
		this.navn = navn;
		this.adresse = adresse;
		this.rabat = rabat;
		bestillinger = new ArrayList<>();
	}
	
	/**
	 * Method for adding a order
	 * @param bestilling
	 */
	public void addBestilling(Bestilling bestilling){
		if(!bestillinger.contains(bestilling)){
			bestillinger.add(bestilling);
			bestilling.setKunde(this);
		}
	}
	
	/**
	 * Method for removing a order
	 * @param bestilling
	 */
	public void removeBestilling(Bestilling bestilling){
		if(bestillinger.contains(bestilling)){
			bestillinger.remove(bestilling);
			bestilling.setKunde(null);
		}
	}
	
	/**
	 * Method for getting all orders
	 * @return
	 */
	public ArrayList<Bestilling> getBestillinger(){
		return new ArrayList<>(bestillinger);
	}
	
	/**
	 * Method for setting the name of this customer
	 * @param navn
	 */
	public void setNavn(String navn){
		this.navn = navn;
	}
	
	/**
	 * Method for getting the name of the customer
	 * @return
	 */
	public String getNavn(){
		return navn;
	}
	
	/**
	 * Method for setting the address of this customer
	 * @param adresse
	 */
	public void setAdresse(String adresse){
		this.adresse = adresse;
	}
	
	/**
	 * Method for getting the address of this customer
	 * @return
	 */
	public String getAdresse(){
		return adresse;
	}
	
	/**
	 * Method for setting the wheter the customer have
	 * savings of not
	 * @param rabat
	 */
	public void setRabat(boolean rabat){
		this.rabat = rabat;
	}
	
	/**
	 * Method for getting the savings
	 * @return
	 */
	public boolean getRabat(){
		return rabat;
	}
}
