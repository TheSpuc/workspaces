package searchAssign;

public class Player {

	private String name;
	private int height;
	private int weight;
	private int numberOfGoals;
	
	public Player(String name, int height, int weight, int numberOfGoals){
		this.name = name;
		this.height = height;
		this.weight = weight;
		this.numberOfGoals = numberOfGoals;
	}
	
	public String getName(){
		return name;
	}
	
	public int getHeight(){
		return height;
	}
	
	public int getWeight(){
		return height;
	}
	
	public int getGoals(){
		return numberOfGoals;
	}
	
	public String toString(){
		return name;
	}
}
