package dao;


import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import model.Company;
import model.Employee;

/**
 * @author mlch
 */
public class Dao {
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("Companytest");
	private EntityManager em = emf.createEntityManager();
	private EntityTransaction tx = em.getTransaction();

	private static Dao dao;

	private Dao(){

	}

	public static Dao getDao(){
		if (dao== null)
			dao = new Dao();
		return dao;
	}

	/**
	 * Returns a list of all stored companies.
	 */
	public List<Company> getAllCompanies() {
		return em.createQuery("SELECT c FROM Company c", Company.class).getResultList();
	}

	/**
	 * Stores the company.<br />
	 * Requires: The company is not stored.
	 */
	public void store(Company company) {
		tx.begin();
		em.persist(company);
		tx.commit();
	}

	/**
	 * Deletes the stored company.<br />
	 * Requires: The company is stored.
	 */
	public void delete(Company company) {
		tx.begin();
		em.remove(company);
		tx.commit();
	}

	/**
	 * Returns a list of all employees in the store.
	 */
	public List<Employee> getAllEmployees() {
		return em.createQuery("SELECT e FROM Employee e", Employee.class).getResultList();
	}

	/**
	 * Stores the employee.<br />
	 * Requires: The employee is not stored.
	 */
	public void store(Employee employee) {
		tx.begin();
		em.persist(employee);
		tx.commit();
	}

	/**
	 * Removes the stored employee.<br />
	 * Requires: The employee is stored.
	 */
	public void remove(Employee employee) {
		tx.begin();
		em.persist(employee);
		tx.commit();
	}

	public void close(){
		// do nothing here
	}
}
