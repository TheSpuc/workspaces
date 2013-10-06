package ai;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Random;

import model.Bold;
import model.Hjaelper;
import model.Player;
import model.PlayerAction;
import model.Sysout;

public class AIHoldUpBall{

	private Player player;
	private Bold bold;
	private Random r;
	private Point target = new Point(); //The target for where we want to place the ball (away from the closest opponents)
	ArrayList<Point> pointsAwayFromOpp = new ArrayList<Point>(); //The targets pointing away from close oppoenents
	
	public AIHoldUpBall(Player player, Bold bold) {
		this.player = player;
		this.bold = bold;
		r = new Random();
	}

	public double getScore(double pressure, double distToClosestOpp) {

		//If we are not under pressure it could be a good idea to hold up the ball and wait for support
		//We assume that the other ai scores are going to be very good if there is a good opportunity to shoot / pass / dribble etc.
		//so here we just focus on whether it's appropriate to hold up the ball in case other ai scores are not high
		
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//scoreHoldUpBall SHOULD NEVER BE GREATER THAN 70 TO MAKE SURE GOOD OPPORTUNITIES TO PASS / SHOOT ETC. GET HIGHER PRIORITY
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!
		//IT MAY ALSO BE A GOOD IDEA TO MAKE SURE scoreHoldUpBall IS NEVER LESS THAN 20 TO MAKE SURE THE PLAYER TRIES TO KEEP POSSESSION IF 
		//THERE IS NOTHING ELSE TO DO WITH THE BALL
		//!!!!!!!!!!!!!!!!!!!!!!!!!!!!
		
		double scoreHoldUpBall = 0;
		
		//As a base line it's a good idea to hold up the ball if there is not a lot of support in front of us
		
		double levelOfSupport = 0; //The amount of support from team mates in front of the player
		
		for (Player p : player.getMyTeam().getPlayers()){
			if (!p.isKeeper && !p.equals(player)){
				if (Hjaelper.Distance(p.x, 0, player.getOppTeam().getGoalX(), 0) < Hjaelper.Distance(player.x, 0, player.getOppTeam().getGoalX(), 0)){
					levelOfSupport++;
				}
			
			}
		}

		//The more opponents we have close to the player (pressure) the more difficult it is to hold up the ball
		Sysout.print("Pressure: " + pressure, "aiholdupball");
		Sysout.print("levelOfSupport: " + levelOfSupport, "aiholdupball");
		
		scoreHoldUpBall = 40 - levelOfSupport * 5 - distToClosestOpp / 8;
		
		Sysout.print("scoreHoldUpBall: " + scoreHoldUpBall, "aiholdupball");
		
		//If a player is on his way to a great position and not heading for offside - and if we are not being closed down - 
		//we could hold up the ball and wait for him to be in the perfect position for a pass.
		
		//We need to find out where we want the ball and the player. We may just jog around with the ball if there is no pressure
		//but if there are defenders close by we'd want to keep the ball away from them and keep the player between the defenders and the ball.
		//If we're waiting for a team mate to get in position we want to get the player in a good position to make the pass  
		
		pointsAwayFromOpp.clear();
		int sumX = 0, sumY = 0; //The sum of x and y in all pointsAwayFromOpp
		
		for (Player p : player.getOppTeam().getPlayers()){
			//If the opponent is close we check which point to take the ball to so we can keep the ball away from this opponent if holding up the ball
			int dist = (int)Hjaelper.Distance(p.getX(), p.getY(), player.getX(), player.getY());
			if (dist < 75){
				double dir = Math.atan2(bold.getX() - p.getX(), bold.getY() - p.getY());
				Point point = new Point();
				point.x = (int)(player.getX() + Math.sin(dir) * (75 - dist));
				sumX += point.x;
				point.y = (int)(player.getY() + Math.cos(dir) * (75 - dist));
				sumY += point.y;
				pointsAwayFromOpp.add(point);
			}
		}
		
		//Target is the middle of all pointsAwayFromOpp if there are any points
		if (pointsAwayFromOpp.size() > 0){
			target.x = sumX / pointsAwayFromOpp.size();
			target.y = sumY / pointsAwayFromOpp.size();
		}
		else{
			//If there are no opponents close by we take the ball forward
			target.x = player.getOppTeam().getGoalX();
			target.y = player.getOppTeam().getGoalY() + 29;
		}
		
		Sysout.print("player.x: " + player.x, "aiholdupball");
		Sysout.print("player.y: " + player.y, "aiholdupball");
		Sysout.print("aiholdupball target: " + target, "aiholdupball");
		
		//If we're almost out of the pitch it's not the best place to dribble 
		//(For now because they often end up running out of the pitch)
		//TODO
		if (player.getPitch().isAlmostOut((int)bold.getX(), (int)bold.getY())){
			scoreHoldUpBall /= 2;
		}
		
		if (scoreHoldUpBall < 20) scoreHoldUpBall = 20;
		
		return scoreHoldUpBall;
	}
	
	public void holdUpBall(int boldDif){
		
		player.getOppTeam().getOppsMarked().clear();

		player.targetSpeed = 20;
		
		player.urgent = false;

		player.targetX = target.x;
		player.targetY = target.y;
		
		double a = Math.atan2((target.x - bold.getX()), (target.y - bold.getY()));
		bold.setA(a);
		bold.setLastTouch(player);
		bold.setPassTo(null);
		bold.setShot(false);
		bold.setCross(false);
		player.setPlayerAction(PlayerAction.holdUpBall);

		player.checkTeammatesOffside();
		player.setState(player.getStateHoldUpBall());
		
		double anglediff = Hjaelper.angleDifference(a, player.getA());
		if (anglediff < 1) anglediff = 1;
		if (anglediff > 2) anglediff = 2;
		
		double power = 12 / anglediff;
		
		bold.setSpeed(power);
		
		player.setMatchMessage("holds up the ball");
	}

	public Point getTarget() {
		return target;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public ArrayList<Point> getPointsAwayFromOpp() {
		return pointsAwayFromOpp;
	}

	public void setPointsAwayFromOpp(ArrayList<Point> pointsAwayFromOpp) {
		this.pointsAwayFromOpp = pointsAwayFromOpp;
	}
	
	
}
