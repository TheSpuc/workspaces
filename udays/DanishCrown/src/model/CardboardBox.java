package model;

import javax.persistence.Entity;


/**
 * Class for modeling a cardboard box
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
public class CardboardBox extends PackageType {
	
	private static CardboardBox uniqueInstance;

	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private CardboardBox(){
		super(5);
	}

	/**
	 * Static method for getting a CardboardBox object 
	 * and creating one if it dosen't exist
	 * @return CardboardBox object to be used
	 */
	public static CardboardBox getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new CardboardBox();
		}
		return uniqueInstance;
	}
	
}
