package model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import ai.AIClear;
import ai.AIPass;

import model.Match.MatchState;

public class StateKeeperHasBall {
	Player player;
	Bold bold;
	private AIClear aiClear;
	private AIPass aiPass;
	Random r = new Random();
	
	public StateKeeperHasBall(Player player, Bold bold) {
		this.bold = bold;
		this.player = player;
		aiClear = new AIClear(player, bold);
		aiPass = new AIPass(player, bold);
	}

	public void update(){

		// regn ud hvor meget pres der er paa spilleren	
		double pressure = 0; // hvor presset er jeg af modstandere
		// regn ud hvor meget pres der er paa spilleren		
		for (Player opp : player.getOppTeam().getPlayers()){
			if (pressure < 100){
				double d = Hjaelper.Distance(player.x, player.y, opp.x, opp.y);
				if (d < 56)
					pressure += 56 - d;
			}
		}
		if (pressure > 100) pressure = 100;
		
		ArrayList<Player> visibleTeammates = new ArrayList<Player>();
		
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

			//Don't include the keeper himself as a visible team mate and ignore team mates closer to goal than the keeper
			//to avoid dangerous back passes
			if (p != player && 
					Math.abs(p.getX() - player.getMyTeam().getGoalX()) > Math.abs(player.getX() - player.getMyTeam().getGoalX())){
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

		double minD = 0; // afstand fra boldens bane til taetteste modstander
		double scoreLow = 0;
		Player targetP = null;

		for (Player p : visibleTeammates){
			if (!p.isKeeper){

				double r = Math.atan2((p.getX() - player.x), (p.getY() - player.y));
				double teX = player.x + (10 * Math.sin(r));
				double teY = player.y + (10 * Math.cos(r));

				//Find n¾rmeste modstander til boldens bane mod p
				//der er vel(?) ingen grund til at tage modstandere med som er længere væk fra bolden end medspilleren

				ArrayList<Player> pl = new ArrayList<Player>();

				for (Player pm : player.getOppTeam().getPlayers())
					if (Hjaelper.Distance(pm.x, pm.y, bold.x, bold.y) - 80 < 
							Hjaelper.Distance(p.x, p.y, bold.x, bold.y))
						pl.add(pm);

				minD = 0;
				while (Hjaelper.Distance(teX, teY, p.getX(), p.getY()) > 10){
					teX += (10 * Math.sin(r));
					teY += (10 * Math.cos(r));

					for (Player pm : pl){
						if (minD == 0 || Hjaelper.Distance(teX, teY, pm.getX(), pm.getY()) < minD)
							minD = Hjaelper.Distance(teX, teY, pm.getX(), pm.getY());
					}
				}		

				//lav aflevering
				double thisPlayersLowScore = minD * 2;
				if (Hjaelper.Distance(p.x, p.y, player.x, player.y) > 200) 
					thisPlayersLowScore -= Hjaelper.Distance(p.x, p.y, player.x, player.y) - 200;

				if (scoreLow < thisPlayersLowScore) {
					scoreLow = thisPlayersLowScore;
					targetP = p;
				}
			}

		}

		//som udgangspunkt er det en god ide at kontrollere bolden
		double scoreControl = 100;

		//jo mere presset vi er jo mindre tid har vi til at tæmme bolden
		scoreControl -= pressure;

		//hvor svær ville bolden være at tæmme?
		int boldDif = (int)(bold.getSpeed() + Math.abs(bold.getZForce()) + (bold.getZ()) / 1.7);
		int skill = (int)(((player.technique + 100) / 15 + (player.r.nextInt(25) + 1)));
		int taemning = skill - boldDif;
		
		//jo sværere jo længere tid ville det tage og så kunne det være dumt
		if (taemning < 0) scoreControl += taemning;
		
		pressure *= 3;
//		System.out.println("ScoreLow: " + scoreLow + ". Pressure: " + pressure);
		scoreLow -= pressure;
		
		if (scoreLow > 60)
			aiPass.pass(targetP, false, boldDif, new Point((int)targetP.getX(), (int)targetP.getY()), 1, 1);
		else{
			//clear
			aiClear.clear(boldDif);
			bold.shot = false;
		}
		bold.setHasBall(null);
		bold.setShot(false);
		player.wait = 500;
	}	
}
