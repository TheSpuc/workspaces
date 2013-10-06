package model;

public class Country {

	public int ID;
	public String name;
	public int avgActivePlayers;
	public int avgTalent;
	public int currentActivePlayers;
	
	public Country(int iD, String name, int avgActivePlayers, int avgTalent) {
		super();
		ID = iD;
		this.name = name;
		this.avgActivePlayers = avgActivePlayers;
		this.avgTalent = avgTalent;
	}
	
	public Country(int iD, String name, int avgActivePlayers, int avgTalent, int currentActivePlayers) {
		super();
		ID = iD;
		this.name = name;
		this.avgActivePlayers = avgActivePlayers;
		this.avgTalent = avgTalent;
		this.currentActivePlayers = currentActivePlayers;
	}
}
