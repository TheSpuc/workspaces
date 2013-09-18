package model;

import java.util.ArrayList;
import java.util.Date;

/**
 * Models a order
 * @author Mark Medum Bundgaard
 *
 */

public class Bestilling {
	
	
	private Date leveringsTid;
	private ArrayList<Produkt> produkter;
	private Kunde kunde;
	
	/**
	 * Constructor for the class Bestilling
	 * @param leveringsTid
	 * @param kunde != null
	 */
	public Bestilling(Date leveringsTid, Kunde kunde){
		setKunde(kunde);
		this.leveringsTid = leveringsTid;
		produkter = new ArrayList<>();
	}
	
	/**
	 * Method for setting the customer of this order
	 * @param kunde
	 */
	public void setKunde(Kunde kunde){
		if(this.kunde != kunde){
			if(this.kunde != null){
				this.kunde.removeBestilling(this);
			}
		}
		this.kunde = kunde;
		if(kunde != null){
			kunde.addBestilling(this);
		}
	}
	
	/**
	 * Method for getting the customer
	 * @return
	 */
	public Kunde getKunde(){
		return kunde;
	}
	
	/**
	 * Method for setting the delivery date
	 * @param leveringsTid
	 */
	public void setLeveringsTid(Date leveringsTid){
		this.leveringsTid = leveringsTid;
	}
	
	/**
	 * Method for getting the delivery date
	 * @return
	 */
	public Date getLeveringsTid(){
		return leveringsTid;
	}
	
	
	/**
	 * Method for adding a product
	 * @param produkt
	 */
	public void addProdukt(Produkt produkt){
			produkter.add(produkt);
	}
	
	/**
	 * Method for removing a product
	 * @param produkt
	 */
	public void removeProdukt(Produkt produkt){
		if(produkter.contains(produkt)){
			produkter.remove(produkt);
		}
	}
	
	/**
	 * Method for getting all the products
	 * @return
	 */
	public ArrayList<Produkt> getProdukter(){
		return new ArrayList<>(produkter);
	}
	
	/**
	 * Method for getting the price of this order
	 * @return
	 */
	public double pris(){
		double result = 0;
		for(Produkt p : produkter){
			result += p.getPris();
		}
		if(kunde.getRabat()){
			result *= 0.85;
		}
		return result;
	}
}
