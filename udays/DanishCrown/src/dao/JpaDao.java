package dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

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
public class JpaDao implements Dao {
	
	private static JpaDao uniqueInstance;
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("model");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();

	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private JpaDao(){
		
	}
	
	/**
	 * Static method for getting a JpaDao object 
	 * and creating one if it dosen't exist
	 * @return Service object to be used
	 */
	public static JpaDao getInstance(){
		if(uniqueInstance == null){
			uniqueInstance = new JpaDao();
		}
		return uniqueInstance;
	}

	@Override
	public void addTruck(Truck t) {
		tx.begin();
		em.persist(t);
		tx.commit();
	}

	@Override
	public void removeTruck(Truck t) {
		tx.begin();
		em.remove(t);
		tx.commit();
		
	}

	@Override
	public List<Truck> getTrucks() {
		return em.createQuery("SELECT t FROM Truck t", Truck.class).getResultList();
	}

	@Override
	public void addRamp(Ramp r) {
		tx.begin();
		em.persist(r);
		tx.commit();
	}

	@Override
	public void removeRamp(Ramp r) {
		tx.begin();
		em.remove(r);
		tx.commit();
		
	}

	@Override
	public List<Ramp> getRamps() {
		return em.createQuery("SELECT r FROM Ramp r", Ramp.class).getResultList();
	}

	@Override
	public void addTruckRegister(TruckRegister tr) {
		tx.begin();
		em.persist(tr);
		tx.commit();
	}

	@Override
	public void removeTruckRegister(TruckRegister tr) {
		tx.begin();
		em.remove(tr);
		tx.commit();
		
	}

	@Override
	public List<TruckRegister> getTruckRegisters() {
		return em.createQuery("SELECT tr FROM TruckRegister tr", TruckRegister.class).getResultList();
	}

	@Override
	public void addOrder(Order o) {
		tx.begin();
		em.persist(o);
		tx.commit();
	}

	@Override
	public void removeOrder(Order o) {
		tx.begin();
		em.remove(o);
		tx.commit();
		
	}

	@Override
	public List<Order> getOrders() {
		return em.createQuery("SELECT o FROM Order o", Order.class).getResultList();
	}

	@Override
	public void addLoading(Loading l) {
		tx.begin();
		em.persist(l); 
		tx.commit();
	}

	@Override
	public void removeLoading(Loading l) {
		tx.begin();
		em.remove(l);
		tx.commit();
		
	}

	@Override
	public List<Loading> getLoadings() {
		return em.createQuery("SELECT l FROM Loading l", Loading.class).getResultList();
	}

	@Override
	public void addPackageType(PackageType p) {
		tx.begin();
		em.persist(p);
		tx.commit();
	}

	@Override
	public List<PackageType> getPackageTypes() {
		return em.createQuery("SELECT p FROM PackageType p", PackageType.class).getResultList();
	}
	
	@Override
	public void update(){
		tx.begin();
		tx.commit();
	}
}
