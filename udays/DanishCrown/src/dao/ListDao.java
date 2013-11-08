package dao;

import java.util.ArrayList;
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
public class ListDao implements Dao {

	private static ListDao uniqueInstance;

	private List<Truck> trucks = new ArrayList<>();
	private List<Ramp> ramps = new ArrayList<>();
	private List<TruckRegister> truckRegisters = new ArrayList<>();
	private List<PackageType> packageTypes = new ArrayList<>();
	private List<Order> orders = new ArrayList<>();
	private List<Loading> loadings = new ArrayList<>();

	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private ListDao(){
	}

	/**
	 * Static method for getting a ListDao object 
	 * and creating one if it dosen't exist
	 * @return Service object to be used
	 */
	public static ListDao getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new ListDao();
		}
		return uniqueInstance;
	}

	@Override
	public void addTruck(Truck t) {
		if(!trucks.contains(t)){
			trucks.add(t);
		}
	}

	@Override
	public void removeTruck(Truck t) {
		trucks.remove(t);
	}

	@Override
	public List<Truck> getTrucks() {
		return new ArrayList<>(trucks);
	}

	@Override
	public void addRamp(Ramp r) {
		if(!ramps.contains(r)){
			ramps.add(r);
		}
	}

	@Override
	public void removeRamp(Ramp r) {
		ramps.remove(r);
	}

	@Override
	public List<Ramp> getRamps() {
		return new ArrayList<>(ramps);
	}

	@Override
	public void addTruckRegister(TruckRegister tr) {
		if(!truckRegisters.contains(tr)){
			truckRegisters.add(tr);
		}
	}

	@Override
	public void removeTruckRegister(TruckRegister tr) {
		truckRegisters.remove(tr);
	}

	@Override
	public List<TruckRegister> getTruckRegisters() {
		return new ArrayList<>(truckRegisters);
	}

	@Override
	public void addOrder(Order o) {
		if(!orders.contains(o)){
			orders.add(o);
		}
	}

	@Override
	public void removeOrder(Order o) {
		orders.remove(o);
	}

	@Override
	public List<Order> getOrders() {
		return new ArrayList<>(orders);
	}

	@Override
	public void addLoading(Loading l) {
		if(!loadings.contains(l)){
			loadings.add(l);
		}
	}

	@Override
	public void removeLoading(Loading l) {
		loadings.remove(l);
	}

	@Override
	public List<Loading> getLoadings() {
		return new ArrayList<>(loadings);
	}

	@Override
	public void addPackageType(PackageType p) {
		if(!packageTypes.contains(p)){
			packageTypes.add(p);
		}
	}

	@Override
	public List<PackageType> getPackageTypes() {
		return new ArrayList<>(packageTypes);
	}
	
	@Override
	public void update(){}
}
