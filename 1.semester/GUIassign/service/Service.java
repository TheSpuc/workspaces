package service;

import java.util.ArrayList;

import dao.Dao;
import model.Person;

public class Service {

	public static Person createPerson(String title, String name, boolean retired){
		Person ps = new Person(title, name, retired);
		Dao.addPerson(ps);
		return ps;
	}
	
	public static ArrayList<Person> getPersons(){
		return Dao.getPersons();
	}
}

