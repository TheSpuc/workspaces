package bidir_01to01;

public class Person {
	private String name;

	// link to group class (--> 0..1)
	private Group group = null;

	/**
	 * Creates a new person. Requires: name not empty.
	 */
	public Person(String name) {
		this.name = name;
	}

	/**
	 * Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name. Requires: name not empty.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name + " (g: " + (group != null ? group.getName() : "null")
				+ ")";
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Returns the group of this person.
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Sets or unsets the group of this person.<br />
	 * Note: null is an allowed value of <i>group</i>.
	 */
	public void setGroup(Group group) {
		if (this.group != group){
			if (this.group != null){
				this.group.unsetPerson();
			}
			this.group = group;	
			if (group != null){
				group.setPerson(this);
			}
		}
	}
	
	void unsetgroup(){
		this.group = null;
	}
}
