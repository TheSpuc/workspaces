package bookexObserver;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Set;


public class BookTitle extends Observable {


	private String title;
	private int count;
//	private Set<Observer> observers;
	private List<Customer> customers;
	private boolean changed;

	public BookTitle(String title, int count){
		this.title = title;
		this.count = count;
//		observers = new HashSet<>();
		customers = new ArrayList<>();
		changed = false;
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
		setChanged(); //making certain to call that we have made a change
		notifyObservers(); //call to the notify observer, based upon the observer pattern
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

//	public void notifyObservers() { //notifyObserver part, based on UML, here as private
//		if(changed){
//			Iterator<Observer> it = observers.iterator();
//			while(it.hasNext()){
//				it.next().update(this);
//			}
//			changed = false;
//		}
//	}

	public void setChanged(){
		changed = true;
	}

//	public void addObserver(Observer o){
//		observers.add(o);
//	}

//	public void deleteObserver(Observer o){
//		observers.remove(o);
//	}

	@Override
	public String toString(){
		return title;
	}
}
