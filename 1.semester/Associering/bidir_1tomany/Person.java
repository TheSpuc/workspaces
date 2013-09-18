package bidir_1tomany;

public class Person {
	private String name;

	// link to group class (--> 1)
	private Group group = null;

	/**
	 * Creates a new person. Requires: name not empty, group not null.
	 */
	public Person(String name, Group group) {
		this.name = name;
		setGroup(group);
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
		String result = null;
		if (group != null)
			result = name + " (g: " + group.getName() + ")";
		else
			result = name + " (g:null)";
		return result;
	}

	// -----------------------------------------------------------------------------------

	/**
	 * Returns the group of this person.
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Sets the group of this person.<br />
	 * Requires: group != null <br />
	 */
	public void setGroup(Group group) {
		if (this.group != group) {
			if (this.group != null) {
				this.group.removePerson(this);
			}
			this.group = group;
			if (group != null) {
				group.addPerson(this);
			}
		}

	}

}
