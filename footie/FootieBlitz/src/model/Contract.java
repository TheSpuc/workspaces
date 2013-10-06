package model;

import java.util.Date;

import data.DAOCake;

public class Contract {

	public int id;
	public int club_id;
	public int person_id;
	public Player player;
	public Team team;
	public int wage;
	public int goalBonus;
	public int assistBonus;
	public int transferFee;
	public int signOnFee;
	public int minRelease;
	public boolean offered;
	public Date dateOffered;
	public Date endDate;
	public Date startDate;
	public int negotiations;
	public int score;
	public boolean acceptedByClub;
	public boolean extensionOffered = false;
	public boolean acceptedByPlayer;
	public String scoreComments = "";

	public Contract() {

	}

	/*
	 * Evaluates a contract and determines whether the player can accept it, if he wants to reject it or 
	 * what needs to be negotiated if he is interested bu the offer is not good enough
	 * 
	 * @param maxFame is the highest club fame among offers the player currently has
	 */
	public void evaluateContract(int maxFame, Contract negotiatedContract, int expectedWage, 
			ContractResponse response, Contract currentContract, int numberOfOffers){

		//If the player is currently at a club with significantly more fame than this offer he will only
		//consider the offer if he's not happy at his current club
		if (currentContract != null && currentContract.team.fame * 0.85 > team.fame){
			if (DAOCake.percentMatchesStarted(person_id, currentContract.team.id, 30) > 40){
				response = ContractResponse.REJECT;
				scoreComments += "I'm happy at my current club and would only consider a move to a big club at this time.<br />";
			}
		}

		//If the player hasn't decided to reject the offer we continue to evaluate it
		if (response != ContractResponse.REJECT){
			
			//If this offer is from the player's current club and there are other offers he will consider the other offers first 
			if (currentContract != null && currentContract.team.id != team.id && numberOfOffers > 1){
				response = ContractResponse.WAIT;
			}
			else{
				//If fame is close to the maximum fame among current offers the player consider the offer
				//If not he will keep them waiting while he tries to get a contract with the more famous team
				if (team.fame / maxFame + 1 > 0.85){

					if (team != null){
						//Basic score is the clubs fame and the wage
						score = team.getFame() + wage * 3;

						//If there's a release clause it's a plus. How big a plus depends on the amount
						if (minRelease > -1)
							score += (wage * 500 / minRelease) * wage;

						if (wage < expectedWage){
							scoreComments += "I expect a wage of at least " + expectedWage + ".<br />";
							negotiatedContract.wage = expectedWage;
						}

						//Contract shorter than two seasons or longer than five seasons are too short or too long
						int daysToEnddate = Math.abs(Hjaelper.daysBetween(endDate, new Date()));
						if (daysToEnddate < 73 * 2){
							scoreComments += "The contract is too short. I would prefer a contract of at least to seasons.<br />";
							negotiatedContract.endDate = Hjaelper.getDateDaysFromNow(73 * 2);
						}
						else if(daysToEnddate > 73 * 5){
							scoreComments += "The contract is too long. I would prefer a contract of no more than five seasons.<br />";
							negotiatedContract.endDate = Hjaelper.getDateDaysFromNow(73 * 5);
						}

						//If there's a change in negotiatedContract it's because the player wants to negotiate
						if (negotiatedContract.wage != wage ||
								negotiatedContract.signOnFee != signOnFee ||
								negotiatedContract.endDate != endDate ||
								negotiatedContract.minRelease != minRelease){
							
							response = ContractResponse.NEGOTIATE;
						}
						else{
							//All offers from the most famous clubs with decent wage etc. can be accepted. 
							//If there are more than one acceptable offer the score will decide which one is accepted
							response = ContractResponse.ACCEPT;
						}
							
					}
					else{ //The fame of the club is not close to the fame of another club who have sent an offer
						//Wait with this one while the player tries to sign with a bigger club
					}
				}
			}
		}
	}

	public enum ContractResponse{
		REJECT, ACCEPT, NEGOTIATE, WAIT
	}
}
