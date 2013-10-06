package data;

import model.Player;

public class PositionScoreCalculator {

	/**
	 * Calculates the ability of a player to play different positions
	 * Sets a score of 1-100 on all positions on the player
	 * @param p
	 */
	public void calculatePositions(Player p){
		calculateGoalkeeperScore(p);
		p.setBestPositionScore((int)p.getGoalkeeperScore());
		calculateDefenderScore(p);
		if (p.getBestPositionScore() < p.getDefenderScore()){
			p.setBestPositionScore((int)p.getDefenderScore());
			p.setBestPosition(Position.CB);
		}
		calculateFullBackScore(p);
		if (p.getBestPositionScore() < p.getFullbackScore()){
			p.setBestPositionScore((int)p.getFullbackScore());
			p.setBestPosition(Position.FB);
		}
		calculateMidfielderScore(p);
		if (p.getBestPositionScore() < p.getMidfielderScore()){
			p.setBestPositionScore((int)p.getMidfielderScore());
			p.setBestPosition(Position.CM);
		}
		calculateWingerScore(p);
		if (p.getBestPositionScore() < p.getWingerScore()){
			p.setBestPositionScore((int)p.getWingerScore());
			p.setBestPosition(Position.WG);
		}
		calculateStrikerScore(p);
		if (p.getBestPositionScore() < p.getStrikerScore()){
			p.setBestPositionScore((int)p.getStrikerScore());
			p.setBestPosition(Position.ST);
		}
	}
	
	/**
	 * Calculates how good a player p is as a goalkeeper on a rating of 1-100
	 * and sets the players goalkeeperScore.
	 * @param p
	 */
	public void calculateGoalkeeperScore(Player p){
		double score, firstP, secondP;
		firstP = p.getHandling() + p.getShotstopping() + p.getCommandOfArea() + p.getRushingout() + p.getReaction() + p.getVision();
		firstP /= 6;
		firstP = (firstP / 100) * 70;
		
		secondP = p.getStrength() + p.getAgility() + p.getShotpower() + p.getJumping() + p.getEnergy() + p.getMorale();
		secondP /= 6;
		secondP = (secondP / 100) * 30;
		
		score = firstP + secondP;
		score = (double)Math.round(score * 10) / 10;
		p.setGoalkeeperScore(score);
	}
	
	/**
	 * Calculates how good a player p is as a central defender on a rating of 1-100
	 * and sets the players defenderScore.
	 * @param p
	 */
	public void calculateDefenderScore(Player p){
		double score, firstP, secondP, thirdP;
		firstP = p.getStrength() + p.getTackling() + p.getMarking() + p.getAgility() + p.getReaction() + p.getVision();
		firstP /= 6;
		firstP = (firstP / 100) * 70;
		
		secondP = p.getJumping() + p.getHeading() + p.getAcceleration() + ((p.getTopSpeed() - 96 - 40) / 0.75) + p.getPassing() + p.getTechnique() + p.getStamina() + p.getEnergy() + p.getMorale();
		secondP /= 9;
		secondP = (secondP / 100) * 25;
		
		thirdP = p.getDribbling() + p.getShotpower();
		thirdP /= 2;
		thirdP = (secondP / 100) * 5;
		
		score = firstP + secondP + thirdP;
		score = (double)Math.round(score * 10) / 10;
		p.setDefenderScore(score);
	}
	
	/**
	 * Calculates how good a player p is as a full back on a rating of 1-100
	 * and sets the players fullbackScore.
	 * @param p
	 */
	public void calculateFullBackScore(Player p){
		double score, firstP, secondP, thirdP;
		firstP =  p.getTackling() + p.getMarking() + p.getAgility() + p.getAcceleration() + ((p.getTopSpeed() - 96 - 40) / 0.75) +  p.getReaction() + p.getDribbling();
		firstP /= 7;
		firstP = (firstP / 100) * 75;
		
		secondP = p.getStrength() +  p.getPassing() + p.getTechnique() + p.getStamina() + p.getVision() + p.getShotpower() +  p.getEnergy() + p.getMorale();
		secondP /= 8;
		secondP = (secondP / 100) * 20;
		
		thirdP = p.getJumping() + p.getHeading() + p.getShooting();
		thirdP /= 3;
		thirdP = (secondP / 100) * 5;
		
		score = firstP + secondP + thirdP;
		score = (double)Math.round(score * 10) / 10;
		p.setFullbackScore(score);
	}
	
	/**
	 * Calculates how good a player p is as a midfielder on a rating of 1-100
	 * and sets the players midfielderScore.
	 * @param p
	 */
	public void calculateMidfielderScore(Player p){
		double score, firstP, secondP, thirdP;
		firstP = p.getPassing() + p.getReaction() + p.getVision() + p.getTechnique();
		firstP /= 4;
		firstP = (firstP / 100) * 55;
		
		secondP = p.getShooting() + p.getDribbling() + p.getShotpower() + p.getStrength() + p.getTackling() + p.getMarking() + p.getAgility() + p.getAcceleration() + ((p.getTopSpeed() - 96 - 40) / 0.75) +   p.getStamina() + p.getEnergy() + p.getMorale();
		secondP /= 12;
		secondP = (secondP / 100) * 40;
		
		thirdP = p.getJumping() + p.getHeading();
		thirdP /= 2;
		thirdP = (secondP / 100) * 5;
		
		score = firstP + secondP + thirdP;
		score = (double)Math.round(score * 10) / 10;
		p.setMidfielderScore(score);
	}
	
	/**
	 * Calculates how good a player p is as a winger on a rating of 1-100
	 * and sets the players wingerScore.
	 * @param p
	 */
	public void calculateWingerScore(Player p){
		double score, firstP, secondP, thirdP;
		firstP =   p.getAgility() + p.getDribbling() + p.getTechnique() + p.getAcceleration() + ((p.getTopSpeed() - 96 - 40) / 0.75) + p.getReaction();
		firstP /= 6;
		firstP = (firstP / 100) * 75;
		
		secondP = p.getStrength() + p.getMarking() + p.getPassing() +  p.getStamina() + p.getVision() + p.getShotpower() + p.getShooting() + p.getEnergy() + p.getMorale();
		secondP /= 9;
		secondP = (secondP / 100) * 20;
		
		thirdP = p.getJumping() + p.getHeading() + p.getTackling() ;
		thirdP /= 3;
		thirdP = (secondP / 100) * 5;
		
		score = firstP + secondP + thirdP;
		score = (double)Math.round(score * 10) / 10;
		p.setWingerScore(score);
	}
	
	/**
	 * Calculates how good a player p is as a striker on a rating of 1-100
	 * and sets the players strikerScore.
	 * @param p
	 */
	public void calculateStrikerScore(Player p){
		double score, firstP, secondP, thirdP;
		firstP = p.getShooting() +  p.getShotpower() +  p.getTechnique() + p.getJumping() + p.getHeading() + p.getAcceleration() + ((p.getTopSpeed() - 96 - 40) / 0.75) ;
		firstP /= 7;
		firstP = (firstP / 100) * 70;
		
		secondP = + p.getStrength() + p.getAgility() + p.getDribbling() + p.getReaction() + p.getVision() + p.getStamina() +  p.getEnergy() + p.getMorale();
		secondP /= 8;
		secondP = (secondP / 100) * 25;
		
		thirdP = p.getTackling() + p.getMarking() +  p.getPassing();
		thirdP /= 3;
		thirdP = (secondP / 100) * 5;
		
		score = firstP + secondP + thirdP;
		score = (double)Math.round(score * 10) / 10;
		p.setStrikerScore(score);
	}
	
	public enum Position{
		GK(1),
		CB(2),
		FB(3),
		CM(4),
		WG(5),
		ST(6);
		
		private int value;
		private Position(int value) {
			this.value = value;
		}
		public int getValue() {
			return value;
		}
	}
}
