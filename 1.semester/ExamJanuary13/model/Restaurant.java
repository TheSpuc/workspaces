package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Models a restaurant
 * @author Mark Medum Bundgaard
 *
 */

public class Restaurant implements Comparable<Restaurant> {
	
	private String navn;
	private Karakter stjerner;
	private ArrayList<Bestilling> bestillinger;
	
	
	/**
	 * Constructor for the class Restaurant
	 * @param navn
	 * @param stjerner
	 */
	public Restaurant(String navn, Karakter stjerner){
		this.navn = navn;
		this.stjerner = stjerner;
		bestillinger = new ArrayList<>();
	}
	
	/**
	 * Method for creating a order
	 * @param leveringsTid
	 * @param kunde
	 * @return
	 */
	public Bestilling createBestilling(Date leveringsTid, Kunde kunde){
		Bestilling bestilling = new Bestilling(leveringsTid, kunde);
		bestillinger.add(bestilling);
		return bestilling;
	}
	
	
	/**
	 * Method for adding a order
	 * @param bestilling
	 */
	public void addBestilling(Bestilling bestilling){
		if(!bestillinger.contains(bestilling)){
			bestillinger.add(bestilling);
		}
	}
	
	/**
	 * Method for removing a order
	 * @param bestilling
	 */
	public void removeBestilling(Bestilling bestilling){
		if(bestillinger.contains(bestilling)){
			bestillinger.remove(bestilling);
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
	 * Method for setting the name of the restaurant
	 * @param navn
	 */
	public void setNavn(String navn){
		this.navn = navn;
	}
	
	/**
	 * Method for getting the name of the restaurant
	 * @return
	 */
	public String getNavn(){
		return navn;
	}
	
	/**
	 * Method for setting the stars of the restaurant
	 * @param stjerner
	 */
	public void setStjerner(Karakter stjerner){
		this.stjerner = stjerner;
	}
	
	/**
	 * Method for getting the stars of the restaurant
	 * @return
	 */
	public Karakter getStjerner(){
		return stjerner;
	}
	
	/**
	 * Method for finding all orders on a specific date
	 * @param date
	 * @return
	 */
	public ArrayList<String> transportseddel(Date date){
		ArrayList<String> resultList = new ArrayList<>();
		for(Bestilling b : bestillinger){
			if(b.getLeveringsTid().equals(date)){
				String temp = b.getKunde().getNavn() + ", " +b.getKunde().getAdresse();
				resultList.add(temp);
			}
		}
		return resultList;
	}
	
	/**
	 * Method for comparing two restaurants
	 */
	public int compareTo(Restaurant r){
		return getStjerner().compareTo(r.getStjerner());
	}
	

	public String toString(){
		return navn + " " + stjerner;
	}
}
