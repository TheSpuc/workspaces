package data;

public class AgentOpinion {

	/**
	 * Update agent reputations based on how happy the player is with his wages
	 * @param wage (int) the wage of the player
	 * @param wageChange a measure of how much the wage differs from what the player thinks he warrants. The higher the better the player will react
	 * @return the reputation change of the agent
	 */
	public static double calculateAgentReputation(int wage, double wageChange){
			double repChange = wageChange / 2;
			
			return repChange;
	}
	
	/**
	 * Method that calculates end opinion from the amount of change
	 * @param opinion (double)
	 * @param change (double)
	 */
	public static double calcNewOpinion(double opinion, double change){
		double opinionRate = getOpinionRate(opinion);
		if(change > 0){
			while(change > 5){
				opinion += 5 / opinionRate;
				change -= 5;
				opinionRate = PlayerMorale.getMoraleRate(opinion);	
			}
			opinion += change / opinionRate;
			change = 0;
		}
		else if(change < 0){
			while(change < -5){
				opinion += -5 * opinionRate;
				change += 5;
				opinionRate = PlayerMorale.getMoraleRate(opinion);		
			}
			opinion += change * opinionRate;
			change = 0;
		}
		if (opinion > 100) opinion = 100;
		else if(opinion < 1) opinion = 1;
		
		opinion = Math.round(opinion * 100) / 100d;
		
		return opinion;
	}
	
	/**
	 * Method that gets the rate opinion is bought to
	 * @param opinion (double)
	 */
	public static double getOpinionRate(double opinion){
		double opinionRate = 0;
		opinionRate = Math.pow((((double)opinion/100)*2), 2.7);
		if(opinionRate < 0.21) opinionRate = 0.21;
		return opinionRate;
	}
	
}
