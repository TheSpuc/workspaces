package bidir_manytomany;

public class Service
{

    public static void main(String[] args)
    {
        Person p1 = new Person("P1");
        Person p2 = new Person("P2");
        Group g1 = new Group("G1");
        Group g2 = new Group("G2");
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();

        g1.addPerson(p1);
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();

        g2.addPerson(p1);
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        
        g2.addPerson(p2);
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        
        g2.removePerson(p1);
        System.out.println("Person: " + p2);
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
    }

}
