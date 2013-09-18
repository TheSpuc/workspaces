package dao;

import java.util.ArrayList;

import model.Participant;
import model.Hotel;
import model.Conference;
import model.Trip;

/**
 * Class for saving all information
 * @author Mark Medum Bundgaard
 *
 */
public class Dao {

	private static ArrayList<Trip> trips = new ArrayList<>();
	private static ArrayList<Conference> conferences = new ArrayList<>();
	private static ArrayList<Participant> participants = new ArrayList<>();
	private static ArrayList<Hotel> hotels = new ArrayList<>();

	/**
	 * Getting all trips 
	 * @return ArrayList<Trip>
	 */
	public static ArrayList<Trip> getTrips(){
		return new ArrayList<Trip>(trips);
	}

	/**
	 * Setting trips
	 * @param list != null
	 */
	public static void setTrips(ArrayList<Trip> list){
		trips = list;
	}

	/**
	 * Add a trip
	 * @param tr != null
	 */
	public static void addTrip(Trip tr){
		if(!trips.contains(tr)){
			trips.add(tr);
		}
	}

	/**
	 * Getting all conferences
	 * @return ArrayList<Conference<
	 */
	public static ArrayList<Conference> getConferences(){
		return new ArrayList<Conference>(conferences);
	}

	/**
	 * Setting conferences
	 * @param list != null
	 */
	public static void setConferencr(ArrayList<Conference> list){
		conferences = list;
	}

	/**
	 * Add a conference
	 * @param cof != null
	 */
	public static void addConference(Conference cof){
		if(!conferences.contains(cof)){
			conferences.add(cof);
		}
	}

	/**
	 * Getting all participants
	 * @return ArrayList<Participant>
	 */
	public static ArrayList<Participant> getParticipants(){
		return new ArrayList<Participant>(participants);
	}

	/**
	 * Setting participants
	 * @param list != null
	 */
	public static void setParticipants(ArrayList<Participant> list){
		participants = list;
	}

	/**
	 * Add participant
	 * @param par != null
	 */
	public static void addParticipant(Participant par){
		if(!participants.contains(par)){
			participants.add(par);
		}
	}

	/**
	 * Getting all hotels
	 * @return ArrayList<Hotel>
	 */
	public static ArrayList<Hotel> getHotels(){
		return new ArrayList<Hotel>(hotels);
	}

	/**
	 * Setting hotels
	 * @param list != null
	 */
	public static void setHotels(ArrayList<Hotel> list){
		hotels = list;
	}

	/**
	 * Add a hotel
	 * @param hol != null
	 */
	public static void addHotel(Hotel hol){
		if(!hotels.contains(hol)){
			hotels.add(hol);
		}
	}

	/**
	 * For adding some ground information
	 */
	public static void addStuff(){
		Hotel ho1 = new Hotel("moo", 1, 1, 0, "");
		Dao.addHotel(ho1);
		Hotel ho2 = new Hotel("moo1", 1, 1, 0, "");
		Dao.addHotel(ho2);
		Hotel ho3 = new Hotel("moo2", 1, 1, 0, "");
		Dao.addHotel(ho3);
		Hotel ho4 = new Hotel("moo3", 1, 1, 0, "");
		Dao.addHotel(ho4);
		Hotel ho5 = new Hotel("moo4", 1, 1, 0, "");
		Dao.addHotel(ho5);

		Trip ud1 = new Trip("Aber", 50, "");
		Dao.addTrip(ud1);
		Trip ud2 = new Trip("Grise", 50, "");
		Dao.addTrip(ud2);
		Trip ud3 = new Trip("Agurker", 50, "");
		Dao.addTrip(ud3);
	}
}
