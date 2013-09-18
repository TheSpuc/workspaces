package bidir_1tomanycomposition;

public class Person
{
    private String name;

    //link to group class (--> 1)
    private Group group;

    /**
     * Creates a new person.<br />
     * Requires: name not null and group is not null.<br />
     */
    Person(String name, Group group)
    {
        this.name = name;
        this.group = group;
    }

    /**
     * Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.
     * Requires: name not empty.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name + " (g: " + group.getName() + ")";
    }

    //-----------------------------------------------------------------------------------

    /**
     * Returns the group of this person.
     */
    public Group getGroup()
    {
        return group;
    }

    /**
     * Sets the group of this person.<br />
     * Requires:<br /> 
     */
    void unsetGroup()  // Person can be eaten by the Garbage collector
    {
        //One-way link from Person to Group.
    	if(this.group!=null)
    		this.group = null;
    }
}
