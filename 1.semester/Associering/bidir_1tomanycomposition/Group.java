package bidir_1tomanycomposition;

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
        return name + " (p: " + persons + ")";
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
     * Creates a person linked to this group.
     */
    public Person createPerson(String name)
    {
        //Two-way link between Group and Person.
        Person person = new Person(name,this);
        persons.add(person);
        return person;
    }

    /**
     * Removes the person from this group.<br />
     * Requires: 
     * Note: This method breaks the to-1-group multiplicity of the person.
     */
    public void deletePerson(Person person)
    {
        //Two-way link between Group and Person.
    	if(persons.contains(person)){
    		person.unsetGroup(); // Person can be eaten by the Garbage collector
    		persons.remove(person);  
    	}
    }
}
