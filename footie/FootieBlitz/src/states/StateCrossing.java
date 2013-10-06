package states;

import model.Player;

public class StateCrossing implements PlayerState{

	private Player player;
	
	public StateCrossing(Player player){
		this.player = player;
	}
	
	public void update() {
		
		player.setState(player.getStateNothing());
	}
	
	public String toString(){
		return "Lægger ind over";
	}
}
