package generic4September1;

public class Name implements Comparable<Name> {
	
	private String firstName;
	private String lastName;
	
	public Name(String firstName, String lastName){
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public String getlastName(){
		return lastName;
	}
	
	public String toString(){
		return firstName + " " + lastName;
	}

	@Override
	public int compareTo(Name o) {
		return firstName.compareTo(o.getFirstName());
	}
	
	
}
