package unidir_to01;

public class Person {
  
    private String name;
    
	//link to Group class (--> 0..1)
	private Group group;

    /**
     * Creates a new person.
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
     * Sets the name.
     * Requires: name not empty.
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
	 * Note: null is an allowed value of group
	 */
	public void setGroup(Group group) {
		if(this.group!=group)
			this.group = group;
	}
	
	
}
