package service;

import java.util.ArrayList;

import dao.Dao;
import model.Participant;
import model.Hotel;
import model.Conference;
import model.Companion;
import model.Signup;
import model.Trip;

/**
 * Models a class for all service calls
 * @author Mark Medum Bundgaard
 *
 */
public class Service {

	/**
	 * Creates a new Participant
	 * @param name
	 * @param address
	 * @param city
	 * @param country
	 * @param phone
	 * @param companyName
	 * @param companyPhone
	 * @return Participant
	 */
	public static Participant createParticipant(String name, String address, String city, String country, String phone, String companyName, String companyPhone){
		Participant del = new Participant(name, address, city, country, phone, companyName, companyPhone);
		Dao.addParticipant(del);
		Participant temp = del;
		return temp;
	}
	

	/**
	 * Creates a new signup
	 * @param participant != null
	 * @param conference != null
	 * @param hotel
	 * @param lecturer
	 * @param hotelAddition
	 * @param gotCompanion
	 * @param companionName
	 * @return
	 */
	public static Signup createSignup(Participant participant, Conference conference, Hotel hotel, boolean lecturer, boolean hotelAddition, boolean gotCompanion, String companionName){
		Signup til = new Signup(participant, conference, hotel, lecturer, hotelAddition, gotCompanion);
		if(gotCompanion){
			participant.createCompanion(companionName);
		}
		conference.addSignup(til);
		return til;
	}
	
	/**
	 * Get s specific companion of a participant
	 * @param participant != null
	 * @return Companion
	 */
	public static Companion getCompanion(Participant participant){
		return participant.getCompanion();
	}
	
	/**
	 * Get a specific signup of a conference
	 * @param conference != null
	 * @param participant != null
	 * @return Signup
	 */
	public static Signup getSignup(Conference conference, Participant participant){
		Signup signupFound = null;
		for(Signup signup : conference.getSignups()){
			if(signup.getParticipant().equals(participant)){
				signupFound = signup;
			}
		}
		return signupFound;
	}
	
	/**
	 * Sets all the companion trips
	 * @param companion != null
	 * @param trips != null
	 */
	public static void setCompanionTrips(Companion companion, ArrayList<Trip> trips){
		for(Trip trip : trips){
			companion.addTrip(trip);
		}
	}

	/**
	 * Creates a new trip
	 * @param name
	 * @param price
	 * @param date
	 * @return Trip
	 */
	public static Trip createTrip(String name, int price, String date){
		Trip udf = new Trip(name, price, date);
		Dao.addTrip(udf);
		return udf;
	}
	
	/**
	 * Removes a specific trip
	 * @param trip != null
	 */
	public static void removeTrip(Trip trip){
		ArrayList<Trip> tempList = Dao.getTrips();
		if(tempList.contains(trip)){
			tempList.remove(trip);
		}
		Dao.setTrips(tempList);
	}

	/**
	 * Creates a new conference
	 * @param name
	 * @param price
	 * @param conferenceDays
	 * @param fromDate
	 * @param toDate
	 * @return Conference
	 */
	public static Conference createConference(String name, int price, int conferenceDays, String fromDate, String toDate){
		Conference con = new Conference(name, price, conferenceDays, fromDate, toDate);
		Dao.addConference(con);
		return con;
	}
	
	/**
	 * Getting all participants of a specific conference
	 * @param conference != null
	 * @return ArrayList<Participant>
	 */
	public static ArrayList<Participant> getConferenceParticipants(Conference conference){
		ArrayList<Participant> tempList = new ArrayList<>();
		for(Signup sign : conference.getSignups()){
			tempList.add(sign.getParticipant());
		}
		return tempList;
	}
	
	/**
	 * Sets all the hotels
	 * @param con != null
	 * @param list != null
	 */
	public static void setHotels(Conference con, ArrayList<Hotel> list){
		con.setHotels(list);
	}
	
	/**
	 * Removes a specific hotel
	 * @param hotel != null
	 */
	public static void removeHotel(Hotel hotel){
		ArrayList<Hotel> tempList = Dao.getHotels();
		if(tempList.contains(hotel)){
			tempList.remove(hotel);
		}
		Dao.setHotels(tempList);
	}
	
	/**
	 * Removes a specific conference
	 * @param conference != null
	 */
	public static void removeConference(Conference conference){
		ArrayList<Conference> tempList = Dao.getConferences();
		if(tempList.contains(conference)){
			tempList.remove(conference);
		}
		Dao.setConferencr(tempList);
	}
	
	/**
	 * Sets all the trips
	 * @param conference != null
	 * @param trips != null
	 */
	public static void setTrips(Conference conference, ArrayList<Trip> trips){
		ArrayList<Trip> tempList = Dao.getTrips();
		for(Trip tp : trips){
			conference.addTrip(tp);
			tempList.remove(tp);
		}
		Dao.setTrips(tempList);
	}

	/**
	 * Creates a new hotel
	 * @param name
	 * @param priceSingleRoomPrNight
	 * @param priceDoubleRoomPrNight
	 * @param addition
	 * @param additionDescription
	 * @return Hotel
	 */
	public static Hotel createHotel(String name, int priceSingleRoomPrNight, int priceDoubleRoomPrNight, int addition, String additionDescription){
		Hotel hol = new Hotel(name, priceSingleRoomPrNight, priceDoubleRoomPrNight, addition, additionDescription);
		Dao.addHotel(hol);
		return hol;
	}
	
	/**
	 * Calculates price for a specific signup
	 * @param signup != null
	 * @return double
	 */
	public static double calculatePrice(Signup signup){
		return signup.calculatePrice();
	}
	
	/**
	 * Gets hotel addition for a specific hotel
	 * @param hotel != null
	 * @return boolean
	 */
	public static boolean getHotelAddition(Hotel hotel){
		return (hotel.getAddition() > 0);
	}
	
	/**
	 * Gets the addition description for a specific hotel
	 * @param hotel != null
	 * @return String
	 */
	public static String getHotelAdditionDescription(Hotel hotel){
		return hotel.getAdditionDescription();
	}

	/**
	 * Getting all participants
	 * @return ArrayList<Participant>
	 */
	public static ArrayList<Participant> getParticipante(){
		return Dao.getParticipants();
	}

	/**
	 * Getting all the conferences
	 * @return ArrayList<Conference>
	 */
	public static ArrayList<Conference> getConferences(){
		return Dao.getConferences();
	}

	/**
	 * Getting all the hotels
	 * @return ArrayList<Hotel>
	 */
	public static ArrayList<Hotel> getHotels(){
		return Dao.getHotels();
	}
	
	/**
	 * Getting all hotel for a specific conference
	 * @param con != null
	 * @return ArrayList<Hotel>
	 */
	public static ArrayList<Hotel> getHotels(Conference con){
		return con.gethotels();
	}

	/**
	 * Getting all trips
	 * @return ArrayList<Trip>
	 */
	public static ArrayList<Trip> getTrips(){
		return Dao.getTrips();
	}
	
	/**
	 * Getting all the trips for a specific conference
	 * @param con != null
	 * @return ArrayList<Trip>
	 */
	public static ArrayList<Trip> getTrips(Conference con){
		return con.getTrips();
	}
}
