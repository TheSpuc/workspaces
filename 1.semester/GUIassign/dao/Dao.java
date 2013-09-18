package dao;

import java.util.ArrayList;

import model.Person;

public class Dao {

	private static ArrayList<Person> persons = new ArrayList<>();
	
	public static void addPerson(Person ps){
		if(!persons.contains(ps)){
			persons.add(ps);
		}
	}
	
	public static void removePerson(Person ps){
		if(persons.contains(ps)){
			persons.remove(ps);
		}
	}
	
	public static ArrayList<Person> getPersons(){
		return new ArrayList<Person>(persons);
	}
}
