package bidir_manytomany;

import java.util.ArrayList;

public class Person
{
    private String name;

    //link to group class (--> 0..*)
    private ArrayList<Group> groups;

    /**
     * Creates a new person.
     * Requires: name not empty.
     */
    public Person(String name)
    {
        this.name = name;
        this.groups  = new ArrayList<Group>();
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
        String s = name + " [g: ";
        for (Group g : groups) {
            s += g.getName() + ",";
        }
        s += "]";
        return s;
    }

    //-----------------------------------------------------------------------------------

    /**
     * Returns a list of this person's groups.
     */
    public ArrayList<Group> getGroups()
    {
        return new ArrayList<Group>(groups);
    }

   
    /**
     * Adds the group to this person,
     * if the group is not linked to the person. <br />
     * Requires: group is not null
     */
    public void addGroup(Group group)
    {
       if(!groups.contains(group)){
    	   groups.add(group);
    	   group.addPerson(this);
       }
    }

    /**
     * Removes the group from this person.<br />
     * Requires: group is not null
     */
    public void removeGroup(Group group)
    {
        if(groups.contains(group)){
        	groups.remove(group);
        	group.removePerson(this);
        }
    }
}
