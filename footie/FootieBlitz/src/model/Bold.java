package model;

import helpers.BallPhysicsHelper;

import java.awt.Graphics;
import java.awt.Point;
import java.util.Random;

import data.DAOCake;
import model.Match.MatchState;

public class Bold {

	float luftFaktor = 0.99999f;
	float luftKonstant = 0.00005f;
	float jordFaktor = 0.989f;
	float jordKonstant = 0.072f;
	float hoppeKonstant = -0.65f;

	public int secondLowestCountToBall = 0;
	double x, y, z, zForce, maxZForce, minZForce;
	int prevX, prevY;
	final double gravity = 0.85, maxGravity = -47;
	double a, speed = 0;
	double fromX, fromY;
	Player hasBall = null;  
	Player passTo = null, passFrom = null;
	int pitchHeight;
	int pitchWidth;
	int pitchPosX;
	int pitchPosY;
	int shotQuality;
	long bounceTime = 0, ballTimer, setPieceTime; //bouncetime til stolper, balltimer til reaktioner p�
	Team teamHasBall = null;												//�ndringer i boldens bane, setPieceTime til set pieces

	Pitch pitch;
	Random r = new Random();
	boolean udenfor = false, maal = false; // om bolden f�rst har v�ret udenfor banen f�r den ender i m�let
	boolean shot = false, penalty = false, setpieceTaken = true, overligger = false;

	boolean cross = false;
	Graphics backbuffergc;
	int teamAMaal = 0, teamBMaal = 0;
	double curl; 
	Player lastTouch;
	Player lastLastTouch;
	long lastTouchHasHadBallSince, teamHasHadBallSince;
	Player lastShot;
	Match match;
	Settings settings;
	Team teamA;
	Team teamB;
	Player firstToBall;
	Point firstToBallPoint = new Point();
	Player closestPlayer;

	public Bold(){

	}
	
	/**
  * Alternative style for a copy constructor, using a static newInstance
  * method.
  */
  public static Bold makeCopy(Bold originalBold) {
	  Bold copyBold = new Bold();
	  copyBold.a = originalBold.getA();
	  copyBold.x = originalBold.getX();
	  copyBold.y = originalBold.getY();
	  copyBold.speed = originalBold.getSpeed();
	  copyBold.curl = originalBold.getCurl();
	  copyBold.cross = originalBold.cross;
	  copyBold.pitch = originalBold.getPitch();
	  copyBold.match = originalBold.getMatch();
	  copyBold.teamA = originalBold.getTeamA();
	  copyBold.teamB = originalBold.getTeamB();
	  copyBold.teamAMaal = originalBold.getTeamAMaal();
	  copyBold.teamAMaal = originalBold.getTeamBMaal();
	  return copyBold;
  }

	public Bold(int pitchHeight, int pitchPosX, int pitchPosY, int pitchWidth, Pitch pitch, Match match) {
		this.pitchHeight = pitchHeight;
		this.pitchPosX = pitchPosX;
		this.pitchPosY = pitchPosY;
		this.pitchWidth = pitchWidth;
		this.pitch = pitch;
		this.match = match;
		curl = 0;
		lastTouch = null;
	}

	/**
	 * Adjust the direction of a shot based on the curl applied and the target
	 */
	public void adjustAForCurl(Point target){
		double distance = Hjaelper.Distance(getX(), getY(), target.x, target.y);
		double startX = getX();
		double startY = getY();
		Bold checkBall = Bold.makeCopy(this);

		//Check the trajectory of the ball until it's further away than the target
		int whileLimit = 500;
		while (whileLimit > 0 && Hjaelper.Distance(checkBall.getX(), checkBall.getY(), startX, startY) < distance){
			whileLimit--;
			BallPhysicsHelper.calculatePhysics(checkBall, false);
		}

		double angleDifference = Hjaelper.angleDifference(Math.atan2(target.x - getX(), target.y - getY()), 
				Math.atan2(checkBall.getX() - getX(), checkBall.getY() - getY()));

		if (curl < 0)
			setA(Math.atan2(target.x - getX(), target.y - getY()) + angleDifference);
		else
			setA(Math.atan2(target.x - getX(), target.y - getY()) - angleDifference);
	}

	private Player closestPlayer(){
		Player result = teamA.players.get(0);

		for (Player p : teamA.players)
			if (Hjaelper.Distance(p.x, p.y, x, y) < Hjaelper.Distance(result.x, result.y, x, y))
				result = p;

		for (Player p : teamB.players)
			if (Hjaelper.Distance(p.x, p.y, x, y) < Hjaelper.Distance(result.x, result.y, x, y))
				result = p;

		return result;
	}


	public Point meetBallNew(Player p, boolean continueIfOut){
		double mydist = Hjaelper.Distance(p.x, p.y, x, y);
		int count = 0;
		Point result = new Point();

		boolean pointFound = false;
		double arms = 0;
		if (p.isKeeper) arms = p.height / 4;

		Player player = p.cloneForMove();
		Bold tempBall = clone();

		while (count < 300 && !pointFound && (continueIfOut || (!pitch.isOut((int)tempBall.x, (int)tempBall.y)))) {

			count++;
			//Reset the player clone
			player = p.cloneForMove();

			//move the ball
			tempBall.update();

			//Set the player clone's target to where the ball is now
			player.targetX = tempBall.x;
			player.targetY = tempBall.y;

			//Move the player clone the number of times the ball has moved
			for (int i = 0; i < count; i+=5){
				player.targetSpeed = player.topSpeed;
				player.move(false, player.speed, player.a, player.x, player.y);
			}

			if (Hjaelper.Distance(tempBall.x, tempBall.y, player.x, player.y) < 8 && tempBall.z < player.height + arms){
				pointFound = true;
				result = new Point((int)Math.round(tempBall.x), (int)Math.round(tempBall.y));
			}
		}

		p.targetX = result.x;
		p.targetY = result.y;

		return result;
	}

	public Point meetBall(Player p, boolean continueIfOut){
		if (Hjaelper.Distance(p.x, p.y, x, y) < 12 && speed < 25)
			return new Point((int)x,(int)y);

		//ellers find boldens retning og m�d den et sted p� dens linie afh�ngig af dens hastighed
		double boldX = x;
		double boldY = y;
		double playerX = p.x;
		double playerY = p.y;
		double targetX = p.targetX;
		double targetY = p.targetY;
		double targetA = p.targetA;
		double playerSpeed = p.speed;
		double mydist = Hjaelper.Distance(playerX, playerY, boldX, boldY);
		double realDist = mydist;
		int count = 0;
		Bold checkBall = Bold.makeCopy(this);
		Point result = new Point();
		
		boolean pointFound = false;
		boolean ballTrajectoryCrossed = false;
		double arms = 0;
		if (p.isKeeper) arms = p.height / 4;

		while ((mydist > 4 || checkBall.getZ() > p.height - 20 + arms) && count < 1000 && !pointFound && 
				(continueIfOut || (!pitch.isOut((int)checkBall.getX(), (int)checkBall.getY())))) {

				BallPhysicsHelper.calculatePhysics(checkBall, false);
			
			if (ballTrajectoryCrossed && checkBall.getZ() < p.height - 20 + arms){
				//Spilleren har krydset boldens linie og nu er bolden langt nok nede til at han kan n� den s� vi tager det her punkt
				pointFound = true;
			}

			count++;
			playerSpeed = p.speed;
			double dir = Math.atan2((boldX - p.getX()), (boldY - p.getY()));
			double angleDiff = Hjaelper.angleDifference(p.getA(), dir);
			int frames = 0;
			int framesNotAccelerating = 0;

			double agilitymod = settings.getAgilitymod();
			double agilitymod2 = (p.agility+40) * 0.5;


			if (angleDiff > settings.getSharpTurnLimit())
				playerSpeed = 0;

			double tempTopSpeed = p.topSpeed;

			if (angleDiff > 0.2 && angleDiff < settings.getSharpTurnLimit()){
				tempTopSpeed /= (angleDiff * 0.6) + 1;
			}
			double maxTurn = agilitymod * (agilitymod2) * ((p.getTopSpeed() - speed) / p.getTopSpeed());

			if (playerSpeed > tempTopSpeed) playerSpeed = tempTopSpeed;

			while (angleDiff > maxTurn && framesNotAccelerating <= count){
				framesNotAccelerating++;
				angleDiff -= maxTurn;
			}

			while (frames < count){
				frames++;
				if(frames > framesNotAccelerating){
					if (playerSpeed < p.topSpeed * 0.9) playerSpeed += p.getCurrentAcceleration(playerSpeed) * 0.9;
					if (playerSpeed > p.topSpeed * 0.9) playerSpeed = p.topSpeed * 0.9;
					
					if ((int)playerSpeed == (int)(p.topSpeed * 0.9)){
						playerX += (((settings.speedmod) * (playerSpeed + 7)) * Math.sin(dir) / 53.0) * (count - frames);
						playerY += (((settings.speedmod) * (playerSpeed + 7)) * Math.cos(dir) / 53.0) * (count - frames);
						frames = count;
					}
					else{
						playerX += (((settings.speedmod) * (playerSpeed + 7)) * Math.sin(dir) / 53.0);
						playerY += (((settings.speedmod) * (playerSpeed + 7)) * Math.cos(dir) / 53.0);
					}
				}
			}

			if (Hjaelper.Distance(playerX, playerY, p.getX(), p.getY()) > Hjaelper.Distance(boldX, boldY, p.getX(), p.getY())){
				//Spilleren har krydset boldens bane, men bolden m� v�re for h�jt oppe. ballTrajectoryCrossed s�ttes til true s� det n�ste 
				//punkt hvor bolden er langt nok nede v�lges. Se �verst i metoden
				ballTrajectoryCrossed = true;
			}
			mydist = Hjaelper.Distance(playerX, playerY, boldX, boldY);
		}

		if (count == 1000){
			System.out.println("100000000000****");
		}
		double randX = r.nextGaussian() * 4;//lidt tilf�ldighed i hvor man tror bolden lander. Vision g�r det mindre tilf�ldigt
		double randY = r.nextGaussian() * 4;//lidt tilf�ldighed i hvor man tror bolden lander. Vision g�r det mindre tilf�ldigt

		//The speed of the ball and the distance to it has an influence on how easy it is to meet it
		double distance = Hjaelper.Distance(this.x, this.y, p.getX(), p.getY());
		double timeSinceBallChange = System.currentTimeMillis() - ballTimer;
		randX *= (distance / 100.0) * (speed / 100) * (1000.0 / timeSinceBallChange);
		randY *= (distance / 100.0) * (speed / 100) * (1000.0 / timeSinceBallChange);

		//Vision also has an influence
		randX *= 50 / p.vision;
		randY *= 50 / p.vision;

		//		System.out.println("(distance / 100): " + (distance / 100.0));
		//		System.out.println("(speed / 100): " + (speed / 100));
		//		System.out.println("(1000.0 / timeSinceBallChange): " + (1000.0 / timeSinceBallChange));
		//		System.out.println("factor: " + ((distance / 100.0) * (speed / 100) * (1000.0 / timeSinceBallChange)));
		//		System.out.println("randX: " + randX);
		//		System.out.println("randY: " + randY);

		//Error disabled for now to make sure this method works
//		result = new Point((int)boldX + (int)randX, (int)boldY + (int)randY);
		result = new Point((int)boldX, (int)boldY);
		
		p.targetX = targetX;
		p.targetY = targetY;
		p.targetA = targetA;

		return result;
	}

	public Bold clone(){
		Bold result = new Bold();

		result.x = x;
		result.y = y;
		result.z = z;
		result.zForce = zForce;
		result.curl = curl;
		result.speed = speed;
		result.lastTouch = lastTouch;
		result.shot = shot;
		result.cross = cross;

		result.pitch = pitch;

		return result;
	}

	public void update(){
		x += speed * Math.sin(a) * 0.0075;
		y += speed * Math.cos(a) * 0.0075;
		z += zForce / 4;

		boldBehaviour();

		x += speed * Math.sin(a) * 0.0075;
		y += speed * Math.cos(a) * 0.0075;
		z += zForce / 4;

		boldBehaviour();

		x += speed * Math.sin(a) * 0.0075;
		y += speed * Math.cos(a) * 0.0075;
		z += zForce / 4;

		boldBehaviour();

		x += speed * Math.sin(a) * 0.0075;
		y += speed * Math.cos(a) * 0.0075;
		z += zForce / 4;

		boldBehaviour();

		//		if (zForce < 0.5)//her s�nkes boldens hastighed efter hvor stejlt den stiger
		//			speed -= zForce * 0.5;
		//Tyngdekraften trakker ned i bolden hvis den er over jorden
		if (z > 1){
			int m = 1;

			if (zForce < 0)
				m += zForce / -22;

			zForce -= gravity / m;
			speed *= luftFaktor; //luftens modstand
			speed -= luftKonstant;
		}
		else{

			//pga banens modstand
			speed *= jordFaktor;
			speed -= jordKonstant;
			curl *= 0.9;
		}

		//bolden kan hoejst falde med en hvis max hastighed
		//if (zForce < maxGravity) zForce = maxGravity;

		//hvis bolden rammer jorden hopper den op igen
		if (z < 0 && zForce < 0){
			z = 0;
			zForce *= hoppeKonstant; //tag lidt af kraften ud af bolden og vend dens z-retning om
			speed *= 0.7;//tage lidt af farten p� bolden n�r den rammer jorden
			curl *= 0.5;//tag lidt af skrug af n�r bolden rammer jorden
			overligger = false;
		}
		if (z <= 4 && zForce > 0 && zForce < 2){
			zForce = 0;
			z = 0;
		}
		if (speed < 0) speed = 0;	
		if (x < 0) x = 0;
		if (y < 0) y = 0;

		a += curl * speed / 100.0;
		curl *= 0.98;

		if (teamA != null && teamA.getPlayers().size() > 0 && teamB.getPlayers().size() > 0){
			firstToBall = Hjaelper.firstToBall(teamA.getPlayers(), teamB.getPlayers(), this, settings, a, speed);
			firstToBallPoint = meetBall(firstToBall, true);
			closestPlayer = closestPlayer();
		}
		if (z > 230 && !overligger) {
			//			z = 247;
			//			zForce = 0;
		}

		if (zForce < minZForce){
			minZForce = zForce;
		}
		if (zForce > maxZForce) {
			maxZForce = zForce;
		}
		if (speed < 50 && shot) shot = false;
	}

	public boolean hasKeeperGotBall(){
		boolean hasGot = false;

		if(teamA.getKeeper() == getHasBall()) hasGot = true;
		else if(teamB.getKeeper() == getHasBall()) hasGot = true;

		return hasGot;
	}

	public boolean isBallOut(){

		double s1 = ((pitch.pitchHeight - 59) / 2 + pitch.pitchPosY);
		double s2 = ((pitch.pitchHeight - 59) / 2 + pitch.pitchPosY + 59);

		boolean result = false;

		if (x < pitchPosX - 1 || x > pitchPosX + pitchWidth + 1)
			result = true;
		if (y < pitchPosY - 1|| y > pitchPosY + pitchHeight + 1)
			result = true;

		if (y > s1 && y < s2)
			result = false;

		return result;
	}


	public boolean isBallInGoalField(Player p){
		boolean result = false;
		if (p.myTeam.getGoalX() < p.oppTeam.getGoalX()){
			if (x > pitchPosX && x < pitchPosX + 132){
				if (y > (pitchHeight - 322) / 2 + pitchPosY && y < (pitchHeight - 322) / 2 + pitchPosY + 322)
					result = true;
			}
		}
		else{
			if (x > pitchPosX + pitchWidth - 132  && x < pitchPosX + pitchWidth){
				if (y > (pitchHeight - 322) / 2 + pitchPosY && y < (pitchHeight - 322) / 2 + pitchPosY + 322)
					result = true;
			}
		}

		return result;
	}

	public void boldBehaviour(){
		double goalSize = 59;
		double m�l1StolpeX = pitch.pitchWidth + pitch.pitchPosX;
		double m�l2StolpeX = pitch.pitchPosX;
		double m�lStolpeY1 = ((pitch.pitchHeight - goalSize) / 2 + pitch.pitchPosY);
		double m�lStolpeY2 = ((pitch.pitchHeight - goalSize) / 2 + pitch.pitchPosY + goalSize);

		//Stop bolden f�r den forlader m�lboksen
		if (maal){
			if (x > m�l1StolpeX + 7 || x < m�l2StolpeX - 7){//Er bolden p� vej gennem nettet p� x-aksen
				setSpeed(0);
			}
			if (y > m�lStolpeY2 - 1 || y < m�lStolpeY1 + 1){
				setSpeed(speed * 0.5);

				if (x < 200)
					a = Math.PI * 1.5;
				else
					a = Math.PI / 2;
			}
		}

		if (System.currentTimeMillis() - bounceTime > 400){
			//N�r bolden rammer m�lstolperne

			if (y > 302 && y < 310)
				//				Sysout.print("Bold: " + x + ", " + y + ", speed: " + speed);

				//kun ved rigtig hoejde (244 er overligger)
				if (z < 244){
					//h�jre m�l
					if (m�l1StolpeX - x <= 3 && m�l1StolpeX - x > -1){
						//					Sysout.print("Stolpe H. boldY: " + y);
						if (m�lStolpeY1 - y >= 0 && m�lStolpeY1 - y < 1){
							//						Sysout.print("1");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 - r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if (m�lStolpeY1 - y < 2 && m�lStolpeY1 - y >= 1){
							//						Sysout.print("2");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 + r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if  (m�lStolpeY2 - y <= 0 && m�lStolpeY2 - y > -1){
							//						Sysout.print("3");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 - r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if (m�lStolpeY2 - y <= -1 && m�lStolpeY2 - y > -2){
							//						Sysout.print("4");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 + r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
					}
					//venstre m�l
					else if (m�l2StolpeX - x >= -3 && m�l2StolpeX - x < 1){
						//					Sysout.print("Stolpe V. boldY: " + y);
						if (m�lStolpeY1 - y >= 0 && m�lStolpeY1 - y < 1){
							//						Sysout.print("v1");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 + r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if (m�lStolpeY1 - y < 2 && m�lStolpeY1 - y >= 1){
							//						Sysout.print("v2");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY1 - r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if  (m�lStolpeY2 - y <= 0 && m�lStolpeY2 - y > -1){
							//						Sysout.print("v3");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 - r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
						else if (m�lStolpeY2 - y <= -1 && m�lStolpeY2 - y > -2){
							//						Sysout.print("v4");
							setTarget(m�l1StolpeX + 15 - r.nextInt(100), m�lStolpeY2 + r.nextInt(300));						
							setSpeed(getSpeed() * 0.5);
							bounceTime = System.currentTimeMillis();
							shot = false;
						}
					}
				}
			//hvis bolden rammer overliggeren
				else if (z < 260){

					if ((m�l1StolpeX - x >= -1 && m�l1StolpeX - x < 4) || (m�l2StolpeX - x >= -1 && m�l2StolpeX - x < 4)){
						overligger = true;
						if (m�lStolpeY1 - y < 0 && m�lStolpeY2 - y > 0){
							//hvilken retning p� p� x/y aksen
							bounceTime = System.currentTimeMillis();
							shot = false;
							double retningPaaMaal =  Math.PI / 2;
							double retningFraMaal = Math.PI * 1.5;
							//Hvis det er venstre maal
							if (m�l2StolpeX - x >= -1 && m�l2StolpeX - x < 4){
								retningPaaMaal =  Math.PI * 1.5;
								retningFraMaal = Math.PI / 2;
							}
							//hvilken retning p� z-aksen, op/ned
							//						Sysout.print("z = " + z);
							if (z >= 244 && z < 248){
								double reboundAngle = ((z - 244.0) / 4.0); 
								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
								//							Sysout.print("reboundAngle = " + reboundAngle);

								zForce = ((Math.abs((int)zForce) + (speed / 5.5)) * -reboundAngle);
								speed = speed * (1 -reboundAngle);			
								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
							}
							else if (z >= 248 && z < 252){
								double reboundAngle = 1 - ((z - 248.0) / 4.0); 
								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
								//							Sysout.print("reboundAngle = " + reboundAngle);			

								zForce = ((Math.abs((int)zForce)  + (speed / 5.5))* -reboundAngle);
								speed = speed * (1 -reboundAngle);														
								setA(retningFraMaal + (retningPaaMaal - a));
								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
							}
							else if (z >= 252 && z < 256){
								double reboundAngle = ((z - 252.0) / 4.0); 
								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
								//							Sysout.print("reboundAngle = " + reboundAngle);

								zForce = ((Math.abs((int)zForce) + (speed / 5.5)) * reboundAngle);
								speed = speed * (1 -reboundAngle);							
								setA(retningFraMaal + (retningPaaMaal - a));
								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
							}
							else if (z >= 256 && z < 260){
								double reboundAngle = 1 - ((z - 256.0) / 4.0); 
								//							Sysout.print("****rammer overliggeren, z = " + z + ", speed = " + speed + ", zForce = " + zForce);
								//							Sysout.print("reboundAngle = " + reboundAngle);

								zForce = ((Math.abs((int)zForce) + (speed / 5.5)) * reboundAngle);
								speed = speed * (1 -reboundAngle);							
								//							Sysout.print("zForce = " + zForce + ", speed = " + speed);
							}
						}
					}
				}
		}
		//Er bolden udenfor
		if ((x < pitchPosX || x > (pitchPosX + pitchWidth)) &&  
				(m�lStolpeY2 < y || y < m�lStolpeY1) 
				|| (y < pitchPosY || y > pitchPosY + pitchHeight)
				&& !maal){ // om bolden f�rst har v�ret udenfor banen f�r den ender i m�let
			udenfor = true;	
			shot = false;
			//			//Rammer bolden sidenettet
			if (y < m�lStolpeY2 +3 || y > m�lStolpeY1 -3){
				//				setSpeed(0);
			}
		}
		else{
			udenfor = false;
		}

		//Er bolden i m�lkassen
		if (overligger) {
			//Sysout.print("Check om bolden er i m�l: x = " + x + ", y = " + y + ", z = " + z); 
			//Sysout.print("m�l1StolpeX = " +  m�l1StolpeX + ", m�lStolpeY1 = " + m�lStolpeY1 +", m�lStolpeY2 = " + m�lStolpeY2);
		}
		if ((x > m�l1StolpeX && x < m�l1StolpeX + 10) || (x < m�l2StolpeX && x > m�l2StolpeX - 10)){//Er bolden i m�lkassen p� x-aksen
			if (m�lStolpeY2 > y && y > m�lStolpeY1 && udenfor == false && maal == false && 
					(match.getState()==MatchState.ON || match.getState() == MatchState.PENALTYSHOOTOUT)){//Er bolden

				if (z < 245){//hvis bolden er under overliggeren er der maal
					maal = true;
					shot = false;

					if (!match.getState().equals(MatchState.PENALTYSHOOTOUT)){
						if (Math.abs(x - teamB.goalX) < 100)
							teamAMaal++;
						else
							teamBMaal++;

						Sysout.print("MAlllalalalalalalal", "bold,boldBehaviour");
					}
					else if (lastShot != null){
						Sysout.print("It's in!", "bold,boldBehaviour");
						DAOCake.updateShootout(match.getMatchId(), match.getShootoutGoalsA(), match.getShootoutGoalsB());

						if (lastShot.myTeam.equals(teamA)){
							match.shootoutGoalsA++;
							match.getShootoutShotsA().remove(match.getShootoutShotsA().size() - 1);
							match.getShootoutShotsA().add(2);

							match.engine.streamInject += "shoutA:";
							for (Integer i : match.getShootoutShotsA())
								match.engine.streamInject += i;
							match.engine.streamInject +=":";
						}
						else{
							match.shootoutGoalsB++;
							match.getShootoutShotsB().remove(match.getShootoutShotsB().size() - 1);
							match.getShootoutShotsB().add(2);

							match.engine.streamInject += "shoutB:";
							for (Integer i : match.getShootoutShotsB())
								match.engine.streamInject += i;
							match.engine.streamInject +=":";
						}
					}


				}
			}
		}		
	}

	public long getBallTimer() {
		return ballTimer;
	}
	public void setBallTimer(long balltimer) {
		this.ballTimer = balltimer;
	}

	public int getTeamAMaal() {
		return teamAMaal;
	}

	public void setTeamAMaal(int teamAMaal) {
		this.teamAMaal = teamAMaal;
	}

	public int getTeamBMaal() {
		return teamBMaal;
	}

	public void setTeamBMaal(int teamBMaal) {
		this.teamBMaal = teamBMaal;
	}

	public Player getHasBall() {
		return hasBall;
	}

	public double getSpeed(){
		return speed;
	}

	public Player getLastTouch() {
		return lastTouch;
	}

	public Player getLastLastTouch() {
		return lastLastTouch;
	}

	public void setLastTouch(Player lastTouch) {
		if (lastTouch  != null){
			if (this.lastTouch == null){
				teamHasHadBallSince = System.currentTimeMillis();
				this.lastTouch = lastTouch;
				lastTouchHasHadBallSince = System.currentTimeMillis();
			}
			else if (!this.lastTouch.equals(lastTouch)){
				if (!this.lastTouch.myTeam.equals(lastTouch.myTeam)){
					teamHasHadBallSince = System.currentTimeMillis();
				}
				lastLastTouch = this.lastTouch;
				this.lastTouch = lastTouch;
				lastTouchHasHadBallSince = System.currentTimeMillis();
			}
		}
	}

	public void setHasBall(Player hasBall) {
		this.hasBall = hasBall;
		if (hasBall != null) setTeamHasBall(hasBall.myTeam);
	}

	public double getX() {
		return x;
	}

	public void setX(double d) {
		this.x = d;
	}

	public double getFromX() {
		return fromX;
	}

	public double getFromY() {
		return fromY;
	}

	public double getY() {
		return y;
	}
	public void setSpeed(double speed){
		if (speed < 0)
			speed = 0;
		ballTimer = System.currentTimeMillis();
		this.speed = speed;
		fromX = x;
		fromY = y;
		passTo = null;
	}

	public void setTarget(double x, double y){
		a = Math.atan2((x - this.x), (y - this.y));
	}

	public Player getLastShot() {
		return lastShot;
	}

	public void setLastShot(Player lastShot) {
		this.lastShot = lastShot;
	}

	public double getCurl() {
		return curl;
	}

	public void setCurl(double curl) {
		this.curl = curl;
	}

	public void setY(double d) {
		this.y = d;
	}

	public boolean isUdenfor(){
		return udenfor;
	}

	public void setUdenfor(boolean udenfor) {
		this.udenfor = udenfor;
	}

	public double getA() {
		return a;
	}

	public void setA(double a) {
		ballTimer = System.currentTimeMillis();

		if (a != this.a && teamA != null && teamB != null){
			for (Player p : teamA.getPlayers()){
				if (Hjaelper.Distance(x, y, p.x, p.y) < 60)
					p.playerMatchStats.ballDirChanges++;	
			}
			for (Player p : teamB.getPlayers()){
				if (Hjaelper.Distance(x, y, p.x, p.y) < 60)
					p.playerMatchStats.ballDirChanges++;	
			}
		}

		this.a = a;
	}

	public Player getPassTo() {
		return passTo;
	}

	public void setPassTo(Player passTo) {
		this.passTo = passTo;
	}

	public boolean isMaal() {
		return maal;
	}

	public void setMaal(boolean maal) {
		this.maal = maal;
	}

	public boolean isShot() {
		return shot;
	}

	public void setShot(boolean shot) {
		this.shot = shot;
	}
	public int getShotQuality() {
		return shotQuality;
	}
	public void setShotQuality(int shotQuality) {
		this.shotQuality = shotQuality;
	}
	public double getZ() {
		return z;
	}
	public void setZ(double z) {
		this.z = z;
	}
	public double getZForce() {
		return zForce;
	}
	public void setZForce(double force) {
		zForce = force;
	}
	public long getSetPieceTime() {
		return setPieceTime;
	}
	public void setSetPieceTime(long setPieceTime) {
		this.setPieceTime = setPieceTime;
	}
	public Settings getSettings() {
		return settings;
	}
	public void setSettings(Settings settings) {
		this.settings = settings;
	}
	public Team getTeamA() {
		return teamA;
	}
	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}
	public Team getTeamB() {
		return teamB;
	}
	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}
	public Player getFirstToBall() {
		return firstToBall;
	}
	public void setFirstToBall(Player firstToBall) {
		this.firstToBall = firstToBall;
	}
	public boolean isCross() {
		return cross;
	}
	public void setCross(boolean cross) {
		this.cross = cross;
	}
	public boolean isSetpieceTaken() {
		return setpieceTaken;
	}
	public void setSetpieceTaken(boolean setpieceTaken) {
		this.setpieceTaken = setpieceTaken;
	}
	public int getBoldDifOld(){
		//Speed max: 236
		//zforce max: 40
		//zforce min -33
		int result = (int)(speed + Math.abs(zForce) + z / 1.7);
		if (result < 1) result = 1; 
		return result;
	}

	/*
	 * Returns an int from 1-100 representing how difficult it is to execute a shot, pass etc. based on how long 
	 * the player who last touched the ball has had on the ball
	 * 
	 * Make sure this is called after setLastTouch.
	 * 
	 * 1 means the players has had the ball enough time for it not to factor in at all.
	 * 100 means this is a first touch shot/pass etc.
	 */
	public int getTimeOnBallDiff(){

		//If it's a set piece time on ball means nothing
		int result = 1;

		//If it's not a set piece calculate the result
		if (match.getState().equals(MatchState.ON)){
			//number of milliseconds since the player first touched the ball
			result = (int)(System.currentTimeMillis() - lastTouchHasHadBallSince);

			//Three seconds should be enough for any player. We divide by 30 to get the percentage from 0-3 seconds.
			//More than 100 means more than three seconds have passed.
			result /= 30;

			if (result > 100) result = 100;
			if (result < 1) result = 1;

			//100 means the player has had the ball for three seconds so that's the easiest and should be 1
			result = 101 - result;
		}

		return result;
	}

	/**
	 * 
	 * @return the time in ms the ball has been with the team currently in possession
	 */
	public int getTeamTimeOnBall(){
		if (teamHasHadBallSince == 0) 
			return 0;
		else 
			return (int)(System.currentTimeMillis() - teamHasHadBallSince);	
	}

	/*
	 * Returns an int from 1-100 representing how difficult the ball is to strike or control with the feet
	 * The result is determined by the horizontal speed of the ball, the height of the ball and the vertical speed of the ball
	 * At the moment these three factors weigh in equally. Maybe that should be adjusted?
	 */
	public int getBoldDifPercent(){
		//Speed max: 236
		//zforce max: 40
		//zforce min -33
		//z is cm above ground

		//get each factor from 1-100, add them and return the total divided by 3
		int ballSpeed = (int)(speed / 2.36);
		int ballZForce = (int)(Math.abs(zForce) * 2.5);
		int ballZ = (int)(z / 1.7);

		int result = (ballSpeed + ballZForce + ballZ) / 3;

		//Just to make sure...
		if (result < 1) result = 1; 
		if (result > 100) result = 100;

		return result;
	}

	public int getPrevX() {
		return prevX;
	}

	public void setPrevX(int prevX) {
		this.prevX = prevX;
	}

	public int getPrevY() {
		return prevY;
	}

	public void setPrevY(int prevY) {
		this.prevY = prevY;
	}

	public Player getPassFrom() {
		return passFrom;
	}

	public void setPassFrom(Player passFrom) {
		this.passFrom = passFrom;
	}

	public Pitch getPitch() {
		return pitch;
	}

	public void setPitch(Pitch pitch) {
		this.pitch = pitch;
	}

	public boolean isPenalty() {
		return penalty;
	}

	public void setPenalty(boolean penalty) {
		this.penalty = penalty;
	}

	/**
	 * Gets the team that has the ball (team is decided in setHasBall)
	 * @return
	 */
	public Team getTeamHasBall(){
		return teamHasBall;
	}

	/**
	 * sets the team that that has the ball
	 * @param teamHasBall
	 */
	public void setTeamHasBall(Team teamHasBall){
		this.teamHasBall = teamHasBall;
	}

	public long getBounceTime() {
		return bounceTime;
	}

	public void setBounceTime(long bounceTime) {
		this.bounceTime = bounceTime;
	}

	public boolean isOverligger() {
		return overligger;
	}

	public void setOverligger(boolean overligger) {
		this.overligger = overligger;
	}

	public Match getMatch() {
		return match;
	}

	public void setMatch(Match match) {
		this.match = match;
	}
	
	
	
	
}

