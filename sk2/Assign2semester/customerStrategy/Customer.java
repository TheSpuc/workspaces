 package customerStrategy;

public class Customer implements Comparable<Customer> {
	
	private String name;
	private int nr;
	
	public Customer(String name, int nr){
		this.name = name;
		this.nr = nr;
	}
	
	public String getName(){
		return name;
	}
	
	public int getNr(){
		return nr;
	}
	
	@Override
	public String toString(){
		return name + ", " + nr; 
	}

	@Override
	public int compareTo(Customer o) {
		return name.compareTo(o.getName());
	}
}
