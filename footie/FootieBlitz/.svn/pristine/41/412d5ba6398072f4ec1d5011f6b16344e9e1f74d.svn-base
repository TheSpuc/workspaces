package states;

import model.Player;

public class StateClearing implements PlayerState{

	private Player player;
	
	public StateClearing(Player player){
		this.player = player;
	}
	
	public void update() {
		
		player.setState(player.getStateNothing());
	}
	
	public String toString(){
		return "Sparker væk";
	}
}
