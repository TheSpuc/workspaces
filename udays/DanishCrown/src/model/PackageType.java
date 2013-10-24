package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Abstract class to use to contain the same basic information 
 * and methods that stays the same for all subclasses
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="packagetypes")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class PackageType {
	
	@Id
	@GeneratedValue
	@Column(name="PACKAGETYPE_ID")
	private int id;
	
	private int loadingTime;
	
	@ManyToMany
	private List<Truck> trucks;
	
	@OneToMany(mappedBy = "packageType")
	private List<Ramp> ramps;
	
	
	/**
	 * JPA Constructor
	 */
	public PackageType(){}
	
	/**
	 * Constructor of the abstract class PackageType
	 * @param time - int representing the loading time
	 */
	public PackageType(int time){
		loadingTime = time;
		trucks = new ArrayList<>();
		ramps = new ArrayList<>();
	}
	
	/**
	 * Method for getting the loading time
	 * @return int representing the loading time
	 */
	public int getLoadingTime() {
		return loadingTime;
	}
	
	/**
	 * Method for setting the loading
	 * @param time - integer representing the loading time
	 */
	public void setLoadingTime(int time){
		loadingTime = time;
	}
	
	/**
	 * Method for adding a truck
	 * @param t - truck to be added
	 */
	public void addTruck(Truck t){
		if(!trucks.contains(t)){
			trucks.add(t);
			t.addPackageType(this);
		}
	}
	
	/**
	 * Method for removing a truck
	 * @param t - truck to be removed
	 */
	public void removeTruck(Truck t){
		if(trucks.contains(t)){
			trucks.add(t);
			t.removePackageType(this);
		}
	}
	
	/**
	 * Method for getting all the trucks
	 * @return List<Truck> with all trucks
	 */
	public List<Truck> getTrucks(){
		return new ArrayList<>(trucks);
	}
	
	/**
	 * Method for adding a ramp
	 * @param r - ramp to be added
	 */
	void addRamp(Ramp r){
		if(!ramps.contains(r)){
			ramps.add(r);
		}
	}
	
	/**
	 * Method for removing a ramp
	 * @param r - ramp to be removed
	 */
	void removeRamp(Ramp r){
		if(ramps.contains(r)){
			ramps.remove(r);
		}
	}
	
	/**
	 * Method for getting all the ramps
	 * @return List<Ramp> with all the ramps
	 */
	public List<Ramp> getRamps(){
		return new ArrayList<>(ramps);
	}
	
	/**
	 * toString method for the class PackageType
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName();
	}
}