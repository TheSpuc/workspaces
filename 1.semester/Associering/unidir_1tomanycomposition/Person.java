package unidir_1tomanycomposition;

public class Person
{
    private String name;

   

    /**
     * Creates a new person.<br />
     * Requires: name not null 
     */
    Person(String name)
    {
        this.name = name;
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
        return name;
    }

    
}
