package bidir_01tomany;

public class Person {
	private String name;

	// link to group class (--> 0..1)
	private Group group;

	/**
	 * Creates a new person.<br />
	 * Requires: name not empty.
	 */
	public Person(String name) {
		this.name = name;
		this.group = null;
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
	 * Requires:<br />
	 * Note: null is an allowed value of <i>group</i>.
	 */
	public void setGroup(Group group) {
		if (this.group != group){
			if (this.group != null){
				this.group.removePerson(this);
			}
			this.group = group;
			if (group!=null){
				group.addPerson(this);
			}
		}
			
	}
}
