package groupsOneToOneUni;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group { // OneToOne (unidirectional)
	@Id
	@GeneratedValue
	private int id;
	private String name;
	
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

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
}
