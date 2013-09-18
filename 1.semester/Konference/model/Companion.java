package model;

import java.util.ArrayList;

/**
 * Models a Companion
 * @author Mark Medum Bundgaard
 *
 */
public class Companion {

	private String name;
	private String participantName;
	private int participantNumber;
	private ArrayList<Trip> trips;

	/**
	 * Constructor for the class Companion
	 * @param name
	 * @param participantName
	 * @param participantNumber
	 */
	public Companion(String name, String participantName, int participantNumber){
		this.name = name;
		this.participantName = participantName;
		this.participantNumber = participantNumber;
		trips = new ArrayList<>();
	}
	
	/**
	 * Adds a trip
	 * @param trip != null
	 */
	public void addTrip(Trip trip)
	{
		if(!trips.contains(trip)){
			trips.add(trip);
			trip.addCompanion(this);
		}
	}

	/**
	 * Removes a trip
	 * @param trip != null
	 */
	public void removeTrip(Trip trip)
	{
		if (trips.contains(trip)){
			trips.remove(trip);
			trip.removeCompanion(this);
		}
	}
	
	/**
	 * Return the arraylist with all trips
	 * @return ArrayList<Trip>
	 */
	public ArrayList<Trip> getTrips()
	{
		return new ArrayList<Trip>(trips);
	}

	/**
	 * calculate the total price for a the companion trips
	 * @return int 
	 */
	public int calculateTripsPrice(){
		int total = 0;
		for(Trip tr : trips){
			total += tr.getPrice();
		}
		return total;
	}

	public String toString(){
		return name + " (" + participantName + ", " + participantNumber +")" + " Chosen Trips: " + trips;
	}

}