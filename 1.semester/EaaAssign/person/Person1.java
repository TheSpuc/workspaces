package person;

import java.util.GregorianCalendar;
/**
 * Write a description of class Person here.
 * 
 * @author (Margrethe Dybdahl) 
 * @version (12/09/2010)
 */
public class Person1
{
    private String firstName;
    private String lastName;
    private int dayOfBirth; //1..31
    private int monthOfBirth; //1..12
    private int yearOfBirth; // 1900..2010
    private GregorianCalendar dateOfBirth;
    
    /**
     * 
     * @param lastName
     * @param firstName
     * @param dayOfBirth
     * @param monthOfBirth
     * @param yearOfBirth
     */
    public Person1(String lastName, String firstName, int dayOfBirth, int monthOfBirth, int yearOfBirth)
    {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dayOfBirth =  dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth =  yearOfBirth;
        dateOfBirth = new GregorianCalendar(yearOfBirth, (monthOfBirth-1), dayOfBirth);
    }

    /**
     * 
     */
    public void printPerson()
    {
       System.out.println(firstName + " " + lastName + dayOfBirth + "." + monthOfBirth + "." + yearOfBirth);  
    }
    
    /**
     * 
     * @param day
     * @param month
     * @param year
     * @return
     */
    public int age(int day, int month, int year){
        int age = year - yearOfBirth;
        if(month < monthOfBirth || month == monthOfBirth && day < dayOfBirth){
            age--;
        }
        return age;
    }
    
    /**
     * 
     * @param year
     * @return
     */
    public boolean leapYear(int year){
        if(dateOfBirth.isLeapYear(year)){
            return true;
        } else return false;
    }
    
    /**
     * 
     * @param year
     */
    public void dayOfBirth(int year){
        int noOfYear = year - yearOfBirth;
        dateOfBirth.add(GregorianCalendar.YEAR, noOfYear);
        System.out.println("Before test: " + dateOfBirth.getTime());
        dateOfBirth.add(GregorianCalendar.YEAR, -noOfYear);
        System.out.println("After Test: " + dateOfBirth.getTime());
    }
    
}
