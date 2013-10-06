package states;

import model.Hjaelper;
import model.Player;

public class StateRunWithBall implements PlayerState{

	private Player player;
	
	public StateRunWithBall(Player player){
		this.player = player;
	}
	
	public void update() {
		
		if (Hjaelper.Distance(player.getBold().getX(), player.getBold().getY(), player.x, player.y) < 8){
			player.getStateHasBall().update();
		}
		else{			
			//hvis det er for lang tid siden vi har rort bolden eller en anden har rørt bolden har vi mistet den
			if (System.currentTimeMillis() - player.lastdribble > 4000 || (player.getBold().getLastTouch() != null && !player.getBold().getLastTouch().equals(player))){
				player.setState(player.getStateNothing());
				player.getPlayerMatchStats().dribblesLost++;
			}
			else{
				player.targetX = player.getBold().getX();
				player.targetY = player.getBold().getY();
				
				player.targetSpeed = player.topSpeed * (0.50 + (player.dribbling / 250.0));
			}
		}
	}
	
	public String toString(){
		return "Løber med bolden";
	}
}
