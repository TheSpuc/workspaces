package model;

import java.awt.Color;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.Random;

import data.DAOCake;

public class Hjaelper {
	
	static ArrayList<String> firstnames = new ArrayList<String>();
	static ArrayList<String> lastnames = new ArrayList<String>();
	static Random r = new Random();
	
	/**
	 * @param ratingsSum (double) collected sum of last ratings (up to 60 matches)
	 * @param ratingsCount (int) nr. of matches collected in ratingsSum
	 * @return an average rating of the last 60 matches (if played less than 60, the rest is set to a rating of 1)
	 */
	public static double calculateAverageMatchRating(double ratingsSum, int ratingsCount){
		if (ratingsCount > 60) ratingsCount = 60;
		else if (ratingsCount < 60)	ratingsSum += 60 - ratingsCount;
		
		double ratingAvg = ratingsSum / 60d;
		
		ratingAvg = (double)Math.floor(ratingAvg * 10) / 10d;
		
		return ratingAvg;
	}
	
	/**
	 * Expected wage is what the statistics say about the players average rating. Statistics are only given for whole numbers - 
	 * the average in between whole numbers is calculated
	 * @param ratingAvg (double) the average rating of the player
	 * @param flooredRating (int) the average rating of the player floored
	 * @param expectedWage (int) the expected wage of a player with the floored rating in the league he is playing
	 * @param nextWageStep  (int) the expected  wage of a player with the floored rating + 1 step up, in the league he is playing
	 * @return the expected wage of the player
	 */
	public static int calcExpectedWage(double ratingAvg, int flooredRating, int expectedWage, int nextWageStep){
		//ExpectedWage is the basic step we're on plus how close we are to the next step (first decimal in ratingAvg)
		int firstDecimal = (int)Math.round(ratingAvg * 10 - flooredRating * 10);
		return expectedWage += (nextWageStep - expectedWage) / 10 * firstDecimal; 		
	}
	
	public static double round(double number, int decimals){
		
		long calc = Math.round(number * Math.pow(10, decimals));
		return (double)calc / Math.pow(10, decimals);
	}
	
	public static double Distance(double x1,double y1,double x2,double y2){
		
		//old: distance = Math.sqrt(Math.pow(Math.abs(x2 - x1),2) + (Math.pow(Math.abs(y2 - y1),2)));
		return Math.sqrt(Math.abs(
	            (x1 - x2) *  (x1 - x2) + 
	            (y1 - y2) *  (y1 - y2))
	        );
	}
	
	public static double angleDifference(double angle1, double angle2){
		
		double result = 0;
		double a1 = angle1, a2 = angle2;

		if (Math.abs(a1 - a2) > Math.PI){
			result = 2 * Math.PI - Math.abs(a1 - a2);
		}
		else{
			result = Math.abs(a1 - a2);
		}
		
		return result;
	}
	
	public static Player firstToBall(ArrayList<Player> teamA, ArrayList<Player> teamB, Bold bold, Settings settings, double boldA, double boldSpeed){
		
		Player result = null;
		int lowestcount = 99999;
		
		for (Player p : teamA){
			//ellers find boldens retning og mød den et sted på dens linie afhængig af dens hastighed
			double tempX = bold.getX();
			double tempY = bold.getY();
			double mytempX = p.getX();
			double mytempY = p.getY();
			double tempSpeed = boldSpeed;
			double tempZ = bold.getZ();
			double tempZForce = bold.getZForce();
			double tempA = bold.getA();
			double dist = 9999;
			double mydist = Hjaelper.Distance(p.getX(), p.getY(), tempX, tempY);
			int count = 0;

			while (count * 30 < p.wait)
				count++;
			
			while (mydist > 8 && count < 1000) {
				dist = Hjaelper.Distance(tempX, tempY, p.getX(), p.getY());
				tempX += tempSpeed * Math.sin(tempA) * 0.03;
				tempY += tempSpeed * Math.cos(tempA) * 0.03;
				tempZ += tempZForce;
				
				if (tempZ > 1){
					int m = 1;

					if (tempZForce < 0)
						m += tempZForce / -22;
					
					tempZForce -= bold.gravity / m;
					tempSpeed *= bold.luftFaktor; //luftens modstand
					tempSpeed -= bold.luftKonstant;
				}
				else{
					//pga banens modstand
					tempSpeed *= bold.jordFaktor;
					tempSpeed -= bold.jordKonstant;
				}

				if (tempZ < 0 && tempZForce < 0){
					tempZ = 0;
					tempZForce *= -0.699; //tag lidt af kraften ud af bolden og vend dens z-retning om
					tempSpeed *= 0.77;//tage lidt af farten pÂ bolden nÂr den rammer jorden
				}
				if (tempZ <= 16 && tempZForce > 0 && tempZForce < 9){
					tempZForce = 0;
					tempZ = 0;
				}

				if (tempX < 0) tempX = 0;
				if (tempY < 0) tempY = 0;
				
				count++;
				if (p.wait < count * 20){
					double dir = Math.atan2((tempX - p.getX()), (tempY - p.getY()));
					mytempX = p.getX() + (((settings.getSpeedmod()) * (50 + p.topSpeed)) * Math.sin(dir) / 40) * count;
					mytempY = p.getY() + (((settings.getSpeedmod()) * (50 + p.topSpeed)) * Math.cos(dir) / 40) * count;
				}
				mydist = Hjaelper.Distance(mytempX, mytempY, tempX, tempY);
			}
			p.setCountToBall(count);
			if (count < lowestcount){
				bold.secondLowestCountToBall = lowestcount;
				lowestcount = count;
				result = p;
			}
			
		}
		for (Player p : teamB){
			//ellers find boldens retning og mød den et sted på dens linie afhængig af dens hastighed
			double tempX = bold.getX();
			double tempY = bold.getY();
			double mytempX = p.getX();
			double mytempY = p.getY();
			double tempZ = bold.getZ();
			double tempZForce = bold.getZForce();
			double tempSpeed = bold.getSpeed();
			double tempA = bold.getA();
			double dist = 9999;
			double mydist = Hjaelper.Distance(p.getX(), p.getY(), tempX, tempY);
			int count = 0;

			while ((mydist > 8 || tempZ > p.getHeight()) && count < 1000) {
				dist = Hjaelper.Distance(tempX, tempY, p.getX(), p.getY());
				tempX += tempSpeed * Math.sin(tempA) * 0.03;
				tempY += tempSpeed * Math.cos(tempA) * 0.03;
				tempZ += tempZForce;
				if (tempZ > 1){
					int m = 1;
					
					if (tempZForce < 0)
						m += tempZForce / -22;
					//gravity = 2
					tempZForce -= 2 / m;
					tempSpeed*=0.993f; //luftens modstand
				}
				else
					tempSpeed*=0.965f;
				
				if (tempZ < 0 && tempZForce < 0){
					tempZ = 0;
					tempZForce *= -0.699; //tag lidt af kraften ud af bolden og vend dens z-retning om
					tempSpeed *= 0.77;//tage lidt af farten pÂ bolden nÂr den rammer jorden
				}
				if (tempZ <= 16 && tempZForce > 0 && tempZForce < 9){
					tempZForce = 0;
					tempZ = 0;
				}
				
				if (tempX < 0) tempX = 0;
				if (tempY < 0) tempY = 0;
				tempSpeed*=0.977f;
				
				count++;
				if (p.wait < count * 20){
					double dir = Math.atan2((tempX - p.getX()), (tempY - p.getY()));
					mytempX = p.getX() + (((settings.getSpeedmod()) * (50 + p.topSpeed)) * Math.sin(dir) / 40) * count;
					mytempY = p.getY() + (((settings.getSpeedmod()) * (50 + p.topSpeed)) * Math.cos(dir) / 40) * count;
				}
				mydist = Hjaelper.Distance(mytempX, mytempY, tempX, tempY);
			}
			p.setCountToBall(count);
			if (count < lowestcount){
				bold.secondLowestCountToBall = lowestcount;
				lowestcount = count;
				result = p;
			}
		}
		
		return result;
	}
	
	public static double distToLine(double px, double py, double lx1, double ly1, double lx2, double ly2){
		
		Line2D ln = new Line2D.Double(lx1, ly1, lx2, ly2);
		return ln.ptLineDist(px, py);
		
//		double lineDir = Math.PI * 2 + Math.atan2(lx2 - lx1, ly2 - ly1);
//		Point distPoint = intersection((int)lx1, (int)ly1, (int)lx2, (int)ly2, (int)(px - Math.sin(lineDir - Math.PI / 2) * 5000), (int)(py - Math.cos(lineDir - Math.PI / 2) * 5000), (int)(px + Math.sin(lineDir - Math.PI / 2) * 5000), (int)(py + Math.cos(lineDir - Math.PI / 2) * 5000));
//		
//		return Distance(distPoint.x, distPoint.y, px, py);
	}
	
	public static Point intersection(int x1,int y1,int x2,int y2, int x3, int y3, int x4,int y4) {
		int d = (x1-x2)*(y3-y4) - (y1-y2)*(x3-x4);
		if (d == 0) return null;

		int xi = ((x3-x4)*(x1*y2-y1*x2)-(x1-x2)*(x3*y4-y3*x4))/d;
		int yi = ((y3-y4)*(x1*y2-y1*x2)-(y1-y2)*(x3*y4-y3*x4))/d;

		return new Point(xi,yi);
	}
	
	public static void loadNames(String file1, String file2){
		
		
		
		try{
			
			FileInputStream fstream = new FileInputStream(file1);

			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in, "unicode"));
			String strLine;
			int antal = 0;
			
			while ((strLine = br.readLine()) != null)   {
				
				String[] q = strLine.split("\t");

					try{
						int l = Integer.parseInt(q[1].trim());
						antal = l;
						
						String[] x = q[2].trim().split(",");  
						for (int j = 0; j < x.length; j ++){
							if (x[j].trim().length() > 1)
//								System.out.println("INSERT INTO names (country_id, type, name, popularity) VALUES (1, 1, '" + x[j].trim().replace("'", "''") + "', " + antal + ")");
								DAOCake.executeSimpleStatement("INSERT INTO names (country_id, type, name, popularity) VALUES (1, 1, '" + x[j].trim().replace("'", "''") + "', " + antal + ");");
						}
							
						for (int b = 0; b < antal; b++){
//							System.out.println(q[2].trim());
							firstnames.add(q[2].trim());
						}
					}
					catch (Exception e) {
						if (q.length > 1) firstnames.add(q[2].trim());
					}
			}
			System.out.println("Antal personer med navne: " + antal);
			in.close();
			
			fstream = new FileInputStream(file2);

			in = new DataInputStream(fstream);
			br = new BufferedReader(new InputStreamReader(in, "unicode"));

			while ((strLine = br.readLine()) != null)   {
				
				String[] q = strLine.split("\t");
				if (q.length > 2){
					try{
						int l = Integer.parseInt(q[1].trim());
						antal = l;

						String[] x = q[2].trim().split(",");  
						for (int j = 0; j < x.length; j ++)	{						
							if (x[j].trim().length() > 1)
//								System.out.println("INSERT INTO names (country_id, type, name, popularity) VALUES (1, 3, '" + x[j].trim().replace("'", "''") + "', " + antal + ")");
								DAOCake.executeSimpleStatement("INSERT INTO names (country_id, type, name, popularity) VALUES (1, 3, '" + x[j].trim().replace("'", "''") + "', " + antal + ")");

							for (int b = 0; b < antal; b++){
//								System.out.println(x[j]);
								lastnames.add(x[j]);
							}
						}
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				
			}
			in.close();
			
		}catch (Exception e){
			System.err.println("Error: " + e.getMessage());
	    }
	}
	
	public static double playerSpeedToBallSpeed(double playerSpeed){
		
		double result = 0;
				
		//Player move = ((settings.getSpeedmod()) * (speed + 7)) * Math.sin(a) / 53;
		//Bold move = speed * Math.sin(a) * 0.0075 * 4;
		result = Settings.getInstance().getSpeedmod() * (playerSpeed + 7) / 53;
		
		result /= 0.03;
		
		return result;
	}
	
	public static String GetRandFirstName(){

		String result = "No names loaded.";
		
		if (firstnames.size() > 0){
			result = firstnames.get(r.nextInt(firstnames.size()));
			while (result.contains("'") || result.length() > 20)
				result = firstnames.get(r.nextInt(firstnames.size()));
		}
		
		return result;
	}
	public static String GetRandLastName(){

		String result = "No names loaded.";
		
		if (lastnames.size() > 0){
			result = lastnames.get(r.nextInt(lastnames.size()));
			while (result.contains("'") || result.length() > 20)
				result = lastnames.get(r.nextInt(lastnames.size()));
		}
		
		return result;
	}
	
	public static Color getNewColor(String colorStr){
		 return new Color(
		            Integer.valueOf( colorStr.substring( 1, 3 ), 16 ),
		            Integer.valueOf( colorStr.substring( 3, 5 ), 16 ),
		            Integer.valueOf( colorStr.substring( 5, 7 ), 16 ) );
	}
	
	/**
	 * Find the best combination of kits.
	 * Team A's first kit is favored above other choices but not guaranteed
	 * @param a1 Team A's firstcolor
	 * @param a2 Team A's secondcolor
	 * @param b1 Team B's firstcolor
	 * @param b2 Team B's secondcolor
	 * @return
	 */
	public static int findKits(Color a1, Color a2, Color b1, Color b2){
		int chosenKits = 0;
		
		//Get the red, green and blue colors of teamA and teamB's firstcolor and secondcolor
		int a1R = a1.getRed();
		int a1G = a1.getGreen();
		int a1B = a1.getBlue();		
		int a2R = a2.getRed();
		int a2G = a2.getGreen();
		int a2B = a2.getBlue();		
		int b1R = b1.getRed();
		int b1G = b1.getGreen();
		int b1B = b1.getBlue();		
		int b2R = b2.getRed();
		int b2G = b2.getGreen();
		int b2B = b2.getBlue();
		
		//The difference between team A's firstcolor and team B's firstcolor
		double diff11R = Math.abs(a1R - b1R);
		double diff11G = Math.abs(a1G - b1G) * 1.5;
		double diff11B = Math.abs(a1B - b1B) * 0.5;
		double result11 = (long)Math.round(100 - (diff11R + diff11G + diff11B) / (765) * 100);
		
		//The difference between team A's firstcolor and team B's secondcolor
		double diff12R = Math.abs(a1R - b2R);
		double diff12G = Math.abs(a1G - b2G) * 1.5;
		double diff12B = Math.abs(a1B - b2B) * 0.5;
		double result12 = (long)Math.round(100 - (diff12R + diff12G + diff12B) / (765) * 100);
		
		//The difference between team A's secondcolor and team B's firstcolor
		double diff21R = Math.abs(a2R - b1R);
		double diff21G = Math.abs(a2G - b1G) * 1.5;
		double diff21B = Math.abs(a2B - b1B) * 0.5;
		double result21 = (long)Math.round(100 - (diff21R + diff21G + diff21B) / (765) * 100);
		
		//The difference between team A's secondcolor and team B's secondcolor
		double diff22R = Math.abs(a2R - b2R);
		double diff22G = Math.abs(a2G - b2G) * 1.5;
		double diff22B = Math.abs(a2B - b2B) * 0.5;
		double result22 = (long)Math.round(100 - (diff22R + diff22G + diff22B) / (765) * 100);
		
		System.out.println("Result 11: " + result11);
		System.out.println("Result 12: " + result12);
		System.out.println("Result 21: " + result21);
		System.out.println("Result 22: " + result22);
		
		//if team A's firstcolor looks a bit like Team B's firstcolor and secondcolor, we should see if there are some better choices
		if(result11 > 65 && result12 > 65){
			result21 += 15; //We add to this var to make it less preferable, because we prefer Team A's first kit
			result22 += 15; //We add to this var to make it less preferable, because we prefer Team A's first kit
			double kits[] = {result11, result12, result21, result22};			
			Arrays.sort(kits);
			
			if(kits[0] == result11){ chosenKits = 11;}	
			else if(kits[0] == result12){ chosenKits = 12;}
			else if(kits[0] == result21){ chosenKits = 21;}
			else if(kits[0] == result22){ chosenKits = 22;}
			
			System.out.println("Team kits chosen " + chosenKits);
			return chosenKits;
		}
		
		double kits[] = {result11, result12};
		Arrays.sort(kits);
		if(kits[0] == result11){ chosenKits = 11;}	
		else if(kits[0] == result12){ chosenKits = 12;}
		else if(kits[0] == result21){ chosenKits = 21;}
		else if(kits[0] == result22){ chosenKits = 22;}
		
		System.out.println("Team kits chosen " + chosenKits);
		return chosenKits;
	}
	
	/** Switches the kits of two teams, if necessary (kit == 11 makes it unnecessary)
	 * Given the choice of kits, make the switches to color1 and color2
	 * @param kits the choice of kits (11 = the first kit of both teams; 12 = the first kit of teamA and the second kit of teamB 
	 * @param teamA
	 * @param teamB
	 */
	public static void switchKits(int kits, Team teamA, Team teamB){
		Color temp;		
		//The case of kit == 11 does not need a switch of kits
		switch(kits){
		case 12:
			System.out.println("TeamB gets second kit");
			temp = teamB.getColor2();
			teamB.setColor2(teamB.getColor());
			teamB.setColor(temp);				
			break;
		
		case(21):
			System.out.println("TeamA gets second kit");
			temp = teamA.getColor2();
			teamA.setColor2(teamA.getColor());
			teamA.setColor(temp);
			break;
		
		case(22):
			System.out.println("Both teams get second kit");
			temp = teamB.getColor2();
			teamB.setColor2(teamB.getColor());
			teamB.setColor(temp);
			temp = teamA.getColor2();
			teamA.setColor2(teamA.getColor());
			teamA.setColor(temp);
			break;
		}
	}

	
	
	
	public static int[] centroid(int[] points) {
	    int[] centroid = { 0, 0 };

	    for (int i = 0; i < points.length; i+=2) {
	    	if (centroid[0] == 0) 
	    		centroid[0] = points[i];
	    	else
	    		centroid[0] += points[i];
	    	
	    	if (centroid[1] == 0) 
	    		centroid[1] = points[i+1];
	    	else
	    		centroid[1] += points[i+1];
	    }

	    int totalPoints = points.length/2;
	    if (totalPoints < 1) totalPoints = 1;
	    
	    centroid[0] = centroid[0] / totalPoints;
	    centroid[1] = centroid[1] / totalPoints;

	    return centroid;
	}

	public static int calcAttendance(int leagueRep, int seats, int terraces, int seatPrice, int standPrice, int homeFame, int awayFame){
		int result = 0;

		//Calculate average ticket price for the league
		int avgTerrPrice = 3 * leagueRep;
		int avgSeatPrice = 5 * leagueRep;

		System.out.println("avgTerrPrice: " + avgTerrPrice);
		System.out.println("avgSeatPrice: " + avgSeatPrice);
		
		//Calculate the percentage of fans who are willing to pay the set price
		int fansStandPerc = avgTerrPrice * 100 / standPrice;
		int fansSeatPerc = avgSeatPrice * 100 / seatPrice;

//		System.out.println("fansStandPerc: " + fansStandPerc);
//		System.out.println("fansSeatPerc: " + fansSeatPerc);
		
		fansStandPerc = (int)(fansStandPerc * 0.01 * fansStandPerc);
		fansSeatPerc = (int)(fansSeatPerc * 0.01 * fansSeatPerc);
		
		System.out.println("fansStandPerc: " + fansStandPerc);
		System.out.println("fansSeatPerc: " + fansSeatPerc);
		
		//////Away fans

		//Calculate max number of places for awayfans and max number of interested awayfans
		int maxAwayFans = (terraces + seats) / 5;
		int avgMatchFame = (awayFame + homeFame) / 2;
		int awayFans = avgMatchFame * 2 / 60; 

		 System.out.println("awayFans: " + awayFans);
		
		//Calculate the amount of awayfans who want seats
		int seatedAwayFans = (int)(awayFans * 0.4);
		seatedAwayFans = seatedAwayFans * fansSeatPerc / 100;
		seatedAwayFans *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

//		System.out.println("seatedAwayFans: " + seatedAwayFans);
		
		if (seatedAwayFans > seats)
			seatedAwayFans = seats;
		
		if (seatedAwayFans > seats * 2)
			seatedAwayFans = seats * 2;

//		System.out.println("seatedAwayFans: " + seatedAwayFans);
		
		//Those who want to stand + half of those who couldn't get a seat will stand if there is enough tickets
		int standingAwayFans = (int)(awayFans * 0.6);
		if ((int)(awayFans * 0.4) - seatedAwayFans > 0) {
			standingAwayFans += ((int)(awayFans * 0.4) - seatedAwayFans) / 2;
		}

//		System.out.println("seatedAwayFans: " + seatedAwayFans);
		
		//How many of those left to stand will pay for it?
		standingAwayFans = standingAwayFans * fansStandPerc / 100;

		if (standingAwayFans > terraces * 2)
			standingAwayFans = terraces * 2;

		if (seatedAwayFans + standingAwayFans > maxAwayFans)
			standingAwayFans = maxAwayFans - seatedAwayFans;

		standingAwayFans *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

		//If there isn't enough stands for the awayfans and there are seats left half of those who couldn't get seats will take stands
		int fansWithoutStands = 0;
		if (standingAwayFans > terraces) {
			fansWithoutStands = (standingAwayFans - terraces) / 2;
			standingAwayFans = terraces;
		}

		if (fansWithoutStands > 0 && seats - seatedAwayFans > 0){
			//The price of a seat might still scare some away
			fansWithoutStands = fansWithoutStands * fansSeatPerc / 100;
			fansWithoutStands *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

			if (fansWithoutStands > seats - seatedAwayFans)
				fansWithoutStands = seats - seatedAwayFans;

			seatedAwayFans = seatedAwayFans + fansWithoutStands;
		}

		System.out.println("seatedAwayFans: " + seatedAwayFans);
		
		seats = seats - seatedAwayFans;
		terraces = terraces - standingAwayFans;

		////////Home fans

		//Calculate max number of places for home fans and max number of interested home fans
		int maxHomeFans = seats + terraces;
		int homeFans = avgMatchFame / 4;

		System.out.println("homeFans: " + homeFans);
		
		//Calculate the amount of home fans who want seats
		int seatedHomeFans = (int)(homeFans * 0.4);
//		System.out.println("seatedHomeFans: " + seatedHomeFans);
		
		seatedHomeFans = seatedHomeFans * fansSeatPerc / 100;
//		System.out.println("seatedHomeFans: " + seatedHomeFans);
		seatedHomeFans *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

//		System.out.println("seatedHomeFans: " + seatedHomeFans);
		
		if (seatedHomeFans > seats)
			seatedHomeFans = seats;
		
		if (seatedHomeFans > seats * 2)
			seatedHomeFans = seats * 2;

//		System.out.println("seatedHomeFans: " + seatedHomeFans);
		
		//Those who want to sit + half of those who couldn't get a seat will stand if there is enough tickets
		int standingHomeFans = (int)(homeFans * 0.6);
		if ((int)(homeFans * 0.4) - seatedHomeFans > 0) {
			standingHomeFans += ((int)(homeFans * 0.4) - seatedHomeFans) / 2;
		}

		//How many of those left to stand will pay for it?
		standingHomeFans = standingHomeFans * fansStandPerc / 100;

		if (standingHomeFans > terraces * 2)
			standingHomeFans = terraces * 2;

		if (seatedHomeFans + standingHomeFans > maxHomeFans)
			standingHomeFans = maxHomeFans - seatedHomeFans;

		standingHomeFans *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

		//If there isn't enough stands for the home fans and there are seats left half of those who couldn't get seats will take stands
		fansWithoutStands = 0;
		if (standingHomeFans > terraces) {
			fansWithoutStands = (standingHomeFans - terraces) / 2;
			standingHomeFans = terraces;
		}

		if (fansWithoutStands > 0 && seats - seatedHomeFans > 0){
			//The price of a seat might still scare some away
			fansWithoutStands = fansWithoutStands * fansSeatPerc / 100;
			fansWithoutStands *= ((10.0 + r.nextFloat() + r.nextFloat()) / 10.0);

			if (fansWithoutStands > seats - seatedHomeFans)
				fansWithoutStands = seats - seatedHomeFans;

			seatedHomeFans = seatedHomeFans + fansWithoutStands;
		}

		int usedSeats = seatedAwayFans + seatedHomeFans;
		int usedTerraces = standingAwayFans + standingHomeFans;

		result = usedSeats + usedTerraces;

		int income = (usedTerraces * standPrice) + (usedSeats * seatPrice) + (result * 3);

		System.out.println("fansSeatPerc: " + fansSeatPerc);
		System.out.println("fansStandPerc: " + fansStandPerc);
		System.out.println("usedSeats: " + usedSeats);
		System.out.println("usedTerraces: " + usedTerraces);
		
		System.out.println("Income: " + income);
		System.out.println("Attendance: " + result);
		return result;
	}
	
	public static Date getDateDaysFromNow(int days){
		Date result = new Date();
		result.setTime(days * 24 * 60 * 60 * 1000); 
		return result;
	}
	
	public static String dateToSQLString(Date date){
		String result = "'" + (date.getYear() + 1900) +"-";
		if (date.getMonth() + 1 < 10) result += "0";
		result += (date.getMonth() + 1) + "-";
		if (date.getDate() < 10) result += "0";
		result += date.getDate() + "'";
		return result;
	}
	
	public static int daysBetween(Date d1, Date d2){
		 return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24));
	}
}
