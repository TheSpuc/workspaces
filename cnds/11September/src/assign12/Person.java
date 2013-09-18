package assign12;

public class Person {
	
	
	private String name, city;
	private int id;
	
	public Person(String name, String city, int id){
		this.id = id;
		this.name = name;
		this.city = city;
	}
	
	public int getId(){
		return id;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCity(){
		return city;
	}
	
	@Override
	public String toString(){
		return name + " " + city + " " + id;
	}
}
