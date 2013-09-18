package model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;



/**
 * Models a company.
 * 
 * @author mlch
 */
@Entity
public class Company {
	
	
	@Id
	@GeneratedValue
	private int id;
	// Invariant: name not empty, hours >= 0.
	private String name;
	private int hours; // weekly work hours

	// link to Employee class (0..1 --> 0..*)
	
	@OneToMany(mappedBy="company")
	private List<Employee> employees = new ArrayList<Employee>();

	public Company(){}
	
		/**
	 * Creates a new company.<br />
	 * Requires: name not empty, hours >= 0.
	 */
	public Company(String name, int hours) {
		this.name = name;
		this.hours = hours;
	}

	/**
	 * Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.<br />
	 * Requires: name not empty.
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Returns the weekly work hours.
	 */
	public int getHours() {
		return hours;
	}

	/**
	 * Sets the weekly work hours.<br />
	 * Requires: hours >= 0.
	 */
	public void setHours(int hours) {
		this.hours = hours;
	}

	/**
	 * Returns a string describing this company.
	 */
	@Override
	public String toString() {
		return name + " (hours: " + hours + ")";
	}

	// -----------------------------------------------------------------------------

	/**
	 * Returns a list of this company's employees.
	 */
	public List<Employee> getEmployees() {
		return new ArrayList<Employee>(employees);
	}

	/**
	 * 
	 */
	public void addEmployee(Employee employee) {
		if (!employees.contains(employee)){
			employees.add(employee);
			employee.setCompany(this);
		}
	}
	
	/**
	 * 
	 */
	public void removeEmployee(Employee employee) {
		if(employees.contains(employee)){
			employees.remove(employee);
			employee.setCompany(null);
		}
	}

	// -----------------------------------------------------------------------------

	/**
	 * Returns the count of employees in this company.
	 */
	public int getEmployeesCount() {
		return employees.size();
	}
}
