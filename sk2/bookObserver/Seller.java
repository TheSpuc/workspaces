import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class Seller implements Observer {
	
	private String name;
	
	public Seller(String name){
		this.name = name;
	}
	
	@Override
	public void update(Subject s) {
		if(s instanceof BookTitle){
			BookTitle bk = (BookTitle) s;
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
