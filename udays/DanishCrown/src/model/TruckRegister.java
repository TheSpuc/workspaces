package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for modeling an arrival of truck and creating a registration
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="truckregisters")
public class TruckRegister {

	@Id
	@GeneratedValue
	@Column(name="TRUCKREGISTER_ID")
	private int id;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar arrivalTime;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar departureTime;
	
	private int restNeeded;
	private int arrivalWeight;
	private int departureWeight;
	
	@OneToOne
	@JoinColumn(name="TRUCK_ID")
	private Truck truck;
	
	@OneToMany
	@JoinColumn
	private List<PartialOrder> partialOrders;
	
	@OneToMany(mappedBy = "truckRegister")
	private List<Loading> loadings;

	
	/**
	 * JPA Constructor
	 */
	public TruckRegister(){}
	
	
	/**
	 * Constructor for the class TruckRegister
	 * @param truck - truck get the partialOrders
	 * @param partialOrders - List of orders for the truck to contain
	 * @param arrivalTime - arrival time of the truck
	 * @param restNeeded - rest needed for chauffeur
	 * @param arrivalWeight - arrival weight for when the truck arrives
	 */
	public TruckRegister(Truck truck, List<PartialOrder> partialOrders, Calendar arrivalTime, int restNeeded, int arrivalWeight){
		this.arrivalTime = arrivalTime;
		this.restNeeded = restNeeded;
		this.arrivalWeight = arrivalWeight;
		departureWeight = 0;
		this.truck = truck;
		this.partialOrders = new ArrayList<>(partialOrders);
		loadings = new ArrayList<>();
	}
	
	/**
	 * Method for getting the arrival time
	 * @return Calendar representing the arrival time
	 */
	public Calendar getArrivalTime(){
		return arrivalTime;
	}

	/**
	 * Method for setting the arrival time
	 * @param arrivalTime - Calendar for setting arrival time
	 */
	public void setArrivalTime(Calendar arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	/**
	 * Method for getting the departure time
	 * @return Calendar representing the departure time
	 */
	public Calendar getDepartureTime(){
		return departureTime;
	}
	
	/**
	 * Method for setting the departure time
	 * @param departureTime - Calendar for setting departure time
	 */
	public void setDepartureTime(Calendar departureTime){
		this.departureTime = departureTime;
	}

	/**
	 * Method for getting rest needed
	 * @return int representing the rest needed in minutes
	 */
	public int getRestNeeded(){
		return restNeeded;
	}
	
	/**
	 * Method for setting the rest needed
	 * @param restNeeded - integer for setting rest needed
	 */
	public void setRestNeeded(int restNeeded){
		this.restNeeded = restNeeded;
	}
	
	/**
	 * Method for getting the arrival weight
	 * @return int representing the arrival weight 
	 */
	public int getArrivalWeight(){
		return arrivalWeight;
	}
	
	/**
	 * Method for getting the departure weight
	 * @return int representing the departure weight
	 */
	public int getDepartureWeight(){
		return departureWeight;
	}
	
	/**
	 * Method for setting the departure weight
	 * @param departureWeight - integer for setting the departure weight
	 */
	public void setDepartureWeight(int departureWeight){
		this.departureWeight = departureWeight;
	}
	
	/**
	 * Method for getting the truck
	 * @return Truck
	 */
	public Truck getTruck(){
		return truck;
	}
	
	/**
	 * Method for getting all the partial orders
	 * @return List<PartialOrder> with all partial orders
	 */
	public List<PartialOrder> getPartialOrders(){
		return new ArrayList<>(partialOrders);
	}

	/**
	 * Method for getting all the loadings
	 * @return List<Loading> with all loadings
	 */
	public List<Loading> getLoadings(){
		return new ArrayList<>(loadings);
	}
	
	/**
	 * Method for adding a loading
	 * @param loading - loading to be added
	 */
	public void addLoading(Loading loading){
		if(!loadings.contains(loading)){
			loadings.add(loading);
		}
	}

}
