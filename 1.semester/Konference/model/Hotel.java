package model;

/**
 * Models a Hotel
 * @author Mark Medum Bundgaard
 *
 */
public class Hotel {
	private String name;
	private int priceSingleRoomPrNight;
	private int priceDoubleRoomPrNight;
	private int addition;
	private String additionDescription;

	/**
	 * Constructor for the class hotel
	 * @param name
	 * @param priceSingleRoomPrNight
	 * @param priceDoubleRoomPrNight
	 * @param addition
	 * @param additionDescription
	 */
	public Hotel(String name, int priceSingleRoomPrNight, int priceDoubleRoomPrNight, int addition, String additionDescription){
		this.name = name;
		this.priceSingleRoomPrNight = priceSingleRoomPrNight;
		this.priceDoubleRoomPrNight = priceDoubleRoomPrNight;
		this.addition = addition;
		this.additionDescription = additionDescription;
	}

	/**
	 * Getting single room price
	 * @return integer
	 */
	public int getPriceSingleRoomPrNight(){
		return priceSingleRoomPrNight;
	}
	
	/**
	 * Getting double room price
	 * @return integer
	 */
	public int getPriceDoubleRoomPrNight(){
		return priceDoubleRoomPrNight;
	}

	/**
	 * Getting the addition price
	 * @return integer
	 */
	public int getAddition(){
		return addition;
	}
	
	/**
	 * Getting the addition description
	 * @return String
	 */
	public String getAdditionDescription(){
		return additionDescription;
	}
	
	public String toString(){
		return name;
	}
}
