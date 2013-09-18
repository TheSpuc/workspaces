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
	
	public void setName(String name){
		this.name = name;
	}
	
	public void setTitle(String title){
		this.titel = title;
	}
	
	public void setRetired(boolean retired){
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
