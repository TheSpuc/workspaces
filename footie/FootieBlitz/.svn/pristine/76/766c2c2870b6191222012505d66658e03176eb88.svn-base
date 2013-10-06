package states;

import model.Player;

public class StateHTFT implements PlayerState{

	private Player player;
	
	public StateHTFT(Player player){
		this.player = player;
	}
	
	public void update() {
		player.urgent = true;
		if (player.getY() > player.getPitch().getPitchPosY() + 10){
			player.targetX = player.getPitch().getPitchPosX() + player.getPitch().getPitchWidth() / 2;
			player.targetY = player.getPitch().getPitchPosY();
			player.setTargetA(Math.atan2((player.targetX - player.getX()), (player.targetY - player.getY())));
		}
		else{
			player.targetX = player.getPitch().getPitchPosX() + player.getPitch().getPitchWidth() / 2;
			player.targetY = -300;

			player.setTargetA(Math.atan2((player.targetX - player.getX()), (player.targetY - player.getY())));
		}
	}

	public String toString(){
		return "Halvleg / fuld tid";
	}
}
