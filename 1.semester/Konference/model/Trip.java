package model;

import java.util.ArrayList;

/**
 * Models a trip
 * @author Mark Medum Bundgaard
 *
 */
public class Trip {

	private String name;
	private int price;
	private String date;
	private ArrayList<Companion> companions;

	/**
	 * Constructor for the class trip
	 * @param name
	 * @param price
	 * @param date
	 */
	public Trip(String name, int price, String date){
		this.name = name;
		this.price = price;
		this.date = date;
		companions = new ArrayList<>();
	}
	
	/**
	 * Getting the price
	 * @return integer
	 */
	public int getPrice(){
		return price;
	}

	/**
	 * Add companion
	 * @param companion != null
	 */
	public void addCompanion(Companion companion){
		if (!companions.contains(companion)){
			companions.add(companion);
			companion.addTrip(this);
		}
	}

	/**
	 * Remove companion
	 * @param companion != null
	 */
	public void removeCompanion(Companion companion)
	{
		if (companions.contains(companion)){
			companions.remove(companion);
			companion.removeTrip(this);
		}
	}
	
	/**
	 * Getting all companions
	 * @return ArrayList<Companion>
	 */
	public ArrayList<Companion> getCompanions()
	{
		return new ArrayList<Companion>(companions);
	}
	
	public String toString(){
		return name;
	}
}