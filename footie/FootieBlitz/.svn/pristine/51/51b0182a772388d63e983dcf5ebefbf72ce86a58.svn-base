package ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.Settings;
import model.StateHasBall;
import model.Team;

public class AICross{

	private Player player;
	private Bold bold;
	private Random r;
	private Player targetCross = null; //Den spiller indlægget skal lægges til
	
	public AICross(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}

	public double getScore(double igennem, double retningPaaMaal, double vinkelretPaaMaal, ArrayList<Player> visibleTeammates) {

		double scoreCross = 0;

		if (Math.abs(Hjaelper.angleDifference(vinkelretPaaMaal, retningPaaMaal)) > 0.5){

			//taktik
			scoreCross = player.getCrossball() * 0.3;

			//jo taettere paa baglinien des stoerre chance for at laegge ind over
			scoreCross += (250 - Hjaelper.Distance(player.getX(), 0, player.getOppTeam().getGoalX(), 0)) / 4;

			//jo mindre spids vinklen på mål er jo mindre chance for indlæg (lige foran mål skal man ikke lægge ind over)
			scoreCross -= (85 - (35 * Math.pow(2 * Math.abs(Hjaelper.angleDifference(retningPaaMaal, vinkelretPaaMaal)), 2)));
			
			//hvis vi er igennem skal vi ikke spille den
//			System.out.println("****************scorecross før igennem: " + scoreCross);
			scoreCross -= igennem * 20;
//			System.out.println("****************scorecross efter igennem: " + scoreCross);

			//Hvor mange medspillere ligger inde omkring maal?
			int targets = 0;
			for (Player p : visibleTeammates)
				if (Hjaelper.Distance(p.getX(), p.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29) < 110 && Math.abs(p.getX() - player.getOppTeam().getGoalX()) > 8){
					targets++;
					if (targetCross == null || p.getFreeFactor() > targetCross.getFreeFactor()){
						targetCross = p;
//						System.out.println("Den er inde");
					}
				}
			if (targetCross != null){
				scoreCross += targetCross.getFreeFactor() * 3;
//				System.out.println("****************scorecross efter freefactor: " + scoreCross);
				//for hvert target derinde bliver det bedre at slaa et indlaeg
				scoreCross += targets * 25;
//				System.out.println("targets: " + targets);
//				System.out.println("****************scorecross efter targets: " + scoreCross);


				double qu = Math.atan2((targetCross.getX() - bold.getX()), (targetCross.getY() - bold.getY()));

				double vinkelPaaBold = Hjaelper.angleDifference(qu, player.getA());
				if (vinkelPaaBold > Math.PI / 2)
					scoreCross -= 30 * (vinkelPaaBold - (Math.PI / 2));	
//				System.out.println("****************scorecross efter vinkel: " + scoreCross);

			}
			else scoreCross = -20;
		}
		
		
		return scoreCross;
	}
	
	public void cross(int boldDif){
		cross(targetCross, boldDif);
	}
	
	public void cross(Player p, int boldDif){

		double q = 0;
		double off = r.nextDouble() - r.nextDouble();
		if (boldDif <= 0) boldDif = 1;
		off *= 5 / (player.passing + 150) / boldDif;
//		System.out.println("cross OFF: " + off);
		
		
		//target
		double tX = 0, tY = 0;
		
		//If p is null then the player is crossing without a specific target in mind. In that case we aim for the far side of the area. 
		if (p == null){
			
			tX = player.getOppTeam().getGoalX();
			if (player.getOppTeam().getGoalX() < player.getMyTeam().getGoalX())
				tX += 90;
			else
				tX -= 90;
			
			tY = player.getPitch().getPitchMidY();
			if (bold.getY() < player.getPitch().getPitchMidY()) tY += + 40;
			else tY -= 40;
		}
		else{

			tX = p.getX();
			tY = p.getY();
			
			//hvis target er et stykke fra mål lægges indlægget lidt foran ham
			if (Hjaelper.Distance(p.getX(), 0, player.getOppTeam().getGoalX(), 0) > 70){

				int faktor = 20;
				if (player.getOppTeam().getGoalX() < player.getMyTeam().getGoalX())
					faktor *= -1;
				
				tX += faktor;
			}
		}
		
		double power = ((55 + Hjaelper.Distance(tX, tY, bold.getX(), bold.getY()) * 0.25));
		double h = 20 + Hjaelper.Distance(tX, tY, bold.getX(), bold.getY()) * 0.04;

		if (h > 45) h = 45;
		bold.setZForce(h);
//		power -= h * 2;
		
		//Set curl
		double dirToGoalLine = Math.atan2(player.getOppTeam().getGoalX() - player.getX(), 0);
		double curl = calculateCurl(50);
		
		//Curl direction depending on whether the player is facing the goal line or has turned and is facing away from the goal line
		if (Math.abs(Hjaelper.angleDifference(dirToGoalLine, player.getA())) < Math.PI / 2)
			curl *= -1;
		
		q = Math.atan2((tX - bold.getX()), (tY - bold.getY()));
		
		player.speed *= 0.2;
		
		player.checkTeammatesOffside();
		player.setPlayerAction(PlayerAction.passing);
		bold.setZForce(h);
		bold.setLastTouch(player);
		bold.setA(q);
		bold.setCurl(curl);
		bold.setBallTimer(System.currentTimeMillis());//her skal bolden ændre retning
		bold.setSpeed(power);
		bold.adjustAForCurl(new Point((int)tX, (int)tY));
		bold.setA(bold.getA() + off);
		bold.setPassTo(null);
		bold.setShot(false);
		bold.setCross(true);
		bold.setBallTimer(System.currentTimeMillis());//her sættes sidste gang bolden ændrede retning
		player.setMatchMessage("crosses the ball");

		player.setState(player.getStateCrossing());
		player.wait += 400;
		
		player.playerMatchStats.crosses++;
		//		System.out.println(p.shirtNumber + " / " + q + " / " + power);
		//System.out.println("Cross / " + (vinkelPaaBold) + " - power: " + power);
	}
	
	/**
	 * Calculates the curl on a cross
	 * @return the curl of the cross
	 */
	public double calculateCurl(double powerIntent){
		
		//Max curl is 0.015. The better shooting the more curl the player can do
		double result = 0.015 * player.passing / 100;
		
		//The more power we want to put into the shot the less curl we get
		if (powerIntent > 50) result *= 50.0 / powerIntent;
		
		//random choice of curl for now
		//No we want to see curl
//		result *= (r.nextDouble() * 100 + 1.0) / 100.0;
		
		return result;
	}
}
