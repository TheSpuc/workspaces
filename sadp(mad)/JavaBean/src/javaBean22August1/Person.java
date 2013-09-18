package javaBean22August1;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;

public class Person {
	
	//PropertyChangeSupport is used for creating the possibility to listen for changes.
	private PropertyChangeSupport change = new PropertyChangeSupport(this);
	private List<AgeErrorListener> listeners;
	private String name;
	private int age;
	private String adr;
	
	//No parameters in the constructor then we are working with a JavaBean
	public Person(){
		listeners = new ArrayList<>();
		name = "";
		age = -1;
		adr = "";
	}
	
	public String getName(){
		return name;
	}
	
	public void setName(String name){
		String oldName = this.name;
		this.name = name;
		change.firePropertyChange("name", oldName, name); //Fire the change
	}
	
	public int getAge(){
		return age;
	}
	
	public void setAge(int age){
		if(age < 0 || age > 141){
			fireAgeErrorEvent(new AgeErrorEvent(this));
		}else this.age = age;
	}
	
	public String getAdr(){
		return adr;
	}
	
	public void setAdr(String adr){
		String oldAdr = this.adr;
		this.adr = adr;
		change.firePropertyChange("adr", oldAdr, adr); //Fire the change
	}
	
	/**
	 * For adding a listener to this specific object when created
	 * @param l
	 */
	public void addPropertyChangeListener(PropertyChangeListener l){
		change.addPropertyChangeListener(l);
	}
	
	/**
	 * For removing a listener to this specific object when created
	 * @param l
	 */
	public void removePropertyChangeListener(PropertyChangeListener l){
		change.removePropertyChangeListener(l);
	}
	
	
	//From here AgeError related
	
	//Should be synchronized if there is threads running
	public void addAgeErrorListener(AgeErrorListener a){
		listeners.add(a);
	}
	
	//Should be synchronized if there is threads running
	public void removeAgeErrorListener(AgeErrorListener a){
		listeners.remove(a);
	}
	
	//Fire the event when the age isn't between the defined age.
	private void fireAgeErrorEvent(AgeErrorEvent event){
		List<AgeErrorListener> list;
		synchronized (this) { //synchronized so we have the same list every time the method is being run.
			list = new ArrayList<>(listeners);
		}
		
		for(AgeErrorListener ae: list){
			ae.ageOutOfRange(event);
		}
	}

}
