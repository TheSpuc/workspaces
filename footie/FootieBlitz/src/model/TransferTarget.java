package model;

import java.util.Random;

import data.PositionScoreCalculator.Position;

public class TransferTarget {

	public int personId;
	public int userId;
	public String lastname;
	public int bestPositionSkill;
	public Position bestPosition;
	public int avgSkill;
	public int avgMatchRating;
	public int value;
	public int age;
	public int listedPrice;
	public boolean hasClub;
	public int expectedWage;
	public int price;
	public double targetScore;
	
	/*
	 * Calculates a score determining how good a signing this target would be and stores it in targetScore
	 * @Return targetScore
	 */
	public double calcScoreAndPrice(int maxTransfer, int maxWage){
		
		Random r = new Random();
		
		//The players attributes are the most important
		targetScore = bestPositionSkill;
		
		//How much the transfer will cost means something too (max 3)
		price = value;
		if (!hasClub)
			price = 0;
		else if (listedPrice > -1 && listedPrice < price) {
			price = listedPrice;
		}
		
		//Throw in a little random
		price = (int)(price * (0.95 + r.nextDouble() * 0.15));
		
		int adjustForPrice = (maxTransfer / (price+1));
		if (adjustForPrice > 3) adjustForPrice = 3;
		
		targetScore += adjustForPrice;
		
		//Wages means something too (max 3)
		
		//Throw in a little random
		expectedWage = (int)(expectedWage * (0.95 + r.nextDouble() * 0.15));
		
		int adjustForWage = (maxWage / (expectedWage+1));
		if (adjustForWage > 3) adjustForWage = 3;
		
		targetScore += adjustForWage;
		
		//Players older than 29 could be a risk and players younger than 23 will certainly improve
		//max: 3, no minimum value (age of 40 should deduct a lot from score)
		int adjustForAge = 0;
		if (age > 29)
			adjustForAge = 29 - age;
		else if (age < 23)
			adjustForAge = 23 - age;
		
		if (adjustForAge > 3) adjustForAge = 3;
		
		targetScore += adjustForAge;
		
		//Throw in a little random to make sure clubs rate players differently
		targetScore *= (1 + (r.nextDouble() * 0.2) - (r.nextDouble() * 0.2));
		
		//potential could count for something too, but not for now
		
		
		return targetScore;
	}
}
