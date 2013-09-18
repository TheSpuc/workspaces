package groupsAssociationClass;

public class JoinId { // Association class
	private int person;
	private int group;

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + group;
		result = prime * result + person;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		JoinId other = (JoinId) obj;
		if (group != other.group)
			return false;
		if (person != other.person)
			return false;
		return true;
	}
}
