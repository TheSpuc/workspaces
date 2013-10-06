package states;

import model.Player;

public class StateGoal implements PlayerState{

	private Player player;
	
	public StateGoal(Player player){
		this.player = player;
	}
	
	public void update() {
		if (player.getMyTeam() != player.getMatch().getSetPieceTeam()){

			if (player.getBold().getLastShot() != null){
				if (player == player.getBold().getLastShot()){
					player.targetY = player.getOppTeam().getGoalY() + 200;
					player.targetX = player.getOppTeam().getGoalX();
				}
				else{
					player.targetX = player.getBold().getLastShot().getX();
					player.targetY = player.getBold().getLastShot().getY();
				}
			}
			player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));

		}		
	}
	
	public String toString(){
		return "Mål";
	}
}
