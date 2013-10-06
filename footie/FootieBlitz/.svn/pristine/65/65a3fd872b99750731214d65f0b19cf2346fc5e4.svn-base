package states;

import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.Team;
import model.TeamState;
import model.Match.MatchState;

public class StateCorner implements PlayerState{

	private Player player;
	private Bold bold;
	private Team myTeam;
	private Team oppTeam;
	private Match match;
	private Pitch pitch;
	private Random r = new Random();
	
	public StateCorner(Player player){
		this.player = player;
		bold = player.getBold();
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
		pitch = player.getPitch();
	}
	
	public void update() {

		if (match.getSetPieceTeam() == myTeam){

			boolean right = true;

			if (myTeam.getGoalX() < oppTeam.getGoalX()){
				if (match.getSetPieceY() < pitch.getPitchMidY()){
					right = false;
				}
			}
			else{
				if (match.getSetPieceY() > pitch.getPitchMidY()){
					right = false;
				}
			}

			Player taker = null;
			
			//Er ham der skal tage det ifølge taktikken på banen?
			if ((right && (myTeam.getpCornerRight() == null || !myTeam.getPlayers().contains(myTeam.getpCornerRight()))) || (!right && (myTeam.getpCornerLeft() == null || !myTeam.getPlayers().contains(myTeam.getpCornerLeft())))){
				//Niks, vi finder den nærmeste
				for (Player p : myTeam.getPlayers()){
					if (taker == null) taker = p;
					else if (Hjaelper.Distance(taker.posX, taker.posY, match.getSetPieceX(), match.getSetPieceY()) > Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()))
						taker = p;
				}
			}
			else{
				taker = myTeam.getpCornerRight();
				if (!right) taker = myTeam.getpCornerLeft();
			}

			//ham der tager hjørnet gaar til bolden
			if (player == taker){

				//Stiller sig bag bolden
				double ta = Math.atan2(match.getSetPieceX() - oppTeam.getGoalX(), match.getSetPieceY() - oppTeam.getGoalY() + 30);
				player.targetX = match.getSetPieceX() + (Math.sin(ta) * 20);
				player.targetY = match.getSetPieceY() + (Math.cos(ta) * 20);

				if (Hjaelper.Distance(player.targetX, player.targetY, player.x, player.y) > 30 || MatchEngine.waitForSubs)
					player.ready = System.currentTimeMillis();

				//Når vi har stået der lidt løber vi til bolden
				if (System.currentTimeMillis() - player.ready > 6000){
					//Løb hen til bolden til vi er tæt på
					if (Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) > 9){
						player.targetX = bold.getX();
						player.targetY = bold.getY();
					}
					else{

						Player target = null;
						for (Player f : myTeam.getPlayersByHeight())
							if (!f.isKeeper && !f.equals(this))
								if (target == null && f.forwardOnSetpieces && 
										Hjaelper.Distance(f.x, f.y, f.getOppTeam().getGoalX(), f.getOppTeam().getGoalY() + 30) < 100) target = f;

						if (target != null){
							player.getStateHasBall().getAiCross().cross(target, 1);
							bold.setSetpieceTaken(true);
						}
						else{
							player.getStateHasBall().getAiCross().cross(null, 1);
							bold.setSetpieceTaken(true);
						}

						match.setState(MatchState.ON);

						for (Player p : myTeam.getPlayers())
							p.bestemplads = false;
						for (Player p : oppTeam.getPlayers())
							p.bestemplads = false;
					}
				}


			}//de andre gaar i feltet eller placerer sig
			else{

				//dem der skal med frem
				if (player.forwardOnSetpieces){

					if (!player.bestemplads){
						if (myTeam.getGoalX() > oppTeam.getGoalX())
							player.targetX = oppTeam.getGoalX() + 90;
						else
							player.targetX = oppTeam.getGoalX() - 90;

						if (match.getSetPieceY() > pitch.getPitchPosY() + pitch.getPitchHeight() / 2)
							player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2 - 15;
						else
							player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2 + 15;

						player.pladsX = player.targetX;
						player.pladsY = player.targetY;
						player.bestemplads = true;
					}



					if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 9){
						int t = 20;
						if (r.nextDouble() < 0.5) t *= -1;
						player.targetX = player.pladsX + t / 2 + r.nextInt(25) - r.nextInt(25);
						player.targetY = player.pladsY + t + r.nextInt(50) - r.nextInt(50);
					}
					if (Hjaelper.Distance(player.x, player.y, player.pladsX, player.pladsY) < 30)
						player.targetSpeed = player.topSpeed / 3;
				}
				else{
					player.setPosition(myTeam, oppTeam);
					player.targetX = player.posX;
					player.targetY = player.posY;
				}

			}
		}
		else if (!player.isKeeper){ //forsvaret
			myTeam.setTeamState(TeamState.defend);
			
			//TODO: setMarkeringerSetPiece
//			myTeam.setMarkeringer(new ArrayList<Player>(), true);
			
			player.setTargetSpeed(player.topSpeed);
			if (player.getCurrentlyMarking().size() == 1){
				//hvor taet paa skal vi markere
				double d = 0.2 * Hjaelper.Distance(player.getCurrentlyMarking().get(0).x, player.getCurrentlyMarking().get(0).y, myTeam.getGoalX(), myTeam.getGoalY() + 30);
				double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
				d = d * c;
				if (d < 8) d = 8;

				//staa mellem manden og maalet
				double r = Math.atan2(myTeam.getGoalX() - bold.getX(), myTeam.getGoalY() + 30 - bold.getY());
				player.targetX = player.getCurrentlyMarking().get(0).x + d * Math.sin(r);
				player.targetY = player.getCurrentlyMarking().get(0).y + d * Math.cos(r);
			}
			else{
				if (!player.bestemplads){
					if (myTeam.getGoalX() > oppTeam.getGoalX())
						player.targetX = myTeam.getGoalX() - 90;
					else
						player.targetX = myTeam.getGoalX() + 90;

					if (match.getSetPieceY() > pitch.getPitchPosY() + pitch.getPitchHeight() / 2)
						player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2;
					else
						player.targetY = pitch.getPitchPosY() + pitch.getPitchHeight() / 2;

					player.pladsX = player.targetX;
					player.pladsY = player.targetY;
					player.bestemplads = true;
				}

				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 9){
					int t = 20;
					if (r.nextDouble() < 0.5) t *= -1;
					player.targetX = player.pladsX + t / 2 + r.nextInt(25) - r.nextInt(25);
					player.targetY = player.pladsY + t + r.nextInt(50) - r.nextInt(50);
				}
				if (Hjaelper.Distance(player.x, player.y, player.pladsX, player.pladsY) < 30)
					player.setTargetSpeed(player.topSpeed / 3);
			}

			//stå mindst 74 pixels fra bolden
			while (Hjaelper.Distance(match.getSetPieceX(), match.getSetPieceY(), player.targetX, player.targetY) < 74){
				double ta = Math.atan2(player.targetX - match.getSetPieceX(), player.targetY - match.getSetPieceY());
				player.targetX += 1 * Math.sin(ta);
				player.targetY += 1 * Math.cos(ta);
			}

			//en spiller bliver fremme
			if (myTeam.getPlayersFrontToBack().get(0).equals(this) && player.getCurrentlyMarking().size() == 0){
				player.targetSpeed = player.topSpeed / 5;
				if (!player.bestemplads){
					player.bestemplads = true;
					player.targetX = pitch.getPitchMidX() + 50;
					player.targetY = pitch.getPitchMidY();
					if (myTeam.getGoalX() < oppTeam.getGoalX()) player.targetX -= 100;
					player.targetX += r.nextInt(50) - r.nextInt(50);
					player.targetY += r.nextInt(300) - r.nextInt(300);
				}
			}

		}
		else{//forsvarende keeper
			player.targetX = myTeam.getGoalX();
			player.targetY = myTeam.getGoalY() + 30;
			//hvis keeper har bolden før den går ud, så skal han ikke have den med det samme når den går i spil
			player.hasBall = false;
		}
		player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));	
	}

	public String toString(){
		return "Hjørne";
	}
}
