package ai;

import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Player;
import model.Pitch;

public class AIClear{

	private Player player;
	private Bold bold;
	private Random r;
	
	public AIClear(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}

	public double getScore(double pressure) {

		// taktik
		double scoreClear = -200;

		if (Hjaelper.Distance(player.getX(), 0, player.getMyTeam().getGoalX(), 0) < 200){
			scoreClear = (200 - Hjaelper.Distance(player.x, player.y, player.getMyTeam().getGoalX(), player.getMyTeam().getGoalY() + 30)) / 7;
			scoreClear += pressure;
		}
		
		return scoreClear;
	}
	
	public void clear(int boldDif){
		player.speed *= 0.8;
		final double maxAngle = 1.3;//If the ball is hit at a big enough angle, it affects the shotpower
		player.playerMatchStats.clearances++;
		
		double q = Math.atan2(player.getOppTeam().getGoalX() - player.getMyTeam().getGoalX(), 0);
		double vinkelPaaBold = Hjaelper.angleDifference(q, player.getA());
//		System.out.println("CLEAR q: " + q);
//		System.out.println("Player A: " + player.getA());
//		System.out.println("CLEAR vinkelPaaBold: " + vinkelPaaBold);
		
		//Players try to clear the ball away from their own goal, and away from the middle of the pitch.
		if(vinkelPaaBold > maxAngle){
			double excessAngle = vinkelPaaBold - maxAngle;
//			System.out.println("ExcessAngle: " + excessAngle);			
			if(q < 0){
//				System.out.println("Mit hold har mål længst mod højre");
				if (player.y < player.getPitch().getPitchMidY()){
//					System.out.println("Jeg er tættere på toppen af banen");
					if(player.getA() >= 0.5 && player.getA() < 4){
						q -= excessAngle;
						vinkelPaaBold -= excessAngle;
					}
				}
				else{
//					System.out.println("Jeg er tættere på bunden af banen");
					if(player.getA() < 2.63  || player.getA() > 5){
						q += excessAngle;
						vinkelPaaBold += excessAngle;
					}
				}
			}
			else if(q >= 0){
//				System.out.println("Mit hold har mål længst mod venstre");
				if (player.y < player.getPitch().getPitchMidY()){
//					System.out.println("Jeg er tættere på toppen af banen");
					if(player.getA() >= 2.27 && player.getA() < 5.76){
						q += excessAngle;
						vinkelPaaBold += excessAngle;
					}
				}
				else{
//					System.out.println("Jeg er tættere på bunden af banen");
					if(player.getA() < 0.77  || player.getA() > 4.2){
						q -= excessAngle;
						vinkelPaaBold -= excessAngle;
					}
				}
			}
		}
//		System.out.println("CLEAR q: " + q);
		double off = r.nextDouble() - r.nextDouble();
		boldDif /= 10;
		if (boldDif <= 0) boldDif = 1;

		off *= 0.3;
		q += off;
		double op = (25 + r.nextInt(50) / 4);
		
		if (vinkelPaaBold < 1) vinkelPaaBold = 1;
		
		if(vinkelPaaBold > maxAngle) {
			vinkelPaaBold = 1 + Math.pow(vinkelPaaBold, 2) *0.22;
			op = op / (vinkelPaaBold / 1.3);
		}
		double power = ((140 + (player.shotpower * 1.1) + (player.shooting/5)) / vinkelPaaBold) - op * 2;
		if(off < 0) off *= -1;
		if(off > 0.3) off = Math.pow(1.5+off, 3);	
		power -= off;
		if(power < 1) power = 1;
//		System.out.println("CLEAR op: " + op);
//		System.out.println("CLEAR vinkelPaaBold: " + vinkelPaaBold);
//		System.out.println("CLEAR player.shotpower: " + player.shotpower);
//		System.out.println("CLEAR player.shooting: " + player.shooting);
//		System.out.println("CLEAR off: " + off);
//		System.out.println("CLEAR power: " + power);
		double curl = r.nextDouble() / 60;
		bold.setCurl(curl);
		bold.setZForce(op);
		bold.setSpeed(power);
		bold.setLastTouch(player);
		bold.setA(q);
		player.setMatchMessage("clears the ball!");

		player.wait += 500;
		player.setState(player.getStateClearing());
		
		player.checkTeammatesOffside();
	}
}
