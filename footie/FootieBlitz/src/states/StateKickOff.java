package states;

import java.awt.Point;
import java.util.Random;

import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.Match.MatchState;

public class StateKickOff implements PlayerState{

	private Player player;
	private Pitch pitch;
	private Random r = new Random();
	
	public StateKickOff(Player player){
		this.player = player;
		pitch = player.getPitch();
	}
	
	public void update() {

		player.urgent = true;
		player.targetX = player.getStartPosX();

		if (Math.abs(player.targetX - player.getMyTeam().getGoalX()) > pitch.getPitchWidth() / 2)
			player.targetX = pitch.getPitchMidX();
		
		player.targetY = player.getStartPosY();
		Player p1 = null, p2 = null;
		//staa uden for midtercirklen hvis vi ikke giver bolden op.

		int midtx = pitch.getPitchPosX() + pitch.getPitchWidth() / 2;
		int midty = pitch.getPitchPosY() + pitch.getPitchHeight() / 2;

		while (Hjaelper.Distance(midtx, midty, player.targetX, player.targetY) < 90){
			double ta = Math.atan2(player.targetX - midtx, player.targetY - midty);
			player.targetX += 1 * Math.sin(ta);
			player.targetY += 1 * Math.cos(ta);
		}

		if (player.getMatch().getSetPieceTeam() == player.getMyTeam()){
			for (Player p : player.getMyTeam().getPlayers()){
				if (p1 == null) 
					p1 = p;
				else{
					if (Hjaelper.Distance(p.getX(), p.getY(), player.getBold().getX(), player.getBold().getY()) < Hjaelper.Distance(p1.getX(), p1.getY(), player.getBold().getX(), player.getBold().getY()))				
						p1 = p;
				}}
			for (Player p : player.getMyTeam().getPlayers()){
				if (p2 == null && !p.equals(p1)) 
					p2 = p;	
				else if (p2 != null && !p.equals(p1) && Hjaelper.Distance(p.getX(), p.getY(), player.getBold().getX(), player.getBold().getY()) < Hjaelper.Distance(p2.getX(), p2.getY(), player.getBold().getX(), player.getBold().getY()))
					p2 = p;
			}


			if (p1.equals(player)){

				if (p1.getStartPosY() < p2.getStartPosY()){
					player.targetY = pitch.getPitchPosY() + (pitch.getPitchHeight() / 2) - 30;
					player.targetX = pitch.getPitchMidX() + 10;
				}
				else{
					player.targetY = pitch.getPitchPosY() + (pitch.getPitchHeight() / 2) + 5;
					player.targetX = pitch.getPitchMidX() + 1;
				}

			}
			else if (p2.equals(player)){

				if (p1.getStartPosY() < p2.getStartPosY()){
					player.targetY = pitch.getPitchPosY() + (pitch.getPitchHeight() / 2) + 5;
					player.targetX = pitch.getPitchMidX() + 1;
				}
				else{
					player.targetY = pitch.getPitchPosY() + (pitch.getPitchHeight() / 2) - 30;
					player.targetX = pitch.getPitchMidX() + 10;
				}

			}

			//er alle klar?
			if ((Hjaelper.Distance(p1.getX(), p1.getY(), p1.getTargetX(), p1.getTargetY()) > 10 ||
					Hjaelper.Distance(p2.getX(), p2.getY(), p2.getTargetX(), p2.getTargetY()) > 10) ||
					(Hjaelper.Distance(p1.getX(), p1.getY(), player.getBold().getX(), player.getBold().getY()) > 15 && 
							Hjaelper.Distance(p2.getX(), p2.getY(), player.getBold().getX(), player.getBold().getY()) > 15)) {

				player.ready = System.currentTimeMillis();

			}

			//giv bolden op n[r alle er klar og efter lidt tid
			if (System.currentTimeMillis() - player.ready > 2000 + r.nextInt(2000)){
				boolean oppTeamReady = true;
				for (Player p : player.getOppTeam().getPlayers())
					if (Hjaelper.Distance(p.getX(), p.getY(), p.getTargetX(), p.getTargetY()) > 20)
						oppTeamReady = false;

				if(oppTeamReady){

					//						bold.setPassTo(p1);
					//						double ta = Math.atan2((pitch.getPitchPosX() + pitch.getPitchWidth() / 2 - 8 - bold.getX()), 
					//								(pitch.getPitchPosY() + (pitch.getPitchHeight() / 2) - 50 - bold.getY()));
					//						bold.setA(ta);
					//						bold.setSpeed(70);



					if (p1.getY() < p2.getY()){
						if (player.equals(p2)){
							player.getMatch().setState(MatchState.ON);
							player.getMatch().setHalfRunning(true);
							player.getBold().setMaal(false);
							player.getStateHasBall().getAiPass().pass(p1, false, 0, new Point((int)p1.getX(), (int)p1.getY()), 1, 1);
							player.wait = 40;
						}
					}
					else
						if (player.equals(p1)){
							player.getMatch().setState(MatchState.ON);
							player.getMatch().setHalfRunning(true);
							player.getBold().setMaal(false);
							player.getStateHasBall().getAiPass().pass(p2, false, 0, new Point((int)p2.getX(), (int)p2.getY()), 1, 1);
							player.wait = 40;
						}

				}
			}
		}
		if (p1 != null && p2 != null){
		}
		player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));

		//						if (p1 != this && p2 != this && speed<10)//virker ikke endnu
		//						targetA =  Math.atan2((bold.getX() - x), (bold.getY() - y));

	}

	public String toString(){
		return "Kick off";
	}
}
