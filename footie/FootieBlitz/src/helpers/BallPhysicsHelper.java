package helpers;

import model.Bold;

public abstract class BallPhysicsHelper {


	private final static float luftFaktor = 0.99999f;
	private final static float luftKonstant = 0.00005f;
	private final static float jordFaktor = 0.989f;
	private final static float jordKonstant = 0.072f;
	private final static float hoppeKonstant = -0.65f;
	private final static double gravity = 0.85;
	private final static double maxGravity = -47;

	public static void calculatePhysics(Bold bold){
		setDefaultX(bold);
		setDefaultY(bold);
		setDefaultZ(bold);
		
		if(bold.getZ() > 1){
			calculateSpeedAir(bold);
		}else calculateSpeedGround(bold);
		
		bounce(bold);

		if(bold.getSpeed() < 0) bold.setSpeed(0);
		if(bold.getX() < 0) bold.setX(0);
		if(bold.getY() < 0) bold.setY(0);
		
		calculateAngle(bold);
		
	}

	protected static void setDefaultX(Bold bold){
		bold.setX(bold.getX() + bold.getSpeed() * Math.sin(bold.getA()) * 0.03);
	}

	protected static void setDefaultY(Bold bold){
		bold.setY(bold.getY() + bold.getSpeed() * Math.cos(bold.getA()) * 0.03);
	}

	protected static void setDefaultZ(Bold bold){
		bold.setZ(bold.getZ() + bold.getZForce());
	}

	protected static void calculateSpeedAir(Bold bold){
		int accelerationZ = 1;

		if(bold.getZForce() < 0){
			accelerationZ += bold.getZForce() / -22;
		}

		bold.setZForce(bold.getZForce() - gravity / accelerationZ);
		bold.setSpeed((bold.getSpeed() * luftFaktor) - luftKonstant);
		bold.setCurl(bold.getCurl() * 0.98);
	}

	protected static void calculateSpeedGround(Bold bold){
		bold.setSpeed((bold.getSpeed() * jordFaktor) - jordKonstant);
		bold.setCurl(bold.getCurl() * 0.9);
	}

	protected static void bounce(Bold bold){
		if(bold.getZ() < 0 && bold.getZForce() < 0){
			bold.setZ(0);
			bold.setZForce(bold.getZForce() * hoppeKonstant);
			bold.setSpeed(bold.getSpeed() * 0.7);
			bold.setCurl(bold.getCurl() * 0.5);
		}else if(bold.getZ() <= 4 && bold.getZForce() > 0 && bold.getZForce() < 2){
			bold.setZForce(0);
			bold.setZ(0);
		}
	}
	
	protected static void calculateAngle(Bold bold){
		bold.setA(bold.getA() + (bold.getCurl() * bold.getSpeed()/100));
	}
}
