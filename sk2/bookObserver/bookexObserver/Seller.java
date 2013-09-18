package bookexObserver;
import java.util.HashSet;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;


public class Seller implements Observer {
	
	private String name;
	
	public Seller(String name){
		this.name = name;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(o instanceof BookTitle){
			BookTitle bk = (BookTitle) o;
			Set<BookTitle> booktitles = new HashSet<>();
			List<Customer> bklist = bk.getCustomers();
			for(Customer c : bklist){
				booktitles.addAll(c.getBookTitles());
			}
			booktitles.remove(bk);
			System.out.println(booktitles);
		}
	}
	
	@Override
	public String toString(){
		return "Seller: " + name;
	}

}
