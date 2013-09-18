package model;

public class Person {

	private String name;
	private String titel;
	private boolean retired;

	public Person(String name, String title, boolean retired){
		this.name = name;
		this.titel = title;
		this.retired = retired;
	}

	public String toString(){
		String s = titel + " " + name;
		if(retired){
			s += " (retired)"; 
		}
		return s;
	}
}
