package model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Mobile {
	
	@Id
	@GeneratedValue
	private int id;
	private int number;
	private String manufactor;
	private String model;
	private String price;
//	private String buytime;
	@Enumerated (EnumType.STRING)
	private Design design;
	
	public Mobile(){
		
	}
	
	public Mobile(int number, String manufactor, String model, String price, Design design){
		this.number = number;
		this.manufactor = manufactor;
		this.model = model;
		this.price = price;
//		this.buytime = buytime;
		this.design = design;
	}
	
	@Override
	public String toString(){
		return model + ", " + manufactor + ", " + number + ", " + design + ", " + price + ", ";
	}
}
