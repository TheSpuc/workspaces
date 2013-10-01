package assign425September;

public class PersonTest {

	public static void main(String[] args){
		CircularList<Person> list = new CircularList<>();
		list.addEntry(new Person("Jonas"));
		list.addEntry(new Person("Jonas"));
		list.addEntry(new Person("Lizette"));
		list.addEntry(new Person("Mark"));
		list.addEntry(new Person("Simon"));
		list.addEntry(new Person("Tomas"));
		list.addEntry(new Person("Martin"));
		list.addEntry(new Person("Nicolai"));
		list.addEntry(new Person("Margrethe"));
		list.addEntry(new Person("Christine"));
		list.addEntry(new Person("Casper"));
		list.addEntry(new Person("Niclas"));
		list.addEntry(new Person("Sig"));
		list.addEntry(new Person("Emil"));
		list.addEntry(new Person("Henrik"));
		list.addEntry(new Person("Vicki"));
		list.addEntry(new Person("Jeppe"));
		list.addEntry(new Person("Jakob"));
		list.addEntry(new Person("Hans"));
		list.addEntry(new Person("Michael"));
		list.addEntry(new Person("Tobias"));
		list.addEntry(new Person("Christian"));
		list.addEntry(new Person("Soeren"));
		list.addEntry(new Person("Stefan"));
		
		list.print();
		list.randomStart();
//		
		System.out.println(list.remove(100));
		System.out.println();
		list.print();
	}
}
