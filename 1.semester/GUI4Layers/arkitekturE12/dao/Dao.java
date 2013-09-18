package arkitekturE12.dao;

import java.util.ArrayList;


import arkitekturE12.model.Company;
import arkitekturE12.model.Employee;

/**
 * @author mlch and haso
 */
public class Dao {
	private static ArrayList<Company> companies = new ArrayList<Company>();
	private static ArrayList<Employee> employees = new ArrayList<Employee>();

	/**
	 * Returns a list of all companies in the store.
	 */
	public static ArrayList<Company> getAllCompanies() {
		return new ArrayList<Company>(companies);
	}

	/**
	 * Adds the company to the store.<br />
	 * Requires: The company is not stored.
	 */
	public static void addCompany(Company company) {
		if (!companies.contains(company))
			companies.add(company);
	}

	/**
	 * Removes the company from store.<br />
	 * Requires: The company is stored.
	 */
	public static void removeCompany(Company company) {
		if (companies.contains(company))
			companies.remove(company);
	}

	/**
	 * Returns a list of all employees in the store.
	 */
	public static ArrayList<Employee> getAllEmployees() {
		return new ArrayList<Employee>(employees);
	}

	/**
	 * Adds the employee to the store.<br />
	 * Requires: The employee is not stored.
	 */
	public static void addEmployee(Employee employee) {
		if (!employees.contains(employee))
			employees.add(employee);
	}

	/**
	 * Removes the employee from store.<br />
	 * Requires: The employee is stored.
	 */
	public static void removeEmployee(Employee employee) {
		if (employees.contains(employee))
			employees.remove(employee);
	}


}
