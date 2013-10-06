package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import data.DAOCake;

import ai.AIClear;
import ai.AICross;
import ai.AIDribble;
import ai.AIHoldUpBall;
import ai.AIPass;
import ai.AIRunWithBall;
import ai.AIShoot;

import model.Match.MatchState;

public class StateHasBall implements PlayerState{

	private Player player;
	private Random r;
	private Bold bold;
	private Pitch pitch;
	private AIRunWithBall aiRunWithBall;
	private AIDribble aiDribble;
	private AIShoot aiShoot;
	private AIClear aiClear;
	private AICross aiCross;
	private AIPass aiPass;
	private AIHoldUpBall aiHoldUpBall;
	
	public StateHasBall(Player p){
		bold = p.bold;
		pitch = p.pitch;
		player = p;
		r = new Random();

		aiRunWithBall = new AIRunWithBall(player,bold, pitch);
		aiDribble = new AIDribble(player, bold);
		aiShoot = new AIShoot(player, bold);
		aiClear = new AIClear(player, bold);
		aiCross = new AICross(player, bold);
		aiPass = new AIPass(player, bold);
		aiHoldUpBall = new AIHoldUpBall(player, bold);
	}

	

	
	//Taemninger
	private void controlBall(int boldDif){
		Sysout.print("controlball start", "StateHasBall,controlBall");
		player.setMatchMessage(" controls the ball");
		//int boldDif = (int)(bold.getSpeed() + Math.abs(bold.getZForce()) + bold.getZ() / 1.7);
		int skill = (int)((player.technique + 70) / 8 + (r.nextInt(4)));
		int taemning = boldDif - skill;
		Sysout.print("Tæmning: " + skill + " - " + boldDif, "StateHasBall,controlBall");

		if (taemning < 1)
			taemning = 1;

		double boldTaemning = (taemning + 25) * 0.0015;
//		boldTaemning = boldTaemning / r.nextInt(5);
		Sysout.print("boldTaemning: " + boldTaemning, "StateHasBall,controlBall");

		if (boldTaemning >= 0.89) boldTaemning = 0.88;
		bold.setSpeed(2 + bold.getSpeed() * boldTaemning * 0.4);
		Sysout.print("BoldSpeed after taemning: " + bold.getSpeed(), "StateHasBall,controlBall");
		double randA = r.nextDouble() + r.nextDouble() + r.nextDouble() - r.nextDouble() - r.nextDouble() - r.nextDouble(); 
		randA /= 2 - boldTaemning;//vinklen bliver mindre hvis tæmningen er god
		
		Sysout.print("randA: " + randA, "StateHasBall,controlBall");
		
		bold.setA(player.a + randA);
		
		int wait = 105 + taemning;
		player.wait = wait;
		
		Sysout.print("wait: " + wait, "StateHasBall,controlBall");
		
		bold.setZForce(bold.getZForce() / (player.technique * 0.2) + r.nextInt(10));
		player.setTargetSpeed(0);
		player.setSpeed(player.getSpeed() * 0.8);

		bold.setLastTouch(player);
		player.playerAction = PlayerAction.dribble;
		player.targetX = bold.getX();
		player.targetY = bold.getY();
		
		Sysout.print("controlball slut", "StateHasBall,controlBall");

	}


	

	public void update() {
		Sysout.print("StateHasBall.update()", "verbose");
		Sysout.print("hasball update start", "StateHasBall");
		
		//Set last touch so we can calculate how long he's had the ball
		bold.setLastTouch(player);
		
		//doem offside
		if (player.offsideBy > 0 && player.match.state.equals(MatchState.ON)){
			
			player.match.setSetPieceTeam(player.oppTeam);
			player.myTeam.setTeamState(TeamState.defend);
			bold.setLastTouch(null);
			player.match.setSetPieceX(player.offsideX);
			player.match.setSetPieceY(player.offsideY);
			player.match.setState(MatchState.FREE_KICK);

			DAOCake.addToMatchStat(player.getMatch().getMatchId(), player.getMyTeam().id, "offsides");
			
			player.resetOffside();
		}
		//hvis der ikke er offside fortsaetter vi
		else if (bold.getHasBall() == null || !bold.getHasBall().getRole().equals(PlayerRole.GK)){

			//hvis vi kan naa at reagere
			Sysout.print("Balltimer: " + (System.currentTimeMillis() - bold.getBallTimer()), "StateHasBall");
			
			if (System.currentTimeMillis() - bold.getBallTimer() > 550 - player.reaction){
				Sysout.print("Reaction ok.", "StateHasBall");
				
				player.ReactionTime = System.currentTimeMillis();

				//Hvis en modstander har rørt den sidst og er i gang med at drible eller har lavet en aflevering / skud så er det en interception
				if (bold.getLastTouch() != null && bold.getLastTouch().myTeam.equals(player.oppTeam) && 
						(bold.isShot() || bold.passTo != null || bold.getLastTouch().playerAction.equals(PlayerAction.dribble))){
					
					player.playerMatchStats.interceptions++;
					bold.setLastTouch(player);
				}
				
				bold.setPassTo(null);
				
				//hvis det er en aflevering vi modtager sŒ registrer at den er modtaget
				if (bold.getPassTo() == player){

					player.playerAction = PlayerAction.nothing;
				}

				//find ud af hvor taet den naermeste modstander er paa
				double closestOpp = 9999;
				Player pClosestOppInFront = null;
				Player pClosestOppBehind = null;
				
				for (Player p: player.oppTeam.getPlayers()){
					if (Hjaelper.Distance(bold.getX(), bold.getY(), p.getX(), p.getY()) < closestOpp){
						closestOpp = Hjaelper.Distance(bold.getX(), bold.getY(), p.getX(), p.getY());
					}
					if (Hjaelper.Distance(player.oppTeam.goalX, player.oppTeam.goalY + 30, p.getX(), p.getY()) < 
							Hjaelper.Distance(player.oppTeam.goalX, player.oppTeam.goalY + 30, player.x, player.y) && !p.isKeeper){
						
						if (pClosestOppInFront == null ||
								Hjaelper.Distance(bold.getX(), bold.getY(), p.getX(), p.getY()) <
								Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getX(), pClosestOppInFront.getY()))
							pClosestOppInFront = p;	
					}
					else if(Hjaelper.Distance(player.oppTeam.goalX, player.oppTeam.goalY + 30, p.getX(), p.getY()) > 
							Hjaelper.Distance(player.oppTeam.goalX, player.oppTeam.goalY + 30, player.x, player.y) && !p.isKeeper){
						
						if (pClosestOppBehind == null ||
								Hjaelper.Distance(bold.getX(), bold.getY(), p.getX(), p.getY()) <
								Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppBehind.getX(), pClosestOppBehind.getY()))
							pClosestOppBehind = p;	
					}
				}

				if (player.playerAction == PlayerAction.dribble){

					//hvis det er for lang tid siden vi har rort bolden har vi mistet den
					if (System.currentTimeMillis() - player.lastdribble > 2500) 
						player.playerAction = PlayerAction.nothing;
					else if (closestOpp > 100 && Hjaelper.Distance(player.x, player.y, player.myTeam.getGoalX(), player.myTeam.getGoalY()) < 400){
						//Hvis vi er taet paa eget maal og der ikke er modstandere taet paa saa dribl langsomt
						player.targetSpeed = 30;
					}

					//dribble videre hvis det ikke er en aflevering
					if (bold.getPassTo() == null){
						player.targetX = bold.getX();
						player.targetY = bold.getY();
					}
				}

				//Nu tænker vi os om før vi får den for at se om vi skal spille videre første gang. Jaaa//

				///////////////////////////////////////////
				//Alle beslutninger om hvad han goer med bolden:
				///////////////////////////////////////////
				ArrayList<Player> visibleTeammates = new ArrayList<Player>();
				ArrayList<Player> visibleOpponents = new ArrayList<Player>();

				//Time on ball (1-100)
				double timeOnBall = 101 - bold.getTimeOnBallDiff();
				
				//Find the players visible angle at this time
				double maxVisibleAngle = player.vision * 0.9;
				maxVisibleAngle += r.nextInt(21) - 10;
				maxVisibleAngle += timeOnBall / 3;
				if (maxVisibleAngle < 1) maxVisibleAngle = 1;
				if (maxVisibleAngle > 100) maxVisibleAngle = 100;
				
				//Find the players visible distance in metres at this time 
				double maxVisibleDistance = 200 + player.vision * 3;
				maxVisibleDistance += r.nextInt(101) - 50;
				maxVisibleDistance += timeOnBall * 3;
				if (maxVisibleDistance < 200) maxVisibleDistance = 200;
				if (maxVisibleDistance > 800) maxVisibleDistance = 800;
				
				Sysout.print("vision: " + player.vision, "visibleTeammates");
				Sysout.print("timeOnBall: " + timeOnBall, "visibleTeammates");
				Sysout.print("maxVisibleDistance: " + maxVisibleDistance, "visibleTeammates");
				Sysout.print("maxVisibleAngle: " + maxVisibleAngle, "visibleTeammates");
				
				for (Player p : player.getMyTeam().getPlayers()){

					if (p != player){
						//Direction towards team mate
						double retningTilMedspiller = Math.atan2(p.getX() - player.x, p.getY() - player.y);
						//Difference between player's direction and direction to team mate (1-100)
						double vinkelForskel = Math.abs(Hjaelper.angleDifference(player.a, retningTilMedspiller)) * 31.8;
						if (vinkelForskel < 1) vinkelForskel = 1;
						if (vinkelForskel > 100) vinkelForskel = 100;
						//Distance to team mate in metres
						double afstand = Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) / 8;
						
						Sysout.print("vinkelForskel: " + vinkelForskel, "visibleTeammates");
						Sysout.print("afstand: " + afstand, "visibleTeammates");
						
						if (vinkelForskel < maxVisibleAngle && afstand < maxVisibleDistance){
							visibleTeammates.add(p);
							Sysout.print(p.shirtNumber + " visible", "visibleTeammatesResult");
						}
						else{
							Sysout.print(p.shirtNumber + " NOT visible", "visibleTeammatesResult");
						}
						
					}
				}
				for (Player p : player.oppTeam.getPlayers()){

					if (p != player){
						double retningTilMedspiller = Math.atan2(p.getY() - player.y, p.getX() - player.x);
						double vinkelForskel = Hjaelper.angleDifference(player.a, retningTilMedspiller) * 10;
						double afstand = Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) / 20;
						double begraensetUdsyn = (player.vision + 100) / 1.4 - (vinkelForskel + afstand);

						//The longer the player has been on the ball the more he will see
						//The more vision the player has - the less time on the ball means
						double timeOnBallFactor = 100 - bold.getTimeOnBallDiff();
						timeOnBallFactor *= 50.0 / player.vision;
						begraensetUdsyn *= 50.0 / timeOnBallFactor;
						
						if (begraensetUdsyn > 95) begraensetUdsyn = 95;

						//													Sysout.print("udsyn: " + begraensetUdsyn + ", afstand: " + afstand + ", vinkel: " +vinkelForskel);
						//midlertidig for at tage alle med uanset udsyn
						begraensetUdsyn = 900;

						if (begraensetUdsyn > r.nextInt(100) + 1 || 
								Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()) < 30)
							visibleOpponents.add(p);
					}
				}

				Sysout.print("hasball update beslutning", "StateHasBall");
				
				double scoreRunWithBall = 500;
				double scoreDribble = 0; // hvor god mulighed er der for at drible?
				double scoreShoot = 0; // hvor god mulighed er der for skud?
				
				double scoreClear = -900; // skal jeg cleare?
				double scoreCross = -900;
				double scorePass = -500;
				double scoreHoldUpBall = -500;

				int pressure = 0; // The amount of pressure on the player (1-100). For each opponent within 56 pixels (7 metres) 56 minus the distance is added to pressure
				double igennem = -9999; //hvor fri er jeg til at loebe mod maal
				int oppsCloserToGoal = 0;

				ArrayList<Point> throughLine = player.oppTeam.getThroughLine(player.getPitch());
				
				double vinkelretPaaMaal =  Math.atan2(player.oppTeam.getGoalX() - player.myTeam.getGoalX(), 0);
				double retningPaaMaal =  Math.atan2(player.oppTeam.getGoalX() - player.x, player.oppTeam.getGoalY() + 30 - player.y);
				
				// regn ud hvor meget pres der er paa spilleren		
				for (Player opp : player.getOppTeam().getPlayers()){
					if (pressure < 100){
						double d = Hjaelper.Distance(player.x, player.y, opp.x, opp.y);
						if (d < 64){
							pressure += 64 - d;
						
//							If the player is through on goal the keeper's rushing out ability can put extra pressure on the player
							if (igennem > 0 && opp.equals(player.getOppTeam().getKeeper()))
								pressure += opp.rushingout / 3;
						}
					}
				}
				if (pressure > 100) pressure = 100;
				
//				ArrayList<Integer> dist = new ArrayList<Integer>();
				for (Player p : visibleOpponents){

					//afstande til naermeste modstandere
					double distance = Hjaelper.Distance(player.x, player.y, p.getX(), p.getY()); 
//					if (distance < 100){
//						dist.add((100 - (int)distance)/2);
//					}

					//er vi igennem?
					if (!p.isKeeper){
						double pas = Hjaelper.Distance(player.x, player.y, player.oppTeam.getGoalX(), player.oppTeam.getGoalY() + 30) - 
						Hjaelper.Distance(p.getX(), p.getY(), player.oppTeam.getGoalX(), player.oppTeam.getGoalY() + 30);

						if (pas > 0) oppsCloserToGoal++;

						pas -= distance / 2;

						if (pas > igennem)
							igennem = pas;
					}
				}
//				Sysout.print("**************Igennem: " + igennem);
				igennem *= -1;
				//hvis han er igennem, så justeres igennem så der ikke er så stor forskel efter hvor fri man er
				if (igennem > 0){
					igennem = igennem / 4 + 20;
				}
				//hvis han ikke er igennem, så er igennem mindre vigtig
				else igennem = igennem / 25;

				player.igennem = igennem;
				
//				Sysout.print("**************Igennem: " + igennem);
//				Collections.sort(dist);
//				for (int i = dist.size() - 1; i >= 0; i--)
//					pressure += dist.get(i) / (dist.size() - i);

				

				Sysout.print("hasball update scoreshoot", "StateHasBall");
				scoreShoot = aiShoot.getScore(igennem, retningPaaMaal, vinkelretPaaMaal);
				
				Sysout.print("hasball update scoreclear", "StateHasBall");
				scoreClear = aiClear.getScore(pressure);

				Sysout.print("hasball update scorerunwithball", "StateHasBall");
				scoreRunWithBall = aiRunWithBall.getScore(igennem, pClosestOppInFront, pClosestOppBehind);
				
				Sysout.print("hasball update scoredribble", "StateHasBall");
				scoreDribble = aiDribble.getScore(oppsCloserToGoal, pressure, pClosestOppInFront);
				
				Sysout.print("hasball update scorecross", "StateHasBall");
				scoreCross = aiCross.getScore(igennem, retningPaaMaal, vinkelretPaaMaal, visibleTeammates);

				Sysout.print("hasball update scorepass", "StateHasBall");
				scorePass = aiPass.getScore(igennem, visibleTeammates, visibleOpponents, throughLine, closestOpp);

				Sysout.print("hasball update scoreholdupball", "StateHasBall,aiholdupball");
				scoreHoldUpBall = aiHoldUpBall.getScore(pressure, closestOpp);
				
				//Waiting to be tested and added
//				scoreHoldUpBall = -900;
//				scoreDribble = 0;
				
				if (player.instructionHasBall != null){
					System.out.println("Instruction");
					if (player.instructionHasBall.getAction().equals("RunWithBall"))
						scoreRunWithBall = 999999;
					if (player.instructionHasBall.getAction().toLowerCase().equals("controlball"))
						controlBall((int)bold.getBoldDifPercent());
					if (player.instructionHasBall.getAction().toLowerCase().equals("highpass"))
						aiPass.pass(player.instructionHasBall.getTargetPlayer(), true, (int)bold.getBoldDifPercent(), new Point((int)player.instructionHasBall.getTargetPlayer().getX(), (int)player.instructionHasBall.getTargetPlayer().getY()), pressure, (int)bold.getTimeOnBallDiff());
					if (player.instructionHasBall.getAction().toLowerCase().equals("lowpass"))
						aiPass.pass(player.instructionHasBall.getTargetPlayer(), false, (int)bold.getBoldDifPercent(), new Point((int)player.instructionHasBall.getTargetPlayer().getX(), (int)player.instructionHasBall.getTargetPlayer().getY()), pressure, (int)bold.getTimeOnBallDiff());
					if (player.instructionHasBall.getAction().toLowerCase().equals("dribble")){
						aiDribble.getScore(1, 0, player.instructionHasBall.getTargetPlayer());
						aiDribble.setDribbleTarget(new Point(player.instructionHasBall.getTarget()));
						aiDribble.dribble(player.instructionHasBall.getTargetPlayer());
					}
				}
				else{

					//hvis chancen er stor nok er det et highlight
					if (scoreShoot > 90){
						player.myTeam.addReplayCode(-14);
					}


					//					Nu ved vi hvor godt det er at aflevere, skyde og drible 
					//					så lad os se om vi har tid til at kontrollere bolden før vi gør det

					//som udgangspunkt er det en ligeså god ide at kontrollere bolden som den er svær at kontrollere

					//hvor svær ville bolden være at tæmme?
					int boldDif = bold.getBoldDifPercent();
					int skill = (int)(((player.technique + 70) / 8 + (r.nextInt(4))));
					int taemning = boldDif - skill;
					if (taemning < 1) taemning = 1;
					
					double scoreControl = boldDif;

					//				Sysout.print("boldDif: " + boldDif + " / skill: " + skill + " / taemning: " + taemning + " / pressure: " + pressure);

					//jo tættere på en opp er jo mindre tid har vi til at tæmme bolden
					if (closestOpp < 80){
						scoreControl -= 80 - closestOpp / 2;
						//jo sværere jo længere tid ville det tage og så kunne det være dumt
						//if (taemning < 0) scoreControl += taemning;
					}
					else //hvis der er ikke er pres på: jo sværere bolden er at tæmme, jo større chance for at forsøge at tæmme den.
						scoreControl += taemning;
					//				Sysout.print("Control: " + scoreControl + ", Tæmning: " + taemning + ", Pressure: " + pressure + ", Skill: " + skill +
					//						", boldDif: " + boldDif);

					
					Sysout.print("scoreControl: " + scoreControl, "StateHasBall");

					Sysout.print(player.shirtNumber + ". " + player.lastname, "StateHasBall,decision");
					Sysout.print("Bolddif: " + boldDif, "StateHasBall,decision");
					Sysout.print("Clear: " + scoreClear, "StateHasBall,decision");
					Sysout.print("Cross: " + scoreCross, "StateHasBall,decision");
					Sysout.print("Dribble: " + scoreDribble, "StateHasBall,decision");
					Sysout.print("Runwithball: " + scoreRunWithBall, "StateHasBall,decision");
					Sysout.print("Shoot: " + scoreShoot, "StateHasBall,decision");
					Sysout.print("Pass: " + scorePass, "StateHasBall,decision");
					Sysout.print("HoldUpBall: " + scoreHoldUpBall, "StateHasBall,decision");
					Sysout.print("Control: " + scoreControl, "StateHasBall,decision");


					//hvis det alt ialt er en god ide at tæmme bolden gør vi det - hvis vi ikke har haft bolden før
					if (bold.lastTouch != player &&
							scoreControl > scoreRunWithBall && 
							scoreControl > scorePass &&
							scoreControl > scoreShoot &&
							scoreControl > scoreClear &&
							scoreControl > scoreCross &&
							scoreControl > scoreDribble){

						controlBall(boldDif);
						player.playerMatchStats.ballControlAttempts++;

						Sysout.print("Forsøger tæmning...", "StateHasBall");
					}


					//hvis det var så nemt at tæmme bolden (wait=0) at vi kan fortsætte med det samme gør vi det
					//Hvis vi ikke har tæmmet bolden fortsætter vi også
					if (player.wait == 0){

						if (scoreClear >= scoreCross &&
								scoreClear >= scoreRunWithBall &&
								scoreClear >= scorePass &&
								scoreClear >= scoreShoot &&
								scoreClear >= scoreHoldUpBall && 
								scoreClear >= scoreDribble){
							if (bold.z > player.height / 2 && !player.isKeeper)
								aiShoot.head(player.oppTeam.goalX, player.oppTeam.goalY, boldDif);
							else
								aiClear.clear(boldDif);
						}
						else if (scoreCross >= scoreClear &&
								scoreCross >= scoreRunWithBall &&
								scoreCross >= scorePass &&
								scoreCross >= scoreShoot &&
								scoreCross >= scoreHoldUpBall &&
								scoreCross >= scoreDribble){
							aiCross.cross(boldDif);
						}
						else if (scoreRunWithBall >= scoreClear &&
								scoreRunWithBall >= scoreCross &&
								scoreRunWithBall >= scorePass &&
								scoreRunWithBall >= scoreShoot &&
								scoreRunWithBall >= scoreHoldUpBall &&
								scoreRunWithBall >= scoreDribble){

							//Hvis vi er igennem og nærmer os målmanden dribler vi langsomt hvis vi ikke har en forsvarer på nakken
							if (igennem > 0 && Hjaelper.Distance(player.x, player.y, player.oppTeam.goalX, player.oppTeam.goalY + 30) < 140
									&& player.freeFactor > 25)
								aiDribble.customDribble(player.oppTeam.goalX, player.oppTeam.goalY + 30, boldDif, 30);
							else{
								if (player.instructionHasBall != null)
									aiRunWithBall.runWithBall(player.instructionHasBall.getTarget().x, player.instructionHasBall.getTarget().y, player.myTeam, player.oppTeam, boldDif, igennem > 0);
								else
									aiRunWithBall.runWithBall(player.oppTeam.getGoalX(), player.oppTeam.getGoalY() + 29, player.myTeam, player.oppTeam, boldDif, igennem > 0);
							}
						}
						else if (scoreShoot >= scoreRunWithBall &&
								scoreShoot >= scoreCross &&
								scoreShoot >= scorePass &&
								scoreShoot >= scoreClear &&
								scoreShoot >= scoreHoldUpBall &&
								scoreShoot >= scoreDribble){

							if (bold.z > player.height / 2)
								aiShoot.head(boldDif);
							else
								aiShoot.shoot(boldDif, pressure, bold.getTimeOnBallDiff());
						}
						else if (scorePass >= scoreRunWithBall &&
								scorePass >= scoreCross &&
								scorePass >= scorePass &&
								scorePass >= scoreShoot &&
								scorePass >= scoreHoldUpBall &&
								scorePass >= scoreDribble){

							aiPass.pass(boldDif, pressure, (int)bold.getTimeOnBallDiff());

						}
						else if (scoreDribble >= scoreRunWithBall &&
								scoreDribble >= scoreCross &&
								scoreDribble >= scorePass &&
								scoreDribble >= scoreShoot &&
								scoreDribble >= scoreHoldUpBall &&
								scoreDribble >= scorePass){

							aiDribble.dribble(pClosestOppInFront);

						}
						else if (scoreHoldUpBall >= scoreRunWithBall &&
								scoreHoldUpBall >= scoreCross &&
								scoreHoldUpBall >= scorePass &&
								scoreHoldUpBall >= scoreShoot &&
								scoreHoldUpBall >= scoreDribble &&
								scoreHoldUpBall >= scorePass){

							aiHoldUpBall.holdUpBall(bold.getBoldDifPercent());

						}
					}
				}

			}
		}
		Sysout.print("hasball update end", "StateHasBall");
	}

	public AIDribble getAiDribble() {
		return aiDribble;
	}

	public AIShoot getAiShoot() {
		return aiShoot;
	}
	
	public AICross getAiCross() {
		return aiCross;
	}
	public AIPass getAiPass() {
		return aiPass;
	}




	public AIHoldUpBall getAiHoldUpBall() {
		return aiHoldUpBall;
	}




	public void setAiHoldUpBall(AIHoldUpBall aiHoldUpBall) {
		this.aiHoldUpBall = aiHoldUpBall;
	}
	
}
