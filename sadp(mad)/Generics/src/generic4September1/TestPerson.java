package generic4September1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TestPerson {

	public static void main(String[] args){
		Person<String> p = new Person<String>("Emil");
		System.out.println(p.getName());
		
		Person<Name> p2 = new Person<Name>(new Name("Emil", "The MAN!"));
		System.out.println(p2.getName());
		
		
		Person<Name> p3 = new Person<Name>(new Name("Charlie", ""));
		Person<Name> p4 = new Person<Name>(new Name("Billie", ""));
		Person<Name> p5 = new Person<Name>(new Name("Abemand", ""));
		
		List<Person<Name>> list = new ArrayList<>();
		list.add(p3);
		list.add(p4);
		list.add(p5);
		
		System.out.println(list);
		
		Collections.sort(list);
		
		System.out.println(list);
	}
}
