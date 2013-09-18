
public class Buyer implements Observer {

	private String name;
	
	public Buyer(String name){
		this.name = name;
	}
	
	@Override
	public void update(Subject s) {
		if(s instanceof BookTitle){
			BookTitle bk = (BookTitle) s;
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
