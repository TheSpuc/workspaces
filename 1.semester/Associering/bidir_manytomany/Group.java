package bidir_manytomany;

import java.util.ArrayList;

public class Group
{
    private String name;

    //link to Person class (--> 0..*)
    private ArrayList<Person> persons;

    /**
     * Creates a new group.<br />
     * Requires: name not empty. 
     */
    public Group(String name)
    {
        this.name = name;
        this.persons = new ArrayList<Person>();
    }

    /**
     * Returns the name.
     */
    public String getName()
    {
        return name;
    }

    /**
     * Sets the name.<br />
     * Requires: name not empty.
     */
    public void setName(String name)
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        String s = name + " [p: ";
        for (Person p : persons) {
            s += p.getName() + ",";
        }
        s += "]";
        return s;
    }

    //-----------------------------------------------------------------------------------

    /**
     * Returns a list of persons in this group.
     */
    public ArrayList<Person> getPersons()
    {
        return new ArrayList<Person>(persons);
    }

   
    /**
     * Adds the person to this group,
     * if the person is not linked to the group.<br />
     * Requires: person is not null
     */
    public void addPerson(Person person){
    	
    	if (!persons.contains(person)){
    		persons.add(person);
    		person.addGroup(this);
    	}
        //Two-way link between Group and Person.  
    }

    /**
     * Removes the person from this group.<br />
     * Requires: person is not null
     */
    public void removePerson(Person person)
    {
        //Two-way link between Group and Person.
    	if (persons.contains(person)){
    		persons.remove(person);
    		person.removeGroup(this);
    	}
    }
}
