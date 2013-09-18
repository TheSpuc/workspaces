package opg31001;

import java.util.ArrayList;

public class Team {

	private ArrayList<Player> team;
	private String name;
	
	public Team(String name){
		team = new ArrayList<Player>();
		this.name = name;
	}
	
	public String getName(){
		return name;
	}
	
	public void addPlayer(Player p){
		team.add(p);
	}
	
	public void printPlayers(){
		for(Player pl : team){
			System.out.println("Name: " + pl.getName() + ", Age: " + pl.getAge() + ", Score: " + pl.getScore());
		}
	}
	
	public double calAverageAge(){
		double result = 0;
		for(Player pl : team){
			result += pl.getAge();
		}
		return result/team.size();
	}
	
	public int calTotalScore(){
		int result = 0;
		for(Player p : team){
			result += p.getScore();
		}
		return result;
	}
	
	public int calOldPlayers(int ageLimit){
		int result = 0;
		for(Player p : team){
			if(p.getAge() >= ageLimit){
				result++;
			}
		}
		return result;
	}
	
	public int maxScore(){
		int result = 0;
		for(Player p : team){
			result += p.getScore();
		}
		return result;
	}
	
	public String bestPlayer(){
		String player = "";
		int result = 0;
		for(Player p : team){
			if(p.getScore() > result){
				player = p.getName();
			}
		}
		return player;
	}

}
