package unidir_to01;

public class Service {

    public static void main(String[] args) {
        Group g1 = new Group("G1");
        Person p1 = new Person("P1");
        p1.setGroup(g1);

        System.out.println("Group: " + g1);
        System.out.println("Person: " + p1);
        System.out.println();

        Group g2 = new Group("G2");
        Person p2 = new Person("P2");
        p2.setGroup(g2);

        System.out.println("Group: " + g2);
        System.out.println("Person: " + p2);
        System.out.println();

        p1.setGroup(g2);
        p2.setGroup(g1);

        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        
        p1.setGroup(null);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        

    }

}
