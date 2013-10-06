package states;

import java.awt.Point;

import model.Bold;
import model.Hjaelper;
import model.Player;
import model.PlayerAction;
import model.TeamState;

public class StateRunOffBall implements PlayerState{

	private Player player;
	private Bold bold;
	
	public StateRunOffBall(Player player){
		this.player = player;
		bold = player.getBold();
	}
	
	public void update() {
		
		player.targetSpeed = player.topSpeed;
		player.targetX = player.runTargetX;
		player.targetY = player.runTargetY;

		if (player.getMyTeam().getTeamState() == TeamState.defend){
			player.setPlayerAction(PlayerAction.nothing);
			player.setState(player.getStateNothing());
		}

		//hvis vi er hvor vi ville loebe hen eller paa offside graensen kan vi godt loebe tilbage (indtil vi laver noget mere intelligent
		if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 10 ||
				Math.abs(player.getOppTeam().getGoalX() - player.x) < player.getOppTeam().getOffsideDistToGoal()){
			player.setState(player.getStateNothing());
		}
		
		double tempX = bold.getX() + 10 * Math.sin(bold.getA());
		double tempY = bold.getY() + 10 * Math.cos(bold.getA());
		
		//hvis vi løber i vejen for bolden så gå væk
		if (Hjaelper.Distance(player.x, player.y, tempX, tempY) < 60 
				&& !bold.isShot() && !bold.isCross() && bold.getPassTo()==null){


			if (Hjaelper.Distance(bold.getLastTouch().x, bold.getLastTouch().y, player.x, player.y) > 10){

				double q = bold.getLastTouch().getA();
				q -= Math.PI;
				if (q > Math.PI * 2) q -= Math.PI * 2;
				else if(q < 0) q += Math.PI * 2;

				double t1x = bold.getLastTouch().x + 30 * Math.sin(q);
				double t1y = bold.getLastTouch().y + 30 * Math.cos(q);

				player.targetX = t1x;
				player.targetY = t1y;
			}
			
			double ta = Math.atan2(player.x - tempX, player.y - tempY);
			int safetyFirst = 0;
			while (Hjaelper.Distance(player.targetX, player.targetY, tempX, tempY) < 60 && safetyFirst < 50){
				player.targetX += 5 * Math.sin(ta);
				player.targetY += 5 * Math.cos(ta);
				safetyFirst++;

				if (player.getPitch().isAlmostOut((int)player.targetX, (int)player.targetY)){
					ta = Math.atan2(player.getOppTeam().getGoalX() - player.targetX, 0);
				}
			}
		}
		
		//den med- og modspiller der kan komme først til bolden
		Player opp = null;
		Player mate = null;
		for (Player p : player.getOppTeam().getPlayers())
			if (opp == null || p.getCountToBall() < opp.getCountToBall())
				opp = p;
		
		for (Player p : player.getMyTeam().getPlayers())
			if (mate == null || p.getCountToBall() < mate.getCountToBall())
				mate = p;

		if (bold.getPassTo() != null && bold.getPassTo().equals(player)){
			player.setState(player.getStateNothing());
		}
		
		//Hvis der ikke er en aflevering igang og vi kan komme først til bolden af vores hold tager vi den
		else if (bold.getPassTo() == null && bold.getLastTouch().getPlayerAction() != PlayerAction.dribble){
			if (mate.equals(player)){
				player.setPlayerAction(PlayerAction.chasingBall);
				player.setState(player.getStateNothing());
				Point p = bold.meetBall(player, true);
				player.targetX = p.x;
				player.targetY = p.y;
//				System.out.println(player.shirtNumber + " jagter bolden");
			}
		}//Hvis der er en aflevering igang, men modtageren ikke kan nå den før modstanderen tager vi den
		else if (bold.getPassTo() != null && bold.getPassTo().getCountToBall() > opp.getCountToBall()){
			if (mate.equals(player)){
				player.setPlayerAction(PlayerAction.chasingBall);
				player.setState(player.getStateNothing());
				Point p = bold.meetBall(player, true);
				player.targetX = p.x;
				player.targetY = p.y;
//				System.out.println(player.shirtNumber + " jagter bolden");
			}
		}
		
		//Er bolden ude eller har keeperen loeber vi paa plads
		if (bold.getHasBall() != null && bold.getHasBall().isKeeper() || bold.isUdenfor()){
			player.targetX = player.posX;
			player.targetY = player.posY;
		}
	}
	
	public String toString(){
		return "Laver et løb";
	}
}
