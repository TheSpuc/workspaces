package service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import model.Loading;
import model.Order;
import model.PackageType;
import model.PartialOrder;
import model.Truck;
import model.TruckRegister;
import dao.Dao;
import dao.JpaDao;
import dao.ListDao;

public class ServiceGUI {
	
	private static ServiceGUI uniqueInstance;
	private Dao dao = JpaDao.getInstance();
	
	
	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private ServiceGUI(){}
	
	
	/**
	 * Static method for getting a ServiceGUI object 
	 * and creating one if it dosen't exist
	 * @return Service object to be used
	 */
	public static ServiceGUI getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new ServiceGUI();
		}
		return uniqueInstance;
	}
	
	
	/**
	 * Method for getting the trucks which is not linked to a truckRegister
	 * @return List<Truck> with all trucks which are not linked with a truckRegister
	 */
	public List<Truck> getTrucksWithoutTruckRegister() {
		List<Truck> result = dao.getTrucks();
		List<TruckRegister> truckRegisters = dao.getTruckRegisters();

		for(TruckRegister tr: truckRegisters) {
			Truck truck = tr.getTruck();	
			if (result.contains(truck)) {
				result.remove(truck);
			}
		}

		return result;
	}

	/**
	 * Method for getting all partialOrders with a specific packageType 
	 * which are not linked to a truckRegister
	 * @param packageType - packagetype that should be searched for
	 * @return List<PartialOrder> with all partialOrders with the same packageType
	 */
	public List<PartialOrder> getPartialOrdersWithoutTruckRegister(PackageType packageType) {
		List<Order> orders = dao.getOrders();
		List<PartialOrder> partialOrders = new ArrayList<>();
		
		for (Order order : orders) {
			if (order.getPackageType().equals(packageType)) {
				partialOrders.addAll(order.getPartialOrders());
			}
		}
		
		List<TruckRegister> truckRegisters = dao.getTruckRegisters();
		List<PartialOrder> registeredPartialOrders = new ArrayList<>();

		for (TruckRegister truckRegister : truckRegisters) {
			List<PartialOrder> pos = truckRegister.getPartialOrders();
			if (pos.get(0).getPackageType().equals(packageType)) {
				registeredPartialOrders.addAll(pos);
			}
		}
		
		partialOrders.removeAll(registeredPartialOrders);
		return partialOrders;
	}
	
	/**
	 * Method for checking if the truck can contain 
	 * the weight of the chosen partialOrder/partialOrders
	 * @param t - truck that should contain the order
	 * @param partialOrders - List of partialOrders the truck should contain
	 * @return boolean representing if the truck can contain the partialorders
	 */
	public boolean checkTrucksPartialOrders(Truck t, List<PartialOrder> partialOrders){
		boolean result = false;
		int totalWeight = 0;
		for (PartialOrder p : partialOrders){
			totalWeight += p.getWeight();
		}
		if (totalWeight <= Truck.getTruckcapacity()){
			result = true;
		}
		return result;
	}

	/**
	 * Method for creating a String representation of the partialOrders 
	 * of a specific loading
	 * @param loading - loading to get the paritalOrder information from
	 * @return String with the information to be shown
	 */
	public String printPartialOrder(Loading loading) {
		List<PartialOrder> partialOrders =  loading.getTruckRegister().getPartialOrders();
		String result = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy");

		for (PartialOrder po: partialOrders) {
			result += " Partial order nr: " + po.getPartialOrderNumber() + 
					"\n Weight: " + po.getWeight() + " kg\n Margin: " + po.getMargin() + "kg\n Date: " + 
					dateFormat.format(po.getDate().getTime()) + "\n\n";
		}
		return result;
	}

	/**
	 * Method for creating a String representation of the truckRegister
	 * of a specific loading
	 * @param loading - loading to get the truckRegister information from
	 * @return String with the information to be shown
	 */
	public String printTruckRegister(Loading loading) {
		TruckRegister tr = loading.getTruckRegister();
		String result = "";

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

		result += " Arrival time: " + dateFormat.format(tr.getArrivalTime().getTime()) + 
				"\n Rest needed: " + tr.getRestNeeded() + " minutes\n Start weight: " + 
				tr.getArrivalWeight() + " kg";

		return result;
	}

	/**
	 * Method for creating a String representation of the truck
	 * of a specific loading
	 * @param loading - loading to get the truck information from
	 * @return String with the information to be shown
	 */
	public String printTruck(Loading loading) {
		Truck t = loading.getTruckRegister().getTruck();
		String result = "";

		result += " Registration number: " + t.getRegNo() + "\n Chauffeur: " + 
				t.getChauffeurName() + "\n Phone: " + t.getPhone();

		return result;
	}

	/**
	 * Method for creating a String representation of partialOrder information
	 * for a specific truckRegister
	 * @param truckRegister - truckRegister to get the information of the partialOrders from
	 * @return String with the information to be shown
	 */
	public String printNotify(TruckRegister truckRegister){
		String result = "";
		List<PartialOrder> partialOrders = truckRegister.getPartialOrders();

		SimpleDateFormat dateFormat = new SimpleDateFormat("MM-dd-yyyy HH:mm");

		for (PartialOrder po: partialOrders) {
			result += " Partial order nr: " + po.getPartialOrderNumber() + 
					"\n Weight: " + po.getWeight() + " kg\n Date: " + 
					dateFormat.format(po.getDate().getTime()) + "\n\n";
		}

		result += " Your order is ready to be picked up!";
		return result;
	}
}
