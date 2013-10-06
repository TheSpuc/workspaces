package states;

import java.awt.Point;

import model.Player;
import model.PlayerAction;

public class StatePressuring implements PlayerState{

	
	private Player player;
	private Player targetPlayer;
	
	public StatePressuring(Player player){
		this.player = player;
	}
	
	public void setTargetPlayer(Player target){
		targetPlayer = target;
	}
	
	public void update() {
		//Vi presser indtil en anden har rørt bolden eller bolden er på vej til en anden eller en anden 
		//medspiller kommer først til bolden eller allerede rører den
		
		if (player.getBold().getFirstToBall().getMyTeam().equals(player.getMyTeam()) && 
				!player.getBold().getFirstToBall().equals(player)){
			
			player.setState(player.getStateNothing());
		}
		else if (player.getBold().getLastTouch().getMyTeam().equals(player.getMyTeam())){
			
			player.setState(player.getStateNothing());
		}
		else{
			//Hvis det er en aflevering der er på vej til ham vi skal presse går vi erfter der hvor han møder bolden
			//På dette tidspunkt kan vi ikke komme først til den
			if (player.getBold().getPassTo() != null && targetPlayer.equals(player.getBold().getPassTo())){
				player.targetX = player.getBold().getPassTo().x;
				player.targetY = player.getBold().getPassTo().y;
				//			System.out.println(player.getShirtNumber() + " Pressure pass");
			}
			else{
				//Ellers går vi simpelthen efter bolden
				Point p = player.getBold().meetBall(player, true);
				player.targetX = p.x;
				player.targetY = p.y;
				//			System.out.println(player.getShirtNumber() + " Pressure player");
			}
		}
		player.targetSpeed = player.topSpeed;
		player.setPlayerAction(PlayerAction.chasingBall);
		
		if (player.getBold().getPassTo() != null && !player.getBold().getPassTo().equals(targetPlayer)){
			player.setState(player.getStateNothing());
		}
		else if (player.getBold().getLastTouch() != null && !player.getBold().getLastTouch().equals(targetPlayer)){
			player.setState(player.getStateNothing());
		}
	}
	
}
