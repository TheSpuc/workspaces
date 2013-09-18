
public class UsePersAdmin {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Person p1 = new Person("Niclas");
		Person p2 = new Person("Jeppe");
		Person p3 = new Person("Mark");
		
		PersonAdministration persadmin = PersonAdministration.getInstance();
		
		System.out.println("Empty: " + persadmin.getPersons());
		
		persadmin.addPerson(p1);
		
		System.out.println("1 add: " + persadmin.getPersons());
		
		persadmin.addPerson(p2);
		
		System.out.println("2 add: " + persadmin.getPersons());
		
		persadmin.removePerson(p1);
		
		System.out.println("1 remove: " + persadmin.getPersons());
		
	}

}
