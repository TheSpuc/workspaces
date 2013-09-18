package bookexObserver;

import java.util.Observable;
import java.util.Observer;


public class Buyer implements Observer {

	private String name;
	
	public Buyer(String name){
		this.name = name;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof BookTitle){
			BookTitle bk = (BookTitle) o;
			if(bk.getCount() < 6){
				System.out.println("buying 10 more of: " + bk.getTitle());
				bk.buyForStorage(10);
			}
		}
	}
	
	@Override
	public String toString(){
		return "Buyer: " + name;
	}

}
