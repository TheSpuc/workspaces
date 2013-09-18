package groupsManyToManyBi;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class Person { // ManyToMany (bidirectional)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@ManyToMany(mappedBy="persons")
	private List<Group> groups = new ArrayList<Group>();

	public Person(String name) {
		this.name = name;
	}

	public Person() {
	}

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Group> getGroups() {
		return groups;
	}

	public void addGroup(Group group) {
		if (!groups.contains(group)) {
			groups.add(group);
			group.addPerson(this);
		}
	}

	public void removeGroup(Group group) {
		if (groups.contains(group)) {
			groups.remove(group);
			group.removePerson(this);
		}
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
