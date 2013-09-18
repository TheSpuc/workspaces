package personGaver;

import java.util.ArrayList;

public class Person {

	private String name;
	private int age;
	private ArrayList<Present> presents;

	public Person(String name, int age){
		this.name = name;
		this.age = age;
		presents = new ArrayList<>();
	}

	public void setAge(int age){
		this.age = age;
	}

	public void addPresent(Present pt){
		if(!presents.contains(pt)){
			presents.add(pt);
		}
	}

	public int totalPresentMoney(){
		int total = 0;
		for(Present pt: presents){
			total += pt.getPrice();
		}
		return total;
	}

	public ArrayList<Present> getPresents(){
		return new ArrayList<Present>(presents);
	}

	public String toString(){
		return name + ", age: " + age;
	}

	public ArrayList<Person> getPresentPeople(){
		ArrayList<Person> persons = new ArrayList<>();
		for(Present pt : presents){
			persons.add(pt.getPerson());
		}
		return persons;
	}
}
