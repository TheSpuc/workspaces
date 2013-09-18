package model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Models an employee.
 * 
 * @author mlch
 */
@Entity
public class Employee {

	@Id
	@GeneratedValue
	private int id;
	// Invariant: name not empty, wage >= 0.
	private String name;
	private int wage; // hourly wage

	// link to company class (0..* --> 0..1)
	@ManyToOne
	private Company company;

	
	public Employee(){}

	/**
	 * Creates a new employee.<br />
	 * Requires: name not empty, wage >= 0.
	 */
	public Employee(String name, int wage) {
		this.name = name;
		this.wage = wage;
		this.company = null;
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
	 * Returns the wage.
	 */
	public int getWage() {
		return wage;
	}

	/**
	 * Sets the wage.<br />
	 * Requires: wage >= 0.
	 */
	public void setWage(int wage) {
		this.wage = wage;
	}

	/**
	 * Returns a string describing this employee.
	 */
	@Override
	public String toString() {
		String s = name + " (wage: " + wage + ")";
		if (company != null)
			s = name + " (wage: " + wage + ", company: " + company.getName()
					+ ")";
		return s;
	}

	// -----------------------------------------------------------------------------

	/**
	 * Returns the company of this employee. If this employee does not have a
	 * company, null is returned.
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company of this employee.<br />
	 * Requires:
	 */
	public void setCompany(Company company) {
		if (this.company != company) {
			if (this.company != null) {
				this.company.removeEmployee(this);
			}
			this.company = company;
			if (company != null) {
				company.addEmployee(this);
			}
		}

	}

	// -----------------------------------------------------------------------------

	/**
	 * Calculates the weekly salary.<br />
	 * Requires: This employee has a company.
	 */
	public double calcWeeklySalary() {
		return wage * company.getHours();
	}

}
