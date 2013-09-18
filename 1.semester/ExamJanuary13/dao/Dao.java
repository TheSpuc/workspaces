package dao;

import java.util.ArrayList;

import model.Kunde;
import model.Produkt;
import model.Restaurant;

/**
 * Dao class for SK January 2013
 * @author Mark Medum Bundgaard
 *
 */

public class Dao {
	
	private static ArrayList<Restaurant> restauranter = new ArrayList<>();
	private static ArrayList<Produkt> produkter = new ArrayList<>();
	private static ArrayList<Kunde> kunder = new ArrayList<>();
	
	/**
	 * Method for adding a restaurant
	 * @param restaurant
	 */
	public static void addRestaurant(Restaurant restaurant){
		if(!restauranter.contains(restaurant)){
			restauranter.add(restaurant);
		}
	}
	
	/**
	 * Method for getting all the restaurants
	 * @return
	 */
	public static ArrayList<Restaurant> getRestauranter(){
		return new ArrayList<>(restauranter);
	}
	
	
	/**
	 * Method for adding a product
	 * @param produkt
	 */
	public static void addProdukt(Produkt produkt){
		if(!produkter.contains(produkt)){
			produkter.add(produkt);
		}
	}
	
	
	/**
	 * Method for getting all the products
	 * @return
	 */
	public static ArrayList<Produkt> getProdukter(){
		return new ArrayList<>(produkter);
	}
	
	
	/**
	 * Method for adding a customer
	 * @param kunde
	 */
	public static void addKunde(Kunde kunde){
		if(!kunder.contains(kunde)){
			kunder.add(kunde);
		}
	}
	
	/**
	 * Method for getting all the customers
	 * @return
	 */
	public static ArrayList<Kunde> getKunder(){
		return new ArrayList<>(kunder);
	}
}
