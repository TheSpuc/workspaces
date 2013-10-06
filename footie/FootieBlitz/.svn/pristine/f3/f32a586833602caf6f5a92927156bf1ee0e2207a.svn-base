package states;

import model.Player;

public class StatePassing implements PlayerState{

	private Player player;
	
	public StatePassing(Player player){
		this.player = player;
	}
	
	public void update() {
		//Efter afleveringer går vi tilbage til Nothing og finder ud af hvad vi skal
		
		//Tjek om ikke vi skal lave løb fremad her.
		player.setState(player.getStateNothing());
	}
	
	public String toString(){
		return "Afleverer";
	}
}
