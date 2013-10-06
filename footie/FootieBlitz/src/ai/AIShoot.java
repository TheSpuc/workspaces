package ai;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

import data.DAOCake;

import model.Bold;
import model.Hjaelper;
import model.Player;
import model.Sysout;


public class AIShoot{

	private Player player;
	private Random r;
	private Bold bold;
	private int shootX, shootY;
	private boolean shootPlaced = false;
	private boolean shootHigh = true;
	
	public AIShoot(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}


	public double getScore(double igennem, double retningPaaMaal, double vinkelretPaaMaal) {

		double scoreShoot = 0;

		//Set target to middle of goal. The real target is calculated afterwards
		shootX = player.getOppTeam().getGoalX();
		shootY = player.getOppTeam().getGoalY() + 29;
		
		shootPlaced = true; //skal jeg placere eller fyre igennem?
		shootHigh = r.nextBoolean();
		
		//Skud udefra afhaengig af longshots taktik og om vi er igennem
		if (Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29) > 150){
			scoreShoot = player.getLongshots() - r.nextInt(30);
			if (scoreShoot < 1) scoreShoot = 1;
//			System.out.println("****************scoreshoot før igennem: " + scoreShoot);
			scoreShoot -= igennem * 3;
			shootPlaced = false;
			shootHigh = true;
		}
		else{ //Er vi tættere på mål er det umiddelbart bedre at skyde jo tættere vi er på mål
			scoreShoot = 0.5 * (150 - Hjaelper.Distance(player.getX(), player.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 29));
		}
		
		//find ud af om en af siderne af maalet er fri
		double q = Math.atan2(player.getOppTeam().getGoalX() - player.getX(), player.getOppTeam().getGoalY() - player.getY());
		double tempX = player.getX() + (10 * Math.sin(q));
		double tempY = player.getY() + (10 * Math.cos(q));
		double stolpe1 = 0;
		double stolpe2 = 0;

		//hvor fri er stolpe1
		int limitWhile = 1000;
		while (Hjaelper.Distance(tempX, tempY, player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY()) > 10 && limitWhile > 0){
			limitWhile--;
			tempX += (10 * Math.sin(q));
			tempY += (10 * Math.cos(q));

			for (Player pm : player.getOppTeam().getPlayers()){
				if (stolpe1 == 0 || Hjaelper.Distance(tempX, tempY, pm.getX(), pm.getY()) < stolpe1)
					stolpe1 = Hjaelper.Distance(tempX, tempY, pm.getX(), pm.getY());
			}
		}

		q = Math.atan2(player.getOppTeam().getGoalX() - player.getX(), player.getOppTeam().getGoalY() + 59 - player.getY());
		tempX = player.getX() + (10 * Math.sin(q));
		tempY = player.getY() + (10 * Math.cos(q));

		//hvor fri er stolpe2
		limitWhile = 1000;
		while (Hjaelper.Distance(tempX, tempY, player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 59) > 10 && limitWhile > 0){
			limitWhile--;
			tempX += (10 * Math.sin(q));
			tempY += (10 * Math.cos(q));

			for (Player pm : player.getOppTeam().getPlayers()){
				if (stolpe1 == 0 || Hjaelper.Distance(tempX, tempY, pm.getX(), pm.getY()) < stolpe1)
					stolpe1 = Hjaelper.Distance(tempX, tempY, pm.getX(), pm.getY());
			}
		}

		if (stolpe1 > stolpe2 + r.nextInt(8)){
			scoreShoot += stolpe1 * 8;
			shootX = player.getOppTeam().getGoalX();
			shootY = player.getOppTeam().getGoalY() + 54;
			if (!shootPlaced) shootY -= 5;
		}
		else{
			scoreShoot += stolpe2 * 8;
			shootX = player.getOppTeam().getGoalX();
			shootY = player.getOppTeam().getGoalY() + 5;
			if (!shootPlaced) shootY += 5;
		}
//		System.out.println("Stolpe 1 og 2: " + stolpe1 + " / " + stolpe2);

		double angleDiff = Hjaelper.angleDifference(retningPaaMaal, player.getA());

		//Distance to the nearest post
		double distToGoal = Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY());
		if (Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 59) < distToGoal)
			distToGoal = Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY() + 59);
		
		
		//jo mindre afstand til maal jo bedre score
		scoreShoot += 1.8 * (140 - distToGoal);
		
		//hvis vi er igennem skal vi ikke sparke udefra
		//				scoreShoot -= igennem / 2.5;

		// jo mere han har ryggen til target jo mindre score
		if (angleDiff > Math.PI / 2) scoreShoot -= angleDiff * 22; 
//		System.out.println("****************scoreshoot efter ryggen: " + scoreShoot);

		// jo mere spids vinkelen mod maal er jo mindre score
		if (Hjaelper.angleDifference(retningPaaMaal, vinkelretPaaMaal) > 0.3)
			scoreShoot -= 10 * Math.pow(2 * Hjaelper.angleDifference(retningPaaMaal, vinkelretPaaMaal), 2);

		//		System.out.println("****************scoreshoot efter vinkel: " + scoreShoot);
		
		//If the ball is too high to kick it it is going to be a header so make sure we're close enough to head
		if (bold.getZ() > player.height / 2 && distToGoal > 50){
			scoreShoot -= distToGoal - 50;
		}
		
		return scoreShoot;
	}

	public void shoot(int boldDif, int pressure, int timeOnBall){
		shoot(shootPlaced, boldDif, pressure, timeOnBall);
	}
	
	public void shoot(boolean placed, int boldDif, int pressure, int timeOnBall){
		bold.setPenalty(false);
		player.speed *= 0.6;
		
		//TODO We need a method that calculates powerIntent. We need to make it a setting on the player.
		double powerIntent = 100;
		powerIntent += (r.nextGaussian() * 5) * (120 / player.shooting);
		
		bold.setCross(false);

		double op = r.nextInt(5);
		
		if (shootHigh) op = (r.nextDouble() * 7 + 14);
		if (op < 1) op = 1;
		
		System.out.println("shootX: " + shootX);
		System.out.println("shootY: " + shootY);
		
		double distance = Hjaelper.Distance(shootX, shootY, bold.getX(), bold.getY());
		double a = Math.atan2((shootX - bold.getX()), (shootY - bold.getY()));
		double vinkelPaaBold = Hjaelper.angleDifference(a, player.getA());
		if (vinkelPaaBold < 1) vinkelPaaBold = 1;
		
		
		
		double off = AIHelper.directionError(boldDif, pressure, player, AIHelper.PlayerAction.Shot);
		if (!placed)
			off *= 2;
		
		double power = calculatePower(powerIntent, off, vinkelPaaBold, op);
		
		double curl = calculateCurl(powerIntent);
		
		boolean writeFile = false;
		if (writeFile){
			File file = new File("D://test2.txt"); ///var/www/test_footiecake/app/webroot/files/chatlog.html

			try { 
				// check whether the file is existed or not
				if (file.exists()) {
					// create a new file if the file is not existed
					file.createNewFile();
				}
				// new a writer and point the writer to the file
				FileWriter writer = new FileWriter(file, true);

				// writer the content to the file
				writer.append("\r\n");
				writer.append("***player.shotpower<<" + player.shotpower + ">>***\r\n");
				writer.append("***player.shooting<<" + player.shooting + ">>***\r\n");
				writer.append("***vinkelPaaBold<<" + vinkelPaaBold + ">>***\r\n");
				writer.append("***off<<" + off + ">>***\r\n");
				writer.append("***boldDif<<" + boldDif + ">>***\r\n");
				writer.append("***a<<" + a + ">>***\r\n");
				writer.append("***power<<" + power + ">>***\r\n");
				writer.append("***op<<" + op + ">>***\r\n");
				writer.append("\r\n");
				// always remember to close the writer
				writer.close();
				writer = null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//skru hvis det ikke er udspark - hojde hvis det er
		if (player.isKeeper){
			bold.setZForce(op);
			power = 80 + (player.shotpower * 2 + 80 - r.nextInt(60)) - op;
		}else{
			player.getMyTeam().addReplayCode(-13);
			bold.setZForce(op);
			bold.setShotQuality((int)(player.shooting + 100 + r.nextInt(50)+1));
			bold.setCurl(curl);
			
//			System.out.println(curl);
			
//			//hvis bolden er langt fra mål skal bolden højt op - bruges til clearinger...-skal laves
//			if (Hjaelper.Distance(bold.getX(), bold.getY(), player.getOppTeam().getGoalX(), player.getOppTeam().getGoalY()) > 300){
//				bold.setZForce(40);
//				power *= 0.8;
//				curl *= 0.5;
//				bold.setCurl(curl);
//			}
		}

		bold.setSpeed(power);
		bold.setLastTouch(player);
		bold.setLastShot(player);
		bold.setShot(true);
		bold.setA(a);
		bold.adjustAForCurl(new Point(shootX, shootY));
		bold.setA(bold.getA() + off);
		System.out.println(curl);
		player.setMatchMessage("shoots!");

		if (placed && r.nextDouble() < 0.1){
			//lob (fjernet midlertidigt. Skal undgås på straffe / frispark og skal tjekkes på styrke, højde osv.)
//			bold.setZForce(30);
//			bold.setSpeed(power * 0.3);
//			bold.setA(Math.atan2(player.getOppTeam().getGoalX() - player.getX(), player.getOppTeam().getGoalY() + 29 - player.getY()));
		}

		player.getMyTeam().addStatShots();
		player.playerMatchStats.shots++;
		DAOCake.updateMatchStat(player.getMatch().getMatchId(), player.getMyTeam().getId(), "shots", player.getMyTeam().getStatShots());

		player.wait += 500;
		player.setState(player.getStateShooting());
		
		player.checkTeammatesOffside();
	}
	
	
	
	
	/**
	 * Calculates the power of a shot
	 * @param off how bad the shot is, the closer to 0 the better
	 * @param vinkelPaaBold the players angle on the ball
	 * @param op the amount of which the ball travels upwards
	 * @return the power of a shot
	 */
	public double calculatePower(double powerIntent, double off, double vinkelPaaBold, double op){
		final double maxAngle = 1.5;//If the ball is hit at a big enough angle, it affects the shotpower
		if(vinkelPaaBold > maxAngle) {
			vinkelPaaBold = 1 + Math.pow(vinkelPaaBold, 2) *0.21; 
			op = op / (vinkelPaaBold / 1.5); //hvis man står meget skævt på bolden, ryger den ikke så langt op i luften
		}
		if(off < 0) off *= -1;
		if(off > 0.3) off = Math.pow(1.5+off, 3);		
		double power = ((powerIntent + 40 + (player.shotpower * 1.1) + (player.shooting/5)) / vinkelPaaBold) - op * 2;
		power -= off;
		if (shootPlaced) power *= 0.8;
		
		if(power < 1) power = 1;
		return power;
	}
	
	public double calculateHeaderPower(double vinkelPaaBold, double op){
		final double maxAngle = 1.3;//If the ball is hit at a big enough angle, it affects the shotpower
		if(vinkelPaaBold > maxAngle) {
			vinkelPaaBold = Math.pow(vinkelPaaBold, 3);
		}
		else if(vinkelPaaBold < maxAngle) vinkelPaaBold = 1;		
		double power = (10 + r.nextInt(10) + (player.heading * 0.8) + (bold.getSpeed() / 8)) / vinkelPaaBold;
		
		return power;
	}
	/**
	 * Calculates the curl on a shot
	 * @return the curl of the shot
	 */
	public double calculateCurl(double powerIntent){
		
		//Max curl is 0.015. The better shooting the more curl the player can do
		double result = 0.015 * player.shooting / 100;
		
		//The more power we want to put into the shot the less curl we get
		if (powerIntent > 50) result *= 50.0 / powerIntent;
		
		//random choice of curl for now
		//No we want to see curl
//		result *= (r.nextDouble() * 100 + 1.0) / 100.0;
		
		return result;
	}
	
	public void head(int boldDif){
		head(shootX, shootY, boldDif);
	}
	
	public void head(int tX, int tY, int boldDif){
		player.speed *= 0.2;

		bold.setCross(false);

		double a = Math.atan2((tX - player.getX()), (tY - player.getY()));

		double off = r.nextDouble() - r.nextDouble();

		boldDif /= 10;
		if (boldDif <= 0) boldDif = 1;
//		System.out.println("Heading Bolddif: " + boldDif);
		off *= 4 / ((player.heading * 2) - boldDif);

//		System.out.println("Heading OFF = " + off + ", a: " + a);
		a += off;

		//intelligent hoejde paa bolden senere - skal laves
		double op = r.nextDouble() * 3 + r.nextDouble() * -3;

		double vinkelPaaBold = Hjaelper.angleDifference(a, player.getA());
		if (vinkelPaaBold < 1) vinkelPaaBold = 1;

		double power = 20 + r.nextInt(10) + (player.heading / vinkelPaaBold + player.heading);

		bold.setZForce(op);
		bold.setCurl(0);

		bold.setShotQuality((int)(player.heading + 100 + r.nextInt(100)+1));
		bold.setSpeed(power);
		bold.setZForce(op);


		bold.setLastTouch(player);
		bold.setLastShot(player);
		bold.setShot(true);
		bold.setA(a);
		player.setMatchMessage("heads!");

		if (!player.isKeeper){
			player.getMyTeam().addStatShots();
			DAOCake.updateMatchStat(player.getMatch().getMatchId(), player.getMyTeam().getId(), "shots", player.getMyTeam().getStatShots());
		}
		
		player.playerMatchStats.headers++;
		player.wait += 500;
		player.setState(player.getStateShooting());
		
		player.checkTeammatesOffside();
	}

	public int getShootX() {
		return shootX;
	}

	public void setShootX(int shootX) {
		this.shootX = shootX;
	}

	public int getShootY() {
		return shootY;
	}

	public void setShootY(int shootY) {
		this.shootY = shootY;
	}

	public boolean isShootHigh() {
		return shootHigh;
	}

	public void setShootHigh(boolean shootHigh) {
		this.shootHigh = shootHigh;
	}
	
}
