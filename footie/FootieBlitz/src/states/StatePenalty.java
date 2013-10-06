package states;

import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.PlayerRole;
import model.Team;
import model.Match.MatchState;

public class StatePenalty implements PlayerState{

	private Player player;
	private Bold bold;
	private Team myTeam;
	private Team oppTeam;
	private Match match;
	private Pitch pitch;
	private Random r = new Random();
	
	public StatePenalty(Player player){
		this.player = player;
		bold = player.getBold();
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
		pitch = player.getPitch();
	}
	
	public void update() {
		//Straffe til mit hold
		if (match.getSetPieceTeam() == myTeam){

			Player taker = null;
			
			//The goalkeeper cannot take penalties at the moment. 
			if(myTeam.getpPenalty() != null && myTeam.getpPenalty().getRole() == PlayerRole.GK){
				myTeam.setpPenalty(null);
			}
			
			//Er ham der skal tage det ifølge taktikken på banen?
			if (myTeam.getpPenalty() == null || !myTeam.getPlayers().contains(myTeam.getpPenalty())){
				//Niks, vi finder den med mest i shooting
				for (Player p : myTeam.getPlayers()){
					if (taker == null) taker = p;
					else if (taker.getShooting() < p.getShooting()) 
						taker = p;
				}
			}
			else{
				taker = myTeam.getpPenalty();
			}
			
			if (taker.equals(player)){
				if (match.getSetPieceX() > pitch.getPitchWidth() / 2){
					player.targetX = match.getSetPieceX() - 20;
				}
				else{
					player.targetX = match.getSetPieceX() + 20;
				}
				player.targetY = match.getSetPieceY();
				player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));

				//hvis keeperen eller taker ikke er på plads venter vi
				if (Hjaelper.Distance(oppTeam.getKeeper().getX(), oppTeam.getKeeper().getY(), oppTeam.getKeeper().getTargetX(), oppTeam.getKeeper().getTargetY()) > 8 ||
						Hjaelper.Distance(taker.getX(), taker.getY(), taker.getTargetX(), taker.getTargetY()) > 25 || MatchEngine.waitForSubs)
					player.ready = System.currentTimeMillis();

				if (System.currentTimeMillis() - player.ready > 4000){

					player.targetX = bold.getX();
					player.targetY = bold.getY();
					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));

					if (Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) < 9){

						player.getStateHasBall().getAiShoot().getScore(1, 1, 1);
						player.getStateHasBall().getAiShoot().shoot(true, 1, 1, 1);
						bold.setPenalty(true);
						match.setState(MatchState.ON);
					}
				}

			}
			//Tre bagerste spillere blive hjemme
			else{

				int s = myTeam.getPlayersFrontToBack().size();
				if (player == myTeam.getPlayersFrontToBack().get(s-1) || 
						player == myTeam.getPlayersFrontToBack().get(s-2) || 
						player == myTeam.getPlayersFrontToBack().get(s-3)){
					player.targetX = player.posX;
					player.targetY = player.posY;
					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
					if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
						player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
				}

				//alle andre stiller sig uden for feltet
				else{
					if (match.getSetPieceX() > pitch.getPitchWidth() / 2){
						player.targetX = match.getSetPieceX() - 44;
						player.targetY = player.posY;
						player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
						if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
							player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
					}
					else{
						player.targetX = match.getSetPieceX() + 44;
						player.targetY = player.posY;
						player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
						if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
							player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
					}
					while (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), player.targetX, player.targetY) < 80){
						double ta = Math.atan2(player.targetX - match.getSetPieceX(), player.targetY - match.getSetPieceY());
						player.targetX += 1 * Math.sin(ta);
						player.targetY += 1 * Math.cos(ta);
					}
					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
				}
			}

		}
		else{// forsvarende hold ved straffe
			//To forreste spillere bliver fremme
			if (player == myTeam.getPlayersFrontToBack().get(0) || 
					player == myTeam.getPlayersFrontToBack().get(1)){
				player.targetX = player.posX;
				player.targetY = player.posY;
				player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
					player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
			}
			else if (player.getRole() == PlayerRole.GK){

				player.targetX = myTeam.getGoalX();
				player.targetY = myTeam.getGoalY() + 30;

				player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));

				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
					player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
			}
			//alle andre stiller sig uden for feltet
			else{
				if (match.getSetPieceX() > pitch.getPitchWidth() / 2){
					player.targetX = match.getSetPieceX() - 44;
					player.targetY = player.posY;
					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
					if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
						player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
				}
				else{
					player.targetX = match.getSetPieceX() + 44;
					player.targetY = player.posY;
					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
					if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 8)
						player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
				}
				while (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), player.targetX, player.targetY) < 80){
					double ta = Math.atan2(player.targetX - match.getSetPieceX(), player.targetY - match.getSetPieceY());
					player.targetX += 1 * Math.sin(ta);
					player.targetY += 1 * Math.cos(ta);
				}
				player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
			}
		}
	}

	public String toString(){
		return "Straffe";
	}
}
