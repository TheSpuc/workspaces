package model;

/**
 * Models a Participant
 * @author Mark Medum Bundgaard
 *
 */
public class Participant {

	private String name;
	private String address;
	private String city;
	private String country;
	private String phone;
	private int participantNumber;
	private String companyName;
	private String companyPhone;
	private Companion companion;

	/**
	 * Constructor for the class participant
	 * @param name
	 * @param address
	 * @param city
	 * @param country
	 * @param phone
	 * @param companyName
	 * @param companyPhone
	 */
	public Participant(String name, String address, String city, String country, String phone, String companyName, String companyPhone) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.country = country;
		this.phone = phone;
		this.participantNumber = (int)((1000*Math.random())+1);
		this.companyName = companyName;
		this.companyPhone = companyPhone;
	}

	/**
	 * Set the companion
	 * @param companion != null
	 */
	public void setCompanion(Companion companion){
		if(this.companion != companion){
			this.companion = companion;
		}
	}

	/**
	 * Create a companion with a name
	 * @param name length > 0 
	 * @return Companion
	 */
	public Companion createCompanion(String name){
		Companion companion = new Companion(name, this.name, this.participantNumber);
		this.setCompanion(companion);
		return companion;
	}

	/**
	 * Getting the price for all trips of the companion
	 * @return integer
	 */
	public int companionPrice(){
		int total = 0;
		if(companion != null){
			total = companion.calculateTripsPrice();
		}
		return total;
	}

	/**
	 * Getting the companion
	 * @return Companion
	 */
	public Companion getCompanion(){
		return companion;
	}

	public String toString(){
		String s = name + ", participant number: " + participantNumber;
		if(companion != null){
			s += " " + companion;
		}
		return s;
	}
}