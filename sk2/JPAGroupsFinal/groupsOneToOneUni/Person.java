package groupsOneToOneUni;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

@Entity
public class Person { // OneToOne (unidirectional)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToOne // default
	@JoinColumn(unique = true, nullable = false)
	private Group group;

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

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		this.group = group;
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + ", group=" + group + "]";
	}
}
