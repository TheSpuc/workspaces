package states;

import model.Player;

public class StateBeforeMatch implements PlayerState{

	private Player player;
	
	public StateBeforeMatch(Player player){
		this.player = player;
	}
	
	public void update() {
		player.urgent = true;
		if (player.getY() < player.getPitch().getPitchPosY() + 10){
			player.targetX = player.getX();
			player.targetY = player.getPitch().getPitchPosY() + 50;
			player.setTargetA(Math.atan2((player.targetX - player.getX()), (player.targetY - player.getY())));
		}
		else{
			player.targetX = player.getStartPosX();
			player.targetY = player.getStartPosY();

			if (player.getX() == player.targetX && player.getY() == player.targetY)
				player.setTargetA(Math.atan2((player.getBold().getX() - player.getX()), (player.getBold().getY() - player.getY())));
		}		
	}

	public String toString(){
		return "Before match";
	}
}
