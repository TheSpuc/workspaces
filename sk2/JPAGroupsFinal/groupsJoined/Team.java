package groupsJoined;

import javax.persistence.Entity;

@Entity
public class Team extends Group{ // Joined
	private String task;

	public Team(String name, String task) {
		super(name);
		this.task = task;
	}

	public Team() {
		super();
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	@Override
	public String toString() {
		return "Team [task=" + task + ", toString()=" + super.toString() + "]";
	}
}
