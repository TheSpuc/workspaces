package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

/**
 * Class for modeling a truck
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="trucks")
public class Truck {
	
	@Id
	@GeneratedValue
	@Column(name="TRUCK_ID")
	private int id;
	
	private String regNo;
	private String chauffeurName;
	private String phone;
	
	@ManyToMany(mappedBy="trucks")
	private List<PackageType> packageTypes;
	
	//Max capacity for a trucks.
	public static final int TRUCKCAPACITY = 20000;

	/**
	 * JPA Constructor
	 */
	public Truck(){}
	
	/**
	 * Constructor for the class Truck
	 * @param regNo - registration number for the truck
	 * @param chauffeurName - name of the chauffeur
	 * @param phone - phone number of the chauffeur
	 * @param packagetypes != null - package types which the truck can contain
	 */
	public Truck(String regNo, String chauffeurName, String phone, PackageType[] packagetypes) {
		this.regNo = regNo;
		this.chauffeurName = chauffeurName;
		this.phone = phone;
		packageTypes = new ArrayList<>();
		
		//adding the package types to the truck
		for (PackageType packageType : packagetypes) {
			addPackageType(packageType);
		}
	}
	
	/**
	 * Method for getting the registration number
	 * @return String representing the registration number
	 */
	public String getRegNo() {
		return regNo;
	}
	
	/**
	 * Method for getting the chauffeur name
	 * @return String representing the chauffeur name
	 */
	public String getChauffeurName() {
		return chauffeurName;
	}
	
	/**
	 * Method for getting the phone number of the chauffeur
	 * @return String representing the phone number
	 */
	public String getPhone() {
		return phone;
	}

	/**
	 * Method for adding a packagetype
	 * @param packageType != null - packagetype to be added
	 */
	public void addPackageType(PackageType packageType) {
		if (!packageTypes.contains(packageType)) {
			packageTypes.add(packageType);
			packageType.addTruck(this);
		}
	}
	
	/**
	 * Method for removing a packagetype
	 * @param packageType - packageType to be removed
	 */
	public void removePackageType(PackageType packageType) {
		if (packageTypes.size() > 1) {
			if (packageTypes.contains(packageType)) {
				packageTypes.remove(packageType);
				packageType.removeTruck(this);
			}
		}
	}
	
	/**
	 * Method for getting all packagetypes
	 * @return List<PackageType> with all the package types of the truck
	 */
	public List<PackageType> getPackageTypes() {
		return new ArrayList<>(packageTypes);
	}
	
	/**
	 * Method for getting the truck capacity
	 * @return int representing the truck capacity
	 */
	public static int getTruckcapacity() {
		return TRUCKCAPACITY;
	}
	
	/**
	 * toString method for the class truck
	 */
	@Override
	public String toString(){
		return regNo;
	}
}
