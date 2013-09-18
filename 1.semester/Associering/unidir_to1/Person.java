package unidir_to1;

public class Person {
  
    private String name;
    
	//link to Group class (--> 0..1)
	private Group group;

    /**
     * Creates a new person.
     * Requires: name not null and group not null
     */
    public Person(String name, Group group) {

        this.name = name;
        this.group = group;
    }

    /**
     * Returns the name.
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name.
     * Requires: name not null.
     */
    public void setName(String name) {
    
        this.name = name;
    }

    @Override
    public String toString() {
		return name + " (g: " + (group != null ? group.getName() : "null") + ")";

    }
    /**
	 * Returns the group of this person.
	 */
	public Group getGroup() {
		return group;
	}

	/**
	 * Sets the group of this person.
	 * Requires: group is not null
	 */
	public void setGroup(Group group) {
		if(this.group!=group)
			this.group = group;
	}

	
}
