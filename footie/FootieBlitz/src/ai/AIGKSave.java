package ai;

import java.util.Random;

import model.Bold;
import model.Match;
import model.Pitch;
import model.Player;
import model.Sysout;
import model.Match.MatchState;

public class AIGKSave {

	Player player;
	Match match;
	Pitch pitch;
	Bold bold;
	Random r = new Random();	

	public AIGKSave(Player player, Match match, Pitch pitch, Bold bold) {
		super();
		this.player = player;
		this.match = match;
		this.pitch = pitch;
		this.bold = bold;
	}


	public void save(){
		int hold = 2;
		if (player.getMyTeam().getGoalX() < player.getOppTeam().getGoalX())
			hold = 1;
		if (match.getState().equals(MatchState.PENALTYSHOOTOUT))
			if (match.getSetPieceX() < pitch.getPitchMidX())
				hold = 1;
			else
				hold = 2;

		/*
	Acceleration + topspeed afgør om målmanden når at komme hen til bolden
	Reaction (i forhold til hvor lang tid bolden har været undervejs) afgør om han når at reagere og forsøge at redde den
	Shotstopping afgør om han får en hånd, fod eller andet på bolden
	Handling (i forhold til boldens hastighed og skuddets kvalitet) afgør om han kan holde bolden eller om den bliver pareret
		 */

		double abc = r.nextInt(40);
		int saveQuality = (int)(player.shotstopping * 2.5 + r.nextInt(80) + 100);
		double shotqual = bold.getShotQuality(); 

		player.playerMatchStats.saveAttempts++;
		double time = System.currentTimeMillis() - bold.getBallTimer();
		if (time > 500) shotqual /= (time / 500);

		double saveP = 450 + r.nextInt(250) - (player.reaction * 0.7 + r.nextInt((int)player.shotstopping) + player.shotstopping);
		double shotP = System.currentTimeMillis() - bold.getBallTimer() - (bold.getCurl() * 20);
		if (shotP > 600) shotP -= (shotP - 600) / 1.2;//hvis der er gået mere end 0.6 sekunder siden skudet, bliver det ikke meget nemmere at tage
		shotP -= bold.getSpeed() / 5;

		Sysout.print("Redning hvis: 450 + r.nextInt(250) - (reaction  * 0.7 + shotstopping * 2.2) < tid - boldtid - bold.getCurl*20: " + saveP + " < " + shotP  + " og savequal > shotqual: " + saveQuality + " > " + shotqual, "player,moveGK,save");
		Sysout.print("Griber bolden hvis: (35 + handling*1.2 + abc + (time / 10) > boldspeed/2.5 + shotqual - rand(40): " + (35 + player.handling*1.2 + abc + (time / 10)) + " > " + (bold.getSpeed()/2.5 + shotqual) + " (- rand(40))", "player,moveGK,save");

		Sysout.print("handling: "+player.handling, "player,moveGK,save");
		Sysout.print("abc: "+abc, "player,moveGK,save");
		Sysout.print("time / 20: "+(time / 20), "player,moveGK,save");
		Sysout.print("boldspeed: "+bold.getSpeed(), "player,moveGK,save");
		//					Sysout.print("abc: "+ abc);
		//						Sysout.print("boldgetBalltimer(): "+bold.getBallTimer());
		//					Sysout.print("System.currentTimeMillis(): " + System.currentTimeMillis());
		Sysout.print("Boldspeed: "+bold.getSpeed(), "player,moveGK,save");

		if (saveP < shotP && saveQuality > shotqual){	
			Sysout.print("redning", "player,moveGK,save");
			if ((35 + player.handling * 1.2 + abc + (time / 10)) > bold.getSpeed()/2.5 + shotqual - r.nextInt(40)){
				//Sysout.print("har bolden");
				bold.setHasBall(player);
				bold.setZ(0);
				bold.setZForce(0);
				bold.setSpeed(0);
				bold.setLastTouch(player);
				bold.setHasBall(player);
				player.saveTime = System.currentTimeMillis();
				player.speed = 0;
				bold.setShotQuality(0);
			}
			else { 
				double randomDouble = r.nextDouble();
				if (player.x > pitch.getPitchPosX() + (pitch.getPitchWidth() / 2))
					bold.setTarget(player.x + 15 - r.nextInt(100), player.y - 200 + r.nextInt(400));						
				else
					bold.setTarget(player.x - 15 + r.nextInt(100), player.y - 200 + r.nextInt(400));		

				bold.setLastTouch(player);
				bold.setHasBall(null);
				bold.setSpeed(bold.getSpeed() * (randomDouble * 0.8));
				bold.setZForce(bold.getZForce() * (1- randomDouble + r.nextDouble()));
				player.saveTime = System.currentTimeMillis();
				player.speed = 0;
			}
			player.wait = 500;
			player.playerMatchStats.saves++;
			if (bold.getLastShot() != null) bold.getLastShot().playerMatchStats.shotsOnTarget++;
			bold.setShot(false);
			bold.setLastShot(null);
			bold.setShotQuality(0);
		}
		else{// ingen redning
			Sysout.print("drop", "player,moveGK,save");
			//					Sysout.print(bold.getSpeed());
			//chance for at bolden stadig rammer målmanden (her skal rushingOut med!)
			if(r.nextInt(100) < 35){
				Sysout.print("Målmanden nåede ikke at reagere, men bolden ramte ham", "player,moveGK,save");
				player.NoReact();
			}

			player.wait = 500; //vent lidt så vi ikke prøver at tage bolden igen om 500 ms.
		}
		if (player.kasterSig){
			player.kasterSig = false;
			player.wait = 750;
		}
	}
}