package service;

import java.util.ArrayList;

import dao.Dao;
import model.Person;

public class Service {

	public static Person createPerson(String title, String name, boolean retired){
		Person person = new Person(title, name, retired);
		Dao.addPerson(person);
		return person;
	}
	
	public static void updatePerson(Person person, String name, String title, boolean retired){
		person.setName(name);
		person.setTitle(title);
		
	}
	
	public static void removePerson(Person person){
		Dao.removePerson(person);
	}
	
	public static ArrayList<Person> getPersons(){
		return Dao.getPersons();
	}
}
