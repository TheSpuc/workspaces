package swimmerVersion1_unidi;

public class TraeningsPlan {
	private char niveau;
	private int ugentligVandTimer;
	private int ugentligStyrkeTimer;
	
	public TraeningsPlan(char niveau, int ugentligVandTimer, int ugentligStyrkeTimer) {
		this.niveau = niveau;
		this.ugentligVandTimer = ugentligVandTimer;
		this.ugentligStyrkeTimer = ugentligStyrkeTimer;
	}
	public char getNiveau() {
		return niveau;
	}
	public void setNiveau(char niveau) {
		this.niveau = niveau;
	}
	public int getUgentligStyrkeTimer() {
		return ugentligStyrkeTimer;
	}
	public void setUgentligStyrkeTimer(int ugentligStyrkeTimer) {
		this.ugentligStyrkeTimer = ugentligStyrkeTimer;
	}
	public int getUgentligVandTimer() {
		return ugentligVandTimer;
	}
	public void setUgentligVandTimer(int ugentligVandTimer) {
		this.ugentligVandTimer = ugentligVandTimer;
	}
	
}
