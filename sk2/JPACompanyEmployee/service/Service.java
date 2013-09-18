package service;

import java.util.List;

import dao.Dao;


// import dao.DaoDb4o;



import model.Company;
import model.Employee;

/**
 * @author mlch
 */
public class Service {
	private Dao dao;
	private static Service service;
	
	private Service(){
		dao = Dao.getDao();	
	}
	
	public static Service getService(){
		if (service==null){
			service = new Service();
		}
		return service;
	}
	/**
	 * Returns a list of all employees.
	 */
	public  List<Employee> getAllEmployees() {
		return dao.getAllEmployees();
	}

	/**
	 * Creates an employee.<br />
	 * Requires: name not empty, wage >= 0.
	 */
	public Employee createEmployee(String name, int wage) {
		Employee employee = new Employee(name, wage);
		dao.store(employee);
		return employee;
	}

	/**
	 * Deletes the employee.
	 */
	public void deleteEmployee(Employee employee) {
		if (employee.getCompany() != null)
			employee.setCompany(null);
		dao.remove(employee);
	}

	/**
	 * Updates the employee.<br />
	 * Requires: name not empty, wage >= 0, and the name or the wage has
	 * changed.<br />
	 */
	public void updateEmployee(Employee employee, String name, int wage) {
		employee.setName(name);
		employee.setWage(wage);
		dao.store(employee);
	}

	/**
	 * Updates the company of the employee.<br />
	 * Requires: The company of the employee has changed.<br />
	 * Note: null is an allowed value of company.
	 */
	public void updateCompanyOfEmployee(Employee employee, Company company) {
		employee.setCompany(company);
		dao.store(company);
	}

	public List<Company> getAllCompanies() {
		return dao.getAllCompanies();
	}

	/**
	 * Creates a company.<br />
	 * Requires: name not empty, hours >= 0.
	 */
	public  Company createCompany(String name, int hours) {
		Company company = new Company(name, hours);
		dao.store(company);
		return company;
	}

	/**
	 * Deletes the company.<br />
	 * Requires: The company has no employees.
	 */
	public void deleteCompany(Company company) {
		dao.delete(company);
	}

	/**
	 * Updates the company.<br />
	 * Requires: name not empty, hours >= 0, and the name or the hours has
	 * changed.
	 */
	public  void updateCompany(Company company, String name, int hours) {
		company.setName(name);
		company.setHours(hours);
		dao.store(company);
	}

	/**
	 * Does any initialising needed when the application starts up.
	 */
	public void startUp() {
//		 createSomeObjects();
	}

	/**
	 * Does any housekeeping needed when the application closes down.
	 */
	public void closeDown() {
		// nothing needed
		dao.close();
	}

	// -----------------------------------------------------------------------------------

	// This method is used to initialise the store with some objects.
	private void createSomeObjects() {
		Company c1 = createCompany("IBM", 37);
		createCompany("AMD", 40);
		Company c3 = createCompany("SLED", 45);
		createCompany("Vector", 32);

		createEmployee("Bob Dole", 210);
		Employee e2 = createEmployee("Alice Schmidt", 250);
		updateCompanyOfEmployee(e2, c1);
		createEmployee("George Down", 150);
		Employee e4 = createEmployee("Rita Uphill", 300);
		updateCompanyOfEmployee(e4, c3);
	}
}
