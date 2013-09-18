package model;

import java.util.ArrayList;

/**
 * Models a Conference
 * @author Mark Medum Bundgaard
 *
 */
public class Conference {

	private String name;
	private int price;
	private int conferenceDays;
	private String fromDate;
	private String toDate;
	private ArrayList<Signup> signups;
	private ArrayList<Hotel> hotels;
	private ArrayList<Trip> trips;

	/**
	 * Constructor for the class Conference
	 * @param name
	 * @param price
	 * @param conferenceDays
	 * @param fromDate
	 * @param toDate
	 */
	public Conference(String name, int price, int conferenceDays, String fromDate, String toDate) {
		this.name = name;
		this.price = price;
		this.conferenceDays = conferenceDays;
		this.fromDate = fromDate;
		this.toDate = toDate;
		signups = new ArrayList<>();
		hotels = new ArrayList<>();
		trips = new ArrayList<>();
	}
	
	/**
	 * Getting the price for the conference
	 * @return
	 */
	public int getPrice(){
		return price;
	}

	/**
	 * Getting the days of the conference
	 * @return
	 */
	public int getConferenceDays(){
		return conferenceDays;
	}

	/**
	 * Add a signup
	 * @param Signup != null
	 */
	public void addSignup(Signup Signup){
		if(!signups.contains(Signup)){
			signups.add(Signup);
			Signup.setConference(this);
		}
	}
	
	/**
	 * Remove a signup
	 * @param Signup != null
	 */
	public void removeSignup(Signup Signup){
		if(signups.contains(Signup)){
			signups.remove(Signup);
			Signup.setConference(null);
		}
	}

	/**
	 * Getting the ArrayList with all signups
	 * @return ArrayList<Signup>
	 */
	public ArrayList<Signup> getSignups(){
		return new ArrayList<Signup>(signups);
	}

	/**
	 * Add a hotel
	 * @param hotel != null
	 */
	public void addHotel(Hotel hotel){
		if (!hotels.contains(hotel)) {
			hotels.add(hotel);
		}
	}

	/**
	 * Remove a hotel
	 * @param hotel != null
	 */
	public void removeHotel(Hotel hotel){
		if (hotels.contains(hotel)) {
			hotels.remove(hotel);
		}
	}
	
	/**
	 * Set all hotels
	 * @param list != null
	 */
	public void setHotels(ArrayList<Hotel> list){
		hotels = list;
	}

	/**
	 * Getting all hotels
	 * @return ArrayList<Hotel>
	 */
	public ArrayList<Hotel> gethotels(){
		return new ArrayList<Hotel>(hotels);
	}
	
	/**
	 * Add a trip
	 * @param trip != null
	 */
	public void addTrip(Trip trip){ 
		if(!trips.contains(trip)){
			trips.add(trip);
		}
	}
	
	/**
	 * Remove a trip
	 * @param trip != null
	 */
	public void removeTrip(Trip trip){
		if(trips.contains(trip)){
			trips.remove(trip);
		}
	}

	/**
	 * Getting all trips
	 * @return ArrayList<Trip>
	 */
	public ArrayList<Trip> getTrips() {
		return new ArrayList<Trip>(trips);
	}

	public String toString(){
		return name + ", nr of days: " + conferenceDays;
	}
}
