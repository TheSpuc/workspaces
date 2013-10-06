package states;

import model.Hjaelper;
import model.Player;

public class StateDribbling implements PlayerState{

	private Player player;
	
	public StateDribbling(Player player){
		this.player = player;
	}
	
	public void update() {
		
		player.targetX = player.getBold().getX();
		player.targetY = player.getBold().getY();
		
		player.targetSpeed = player.topSpeed * (0.62 + (player.dribbling / 300.0));
		
		if (Hjaelper.Distance(player.getBold().getX(), player.getBold().getY(), player.x, player.y) < 8){
			player.getStateHasBall().update();
		}
		else{	
			
			//hvis det er for lang tid siden vi har rort bolden eller en anden har rørt bolden har vi mistet den
			if (player.getBold().getLastTouch() != null && !player.getBold().getLastTouch().equals(player)){
				player.setState(player.getStateNothing());
				player.getPlayerMatchStats().dribblesLost++;
			}
		}
	}
	
	public String toString(){
		return "Dribler";
	}
}
