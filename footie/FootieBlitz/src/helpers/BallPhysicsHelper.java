package helpers;

import java.util.Random;

import data.DAOCake;
import model.Bold;
import model.Sysout;
import model.Match.MatchState;

public abstract class BallPhysicsHelper {


	private final static float luftFaktor = 0.99999f;
	private final static float luftKonstant = 0.00005f;
	private final static float jordFaktor = 0.989f;
	private final static float jordKonstant = 0.072f;
	private final static float hoppeKonstant = -0.65f;
	private final static double gravity = 0.85;
	private final static double maxGravity = -47;
	private static Random r = new Random();

	public static void calculatePhysics(Bold bold, Boolean isRealBall){
		if(isRealBall){
//			moveBall(bold);
//			bold.boldBehaviour();
//			moveBall(bold);
//			bold.boldBehaviour();
//			moveBall(bold);
//			bold.boldBehaviour();
//			moveBall(bold);
//			bold.boldBehaviour();
		}
		else{
		setDefaultX(bold);
		setDefaultY(bold);
		setDefaultZ(bold);
		}
		
		if(bold.getZ() > 1){
			calculateSpeedAir(bold);
		}else calculateSpeedGround(bold);
		bounce(bold);

		if(bold.getSpeed() < 0) bold.setSpeed(0);
		if(bold.getX() < 0) bold.setX(0);
		if(bold.getY() < 0) bold.setY(0);	
		
		calculateAngle(bold);
	}

	protected static void moveBall(Bold bold){
		bold.setX(bold.getX() + bold.getSpeed() * Math.sin(bold.getA()) * 0.0075);
		bold.setY(bold.getY() + bold.getSpeed() * Math.cos(bold.getA()) * 0.0075);
		bold.setZ(bold.getZ() / 4);
	}
	
	private static void boldBehaviour(Bold bold){
//		double goalSize = 59;
//		double m�l1StolpeX = bold.getPitch().getPitchWidth() + bold.getPitch().getPitchPosX();
//		double m�l2StolpeX = bold.getPitch().getPitchPosX();
//		double m�lStolpeY1 = ((bold.getPitch().getPitchHeight() - goalSize) / 2 + bold.getPitch().getPitchPosY());
//		double m�lStolpeY2 = ((bold.getPitch().getPitchHeight() - goalSize) / 2 + bold.getPitch().getPitchPosY() + goalSize);
//
//		//Stop bolden f�r den forlader m�lboksen
//		if (bold.isMaal()){
//			if (bold.getX() > m�l1StolpeX + 7 || bold.getX() < m�l2StolpeX - 7){//Er bolden p� vej gennem nettet p� x-aksen
//				bold.setSpeed(0);
//			}
//			if (bold.getY() > m�lStolpeY2 - 1 || bold.getY() < m�lStolpeY1 + 1){
//				bold.setSpeed(bold.getSpeed() * 0.5);
//
//				if (bold.getX() < 200)
//					bold.setA(Math.PI * 1.5);
//				else
//					bold.setA(Math.PI / 2);
//			}
//		}
//
//		if (System.currentTimeMillis() - bold.getBounceTime() > 400){
//			//N�r bolden rammer m�lstolperne
//
//			if (bold.getY() > 302 && bold.getY() < 310)
//				//				Sysout.print("Bold: " + x + ", " + y + ", speed: " + speed);
//
//				//kun ved rigtig hoejde (244 er overligger)
//				if (bold.getZ() < 244){
//					//h�jre m�l
//					if (m�l1StolpeX - bold.getX() <= 3 && m�l1StolpeX - bold.getX() > -1){
//						//					Sysout.print("Stolpe H. boldY: " + y);
//						if (m�lStolpeY1 - bold.getY() >= 0 && m�lStolpeY1 - bold.getY() < 1){
//							//						Sysout.print("1");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 - r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if (m�lStolpeY1 - bold.getY() < 2 && m�lStolpeY1 - bold.getY() >= 1){
//							//						Sysout.print("2");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 + r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBallTimer(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if  (m�lStolpeY2 - bold.getY() <= 0 && m�lStolpeY2 - bold.getY() > -1){
//							//						Sysout.print("3");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 - r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBallTimer(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if (m�lStolpeY2 - bold.getY() <= -1 && m�lStolpeY2 - bold.getY() > -2){
//							//						Sysout.print("4");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 + r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//					}
//					//venstre m�l
//					else if (m�l2StolpeX - bold.getX() >= -3 && m�l2StolpeX - bold.getX() < 1){
//						//					Sysout.print("Stolpe V. boldY: " + y);
//						if (m�lStolpeY1 - bold.getY() >= 0 && m�lStolpeY1 - bold.getY() < 1){
//							//						Sysout.print("v1");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 + r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if (m�lStolpeY1 - bold.getY() < 2 && m�lStolpeY1 - bold.getY() >= 1){
//							//						Sysout.print("v2");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 - r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if  (m�lStolpeY2 - bold.getY() <= 0 && m�lStolpeY2 - bold.getY() > -1){
//							//						Sysout.print("v3");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 - r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//						else if (m�lStolpeY2 - bold.getY() <= -1 && m�lStolpeY2 - bold.getY() > -2){
//							//						Sysout.print("v4");
//							bold.setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 + r.nextInt(300));						
//							bold.setSpeed(bold.getSpeed() * 0.5);
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//						}
//					}
//				}
//			//hvis bolden rammer overliggeren
//				else if (bold.getZ() < 260){
//					if ((m�l1StolpeX - bold.getX() >= -1 && m�l1StolpeX - bold.getX() < 4) || (m�l2StolpeX - bold.getX() >= -1 && m�l2StolpeX - bold.getX() < 4)){
//						bold.setOverligger(true);
//						if (m�lStolpeY1 - bold.getY() < 0 && m�lStolpeY2 - bold.getY() > 0){
//							//hvilken retning p� p� x/y aksen
//							bold.setBounceTime(System.currentTimeMillis());
//							bold.setShot(false);
//							double retningPaaMaal =  Math.PI / 2;
//							double retningFraMaal = Math.PI * 1.5;
//							//Hvis det er venstre maal
//							if (m�l2StolpeX - bold.getX() >= -1 && m�l2StolpeX - bold.getX() < 4){
//								retningPaaMaal =  Math.PI * 1.5;
//								retningFraMaal = Math.PI / 2;
//							}
//							//hvilken retning p� z-aksen, op/ned
//							//						Sysout.print("z = " + z);
//							if (bold.getZ() >= 244 && bold.getZ() < 248){
//								double reboundAngle = ((bold.getZ() - 244.0) / 4.0); 
//								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
//								//							Sysout.print("reboundAngle = " + reboundAngle);
//
//								bold.setZForce(((Math.abs((int)bold.getZForce()) + (bold.getSpeed() / 5.5)) * - reboundAngle));
//								bold.setSpeed(bold.getSpeed() * (1 -reboundAngle));			
//								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
//							}
//							else if (bold.getZ() >= 248 && bold.getZ() < 252){
//								double reboundAngle = 1 - ((bold.getZ() - 248.0) / 4.0); 
//								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
//								//							Sysout.print("reboundAngle = " + reboundAngle);			
//
//								bold.setZForce(((Math.abs((int)bold.getZForce())  + (bold.getSpeed() / 5.5))* -reboundAngle));
//								bold.setSpeed(bold.getSpeed() * (1 -reboundAngle));														
//								bold.setA(retningFraMaal + (retningPaaMaal - bold.getA()));
//								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
//							}
//							else if (bold.getZ() >= 252 && bold.getZ() < 256){
//								double reboundAngle = ((bold.getZ() - 252.0) / 4.0); 
//								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
//								//							Sysout.print("reboundAngle = " + reboundAngle);
//								bold.setZForce(((Math.abs((int)bold.getZForce())  + (bold.getSpeed() / 5.5))* -reboundAngle));
//								bold.setSpeed(bold.getSpeed() * (1 -reboundAngle));														
//								bold.setA(retningFraMaal + (retningPaaMaal - bold.getA()));
//								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
//							}
//							else if (bold.getZ() >= 256 && bold.getZ() < 260){
//								double reboundAngle = 1 - ((bold.getZ() - 256.0) / 4.0); 
//								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
//								//							Sysout.print("reboundAngle = " + reboundAngle);
//
//								bold.setZForce(((Math.abs((int)bold.getZForce())  + (bold.getSpeed() / 5.5))* -reboundAngle));
//								bold.setSpeed(bold.getSpeed() * (1 -reboundAngle));								
//								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
//							}
//						}
//					}
//				}
//		}
//		//Er bolden udenfor
//		if ((bold.getX() < bold.getPitch().getPitchPosX() || bold.getX() > (bold.getPitch().getPitchPosX() + bold.getPitch().getPitchWidth())) &&  
//				(m�lStolpeY2 < bold.getY() || bold.getY() < m�lStolpeY1) 
//				|| (bold.getY() < bold.getPitch().getPitchPosY() || bold.getY() > bold.getPitch().getPitchPosY() + bold.getPitch().getPitchHeight())
//				&& !bold.isMaal()){ // om bolden f�rst har v�ret udenfor banen f�r den ender i m�let
//			bold.setUdenfor(true);	
//			bold.setShot(false);
//			//			//Rammer bolden sidenettet
//			if (bold.getY() < m�lStolpeY2 +3 || bold.getY() > m�lStolpeY1 -3){
//				//				setSpeed(0);
//			}
//		}
//		else{
//			bold.setUdenfor(false);
//		}
//
//		//Er bolden i m�lkassen
//		if (bold.isOverligger()) {
//			//Sysout.print("Check om bolden er i m�l: x = " + x + ", y = " + y + ", z = " + z); 
//			//Sysout.print("m�l1StolpeX = " +  m�l1StolpeX + ", m�lStolpeY1 = " + m�lStolpeY1 +", m�lStolpeY2 = " + m�lStolpeY2);
//		}
//		if ((bold.getX() > m�l1StolpeX && bold.getX() < m�l1StolpeX + 10) || (bold.getX() < m�l2StolpeX && bold.getX() > m�l2StolpeX - 10)){//Er bolden i m�lkassen p� x-aksen
//			if (m�lStolpeY2 > bold.getY() && bold.getY() > m�lStolpeY1 && bold.isUdenfor() == false && bold.isMaal() == false && 
//					(bold.getMatch().getState()==MatchState.ON || bold.getMatch().getState() == MatchState.PENALTYSHOOTOUT)){//Er bolden
//
//				if (bold.getZ() < 245){//hvis bolden er under overliggeren er der maal
//					bold.setMaal(true);
//					bold.setShot(false);
//
//					if (!bold.getMatch().getState().equals(MatchState.PENALTYSHOOTOUT)){
//						if (Math.abs(bold.getX() - bold.getTeamB().getGoalX()) < 100)
//							bold.setTeamAMaal(bold.getTeamAMaal() + 1);
//						else
//							bold.setTeamBMaal(bold.getTeamBMaal() + 1);
//
//						Sysout.print("MAlllalalalalalalal", "bold,boldBehaviour");
//					}
//					else if (bold.getLastShot() != null){
//						Sysout.print("It's in!", "bold,boldBehaviour");
//						DAOCake.updateShootout(bold.getMatch().getMatchId(), bold.getMatch().getShootoutGoalsA(), bold.getMatch().getShootoutGoalsB());
//
//						if (bold.getLastShot().getMyTeam().equals(bold.getTeamA())){
//							bold.getMatch().setShootoutGoalsA(bold.getMatch().getShootoutGoalsA() + 1);
//							bold.getMatch().getShootoutShotsA().remove(bold.getMatch().getShootoutShotsA().size() -1);
//							bold.getMatch().getShootoutShotsA().add(2);
//							
//							bold.getMatch().getEngine().streamInject += "shoutA:";
//							
//							for (Integer i : bold.getMatch().getShootoutShotsA())
//								bold.getMatch().getEngine().streamInject += i;
//							
//							bold.getMatch().getEngine().streamInject +=":";
//						}
//						else{
//							bold.getMatch().setShootoutGoalsB(bold.getMatch().getShootoutGoalsB() + 1);
//							bold.getMatch().getShootoutShotsB().remove(bold.getMatch().getShootoutShotsB().size() -1);
//							bold.getMatch().getShootoutShotsB().add(2);
//
//							bold.getMatch().getEngine().streamInject += "shoutB:";
//							for (Integer i : bold.getMatch().getShootoutShotsB())
//								bold.getMatch().getEngine().streamInject += i;
//							bold.getMatch().getEngine().streamInject +=":";
//						}
//					}
//
//
//				}
//			}
//		}		
	}
	
	protected static void setDefaultX(Bold bold){
		bold.setX(bold.getX() + bold.getSpeed() * Math.sin(bold.getA()) * 0.03);
	}

	protected static void setDefaultY(Bold bold){
		bold.setY(bold.getY() + bold.getSpeed() * Math.cos(bold.getA()) * 0.03);
	}

	protected static void setDefaultZ(Bold bold){
		bold.setZ(bold.getZ() + bold.getZForce() / 256);
	}

	protected static void calculateSpeedAir(Bold bold){
		int accelerationZ = 1;

		if(bold.getZForce() < 0){
			accelerationZ += bold.getZForce() / -22;
		}

		bold.setZForce(bold.getZForce() - gravity / accelerationZ);
		bold.setSpeed((bold.getSpeed() * luftFaktor) - luftKonstant);		
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
		bold.setCurl(bold.getCurl() * 0.98);
	}
}
