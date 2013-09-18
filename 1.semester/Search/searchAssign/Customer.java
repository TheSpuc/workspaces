package searchAssign;

public class Customer implements Comparable<Customer> {

	private String firstName;
	private String lastName;
	private int age;
	
	public Customer(String firstName, String lastName, int age){
		this.firstName = firstName;
		this.lastName = lastName;
		this.age = age;
	}
	
	public String getFirstName(){
		return firstName;
	}
	
	public String getLastName(){
		return lastName;
	}
	
	public int getAge(){
		return age;
	}
	
	public void setFirstName(String firstName){
		this.firstName = firstName;
	}
	
	public void setLastName(String lastName){
		this.lastName = lastName;
	}
	
	public void setAge(int age){
		this.age = age;
	}
	
	public int compareTo(Customer cus){
		int temp = lastName.compareTo(cus.getLastName());
		if(temp == 0){
			temp = firstName.compareTo(cus.getFirstName());
			if(temp == 0){
				temp = ((Integer)age).compareTo(cus.getAge());
			}
		}
		return temp;
	}
}
