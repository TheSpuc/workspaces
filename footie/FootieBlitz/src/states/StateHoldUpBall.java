package states;

import model.Hjaelper;
import model.Player;

public class StateHoldUpBall implements PlayerState{

	private Player player;
	
	public StateHoldUpBall(Player player){
		this.player = player;
	}
	
	public void update() {
		
		player.setTargetSpeed(20);
		player.targetX = player.getBold().getX();
		player.targetY = player.getBold().getY();
		
		
		//TODO:
				//What are we doing while holding up the ball. When do we need to do something else?
		
		if (Hjaelper.Distance(player.getBold().getX(), player.getBold().getY(), player.getX(), player.getY()) < 8)
			player.setState(player.getStateNothing());
		
	}
	
	public String toString(){
		return "Holding up the ball";
	}
}
