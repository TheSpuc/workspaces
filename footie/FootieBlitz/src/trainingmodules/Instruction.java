package trainingmodules;

import java.awt.Point;

import model.Player;

public class Instruction {

	private Point target;
	private String action = "";
	private Player targetPlayer = null;
	
	public Instruction(Point target, Player targetPlayer, String action) {
		super();
		this.target = target;
		this.targetPlayer = targetPlayer;
		this.action = action;
	}

	public Point getTarget() {
		return target;
	}

	public void setTarget(Point target) {
		this.target = target;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	public Player getTargetPlayer() {
		return targetPlayer;
	}
}
