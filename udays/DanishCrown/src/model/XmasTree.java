package model;

import javax.persistence.Entity;


/**
 * Class for modeling a christmas tree
 * @author Henrik McQuoid Jespersen, Mark Medum Bundgaard, Niclas Hvid Hansen
 *
 */
@Entity
public class XmasTree extends PackageType {
	
	private static XmasTree uniqueInstance;

	
	/**
	 * Private constructor, for use with Singleton pattern
	 */
	private XmasTree(){
		super(10);
	}
	
	/**
	 * Static method for getting a XmasTree object 
	 * and creating one if it dosen't exist
	 * @return XmasTree object to be used
	 */
	public static XmasTree getInstance(){
		if (uniqueInstance == null){
			uniqueInstance = new XmasTree();
		}
		return uniqueInstance;
	}

}
