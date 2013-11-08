package service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import model.CardboardBox;
import model.Loading;
import model.Order;
import model.PackageType;
import model.PartialOrder;
import model.PlasticBox;
import model.Ramp;
import model.State;
import model.Truck;
import model.TruckRegister;
import model.XmasTree;
import dao.Dao;
import dao.JpaDao;
import dao.ListDao;

/**
 * 
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
public class Service {

	private static Service uniqueInstance;
	private Dao dao = JpaDao.getInstance();

	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private Service() {}

	/**
	 * Static method for getting a Service object 
	 * and creating one if it dosen't exist
	 * @return Service object to be used
	 */
	public static Service getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new Service();
		}
		return uniqueInstance;
	}

	/**
	 * Method for creating a Truck
	 * @param regNo - registration number
	 * @param chauffeurName - name of the chauffeur
	 * @param phone - number of the chauffeur
	 * @param packagetypes - packagetypes the truck should be able to contain
	 * @return Truck with the information
	 */
	public Truck createTruck(String regNo, String chauffeurName, String phone, PackageType[] packagetypes){
		Truck t = new Truck(regNo, chauffeurName, phone, packagetypes);
		dao.addTruck(t);
		return t;
	}

	/**
	 * Method for deleting a truck
	 * @param t - truck to be deleted
	 */
	public void deleteTruck(Truck t){
		dao.removeTruck(t);
	}

	/**
	 * Method for getting a List with all trucks
	 * @return List<Truck> with all trucks
	 */
	public List<Truck> getTrucks(){
		return dao.getTrucks();
	}

	/**
	 * Method for creating a Order
	 * @param number - order number
	 * @param weight - total weight
	 * @param date - date when the order is being placed
	 * @param margin - margin of the order
	 * @param packageType - packagetype of the order
	 * @return Order with the information
	 */
	public Order createOrder(int number, int weight, Calendar date, int margin, PackageType packageType){
			Order o = new Order(number, weight, margin, date, packageType);
			dao.addOrder(o);
			return o;
	}
	
	/**
	 * Method for checking if there already exists a order 
	 * with the inputted number
	 * @param number - int to check for 
	 * @return boolean true if the order number dosen't exist, false otherwise.
	 */
	public boolean checkOrderNumber(int number){
		List<Order> orders = getOrders();
		boolean searching = true;
		int index = 0;
		while(index < orders.size() && searching){
			if(orders.get(index).getNumber() == number){
				searching = false;
			}index++;
		}
		return searching;
	}

	/**
	 * Method for deleting a order
	 * @param o - order to be deleted
	 */
	public void deleteOrder(Order o){
		dao.removeOrder(o);
	}

	/**
	 * Method for getting all the orders
	 * @return List<Order> with all orders
	 */
	public List<Order> getOrders(){
		return dao.getOrders();
	}

	/**
	 * Method for registering an arrival of a truck
	 * and creating a truckRegister with the information and
	 * thereby creating a loading from that specific truckRegister
	 * @param truck - truck
	 * @param partialOrders - List with all the partialOrders the truck should contain
	 * @param restNeeded - rest needed of the chauffeur
	 * @param arrivalWeight - weight of the truck
	 * @return TruckRegister with the information
	 */
	public TruckRegister registerArrival(Truck truck, List<PartialOrder> partialOrders, int restNeeded, int arrivalWeight){
		TruckRegister tr = new TruckRegister(truck, partialOrders, new GregorianCalendar(), restNeeded, arrivalWeight);
		//Creating the specific loading for the newly created truckRegister tr
		dao.addTruckRegister(tr);
		createLoading(tr);
		return tr;
	}

	/**
	 * Method for searching for the best ramp for a loading without priority
	 * @param truckRegister - For getting the information about the time and packagetype
	 */
	private void createLoading(TruckRegister truckRegister){ 

		//getting all the partialOrders
		List<PartialOrder> currentOrders = truckRegister.getPartialOrders(); 

		//for holding the calculated loading time for all the orders
		int loadingTime = 0; 

		//getting the loading Time for each partialOrder placed in the truck
		for(PartialOrder po : currentOrders){ 
			//adding each specific loading time, by calling a helping method placed in PartialOrder po.
			loadingTime += po.getLoadingTime(); 
		}

		//specific packagetype for the orders
		PackageType currentPackageType = currentOrders.get(0).getPackageType();
		//All ramps with the same packagetype as the orders.
		List<Ramp> ramps = currentPackageType.getRamps(); 


		//Start on search for the best spot on a specific ramp
		//pickedRamp to hold the ramp the loading should be added to when the search is finished
		Ramp pickedRamp = null; 
		//for comparing the best queue time in minutes
		int bestTimeInMin = Integer.MAX_VALUE; 

		//if we find a empty slot, the search should terminate, because its possible to start loading right away
		boolean foundEmptySlot = false;
		//index for making it possible to go through all out ramps.
		int index = 0; 

		//Search
		//Keep searching while there is more ramps in the List and if we haven't found a empty ramp
		while(index < ramps.size() && !foundEmptySlot){
			//the specific ramp we looking on right now
			Ramp currentRamp = ramps.get(index);
			//Temporary loading time for the ramp we are looking on right now
			int queueDoneInMin = 0;  

			//Making the specific comparison whether or not the ramp is better
			//call for helping method placed on the ramp, which calculates the minutes it takes for the queue is done
			queueDoneInMin += getFinishWithQueue(currentRamp);
			//the ramp is empty, then it must be the best ramp.
			if(queueDoneInMin == 0){ 
				bestTimeInMin = queueDoneInMin;
				foundEmptySlot = true;
				pickedRamp = currentRamp;
			}else if(queueDoneInMin < bestTimeInMin){ //if the queueDoneInMin is better than the best time we are holding at the moment, we overwrite the information with the better one.
				bestTimeInMin = queueDoneInMin;
				pickedRamp = currentRamp;
			}
			//adding one to the index, for looking at the next ramp
			index++; 
		}

		//making certain that we found a ramp.
		if(pickedRamp != null){ 
			//getting the current time for comparison.
			Calendar currentTime= new GregorianCalendar(); 
			//adding the time it takes for the best queue time.
			currentTime.add(Calendar.MINUTE, bestTimeInMin); 
			//cloning the current time, and saving it for getting the estimated finish time for the new loading
			Calendar estimatedFinish = (GregorianCalendar) currentTime.clone();
			//adding the loading time for the all the orders
			estimatedFinish.add(Calendar.MINUTE, loadingTime); 
			//creating the loading, and thereby adding it to the picked ramp with the estimated start and finish
			Loading l = new Loading(currentTime, estimatedFinish, truckRegister, pickedRamp);
			if(foundEmptySlot){
				l.setState(State.BEINGLOADED);
			}
			//adding it to dao.
			dao.addLoading(l); 
		}else {
			//if the ramp is null, we throw an exception for terminating the method
			throw new RuntimeException("The specific loading cant not be created"); 
		}
	}


	/**
	 * Method for getting the time, for a specific ramp 
	 * to be done with its queue 
	 * @return long - minutes till done with whole queue
	 */
	private long getFinishWithQueue(Ramp ramp){
		long time = -1;
		Calendar currentTime = new GregorianCalendar();
		List<Loading> loadings = ramp.getLoadingsInQueue();
		if(loadings.isEmpty()){
			time = 0;
		}else {
			Loading l = loadings.get(loadings.size()-1); 

			long start = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTimeInMillis());
			long finish = TimeUnit.MILLISECONDS.toMinutes(l.getEstimatedFinish().getTimeInMillis());

			time = finish-start; 
		}
		return time;
	}

	/**
	 * Method for registering a departure, and checking the final
	 * weight of that specific departure
	 * @param truckRegister - the truckregister which is ready for departure
	 * @param loading - the loading being loaded right now
	 * @param ramp - the ramp which is done with the loading
	 */
	public boolean registerDeparture(TruckRegister truckRegister, Loading loading, Ramp ramp){
		//Setting the state on the loading, and calling a helping method for finding the next loading
		boolean result = true;
		nextLoading(loading, ramp);

		List<PartialOrder> partialOrders = truckRegister.getPartialOrders();

		int orderWeight = 0;
		int margin = 0;
		for(PartialOrder po : partialOrders){
			orderWeight += po.getWeight();
			margin += po.getMargin();
		}

		int checkWeight = truckRegister.getDepartureWeight()-truckRegister.getArrivalWeight();


		if(checkWeight <= orderWeight+margin && checkWeight >= orderWeight-margin){
			List<Loading> loadings = truckRegister.getLoadings();
			Loading l = loadings.get(loadings.size()-1);
			l.setControlWeightSuccess(true);

			truckRegister.setDepartureTime(new GregorianCalendar());
			dao.update();
		}else {
			createPriorityLoading(truckRegister);
			result = false;
		}
		return result;
	}

	public void setDepartureWeight(TruckRegister truckRegister, int depatureWeight){
		truckRegister.setDepartureWeight(depatureWeight);
	}

	/**
	 * Method for setting the current loading to done
	 * and changing state for the next loading in queue
	 * to beingloaded. Hereby using that we always have a 
	 * sorted list of loadings.
	 * @param loading - specific loading that is finished
	 */
	private void nextLoading(Loading loading, Ramp ramp){
		loading.setState(State.DONE);
		List<Loading> tempLoadings = ramp.getLoadingsInQueue();
		if(!tempLoadings.isEmpty()){
			tempLoadings.get(0).setState(State.BEINGLOADED);
		}
		dao.update();
	}

	/**
	 * Method for searching for the best ramp for a loading with priority 
	 * @param truckRegister - the truckregister that should be created a loading with priority from
	 */
	private void createPriorityLoading(TruckRegister truckRegister){
		List<PartialOrder> currentOrders = truckRegister.getPartialOrders(); //getting all the partialOrders

		//for holding the calculated loading time for the order
		int loadingTime = 0; 

		//getting the loading Time for each partialOrder placed in the truck
		for(PartialOrder po : currentOrders){ 
			//adding each specific loading time, by calling a helping method placed in PartialOrder po.
			loadingTime += po.getLoadingTime(); 
		}

		//specific packagetype for the orders
		PackageType currentPackageType = currentOrders.get(0).getPackageType(); 
		//All ramps with the same packagetype as the orders.
		List<Ramp> ramps = currentPackageType.getRamps(); 


		//Start on search for the best spot on a specific ramp
		//pickedRamp to hold the ramp the loading should be added to when the search is finished
		Ramp pickedRamp = null; 
		//for comparing the best queue time in minutes
		int bestTimeInMin = Integer.MAX_VALUE; 

		//if we find a empty slot, the search should terminate, because its possible to start loading right away
		boolean foundEmptySlot = false; 
		//index for making it possible to go through all out ramps.
		int index = 0; 

		//Search
		//Keep searching while there is more ramps in the List and if we haven't found a empty ramp
		while(index < ramps.size() && !foundEmptySlot){
			//the specific ramp we looking on right now
			Ramp currentRamp = ramps.get(index); 
			//Temporary loading time for the ramp we are looking on right now
			int queueDoneInMin = 0;  

			queueDoneInMin += getFinishWithPriorityLoadings(currentRamp);
			//Making the specific comparison whether or not the ramp is better
			//call for helping method placed on the ramp, which calculates the minutes it takes for the queue is done
			//the ramp is empty, then it must be the best ramp.
			if(queueDoneInMin == 0){ 
				bestTimeInMin = queueDoneInMin;
				foundEmptySlot = true;
				pickedRamp = currentRamp;
			}else if(queueDoneInMin < bestTimeInMin){ //if the queueDoneInMin is better than the best time we are holding at the moment, we overwrite the information with the better one.
				bestTimeInMin = queueDoneInMin;
				pickedRamp = currentRamp;
			}
			//adding one to the index, for looking at the next ramp
			index++; 
		}

		//making certain that we found a ramp.
		if(pickedRamp != null){ 
			//getting the current time for comparison.
			Calendar currentTime= new GregorianCalendar(); 
			//adding the time it takes for the best queue time.
			currentTime.add(Calendar.MINUTE, bestTimeInMin); 

			//cloning the current time, and saving it for getting the estimated finish time for the new loading
			Calendar estimatedFinish = (GregorianCalendar) currentTime.clone();
			//adding the loading time for the all the orders
			estimatedFinish.add(Calendar.MINUTE, loadingTime); 

			//finding the minutes we should move all loadings which just are placed in queue
			long start = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTimeInMillis());
			long finish = TimeUnit.MILLISECONDS.toMinutes(estimatedFinish.getTimeInMillis());
			long time = finish-start;
			int timeToAdd = 0;
			timeToAdd += time;

			//Getting all loadings without priority and thereby moving each
			//start and finish the number of minutes it takes the priority 
			//loading to end.
			List<Loading> tempLoadings = pickedRamp.getQueueWithoutPriority();
			for (Loading lo : tempLoadings){
				Calendar tempStart = lo.getEstimatedStart();
				tempStart.add(Calendar.MINUTE, timeToAdd);
				lo.setEstimatedStart(tempStart);
				Calendar tempFinish = lo.getEstimatedFinish();
				tempFinish.add(Calendar.MINUTE, timeToAdd);
				lo.setEstimatedFinish(tempFinish);
			}
			//creating the loading, and thereby adding it to the picked ramp with the estimated start and finish
			Loading l = new Loading(currentTime, estimatedFinish, truckRegister, pickedRamp);

			//If the ramp we found was empty, 
			//then we set the state as being loaded
			//else we sat the sate as priority
			if(foundEmptySlot){
				l.setState(State.BEINGLOADED);
			}else l.setState(State.PRIORITY);
			//adding it to dao.
			dao.addLoading(l); 
		}else {
			//if the ramp is null, we throw an exception for terminating the method
			throw new RuntimeException("The specific loading cant not be created"); 
		}
	}

	/**
	 * Method for getting the time, for a specific ramp
	 * to be done with the priority queue, hereby where
	 * state is either beingloaded or priority
	 * @param ramp - specific ramp for finding the time
	 * @return long, minutes till done with priority queue
	 */
	private long getFinishWithPriorityLoadings(Ramp ramp){
		long time = -1;
		Calendar currentTime = new GregorianCalendar();
		List<Loading> loadings = ramp.getLoadingsInQueue();
		if(loadings.isEmpty()){
			time = 0;
		}else {
			Loading estimatedFinishTime = null;
			int index = 0;
			boolean found = false;
			while(index < loadings.size() && !found){
				Loading temp = loadings.get(index);
				State state = temp.getState();
				if(state.equals(State.BEINGLOADED) || state.equals(State.PRIORITY)){
					estimatedFinishTime = temp;
				}else found = true;
				index++;
			}
			long start = TimeUnit.MILLISECONDS.toMinutes(currentTime.getTimeInMillis());
			long finish = TimeUnit.MILLISECONDS.toMinutes(estimatedFinishTime.getEstimatedFinish().getTimeInMillis());

			time = finish-start;
		}
		return time;
	}

	/**
	 * Method for finding the loading thats
	 * being loaded on the specific ramp
	 * @param ramp - specific ramp for finding the loading
	 * @return Loading with the state of beingLoaded
	 */
	public Loading getLoadingBeingLoaded(Ramp ramp){
		return ramp.getLoadingBeingLoaded();
	}

	/**
	 * Method for getting all loadings which dont 
	 * have the state done from a specific ramp
	 * @param ramp - specific ramp to get the loadings from
	 * @return List<Loading> with all loadings that dont have the state of being done
	 */
	public List<Loading> getLoadingsInQueue(Ramp ramp){
		return ramp.getLoadingsInQueue();
	}

	/**
	 * Method for getting all truckRegisters
	 * @return List<TruckRegister> with all truckRegisters
	 */
	public List<TruckRegister> getTruckRegisters(){
		return dao.getTruckRegisters();
	}

	/**
	 * Method for creating a Ramp
	 * @param number - ramp number
	 * @param packageType - the packagetype the ramp can load
	 * @return Ramp with the information
	 */
	public Ramp createRamp(int number, PackageType packageType){
		Ramp r = new Ramp(number, packageType);
		dao.addRamp(r);
		return r;
	}

	/**
	 * Method for getting all the ramps
	 * @return List<Ramp> with all ramps
	 */
	public List<Ramp> getRamps(){
		return dao.getRamps();
	}


	/**
	 * Method for getting all the loadings
	 * @return List<Loading> with all loadings
	 */
	public List<Loading> getLoadings(){
		return dao.getLoadings();
	}

	/**
	 * Method for getting all loadings from a specific ramp
	 * @param ramp - ramp to get loading from
	 * @return List<Loading> of all loadings from a specific ramp
	 */
	public List<Loading> getLoadingsFromRamp(Ramp ramp){
		return ramp.getLoadings();
	}

	/**
	 * Method for adding a packagetype
	 * @param packageType - packagetype to add
	 */
	public void addPackageType(PackageType packageType){
		dao.addPackageType(packageType);
	}

	/**
	 * Method for getting all packagetypes
	 * @return List<PackageType> containing all packagetypes
	 */
	public List<PackageType> getPackageTypes(){
		return dao.getPackageTypes();
	}

	/**
	 * Method for creating some objects
	 * so we have something to show in the gui
	 * Suppressing the unused warnings!
	 */
	@SuppressWarnings("unused")
	public void createSomeObjects() {

		PackageType xmasTree = XmasTree.getInstance();
		addPackageType(xmasTree);
		PackageType plasticBox = PlasticBox.getInstance();
		addPackageType(plasticBox);
		PackageType cardboardBox = CardboardBox.getInstance();
		addPackageType(cardboardBox);

		// XmasTree
		Ramp r1 = createRamp(1, xmasTree);
		Ramp r2 = createRamp(2, xmasTree);
		Ramp r3 = createRamp(3, xmasTree);
		Ramp r4 = createRamp(4, xmasTree);
		Ramp r5 = createRamp(5, xmasTree);
		Ramp r6 = createRamp(6, xmasTree);
		Ramp r7 = createRamp(7, xmasTree);
		Ramp r8 = createRamp(8, xmasTree);

		// PlasticBox
		Ramp r9 = createRamp(9, plasticBox);
		Ramp r10 = createRamp(10, plasticBox);
		Ramp r11 = createRamp(11, plasticBox);
		Ramp r12 = createRamp(12, plasticBox);
		Ramp r13 = createRamp(13, plasticBox);
		Ramp r14 = createRamp(14, plasticBox);
		Ramp r15 = createRamp(15, plasticBox);
		Ramp r16 = createRamp(16, plasticBox);

		// CardboardBox
		Ramp r17 = createRamp(17, cardboardBox);
		Ramp r18 = createRamp(18, cardboardBox);
		Ramp r19 = createRamp(19, cardboardBox);
		Ramp r20 = createRamp(20, cardboardBox);
		Ramp r21 = createRamp(21, cardboardBox);
		Ramp r22 = createRamp(22, cardboardBox);
		Ramp r23 = createRamp(23, cardboardBox);
		Ramp r24 = createRamp(24, cardboardBox);
		Ramp r25 = createRamp(25, cardboardBox);

		PackageType[] type1 = {xmasTree};
		PackageType[] type2 = {cardboardBox, plasticBox};
		PackageType[] type3 = {xmasTree, cardboardBox, plasticBox};

		// Orders with xmastree

		Order o1 = createOrder(1, 20000, new GregorianCalendar(), 0, xmasTree);
		Truck t1 = createTruck("1234567", "Mark", "11111111", type1);
		TruckRegister tr1 = registerArrival(t1, o1.getPartialOrders(), 0, 5000);

		Order o2 = createOrder(2, 15000, new GregorianCalendar(), 0, xmasTree);
		Truck t2 = createTruck("6789012", "Emil", "66666666", type1);
		TruckRegister tr2 = registerArrival(t2, o2.getPartialOrders(), 0, 2500);

		Order o3 = createOrder(3, 100000, new GregorianCalendar(), 10, xmasTree);

		Truck t3 = createTruck("AA11111", "Mike", "12345678", type1);
		List<PartialOrder> po1 = new ArrayList<>();
		po1.add(o3.getPartialOrders().get(0));
		TruckRegister tr3 = registerArrival(t3, po1, 0, 5000);

		Truck t4 = createTruck("AA22222", "Hans", "87654321", type1);
		List<PartialOrder> po2 = new ArrayList<>();
		po2.add(o3.getPartialOrders().get(1));
		TruckRegister tr4 = registerArrival(t4, po2, 0, 2500);

		Truck t5 = createTruck("AA22222", "Jonas", "87654321", type1);
		List<PartialOrder> po3 = new ArrayList<>();
		po3.add(o3.getPartialOrders().get(2));
		TruckRegister tr5 = registerArrival(t5, po3, 0, 3000);

		Truck t6 = createTruck("AA22222", "Jakob", "87654321", type1);
		List<PartialOrder> po4 = new ArrayList<>();
		po4.add(o3.getPartialOrders().get(3));
		TruckRegister tr6 = registerArrival(t6, po4, 0, 5000);

		Truck t7 = createTruck("AA22222", "Soren", "87654321", type1);
		List<PartialOrder> po5 = new ArrayList<>();
		po5.add(o3.getPartialOrders().get(4));
		TruckRegister tr7 = registerArrival(t7, po5, 0, 4000);

		Order o4 = createOrder(4, 10000, new GregorianCalendar(), 0, xmasTree);
		Order o5 = createOrder(5, 10000, new GregorianCalendar(), 0, xmasTree);

		List<PartialOrder> temp = new ArrayList<>(o4.getPartialOrders());
		temp.add(o5.getPartialOrders().get(0));

		Truck t8 = createTruck("3456789", "Niclas", "33333333", type3);
		TruckRegister tr8 = registerArrival(t8, temp, 0, 2000);

		// Orders with plasticBox

		Order o6 = createOrder(6, 10000, new GregorianCalendar(), 0, plasticBox);
		Truck t9 = createTruck("2345678", "Henrik", "22222222", type2);
		TruckRegister tr9 = registerArrival(t9, o6.getPartialOrders(), 0, 10000);

		Order o7 = createOrder(7, 35000, new GregorianCalendar(), 0, plasticBox);

		Truck t10 = createTruck("5678901", "Jeppe", "55555555", type3);
		List<PartialOrder> po6 = new ArrayList<>();
		po6.add(o7.getPartialOrders().get(0)); 
		TruckRegister tr10 = registerArrival(t10, po6, 0, 4400);

		Truck t11 = createTruck("4567890", "Stefan", "44444444", type2);
		List<PartialOrder> po7 = new ArrayList<>();
		po7.add(o7.getPartialOrders().get(1));
		TruckRegister tr11 = registerArrival(t11, po7, 0, 3900);

		Order o8 = createOrder(8, 3000, new GregorianCalendar(), 10, plasticBox);
		Truck t12 = createTruck("ZC27141", "Nicolai", "27416166", type2);
		TruckRegister tr12 = registerArrival(t12, o8.getPartialOrders(), 0, 1000);

		Order o9 = createOrder(9, 15000, new GregorianCalendar(), 10, plasticBox);
		Truck t13 = createTruck("7890123", "Martin", "77777777", type2);
		TruckRegister tr13 = registerArrival(t13, o9.getPartialOrders(), 0, 1200);

		Order o10 = createOrder(10, 1200, new GregorianCalendar(), 10, plasticBox);
		Order o11 = createOrder(11, 2800, new GregorianCalendar(), 10, plasticBox);
		Order o12 = createOrder(12, 16000, new GregorianCalendar(), 20, plasticBox);

		Truck t14 = createTruck("BB11111", "Simon", "55555555", type2);
		List<PartialOrder> po9 = new ArrayList<>();
		po9.add(o10.getPartialOrders().get(0));
		po9.add(o11.getPartialOrders().get(0));
		po9.add(o12.getPartialOrders().get(0));
		TruckRegister tr14 = registerArrival(t14, po9, 0, 15000);

		// Orders with cardboardBox

		Order o13 = createOrder(13, 11000, new GregorianCalendar(), 12, cardboardBox);
		Truck t15 = createTruck("8901234", "Vicki", "88888888", type3);
		TruckRegister tr15 = registerArrival(t15, o13.getPartialOrders(), 0, 1100);

		// Orders

		Order o14 = createOrder(14, 83117, new GregorianCalendar(), 10, xmasTree);
		Order o15 = createOrder(15, 32400, new GregorianCalendar(), 10, plasticBox);
		Order o16 = createOrder(16, 79854, new GregorianCalendar(), 45, cardboardBox);

		// Truck 

		Truck t16 = createTruck("123456", "Casper", "12345678", type2);
		Truck t17 = createTruck("aa1547", "Sigh", "1564878", type3);
		Truck t18 = createTruck("wr18214", "Tobias", "1234567", type1);
	}
}
