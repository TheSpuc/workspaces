package groupsOneToManyUniFK;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Person { // OneToMany (unidirectional - foreign key)
	@Id
	@GeneratedValue
	private int id;
	private String name;

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

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
