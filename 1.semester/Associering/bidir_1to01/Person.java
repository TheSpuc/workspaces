package bidir_1to01;



public class Person {
	private String name;

	// link to group class (--> 1)
	private Group group = null;

	/**
	 * Creates a new person. 
	 * Requires: name and group is not empty.
	 */
	public Person(String name, Group group) {
		this.name = name;
		this.group = group;
		group.setPerson(this);
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
		String result =null;
		if(group!=null)
			result= name + " (g: " + group.getName() + ")";
		else
			result= name + " (g:null)";
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
	 * Sets or unsets the group of this person.<br />
	 * Requires:<br />
	 * Note: This method breaks the to-1-group multiplicity of this person, if
	 * <i>group</i> is null.
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
