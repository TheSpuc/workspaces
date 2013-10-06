package ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.PlayerRole;
import model.Settings;
import model.StateHasBall;
import model.Sysout;
import model.Team;

public class AIPass{

	private Player player;
	private Bold bold;
	private Random r;
	private int passX, passY, throughX, throughY;
	private String passType = "";
	private Player passTarget = null;
	Point inFrontOfTarget = new Point(); //The point in front of the team mate the player wants to pass to
	
	public AIPass(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}


	public double getScore(double igennem, ArrayList<Player> visibleTeammates, ArrayList<Player> visibleOpponents, ArrayList<Point> throughLine, double closestOppToMe) {

		double scorePass = 0;

		//jo taettere visibleTeamates er, jo stoerre score

		//Find mest fri holdkammerat
		Player targetLow = null;
		Player targetLowInfront = null;
		Player targetHigh = null;
		Player targetThrough = null;
		double scoreLow = -9999, scoreLowInFront = -9999, scoreHigh = -9999, scoreThrough = -9999;

		double minD = 0; // afstand fra boldens bane til taetteste modstander
		double minDInFront = 0; // Distance of the closes opp from the line from the ball to the point in front of the player
		double minFri = 0; //afstand fra modtager til naermeste modstander
		
		
//		for (Player l : visibleTeammates)
//			System.out.print(l.getShirtNumber() + ", ");
		Sysout.print("Visible opps:", "AIPass");
		for (Player l : visibleOpponents){
			Sysout.print(l.getShirtNumber() + ". " + l.getLastname(), "AIPass");
		}
		 

		//antal medspillere længere fremme
		int antalLaengereFremme = 0;

		for (Player u : visibleTeammates)
			if (Hjaelper.Distance(player.getX(), 0, player.getOppTeam().getGoalX(), 0) >
			Hjaelper.Distance(u.getX(), 0, u.getOppTeam().getGoalX(), 0))
				antalLaengereFremme++;

		for (Player p : visibleTeammates){
			if (!p.isKeeper && !p.getRole().equals(PlayerRole.GK)){

				Sysout.print("Teammate: " + p.getShirtNumber() + ". " + p.getLastname(), "AIPass");	

				double dirToGoal = Math.atan2(p.getOppTeam().getGoalX() - p.getX(), p.getOppTeam().getGoalY() + 29 - p.getY());
				double distToPlayer = Hjaelper.Distance(p.getX(), p.getY(), bold.getX(), bold.getY());
				double distToClosestOpp = 900;
				
				Point inFrontOfThisPlayer = new Point((int)p.getX(), (int)p.getY());
				
				//If the player is facing towards the opponents goal, pass the ball in front of him
				if (Math.abs(Hjaelper.angleDifference(dirToGoal, p.getA())) < 1.5){
					inFrontOfThisPlayer.x += Math.sin(dirToGoal) * p.getSpeed() * 0.6 * distToPlayer / 150.0;
					inFrontOfThisPlayer.y += Math.cos(dirToGoal) * p.getSpeed() * 0.6 * distToPlayer / 150.0;
					Sysout.print("************* 1 pixels in fornt of player 1: " + (p.getSpeed() * 0.6 * distToPlayer / 150.0), "AIPass");
				}
				else{
					//If he is facing away from the goal pass it directly to him or a little bit in front of him if he's running
					inFrontOfThisPlayer.x += Math.sin(p.getA()) * p.getSpeed() * 0.2 / (1 + distToPlayer / 150.0);
					inFrontOfThisPlayer.y += Math.cos(p.getA()) * p.getSpeed() * 0.2 / (1 + distToPlayer / 150.0);
					Sysout.print("************* pixels in fornt of player: " + (p.getSpeed() * 0.2 / (1 + distToPlayer / 150.0)), "AIPass");
				}
				
				Sysout.print("p.getSpeed(): " + p.getSpeed(), "AIPass");	
				Sysout.print("Hjaelper.angleDifference(dirToGoal, p.getA()): " + Hjaelper.angleDifference(dirToGoal, p.getA()), "AIPass");	
				
				double r = Math.atan2((p.getX() - bold.getX()), (p.getY() - bold.getY()));
				double rInFront = Math.atan2((inFrontOfThisPlayer.x - bold.getX()), (inFrontOfThisPlayer.y - bold.getY()));
				double teX = bold.getX() + (10 * Math.sin(r));
				double teY = bold.getY() + (10 * Math.cos(r));
				double teXInFront = bold.getX() + (10 * Math.sin(rInFront));
				double teYInFront = bold.getY() + (10 * Math.cos(rInFront));
				
				//Find n¾rmeste modstander til boldens bane mod p
				//der er vel(?) ingen grund til at tage modstandere med som er længere væk fra bolden end medspilleren

				ArrayList<Player> pl = new ArrayList<Player>();

				for (Player pm : visibleOpponents){					
					if (Hjaelper.Distance(pm.getXIn(15), pm.getYIn(15), bold.getX(), bold.getY()) - 15 < 
							Hjaelper.Distance(p.getXIn(15), p.getYIn(15), bold.getX(), bold.getY()))
						pl.add(pm);
				
					double d = Hjaelper.Distance(pm.getXIn(15), pm.getYIn(15), p.getXIn(15), p.getYIn(15));
					if (d < distToClosestOpp) distToClosestOpp = d;
				}

				minD = 100;
				minDInFront = 100;
				
				int limitWhile = 1000;
				while (Hjaelper.Distance(teX, teY, p.getX(), p.getY()) > 10 && limitWhile > 0){
					limitWhile--;
					teX += (10 * Math.sin(r));
					teY += (10 * Math.cos(r));

					for (Player pm : pl){
						//We give the opps a score based on their distance to the line of the ball and their distance to the ball itself
						double score = Hjaelper.Distance(teX, teY, pm.getXIn(15), pm.getYIn(15)) * (40 / Hjaelper.Distance(bold.getX(), bold.getY(), pm.getXIn(15), pm.getYIn(15)));
						if (minD == 100 || score < minD)
							minD = score;
					}
				}	
				
				limitWhile = 1000;
				while (Hjaelper.Distance(teXInFront, teYInFront, inFrontOfThisPlayer.x, inFrontOfThisPlayer.y) > 10 && limitWhile > 0){
					limitWhile--;
					teXInFront += (10 * Math.sin(rInFront));
					teYInFront += (10 * Math.cos(rInFront));

					for (Player pm : pl){
						//We give the opps a score based on their distance to the line of the ball and their distance to the ball itself
						double score = Hjaelper.Distance(teXInFront, teYInFront, pm.getXIn(15), pm.getYIn(15)) * (40 / Hjaelper.Distance(bold.getX(), bold.getY(), pm.getXIn(15), pm.getYIn(15)));
						if (minDInFront == 100 || score < minDInFront)
							minDInFront = score;
					}
				}
				if(minD > 50) minD = 50; //sikrer at selvom en medspiller er meget fri, så bliver scoreLow ikke vildt høj
				if(minDInFront > 50) minDInFront = 50; 
				
				//lav aflevering
				double thisPlayersLowScore = minD;
				double thisPlayersLowScoreInFront = minDInFront;
				
				Sysout.print("thisPlayersLowScore1: " + thisPlayersLowScore, "AIPass");
				Sysout.print("thisPlayersLowScoreInFront1: " + thisPlayersLowScoreInFront, "AIPass");

				// spillere tættere på end 60 får trukket alt under 60 fra
				if (Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) < 60){
					thisPlayersLowScore -= (60 - Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()));
					thisPlayersLowScoreInFront -= (60 - Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()));
				}
				Sysout.print("thisPlayersLowScore2: " + thisPlayersLowScore, "AIPass");
				Sysout.print("thisPlayersLowScoreInFront2: " + thisPlayersLowScoreInFront, "AIPass");
				
				// spillere længere væk end 180 får trukket alt over 200 fra
				if (Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) > 200){
					thisPlayersLowScore -= Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) - 200;
					thisPlayersLowScoreInFront -= Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) - 200;
				}

				Sysout.print("thisPlayersLowScore3: " + thisPlayersLowScore, "AIPass");
				Sysout.print("thisPlayersLowScoreInFront3: " + thisPlayersLowScoreInFront, "AIPass");
				
				//er medspilleren længere fremme eller tilbage?
				double afstand = Math.abs(p.getX() - p.getMyTeam().getGoalX()) - Math.abs(player.getX() - p.getMyTeam().getGoalX()); 

//				System.out.println("Afstand: " + afstand);
				if (afstand > 10)
					afstand = 10 + afstand / 8;
				else 
					afstand = afstand / 4;
				
				//Hvis medspilleren er længere tilbage end dig, så er der mindre chance for at han bliver spillet
				//medmindre der ikke er muligheder længere fremme
				
				
				
				//If thisPlayersLowScore isn't greater than 10 it's not a great opportunity for a pass and there's no reason to add (or subtract) for afstand
				//This is dependant on how direct the team wants to play and how long they've had the ball
				Sysout.print("bold.getTeamTimeOnBall(): " + bold.getTeamTimeOnBall(), "AIPass");
				Sysout.print("player.getMyTeam().buildUpPatience: " + player.getMyTeam().buildUpPatience, "AIPass");
				if (antalLaengereFremme > 0 && bold.getTeamTimeOnBall() > player.getMyTeam().buildUpPatience){
					thisPlayersLowScore += afstand;
					thisPlayersLowScoreInFront += afstand;	
				}
				
				Sysout.print("thisPlayersLowScore4: " + thisPlayersLowScore, "AIPass");
				Sysout.print("thisPlayersLowScoreInFront4: " + thisPlayersLowScoreInFront, "AIPass");
				
				boolean firstToBall = false;

				//hvis medspilleren er offside er han rigtig daarlig at spille til
				if (Hjaelper.Distance(p.getX(), p.getY(), player.getOppTeam().getGoalX(), p.getY()) < player.getOppTeam().getOffsideDistToGoal()){
					thisPlayersLowScore -= 700;
					thisPlayersLowScoreInFront -= 700;
					Sysout.print("thisPlayersLowScore5: " + thisPlayersLowScore, "AIPass");	
					Sysout.print("thisPlayersLowScoreInFront5: " + thisPlayersLowScoreInFront, "AIPass");
				}
				//Hvis medspilleren er uden for banen er han også rigtig dårlig at spille til
				else if (player.getPitch().isOut(p.getXIn(15), p.getYIn(15))){
					thisPlayersLowScore -= 700;
					thisPlayersLowScoreInFront -= 700;
				}
				else{
					//hvis han ikke kommer først til bolden skal vi alligevel ikke spille til ham
					
					//Vi skal tjekke om en modstander ville komme til bolden før ham. Ikke hans medspillere

					//Checking if the teammate will get to the ball first shouldn't be necessary if we can check that no opponents can get in the way of the pass
//					firstToBall = checkPass(p, false, visibleTeammates, visibleOpponents);
//					if (!firstToBall){
//						thisPlayersLowScore -= 90;
//						Sysout.print("thisPlayersLowScore6: " + thisPlayersLowScore, "AIPass");
//					}
				}

				//det er mindre smart at spille bolden tilbage til ham der lige har haft den hvis vi er på egen banehalvdel
				if (bold.getLastLastTouch() != null && bold.getLastLastTouch().equals(p) && Math.abs(bold.getX() - player.getMyTeam().getGoalX()) < 400){	
					thisPlayersLowScore -= 75;
					thisPlayersLowScoreInFront -= 75;
					Sysout.print("thisPlayersLowScore7: " + thisPlayersLowScore, "AIPass");
					Sysout.print("thisPlayersLowScoreInFront7: " + thisPlayersLowScoreInFront, "AIPass");
				}

				//If there is an opponent close to the team mate he may not be a great pass option
				if (distToClosestOpp < 50){
					thisPlayersLowScore *= (0.5 + distToClosestOpp / 100);
					thisPlayersLowScoreInFront *= (0.5 + distToClosestOpp / 100);
				}
				Sysout.print("thisPlayersLowScore8: " + thisPlayersLowScore, "AIPass");
				Sysout.print("thisPlayersLowScoreInFront8: " + thisPlayersLowScoreInFront, "AIPass");
				
				if (scoreLow < thisPlayersLowScore) {
					scoreLow = thisPlayersLowScore;
					targetLow = p;
				}

				if (scoreLowInFront < thisPlayersLowScoreInFront) {
					scoreLowInFront = thisPlayersLowScoreInFront;
					targetLowInfront = p;
					inFrontOfTarget = inFrontOfThisPlayer;
					Sysout.print("inFrontOfTarget: " + inFrontOfTarget, "AIPass");
					Sysout.print("player pos: " + targetLowInfront.getX() + ", " + targetLowInfront.getY(), "AIPass");
				}
				
				double thisPlayersHighScore = 0;

				double thisPlayersThroughScore = 0;

				for (int i = 0; i < throughLine.size() -1; i++){
            		
            		//Vi tjekker kun på det liniestykke der er ud for angriberen
            		if (throughLine.get(i).y > p.getYIn(15) && throughLine.get(i+1).y < p.getYIn(15)){
            			double lineDir = Math.PI * 2 + Math.atan2(throughLine.get(i+1).x - throughLine.get(i).getX(), throughLine.get(i+1).y - throughLine.get(i).y);
            			double lineX = throughLine.get(i).x + (Math.sin(lineDir) * (Math.abs(p.getYIn(15) - throughLine.get(i).y) / Math.abs(Math.cos(lineDir))));
            			
            			//hvis han er længere fremme end forsvarslinien
            			if (Math.abs(lineX - player.getOppTeam().getGoalX()) > Math.abs(p.getXIn(15) - player.getOppTeam().getGoalX())){

            				Point distPoint = Hjaelper.intersection(throughLine.get(i).x, throughLine.get(i).y, throughLine.get(i+1).x, throughLine.get(i+1).y, (int)p.getXIn(15), (int)p.getYIn(15), (int)(p.getXIn(15) + Math.sin(lineDir - Math.PI / 2) * 500), (int)(p.getYIn(15) + Math.cos(lineDir - Math.PI / 2) * 500));
            				double throughDist = Hjaelper.Distance(distPoint.x, distPoint.y, p.getXIn(15), p.getYIn(15));
//            				System.out.println(p.getShirtNumber() + " through dist: " + throughDist);
            				thisPlayersThroughScore = throughDist * 4;
            				
            				//How many percent of our speed is the player NOT using
            				double speedPercent = (1 - (p.getSpeed() / p.getTopSpeed()));
            				
            				//How far ahead of the team mate do we place the ball
            				int distAhead = (int)(p.speed / 3 + (p.acceleration * speedPercent) / 3);
            				
            				//Team mates direction straight forward
            				double dirForward = Math.atan2(p.getOppTeam().getGoalX() - p.x, 0);
            				
            				//The more the team mate needs to turn the less distance ahead of him 
            				double angleDiff = Math.abs(Hjaelper.angleDifference(p.getA(), dirForward));
            				if (angleDiff > 1) distAhead /= angleDiff * 2;
            				
            				Sysout.print("distAhead: " + distAhead, "AIPass");
            				Sysout.print("p.speed: " + p.speed, "AIPass");
            				Sysout.print("speedPercent: " + speedPercent, "AIPass");
            				Sysout.print("angleDiff: " + angleDiff, "AIPass");
            				
            				throughX = (int)p.getX() + distAhead;
            				if (player.getMyTeam().getGoalX() > player.getOppTeam().getGoalX())
            					throughX = (int)p.getX() - distAhead;
            				throughY = (int)p.getY();
            			}
            			i = throughLine.size()-1;
            		}
				}
				
				//If the teammate is close to the goal line it's not such a good idea to pass it in front of him
				//Even if he has a little space in front of him he won't be able to run much further
				if (Math.abs(p.getOppTeam().getGoalX() - p.getXIn(15)) < 100){
					thisPlayersThroughScore -= (100 - Math.abs(p.getOppTeam().getGoalX() - p.getXIn(15)));
				}
				
				//hvis medspilleren er offside er han rigtig daarlig at spille til
				//usikkerhed afhængig af vision?
				if (Hjaelper.Distance(p.getX(), p.getY(), player.getOppTeam().getGoalX(), p.getY()) < player.getOppTeam().getOffsideDistToGoal()){
					thisPlayersThroughScore -= 900;
				}

//				thisPlayersThroughScore += afstand;
		
//				System.out.println("Through " + throughScore1 + " " + throughScore2 + " " + throughScore3 + " - " + p.getShirtNumber());
				//vi trækker fra hvis medspilleren er længere væk end vi kan aflevere
				
				if (player.convertDistToPassPow(Hjaelper.Distance(throughX, throughY, bold.getX(), bold.getY()), true) > player.getMaxPassPow())
					thisPlayersThroughScore -= 30 * player.convertDistToPassPow(Hjaelper.Distance(throughX, throughY, bold.getX(), bold.getY()), true) - player.getMaxPassPow();

				//TODO: If the player is through he should decide to run with the ball in AIRunWIthBall and not think about it when checking passing options
				if (igennem > 0){
//					System.out.println("Igennem: " + igennem);
					thisPlayersThroughScore -= igennem * 2;
				}
				
				//Hvis det er targetman kan vi spille op på ham
				if (p.equals(player.getMyTeam().getpTargetMan())){
					thisPlayersHighScore = p.getFreeFactor() + 20 - (Math.abs(p.getX() - player.getOppTeam().getGoalX()) - player.getOppTeam().getOffsideDistToGoal()) / 10;
				}
				else
					thisPlayersHighScore = p.getFreeFactor() / ((Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) / 100) + 1);

				//						System.out.println("High: " + thisPlayersHighScore);

				//Hvis medspilleren er længere tilbage end dig, så er der mindre chance for at han bliver spillet
				if (player.getMyTeam().getGoalX() < player.getOppTeam().getGoalX()){
					if (player.getX() > p.getX() + 50)
						thisPlayersHighScore -= Math.abs(p.getX() - player.getX()) + 60;
				}
				else{ 
					if (player.getX() < p.getX() - 50)
						thisPlayersHighScore -= Math.abs(p.getX() - player.getX()) + 60;
				}
//				System.out.println("High 1: " + thisPlayersHighScore + "(" + p.shirtNumber + ")");
				// spillere tættere på end 250 får trukket alt under 250 fra
				if (Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) < 250){
					thisPlayersHighScore -= (250 - Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY())) * 2;
				}
				// spillere længere væk end 250 får trukket alt over 250 fra - HVORFOR?
				if (Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY()) > 250){
//					thisPlayersHighScore -= (Hjaelper.Distance(player.getX(), player.getY(), p.getX(), p.getY())-250) * 2;
				}
				
//				System.out.println("High 2: " + thisPlayersHighScore + "(" + p.shirtNumber + ")");
				//hvis medspilleren er offside er han rigtig daarlig at spille til
				if (Hjaelper.Distance(p.getX(), p.getY(), player.getOppTeam().getGoalX(), p.getY()) < player.getOppTeam().getOffsideDistToGoal())
					thisPlayersHighScore -= 100;

				// det er han også hvis han ikke kan få fat i afleveringen
//				if (!checkPass(p, true, player.myTeam.players, player.oppTeam.players)){
//					thisPlayersHighScore -= 100;
//				}

				//hvis vi kan lave en lav aflevering til ham er der ingen grund til at lave en høj
				if (thisPlayersLowScore > thisPlayersHighScore / 2 && thisPlayersLowScore > 15){
					//thisPlayersHighScore = thisPlayersLowScore -1;
				}
				
				//The player still needs to have the vision to see the through pass
				Random rand = new Random();
				if (player.vision / 2 - 15 < rand.nextInt(76))
					thisPlayersThroughScore *= 0.7;

				if (p != null && throughX > 0 && throughY > 0 && thisPlayersThroughScore > scoreThrough){
					scoreThrough = thisPlayersThroughScore;
					targetThrough = p;
					passX = throughX;
					passY = throughY;
				}
					
				if (thisPlayersHighScore > scoreHigh){
					scoreHigh = thisPlayersHighScore;
					targetHigh = p;
				}

				//							System.out.println(p.shirtNumber + " - " + "minD: " + minD + ", lowscore: " + thisPlayersLowScore + 
				//									", freefactor: " + p.getFreeFactor() + ", highscore: " + thisPlayersHighScore + 
				//									", throughscore: " + thisPlayersThroughScore);
			}


		}

		
		//find taktik for short/long passing og tag det med
		double sh = 50.0 / (double)player.getShortLongPassing();
		Sysout.print("shortLongPassing: " + player.getShortLongPassing(), "AIPass");
		double lo = (double)player.getShortLongPassing() / 50.0;
		double th = (double)player.getThroughballs() / 100.0;


		Sysout.print("scoreLow: " + scoreLow, "AIPass");
		Sysout.print("scoreLowInFront: " + scoreLowInFront, "AIPass");
		scoreLow *= sh;
		scoreLowInFront *= sh;
		Sysout.print("scoreLow: " + scoreLow, "AIPass");
		scoreHigh *= lo;
		scoreThrough *= th;
		
		
		//scoreLow is kind of really replaced by scoreLowInFront now since it doesn't make sense to pass the ball behind a team mate
		//which is what will happen with scoreLow if the team mate is running towards the opponents goal
		scoreLow = -900;
		
		//If there are no opponents close to the player, there's no need to pass the ball (should stop the needless first-time passes in defence when they are not pressed)
		//Commented out because it means less chance of passing to a team mate open in front of goal if the player in posession has no opps close to him
//		if (closestOppToMe > 50){
//			scoreLowInFront -= (closestOppToMe - 50) / 2;
//		}
		
		double timeOnBallDiff = player.getBold().getTimeOnBallDiff();
		
		//Make sure that players want to hold on to the ball more
		scoreLowInFront -= timeOnBallDiff;
		scoreHigh -= timeOnBallDiff;
		
		//If there isn't actually a target don't pass
		if (targetLowInfront == null)
			scoreLowInFront = -9999;
		if (targetLow == null)
			scoreLow = -9999;
		if (targetThrough == null)
			scoreThrough = -9999;
		if (targetHigh == null)
			scoreHigh = -9999;
		
		
		if (scoreLowInFront > scoreHigh &&
			scoreLowInFront > scoreThrough &&
				(scoreLowInFront > scoreLow ||
				 (scoreLowInFront * 1.3 > scoreLow && 
				  scoreLowInFront > 25))){
			
			scorePass = scoreLowInFront;
			passType = "lowInFront";
			passTarget = targetLowInfront;
		}
		else if (scoreLow > scoreHigh){
			if (scoreLow > scoreThrough){
				scorePass = scoreLow;
				passType = "low";
				passTarget = targetLow;
			}
			else{
				scorePass = scoreThrough;
				passType = "through";
				passTarget = targetThrough;
			}
		}
		else{
			if (scoreHigh > scoreThrough){
				scorePass = scoreHigh;
				passType = "high";
				passTarget = targetHigh;
			}
			else{
				scorePass = scoreThrough;
				passType = "through";
				passTarget = targetThrough;
			}
		}
		
		Sysout.print("passType: " + passType, "AIPass");
		
		//Hvis man er igennem så skal man ikke spille den!
		//TODO: Being through doesn't matter when checking passes. If the player is through he should get a high score from airunwithball
		scorePass -= igennem * 4;
		
		return scorePass;
	}
	
	public void pass(int boldDif, int pressure, int timeOnBall){
		
		System.out.println("Pass: " + passType);
		Sysout.print("Pass: " + passType, "AIPass");
		
		if (passType == "high")
			pass(passTarget, true, boldDif, new Point((int)passTarget.getX(), (int)passTarget.getY()), pressure, timeOnBall);
		else if(passType == "low")
			pass(passTarget, false, boldDif, new Point((int)passTarget.getX(), (int)passTarget.getY()), pressure, timeOnBall);
		else if(passType == "lowInFront")
			pass(passTarget, false, boldDif, inFrontOfTarget, pressure, timeOnBall);
		else{
			throughPass(passTarget, (int)passX, (int)passY, boldDif, pressure, timeOnBall);
		}
	}
	
	public void throughPass(Player target, int x, int y, int boldDif, int pressure, int timeOnBall){
		//		System.out.println("boldDif i throughpass start: " + boldDif);
		bold.setCross(false);

		player.speed *= 0.8;

		player.getOppTeam().getOppsMarked().clear();

		double tX = x;
		double tY = y;

		double a = Math.atan2((tX - player.getX()), (tY - player.getY()));

		//hoej bold medmindre der ikke er modstandere i vejen
		boolean high = false;
		double tempX = player.getX() + (10 * Math.sin(a));
		double tempY = player.getY() + (10 * Math.cos(a));

		int limitWhile = 1000;
		while (Hjaelper.Distance(tempX, tempY, tX, tY) > 10 && high == false && limitWhile > 0){
			limitWhile--;
			tempX += (10 * Math.sin(a));
			tempY += (10 * Math.cos(a));

			for (Player pm : player.getOppTeam().getPlayers()){
				if (Hjaelper.Distance(tempX, tempY, pm.getX(), pm.getY()) < 20)
					high = true;
			}
		}

		double off = AIHelper.directionError(boldDif, pressure, player, AIHelper.PlayerAction.ThroughPass);
		//Midlertidigt fjernet
		a += off;
		Sysout.print("Off 1: " + off, "AIPass");

		double vinkelPaaBold = Hjaelper.angleDifference(a, player.getA());
		if (vinkelPaaBold < 1) vinkelPaaBold = 1;
		else vinkelPaaBold = (vinkelPaaBold / 5) + 1;

		//		if (match.getState() == MatchState.ON && vinkelPaaBold > Math.PI / 10 + 1){
		//			turnWithBall(a);
		//		}

		//set boldens fart og evt. højde efter hvor langt v¾k modtageren er
//		double diff = ((r.nextInt(boldDif) - r.nextInt(boldDif)) * 150) / (player.passing + 50);

		//		Sysout.print("diff ved throughPass styrke: " + diff);

		double power = player.convertDistToPassPow(Hjaelper.Distance(tX, tY, player.getX(), player.getY()), high);
		//power += diff;
		//fart må ikke være højere end shotpower...skal udbygges
		if (power > player.getMaxPassPow())
			power = player.getMaxPassPow();

		int r1 = r.nextInt(boldDif);
		//		Sysout.print("r1: " + r1);
		int r2 = r.nextInt(boldDif);
		//		Sysout.print("r2: " + r2);

//		diff = ((r.nextInt(boldDif) - r.nextInt(boldDif)) * 150) / (player.passing + 50);

		//		Sysout.print("diff ved throughPass højde: " + diff + ", boldDif: " + boldDif);

		double h = 15 + (Hjaelper.Distance(tX, tY, player.getX(), player.getY())) * 0.07;
		System.out.println("H: " + h);
		if (h > 25) h = 25;
		if (high){
			bold.setZForce(h);
			power -= h / 7;
		}
		player.checkTeammatesOffside();

		player.setPlayerAction(PlayerAction.passing);
		if (target!=null) target.setPlayerAction(PlayerAction.receiving_pass);
		bold.setLastTouch(player);
		bold.setA(a);
		bold.setSpeed(power);
		bold.setPassTo(target);
		bold.setPassFrom(player);
		bold.setShot(false);

		player.wait += 300;
		player.setState(player.getStatePassing());

		player.setMatchMessage("Passes the ball in space to " + target.getLastname());
		player.playerMatchStats.passes++;
		player.playerMatchStats.throughPasses++;
	}
	
	public void pass(Player target, boolean high, int boldDif, Point targetPoint, int pressure, int timeOnBall){

		bold.setCross(false);

		player.speed *= 0.6;

		player.getOppTeam().getOppsMarked().clear();

		bold.setBallTimer(System.currentTimeMillis());//her sættes sidste gang bolden ændrede retning
		double tX = target.getX();
		double tY = target.getY();
		double tA = target.getA();

		tX += (target.getTopSpeed() / 4) * Math.sin(tA);
		tY += (target.getTopSpeed() / 4) * Math.cos(tA);

		tX = targetPoint.x;
		tY = targetPoint.y;
		
		//set boldens fart efter hvor langt v¾k modtageren er
		double diff = (r.nextDouble() - r.nextDouble()) * (boldDif * 4) / player.passing;
		double power = player.convertDistToPassPow(Hjaelper.Distance(tX, tY, bold.getX(), bold.getY()), high);
		
		//PowerDirect is the power neede to pass directly to the player and not in front of him
		//If powerDirect is greater than power we use powerDirect and the players actual position to avoid weak passes
		double powerDirect = player.convertDistToPassPow(Hjaelper.Distance(target.getX(), target.getY(), bold.getX(), bold.getY()), high);
		
//		if (powerDirect > power){
//			power = powerDirect;
//			tX = target.getX();
//			tY = target.getY();
//		}
		
		power += diff;
		
		System.out.println("tX: " + tX);
		System.out.println("tY: " + tY);
		
		double a = Math.atan2((tX - bold.getX()), (tY - bold.getY()));

		double off = AIHelper.directionError(boldDif, pressure, player, AIHelper.PlayerAction.Pass);
		
		a += off;
		Sysout.print("Passing: " + player.passing + " / bolddif: " + boldDif + " / Off 2: " + off, "AIPass");
		
		if (Settings.SO){
//			Sysout.print("passing Bolddif: " + boldDif + " for " + lastname + " som har " + passing + " i passing");
//			Sysout.print("passing OFF: " + off);
		}
		
		double vinkelPaaBold = Hjaelper.angleDifference(a, player.getA());
		if (vinkelPaaBold < 1) vinkelPaaBold = 1;
		else vinkelPaaBold = (vinkelPaaBold / 5) + 1;

		

		//fart må ikke være højere end shotpower...skal udbygges
		if (power > player.getMaxPassPow())
			power = player.getMaxPassPow();

		double h = 15 + Hjaelper.Distance(tX, tY, player.getX(), player.getY()) / 20;
		if (h > 100) h = 100;

		player.setPlayerAction(PlayerAction.passing);
		target.setPlayerAction(PlayerAction.receiving_pass);
		bold.setLastTouch(player);
		bold.setA(a);
		bold.setBallTimer(System.currentTimeMillis());//her skal bolden ændre retning
		if (high){
			player.playerMatchStats.longPasses++;
//			Sysout.print("Hoej aflevering");
			bold.setZForce(h);
			power -= h / 4;
		}
		else{
			
			bold.setZ(5);
			bold.setZForce((power + r.nextInt(30)) / (r.nextInt(15)+(player.passing / 10)));
			if (Settings.SO) Sysout.print("Lav aflevering", "AIPass");
		}

		bold.setSpeed(power);
		bold.setPassTo(target);
		bold.setPassFrom(player);
		bold.setShot(false);

		player.checkTeammatesOffside();
		player.setMatchMessage("passes the ball to " + target.getLastname());
		player.playerMatchStats.passes++;

		player.wait += 300;
		player.setState(player.getStatePassing());
	}
	
	//hvis vi afleverede til tm ville han så komme først til bolden?
//	private boolean checkPass(Player tm, boolean high, ArrayList<Player> teamA, ArrayList<Player> teamB){
//		if (Settings.DEBUG) Sysout.print("checkpass start", "AIPass");
//		double tz = bold.getZForce();
//
//		double tX = tm.getX();
//		double tY = tm.getY();
//		double tA = tm.getA();
//
//		tX += (tm.getTopSpeed() / 3) * Math.sin(tA);
//		tY += (tm.getTopSpeed() / 3) * Math.cos(tA);
//
//		double a = Math.atan2((tX - player.getX()), (tY - player.getY()));
//
//		double power = ((90 + r.nextInt(15) + Hjaelper.Distance(tX, tY, player.getX(), player.getY()) * 0.45));
//		double h = 8 + Hjaelper.Distance(tX, tY, player.getX(), player.getY()) / 15;
//		if (h > 100) h = 100;
//
//		if (high){
//			bold.setZForce(h);
//			power -= h;
//		}
//
//		boolean result;
//		ArrayList<Player> mt = new ArrayList<Player>();
//		for (Player p : player.getMyTeam().getPlayers())
//			if (!p.equals(player))
//				mt.add(p);
//		
//		Player tp = Hjaelper.firstToBall(mt, player.getOppTeam().getPlayers(), bold, player.getSettings(), a, power);
//		result = tp.getMyTeam().equals(tm.getMyTeam());
//
//		bold.setZForce(tz);
//
//		if (Settings.DEBUG) Sysout.print("checkpass slut", "AIPass");
//		return result;
//	}
}
