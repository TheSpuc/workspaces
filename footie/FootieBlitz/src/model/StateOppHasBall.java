package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.Map.Entry;

import states.StatePressuring;

import data.DAOCake;
import model.Match.MatchState;

public class StateOppHasBall implements PlayerState{

	private Player player;
	private Bold bold;
	private Pitch pitch;
	private Random r = new Random();

	public StateOppHasBall(Player p){
		bold = p.bold;
		player = p;	
	}

	public void update() {
		Sysout.print("StateOppHasBall.update()", "verbose");
		boolean position = false;


		if (System.currentTimeMillis() - bold.getBallTimer() > 350 - player.reaction || true){
			//FIX det så der tackles jævnligt men ikke hver opdate. pt. random
			if (player.match.state.equals(MatchState.ON) && r.nextDouble() < 0.3 && 
					(bold.getHasBall() == null || !bold.getHasBall().getRole().equals(PlayerRole.GK))){ 
				checkTackles();	
			}

			//den med- og modspiller der kan komme først til bolden
			Player opp = null;
			Player mate = null;
			for (Player p : player.oppTeam.players)
				if (opp == null || p.countToBall < opp.countToBall)
					opp = p;

			for (Player p : player.myTeam.players)
				if (mate == null || p.countToBall < mate.countToBall)
					mate = p;

			if (player.shirtNumber == 31){
				Sysout.print("mate: " + mate.shirtNumber, "StateOppHasBall,marking");
				Sysout.print("mctb: " + mate.countToBall, "StateOppHasBall,marking");
				Sysout.print("octb: " + opp.countToBall, "StateOppHasBall,marking");
			}

			//hvis vi kan komme foerst til bolden tager vi den
			//Vi går også efter den nogle gange (afhængig af aggresion) hvis modstanderen 
			//ikke dækker den (forsøg på at vinde bolden) 
			boolean chaseBall = false;
			boolean goingForPass = false;
			if (bold.getHasBall() == null || !bold.getHasBall().getRole().equals(PlayerRole.GK)){
				if (player.match.state.equals(MatchState.ON)){

					if (bold.firstToBall == player){
						chaseBall = true;
					}
					else if (opp == null && player.equals(mate)){
						chaseBall = true;
					}
					else if (player.equals(mate) && (player.countToBall < 10 ||
							(Math.abs(opp.countToBall - player.countToBall) < player.countToBall / 8 || 
									Math.abs(bold.x - player.myTeam.goalX) < player.myTeam.offsideDistToGoal))){

						chaseBall = true;
//						System.out.println(player.shirtNumber + " GÅR EFTER BOLDEN********************");
					}
					//Hvis der er en aflevering på vej til en modstander spilleren dækker op
					//og han kun dækker den ene spiller op så løb hen mod det sted hvor modstanderen 
					//skal møde bolden for at presse ham
					else if (false && bold.passTo != null && player.currentlyMarking.contains(bold.passTo) &&
							player.currentlyMarking.size() == 1 &&
							bold.passTo.playerAction.equals(PlayerAction.receiving_pass) &&
							player.aggression + 100 / 2 > 49 + r.nextInt(10000)){

						((StatePressuring)player.getStatePressuring()).setTargetPlayer(bold.passTo);
						player.setState(player.getStatePressuring());
						goingForPass = true;
//						System.out.println(player.shirtNumber + " GÅR EFTER AFLEVERING********************");
					}
					//Hvis spilleren kun dækker en modstander op og modstanderen ikke 
					//står i vejen for bolden så gå ind og forsøg at få fat i den
					//afhængigt af aggression 
					else if (false && player.currentlyMarking.size() == 1 && bold.lastTouch.equals(player.currentlyMarking.get(0)) &&
							Hjaelper.Distance(bold.x, bold.y, player.x, player.y) < 
							Hjaelper.Distance(player.currentlyMarking.get(0).x, player.currentlyMarking.get(0).y, player.x, player.y) &&
							player.aggression + 100 / 2 > 49 + r.nextInt(500)){
						
						((StatePressuring)player.getStatePressuring()).setTargetPlayer(player.currentlyMarking.get(0));
						player.setState(player.getStatePressuring());
//						System.out.println(player.shirtNumber + " PRESSER********************");
						chaseBall = true;
					}
				}
			}

			if (goingForPass){
				//We are already doing it
			}
			else if (chaseBall){
				Point p = bold.meetBall(player, true);
				player.targetX = p.x;
				player.targetY = p.y;
				player.targetSpeed = player.topSpeed;
				player.setPlayerAction(PlayerAction.chasingBall);
			}
			else{


				if (player.currentlyMarking.size() == 0){
					player.targetSpeed = player.topSpeed;
					player.setPlayerAction(PlayerAction.position);
					double defDist = 9999;

					for (Player p : player.myTeam.players)
						if (!p.isKeeper)
							if (Hjaelper.Distance(p.posX, 0, player.myTeam.goalX, 0) < defDist)
								defDist = Hjaelper.Distance(p.posX, 0, player.myTeam.goalX, 0);


					//hvis vi ikke dækker op og vi er langt bagude så stå et sted mellem egen pos og linien mellem bolden og målet
					if (Hjaelper.Distance(player.posX, 0, player.myTeam.goalX, 0) < defDist + 90){

						double q = Math.atan2(bold.x - player.myTeam.goalX, bold.y - player.myTeam.goalY + 29);

						double tempX = player.myTeam.goalX + Math.sin(q) * Hjaelper.Distance(player.posX, player.posY, player.myTeam.goalX, player.myTeam.goalY + 29);
						double tempY = player.myTeam.goalY + 29 + Math.cos(q) * Hjaelper.Distance(player.posX, player.posY, player.myTeam.goalX, player.myTeam.goalY + 29);

						q = Math.atan2(player.posX - tempX, player.posY - tempY);

						player.targetX = player.posX - 40 * Math.sin(q);
						player.targetY = player.posY - 40 * Math.cos(q);
					}
					else{
						player.targetX = player.posX;
						player.targetY = player.posY;
					}
					player.targetX = player.posX;
					player.targetY = player.posY;

				}
				else{

					player.targetSpeed = player.topSpeed;

					for (Player mark : player.currentlyMarking){
						//hvis ham vi markerer har bolden
						if (bold.getPassTo() == null && mark.equals(bold.getLastTouch()) && !bold.isShot()){

							boolean tryTackle = false;
							Random r = new Random();

							//hvis bolden er taet paa vores maal og mellem os og modstanderen tackler vi
							if ((Hjaelper.Distance(bold.getX(), bold.getY(), player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30) < player.getMyTeam().backLine &&
									Hjaelper.Distance(bold.getX(), bold.getY(), player.x, player.y) <
									Hjaelper.Distance(bold.getLastTouch().getX(), bold.getLastTouch().getY(), player.x, player.y)) ||
									player.myTeam.teamState.equals(TeamState.mid)){

								tryTackle = true;
							}
							else if(r.nextInt(50000) < player.aggression + 100){
								tryTackle = true;
							}


							if (tryTackle){
								Point p = bold.meetBall(player, true);
								player.targetX = p.x;
								player.targetY = p.y;

							}
							else{
								position = true;
							}

						}
						else
							position = true;
					}

				}

				if (position){

					player.playerMatchStats.updatesMarking++;

					ArrayList<Integer> points = new ArrayList<Integer>();

					for (Player mark : player.currentlyMarking){

						//Hvis modstanderen ikke er nået frem til forsvarerens område venter han på ham i området
						if (Hjaelper.Distance(mark.x, 0, player.myTeam.goalX, 0) >
						Hjaelper.Distance(player.myTeam.goalX, 0, player.posX, 0) + 80){

							//staa mellem angriberen og posX, posY
							double r = Math.atan2(mark.x - player.posX, mark.y - player.posY);
							player.targetX = player.posX + 30 * Math.sin(r);
							player.targetY = player.posY + 30 * Math.cos(r);

							points.add((int)player.targetX);
							points.add((int)player.targetY);

							//						if (Settings.SO) 
							Sysout.print(player.shirtNumber + " waiting to mark " + mark.shirtNumber, "StateOppHasBall,marking");
						}
						else{

							//hvor taet paa skal vi markere
							double d = 0.15 * Hjaelper.Distance(mark.getX(), mark.getY(), player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30);
							double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
							if (c < 1) c = 1;

							d = d * c;
							d += Hjaelper.distToLine(player.x, player.y, mark.x, mark.y, player.myTeam.goalX, player.myTeam.goalY + 29);
							if (d < 12) d = 12;

							//staa mellem manden og maalet
							double r = Math.atan2(player.getMyTeam().getGoalX() - mark.getX(), player.getMyTeam().getGoalY() + 30 - mark.getY());
							player.targetX = mark.getX() + d * Math.sin(r);
							player.targetY = mark.getY() + d * Math.cos(r);

							points.add((int)player.targetX);
							points.add((int)player.targetY);
						}
					}

					if (player.currentlyMarking.size() > 0){
						player.setPlayerAction(PlayerAction.marking);



						///////////////////////Forslag til nyt - måske ikke nødvendigt//////////////
						//Her burde forsvareren:
						//-Markere foran den angriber der er tættest på mål
						//-Hvis en anden angriber som også skal markeres har bolden: Gradvist gå over mod ham når han kommer tættere på mål
						//-Forsøge at holde offsidelinien i forhold til de angribere der skal markeres og ikke har bolden (man går kun ned bag offsidelinien efter angribere der har bolden)

						//Angribernes score: Hvor tæt på mål er de, har de bolden, er de offside, deres vinkel på mål?
						SortedMap<Double, Player> scoredAttackers = new TreeMap<Double, Player>();

						//Samlet sum af scores nedenunder
						double totalScores = 0;

						//Jo lavere score jo vigtigere er det at være tæt på angriberen
						for (Player p : player.currentlyMarking){
							double score = 0;

							//Som udgangspunkt er man ligeså vigtig som man er tæt på mål
							score = Hjaelper.Distance(p.x, p.y, player.myTeam.goalX, player.myTeam.goalY + 30);

							//Jo spidsere vinkel angriberen har på mål jo mindre vigtig er han
							double vinkelretPaaMaal = Math.atan2(p.oppTeam.getGoalX() - p.myTeam.getGoalX(), 0);
							double retningPaaMaal = Math.atan2(p.oppTeam.getGoalX() - p.x, p.oppTeam.getGoalY() + 30 - p.y);
							double angleFactor = Hjaelper.angleDifference(retningPaaMaal, vinkelretPaaMaal);
							if (angleFactor < 1) angleFactor = 1;

							score *= angleFactor;

							//Hvis angriberen har rørt bolden sidst og der ikke er en aflevering eller et skud i gang
							//Eller hvis der er en aflevering på vej til ham - så er han vigtigere
							if ((bold.getLastTouch().equals(p) && !bold.isShot() && bold.passTo == null) || (bold.getPassTo() != null && bold.getPassTo().equals(p)))
								score /= 2.0;

							//Hvis man er offside (og ikke har bolden) er man mindre vigtig
							if (Math.abs(player.myTeam.goalX - p.x) < player.myTeam.offsideDistToGoal && bold.getLastTouch() != null && !bold.getLastTouch().equals(p) && bold.getPassTo() != null && !bold.getPassTo().equals(p))
								score *= 2;

							scoredAttackers.put(score, p);
							totalScores += score;

							Sysout.print("AttScore: " + p.shirtNumber + " - " + score, "StateOppHasBall,marking");
						}

						//Dæk som udgangspunkt op foran den vigtigste


						//Derefter trækker vi over mod de andre på y-aksen

						double markX = 0; 	//Vores (forsvarerens) placering på X-aksen. Sættes afhængig af boldholder / offsidelinie
						double markY = 0;	//Vores (forsvarerens) placering på Y-aksen.

						for (Entry<Double, Player> e : scoredAttackers.entrySet()){

							if (markX == 0){
								//Den første angriber er den vigtigste. Som udgangspunkt står vi mellem ham og målet
								double d = 0.16 * Hjaelper.Distance(e.getValue().getX(), e.getValue().getY(), player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30);
								d += 0.30 * Hjaelper.Distance(e.getValue().getX(), e.getValue().getY(), player.x, player.y);
								double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
								if (c < 1) c = 1;

								d = d * c;
								if (d < 16) d = 16;

								double r = Math.atan2(player.myTeam.goalX - e.getValue().getX(), player.myTeam.goalY + 30 - e.getValue().getY());
								markX = e.getValue().getX() + d * Math.sin(r);
								markY = e.getValue().getY() + d * Math.cos(r);
							}
							else{
								//Resten af dem vi skal dække op trækker vi over mod afhængig af deres score

								double attDirToGoal = Math.atan2(player.myTeam.goalX - e.getValue().x, player.myTeam.goalY + 29 - e.getValue().x);
								//							double attDirInv = attDirToGoal + Math.PI / 2;
								//							
								//							Point p = Hjaelper.intersection(
								//									(int)(markX - Math.sin(attDirInv) * 1000), 
								//									(int)(markY - Math.cos(attDirInv) * 1000), 
								//									(int)(markX + Math.sin(attDirInv) * 1000), 
								//									(int)(markY + Math.cos(attDirInv) * 1000), 
								//									(int)e.getValue().x, 
								//									(int)e.getValue().y, 
								//									player.myTeam.goalX, 
								//									player.myTeam.goalY + 29);
								//							
								//							double scorePercent = e.getKey() / totalScores;
								//							
								//							double r = Math.atan2(p.x - markX, p.y - markY);
								//							double factor = Hjaelper.Distance(markX, markY, p.x, p.y) * scorePercent;
								//							
								//							markX += factor * Math.sin(r);
								//							markY += factor * Math.cos(r);
							}
						}

						player.targetX = markX + player.markErr.x;
						player.targetY = markY + player.markErr.y;

						if (Hjaelper.Distance(markX, markY, player.myTeam.goalX, player.myTeam.goalY + 29) > Hjaelper.Distance(player.x, player.y, player.myTeam.goalX, player.myTeam.goalY + 29))
							player.targetSpeed = player.topSpeed / 4;
						/////////////////////////////////////////////////////////////////
						//TEST
						////////////////////////////////////////////////////////////////////////////////////////////


						//					int pointsArray[] = new int[points.size()];
						//
						//					for (int i = 0; i < points.size(); i++){
						//						pointsArray[i] = points.get(i);
						//					}
						//
						//					int markPoint[] = Hjaelper.centroid(pointsArray); 
						//					if (pointsArray.length == 2){
						//						markPoint[0] = pointsArray[0];
						//						markPoint[1] = pointsArray[1];
						//					}
						//
						//					if (player.shirtNumber == 31){
						//						Sysout.print("len: " + pointsArray.length);
						//						for (int i : markPoint)
						//							Sysout.print("mp: " + i);
						//					}
						//						
						//					
						//					double d = 0.15 * Hjaelper.Distance(markPoint[0], markPoint[1], player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30);
						//					double c = (double)(100.0 - (double)player.getPressing()) / (double)100.0;
						//					if (c < 1) c = 1;
						//					
						//					d = d * c;
						//					if (d < 12) d = 12;
						//
						//					double r = Math.atan2(player.myTeam.goalX - markPoint[0], player.myTeam.goalY + 30 - markPoint[1]);
						//					player.targetX = markPoint[0] + d * Math.sin(r);
						//					player.targetY = markPoint[1] + d * Math.cos(r);
						//
						//					if (player.shirtNumber == 31){
						//						Sysout.print("tx: " + player.targetX);
						//						Sysout.print("ty: " + player.targetY);
						//					}
						//					
						//					player.targetX += player.markErr.x;
						//					player.targetY += player.markErr.y;
					}
					else{
						player.targetX = player.posX + player.markErr.x;
						player.targetY = player.posY + player.markErr.y;
					}
				}

				if (player.shirtNumber == 31){
					Sysout.print("tx: " + player.targetX, "StateOppHasBall,marking");
					Sysout.print("ty: " + player.targetY, "StateOppHasBall,marking");
					Sysout.print("mex: " + player.markErr.x, "StateOppHasBall,marking");
					Sysout.print("mey: " + player.markErr.y, "StateOppHasBall,marking");
					Sysout.print("ftb: " + bold.firstToBall.shirtNumber, "StateOppHasBall,marking");

				}

				if (player.shirtNumber == 31){
					Sysout.print("tx: " + player.targetX, "StateOppHasBall,marking");
					Sysout.print("ty: " + player.targetY, "StateOppHasBall,marking");
				}

				//ikke længere tilbage end offsidelinien
				if (Math.abs(player.targetX - player.myTeam.goalX) < player.myTeam.getBackLine() && !player.getPlayerAction().equals(PlayerAction.chasingBall)){

					//Hvis ham der kan komme først til bolden er modstander og han møder bolden bag offsidelinien - eller hvis der er et indlæg på vej ind - er vi nødt til at bakke ned bag linien
					if ((bold.firstToBall.myTeam.equals(player.oppTeam) && Math.abs(bold.firstToBallPoint.x - player.myTeam.getGoalX()) < player.myTeam.getBackLine()) || bold.isCross()){
						//ingenting fordi vi placerer os som vi alligevel ville 
					}
					else{ //ingen modstander kan komme ind bag offsidelinien så vi holder den
						player.targetX = player.myTeam.goalX + player.myTeam.getBackLine();
						if (player.myTeam.goalX > player.pitch.getPitchMidX())
							player.targetX = player.myTeam.goalX - player.myTeam.getBackLine();
					}
				}

				if (player.shirtNumber == 31){
					Sysout.print("tx: " + player.targetX, "StateOppHasBall,marking");
					Sysout.print("ty: " + player.targetY, "StateOppHasBall,marking");
				}

				//Er bolden ude eller har keeperen loeber vi paa plads
				//		if (bold.getHasBall() != null && bold.getHasBall().isKeeper() || bold.isUdenfor()){
				//			player.targetX = player.posX;
				//			player.targetY = player.posY;
				//		}
			}
		}

	}


	private void checkTackles(){
		if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < 11){
			Sysout.print("checktackles start", "StateHasBall,checkTackles");
			Sysout.print(player.lastname + ": " + Hjaelper.Distance(player.x, player.y, bold.x, bold.y), "StateHasBall,checkTackles");

			Player opp = new Player();
			boolean challenge = true;

			opp = bold.getLastTouch();
			if(opp == null){
				for(Player p : player.oppTeam.getPlayers()){
					if(opp == null)opp = p;
					else {
						if(Hjaelper.Distance(p.x, p.y, bold.x, bold.y) < Hjaelper.Distance(opp.x, opp.y, bold.x, bold.y))
							opp = p;
					}
				}
			}

			Match match = player.match;
			Sysout.print("checktackles p1: challenge " + challenge, "StateHasBall,checkTackles");
			if (challenge && bold.getLastTouch() != player && opp.getWait() < 1){
				Sysout.print("checktackles p2", "StateHasBall,checkTackles");
				Sysout.print("opp: " + opp.getLastname() + ", " + Hjaelper.Distance(opp.x, opp.y, bold.x, bold.y), "StateHasBall,checkTackles");
				//Har modstanderen bolden og er han tæt på bolden
				if (bold.getLastTouch().equals(opp) && Hjaelper.Distance(opp.x, opp.y, bold.x, bold.y) < 10){
					Sysout.print("checktackles p3", "StateHasBall,checkTackles");
					player.playerMatchStats.tackles++;

					player.lastUpdate = System.currentTimeMillis();

					player.targetX = 50 * Math.sin(player.a);
					player.targetY = 50 * Math.cos(player.a);

					double oppScore = opp.getDribbling() + opp.getAgility() + opp.getStrength() / 10 + 100;
					double myScore = (player.tackling * 2) + player.strength / 10 + 150;

					oppScore += oppScore * (r.nextDouble() / 2);
					myScore += myScore * (r.nextDouble() / 2);
					Sysout.print("oppScore: " + oppScore + ", myScore: " + myScore, "StateHasBall,checkTackles");
					checkTackleOutcome(player, match, opp, myScore, oppScore);
				}
				else if(Hjaelper.Distance(opp.x, opp.y, bold.x, bold.y) < 11){
					//Ingen af de to har rort bolden sidst. 

					Sysout.print("checktackles p4", "StateHasBall,checkTackles");
					player.playerMatchStats.tackles++;

					double oppScore = opp.getStrength() + (opp.getTackling() * 2) + opp.getAggression();
					double myScore = player.strength + (player.tackling * 2) + player.aggression;

					oppScore += oppScore * (r.nextDouble() / 2);
					myScore += myScore * (r.nextDouble() / 2);
					Sysout.print("oppScore: " + oppScore + ", myScore: " + myScore, "StateHasBall,checkTackles");
					checkTackleOutcome(player, match, opp, myScore, oppScore);
				}
			}
			Sysout.print("checktackles slut", "StateHasBall,checkTackles");
		}		
	}

	/**
	 * Checks the outcome of a tackle
	 * @param p the player making the tackle
	 * @param match
	 * @param opp the player who is takcled
	 * @param myScore the quality of the tacklers tackle
	 * @param oppScore the quality of the evade/tackle
	 */
	public void checkTackleOutcome(Player p, Match match, Player opp, double myScore, double oppScore){
		//Fantastisk tackling - dribble
		if (myScore > oppScore * 1.3){
			Sysout.print("Fantastisk tackling", "StateHasBall,checkTackles");
			player.playerMatchStats.successfulTackles++;
			player.wait = 380  + (long)(player.tackling + player.reaction);
			opp.setWait(2000 + (long)(opp.tackling + opp.reaction));
			opp.setState(opp.getStateNothing());
			bold.setLastTouch(player);
			player.hasBall = true;
		}
		//Ok tackling. Bold springer vak
		else if (myScore + (player.aggression * 0.5) > oppScore){
			Sysout.print("Ok tackling", "StateHasBall,checkTackles");
			player.playerMatchStats.successfulTackles++;
			player.wait = 700 + (long)(player.tackling + player.reaction);
			opp.setWait(1500 + (long)(opp.tackling + opp.reaction));
			opp.setState(opp.getStateNothing());
			bold.setZForce((r.nextGaussian() + 3) * 5);
			bold.setSpeed((50 + 50 * r.nextDouble()) / (bold.getZForce()/10));
			Sysout.print("ZForce:" + bold.getZForce(), "StateHasBall,checkTackles");
			bold.setLastTouch(player);
			bold.setA((Math.PI * 2) * r.nextDouble());
			player.setSpeed(player.getSpeed() * 0.5);
			opp.setSpeed(opp.getSpeed() * 0.3);
		}
		//Mislykket tackling
		else{
			Sysout.print("Mislykket tackling", "StateHasBall,checkTackles");
			//risiko for frispark
			double tackleD1 = r.nextDouble();
			double tackleD2 = 0.35 + (player.aggression / 200);

			if (tackleD1 < tackleD2){
				System.out.println("Tackle shit: D1 " + tackleD1 + ", D2 " + tackleD2);
				if (r.nextDouble() < 0.20 + (player.aggression / 300)){
					if (r.nextDouble() < 0.05 + (player.aggression / 300)){
						player.setMatchMessage("får rødt kort!");//rødt card
						Sysout.print("#########################################RØØØØDT til: " + player.lastname, "StateHasBall,checkTackles");
					}
					else{
						player.setMatchMessage("får gult kort!");//yellow card
						Sysout.print("#########################################GUUULT til: " + player.lastname, "StateHasBall,checkTackles");
					}
				}

				player.setSpeed(player.getSpeed() * 0.3);
				opp.setSpeed(opp.getSpeed() * 0.9);
				opp.setWait(2200 + (long)(opp.tackling + opp.reaction));
				player.setWait(2200 + (long)(player.tackling + player.reaction));
				player.playerMatchStats.freekicksCommitted++;
				DAOCake.addToMatchStat(match.getMatchId(), player.getMyTeam().id, "fouls");

				//hvis det er i eget felt er der straffe
				if (bold.isBallInGoalField(player)){
					createPenalty(player, match, opp);
				}
				else{ //ellers er der frispark
					createFreekick(player, match, opp);
				}
			}
			else{
				player.playerAction = PlayerAction.nothing;
				player.wait = 1200 + (long)(player.tackling + player.reaction);
			}
		}
	}

	/**
	 * 
	 * @param p The player who caused the penalty
	 * @param match
	 * @param opp The player who was fouled 
	 */
	public void createPenalty(Player p, Match match, Player opp){
		Sysout.print("Penalty committed by " + player.firstname + " " + player.lastname, "StateHasBall,createPenalty");
		player.setMatchMessage(" begår straffe på " + opp.lastname);
		match.setState(MatchState.PENALTY);
		player.myTeam.addReplayCode(-15);
		bold.setSetPieceTime(System.currentTimeMillis());
		if (p.getMyTeam().getGoalX() < p.getOppTeam().getGoalX()) match.setSetPieceX(pitch.getPitchPosX() + 88);
		else match.setSetPieceX(pitch.getPitchPosX() + pitch.getPitchWidth() - 88);
		match.setSetPieceY(pitch.getPitchHeight() / 2 + pitch.getPitchPosY());
		match.setSetPieceTeam(player.oppTeam);
	}

	/**
	 * 
	 * @param p The player who committed the freekick
	 * @param match
	 * @param opp The player who was fouled
	 */
	public void createFreekick(Player p, Match match, Player opp){
		Sysout.print("Freekick committed by " + player.firstname + " " + player.lastname, "StateHasBall,createFreekick");
		match.setSetPieceX((int)player.x);
		match.setSetPieceY((int)player.y);
		player.setMatchMessage(" begår frispark på " + opp.lastname);
		bold.setSetPieceTime(System.currentTimeMillis());
		match.setState(MatchState.FREE_KICK);
		match.setSetPieceTeam(player.oppTeam);
		for (Player pl : player.myTeam.getPlayers())
			pl.setOffsideBy(0);

		for (Player pl : player.oppTeam.getPlayers())
			pl.setOffsideBy(0);
	}	
}
