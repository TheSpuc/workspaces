package ai;

import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.Settings;
import model.StateHasBall;
import model.Sysout;
import model.Team;

public class AIRunWithBall{

	private Player player;
	private Random r;
	private Bold bold;
	private Pitch pitch;
	
	public AIRunWithBall(Player player, Bold bold, Pitch pitch) {
		this.player = player;
		this.bold = bold;
		this.pitch = pitch;
		r = new Random();
	}


	public double getScore(double igennem, Player pClosestOppInFront, Player pClosestOppBehind) {

		// taktik
		double scoreRunWithBall = player.getDribble()/2 + 30;

		// jo mere vi er igennem jo mere skal vi drible
		if (igennem > 0) scoreRunWithBall += igennem * 5;

		//jo taettere paa modstanderens maalmand jo mindre dribler vi
		if (Hjaelper.Distance(player.getX(), player.getY(), player.getOppTeam().getKeeper().getX(), player.getOppTeam().getKeeper().getY()) < 130)
			scoreRunWithBall -= 4 * (130 -  Hjaelper.Distance(player.getX(), player.getY(), player.getOppTeam().getKeeper().getX(), player.getOppTeam().getKeeper().getY()));

		//Hvis vi nærmer os en modstander foran er der ikke så meget plads at løbe med bolden på.
		if (pClosestOppInFront != null && Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getX(), pClosestOppInFront.getY()) < 100)
			scoreRunWithBall -= 0.7 * (100 - Hjaelper.Distance(bold.getX(), bold.getY(), pClosestOppInFront.getX(), pClosestOppInFront.getY()));
		
		//Hvis vi en modstander nærmer sig bagfra skal vi nok overveje at gøre noget andet end at løbe med bolden
		if (pClosestOppBehind != null && Hjaelper.Distance(player.getX(), player.getY(), pClosestOppBehind.getX(), pClosestOppBehind.getY()) < 50){
			scoreRunWithBall -= 0.7 * (50 - Hjaelper.Distance(player.getX(), player.getY(), pClosestOppBehind.getX(), pClosestOppBehind.getY())) + player.strength - 10 + player.acceleration - pClosestOppBehind.strength - pClosestOppBehind.acceleration;
		}
		
		
		return scoreRunWithBall;
	}

	public void runWithBall(double tX, double tY, Team myTeam, Team oppTeam, int boldDif, boolean igennem){

		if (boldDif <= 0) boldDif = 1;

		if (System.currentTimeMillis() - player.getLastdribble() > 950 - (r.nextInt((int)player.getDribbling()) * 3)
				&& player.getPlayerAction() != PlayerAction.passing){

			player.playerMatchStats.dribbles++;
			
			player.setLastdribble(System.currentTimeMillis());
			Sysout.print("RunWith speed = " + player.speed, "airunWithBall");
			player.speed *= 0.4 + ((player.dribbling + 80) / 400) + (r.nextDouble()* 0.1);
			Sysout.print("RunWith after " + player.speed, "airunWithBall");
			player.urgent = true;

			double dribtX = tX;
			double dribtY = tY;

			double q;
			double tempX = player.getX(), tempY = player.getY();

			boolean block = false; //Nogen i vejen?

			q = Math.atan2((dribtX - bold.getX()), (dribtY - bold.getY()));

			tempX += 15 * Math.sin(q);
			tempY += 15 * Math.cos(q);
			
			int limitWhile = 1000;
			while (!igennem && Hjaelper.Distance(tempX, tempY, dribtX, dribtY) > 5 &&
					Hjaelper.Distance(tempX, tempY, player.getX(), player.getY()) < 70  && 
					limitWhile > 0){

				limitWhile--;
				q = Math.atan2((dribtX - tempX), (dribtY - tempY));
				tempX += 8 * Math.sin(q);
				tempY += 8 * Math.cos(q);

				for (Player p : oppTeam.getPlayers()){
					if (p != player){
						if ((Hjaelper.Distance(tempX, tempY, p.getXIn(30), p.getYIn(30)) < 25)
								&& Hjaelper.Distance(player.getX(), player.getY(), tempX, tempY) + 1 > Hjaelper.Distance(p.getX(), p.getY(), tempX, tempY)){

							q = Math.atan2((p.getX() - bold.getX()), (p.getY() - bold.getY()));
							dribtX = p.getXIn(30);
							dribtY = p.getYIn(30);

							block = true;

							q -= Math.PI / 2;
							if (q > Math.PI * 2) q -= Math.PI * 2;
							else if(q < 0) q += Math.PI * 2;

							double t1x = dribtX - 40 * Math.sin(q);
							double t1y = dribtY - 40 * Math.cos(q);
							double t2x = dribtX + 40 * Math.sin(q);
							double t2y = dribtY + 40 * Math.cos(q);

							if (pitch.isAlmostOut((int)player.getX(), (int)player.getY())){


								if (pitch.isOut((int)t1x, (int)t1y)){
									t1x = myTeam.getGoalX();
									t1y = myTeam.getGoalY();
								}
								else if (pitch.isOut((int)t2x, (int)t2y)){
									t2x = myTeam.getGoalX();
									t2y = myTeam.getGoalY();
								}
							}

							double d1 = Math.sqrt(Math.abs(t1x - player.getX()) * Math.abs(t1x - player.getX()) + Math.abs(t1y - player.getY()) * Math.abs(t1y - player.getY()));
							double d2 = Math.sqrt(Math.abs(t2x - player.getX()) * Math.abs(t2x - player.getX()) + Math.abs(t2y - player.getY()) * Math.abs(t2y - player.getY()));
							boolean d1OK = true;
							boolean d2OK = true;


							if (d1 > d2 && d1OK){
								dribtX = t1x;
								dribtY = t1y;
							}
							else if (d2OK){
								dribtX = t2x;
								dribtY = t2y;
							}
						}
					}
				}
			}

			q = Math.atan2((dribtX - bold.getX()), (dribtY - bold.getY()));
			tempX = bold.getX() + 40 * Math.sin(q);
			tempY = bold.getY() + 40 * Math.cos(q);

			for (Player p : oppTeam.getPlayers()){
				if (Hjaelper.Distance(tempX, tempY, p.getX(), p.getY()) -20 <
						Hjaelper.Distance(player.getX(), player.getY(), tempX, tempY))
					block = true;
			}

			double rand = (r.nextDouble() / ((player.dribbling + 100 / boldDif)));
			if (r.nextDouble() < 0.5) rand *= -1;

			double a = Math.atan2((dribtX - bold.getX()), (dribtY - bold.getY())) + rand;
			player.setTargetA(Math.atan2((dribtX - player.getX()), (dribtY - player.getY())));
			player.targetX = tempX;
			player.targetY = tempY;
			bold.setA(a);
			bold.setLastTouch(player);
			bold.setPassTo(null);
			bold.setShot(false);
			bold.setCross(false);
			player.setPlayerAction(PlayerAction.dribble);


			player.checkTeammatesOffside();
			int dif1 = r.nextInt(boldDif);
			int dif2 = r.nextInt(boldDif);
			double randoom = (dif1 - dif2) / ((player.dribbling+100) * 0.05);

			//Til test
			randoom = 0;

			if (Settings.SO) Sysout.print("dribbling: " + player.dribbling + ", dribbling * 0.1: " + player.dribbling * 0.1, "airunWithBall");
			if (Settings.SO) Sysout.print("BoldDif!!!!!!!: " + boldDif, "airunWithBall");
			if (Settings.SO) Sysout.print("Randoom!!!!!!!: " +randoom, "airunWithBall");

			//q er retningen mod target
			//qb er retningen mod bolden
			//qba er forskellen mellem de to = hvor meget han skal vende med bolden
			//bdist er foskellen på afstanden mellem bolden og hen mod dribletarget og afstanden fra spilleren hen mod dribletarget. 
			//	bdist bruges til at putte lidt mere fart i driblingen hvis bolden er under eller bag spilleren så den kommer frem foran ham igen
			double qb = Math.atan2(bold.getX() - player.x, bold.getY() - player.y);
			double qba = Hjaelper.angleDifference(q, qb);
			double bdist = Hjaelper.Distance(tempX, tempY, bold.getX(), bold.getY()) + 8 - Hjaelper.Distance(tempX, tempY, player.getX(), player.getY());
			if (bdist < 0) bdist = 0;

			//Hvis spilleren skal vende om skal der ikke tages hensyn til bdist. Ellers vil bolden komme for langt foran spilleren
			if (Hjaelper.angleDifference(player.getA(), q) > Math.PI / 2){
				bdist = 0;
			}
			
			Sysout.print(bdist + " - bdist", "airunWithBall");

			if (qba > player.getSettings().getSharpTurnLimit()){//Vi skal vende meget
				bold.setSpeed(8 + player.acceleration * 0.10 + player.agility * 0.10);
				player.setMatchMessage("turns with the ball");
				player.getMatch().addCommentary("turns with the ball");
				Sysout.print("turns with the ball", "airunWithBall");
			}
			else if (qba > Math.PI / 4){//Vi skal vende lidt
				bold.setSpeed(Hjaelper.playerSpeedToBallSpeed(player.speed) * 1.4 + player.acceleration * 0.1);
				player.getMatch().addCommentary("changes direction with the ball");
				Sysout.print("changes direction with the ball", "airunWithBall");
			}
			else if (block){//forsvarer foran
				double sp = Hjaelper.playerSpeedToBallSpeed(player.speed) * 1.5 + bdist * 1.7;
				bold.setSpeed(sp);
				player.setMatchMessage("dribbles with the ball");
				player.getMatch().addCommentary("dribbles with the ball");
				Sysout.print("runs at the defender", "airunWithBall");
			}
			else{//ingen forsvarer foran
				double sp = Hjaelper.playerSpeedToBallSpeed(player.speed) * 1.6 + bdist * 1.8;
				bold.setSpeed(sp);
				player.setMatchMessage("runs with the ball");
				player.getMatch().addCommentary("runs with the ball");
				Sysout.print("runs with the ball", "airunWithBall");
			}

			player.setTargetX(bold.getX() + 10 * Math.sin(bold.getA()));
			player.setTargetY(bold.getY() + 10 * Math.cos(bold.getA()));
			
			player.setState(player.getStateRunWithBall());
		}
	}
}
