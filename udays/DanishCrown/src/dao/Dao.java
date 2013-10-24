package dao;

import java.util.List;

import model.Loading;
import model.Order;
import model.PackageType;
import model.Ramp;
import model.Truck;
import model.TruckRegister;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public interface Dao {
	
	/**
	 * Method for adding a truck
	 * @param t - truck to be added
	 */
	public void addTruck(Truck t);
	
	/**
	 * Method for removing a truck
	 * @param t - truck to be removed
	 */
	public void removeTruck(Truck t);
	
	/**
	 * Method for getting all trucks
	 * @return List<Truck> containing all trucks
	 */
	public List<Truck> getTrucks();

	/**
	 * Method for adding a ramp
	 * @param r - ramp to be added
	 */
	public void addRamp(Ramp r);
	
	/**
	 * Method for removing a ramp
	 * @param r - ramp to be removed
	 */
	public void removeRamp(Ramp r);
	
	/**
	 * Method for getting all ramps
	 * @return List<Ramp> containing all ramps
	 */
	public List<Ramp> getRamps();
	
	/**
	 * Method for adding a truckRegister
	 * @param tr - truckRegister to be added
	 */
	public void addTruckRegister(TruckRegister tr);
	
	/**
	 * Method for removing a truckRegister
	 * @param tr - truckRegister to be removed
	 */
	public void removeTruckRegister(TruckRegister tr);
	
	/**
	 * Method for getting all truckRegisters
	 * @return List<TruckRegister> containing all truckRegisters
	 */
	public List<TruckRegister> getTruckRegisters();

	/**
	 * Method for adding a order
	 * @param o - order to be added
	 */
	public void addOrder(Order o);
	
	/**
	 * Method for removing a order
	 * @param o - order to be removed
	 */
	public void removeOrder(Order o);
	
	/**
	 * Method for getting all orders
	 * @return List<Order> containing all orders
	 */
	public List<Order> getOrders();
	
	/**
	 * Method for adding a loading
	 * @param l - loading to be added
	 */
	public void addLoading(Loading l);
	
	/**
	 * Method for removing a loading
	 * @param l - loading to be removed
	 */
	public void removeLoading(Loading l);
	
	/**
	 * Method for getting all loadings
	 * @return List<Loading> containing all loadings
	 */
	public List<Loading> getLoadings();
	
	/**
	 * Method for adding a packageType
	 * @param p - packageType to be added
	 */
	public void addPackageType(PackageType p);
	
	/**
	 * Method for getting all packageTypes
	 * @return List<PackageType> containing all packageTypes
	 */
	public List<PackageType> getPackageTypes();
	
	/**
	 * Method for updating the JPA information
	 * Minor hack, instead of persisting each object after change.
	 */
	public void update();
}
