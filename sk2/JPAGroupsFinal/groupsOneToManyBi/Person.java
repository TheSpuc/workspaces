package groupsOneToManyBi;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Person { // OneToMany (bidirectional)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@ManyToOne
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
		if (this.group != group) {
			if (this.group != null)
				this.group.removePerson(this);
			this.group = group;
			if (group != null)
				group.addPerson(this);
		}
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
