package ai;

import java.awt.Point;
import java.util.Random;

import model.Bold;
import model.ComplexAction;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.Settings;
import model.ComplexAction.Action;
import model.Sysout;

public class AIDribble{

	//Hvilken form for dribling skal udføres og hvorhen?
	String dribbleAction = "";
	Point dribbleTarget = new Point();
	Point runTarget = new Point();
	Point dribbleVia = new Point();

	double XLeftBehindOpp = 0;
	double YLeftBehindOpp = 0;
	double XLeftOfOpp = 0;
	double YLeftOfOpp = 0;
	double XRightBehindOpp = 0;
	double YRightBehindOpp = 0;
	double XRightOfOpp = 0;
	double YRightOfOpp = 0;
	
	private Player player;
	private Random r;
	private Bold bold;

	public AIDribble(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}

	public double getScore(int oppsCloserToGoal, double pressure, Player pClosestOppInFront) {

		double scoreDribble = 0;
		dribbleAction = "";
		dribbleTarget.x = player.getOppTeam().getGoalX();
		dribbleTarget.y = player.getOppTeam().getGoalY() + 29;
		
		
		if (pClosestOppInFront != null){

			//Hvis den nærmest modstander tættere på mål er et stykke væk er der ingen grund til at begynde at drible uden om ham - endnu. - udkommenteret
			//fordi det også handler om modstandere ved siden af og bagved spilleren (hvilket er med under pressure)
//			if (pClosestOppInFront != null && Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getX(), pClosestOppInFront.getY()) > 80)
//				scoreDribble = -30;


			//Hvis pClosestOppInFront == null er der ingen forsvarer foran angriberen
			if (pClosestOppInFront != null){

				double distToOpp = Hjaelper.Distance(player.getX(), player.getY(), pClosestOppInFront.getX(), pClosestOppInFront.getY());
				
				//Punktet bag, til højre for og til venstre for forsvareren og hvor tæt den nærmeste anden forsvarer er på punkterne
				double dirBoldToClosestOpp = Math.atan2(pClosestOppInFront.getX() - bold.getX(), pClosestOppInFront.getY() - bold.getY());
				double dirBoldToClosestOppIn15 = Math.atan2(pClosestOppInFront.getXIn(15) - bold.getX(), pClosestOppInFront.getYIn(15) - bold.getY());
				double XBehindOpp = pClosestOppInFront.getXIn(15) + 40 * Math.sin(dirBoldToClosestOppIn15);
				double YBehindOpp = pClosestOppInFront.getYIn(15) + 40 * Math.cos(dirBoldToClosestOppIn15);

				//addToDirs is how much we adjust the point left and right of the opp based on how close we are to him
				//the closer we are the more we need to adjust so we don't run into him
				double addToDirs = 0;
				if (Hjaelper.Distance(pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15), bold.getX(), bold.getY()) < 40)
					addToDirs = (40 - Hjaelper.Distance(pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15), bold.getX(), bold.getY())) / 70;
				
				//dribbleDist is how far we need to take the ball 
				double dribbleDist = 40;
				if (addToDirs > 0) dribbleDist = 40 + addToDirs * 20;
				
				Sysout.print("addToDirs: " + addToDirs, "aidribble.scores");
				
				double dirStraight = Math.atan2(dribbleTarget.x - bold.getX(), dribbleTarget.y - bold.getY());
				double XStraight = bold.getX() + dribbleDist * Math.sin(dirStraight);
				double YStraight = bold.getY() + dribbleDist * Math.cos(dirStraight);
				
				double dirRight = dirBoldToClosestOppIn15 + Math.PI / 2 + addToDirs;
				XRightOfOpp = pClosestOppInFront.getXIn(15) + dribbleDist * Math.sin(dirRight);
				YRightOfOpp = pClosestOppInFront.getYIn(15) + dribbleDist * Math.cos(dirRight);
				
				//If going straight towards our target is further away from the defender than going around him and closer to the player than the defender
				//and the player is closer to the straight ahead target than the defender
				//it's because we're already half way around him and we need to just continue towards target instead of cutting in
				if (Hjaelper.Distance(XStraight, YStraight, pClosestOppInFront.getX(), pClosestOppInFront.getY()) >
					Hjaelper.Distance(XRightOfOpp, YRightOfOpp, pClosestOppInFront.getX(), pClosestOppInFront.getY()) &&
					Hjaelper.Distance(XStraight, YStraight, player.getX(), player.getY()) <
					Hjaelper.Distance(XRightOfOpp, YRightOfOpp, player.getX(), player.getY()) &&
					Hjaelper.Distance(XStraight, YStraight, pClosestOppInFront.getX(), pClosestOppInFront.getY()) - 4 >
					Hjaelper.Distance(XStraight, YStraight, player.getX(), player.getY())){
					
					XRightOfOpp = XStraight;
					YRightOfOpp = YStraight;
					Sysout.print("Going straight instead of right", "aidribble.scores");
				}
				
				double distRightOfOpp = Hjaelper.Distance(XRightOfOpp, YRightOfOpp, player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 30);

				XRightBehindOpp = XRightOfOpp + dribbleDist * Math.sin(dirBoldToClosestOppIn15);
				YRightBehindOpp = YRightOfOpp + dribbleDist * Math.cos(dirBoldToClosestOppIn15);

				
				double dirLeft = dirBoldToClosestOppIn15 - Math.PI / 2 - addToDirs;
				XLeftOfOpp = pClosestOppInFront.getXIn(15) + dribbleDist * Math.sin(dirLeft);
				YLeftOfOpp = pClosestOppInFront.getYIn(15) + dribbleDist * Math.cos(dirLeft);
				
				//If going straight towards our target is further away from the defender than going around him and closer to the player than the defender
				//and the player is closer to the straight ahead target than the defender
				//it's because we're already half way around him and we need to just continue towards target instead of cutting in
				if (Hjaelper.Distance(XStraight, YStraight, pClosestOppInFront.getX(), pClosestOppInFront.getY()) >
					Hjaelper.Distance(XLeftOfOpp, YLeftOfOpp, pClosestOppInFront.getX(), pClosestOppInFront.getY()) &&
					Hjaelper.Distance(XStraight, YStraight, player.getX(), player.getY()) <
					Hjaelper.Distance(XLeftOfOpp, YLeftOfOpp, player.getX(), player.getY()) &&
					Hjaelper.Distance(XStraight, YStraight, pClosestOppInFront.getX(), pClosestOppInFront.getY()) - 4 >
					Hjaelper.Distance(XStraight, YStraight, player.getX(), player.getY())){
					
					XLeftOfOpp = XStraight;
					YLeftOfOpp = YStraight;
					Sysout.print("Going straight instead of left", "aidribble.scores");
				}

				double distLeftOfOpp = Hjaelper.Distance(XLeftOfOpp, YLeftOfOpp, player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 30);

				XLeftBehindOpp = XLeftOfOpp + dribbleDist * Math.sin(dirBoldToClosestOppIn15);
				YLeftBehindOpp = YLeftOfOpp + dribbleDist * Math.cos(dirBoldToClosestOppIn15);

				double spaceRightBehindOpp = 99999;
				double spaceLeftBehindOpp = 99999;
				double spaceRightOfOpp = 99999;
				double spaceLeftOfOpp = 99999;
				for (Player pl : player.getOppTeam().getPlayers()){
					if (!pl.equals(pClosestOppInFront) &&
							Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29) -10 <
							Hjaelper.Distance(pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29)){

						if (Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XBehindOpp, YBehindOpp) < spaceRightBehindOpp)
							spaceRightBehindOpp = Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XRightBehindOpp, YRightBehindOpp);

						if (Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XBehindOpp, YBehindOpp) < spaceLeftBehindOpp)
							spaceLeftBehindOpp = Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XLeftBehindOpp, YLeftBehindOpp);

						//If the opponent is further away from goal than the player he's not important in terms of the space next to the opponent
						if (Math.abs(pl.getXIn(15) - pl.getMyTeam().getGoalX()) - 35 < Math.abs(player.getX() - pl.getMyTeam().getGoalX()) &&
								Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XRightOfOpp, YRightOfOpp) < spaceRightOfOpp)
							spaceRightOfOpp = Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XRightOfOpp, YRightOfOpp);

						//If the opponent is further away from goal than the player he's not important in terms of the space next to the opponent
						if (Math.abs(pl.getXIn(15) - pl.getMyTeam().getGoalX()) - 35 < Math.abs(player.getX() - pl.getMyTeam().getGoalX()) &&
								Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XLeftOfOpp, YLeftOfOpp) < spaceLeftOfOpp)
							spaceLeftOfOpp = Hjaelper.Distance(pl.getXIn(15), pl.getYIn(15), XLeftOfOpp, YLeftOfOpp);
					}
				}

				//dirScores: All of these are closer to 0 the more the direction points to the target
				//I.e. myDirScoreLeft will be close to 0 if the players direction points towards the point to the left of the defender
				
				//Hvor meget vender vi væk fra forsvareren eller punktet til venstre / højre for ham
				double myDirScore = Math.abs(Hjaelper.angleDifference(player.getA(), Math.atan2(pClosestOppInFront.getXIn(15) - player.getX(), pClosestOppInFront.getYIn(15) - player.getY())));
				double myDirScoreLeft = Hjaelper.angleDifference(player.getA(), Math.atan2(XLeftOfOpp - player.getX(), YLeftOfOpp - player.getY()));
				double myDirScoreRight = Hjaelper.angleDifference(player.getA(), Math.atan2(XRightOfOpp - player.getX(), YRightOfOpp - player.getY()));

				//Hvor meget vender forsvareren væk fra os
				double oppDirScore = Math.abs(Hjaelper.angleDifference(pClosestOppInFront.getA(), Math.atan2(player.getX() - pClosestOppInFront.getXIn(15), player.getY() - pClosestOppInFront.getYIn(15))));
				double oppDirScoreLeft = Hjaelper.angleDifference(pClosestOppInFront.getA(), Math.atan2(XLeftOfOpp - pClosestOppInFront.getXIn(15), YLeftOfOpp - pClosestOppInFront.getYIn(15)));
				double oppDirScoreRight = Hjaelper.angleDifference(pClosestOppInFront.getA(), Math.atan2(XRightOfOpp - pClosestOppInFront.getXIn(15), YRightOfOpp - pClosestOppInFront.getYIn(15)));

				//Er venstre og højre om forsvarsspilleren på vej mod mål?
				double goalDirScoreLeft = Hjaelper.angleDifference(Math.atan2(player.getOppTeam().getGoalX() - player.getX(), player.getOppTeam().getGoalY() + 29 - player.getY()), 
						Math.atan2(XLeftOfOpp - player.getX(), YLeftOfOpp - player.getY()));
				double goalDirScoreRight = Hjaelper.angleDifference(Math.atan2(player.getOppTeam().getGoalX() - player.getX(), player.getOppTeam().getGoalY() + 29 - player.getY()), 
						Math.atan2(XRightOfOpp - player.getX(), YRightOfOpp - player.getY()));

				//How close is the opp to our line towards target? 
				double closestOppDistToTargetLine = Hjaelper.distToLine(pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15), player.x, player.y, dribbleTarget.x, dribbleTarget.y);
				
				Sysout.print("Trøjenummer der skal afdribles - x,y:", "aidribble.scores");
				Sysout.print(pClosestOppInFront.getShirtNumber() + " - " + pClosestOppInFront.getX() + "," + pClosestOppInFront.getY(), "aidribble.scores");
				Sysout.print("spaceRightBehindOpp: " + spaceRightBehindOpp, "aidribble.scores");
				Sysout.print("spaceLeftBehindOpp: " + spaceLeftBehindOpp, "aidribble.scores");
				Sysout.print("spaceRightOfOpp: " + spaceRightOfOpp, "aidribble.scores");
				Sysout.print("spaceLeftOfOpp: " + spaceLeftOfOpp, "aidribble.scores");
				Sysout.print("myDirScore: " + myDirScore, "aidribble.scores");
				Sysout.print("myDirScoreLeft: " + myDirScoreLeft, "aidribble.scores");
				Sysout.print("myDirScoreRight: " + myDirScoreRight, "aidribble.scores");
				Sysout.print("oppDirScore: " + oppDirScore, "aidribble.scores");
				Sysout.print("oppDirScoreLeft: " + oppDirScoreLeft, "aidribble.scores");
				Sysout.print("oppDirScoreRight: " + oppDirScoreRight, "aidribble.scores");
				Sysout.print("goalDirScoreLeft: " + goalDirScoreLeft, "aidribble.scores");
				Sysout.print("goalDirScoreRight: " + goalDirScoreRight, "aidribble.scores");
				Sysout.print("Left of opp / rigt of opp: " + XLeftOfOpp + "," + YLeftOfOpp + " - " + XRightOfOpp + "," + YRightOfOpp, "aidribble.scores");

				//Divide dribblescores by adjustToStandard so they end up somewhere between 0-100 and balanced with other scores (shoot, pass etc.)
				double adjustToStandard = 5;
				
				//The close we are to the opp the more difficult it become to change direction with a simple drible so distToOpp factors in here
				double distToOppFactor = 1;
				if (distToOpp < 30) distToOppFactor = 1 + (30 - distToOpp) / 15;
				Sysout.print("distToOppFactor: " + distToOppFactor, "aidribble.scores");
				
				//Små driblinger rundt om forsvareren kan altid prøves
				double dribbleLeftScore = 0;
				
				if (spaceLeftOfOpp > 60)
					dribbleLeftScore += (60 - 15) / adjustToStandard;
				else
					dribbleLeftScore += (spaceLeftOfOpp - 15) / adjustToStandard; 
				
				Sysout.print("dribbleLeftScore1: " + dribbleLeftScore, "aidribble.scores");
				
				if (spaceLeftBehindOpp > 60)
					dribbleLeftScore += (60 - 15) / adjustToStandard;
				else
					dribbleLeftScore += (spaceLeftBehindOpp - 15) / adjustToStandard; 

				Sysout.print("dribbleLeftScore2: " + dribbleLeftScore, "aidribble.scores");

				//The closer we are to the opp the more difficult it become to change direction with a simple drible so distToOpp factors in here
				dribbleLeftScore += (scoreFromDirScoreBasedOn(myDirScoreLeft, Math.PI / 2) * distToOppFactor) / adjustToStandard;
				Sysout.print("dribbleLeftScore3: " + dribbleLeftScore, "aidribble.scores");
				dribbleLeftScore += (scoreFromDirScore(oppDirScoreLeft) / distToOppFactor) / adjustToStandard;
				Sysout.print("dribbleLeftScore4: " + dribbleLeftScore, "aidribble.scores");
				
				dribbleLeftScore += scoreFromDirScoreBasedOn(goalDirScoreLeft, Math.PI / 2) / adjustToStandard;
				Sysout.print("dribbleLeftScore5: " + dribbleLeftScore, "aidribble.scores");
				
				//At least 40 in dribbling would be good before trying even basic dribbling
				if (player.getDribbling() < 40)
					dribbleLeftScore += (player.getDribbling() - 40);
				Sysout.print("dribbleLeftScore6: " + dribbleLeftScore, "aidribble.scores");
				
				//Better topspeed than the opp is a good thing too
				if (player.getTopSpeed() < pClosestOppInFront.getTopSpeed())
					dribbleLeftScore += (player.getTopSpeed() - pClosestOppInFront.getTopSpeed()) / 4;
				Sysout.print("dribbleLeftScore7: " + dribbleLeftScore, "aidribble.scores");
				
				//Same goes for acceleration
				if (player.getAcceleration() < pClosestOppInFront.getAcceleration())
					dribbleLeftScore += (player.getAcceleration() - pClosestOppInFront.getAcceleration()) / 2;
				Sysout.print("dribbleLeftScore8: " + dribbleLeftScore, "aidribble.scores");
				
				//And agility acceleration
				if (player.getAgility() < pClosestOppInFront.getAgility())
					dribbleLeftScore += (player.getAgility() - pClosestOppInFront.getAgility()) / 3;
				Sysout.print("dribbleLeftScore9: " + dribbleLeftScore, "aidribble.scores");
				
				if (dribbleLeftScore > scoreDribble){
					scoreDribble = dribbleLeftScore;
					dribbleAction = "ShortDribbles";
					
					
					dribbleTarget.x = (int)XLeftOfOpp;
					dribbleTarget.y = (int)YLeftOfOpp;
				}
				
				double dribbleRightScore = 0;
				if (spaceRightOfOpp > 70)
					dribbleRightScore += (70 - 30) / adjustToStandard;
				else
					dribbleRightScore += (spaceRightOfOpp - 30) / adjustToStandard; 
				Sysout.print("dribbleRightScore1: " + dribbleRightScore, "aidribble.scores");
				
				if (spaceRightBehindOpp > 70)
					dribbleRightScore += (70 - 30) / adjustToStandard;
				else
					dribbleRightScore += (spaceRightBehindOpp - 30) / adjustToStandard; 
				Sysout.print("dribbleRightScore2: " + dribbleRightScore, "aidribble.scores");
				
				//The closer we are to the opp the more difficult it become to change direction with a simple drible so distToOpp factors in here
				dribbleRightScore += (scoreFromDirScoreBasedOn(myDirScoreRight, Math.PI / 2) * distToOppFactor) / adjustToStandard;
				Sysout.print("dribbleRightScore3: " + dribbleRightScore, "aidribble.scores");
				dribbleRightScore += (scoreFromDirScore(oppDirScoreRight) / distToOppFactor) / adjustToStandard;
				Sysout.print("dribbleRightScore4: " + dribbleRightScore, "aidribble.scores");
				
				dribbleRightScore += scoreFromDirScoreBasedOn(goalDirScoreRight, Math.PI / 2) / adjustToStandard;
				Sysout.print("dribbleRightScore5: " + dribbleRightScore, "aidribble.scores");
				
				//At least 40 in dribbling would be good before trying even basic dribbling
				if (player.getDribbling() < 40)
					dribbleRightScore += (player.getDribbling() - 40);
				Sysout.print("dribbleRightScore6: " + dribbleRightScore, "aidribble.scores");
				
				//Better topspeed than the opp is a good thing too
				if (player.getTopSpeed() < pClosestOppInFront.getTopSpeed())
					dribbleRightScore += (player.getTopSpeed() - pClosestOppInFront.getTopSpeed()) / 4;
				Sysout.print("dribbleRightScore7: " + dribbleRightScore, "aidribble.scores");
				
				//Same goes for acceleration
				if (player.getAcceleration() < pClosestOppInFront.getAcceleration())
					dribbleRightScore += (player.getAcceleration() - pClosestOppInFront.getAcceleration()) / 2;
				Sysout.print("dribbleRightScore8: " + dribbleRightScore, "aidribble.scores");
				
				//And agility acceleration
				if (player.getAgility() < pClosestOppInFront.getAgility())
					dribbleRightScore += (player.getAgility() - pClosestOppInFront.getAgility()) / 3;
				Sysout.print("dribbleRightScore9: " + dribbleRightScore, "aidribble.scores");
				
				if (dribbleRightScore > scoreDribble){
					scoreDribble = dribbleRightScore;
					dribbleAction = "ShortDribbles";
					dribbleTarget.x = (int)XRightOfOpp;
					dribbleTarget.y = (int)YRightOfOpp;
				}

				//Spille bolden den ene vej rundt om forsvareren og løbe den anden vej
				//Er smart hvis: Der er plads bag forsvareren til at spille bolden ned i, 
				//der er lidt plads på begge sider af forsvarsspilleren til bolden og angriberen,
				//angriberen er i fart i nogenlunde den retning han skal løbe i,
				//forsvarsspilleren ikke er i fart eller ikke har nogenlunde den retning han skal løbe i.

				adjustToStandard = 4;
				
				
				//Some of the conditions HAVE to be right for this to work. A simple score isn't enough because
				//a lot of space could give a decent score even if the player is facing the wrong way
				if (spaceLeftOfOpp > 40 &&
						spaceLeftBehindOpp > 75 &&
						spaceRightOfOpp > 40 &&
						spaceRightBehindOpp > 70 &&
						Math.abs(myDirScoreRight) < 1 &&
						Math.abs(oppDirScore) < 1 &&
						Math.abs(goalDirScoreLeft) < 1.4 &&
						player.topSpeed - pClosestOppInFront.topSpeed > -20 &&
						(player.speed > player.topSpeed * 0.4 || pClosestOppInFront.speed > pClosestOppInFront.topSpeed * 0.25) &&
						Math.abs(40 - distToOpp) < 20
						
						//Disabled for now as they often lose the ball doing this
						&& 1 == 2
				){
					
					//scoreRunAroundRight: spille bolden venstre om forsvareren og selv løbe højre om
					double scoreRunAroundRight = 0;
					
					if (spaceLeftOfOpp > 100)
						scoreRunAroundRight += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundRight += (spaceLeftOfOpp - 50) / adjustToStandard; 
					Sysout.print("scoreRunAroundRight1: " + scoreRunAroundRight, "aidribble.scores");
					
					if (spaceLeftBehindOpp > 150)
						scoreRunAroundRight += (150 - 70) / adjustToStandard;
					else
						scoreRunAroundRight += (spaceLeftBehindOpp - 70) / adjustToStandard; 
					Sysout.print("scoreRunAroundRight2: " + scoreRunAroundRight, "aidribble.scores");
					
					if (spaceRightOfOpp > 100)
						scoreRunAroundRight += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundRight += (spaceRightOfOpp - 50) / adjustToStandard; 
					Sysout.print("scoreRunAroundRight3: " + scoreRunAroundRight, "aidribble.scores");
					
					if (spaceRightBehindOpp > 100)
						scoreRunAroundRight += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundRight += (spaceRightBehindOpp - 50) / adjustToStandard; 
					Sysout.print("scoreRunAroundRight4: " + scoreRunAroundRight, "aidribble.scores");
					
					//The directions the two players are facing are more important the more speed they have 
					scoreRunAroundRight += scoreFromDirScoreBasedOn(myDirScoreRight, Math.PI / 4) * 15 + player.speed / 30 / adjustToStandard;
					Sysout.print("scoreRunAroundRight5: " + scoreRunAroundRight, "aidribble.scores");
					scoreRunAroundRight += scoreFromDirScoreBasedOn(oppDirScore, Math.PI / 4) * 15 + pClosestOppInFront.speed / 30 / adjustToStandard;
					Sysout.print("scoreRunAroundRight6: " + scoreRunAroundRight, "aidribble.scores");
					
					scoreRunAroundRight += scoreFromDirScoreBasedOn(goalDirScoreLeft, Math.PI / 4) / adjustToStandard;
					Sysout.print("scoreRunAroundRight7: " + scoreRunAroundRight, "aidribble.scores");
					
					//The differences in topspeed are important too 
					//TODO: We should calculate acceleration based on how much the player would need to accelerate and agility based on how much he'd need to turn too
					scoreRunAroundRight += (player.topSpeed / pClosestOppInFront.topSpeed) * 15;
					Sysout.print("scoreRunAroundRight8: " + scoreRunAroundRight, "aidribble.scores");
					
					//The closer we are to the opp the more difficult it becomes to pass the ball around him so distToOpp factors in here
					//The further away from him the more ground we have to make up - 40 is the perfect distance
					scoreRunAroundRight -= Math.abs(40 - distToOpp) / 2;
					Sysout.print("scoreRunAroundRight9: " + scoreRunAroundRight, "aidribble.scores");
					
					if (scoreRunAroundRight > scoreDribble){
						scoreDribble = scoreRunAroundRight;
						dribbleAction = "RunAroundRight";
						dribbleTarget.x = (int)XLeftBehindOpp;
						dribbleTarget.y = (int)YLeftBehindOpp;
						runTarget.x = (int)XRightBehindOpp;
						runTarget.y = (int)YRightBehindOpp;
					}
					
				}
				
				
				
				//scoreRunAroundLeft: spille bolden højre om forsvareren og selv løbe venstre om
				double scoreRunAroundLeft = 0;
				
				//Some of the conditions HAVE to be right for this to work. A simple score isn't enough because
				//a lot of space could give a decent score even if the player is facing the wrong way
				if (spaceRightOfOpp > 40 &&
						spaceRightBehindOpp > 75 &&
						spaceLeftOfOpp > 40 &&
						spaceLeftBehindOpp > 70 &&
						Math.abs(myDirScoreLeft) < 1 &&
						Math.abs(oppDirScore) < 1 &&
						Math.abs(goalDirScoreRight) < 1.4 &&
						player.topSpeed - pClosestOppInFront.topSpeed > -20 &&
						(player.speed > player.topSpeed * 0.4 || pClosestOppInFront.speed > pClosestOppInFront.topSpeed * 0.25) &&
						Math.abs(35 - distToOpp) < 16
						
						//Disabled for now as they often lose the ball doing this
						&& 1 == 2
				){
					
					if (spaceRightOfOpp > 100)
						scoreRunAroundLeft += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundLeft += (spaceRightOfOpp - 50) / adjustToStandard; 
					Sysout.print("scoreRunAroundLeft1: " + scoreRunAroundLeft, "aidribble.scores");
					
					if (spaceRightBehindOpp > 150)
						scoreRunAroundLeft += (150 - 70) / adjustToStandard;
					else
						scoreRunAroundLeft += (spaceRightBehindOpp - 70) / adjustToStandard; 
					Sysout.print("scoreRunAroundLeft2: " + scoreRunAroundLeft, "aidribble.scores");
					
					if (spaceLeftOfOpp > 100)
						scoreRunAroundLeft += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundLeft += (spaceLeftOfOpp - 30) / adjustToStandard; 
					Sysout.print("scoreRunAroundLeft3: " + scoreRunAroundLeft, "aidribble.scores");
					
					if (spaceLeftBehindOpp > 100)
						scoreRunAroundLeft += (100 - 50) / adjustToStandard;
					else
						scoreRunAroundLeft += (spaceLeftBehindOpp - 50) / adjustToStandard; 
					Sysout.print("scoreRunAroundLeft4: " + scoreRunAroundLeft, "aidribble.scores");
					
					//The directions the two players are facing are more important the more speed they have 
					scoreRunAroundLeft += scoreFromDirScoreBasedOn(myDirScoreLeft, Math.PI / 4) * 15 + player.speed / 30 / adjustToStandard;
					Sysout.print("scoreRunAroundLeft5: " + scoreRunAroundLeft, "aidribble.scores");
					scoreRunAroundLeft += scoreFromDirScoreBasedOn(oppDirScore, Math.PI / 4) * 15 + pClosestOppInFront.speed / 30 / adjustToStandard;
					Sysout.print("scoreRunAroundLeft6: " + scoreRunAroundLeft, "aidribble.scores");
					
					scoreRunAroundLeft += scoreFromDirScoreBasedOn(goalDirScoreRight, Math.PI / 4) / adjustToStandard;
					Sysout.print("scoreRunAroundLeft7: " + scoreRunAroundLeft, "aidribble.scores");
					
					//The differences in topspeed are important too 
					//TODO: We should calculate acceleration based on how much the player would need to accelerate and agility based on how much he'd need to turn too
					scoreRunAroundLeft += (player.topSpeed / pClosestOppInFront.topSpeed) * 15;
					Sysout.print("scoreRunAroundLeft8: " + scoreRunAroundLeft, "aidribble.scores");
					
					//The closer we are to the opp the more difficult it becomes to pass the ball around him so distToOpp factors in here
					//The further away from him the more ground we have to make up - 40 is the perfect distance
					scoreRunAroundLeft -= Math.abs(40 - distToOpp) / 2;
					Sysout.print("scoreRunAroundLeft9: " + scoreRunAroundLeft, "aidribble.scores");
					
					if (scoreRunAroundLeft > scoreDribble){
						scoreDribble = scoreRunAroundLeft;
						dribbleAction = "RunAroundLeft";
						dribbleTarget.x = (int)XRightBehindOpp;
						dribbleTarget.y = (int)YRightBehindOpp;
						runTarget.x = (int)XLeftBehindOpp;
						runTarget.y = (int)YLeftBehindOpp;
					}
				}
				
				
//				scoreDribble = 900;
				
				
				//Tage et langt træk forbi forsvarsspilleren og løbe forbi samme vej
				//Er smart hvis: Der er plads bag forsvareren til at spille bolden ned i, 
				//der er lidt plads på den ene side af forsvarsspilleren til bolden og angriberen,
				//angriberen er i fart i nogenlunde den retning han skal løbe i,
				//forsvarsspilleren ikke er i fart eller ikke har nogenlunde den retning han skal løbe i.
				double scoreLongHaulRight = 0;
				double scoreLongHaulLeft = 0;
				//			if (Hjaelper.Distance(player.x, player.y, pClosestOppInFront.x, pClosestOppInFront.y) > 23 &&
				//					Hjaelper.Distance(player.x, player.y, pClosestOppInFront.x, pClosestOppInFront.y) < 38){
				//				
				//				if (spaceLeftBehindOpp > 80 &&
				//						spaceLeftOfOpp > 25 &&
				//						Math.abs(myDirScoreLeft) < Math.PI / 3 &&
				//						oppDirScore < Math.PI / 3 &&
				//						player.speed > player.topSpeed * 0.4 &&
				//						Math.abs(goalDirScoreLeft) < Math.PI / 2){
				//					scoreDribble = 500;
				//					dribbleAction = "LongHaul";
				//					dribbleTarget.x = (int)XLeftOfOpp;
				//					dribbleTarget.y = (int)YLeftOfOpp;
				//				}
				//				if (spaceRightBehindOpp > 80 &&
				//						spaceRightOfOpp > 25 &&
				//						Math.abs(myDirScoreRight) < Math.PI / 3 &&
				//						oppDirScore < Math.PI / 3 &&
				//						player.speed > player.topSpeed * 0.4 &&
				//						Math.abs(goalDirScoreRight) < Math.PI / 2){
				//					scoreDribble = 500;
				//					dribbleAction = "LongHaul";
				//					dribbleTarget.x = (int)XRightOfOpp;
				//					dribbleTarget.y = (int)YRightOfOpp;
				//				}
				//			}

				//			if (Settings.SO) System.out.println("scoreLongHaulLeft");
				//			double scoreLongHaulLeft = spaceLeftBehindOpp - 60;
				//			if (Settings.SO) System.out.println(scoreLongHaulLeft);
				//			if (spaceLeftOfOpp - 25 < 0) scoreLongHaulLeft += spaceLeftOfOpp - 25;
				//			if (Settings.SO) System.out.println(scoreLongHaulLeft);
				//			scoreLongHaulLeft -= (myDirScoreLeft - Math.PI / 3) * 50;
				//			if (Settings.SO) System.out.println(scoreLongHaulLeft);
				//			scoreLongHaulLeft += (1.5 - oppDirScore) * pClosestOppInFront.speed - 50;
				//			if (Settings.SO) System.out.println(scoreLongHaulLeft);
				//			scoreLongHaulLeft += (player.speed - player.topSpeed);
				//			
				//			if (Settings.SO) System.out.println("scoreLongHaulLeft: " + scoreLongHaulLeft);
				//			
				//			if (Settings.SO) System.out.println("scoreLongHaulRight");
				//			double scoreLongHaulRight = spaceRightBehindOpp - 60;
				//			if (Settings.SO) System.out.println(scoreLongHaulRight);
				//			if (spaceRightOfOpp - 25 < 0) scoreLongHaulRight += spaceRightOfOpp - 25;
				//			if (Settings.SO) System.out.println(scoreLongHaulRight);
				//			scoreLongHaulRight -= (myDirScoreRight - Math.PI / 3) * 50;
				//			if (Settings.SO) System.out.println(scoreLongHaulRight);
				//			scoreLongHaulRight += (1.5 - oppDirScore) * pClosestOppInFront.speed - 50;
				//			if (Settings.SO) System.out.println(scoreLongHaulRight);
				//			scoreLongHaulRight += (player.speed - player.topSpeed);
				//			
				//			if (Settings.SO) System.out.println("scoreLongHaulRight: " + scoreLongHaulRight);

				//			if (scoreLongHaulLeft > scoreDribble){
				//				scoreDribble = scoreLongHaulLeft;
				//				dribbleAction = "LongHaul";
				//				dribbleTarget.x = (int)XLeftOfOpp;
				//				dribbleTarget.y = (int)YLeftOfOpp;
				//				
				//			}
				//			if (scoreLongHaulRight > scoreDribble){
				//				scoreDribble = scoreLongHaulRight;
				//				dribbleAction = "LongHaul";
				//				dribbleTarget.x = (int)XRightOfOpp;
				//				dribbleTarget.y = (int)YRightOfOpp;
				//			}


				//Stepover i en retning og løbe forbi spilleren den anden retning
				//Er smart hvis: Der er plads på den ene side af forsvarsspilleren,
				//Spilleren har retning mod ham og han har retning mod spillere,
				//Afstanden mellem spillerne er passende
				
				adjustToStandard = 5;
				
				Sysout.print("distIn15: " + Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15)), "aidribble.scores");
				
				if (Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15)) > 16 &&
						Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15)) < 40 &&
						player.getDribbling() > 40){

					if (spaceLeftOfOpp > 35 &&
							spaceLeftBehindOpp > 25 &&
							myDirScore < Math.PI / 3 &&
							Math.abs(oppDirScore) < Math.PI / 3 && 
							Math.abs(oppDirScoreRight) < Math.abs(oppDirScoreLeft) &&
							Math.abs(goalDirScoreLeft) < Math.PI / 2){
						
						double scoreStepOverLeft = 0;
						
						if (spaceLeftOfOpp > 80)
							scoreStepOverLeft += (80 - 30) / adjustToStandard;
						else
							scoreStepOverLeft += (spaceLeftOfOpp - 30) / adjustToStandard; 
						Sysout.print("scoreStepOverLeft1: " + scoreStepOverLeft, "aidribble.scores");
						
						if (spaceLeftBehindOpp > 60)
							scoreStepOverLeft += (60 - 25) / adjustToStandard;
						else
							scoreStepOverLeft += (spaceLeftBehindOpp - 25) / adjustToStandard; 
						Sysout.print("scoreStepOverLeft2: " + scoreStepOverLeft, "aidribble.scores");
						
						scoreStepOverLeft += scoreFromDirScoreBasedOn(myDirScoreLeft, Math.PI / 4) / adjustToStandard;
						Sysout.print("scoreStepOverLeft3: " + scoreStepOverLeft, "aidribble.scores");
						scoreStepOverLeft += scoreFromDirScoreBasedOn(oppDirScore, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverLeft4: " + scoreStepOverLeft, "aidribble.scores");
						scoreStepOverLeft += scoreFromDirScoreBasedOn(oppDirScoreRight, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverLeft5: " + scoreStepOverLeft, "aidribble.scores");
						scoreStepOverLeft += scoreFromDirScoreBasedOn(goalDirScoreLeft, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverLeft6: " + scoreStepOverLeft, "aidribble.scores");
						
						//DEBUG
						scoreStepOverLeft *= 2;
						
						if (scoreStepOverLeft > scoreDribble){
							scoreDribble = scoreStepOverLeft;
							dribbleAction = "StepOver";
							dribbleTarget.x = (int)XLeftOfOpp;
							dribbleTarget.y = (int)YLeftOfOpp;
							double viaDir = Math.atan2(XRightOfOpp - player.x, YRightOfOpp - player.y);
							dribbleVia = new Point((int)(player.x + Math.sin(viaDir) * 10), (int)(player.y + Math.cos(viaDir) * 10));
						}
					}
					//Steoverright
					if (spaceRightOfOpp > 35 &&
							spaceRightBehindOpp > 25 &&
							myDirScore < Math.PI / 3 &&
							oppDirScore < Math.PI / 3 && 
							oppDirScoreLeft < oppDirScoreRight &&
							goalDirScoreRight < Math.PI / 2){
						
						double scoreStepOverRight = 0;
						
						if (spaceRightOfOpp > 80)
							scoreStepOverRight += (80 - 30) / adjustToStandard;
						else
							scoreStepOverRight += (spaceRightOfOpp - 30) / adjustToStandard; 
						Sysout.print("scoreStepOverRight1: " + scoreStepOverRight, "aidribble.scores");
						
						if (spaceRightBehindOpp > 60)
							scoreStepOverRight += (60 - 25) / adjustToStandard;
						else
							scoreStepOverRight += (spaceRightBehindOpp - 25) / adjustToStandard; 
						Sysout.print("scoreStepOverRight2: " + scoreStepOverRight, "aidribble.scores");
						
						scoreStepOverRight += scoreFromDirScoreBasedOn(myDirScoreRight, Math.PI / 4) / adjustToStandard;
						Sysout.print("scoreStepOverRight3: " + scoreStepOverRight, "aidribble.scores");
						scoreStepOverRight += scoreFromDirScoreBasedOn(oppDirScore, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverRight4: " + scoreStepOverRight, "aidribble.scores");
						scoreStepOverRight += scoreFromDirScoreBasedOn(oppDirScoreLeft, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverRight5: " + scoreStepOverRight, "aidribble.scores");
						scoreStepOverRight += scoreFromDirScoreBasedOn(goalDirScoreRight, Math.PI / 2) / adjustToStandard;
						Sysout.print("scoreStepOverRight6: " + scoreStepOverRight, "aidribble.scores");
						
						//DEBUG
						scoreStepOverRight *= 20;
						
						if (scoreStepOverRight > scoreDribble){
							scoreDribble = scoreStepOverRight;
							dribbleAction = "StepOver";
							dribbleTarget.x = (int)XRightOfOpp;
							dribbleTarget.y = (int)YRightOfOpp;
							double viaDir = Math.atan2(XLeftOfOpp - player.x, YLeftOfOpp - player.y);
							dribbleVia = new Point((int)(player.x + Math.sin(viaDir) * 5), (int)(player.y + Math.cos(viaDir) * 5));
						}
					}
				}

				//Tunnel
				//Er smart hvis: Der er lidt plads bag ved forsvareren til at spille bolden ned i,
				//der er lidt plads på den ene side af forsvareren til at løbe forbi
				//forsvarsspilleren ikke er i fart

				//Playing style factors in too
				scoreDribble += player.getDribble() - 50;

				//The more opponents around the player the less he'll want to dribble
				scoreDribble += 20 - pressure;
				Sysout.print("pressure: " + pressure, "aidribble.scores");

				//make sure scoreDribble is within a standard range. More than 5 means it is always an opstion to dribble even if it's not great
				//Less than 90 means even a stupid player who ALWAYS wants to dribble will shoot if the shooting chance is higher than 90
				if (scoreDribble > 90)
					scoreDribble = 90;
				else if(scoreDribble < 5)
					scoreDribble = 5;
				
				//If the player is close to his own goal it's probably not a good idea to dribble
				if (Math.abs(player.x - player.getMyTeam().getGoalX()) < 450)
					scoreDribble -= (450 - Math.abs(player.x - player.getMyTeam().getGoalX())) / 20;
				
				//Hvis der er gode muligheder for at drible, men der er langt til forsvareren så bare løb fremad
				if (Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15)) > 70 ||
						closestOppDistToTargetLine > 40 ||
						Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29) <
						Hjaelper.Distance(pClosestOppInFront.getXIn(15), pClosestOppInFront.getYIn(15), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29)){
					dribbleAction = "Approach";

					if (Hjaelper.Distance(bold.getX(), bold.getY(), XLeftOfOpp, YLeftOfOpp) < Hjaelper.Distance(bold.getX(), bold.getY(), XRightOfOpp, YRightOfOpp)){
//						dribbleTarget.x = (int)XLeftOfOpp;
//						dribbleTarget.y = (int)YLeftOfOpp;
					}
					else{
//						dribbleTarget.x = (int)XRightOfOpp;
//						dribbleTarget.y = (int)YRightOfOpp;
					}
					dribbleTarget.x = player.getOppTeam().getGoalX();
					dribbleTarget.y = player.getOppTeam().getGoalY() + 29;
				}
				
//				if (dribbleAction.length() == 0)
//					scoreDribble = 0;
			}
		}

		
		//Players are less likely to dribble if they're not good at it - should this be here?
		scoreDribble += player.getDribbling() - 60;
		
		//If we're almost out of the pitch it's not the best place to dribble 
		//(For now because they often end up running out of the pitch)
		//TODO
		if (player.getPitch().isAlmostOut((int)bold.getX(), (int)bold.getY())){
			scoreDribble /= 2;
		}
		
		return scoreDribble;
	}

	public void dribble(Player pClosestOppInFront){
		//bdist bruges til at putte lidt mere fart i driblingen hvis bolden er under eller bag spilleren så den kommer frem foran ham igen
		double bdist = Hjaelper.Distance(dribbleTarget.x, dribbleTarget.y, bold.getX(), bold.getY()) - 
				Hjaelper.Distance(dribbleTarget.x, dribbleTarget.y, player.getX(), player.getY());
		
		if (player.speed > player.topSpeed * (0.62 + (player.dribbling / 300.0)))
			player.speed = player.topSpeed * (0.62 + (player.dribbling / 300.0));
		
//		player.speed *= (0.4 + ((player.dribbling + 80) / 400) + (r.nextDouble()* 0.1));
		
		//baseSpeed is the basic speed to give the ball. We calculate it based on the players speed - except if he needs to turn. 
		//The more the player needs to turn the less baseSpeed the ball should get. Otherwise it'll run away form him
		//If we need to stop and turn baseSpeed is 0
		double baseSpeed = player.speed;
		Sysout.print("player.speed: "+ player.speed, "aidribble");
		
		double dirToTarget = Math.atan2(dribbleTarget.x - player.x, dribbleTarget.y - player.y);
		double dirToBall = Math.atan2(bold.getX() - player.x, bold.getY() - player.y);
		double dirBallToTarget = Math.atan2(dribbleTarget.getX() - bold.getX(), dribbleTarget.getY() - bold.getY());
		
		Sysout.print("abs(angleDiff): "+ Math.abs(Hjaelper.angleDifference(player.getA(), dirToTarget)), "aidribble");
		Sysout.print("abs(angleDiff2): "+ Math.abs(Hjaelper.angleDifference(dirToBall, dirToTarget)), "aidribble");
		Sysout.print("abs(angleDiff3): "+ Math.abs(Hjaelper.angleDifference(player.getA(), dirBallToTarget)), "aidribble");
		
		
		if (Math.abs(Hjaelper.angleDifference(player.getA(), dirToTarget)) > Settings.getInstance().getSharpTurnLimit())
			baseSpeed = 0;
		else if (Math.abs(Hjaelper.angleDifference(player.getA(), dirBallToTarget)) > Settings.getInstance().getSharpTurnLimit())
			baseSpeed = 0;
		else if (Math.abs(Hjaelper.angleDifference(dirToBall, dirToTarget)) > Settings.getInstance().getSharpTurnLimit())
			baseSpeed *= 0.5;
		else
			baseSpeed /= (1.0 + Math.abs(Hjaelper.angleDifference(player.getA(), dirToTarget)));
		
		Sysout.print("baseSpeed: "+ baseSpeed, "aidribble");
		
		//bdist is adjusted based on the baseSpeed. It should always count for something but the faster the player is running
		//the more it should count for
		Sysout.print("bdist1: "+ bdist, "aidribble");
		bdist = bdist * 1 + (bdist * baseSpeed * 0.02);
		bdist = bdist + 4;
//		bdist *= 0.5;
		Sysout.print("bdist2: "+ bdist, "aidribble");
		
		Sysout.print("playerSpeedToBallSpeed * 1.5: " + (Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 1.5), "aidribble");
		
		Sysout.print("player x/y, bold x/y: " + player.x + "/" + player.y + ", " + bold.getX() + "/" + bold.getY(), "aidribble");
		
		Sysout.print("dribbleAction: "+ dribbleAction, "aidribble");
		Sysout.print("dribbleTarget: "+ dribbleTarget, "aidribble");
		
		//percentFullSpeed is the percentage of the players topspeed his current speed is at. The further from topspeed he is
		//the more his acceleration will factor in
//		double percentFullSpeed = baseSpeed / player.topSpeed;
		
		//accelerationFactor is how much we add to baseSpeed because of acceleration
//		double accelerationFactor = player.acceleration * (1 - percentFullSpeed) * 0.2;
//		Sysout.print("accelerationFactor: "+ accelerationFactor, "aidribble");
		
//		baseSpeed += accelerationFactor;

		if (dribbleAction == "RunAroundRight"){

			//DEBUG
//			pClosestOppInFront.wait += 500;

			player.setMatchMessage("plays the ball past " + pClosestOppInFront.getLastname());
			player.getMatch().addCommentary("plays the ball past " + pClosestOppInFront.getLastname());
			double sp = 40 + Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 2 + bdist;
			customDribble(dribbleTarget.x, dribbleTarget.y, bold.getBoldDifPercent(), sp);
			player.complexActions.add(new ComplexAction(Action.RUNAROUNDRIGHT, runTarget, player.topSpeed, player, bold, pClosestOppInFront));
		}
		else if (dribbleAction == "RunAroundLeft"){

			//DEBUG
//			pClosestOppInFront.wait += 500;
			
			player.setMatchMessage("plays the ball past " + pClosestOppInFront.getLastname());
			player.getMatch().addCommentary("plays the ball past " + pClosestOppInFront.getLastname());
			double sp = 40 + Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 2 + bdist;
			customDribble(dribbleTarget.x, dribbleTarget.y, bold.getBoldDifPercent(), sp);
			player.complexActions.add(new ComplexAction(Action.RUNAROUNDLEFT, runTarget, player.topSpeed, player, bold, pClosestOppInFront));
		}
		else if (dribbleAction == "LongHaul"){
			player.setMatchMessage("tries to take the ball past " + pClosestOppInFront.getLastname());
			player.getMatch().addCommentary("tries to take the ball past " + pClosestOppInFront.getLastname());
			double sp = Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 2 + bdist;
			customDribble(dribbleTarget.x, dribbleTarget.y, bold.getBoldDifPercent(), sp);
		}
		else if (dribbleAction == "Approach"){
			player.setMatchMessage("takes the ball forward");
			player.getMatch().addCommentary("takes the ball forward");
			double sp = Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 1.9 + bdist;
			customDribble(dribbleTarget.x, dribbleTarget.y, bold.getBoldDifPercent(), sp);
		}
		else if (dribbleAction == "StepOver"){
			
			//Defender should wait a basic period to allow the stepover to finish unless he is a very good tackler/marker
			//Apart from the basic wait the defender could be fooled by the stepover and have to wait even more to regain composure and react
			
			//DEBUG
			pClosestOppInFront.wait += 500;
			
			player.setMatchMessage("with a cheeky stepover");
			player.getMatch().addCommentary("with a cheeky stepover");
			double stepOverSpeed = 0.5 + 0.5 * ((80 + player.dribbling + player.agility) / 250);
			ComplexAction stepOver = new ComplexAction(Action.STEPOVER, dribbleVia, stepOverSpeed, player, bold, pClosestOppInFront);
			stepOver.setBallTarget(dribbleTarget);
			player.complexActions.add(stepOver);
			player.complexActions.add(new ComplexAction(Action.DRIBBLE, dribbleTarget, 20, player, bold, null));
		}
		else if (dribbleAction == "ShortDribbles"){
			player.setMatchMessage("takes on the defender");
			player.getMatch().addCommentary("takes on the defender");
			
			//If the opponent is close he may attempt a tackle and lose time - move this to the defenders ai
			if (Hjaelper.Distance(pClosestOppInFront.x, pClosestOppInFront.y, player.x, player.y) < 20){
				//wait er den tid det tager forsvareren at blive klar igen efter at have forsøgt entackling hvis vi dribler tæt på ham
				int wait = r.nextInt((int)player.dribbling * 8) - r.nextInt((int)pClosestOppInFront.tackling * 7);
				if (wait > 0)
					pClosestOppInFront.setWait(wait);
			}
			
			double sp = Hjaelper.playerSpeedToBallSpeed(baseSpeed) * 1.9 + bdist;
			customDribble(dribbleTarget.x, dribbleTarget.y, bold.getBoldDifPercent(), sp);
		}
		
		player.setTargetX(bold.getX() + 10 * Math.sin(bold.getA()));
		player.setTargetY(bold.getY() + 10 * Math.cos(bold.getA()));
		
		player.setState(player.getStateDribbling());
	}
	
	public void customDribble(double tX, double tY, int boldDif, double force){
		if (boldDif <= 0) boldDif = 1;
		
		if (player.getPlayerAction() != PlayerAction.passing){
			
			player.playerMatchStats.dribbles++;
			
			player.getOppTeam().getOppsMarked().clear();

			player.setLastdribble(System.currentTimeMillis());
			player.urgent = true;

			//boldSpeedRandom: hvor upræcist farten i driblingen bliver
			int boldSpeedRandom = (r.nextInt(boldDif * 2) - r.nextInt(boldDif * 2)) / (int) (player.getDribbling());
			Sysout.print("boldSpeedRandom: "+ boldSpeedRandom, "aidribble");
			//////Midlertidig til test
//			boldSpeedRandom = 0;
			//////
			
			player.targetX = tX;
			player.targetY = tY;
			double a = Math.atan2((tX - bold.getX()), (tY - bold.getY()));
			double off = r.nextDouble() - r.nextDouble();
			off *= 40 / player.dribbling;
			Sysout.print("off: "+ off, "aidribble");
			a+= off;
			
			bold.setA(a);
			bold.setLastTouch(player);
			bold.setPassTo(null);
			bold.setShot(false);
			bold.setCross(false);
			player.setPlayerAction(PlayerAction.dribble);

			player.checkTeammatesOffside();
			player.setState(player.getStateDribbling());
			
			bold.setSpeed(force + boldSpeedRandom);
			
		}
	}

	private double scoreFromDirScoreBasedOn(double dirScore, double base){
		return (base - Math.abs(dirScore)) * 20;
	}
	
	private double scoreFromDirScore(double dirScore){
		return Math.abs(dirScore) * 20;
	}
	
	public Point getDribbleTarget() {
		return dribbleTarget;
	}

	public void setDribbleTarget(Point dribbleTarget) {
		this.dribbleTarget = dribbleTarget;
	}

	public int getXLeftBehindOpp() {
		return (int)XLeftBehindOpp;
	}

	public void setXLeftBehindOpp(double xLeftBehindOpp) {
		XLeftBehindOpp = xLeftBehindOpp;
	}

	public int getYLeftBehindOpp() {
		return (int)YLeftBehindOpp;
	}

	public void setYLeftBehindOpp(double yLeftBehindOpp) {
		YLeftBehindOpp = yLeftBehindOpp;
	}

	public int getXLeftOfOpp() {
		return (int)XLeftOfOpp;
	}

	public void setXLeftOfOpp(double xLeftOfOpp) {
		XLeftOfOpp = xLeftOfOpp;
	}

	public int getYLeftOfOpp() {
		return (int)YLeftOfOpp;
	}

	public void setYLeftOfOpp(double yLeftOfOpp) {
		YLeftOfOpp = yLeftOfOpp;
	}

	public int getXRightBehindOpp() {
		return (int)XRightBehindOpp;
	}

	public void setXRightBehindOpp(double xRightBehindOpp) {
		XRightBehindOpp = xRightBehindOpp;
	}

	public int getYRightBehindOpp() {
		return (int)YRightBehindOpp;
	}

	public void setYRightBehindOpp(double yRightBehindOpp) {
		YRightBehindOpp = yRightBehindOpp;
	}

	public int getXRightOfOpp() {
		return (int)XRightOfOpp;
	}

	public void setXRightOfOpp(double xRightOfOpp) {
		XRightOfOpp = xRightOfOpp;
	}

	public int getYRightOfOpp() {
		return (int)YRightOfOpp;
	}

	public void setYRightOfOpp(double yRightOfOpp) {
		YRightOfOpp = yRightOfOpp;
	}

	
}
