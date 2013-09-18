package bidir_01tomany;

import java.util.ArrayList;

public class Group {
	private String name;

	// link to Person class (--> 0..*)
	private ArrayList<Person> persons;

	/**
	 * Creates a new group.<br />
	 * Requires: name not empty.
	 */
	public Group(String name) {
		this.name = name;
		this.persons =  new ArrayList<Person>();
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

	// -----------------------------------------------------------------------------------

	/**
	 * Returns a list of persons in this group.
	 */
	public ArrayList<Person> getPersons() {
		return new ArrayList<Person>(persons);
	}

	/**
	 * Adds the person to this group.<br />
	 * Requires: No group is linked to the person.
	 */
	public void addPerson(Person person) {
		// Two-way link between Group and Person.
		if (!persons.contains(person)) {
			persons.add(person);
			person.setGroup(this);
		}
	}

	/**
	 * Removes the person from this group.<br />
	 * Requires: This group is linked to the person.
	 */
	public void removePerson(Person person) {
		// Two-way link between Group and Person.
		if (persons.contains(person)) {
			persons.remove(person);
			person.setGroup(null);
			
		}
	}
}
