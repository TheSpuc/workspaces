package unidir_1tomanyaggregering;

public class Service
{

    public static void main(String[] args)
    {
        Group g1 = new Group("G1");
        Group g2 = new Group("G2");

        Person p1 = g1.createPerson("P1");
        Person p2 = g2.createPerson("P2");
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();

        movePerson(g2,g1,p2);
        System.out.println("Person: " + p1);
        System.out.println("Person: " + p2);
        System.out.println("Group: " + g1);
        System.out.println("Group: " + g2);
        System.out.println();
    }
    
    static void movePerson(Group glGruppe, Group nyGruppe, Person p){
    	glGruppe.removePerson(p);
    	nyGruppe.addPerson(p);
    }

}
