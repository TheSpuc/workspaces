package person;

import java.util.Date;

import specification.DateUtil;

/**
 *Models a Person, with a salary and company. 
 * @author MMB
 *
 */
public class Person
{
	private String navn;
	private String adresse;
	private int firma;
	private double maanedslon;
	private Date foedselsdag; 

	/**
	 * Initialize a Person
	 * Demand: maanedslon>=0 && firma >=0 
	 * @param navn
	 * @param adresse
	 * @param maanedslon
	 * @param firma
	 * @param aar
	 * @param maaned
	 * @param dag
	 */
	public Person(String navn, String adresse, double maanedslon, int firma, String foedselsdag)
	{
		this.navn = navn;
		this.adresse = adresse;
		this.maanedslon = maanedslon;
		this.firma = firma;
		this.foedselsdag = DateUtil.createDate(foedselsdag);
	}

	/**
	 * Registers the persons name
	 * @param navn
	 */
	public void setNavn(String navn){
		this.navn = navn;
	}

	/**
	 * Returns the name of the person
	 * @return name
	 */
	public String getNavn(){
		return navn;
	}

	/**
	 * Adds a new company to the person
	 * @param nytFirma >=1
	 */
	public void nytFirma(int nytFirma){
		firma += nytFirma;
	}

	/**
	 * Returns the number of companys
	 * @return company
	 */
	public int antalletAfFirmaer(){
		return firma;
	}

	/**
	 * Registers the persons address
	 * @param adresse
	 */
	public void setAdresse(String adresse){
		this.adresse = adresse;
	}

	/**
	 * Return the address
	 * @return address
	 */
	public String getAdresse(){
		return adresse;
	}

	/**
	 * Registers the monthly salary of the person
	 * @param maanedslon >=1
	 */
	public void setMaanedslon(double maanedslon){
		this.maanedslon = maanedslon;
	}

	/**
	 * Returns the monthly salary of the person
	 * @return maanedslon
	 */
	public double getMaanedslon(){
		return maanedslon;
	}

	/**
	 * Prints out the yearly salary of the person
	 */
	public void aarsLon(){
		double aarslon = (maanedslon*12) * 1.025;
		System.out.println("Aarsloen med feriepenge " + aarslon);
	}

	/**
	 * Registers the birthday of the person.
	 * @param aar
	 * @param maaned
	 * @param dag
	 */
	public void setFoedselsdag(String foedselsdag){
		this.foedselsdag =  DateUtil.createDate(foedselsdag);
	}


	/**
	 * Prints out the name, address and monthly salary
	 */
	public void printPerson(){
		System.out.println("Navn " + navn);
		System.out.println("Adresse " + adresse);
		System.out.println("Maanedslon " + maanedslon + " kr.");
	}

	/**
	 * Return a String representation for a person
	 */
	public String toString(){
		return navn + ", " + adresse + ", firma: " + firma + ", maanedslon: " + maanedslon + ", foedselsdag: " + DateUtil.dateToString(foedselsdag);  
	}
}
