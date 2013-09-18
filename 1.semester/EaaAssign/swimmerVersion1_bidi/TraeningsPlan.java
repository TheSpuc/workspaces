package swimmerVersion1_bidi;

import java.util.ArrayList;

public class TraeningsPlan {
	private char niveau;
	private int ugentligVandTimer;
	private int ugentligStyrkeTimer;
	private ArrayList<Swimmer> swimmers;

	public TraeningsPlan(char niveau, int ugentligVandTimer, int ugentligStyrkeTimer) {
		this.niveau = niveau;
		this.ugentligVandTimer = ugentligVandTimer;
		this.ugentligStyrkeTimer = ugentligStyrkeTimer;
		swimmers = new ArrayList<>();
	}

	public void addSwimmer(Swimmer sw){
		if(!swimmers.contains(sw)){
			swimmers.add(sw);
			sw.setTraeningsPlan(this);
		}
	}
	public void removeSwimmmer(Swimmer sw){
		if(swimmers.contains(sw)){
			swimmers.remove(sw);
			sw.setTraeningsPlan(null);
		}
	}
	public String toString(){
		return niveau + ", " + swimmers;
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
	
	public ArrayList<Swimmer> getSwimmers(){
		return new ArrayList<Swimmer>(swimmers);
	}

}
