package model;

import javax.persistence.Entity;


/**
 * Class for modeling a plastic box
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
public class PlasticBox extends PackageType {

	private static PlasticBox uniqueInstance;
	
	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private PlasticBox(){
		super(10);
	}
	
	/**
	 * Static method for getting a PlasticBox object 
	 * and creating one if it dosen't exist
	 * @return PlasticBox object to be used
	 */
	public static PlasticBox getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new PlasticBox();
		}
		return uniqueInstance;
	}
}
