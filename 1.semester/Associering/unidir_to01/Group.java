package unidir_to01;

public class Group {
	
	private String name;

	/**
	 * Creates a new group.<br />
	 * Requires: name not empty.
	 */
	public Group(String name) {
		this.name = name;
	}

	/**
	 * Returns the name.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.<br />
	 * Requires: name not empty.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	//-----------------------------------------------------------------------------------

	
	

}
