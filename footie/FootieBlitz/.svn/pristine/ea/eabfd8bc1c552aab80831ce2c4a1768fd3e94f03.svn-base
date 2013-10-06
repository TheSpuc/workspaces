package states;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.StateOppHasBall;
import model.Team;
import model.TeamState;
import model.Match.MatchState;

public class StateGoalKick implements PlayerState{

	private Player player;
	private Bold bold;
	private Team myTeam;
	private Team oppTeam;
	private Match match;
	private Random r = new Random();
	
	public StateGoalKick(Player player){
		this.player = player;
		bold = player.getBold();
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
	}
	
	public void update() {
		player.targetX = player.posX;
		player.targetY = player.posY;

		player.targetSpeed = player.topSpeed;

		if (match.getSetPieceTeam() == myTeam){

			myTeam.setTeamState(TeamState.mid);
			Player keeper = myTeam.getKeeper();

			if (keeper == player){
				
				player.targetX = match.getSetPieceX();
				player.targetY = match.getSetPieceY();

				//er han klar?
				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) > 10 || MatchEngine.waitForSubs){

					player.ready = System.currentTimeMillis();
					player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
				}
				else{

					if (System.currentTimeMillis() - player.ready > 6500 + r.nextInt(2000)){
						if (Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) < 8) {
							
							Player frontPlayer = null;
							
							ArrayList<Player> visibleTeammates = new ArrayList<Player>();
							ArrayList<Player> visibleOpponents = new ArrayList<Player>();

							for (Player p : myTeam.getPlayers()){

								if (frontPlayer == null || 
										Hjaelper.Distance(bold.getX(), bold.getY(), frontPlayer.x, frontPlayer.y) < Hjaelper.Distance(bold.getX(), bold.getY(), p.x, p.y))
									frontPlayer = p;
									
								if (p != player){
									double retningTilMedspiller = Math.atan2(p.getY() - player.y, p.getX() - player.x);
									double vinkelForskel = Hjaelper.angleDifference(player.getA(), retningTilMedspiller) * 10;
									double afstand = Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) / 20 - 10;
									double begraensetUdsyn = (player.vision + 100) / 1.5 - (vinkelForskel + afstand);

									if (begraensetUdsyn > 95) begraensetUdsyn = 95;

									//								System.out.println("udsyn: " + begraensetUdsyn + ", afstand: " + afstand + ", vinkel: " +vinkelForskel);
									//midlertidig for at tage alle med uanset udsyn
									begraensetUdsyn = 900;

									if (begraensetUdsyn > r.nextInt(100) + 1)
										visibleTeammates.add(p);
								}
							}
							for (Player p : oppTeam.getPlayers()){

								if (p != player){
									double retningTilMedspiller = Math.atan2(p.getY() - player.y, p.getX() - player.x);
									double vinkelForskel = Hjaelper.angleDifference(player.getA(), retningTilMedspiller) * 10;
									double afstand = Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) / 20;
									double begraensetUdsyn = (player.vision + 100) / 1.4 - (vinkelForskel + afstand);

									if (begraensetUdsyn > 95) begraensetUdsyn = 95;

									//								System.out.println("udsyn: " + begraensetUdsyn + ", afstand: " + afstand + ", vinkel: " +vinkelForskel);
									//midlertidig for at tage alle med uanset udsyn
									begraensetUdsyn = 900;

									if (begraensetUdsyn > r.nextInt(100) + 1 || 
											Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) < 30)
										visibleOpponents.add(p);
								}
							}
							
							double scorePass = player.getStateHasBall().getAiPass().getScore(0, visibleTeammates, visibleOpponents, player.getOppTeam().getThroughLine(player.getPitch()), 100);
							
							if (scorePass > 50 + r.nextInt(60)){
								player.getStateHasBall().getAiPass().pass(1, 1, 1);//boldDif skal indsættes istedet for 1
								bold.setSetpieceTaken(true);
								match.setState(MatchState.ON);
								keeper.setHasBall(false);
								bold.setHasBall(null);	
								player.wait = 500;
							}
							else{
								player.getStateHasBall().getAiPass().pass(frontPlayer, true, 1, new Point((int)frontPlayer.getX(), (int)frontPlayer.getY()), 1, 1);
								bold.setShot(false);
								bold.setSetpieceTaken(true);
								match.setState(MatchState.ON);
								keeper.setHasBall(false);
								bold.setHasBall(null);
								player.wait = 500;
							}
						}
					}
					else if(Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) < 8){
						bold.setZ(5);
						bold.setX(player.x);
						bold.setY(player.y);
					}
				}
			}
		}
		else if (!player.isKeeper){
			if (player.getPlayerAction().equals(PlayerAction.chasingBall) || player.getPlayerAction().equals(PlayerAction.goForBall) || player.getPlayerAction().equals(PlayerAction.pressure))
				player.setPlayerAction(PlayerAction.nothing);
			
			myTeam.setTeamState(TeamState.mid);
			StateOppHasBall s = new StateOppHasBall(player);
			s.update();
		}

		player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
	}

	public String toString(){
		return "Målspark";
	}
}
