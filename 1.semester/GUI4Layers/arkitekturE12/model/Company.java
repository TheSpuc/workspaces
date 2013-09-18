package arkitekturE12.model;

import java.util.ArrayList;


/**
 * Models a company.
 * 
 * @author mlch
 * @version mad 06.09.2011
 */

public class Company {
	private String name;
	private int hours; // weekly work hours

	// link to Employee class (0..1 --> 0..*)
	private ArrayList<Employee> employees = new ArrayList<Employee>();

	/**
	 * Creates a new company.<br />
	 * Requires: hours >= 0.
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
	 * Sets the name.
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
	public ArrayList<Employee> getEmployees() {
		return new ArrayList<Employee>(employees);
	}

	/**
	 * Adds the employee to this company. Requires: The employee is not linked
	 * to this company. Note: Package visible method to be used in Employee
	 * class only.
	 */
	void addEmployee(Employee employee) {
		if (!employees.contains(employee))
			employees.add(employee);
	}

	/**
	 * Removes the employee from this company. Requires: The employee is linked
	 * to this company. Note: Package visible method to be used in Employee
	 * class only.
	 */
	void removeEmployee(Employee employee) {
		if (employees.contains(employee))
			employees.remove(employee);
	}

	// -----------------------------------------------------------------------------

	/**
	 * Returns the count of employees in this company.
	 */
	public int getEmployeesCount() {
		return employees.size();
	}
}
