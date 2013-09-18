import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;


public class BookTitle implements Subject {


	private String title;
	private int count;
	private Set<Observer> oberservers;
	private List<Customer> customers;

	public BookTitle(String title, int count){
		this.title = title;
		this.count = count;
		oberservers = new HashSet<>();
		customers = new ArrayList<>();
	}

	public String getTitle(){
		return title;
	}

	public int getCount(){
		return count;
	}
	
	public ArrayList<Customer> getCustomers(){
		//getting all customers, so its possible to run through each brought book titles
		return new ArrayList<>(customers);
	}
	
	//number of book titles added - being called from the observer
	public void buyForStorage(int number){
		count += number;
	}
	
	//Simulating a order of a book
	public void buy(Customer cus){
		cus.addBookTitle(this);
		count--;
		fire(); //call to the notify observer, based upon the observer pattern
	}

	//Associations
	public void addCustomer(Customer c){
		if(!customers.contains(c)){
			customers.add(c);
			c.addBookTitle(this);
		}
	}

	public void removeCustomer(Customer c){
		customers.remove(c);
		c.removeBookTitle(this);
	}


	//Observer pattern from here

	private void fire() { //notifyObserver part, based on UML, here as private
		Iterator<Observer> it = oberservers.iterator();
		while(it.hasNext()){
			it.next().update(this);
		}
	}

	@Override
	public void addObserver(Observer o) {
		oberservers.add(o);
	}

	@Override
	public void removeObserver(Observer o) {
		oberservers.remove(o);
	}
	
	@Override
	public String toString(){
		return title;
	}
}
