package model;

import java.awt.Point;
import java.util.Random;

import ai.AIPass;
import ai.AIRunOffBall;

public class StateTeamHasBall implements PlayerState{

	Bold bold;
	Player player;
	Random r;	
	
	private AIRunOffBall aiRunOffBall;
	
	public StateTeamHasBall(Player player){
		this.player = player;
		bold = player.bold;
		r = new Random();
		
		aiRunOffBall = new AIRunOffBall(player, bold);
	}
	public void update() {
		Sysout.print("StateTeamHasBall.update()", "verbose");
		double scoreRunOffBall = aiRunOffBall.getScore();

		
		if (player.instructionNoBall != null){ 
			if(player.instructionNoBall.getAction().equals("dribble")){
				player.playerAction = PlayerAction.dribble;
				player.setState(player.getStateDribbling());
			}
			else if(player.instructionNoBall.getAction().toLowerCase().equals("position")){
				player.playerAction = PlayerAction.position;
				player.targetX = player.instructionNoBall.getTarget().x;
				player.targetY = player.instructionNoBall.getTarget().y;
			}
			else if(player.instructionNoBall.getAction().toLowerCase().equals("meetball")){
				player.playerAction = PlayerAction.receiving_pass;
				Point point = bold.meetBall(player, true);
				player.targetX = point.x;
				player.targetY = point.y;
			}
		}
		else{
			if (scoreRunOffBall > 25){
				player.playerAction = PlayerAction.run;
				player.getStateRunOffBall().update();
				//			player.setMatchMessage("makes a run.");
			}
			else{
				player.playerAction = PlayerAction.position;

				if (r.nextDouble() < 0.1){
					player.targetX = player.posX + player.r.nextInt(50) - player.r.nextInt(50);
					player.targetY = player.posY + player.r.nextInt(50) - player.r.nextInt(50);

					//hvis vi er laengere fremme end bolden
					if (Hjaelper.Distance((int)player.posX, 0, player.oppTeam.goalX, 0) <
							Hjaelper.Distance((int)player.x, 0, player.oppTeam.goalX, 0)){

						//lob fremad eller loeb fri for boldholderen hvis vi er taet paa ham
						Player p1 = null, p2 = null, p3 = null;

						for (Player p : player.myTeam.players){

							if (!p.isKeeper){
								if (p1 == null) p1 = p;
								else if (p2 == null) p2 = p;
								else if (p3 == null) p3 = p;
								else if (Hjaelper.Distance(bold.getX(), bold.getY(), p1.x, p1.y) > 
								Hjaelper.Distance(bold.getX(), bold.getY(), p.x, p.y)) p1 = p;

								else if (Hjaelper.Distance(bold.getX(), bold.getY(), p2.x, p2.y) > 
								Hjaelper.Distance(bold.getX(), bold.getY(), p.x, p.y)) p2 = p;

								else if (Hjaelper.Distance(bold.getX(), bold.getY(), p3.x, p3.y) > 
								Hjaelper.Distance(bold.getX(), bold.getY(), p.x, p.y)) p3 = p;
							}
						}
						if (p1.equals(player)/* || p2.equals(player)/* || p3.equals(player)*/){
							rykfri();
						}
					}


				}
			}

			if (player.playerAction.equals(PlayerAction.position) ||
					player.playerAction.equals(PlayerAction.nothing) ||
							player.playerAction.equals(PlayerAction.run)){

				double tempX = bold.x + 10 * Math.sin(bold.a);
				double tempY = bold.y + 10 * Math.cos(bold.a);

				//hvis vi løber i vejen for bolden så gå væk
				if (Hjaelper.Distance(player.x, player.y, tempX, tempY) < 60 
						&& !bold.isShot() && !bold.cross && bold.passTo==null){

					double dirToGoal = Math.atan2(player.getOppTeam().getGoalX() - player.getX(), 0);

					double newX1 = tempX + 60 * Math.sin(dirToGoal - Math.PI / 4);
					double newY1 = tempY + 60 * Math.cos(dirToGoal - Math.PI / 4);
					
					double newX2 = tempX + 60 * Math.sin(dirToGoal + Math.PI / 4);
					double newY2 = tempY + 60 * Math.cos(dirToGoal + Math.PI / 4);
					
					if (Hjaelper.Distance(player.x, player.y, newX1, newY1) < Hjaelper.Distance(player.x, player.y, newX2, newY2)){
						player.targetX = newX1;
						player.targetY = newY1;
					}
					else{
						player.targetX = newX2;
						player.targetY = newY2;
					}
					
//					if (Hjaelper.Distance(bold.lastTouch.x, bold.lastTouch.y, player.x, player.y) > 20){
//
//						double q = bold.lastTouch.a;
//						q -= Math.PI;
//						if (q > Math.PI * 2) q -= Math.PI * 2;
//						else if(q < 0) q += Math.PI * 2;
//
//						double t1x = bold.lastTouch.x + 30 * Math.sin(q);
//						double t1y = bold.lastTouch.y + 30 * Math.cos(q);
//
//						player.targetX = t1x;
//						player.targetY = t1y;
//					}
//
//					double ta = Math.atan2(player.x - tempX, player.y - tempY);
//					int safetyFirst = 0;
//					while (Hjaelper.Distance(player.targetX, player.targetY, tempX, tempY) < 60 && safetyFirst < 50){
//						player.targetX += 5 * Math.sin(ta);
//						player.targetY += 5 * Math.cos(ta);
//						safetyFirst++;
//
//						if (player.pitch.isAlmostOut((int)player.targetX, (int)player.targetY)){
//							ta = Math.atan2(player.oppTeam.goalX - player.targetX, 0);
//						}
//					}
				}
			}


			//hvis der er et indlaeg som jeg er taet paa
			if ((bold.isCross() && //Hjaelper.Distance(player.x, player.y, player.oppTeam.getGoalX(), player.oppTeam.getGoalY() + 30) < 150)
					player.countToBall / (bold.firstToBall.countToBall + 1) < 1.1)
					//eller hvis der er en aflevering til mig
					|| (bold.getPassTo() != null && bold.getPassTo() == player)){

				 
				//saa gaar vi efter bolden
				player.targetSpeed = player.topSpeed;
				Point point = bold.meetBall(player, true);
				player.playerAction = PlayerAction.chasingBall;
				if (bold.getPassTo() == player)
					player.playerAction = PlayerAction.receiving_pass;

				player.targetX = point.x;
				player.targetY = point.y;
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

			//Hvis der ikke er en aflevering igang og vi kan komme først til bolden af vores hold tager vi den
			if (bold.getPassTo() == null && bold.getLastTouch().getPlayerAction() != PlayerAction.dribble){
				if (mate.equals(player)){
					player.playerAction = PlayerAction.chasingBall;
					Point p = bold.meetBall(player, true);
					player.targetX = p.x;
					player.targetY = p.y;
					//				System.out.println(player.shirtNumber + " jagter bolden");
				}
			}//Hvis der er en aflevering igang, men modtageren ikke kan nå den før modstanderen tager vi den
			else if (bold.getPassTo() != null && bold.getPassTo().countToBall > opp.countToBall){
				if (mate.equals(player)){
					player.playerAction = PlayerAction.chasingBall;
					Point p = bold.meetBall(player, true);
					player.targetX = p.x;
					player.targetY = p.y;
					//				System.out.println(player.shirtNumber + " jagter bolden");
				}
			}
			else if (player.playerAction.equals(PlayerAction.chasingBall) && !bold.isCross())
				player.playerAction = PlayerAction.position;

			//loeb tilbage hvis vi er offside
			if (player.offsideBy > 20){
				player.targetX = player.posX;
				player.targetY = player.posY;
				player.targetSpeed = player.topSpeed / 3;
				if (bold.getPassTo() == player){
					bold.setPassTo(null);
				}
			}

			//pas paa ikke at blive offside
			if (Hjaelper.Distance(player.x, player.y, player.getOppTeam().getGoalX(), player.y) < 
						player.getOppTeam().getOffsideDistToGoal() &&
					player.playerAction != PlayerAction.receiving_pass && player.playerAction != PlayerAction.chasingBall
					&& player.playerAction != PlayerAction.dribble && bold.getLastTouch() != player){
				
				if (player.getMyTeam().getGoalX() < player.getOppTeam().getGoalX())
					player.targetX = player.getOppTeam().getGoalX() - player.getOppTeam().getOffsideDistToGoal();
				else
					player.targetX = player.getOppTeam().getGoalX() + player.getOppTeam().getOffsideDistToGoal();
			}

			//Er bolden ude eller har keeperen ellers placerer vi os loeber vi paa plads
			if (bold.getHasBall() != null && bold.getHasBall().isKeeper() || bold.isUdenfor() || player.playerAction.equals(PlayerAction.position)){
				player.targetX = player.posX;
				player.targetY = player.posY;
			}
		}
		
	}

	private void rykfri(){
		//test
		//stiller sig saa han kan spilles

		double q;
		double tempX = player.x, tempY = player.y;

		//ryk lidt hen mod boldholderen
		//		q = Math.atan2((bold.getX() - player.x), (bold.getY( )- player.y));
		//		tempX += 30 * Math.sin(q);
		//		tempY += 30 * Math.cos(q);

		//find retningen mod boldn
		q = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
		tempX += 10 * Math.sin(q);
		tempY += 10 * Math.cos(q);

		//gaar vi den ene eller anden vej vaek fra modspillerne
		boolean t1 = true;
		if (r.nextDouble() < r.nextDouble()) t1 = false;

		//gaa hen mod bolden og hvis der staar noget i vejen saa gaa vaek saa linien mod bolden er fri
		int limitWhile = 1000;
		while (Hjaelper.Distance(tempX, tempY, bold.getX(), bold.getY()) > 15 && limitWhile > 0){
			limitWhile--;
			q = Math.atan2((bold.getX() - player.x), (bold.getY() - player.y));
			tempX += 5 * Math.sin(q);
			tempY += 5 * Math.cos(q);

			for (Player p : player.myTeam.getPlayers()){
				if (p != player){
					if ((Hjaelper.Distance(tempX, tempY, p.getX(), p.getY()) < 15)){

						q -= Math.PI / 2;
						if (q > Math.PI * 2) q -= Math.PI * 2;
						else if(q < 0) q += Math.PI * 2;

						double t1x = player.x - 20 * Math.sin(q);
						double t1y = player.y - 20 * Math.cos(q);
						double t2x = player.x + 20 * Math.sin(q);
						double t2y = player.y + 20 * Math.cos(q);
						//double d1 = Math.sqrt(Math.abs(t1x - x) * Math.abs(t1x - x) + Math.abs(t1y - y) * Math.abs(t1y - y));
						//double d2 = Math.sqrt(Math.abs(t2x - x) * Math.abs(t2x - x) + Math.abs(t2y - y) * Math.abs(t2y - y));

						if (t1){
							player.targetX = t1x;
							player.targetY = t1y;
						}
						else{
							player.targetX = t2x;
							player.targetY = t2y;
						}

					}
				}
			}
			for (Player p : player.oppTeam.getPlayers()){
				if ((Hjaelper.Distance(tempX, tempY, p.getX(), p.getY()) < 15)){//t¾t nok pŒ target

					q -= Math.PI / 2;
					if (q > Math.PI * 2) q -= Math.PI * 2;
					else if(q < 0) q += Math.PI * 2;

					//					System.out.println(player.shirtNumber + " rykker fri...");

					double t1x = player.x - 50 * Math.sin(q);
					double t1y = player.y - 50 * Math.cos(q);
					double t2x = player.x + 50 * Math.sin(q);
					double t2y = player.y + 50 * Math.cos(q);
					//double d1 = Math.sqrt(Math.abs(t1x - x) * Math.abs(t1x - x) + Math.abs(t1y - y) * Math.abs(t1y - y));
					//double d2 = Math.sqrt(Math.abs(t2x - x) * Math.abs(t2x - x) + Math.abs(t2y - y) * Math.abs(t2y - y));

					if (t1){
						player.targetX = t1x;
						player.targetY = t1y;
					}
					else{
						player.targetX = t2x;
						player.targetY = t2y;
					}
				}
			}
		}
	}

}
