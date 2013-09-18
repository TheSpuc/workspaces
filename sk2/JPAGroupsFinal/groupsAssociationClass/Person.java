package groupsAssociationClass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Person { // Association class
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(mappedBy="person")
	private List<Join> joins = new ArrayList<Join>();

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

	public List<Join> getJoins() {
		return joins;
	}

	public void addJoin(Join join) {
		if (!joins.contains(join)) {
			joins.add(join);
			join.setPerson(this);
		}
	}

	public void removeJoin(Join join) {
		if (joins.contains(join)) {
			joins.remove(join);
			join.setPerson(null);
		}
	}

	@Override
	public String toString() {
		return "Person [id=" + id + ", name=" + name + "]";
	}
}
