package test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

import model.Employee;

public class TestFindHul {

	private static EntityManagerFactory emf;
	private static EntityManager em;

	public static void main(String[] args) {
		emf = Persistence.createEntityManagerFactory("Companytest");
		em = emf.createEntityManager();

//		System.out.println(findEmployee("Bob Dole"));
		System.out.println(highSalat());
		System.out.println(hiredInCompany("SLED"));

		em.close();
		emf.close();
	}

	public static Employee findEmployee(String name) {
		Employee emp = null;
		String jplQuery = "SELECT e FROM Employee e WHERE e.name = :name";
		Query query = em.createQuery(jplQuery);
		query.setParameter("name", name);
		List<Employee> list = query.getResultList();
		if (list.size() > 0)
			emp = list.get(0);
		return emp;
	}
	
	public static List<Employee> highSalat(){
		return em.createQuery("SELECT e FROM Employee e WHERE e.wage > 500", Employee.class).getResultList();
	}
	
	public static List<Employee> hiredInCompany(String company){
		return em.createQuery("SELECT e FROM Employee e JOIN e.company c WHERE c.name = ?1 ", Employee.class).setParameter(1, company).getResultList();
	}
}
