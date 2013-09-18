package model;

/**
 * Models a product
 * @author Mark Medum Bundgaard
 *
 */

public class Produkt {
	
	private String navn;
	private double pris;
	
	/**
	 * Constructor of the class Produkt
	 * @param navn
	 * @param pris
	 */
	public Produkt(String navn, double pris){
		this.navn = navn;
		this.pris = pris;
	}
	
	/**
	 * Method for setting the name of this product
	 * @param navn
	 */
	public void setNavn(String navn){
		this.navn = navn;
	}
	
	/**
	 * Method for getting the name of this product
	 * @return
	 */
	public String getNavn(){
		return navn;
	}
	
	/**
	 * Method for setting the price of this product
	 * @param pris
	 */
	public void setPris(double pris){
		this.pris = pris;
	}
	
	/**
	 * Method for getting the price of this product
	 * @return
	 */
	public double getPris(){
		return pris;
	}
}
