package states;

import model.Player;

public class StateShooting implements PlayerState{

	private Player player;
	
	public StateShooting(Player player){
		this.player = player;
	}
	
	public void update() {
		//Efter skud går vi tilbage til Nothing og finder ud af hvad vi skal
		player.setState(player.getStateNothing());
	}
	
	public String toString(){
		return "Skyder";
	}
}
