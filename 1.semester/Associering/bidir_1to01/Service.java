package bidir_1to01;

public class Service
{

    public static void main(String[] args)
    {
        

        Group g1 = new Group("G1");
        Group g2 = new Group("G2");
        Person p1 = new Person("P1", g1);
        
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();

        g2.setPerson(p1);
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();
        
        g2.setPerson(null);
        System.out.println("Person: " + p1);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();
    }

   

}
