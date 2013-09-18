package groupsOneToManyUniFK;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group { // OneToMany (unidirectional - foreign key)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany // default
	@JoinColumn
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
		}
	}

	public void removePerson(Person person) {
			persons.remove(person);
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
}
