package model;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Class for modeling a partial order
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
@Table(name="partialorders")
public class PartialOrder {

	@Id
	@GeneratedValue
	@Column(name="PARTIALORDER_ID")
	private int id;
	
	private int weight;
	
	@ManyToOne
	private Order order;
	
	private String partialOrderNumber;
	
	/**
	 * JPA Constructor
	 */
	PartialOrder(){}
	
	/**
	 * Constructor of the class PartialOrder
	 * which is package private, because order is doing the creation
	 * @param weight - weight of the partial order
	 * @param date - date of the order
	 * @param packageType - package type of the order
	 * @param margin - margin for the partial order
	 * @param partialOrderNumber - order number of the partial order
	 */
	PartialOrder(Order order, int weight, String partialOrderNumber) {
		this.order = order;
		this.weight = weight;
		this.partialOrderNumber = partialOrderNumber;
	}
	
	/**
	 * Method for getting the order this partialOrder belongs to.
	 * @return Order which partialOrder belongs to.
	 */
	public Order getOrder(){
		return order;
	}
	
	/**
	 * Method for calculating the loadingTime in minutes
	 * @return long representing seconds it takes to load a partial with the specified weight
	 */
	public long getLoadingTime(){
		return TimeUnit.SECONDS.toMinutes((weight*getPackageType().getLoadingTime()/300));
	}
	
	/**
	 * Method for getting the weight
	 * @return int representing the weight
	 */
	public int getWeight(){
		return weight;
	}
	
	/**
	 * Method for getting the date
	 * @return Calendar representing the date
	 */
	public Calendar getDate(){
		return order.getDate();
	}
	
	/**
	 * Method for getting the package type
	 * @return PackageType
	 */
	public PackageType getPackageType(){
		return order.getPackageType();
	}
	
	/**
	 * Method for getting the partial order number
	 * @return String with the order number of the partial order
	 */
	public String getPartialOrderNumber(){
		return partialOrderNumber;
	}
	
	/**
	 * Method for getting the margin
	 * @return int representing the margin
	 */
	public int getMargin(){
		return order.getMargin();
	}

	/**
	 * toString method for the class PartialOrder
	 */
	@Override
	public String toString(){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");
		return "No: " + partialOrderNumber + ", " + dateFormat.format(order.getDate().getTime()) + ", " + weight + " kg";
	}
}
