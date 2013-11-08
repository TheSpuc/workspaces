package model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Class for modeling an order
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="orders")
public class Order {

	@Id
	@GeneratedValue
	@Column(name="ORDER_ID")
	private int id;
	
	private int number;
	private int weight;
	private int margin;
	
	@Temporal(TemporalType.TIMESTAMP)
	private Calendar date;
	
	@ManyToOne
	private PackageType packageType;
	
	@OneToMany(mappedBy="order", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	@JoinColumn
	private List<PartialOrder> partialOrders;

	
	/**
	 * JPA Constructor
	 */
	public Order(){}
	
	
	/**
	 * Constructor for the class Order
	 * @param number - order number
	 * @param weight - total weight
	 * @param date - date when the order is being placed
	 * @param margin - margin of the order
	 * @param packageType - packageType of the order
	 */
	public Order(int number, int weight, int margin, Calendar date, PackageType packageType) {
		this.number = number;
		this.weight = weight;
		this.margin = margin;
		this.date = date;
		this.packageType = packageType;
		partialOrders = new ArrayList<>();
		//Making the call for creating partialOrders
		createPartialOrders();
	}

	/**
	 * Method for creating partial orders, based from the
	 * max truck capacity. The method splits each order up in parts
	 * of the max truck capacity and thereby creating a specific
	 * amount of partialOrders based of the order weight.
	 * Each partialOrder is being created with the calculated weight and
	 * same date, packageType, margin as the order object.
	 * The number is making a String representation of the order number, 
	 * concatenating the index of the while loop as the last part.
	 */
	private void createPartialOrders(){
		int weight = this.weight;
		int index = 1;
		while(weight > 0){
			if(weight <= Truck.TRUCKCAPACITY){
				PartialOrder p = new PartialOrder(this, weight,  number+"-"+index);
				partialOrders.add(p);
				weight = 0;
			}else if( weight > Truck.TRUCKCAPACITY){
				PartialOrder p = new PartialOrder(this, Truck.TRUCKCAPACITY, number+"-"+index);
				partialOrders.add(p);
				weight -= Truck.TRUCKCAPACITY;
			}
			index++;
		}
	}

	/**
	 * Method for getting all partialOrders
	 * @return List<PartialOrder> with all the partialOrders
	 */
	public List<PartialOrder> getPartialOrders(){
		return new ArrayList<>(partialOrders);
	}

	/**
	 * Method for getting the number
	 * @return int representing the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Method for getting the weight
	 * @return int representing the weight
	 */
	public int getWeight() {
		return weight;
	}
	
	/**
	 * Method for getting the margin 
	 * @return int representing the margin
	 */
	public int getMargin(){
		return margin;
	}

	/**
	 * Method for getting the date
	 * @return Calendar representing the date 
	 */
	public Calendar getDate() {
		return date;
	}

	/**
	 * Method for getting the packageType
	 * @return PackageType of the order
	 */
	public PackageType getPackageType() {
		return packageType;
	}
}
