package model;

import java.awt.Point;

public class ComplexAction {

	Action action;
	Point target;
	Point ballTarget;
	double speed;
	Player player;
	Bold bold;
	Player defender;
	boolean defenderFooled = false;

	double dirToDefenderIn15 = 0;
	double dirLeft = 0;
	double XLeftOfOpp = 0;
	double YLeftOfOpp = 0;
	double dirRight = 0;
	double XRightOfOpp = 0;
	double YRightOfOpp = 0;
	
	public ComplexAction(Action action, Point target, double speed, Player player, Bold bold, Player defender) {
		this.action = action;
		this.target = target;
		this.speed = speed;
		this.player = player;
		this.bold = bold;
		this.defender = defender;
	}

	public void run(){

		switch (action){
		case STEPOVER:
			
			if (Hjaelper.Distance(player.x, player.y, target.x, target.y) > 1){
				double dir = Math.atan2(target.x - player.x, target.y - player.y);
				player.x += Math.sin(dir) * speed;
				player.y += Math.cos(dir) * speed;
				
				Point getBallTo = new Point();
				double boldDir = Math.atan2(ballTarget.x - target.x, ballTarget.y - target.y);
				getBallTo.x = (int)(target.x + Math.sin(boldDir) * 8);
				getBallTo.y = (int)(target.y + Math.cos(boldDir) * 8);
				
				double getBallToDir = Math.atan2(getBallTo.x - bold.x, getBallTo.y - bold.y);
				
				//Move ball to prepare dribble
				if (Hjaelper.Distance(getBallTo.x, getBallTo.y, bold.x, bold.y) > 2){
					bold.x += Math.sin(getBallToDir) * speed * 1;
					bold.y += Math.cos(getBallToDir) * speed * 1;
				}
				
				//Turn to the direction we want to dribble in
				if (player.a < boldDir)
					player.a += 0.04;
				else
					player.a -= 0.04;
				
				//Accelerate out of the feint
				player.speed += player.getCurrentAcceleration(player.speed);
			}
			else{
				player.complexActions.remove(this);
			}
//			if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) > 8){
//				double boldDir = Math.atan2(player.x - bold.x, player.y - bold.y);
//				bold.x += Math.sin(boldDir) * speed * 1.5;
//				bold.y += Math.cos(boldDir) * speed * 1.5;
//			}
			
			break;
		
		case DRIBBLE:
			
			if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < 9){
				player.getStateHasBall().getAiDribble().customDribble(target.x, target.y, bold.getBoldDifPercent(), (int)speed);
				player.complexActions.remove(this);
			}	
			else{
				if (bold.getLastTouch() != null && !bold.getLastTouch().equals(player))
					player.complexActions.remove(this);
				else
					player.complexActions.add(0, new ComplexAction(Action.RUNTO, new Point((int)bold.getX(), (int)bold.getY()), player.getTopSpeed(), player, bold, null));
			}
			
			break;
			
		case RUNTO:
			
			if (Hjaelper.Distance(player.x, player.y, target.x, target.y) < 9){
				player.complexActions.remove(this);
			}
			else{
				player.setTargetX(target.x);
				player.setTargetY(target.y);
				player.setTargetSpeed(speed);
			}
			
			break;
			
		case RUNAROUNDLEFT:
			
			dirToDefenderIn15 = Math.atan2(defender.getXIn(15) - player.getX(), defender.getYIn(15) - player.getY());
			dirLeft = dirToDefenderIn15 - Math.PI / 2;
			XLeftOfOpp = defender.getXIn(15) + 12 * Math.sin(dirLeft);
			YLeftOfOpp = defender.getYIn(15) + 12 * Math.cos(dirLeft);
			dirRight = dirToDefenderIn15 + Math.PI / 2;
			XRightOfOpp = defender.getXIn(15) + 12 * Math.sin(dirRight);
			YRightOfOpp = defender.getYIn(15) + 12 * Math.cos(dirRight);
			
			if (Hjaelper.Distance(player.x, player.y, XLeftOfOpp, YLeftOfOpp) - 4 > Hjaelper.Distance(player.x, player.y, XRightOfOpp, YRightOfOpp)){
				action = Action.RUNAROUNDRIGHT;
			}
			else{
				target.x = (int)XLeftOfOpp;
				target.y = (int)YLeftOfOpp;

				if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < Hjaelper.Distance(defender.x, defender.y, bold.x, bold.y)){
					target.x = (int)bold.x;
					target.y = (int)bold.y;
				}

				if (bold.lastTouch != null && !bold.lastTouch.equals(player) || Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < 9){
					player.complexActions.remove(this);
				}
				else{
					player.setTargetX(target.x);
					player.setTargetY(target.y);
					player.setTargetSpeed(speed);
				}
			}
			break;
			
		case RUNAROUNDRIGHT:
			
			dirToDefenderIn15 = Math.atan2(defender.getXIn(15) - player.getX(), defender.getYIn(15) - player.getY());
			dirRight = dirToDefenderIn15 + Math.PI / 2;
			XRightOfOpp = defender.getXIn(15) + 12 * Math.sin(dirRight);
			YRightOfOpp = defender.getYIn(15) + 12 * Math.cos(dirRight);
			dirLeft = dirToDefenderIn15 - Math.PI / 2;
			XLeftOfOpp = defender.getXIn(15) + 12 * Math.sin(dirLeft);
			YLeftOfOpp = defender.getYIn(15) + 12 * Math.cos(dirLeft);
			
			if (Hjaelper.Distance(player.x, player.y, XLeftOfOpp, YLeftOfOpp) + 4 < Hjaelper.Distance(player.x, player.y, XRightOfOpp, YRightOfOpp)){
				action = Action.RUNAROUNDLEFT;
			}
			else{
				target.x = (int)XRightOfOpp;
				target.y = (int)YRightOfOpp;
				
				if (Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < Hjaelper.Distance(defender.x, defender.y, bold.x, bold.y)){
					target.x = (int)bold.x;
					target.y = (int)bold.y;
				}
				
				if (bold.lastTouch != null && !bold.lastTouch.equals(player) || Hjaelper.Distance(player.x, player.y, bold.x, bold.y) < 9){
					player.complexActions.remove(this);
				}
				else{
					player.setTargetX(target.x);
					player.setTargetY(target.y);
					player.setTargetSpeed(speed);
				}
			}
			
			break;
		}
		
	}

	public enum Action {
		STEPOVER, DRIBBLE, RUNTO, RUNAROUNDLEFT, RUNAROUNDRIGHT
	}

	public Point getBallTarget() {
		return ballTarget;
	}

	public void setBallTarget(Point ballTarget) {
		this.ballTarget = ballTarget;
	}
	
	
}
