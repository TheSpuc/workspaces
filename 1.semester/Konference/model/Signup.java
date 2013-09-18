package model;

/**
 * Models a signup for a conference
 * @author Mark Medum Bundgaard
 *
 */
public class Signup {

	private boolean lecturer;
	private boolean hotelAddition;
	private boolean companion;
	private Participant participant;
	private Conference conference;
	private Hotel hotel;

	/**
	 * Constructor for the class signup
	 * @param participant != null
	 * @param konference != null
	 * @param hotel
	 * @param lecturer
	 * @param arrivalDate
	 * @param departureDate
	 */
	public Signup(Participant participant, Conference conference, Hotel hotel, boolean lecturer, boolean hotelAddition, boolean companion) {
		this.lecturer = lecturer;
		this.hotelAddition = hotelAddition;
		this.companion = companion;
		this.hotel = hotel;
		setParticipant(participant);
		setConference(conference);
	}
	
	/**
	 * Getting the hotel
	 * @return Hotel
	 */
	public Hotel getHotel() {
		return hotel;
	}

	/**
	 * Getting the conference
	 * @return Conference
	 */
	public Conference getConference() {
		return conference;
	}

	/**
	 * Getting the participant
	 * @return Participant
	 */
	public Participant getParticipant() {
		return participant;
	}
	
	
	/**
	 * Getting is lecturer
	 * @return boolean
	 */
	public boolean isLecturer(){
		return lecturer;
	}
	
	/**
	 * Getting hotel addition
	 * @return boolean
	 */
	public boolean getHotelAddition(){
		return hotelAddition;
	}
	
	/**
	 * Getting got companion
	 * @return boolean
	 */
	public boolean getCompanion(){
		return companion;
	}
	
	/**
	 * Setting the hotel
	 * @param hotel != null
	 */
	public void setHotel(Hotel hotel) {
		if (this.hotel != hotel)
			this.hotel = hotel;
	}

	/**
	 * Setting the conference
	 * @param conference != null
	 */
	public void setConference(Conference conference) {
		if (this.conference != conference) {
			if (this.conference != null) {
				this.conference.removeSignup(this);
			}
			this.conference = conference;
			if (conference != null) {
				conference.addSignup(this);
			}
		}
	}

	/**
	 * Setting the participant
	 * @param participant != null
	 */
	public void setParticipant(Participant participant) {
		if (this.participant != participant) {
			this.participant = participant;
		}
	}
	
	/**
	 * Calculate the total price for the signup
	 * @return double
	 */
	public double calculatePrice(){
		double total = 0;
		if(hotel != null){ 
			if(companion){ 
				if(hotelAddition){ 
					total += (hotel.getPriceDoubleRoomPrNight() + hotel.getAddition());
				}else if(!hotelAddition){ 
					total += hotel.getPriceDoubleRoomPrNight();
				}
			}else if(!companion){ 
				if(hotelAddition){ 
					total += (hotel.getPriceSingleRoomPrNight() + hotel.getAddition());
				}else if(!hotelAddition){ 
					total += hotel.getPriceSingleRoomPrNight();
				}
			}
			total *=  conference.getConferenceDays()-1;
		}
		if(!isLecturer()){
			total += conference.getPrice();
		}
		total += participant.companionPrice();
		return total;
	}
	
	public String toString(){
		String st = "Konference: " + conference + ". Deltager: " + participant + ", ";
		if(companion){
			st += "";
		}
		st += hotel + ", ";
		return st;
	}

}