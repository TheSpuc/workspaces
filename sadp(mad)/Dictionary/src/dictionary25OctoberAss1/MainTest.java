package dictionary25OctoberAss1;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class MainTest {
	
	public static void main(String[] args){
		
		Customer c1 = new Customer("34455647", "", "Mark", "Medum");
		Customer c2 = new Customer("94583402", "", "Esben", "Dall");
		Customer c3 = new Customer("75843912", "", "Liv", "Medum");
		
		Map<String, Customer> customers = new HashMap<>();
		
		customers.put(c1.getMobile(), c1);
		customers.put(c2.getMobile(), c2);
		customers.put(c3.getMobile(), c3);
		
		
		for(String s : customers.keySet()){
			System.out.println(s);
		}
		
		System.out.println("\nValues\n");
		
		for(Customer c : customers.values()){
			System.out.println(c);
		}
		
		
		System.out.println("\nTreeMap from here\n");
		
		Map<String, Customer> customersTree = new TreeMap<>();
		
		customersTree.put(c1.getMobile(), c1);
		customersTree.put(c2.getMobile(), c2);
		customersTree.put(c3.getMobile(), c3);
		
		for(String s : customersTree.keySet()){
			System.out.println(s);
		}
		
		
		System.out.println("\nValues\n");
		
		for(Customer c : customersTree.values()){
			System.out.println(c);
		}
	}
}
