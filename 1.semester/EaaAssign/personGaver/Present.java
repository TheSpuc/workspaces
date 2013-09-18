package personGaver;

public class Present {

	private String description;
	private int price;
	private Person person;

	/**
	 * 
	 * @param description
	 * @param price
	 * @param person != null
	 */
	public Present(String description, Person person){
		this.description = description;
		this.person = person;
	}

	public void setPrice(int price){
		this.price = price;
	}

	public int getPrice(){
		return price;
	}

	public Person getPerson(){
		return person;
	}

	public void givePresent(Person pt){
		if(!pt.getPresents().contains(this)){
			pt.addPresent(this);
		}
	}
}
