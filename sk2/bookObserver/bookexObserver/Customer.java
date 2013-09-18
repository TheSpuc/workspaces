package bookexObserver;
import java.util.ArrayList;
import java.util.List;


public class Customer {
	
	private String name;
	private List<BookTitle> bookTitles;
	
	public Customer(String name){
		this.name = name;
		bookTitles = new ArrayList<>();
	}
	
	//Associations
	public void addBookTitle(BookTitle bt){
		if(!bookTitles.contains(bt)){
			bookTitles.add(bt);
			bt.addCustomer(this);
		}
	}
	
	public void removeBookTitle(BookTitle bt){
		bookTitles.remove(bt);
		bt.removeCustomer(this);
	}
	
	public ArrayList<BookTitle> getBookTitles(){
		return new ArrayList<>(bookTitles);
	}
	
	@Override
	public String toString(){
		return name;
	}
}
