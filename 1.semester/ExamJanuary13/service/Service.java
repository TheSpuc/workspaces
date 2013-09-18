package service;

import java.util.ArrayList;
import java.util.Date;

import specification.DateUtil;

import dao.Dao;
import model.Bestilling;
import model.Karakter;
import model.Kunde;
import model.Produkt;
import model.Restaurant;

/**
 * Service class for SK January 2013
 * @author Mark Medum Bundgaard
 *
 */

public class Service {

	
	/**
	 * Method for creating a restaurant
	 * @param navn
	 * @param stjerner
	 * @return
	 */
	public static Restaurant createRestaurant(String navn, Karakter stjerner){
		Restaurant restaurant = new Restaurant(navn, stjerner);
		Dao.addRestaurant(restaurant);
		return restaurant;
	}
	
	/**
	 * Method for updating simple attributes of a restaurant
	 * @param restaurant
	 * @param navn
	 * @param stjerner
	 */
	public static void updateRestaurant(Restaurant restaurant, String navn, Karakter stjerner){
		restaurant.setNavn(navn);
		restaurant.setStjerner(stjerner);
	}
	
	/**
	 * Method for creating a product
	 * @param navn
	 * @param pris
	 * @return
	 */
	public static Produkt createProdukt(String navn, double pris){
		Produkt produkt = new Produkt(navn, pris);
		Dao.addProdukt(produkt);
		return produkt;
	}
	
	/**
	 * Method for creating a customer
	 * @param navn
	 * @param adresse
	 * @param rabat
	 * @return
	 */
	public static Kunde createKunde(String navn, String adresse, boolean rabat){
		Kunde kunde = new Kunde(navn, adresse, rabat);
		Dao.addKunde(kunde);
		return kunde;
	}
	
	/**
	 * Method for creating a order for a restaurant
	 * @param restaurant
	 * @param leveringsTid
	 * @param kunde
	 * @return
	 */
	public static Bestilling createBestilling(Restaurant restaurant, Date leveringsTid, Kunde kunde){
		Bestilling bestilling = restaurant.createBestilling(leveringsTid, kunde);
		return bestilling;
	}
	
	/**
	 * Method for getting all restaurants
	 * @return
	 */
	public static ArrayList<Restaurant> getRestauranter(){
		return Dao.getRestauranter();
	}
	
	/**
	 * Method for getting all customers
	 * @return
	 */
	public static ArrayList<Kunde> getKunder(){
		return Dao.getKunder();
	}
	
	/**
	 * Method for getting all products
	 * @return
	 */
	public static ArrayList<Produkt> getProdukter(){
		return Dao.getProdukter();
	}
	
	/**
	 * Method for finding what orders the restaurant have on a specific date
	 * @param restaurant
	 * @param date
	 * @return
	 */
	public static ArrayList<String> getTransportForRestaurant(Restaurant restaurant, Date date){
		return restaurant.transportseddel(date);
	}
	
	/**
	 * Method for returning only five star restaurants
	 * @return
	 */
	public static ArrayList<Restaurant> femStjernerRestauranter(){
		ArrayList<Restaurant> resultList = new ArrayList<>();
		ArrayList<Restaurant> runList = Dao.getRestauranter();
		for(Restaurant r : runList){
			if(r.getStjerner().equals(Karakter.FEM)){
				resultList.add(r);
			}
		}
		if(resultList.isEmpty()){
			throw new RuntimeException("Ingen 5 stjernede restauranter");
		}
		return resultList;
	}
	
	
	/**
	 * Insertion sort because we know that the problem is small,
	 * if the problem would be bigger selectionsort would give us 
	 * better runtime and faster sorting. n^2 swap in worst case
	 * @return
	 */
	public static ArrayList<Restaurant> sortRestauranter(){
		ArrayList<Restaurant> runList = Dao.getRestauranter();
		for(int i = 1; i<runList.size(); i++){
			Restaurant key = runList.get(i);
			int position = i;
			while(position > 0 && runList.get(position-1).compareTo(key) > 0){
				runList.set(position, runList.get(i-1));
				position--;
			}
			runList.set(position, key);
		}
		return runList;
	}
	
	/**
	 * If the problem becomes very big or we dont know if the list is nearly sorted 
	 * then we use selection sort, for less swaps means a faster sorting
	 * n swaps in worst case
	 * @return
	 */
	public static ArrayList<Restaurant> sortSelection(){
		ArrayList<Restaurant> runList = Service.getRestauranter();
		for(int i=0; i<runList.size()-1; i++){
			int min = i;
			for(int j = i+1; j<runList.size(); j++){
				if(runList.get(j).compareTo(runList.get(min)) < 0){
					min = j;
				}
			}
			Restaurant temp = runList.get(min);
			runList.set(min, runList.get(i));
			runList.set(i, temp);
		}
		return runList;
	}
	
	
	/**
	 * Creating of some objects the fill out the gui
	 */
	public static void createSomeObjects(){
		//Kunder:
		Kunde k1 = createKunde("Hans Hansen", "Skoldegade 18", false);
		Kunde k2 = createKunde("Jane Jensen", "Algade 7", true);
		
		//Restauranter && bestillinger:
		Restaurant p = createRestaurant("Pizza Hut", Karakter.TRE);
		createBestilling(p, DateUtil.createDate(2013, 01, 17), k1);
		createBestilling(p, DateUtil.createDate(2013, 01, 16), k2);
		
		createRestaurant("Kebab House", Karakter.FEM);
		createRestaurant("China House", Karakter.FIRE);
		
		//Produkter:
		createProdukt("Pizza", 54);
		createProdukt("Kebab", 37.5);
		
		
	}
}
