package states;

import java.awt.Point;
import java.util.Random;

import javax.print.attribute.standard.MediaSize.Engineering;

import model.Bold;
import model.Hjaelper;
import model.Match;
import model.MatchEngine;
import model.Pitch;
import model.Player;
import model.PlayerRole;
import model.Team;
import model.Match.MatchState;

public class StatePenaltyShootout implements PlayerState{

	private Player player;
	private Bold bold;
	private Team myTeam;
	private Team oppTeam;
	private Match match;
	private Pitch pitch;
	private Random r = new Random();
	
	public StatePenaltyShootout(Player player){
		this.player = player;
		bold = player.getBold();
		myTeam = player.getMyTeam();
		oppTeam = player.getOppTeam();
		match = player.getMatch();
		pitch = player.getPitch();
	}
	
	public void update() {
		
		//Der er ingen i en straffesparkskonkurrence der sprinter rundt
		player.setTargetSpeed(player.getTopSpeed() / 1); //////////////////////////////// skift 1 til 2
		
		//Hvem skal sparke?
		Team takerTeam = match.getSetPieceTeam();
		Player taker = takerTeam.getPenaltyTakers().get(takerTeam.getShootoutPenalty());
		
		//Målmanden der skal stå stiller sig i målet, den anden målmand stiller sig ude til siden
		if (player.isKeeper()){
			
			int goalLineX = pitch.getPitchPosX();
			if (match.getSetPieceX() > pitch.getPitchMidX())
				goalLineX = pitch.getPitchPosX() + pitch.getPitchWidth();
			
			if (match.getSetPieceTeam().equals(myTeam)){
				if (match.getSetPieceX() < pitch.getPitchMidX())
					player.targetX = pitch.getPitchPosX() - 10;
				else
					player.targetX = pitch.getPitchPosX() + pitch.getPitchWidth() + 10;
				player.targetY = pitch.getPitchMidY() - 100;
			}
			else{
				if (!player.kasterSig()){

					if (match.getSetPieceX() < pitch.getPitchMidX())
						player.targetX = pitch.getPitchPosX();
					else
						player.targetX = pitch.getPitchPosX() + pitch.getPitchWidth();
					player.targetY = pitch.getPitchMidY();


					if (bold.isShot()){

						//hvis keeperen prøver at gætte side kaster han sig så snart der er skudt
//						if (player.isGuessPenalties()){
						if (r.nextBoolean()){	
							System.out.println("Keeper gætter");
							
							player.setKasterSig(true);

							if (r.nextBoolean()) player.targetY = myTeam.getGoalY();
							else player.targetY = myTeam.getGoalY() + 59;
							
							player.setA(Math.atan2(player.targetX - player.getX(), player.targetY - player.getY()));
							player.setTargetA(player.getA());
							player.setSpeed((80 + player.getReaction() + player.getAgility()) / 2);
						}
						else if (System.currentTimeMillis() - bold.getBallTimer() > 450 - 2 * player.reaction){
							
							//Hvis keeperen gætter reagerer han først efter 600 - 2*reaction millisekunder
							
							player.setTargetSpeed(player.getTopSpeed()); 
							
							//hvis skuddet går langt forbi gør vi ikke noget
							double tempX = bold.getX() + Math.sin(bold.getA()) * 300;
							double tempY = bold.getY() + Math.cos(bold.getA()) * 300;
							Point rammer = Hjaelper.intersection(goalLineX, myTeam.getGoalY() - 10, goalLineX, myTeam.getGoalY() + 59 + 10, 
									(int)bold.getX(), (int)bold.getY(), (int)tempX, (int)tempY);

							if (rammer != null && rammer.y < myTeam.getGoalY() + 59 + 10 && rammer.y > myTeam.getGoalY() - 10){ //den rammer tæt på mål

								//Som udgangspunkt løber vi hen og tager skuddet 
								Point p = bold.meetBall(player, true);
								tempX = p.x;
								tempY = p.y;

								//hvis vi først kan møde bolden på den anden side af baglinie og tæt på mål
								//så kaster vi os efter den mm. det er et lob - så er der ikke så meget at gøre (fordi vi først kan 
								//møde den på den anden side af baglinien
								if (Math.abs(tempX - pitch.getPitchMidX()) > Math.abs(goalLineX - pitch.getPitchMidX())
										&& Math.abs(bold.getZForce()) < 20
								){
									player.setKasterSig(true);

									tempX = bold.getX() + Math.sin(bold.getA()) * 3;
									tempY = bold.getY() + Math.cos(bold.getA()) * 3;
									double dist = Hjaelper.Distance(bold.getX(), bold.getY(), player.getX(), player.getY());

									while (Math.abs(tempX + 5 - pitch.getPitchMidX()) < Math.abs(goalLineX - pitch.getPitchMidX()) 
											&& Hjaelper.Distance(tempX, tempY, player.getX(), player.getY()) < dist){
										dist = Hjaelper.Distance(tempX, tempY, player.getX(), player.getY());
										tempX += Math.sin(bold.getA()) * 3;
										tempY += Math.cos(bold.getA()) * 3;
									}
									player.setA(Math.atan2(tempX - player.getX(), tempY - player.getY()));
									player.setTargetA(player.getA());
									player.setSpeed((100 + player.getReaction() + player.getAgility()) / 2);

									player.targetX = tempX;
									player.targetY = tempY;
								}
							}
							else{
								player.setTargetX(bold.getX());
								player.setTargetY(bold.getY());
							}	
						}
						
						
					}
				}
			}
		}
		else if (!player.equals(taker)){
			//Alle der ikke skal sparke eller være målmand stiller sig sammen med holdet på den anden side af midten
			
			if (!player.isKeeper()){
				if (match.getSetPieceX() < pitch.getPitchMidX())
					player.targetX = pitch.getPitchMidX() + 60 - Math.abs((3 - myTeam.getPlayers().indexOf(player)) * 2);
				else
					player.targetX = pitch.getPitchMidX() - 60 + Math.abs((3 - myTeam.getPlayers().indexOf(player)) * 2);
				
				if (bold.getTeamA().equals(myTeam))
					player.targetY = pitch.getPitchMidY() - 50 - myTeam.getPlayers().indexOf(player) * 14;
				else
					player.targetY = pitch.getPitchMidY() + 50 + myTeam.getPlayers().indexOf(player) * 14;
			}
		}
		else if (player.equals(taker)){
			//Ham der tager straffesparket skal stille sig bag bolden og tage straffet lidt efter alle er på plads
			
			if (match.getSetPieceX() > pitch.getPitchWidth() / 2){
				player.targetX = match.getSetPieceX() - 20;
			}
			else{
				player.targetX = match.getSetPieceX() + 20;
			}
			player.targetY = match.getSetPieceY();

			//hvis keeperen eller taker ikke er på plads venter vi
			if (Hjaelper.Distance(oppTeam.getKeeper().getX(), oppTeam.getKeeper().getY(), oppTeam.getKeeper().getTargetX(), oppTeam.getKeeper().getTargetY()) > 8 ||
					Hjaelper.Distance(taker.getX(), taker.getY(), taker.getTargetX(), taker.getTargetY()) > 25 || MatchEngine.waitForSubs)
				player.ready = System.currentTimeMillis();

			if (System.currentTimeMillis() - player.ready > 4000){

				player.targetX = bold.getX();
				player.targetY = bold.getY();
				
				if (Hjaelper.Distance(player.x, player.y, bold.getX(), bold.getY()) < 9){

					bold.setMaal(false);
					player.getStateHasBall().getAiShoot().setShootX((int)oppTeam.getKeeper().getX());
					
					if (r.nextBoolean())
						player.getStateHasBall().getAiShoot().setShootY(oppTeam.getGoalY() + 5);
					else
						player.getStateHasBall().getAiShoot().setShootY(oppTeam.getGoalY() + 54);
						
					player.getStateHasBall().getAiShoot().setShootHigh(r.nextBoolean());
					match.setPenaltyTaken(true);
					match.setPenaltyTime(System.currentTimeMillis());
					player.getStateHasBall().getAiShoot().shoot(true, 3, 1, 1); //Bolddif sat til 3 for at "simulere" nerver??? Uhhh
					player.setState(player.getStatePenaltyShootout());
					
					if (player.getMyTeam().equals(player.getBold().getTeamA())){
						match.getShootoutShotsA().add(0);
						
					}
					else{
						match.getShootoutShotsB().add(0);
						
					}
						
				}
			}
		}

		//Vend hen mod target - medmindre vi kaster os efter bolden (keepere)
		if (!player.kasterSig())
			player.targetA = Math.atan2((player.targetX - player.x), (player.targetY - player.y));
	
	}

	public String toString(){
		return "Straffesparkskonkurrence";
	}
}
