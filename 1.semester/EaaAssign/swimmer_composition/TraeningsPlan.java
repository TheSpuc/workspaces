package swimmer_composition;

import java.util.ArrayList;

public class TraeningsPlan {
	private char niveau;
	private int ugentligVandTimer;
	private int ugentligStyrkeTimer;
	private ArrayList<Swimmer> svoemmer;

	public TraeningsPlan(char niveau, int ugentligVandTimer, int ugentligStyrkeTimer) {
		this.niveau = niveau;
		this.ugentligVandTimer = ugentligVandTimer;
		this.ugentligStyrkeTimer = ugentligStyrkeTimer;
		svoemmer = new ArrayList<>();
	}

	public ArrayList<Swimmer> getSvoemmer(){
		return new ArrayList<Swimmer>(svoemmer);
	}

	public void createSwimmer(String name, String club, int year, ArrayList<Double> times){
		Swimmer sw = new Swimmer(name, club, year, times);
		svoemmer.add(sw);
	}
	
	public void deleteSwimmer(Swimmer sw){
		if(svoemmer.contains(sw)){
			svoemmer.remove(sw);
		}
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
