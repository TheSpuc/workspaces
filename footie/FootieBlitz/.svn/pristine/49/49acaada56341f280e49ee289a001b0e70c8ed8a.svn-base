package ai;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Pitch;
import model.Player;
import model.PlayerAction;
import model.Sysout;

public class AIRunOffBall{

	private Player player;
	private Bold bold;
	private Random r;
	
	public AIRunOffBall(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}

	public double getScore() {

		double scoreRun = 20 + r.nextInt(11);

		//Lob lige fremad
		if (player.getMyTeam().getGoalX() < player.getOppTeam().getGoalX()){
			player.runTargetX = player.getPitch().getPitchPosX() + player.getPitch().getPitchWidth() - 100;
		}
		else{
			player.runTargetX = player.getPitch().getPitchPosX() + 100;
		}

		player.runTargetY = (int)player.startPosY;
		
		//HVis et punkt på throughline er langt foran offsidelinien er det fordi der er plads mellem forsvarerne eller mellem den yderste forsvarer og sidelinien
		ArrayList<Point> throughLine = player.getOppTeam().getThroughLine(player.getPitch());
		Point throughPoint = null;
		
		double thisPointScore = -200;
		//FOr hvert punkt skal vi regne med om vi er den spiller på holdet der er tættest på. Det er ham der er tættest på der skal løbe.
		for (Point p : throughLine){
			thisPointScore = Math.abs(p.x - player.getOppTeam().getGoalX()) - player.getOppTeam().getOffsideDistToGoal();
			double throughX = (int)(player.getOppTeam().getGoalX() - player.getOppTeam().getOffsideDistToGoal());
			boolean closest = true;
			int i = 0;
			
			//Find ud af om der er holdkammerater der er tættere på punktet
			while (closest && i < player.getMyTeam().getPlayers().size()){
				Player mate = player.getMyTeam().getPlayers().get(i);
				if (!mate.equals(player) && !mate.isKeeper){
					if (Hjaelper.Distance(player.x, player.y, throughX, p.y) > Hjaelper.Distance(mate.x, mate.y, throughX, p.y))
						closest = false;
				}
				i++;
			}
			if (closest){
				if (player.getOppTeam().getGoalX() < player.getMyTeam().getGoalX())
					throughX = (int)(player.getOppTeam().getGoalX() + player.getOppTeam().getOffsideDistToGoal());

				thisPointScore -= Hjaelper.Distance(player.x, player.y, p.x, p.y) / 2;
				thisPointScore -= Hjaelper.Distance(bold.getX(), bold.getY(), p.x, p.y) / 2;

				if (thisPointScore > scoreRun){
					scoreRun = thisPointScore;
					throughPoint = p;

					player.runTargetY = throughPoint.y;
					player.runTargetX = (int)throughX;
					
					if (player.runTargetY > player.getPitch().getPitchHeight() + player.getPitch().getPitchPosY() - 10)
						player.runTargetY -= 10;
					else if (player.runTargetY < player.getPitch().getPitchPosY() + 10)
						player.runTargetY += 10;
				}
			}
		}
		
		int rand = r.nextInt(15);
		scoreRun -= rand;
		
		//If the player in possession is holding up the ball two of his nearest team mates need to help him out
		if (bold.getLastTouch() != null && bold.getLastTouch().getMyTeam().equals(player.getMyTeam()) &&
				(bold.getLastTouch().getPlayerAction().equals(PlayerAction.holdUpBall) ||
				bold.getLastTouch().getPlayerAction().equals(PlayerAction.dribble)
						)){
			
			//The two team mates who need to help out
			Player mate1 = null;
			Player mate2 = null;
			
			//The two points they need to run to to help out
			Point point1 = new Point((int)bold.getLastTouch().getX(), (int)bold.getLastTouch().getY());
			Point point2 = new Point((int)bold.getLastTouch().getX(), (int)bold.getLastTouch().getY());
			
			//How far from the player on the ball the two points should be
			int distanceFromPlayer = 8 * 15; //8 pixel = 1 meter
			
			//The first point is directly behind the player on the ball if he's holding up play
			double dir1 = 0;
			if (bold.getLastTouch().getPlayerAction().equals(PlayerAction.holdUpBall)){
				dir1 = Math.atan2(player.getMyTeam().getGoalX() - bold.getLastTouch().getX(), 0);
				point1.x += distanceFromPlayer * Math.sin(dir1);
				point1.y += distanceFromPlayer * Math.cos(dir1);
			}
			
			boolean point2Up = false;
			//The second point is in frnot of the player on the ball - between the player and the sideline
			//If the player on the ball is too close to the side line point2 will be between the player on the ball and the middle of the pitch
			if (Math.abs(bold.getLastTouch().getY() - player.getPitch().getPitchPosY()) < distanceFromPlayer){
				
				double dir2 = Math.atan2(player.getOppTeam().getGoalX() - bold.getLastTouch().getX(), 1); //down
				point2.x += distanceFromPlayer * Math.sin(dir2);
				point2.y += distanceFromPlayer * Math.cos(dir2);
			}
			else if (Math.abs(bold.getLastTouch().getY() - player.getPitch().getPitchPosY() + player.getPitch().getPitchHeight()) < distanceFromPlayer){
				double dir2 = Math.atan2(player.getOppTeam().getGoalX() - bold.getLastTouch().getX(), -1); //up
				point2.x += distanceFromPlayer * Math.sin(dir2);
				point2.y += distanceFromPlayer * Math.cos(dir2);
				point2Up = true;
			}
			else{
				double dir2 = 0;
				if (bold.getLastTouch().getY() < player.getPitch().getPitchMidY()){
					dir2 = Math.atan2(player.getOppTeam().getGoalX() - bold.getLastTouch().getX(), -1); //up
					point2Up = true;
				}
				else{
					dir2 = Math.atan2(player.getOppTeam().getGoalX() - bold.getLastTouch().getX(), 1); //down
				}
				point2.x += distanceFromPlayer * Math.sin(dir2);
				point2.y += distanceFromPlayer * Math.cos(dir2);
			}
			
			//If the player in possession is dribbling then the first point is to the same side as point2 but not in front of the player
			if (bold.getLastTouch().getPlayerAction().equals(PlayerAction.dribble)){
				if (point2Up){
					dir1 = Math.atan2(0, -1);
				}
				else{
					dir1 = Math.atan2(0, 1);
				}
				point1.x += distanceFromPlayer * Math.sin(dir1);
				point1.y += distanceFromPlayer * Math.cos(dir1);
			}
			
			for (Player p : player.getMyTeam().getPlayers()){
				if (!p.equals(player.getMyTeam().getKeeper()) && !p.equals(bold.getLastTouch())){
					if (mate1 == null){
						mate1 = p;
					}
					else if (mate2 == null){
						mate2 = p;
					}
					else if (Hjaelper.Distance(p.getX(), p.getY(), point1.x, point1.y) < 
							Hjaelper.Distance(mate1.getX(), mate1.getY(), point1.x, point1.y)){
						
						mate1 = p;
					}
					else if (Hjaelper.Distance(p.getX(), p.getY(), point2.x, point2.y) < 
							Hjaelper.Distance(mate2.getX(), mate2.getY(), point2.x, point2.y)){
						
						mate2 = p;
					}
				}
			}
			
//			System.out.println("mate1: " + mate1.getShirtNumber());
//			System.out.println("mate2: " + mate2.getShirtNumber());
			
			if (player.equals(mate1)){
				player.runTargetY = point1.y;
				player.runTargetX = point1.x;
				scoreRun = 90;
			}
			else if (player.equals(mate2)){
				player.runTargetY = point2.y;
				player.runTargetX = point2.x;
				scoreRun = 90;
			}
		}
		else{
			int numberOfRuns = 0;
			for (Player pl : player.getMyTeam().getPlayers()){
				if (pl.getPlayerAction() != null && pl.getPlayerAction().equals(PlayerAction.run))
					numberOfRuns++;
			}

			scoreRun -= numberOfRuns * 30;

			scoreRun += (player.getRuns() - 50) / 4;
		}
		
		Sysout.print("Througpoint: " + throughPoint, "AIRunOffBall");
		Sysout.print("scoreRun: " + scoreRun, "AIRunOffBall");
		Sysout.print("rand: " + rand, "AIRunOffBall");
		
		return scoreRun;
	}
	
	public void run(int boldDif){
		player.setState(player.getStateRunOffBall());
	}
}
