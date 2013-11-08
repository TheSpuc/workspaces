package dictionary25OctoberAss1;

public class Customer {
	
	private String mobile;
	private String phone;
	private String firstName;
	private String lastName;
	
	public Customer(String mobile, String phone, String firstName, String lastName){
		this.mobile = mobile;
		this.phone = phone;
		this.firstName = firstName;
		this.lastName = lastName;
	}
	
	public String getMobile(){
		return mobile;
	}
	
	@Override
	public String toString(){
		return firstName + " " + lastName + " mobile: " + mobile + " phone: " + phone;
	}
	
}
