import java.util.HashSet;
import java.util.Set;


public class PersonAdministration {

	private static PersonAdministration peradmin;
	private Set<Person> persons;
	
	private PersonAdministration(){
		persons = new HashSet<>();
	}
	
	public static PersonAdministration getInstance(){
		if(peradmin == null){
			peradmin = new PersonAdministration();
		}
		return peradmin;
	}
	
	public void addPerson(Person p){
		persons.add(p);
	}
	
	public void removePerson(Person p){
		persons.remove(p);
	}
	
	public Set<Person> getPersons(){
		return new HashSet<>(persons);
	}

}
