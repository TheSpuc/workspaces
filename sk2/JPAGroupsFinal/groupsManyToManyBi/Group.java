package groupsManyToManyBi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group { // ManyToMany (bidirectional)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@ManyToMany
	private List<Person> persons = new ArrayList<Person>();
	
	public Group(String name) {
		this.name = name;
	}

	public Group() {}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Person> getPersons() {
		return persons;
	}

	public void addPerson(Person person) {
		if (!persons.contains(person)) {
			persons.add(person);
			person.addGroup(this);
		}
	}

	public void removePerson(Person person) {
		if (persons.contains(person)) {
			persons.remove(person);
			person.removeGroup(this);
		}
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
}
