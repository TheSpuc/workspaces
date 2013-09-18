package unidir_tomany;

import java.util.ArrayList;

public class Group {

	private String name;

	//link to Person class (--> 0..*)
	private ArrayList<Person> persons;

	/**
	 * Creates a new group.<br />
	 * Requires: name not empty.
	 */
	public Group(String name) {
		this.name = name;
		this.persons = new ArrayList<Person>();
	}

	/**
	 * Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.<br />
	 * Requires: name not empty.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " (p: " + persons + ")";
	}

	//-----------------------------------------------------------------------------------

	/**
	 * Returns a list of persons in this group.
	 */
	public ArrayList<Person> getPersons() {
		return new ArrayList<Person>(persons) ;
	}

	/**
	 * Adds the person to this group.<br />
	 * Requires: person is not null.
	 */
	public void addPerson(Person person) {
		if (!persons.contains(person))
			persons.add(person);
	}

	/**
	 * Removes the person from this group.<br />
	 */
	public void removePerson(Person person) {
		if (persons.contains(person))
			persons.remove(person);
	}

}
