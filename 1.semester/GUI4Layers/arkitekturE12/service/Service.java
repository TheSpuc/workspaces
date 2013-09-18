package arkitekturE12.service;

import java.util.ArrayList;

import arkitekturE12.dao.Dao;
import arkitekturE12.model.Company;
import arkitekturE12.model.Employee;

/**
 * @author mlch and haso
 * @version mad 06.09.2011
 */
public class Service
{
	
	public static Employee createEmployee(String name, int wage, Company company){
		Employee newEmployee;
		if(company != null)
			newEmployee = new Employee(name, wage, company);	
		else
			newEmployee = new Employee(name, wage);	
		Dao.addEmployee(newEmployee);
		return newEmployee;
	}
	
	public static Company createCompany(String name, int hours){
		Company newCompany = new Company(name, hours);
		Dao.addCompany(newCompany);
		return newCompany;
		
	}
	public static void updateCompany(Company company, String name, int hours){
		company.setName(name);
		company.setHours(hours);
	}
	
	public static void updateEmployee(Employee employee, String name, int wage, Company company){
		 employee.setName(name);
         employee.setWage(wage);
         employee.setCompany(company);
	}
	
	public static void deleteEmployee(Employee employee){
		employee.setCompany(null);
		Dao.removeEmployee(employee);
	}
	
	public static void deleteCompany(Company company){ 
		Dao.removeCompany(company);
	}
	
	public static ArrayList<Company> getCompanies(){ 
		return Dao.getAllCompanies();
	}
	
	public static ArrayList<Employee> getEmployees(){ 
		return Dao.getAllEmployees();
	}
	
	 
	// This method is used to initialise the store with some objects.
	public static void createAndStoreSomeObjects() {
		Company c1 = createCompany("IBM", 37);
		Company c2 = createCompany("AMD", 40);
		Company c3 = createCompany("SLED", 45);
		Company c4 = createCompany("Vector", 32);
		 

		Employee e1 = createEmployee("Bob Dole", 210,null);
		Employee e2 = createEmployee("Alice Schmidt", 250, c1);
		Employee e3 = createEmployee("George Down", 150, null);
		Employee e4 = createEmployee("Rita Uphill", 300, c3);
			 
	}
    
}
