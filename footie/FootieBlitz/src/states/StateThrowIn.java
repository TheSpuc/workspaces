package states;

import java.awt.Point;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.PlayerRole;
import model.Sysout;
import model.Team;

public class StateThrowIn implements PlayerState{

	private Player player;
	private Team myTeam;
	private Team oppTeam;
	private Bold bold;
	private Pitch pitch;
	private Match match;
	Random r = new Random();
	
	public StateThrowIn(Player player){
		this.player = player;
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
		pitch = player.getPitch();
		bold = player.getBold();
	}
	
	public void update() {
		player.setPosition(player.getMyTeam(), player.getOppTeam());

		if (!player.isKeeper){
			player.targetX = player.posX;
			player.targetY = player.posY;
			//ryk over mod kasteren
			//			double ta = Math.atan2(match.getSetPieceX() - targetX, match.getSetPieceY() - targetY);
			//			targetX += (Hjaelper.Distance(targetX, targetY, match.getSetPieceX(), match.getSetPieceY()) / 10) * Math.sin(ta);
			//			targetY += (Hjaelper.Distance(targetX, targetY, match.getSetPieceX(), match.getSetPieceY()) / 5) * Math.cos(ta);
			if (match.getSetPieceY() < player.targetY){
				player.targetY -=(Hjaelper.Distance(0, player.targetY, 0, match.getSetPieceY()) / 5);
			}
			else player.targetY +=(Hjaelper.Distance(0, player.targetY, 0, match.getSetPieceY()) / 5);

			if (player.x == player.targetX && player.y == player.targetY) player.targetA = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
			else player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
		}

		if (match.getSetPieceTeam() == myTeam){
//			System.out.println("Det er mit hold: " + player.getFirstname() + "(" + player.getShirtNumber() + ")");
		
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

			//The goalkeeper cannot take freekicks at the moment
			if(myTeam.getpThrowinRight() != null && myTeam.getpThrowinRight().getRole() == PlayerRole.GK){
				myTeam.setpThrowinRight(null);
			}
			if(myTeam.getpThrowinLeft() != null && myTeam.getpThrowinLeft().getRole() == PlayerRole.GK){
				myTeam.setpThrowinLeft(null);
			}
			//Er ham der skal tage det ifølge taktikken på banen?
			if ((right && (myTeam.getpThrowinRight() == null || !myTeam.getPlayers().contains(myTeam.getpThrowinRight()))) || 
					(!right && (myTeam.getpThrowinLeft() == null || !myTeam.getPlayers().contains(myTeam.getpThrowinLeft())))){
				//Niks, vi finder den nærmeste
				for (Player p : myTeam.getPlayers()){
					if (taker == null) taker = p;
					else if (Hjaelper.Distance(taker.posX, taker.posY, match.getSetPieceX(), match.getSetPieceY()) > Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()))
						taker = p;
				}
			}
			else{
				taker = myTeam.getpThrowinRight();
				if (!right) taker = myTeam.getpThrowinLeft();
			}
			
			
			
			if (taker == player){

				player.targetX = match.getSetPieceX();
				player.targetY = match.getSetPieceY();
				
				if (match.getSetPieceY() < pitch.getPitchMidY()){
					player.targetY = match.getSetPieceY() - 6;
				}
				else
					player.targetY = match.getSetPieceY() + 6;

				if (player.x == player.targetX && player.y == player.targetY) player.targetA = Math.atan2((match.getSetPieceX() - player.x), (match.getSetPieceY() - player.y));
				else player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
				
				//er jeg klar?
				if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) > 9 || Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) > 9 || MatchEngine.waitForSubs){
					player.ready = System.currentTimeMillis();

				}
				else{
					//find narmeste medspiller
					Player modtager = null;
					for (Player p : myTeam.getPlayers()){

						if (p != player){

							//find den der er tattest paa
							if (modtager == null) modtager = p;
							else{
								if (Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) <  
										Hjaelper.Distance(player.x, player.y, modtager.getX(), modtager.getY()) && !pitch.isOut((int)p.x, (int)p.y))
									modtager = p;
							}
						}
					}

					player.speed = 0;

					if (System.currentTimeMillis() - player.ready > 2000 + r.nextInt(2000)){
						bold.setZ(player.getHeight());
						bold.setX(player.x);
						player.getStateHasBall().getAiPass().pass(modtager, false, 1, new Point((int)modtager.getX(), (int)modtager.getY()), 1, 1);
						bold.setZForce(15);
						bold.setSetPieceTime(System.currentTimeMillis());
						bold.setSetpieceTaken(true);
						player.wait = 500;
						player.ready = System.currentTimeMillis();
						for (Player p : myTeam.getPlayers())
							p.bestemplads = false;
					}
				}

			}
			else{// dem der ikke tager indkastet - tre rykker over og hjælper (hvis egen halvdel rykker kun to over)

				Player p1 = null;
				Player p2 = null;
				Player p3 = null;

				for (Player p : myTeam.getPlayers()){
					if (!p.equals(taker) && !p.isKeeper){
						if (p1 == null) p1 = p;
						else if (p2 == null) p2 = p;
						else if (p3 == null) p3 = p;
						else if (Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()) < 
								Hjaelper.Distance(p1.x, p1.y, match.getSetPieceX(), match.getSetPieceY())) p1 = p;
						else if (Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()) < 
								Hjaelper.Distance(p2.x, p2.y, match.getSetPieceX(), match.getSetPieceY())) p2 = p;
						else if (Hjaelper.Distance(p.posX, p.posY, match.getSetPieceX(), match.getSetPieceY()) < 
								Hjaelper.Distance(p3.x, p3.y, match.getSetPieceX(), match.getSetPieceY())) p3 = p;
					}
				}

				if (player.equals(p1) || player.equals(p2) || (player.equals(p3) && Math.abs(match.getSetPieceX() - myTeam.getGoalX()) > 400)){
					if (!player.bestemplads){

						double rd = r.nextDouble() * 0.2;
						if (r.nextBoolean()) rd *= -1;

						double ta = Math.atan2(oppTeam.getGoalX() - match.getSetPieceX(), oppTeam.getGoalY() - match.getSetPieceY()) + rd;
						if (player.equals(p2))
							ta = Math.atan2(myTeam.getGoalX() - match.getSetPieceX(), myTeam.getGoalY() - match.getSetPieceY()) + rd;
						else if(player.equals(p3))
							ta = Math.atan2(0, pitch.getPitchMidY() - match.getSetPieceY()) + rd;

						int dist = r.nextInt(40);
						player.pladsX = match.getSetPieceX() + Math.sin(ta) * 100 + dist;
						player.pladsY = match.getSetPieceY() + Math.cos(ta) * 100 + dist;

						if (player.pladsY > pitch.getPitchPosY() + pitch.getPitchHeight()) player.pladsY = pitch.getPitchPosY() + pitch.getPitchHeight() - 30;
						if (player.pladsY < pitch.getPitchPosY()) player.pladsY = pitch.getPitchPosY()+ 30;
						if (player.pladsX > pitch.getPitchPosX() + pitch.getPitchWidth()) player.pladsX = pitch.getPitchPosX() + pitch.getPitchWidth() - 30;
						if (player.pladsX < pitch.getPitchPosX()) player.pladsX = pitch.getPitchPosX() + 30;
						player.bestemplads = true;
					}

					player.targetX = player.pladsX;
					player.targetY = player.pladsY;

					if (Hjaelper.Distance(player.x, player.y, player.targetX, player.targetY) < 9 && Hjaelper.Distance(taker.x, taker.y, taker.targetX, taker.targetY) < 10)
						player.bestemplads = false;

					player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
				}
			}
		}
		else{// forsvar
			if (player.getCurrentlyMarking().size() == 1){
				//hvor taet paa skal vi markere
				double d = 0.2 * Hjaelper.Distance(player.getCurrentlyMarking().get(0).x, player.getCurrentlyMarking().get(0).y, player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30);
				double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
				d = d * c;
				if (d < 8) d = 8;

				//staa mellem manden og maalet
				double r = Math.atan2(myTeam.getGoalX() - bold.getX(), myTeam.getGoalY() + 30 - bold.getY());
				player.targetX = player.getCurrentlyMarking().get(0).x + d * Math.sin(r);
				player.targetY = player.getCurrentlyMarking().get(0).y + d * Math.cos(r);

			}
			player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
		}
		
		if (player.getRole().equals(PlayerRole.GK) || player.isKeeper){
			player.targetX = myTeam.getGoalX();
			player.targetY = myTeam.getGoalY() + 30;
		}		
	}

	public String toString(){
		return "Indkast";
	}
}
