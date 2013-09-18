package bidir_01to01;

public class Group {
	private String name;

	// link to Person class (--> 0..1)
	private Person person = null;

	/**
	 * Creates a new group.<br />
	 * Requires: name not empty.
	 */
	public Group(String name) {
		this.name = name;
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
		return name + " (p: " + (person != null ? person.getName() : "null")
				+ ")";
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Returns the person of this group.
	 */
	public Person getPerson() {
		return person;
	}

	/**
	 * Sets the person of this group.<br />
	 * Note: null is an allowed value of <i>person</i>.
	 */
	public void setPerson(Person person) {
		if (person != this.person){
			if (this.person != null){
				this.person.unsetgroup();
			}
			this.person = person;
			if (person !=null){
				person.setGroup(this);
			}
		}
	}
	
	void unsetPerson(){
		this.person = null;
	}
	 
}
