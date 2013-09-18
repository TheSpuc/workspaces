package groupsAssociationClass;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="groups")
public class Group { // Association class
	@Id
	@GeneratedValue
	private int id;
	private String name;
	@OneToMany(mappedBy = "group")
	private List<Join> joins = new ArrayList<Join>();

	public Group(String name) {
		this.name = name;
	}

	public Group() {
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
			join.setGroup(this);
		}
	}

	public void removeJoin(Join join) {
		if (joins.contains(join)) {
			joins.remove(join);
			join.setGroup(null);
		}
	}

	@Override
	public String toString() {
		return "Group [id=" + id + ", name=" + name + "]";
	}
}
