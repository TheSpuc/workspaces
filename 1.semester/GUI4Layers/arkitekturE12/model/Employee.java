package arkitekturE12.model;

/**
 * Models an employee.
 * 
 * @author mlch
 * @version mad 06.09.2011
 */

public class Employee {
	private String name;
	private int wage; // hourly wage

	// link to company class (0..* --> 0..1)
	private Company company = null;

	/**
	 * Creates a new employee.<br />
	 * Requires: wage >= 0.
	 */
	public Employee(String name, int wage) {
		this.name = name;
		this.wage = wage;
	}

	/**
	 * Creates a new employee linked to the company.<br />
	 * Requires: wage >= 0.
	 */
	public Employee(String name, int wage, Company company) {
		this(name, wage); //kalder constructor ovenover
		if (company !=null) 
			this.setCompany(company);
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
	 * Returns the company of this employee (or null, if no company).
	 */
	public Company getCompany() {
		return company;
	}

	/**
	 * Sets the company of this employee.<br />
	 */
	public void setCompany(Company company) {
		if (this.company != company) {
			if (this.company != null) {
				this.company.removeEmployee(this);
			}
			this.company = company;
			if (this.company != null)
				this.company.addEmployee(this);
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
