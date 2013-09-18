package groupsAssociationClass;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@IdClass(JoinId.class)
@Table(name="joins")
public class Join { // Association class
	@Id
	@ManyToOne
	private Person person;
	@Id
	@ManyToOne
	private Group group;
	@Temporal(TemporalType.TIMESTAMP)
	private Date start;

	public Join(Person person, Group group, Date start) {
		super();
		this.start = start;
		setPerson(person);
		setGroup(group);
	}

	public Join() {
		super();
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		if (this.person != person) {
			if (this.person != null)
				this.person.removeJoin(this);
			this.person = person;
			if (person != null)
				person.addJoin(this);
		}
	}

	public Group getGroup() {
		return group;
	}

	public void setGroup(Group group) {
		if (this.group != group) {
			if (this.group != null)
				this.group.removeJoin(this);
			this.group = group;
			if (group != null)
				group.addJoin(this);
		}
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	@Override
	public String toString() {
		return "Participation [person=" + person + ", group=" + group
				+ ", start=" + start + "]";
	}
}
