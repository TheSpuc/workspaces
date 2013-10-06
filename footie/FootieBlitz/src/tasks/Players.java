package tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import model.Contract;
import model.Country;
import model.Hjaelper;
import model.Player;
import model.PlayerTrainingInfo;
import model.Contract.ContractResponse;
import model.Team;

import data.ClubsDAO;
import data.DAOCake;
import data.PositionScoreCalculator;
import data.PositionScoreCalculator.Position;
import data.teamSetup.SetupPositions;

public class Players {

	/*
	 * Update players position rating, value, reputation etc.
	 */
	public static void UpdatePlayerRatings(){
		PositionScoreCalculator posCalc = new PositionScoreCalculator();

		ArrayList<Integer> playerIDs = DAOCake.getAllPlayerIDs();
		
		try {
			for (Integer id : playerIDs){
				Player p = DAOCake.loadPlayer(id);
				
				posCalc.calculatePositions(p);
				double avgRating = DAOCake.getPlayerAvgRating(p.getId(), false);
				double avgRatingNorm = DAOCake.getPlayerAvgRating(p.getId(), true);

				int ratingScore = (int)(avgRating * 10);
				int positionScore = (int)Math.round(p.getGoalkeeperScore());
				PositionScoreCalculator.Position bestPosition = Position.GK;
				
				if ((int)Math.round(p.getDefenderScore()) > positionScore){
					positionScore = (int)Math.round(p.getDefenderScore());
					bestPosition = Position.CB;
				}

				if ((int)Math.round(p.getFullbackScore()) > positionScore){
					positionScore = (int)Math.round(p.getFullbackScore());
					bestPosition = Position.FB;
				}

				if ((int)Math.round(p.getMidfielderScore()) > positionScore){
					positionScore = (int)Math.round(p.getMidfielderScore());
					bestPosition = Position.CM;
				}

				if ((int)Math.round(p.getWingerScore()) > positionScore){
					positionScore = (int)Math.round(p.getWingerScore());
					bestPosition = Position.WG;
				}

				if ((int)Math.round(p.getStrikerScore()) > positionScore){
					positionScore = (int)Math.round(p.getStrikerScore());
					bestPosition = Position.ST;
				}

				int overAllScore = (ratingScore * 2 + positionScore) / 3;
				int avgPositionRating = (int)p.getGoalkeeperScore();
				if (bestPosition != Position.GK){
					avgPositionRating = (int)((p.getDefenderScore() + 
							p.getFullbackScore() + 
							p.getMidfielderScore() + 
							p.getWingerScore() + 
							p.getStrikerScore()) / 5);
				}
				
				int avgTopPrice = DAOCake.getAvgTopTransfers();

				int playerValue = overAllScore  * avgTopPrice / 100;
				playerValue = (int)Math.round(playerValue / 1000.0) * 1000;

				DAOCake.UpdateOrCreatePlayerRating(p, positionScore, playerValue, bestPosition.getValue(), avgPositionRating, avgRating, avgRatingNorm);
			}
			
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Player ratings updated");
	}
	
	/*
	 * Players react to contract offers
	 */
	public static void ReactToContracts(){

		//Get all players with offers waiting
		ArrayList<Player> players = DAOCake.getPlayersWithContractOffers(false);

		for (Player player : players){
			//Get the offers and club info the player has
			ArrayList<Contract> offers = DAOCake.getContractOffersForPlayer(player.getId());
			
			//The player wont take a decision until he's had at least two days to think about the first offer
			int daysSinceFirstOffer = 0;
			for (Contract offer : offers){
				if (Hjaelper.daysBetween(new Date(), offer.dateOffered) > daysSinceFirstOffer)
					daysSinceFirstOffer = Hjaelper.daysBetween(new Date(), offer.dateOffered);
			}
			
			if (daysSinceFirstOffer > 1){

				//Get the player's current contract
				Contract currentContract = DAOCake.getPlayerContract(player.getId());
				
				//How many matches has the player started out of the last 30 at his current club
				int matchesStarted = 0;
				//How many matches has his current club played while he's been at the club
				int possibleStarts = 0;
				
				//The message the player will send with the response to the offer
				String message = "";

				//The response to the contract. 1=accept, 2=reject, 3=negotiate
				Contract.ContractResponse response = Contract.ContractResponse.WAIT;
				
				if (currentContract == null){
					//The player is not contracted to a club. He will evaluate all offers and if the best one is good enough
					//he will accept it. If not he will renegotiate all offers from clubs he's interested in playing for.
					//The longer the player has been without contract the less he will expect.
					
				}
				else{
					//If the current contract was signed less than half a season ago the player wont consider a new contract
					//unless it's a payraise
					if (Math.abs(Hjaelper.daysBetween(new Date(), currentContract.startDate)) < 73 / 2){
						for (Contract offer : offers){
							
							Team offeringTeam = ClubsDAO.loadTeam(offer.club_id, null, false, false);
							Contract negotiatedContract = new Contract();
							negotiatedContract.id = offer.id;
							negotiatedContract.wage = offer.wage;
							negotiatedContract.signOnFee = offer.signOnFee;
							negotiatedContract.endDate = offer.endDate;
							negotiatedContract.minRelease = offer.minRelease;
							
							if (currentContract.club_id == offer.club_id){ //This offer is from the players current club
								
								if (currentContract.wage * 1.1 <= offer.wage){
									
									//There's no pay raise here. The player won't consider this offer because he signed a contract recently
									message = player.getFirstname() + " " + player.getLastname() + " has rejected your contract offer " + 
											" adding the following message: <br /><br />"+
											"My current contract has only just started and i would only consider a new one if it carries a decent pay raise.";

									response = ContractResponse.REJECT;
								}
								else if(currentContract.minRelease > -1 && currentContract.minRelease < offer.minRelease){
									//There's a pay raise but the minimum release clause has been raised so the player wont sign
									//so quickly after signing the last one
									message = player.getFirstname() + " " + player.getLastname() + " has sent a negotiated contract offer " + 
											" adding the following message: <br /><br />"+
											"I appreciate the pay raise, but i'm not willing to raise the minimum release clause at this time.";

									negotiatedContract.minRelease = currentContract.minRelease;
									response = ContractResponse.NEGOTIATE;
								}
								else{
									//This is a pay raise with no raise in release clause so the player will take the pay raise
									//if it matches what he deserves

									double avgRating = DAOCake.getPlayerAvgRating(player.getId(), true);
									int expectedWage = DAOCake.getPlayerAvgWage(avgRating, offeringTeam.leagueId);
									
									//He should probably consider the length of the contract here unless he's very happy at the club

									if (offer.wage >= expectedWage){
										message = player.getFirstname() + " " + player.getLastname() + " has accepted your contract offer " + 
												" adding the following message: <br /><br />"+
												"I appreciate the pay raise and have signed the new contract.";

										response = ContractResponse.ACCEPT;
									}
									else{
										message = player.getFirstname() + " " + player.getLastname() + " has sent a negotiated contract offer " + 
												" adding the following message: <br /><br />"+
												"I appreciate the pay raise but my perfomances merit a higher wage. I expect at least " + expectedWage;

										response = ContractResponse.NEGOTIATE;
										negotiatedContract.wage = expectedWage;
									}
								}
							}
							else{ //Offer not from his current club
								
								//The player signed with his current club recently and doesn't want to move
								message = player.getFirstname() + " " + player.getLastname() + " has rejected your contract offer " + 
										" adding the following message: <br /><br />"+
										"My current contract has only just started and i won't consider moving to a new club at this time.";

								response = ContractResponse.REJECT;
							}
							
							switch (response){
							
								case ACCEPT:
									DAOCake.respondToContract(true, offer.id, message, offer.team.ownerId);
								break;
								
								case REJECT:
									DAOCake.respondToContract(false, offer.id, message, offer.team.ownerId);
								break;
								
								case NEGOTIATE:
									DAOCake.playerNegotiateContract(negotiatedContract, message, offer.team.ownerId);
								break;
							}
						}
					}
					else{ //current contract was signed more than half a season ago
						
						//Check club fame on offers
						//If the player is too good for a club - reject the offer immediately
						//If the player's current club is better than the new one the player only considers the offer if the wage is considerably better or he's not playing enough at his current club
						//All offers not rejected are evaluated based on wage, length, release clause, sign on fee
						//If one of the offers from the club with the highest fame or the rest with almost as much fame is good enough he'll take the best one of them
						//Offers not god enough are negotiated until the number of negotiations becomes too high
						//The more offers a player has the less times he'll negotiate an offer that isn't the best one

						double avgRating = DAOCake.getPlayerAvgRating(player.getId(), true);
						
						//The highest club fame in current offers
						int maxFame = 0;
						for (Contract offer : offers)
							if (offer.team.getFame() > maxFame) maxFame = offer.team.getFame();
						
						
						for (Contract offer : offers){
							
							Team offeringTeam = ClubsDAO.loadTeam(offer.club_id, null, false, false);
							int expectedWage = DAOCake.getPlayerAvgWage(avgRating, offeringTeam.leagueId );
							
							Contract negotiatedContract = new Contract();
							negotiatedContract.id = offer.id;
							negotiatedContract.wage = offer.wage;
							negotiatedContract.signOnFee = offer.signOnFee;
							negotiatedContract.endDate = offer.endDate;
							negotiatedContract.minRelease = offer.minRelease;
							
							offer.evaluateContract(maxFame, negotiatedContract, expectedWage, response, currentContract, offers.size());
							
							switch (response){
							
							case ACCEPT:
								DAOCake.respondToContract(true, offer.id, message, offer.team.ownerId);
							break;
							
							case REJECT:
								DAOCake.respondToContract(false, offer.id, message, offer.team.ownerId);
							break;
							
							case NEGOTIATE:
//								DAOCake.playerNegotiateContract();
							break;
							}
						}
					}
					
				}
				
			}
		}
	}

	/*
	 * Randomly retires players who haven't had a contract in 3 seasons
	 */
	public static void retirePlayersWithoutContractIn3Seasons(){
		
		ArrayList<Integer> players = DAOCake.getPlayersWithoutContractIn3Seasons();
		Random r = new Random();
		
		for (Integer i : players){
			if (r.nextDouble() > 0.9){
				
			}
		}
	}
	
	public static void addPlayerPoints(){
		int id = 0;

		try {

			ArrayList<PlayerTrainingInfo> trainingInfo = DAOCake.getPlayerTrainingInfo(); 

			double pp = 0;
			Random ran = new Random();

			for (PlayerTrainingInfo info :  trainingInfo){

				pp = 0;

				//Der er flere trin vi skal gennem for at finde det antal PP spilleren skal have
				//1: NATURLIG UDVIKLING (SPILLERENS POTENTIALE) 
				pp = info.basicPP;

				//hvis pp er negativ, skal vi have en algoritme der selv skærer ned på spillerens egenskaber
				//2:FJERN ATTRIBUTE POINTS FRA SPILLEREN HVIS HANS NATURLIGE UDVIKLING ER I MINUS
				//removePPFromNaturalDevelopment(pp);
				if (pp < 0){
					DAOCake.removePPFromNaturalDevelopment(pp, info.player);
					pp = 0; //Når vi har taget de PP fra atributterne som vi skal, så skal de ikke tages igen.
				}
				
				//3: TRÆNINGSFACILITETER 

				int r;
				double ppFacc = 0;

				if (info.totalPPFromFacc < Player.maxCareerPPFromFacc && 
						info.totalPPFromFacc + info.totalPPFromXP < Player.maxCareerPPFromFaccAndXP){

					//level 1:
					if (info.trainingFacilities  == 1){
						r = ran.nextInt(4) +1;
						ppFacc = (double)r / 10.0; 
					}				
					//level 2:
					if (info.trainingFacilities == 2){
						r = ran.nextInt(5) +1;
						ppFacc = (double)r / 10.0; 
					}				
					//level 3:
					if (info.trainingFacilities == 3){
						r = ran.nextInt(6) +1;
						ppFacc = (double)r / 10.0; 
					}
					//level 4:
					if (info.trainingFacilities == 4){
						r = ran.nextInt(7) +1;
						if (r>6) r = 6;
						ppFacc = (double)r / 10.0; 
					}
					//level 5:
					if (info.trainingFacilities == 5){
						r = ran.nextInt(8) +1;
						if (r>6) r = 6;
						ppFacc = (double)r / 10.0; 
					}
					//level 6:
					if (info.trainingFacilities == 6){
						r = ran.nextInt(9) +1;
						if (r>6) r = 6;
						ppFacc = (double)r / 10.0; 
					}
					//level 7:
					if (info.trainingFacilities == 7){
						r = ran.nextInt(10) +1;
						if (r>6) r = 6;
						ppFacc = (double)r / 10.0;
					}
					//level 8:
					if (info.trainingFacilities == 8){
						r = ran.nextInt(10) +1;
						if (r>7) r = 7;
						ppFacc = (double)r / 10.0;
					}
					//level 9:
					if (info.trainingFacilities == 9){
						r = ran.nextInt(11) +1;
						if (r>7) r = 7;
						ppFacc = (double)r / 10.0; 
					}
					//level 10:
					if (info.trainingFacilities == 10){
						r = ran.nextInt(12) +1;
						if (r>7) r = 7;
						ppFacc = (double)r / 10.0; 
					}
					//				r = ran.nextInt(3)+1;//var først ment som en bonus til dem der ikke spillede kampe. men nu blot en bonus til alle
					//				ppFacc += (double)r / 15.0; 

					pp += ppFacc;
				}

				//4: Training boost....todo (Enkelte spillere vælges ud til at få et træningsboost hvor de får markant flere pp hver dag i et tidsinterval)


				//5: OPDATER SPILLEREN med de nye pp
				if (pp < 0){

				}
				else{
					pp = Hjaelper.round(pp, 2);
					DAOCake.addToPlayerpoints(info.personId, pp, ppFacc);
				}

			}
			System.out.println("PP added");

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Last id: " + id);
		}
	}

	public static void generateNewPlayers(){

		for (Country country : DAOCake.getAllCountries(true)){

			System.out.println("Generating players for " + country.name);
			System.out.println("avgActivePlayers: " + country.avgActivePlayers);
			System.out.println("currentActivePlayers: " + country.currentActivePlayers);

			//percentOverAverageLimit is the limit to how many active players a country can have. A value of 10 means we won't generate 
			//any new players if there's 10 percent more active players than average.
			double percentOverAverageLimit = 10;

			//Check the country's current number of active players against what it should be and decide how many new players to generate
			//if playerFactor is > 1 we have less active players than average and vice versa
			double playerFactor = 0;
			if (country.currentActivePlayers == 0) country.currentActivePlayers = 1;
			
			playerFactor = (double)country.avgActivePlayers / (double)country.currentActivePlayers;

			System.out.println("playerFactor: " + playerFactor);

			if (playerFactor > 1.0 - percentOverAverageLimit / 100.0){

				//the maximum number of new players is 1% of the average number but at least 1
				int maxNewPlayers = country.avgActivePlayers / 200;
				if (maxNewPlayers < 1) maxNewPlayers = 1;

				Random r = new Random();

				for (int i = 0; i < maxNewPlayers; i++){

					//The chance of a new player is dependant on playerFactor
					if (r.nextDouble() < playerFactor - 1 + percentOverAverageLimit / 100.0){
						int id = createNewPlayer(country.ID);
						System.out.println("New player generated: " + id);
					}
				}
			}
		}
	}

	public static void growPlayers(){

		try {
			ArrayList<Player> players = DAOCake.getGrowingPlayers();
			Random r = new Random();

			for (Player p : players){
				//Players grow based on age and how far they are from their final height
				int chanceOfGrowth = 0;

				//The chance of growing is bigger the older the player is (they start at 14 and should be finished growing around 18 so the further from 14 their age is the bigger the chance of growing so they can finish)
				//				chanceOfGrowth =  1 + (p.age / 365) * 2;

				//If the player is older than 15 the chance of growth is bigger the further from final height the player is
				//				if (p.age / 365 > 15)
				chanceOfGrowth = 1 + (p.finalHeight - (int)p.height) / 3;

				if (r.nextInt(100) < chanceOfGrowth){
					DAOCake.growPlayer(p.getId());
				}
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println("Players grown");
	}

	/*
	 * Returns id of the new player
	 */
	public static int createNewPlayer(int countryId){

		int result = -1;
		Random r = new Random();
		final int POTENTIAL_GAUSSIAN_VARIANCE = 7;
		
		//Generate first- and last names
		String firstname = DAOCake.getRandomName(true, countryId);
		String lastname = DAOCake.getRandomName(false, countryId);

		//Starting age is 14 years. We add random 0-4 days to make sure players have different day counters and not all 0 and 5 (one real days is 5 days in footie)
		int age = 14 * 365 + r.nextInt(5);

		//Set current and final height
		int currentHeight = 160 + r.nextInt(12) + r.nextInt(12);
		int finalHeight = currentHeight + r.nextInt(12) + r.nextInt(12);

		//Set agent loyalty randomly between 10 and 90
		int agentLoyalty = 10 + r.nextInt(21) + r.nextInt(21) + r.nextInt(21) + r.nextInt(21);

		//Set overall potential from 0 - 100 based on avg. talent for the player's country
		//A gaussian pattern is used
		int potential = (int)(DAOCake.getCountryAvgTalent(countryId) + r.nextGaussian() * POTENTIAL_GAUSSIAN_VARIANCE);
		System.out.println("********************************************* potential: " + potential);
		if (potential > 100) potential = 100;
		if (potential < 1) potential = 1;

		//Is the player going to be a goal keeper? 
		boolean keeper = false;
		//About 1 out of 11 players is a goalkeeper
		if (r.nextInt(11) == 0) keeper = true;

		//We have different general development patterns that players will more or less follow (always some random thrown in)
		//0: young gun - develops quickly early on but will not receive many pp later on and only remain strong if he has good facilities and gets playing time
		//1: late bloomer - develops slowly early on but stays strong for longer
		//2: general - standard development which is good development early on and less development later on
		//3: failed wonderkid (rare) - develops quickly early on but burns out and loses his ability early in his career (negative basic pp)
		int pattern = r.nextInt(3);

		//failed wonderkid is rare
		if (r.nextDouble() < 0.05)
			pattern = 3;

		//How much random (in %) comes into the patterns
		int random = 10;

		//Potential is how good the player can become on a scale of 1-100. Calculate how many pp should be given in total over the entire career based on potential
		//2100-2300 is a player of Messi-like quality
		//About 25-30% of the PP should come from potential (basic PP given each night). The rest is training facilities, match experience and the PP in the stats the player starts with
		int totalPP = 600 / 100 * potential;

		//We set how many basic PP the player receives per day for each year from 14 to the age of 36 by which time they stop receiving basic PP (but could continue playing). 22 seasons

		//The amount of PP per season this player should get over the entire course of his career
		double avgPPPerSeason = (double)totalPP / 22.0;

		//The amount of PP per day this player should get over the entire course of his career
		double avgPPPerDay = avgPPPerSeason / 73.0;

		//The potentials for each of the players year from 14 to 36
		double[] ppPotentials = new double[22];

		//The total potential spent so far. This should never exceed totalPP
		double totalDailyPPUsed = 0;

		//Create 22 potentials
		for (int i = 0; i < 22; i++){

			//The age we're looking at now
			int currentAge = 14 + i;
			//The basic PP given each day at this age
			double dailyPPThisYear = 0;


			switch (pattern){
			//0: young gun - develops quickly early on but will not receive many pp later on and only remain strong if he has good facilities and gets playing time
			case 0:
				if (currentAge < 19) //5 years of quick growth
					dailyPPThisYear = avgPPPerDay * 2.2;
				else if (currentAge == 19)//Slow down growth
					dailyPPThisYear = avgPPPerDay * 1.8;
				else if (currentAge == 20)//Slow down growth
					dailyPPThisYear = avgPPPerDay * 1.5;
				else if (currentAge == 21)//Slow down growth
					dailyPPThisYear = avgPPPerDay * 1.3;
				else if (currentAge < 25)//3 years of normal growth
					dailyPPThisYear = avgPPPerDay;
				else if (currentAge < 29)//6 years of half growth
					dailyPPThisYear = avgPPPerDay * 0.5;
				else if (currentAge == 29)//Slow down
					dailyPPThisYear = avgPPPerDay * 0.4;
				else 					//No more basic growth after 29
					dailyPPThisYear = 0;
				break;


				//1: late bloomer - develops slowly early on but stays strong for longer
			case 1:
				if (currentAge < 16) //2 years of half growth
					dailyPPThisYear = avgPPPerDay * 0.5;
				else if (currentAge < 20)//4 years of slow (for a young player) growth
					dailyPPThisYear = avgPPPerDay * 1;
				else if (currentAge < 22)//2 years of good development
					dailyPPThisYear = avgPPPerDay * 1.5;
				else if (currentAge < 27)//5 years of double growth
					dailyPPThisYear = avgPPPerDay * 2.0;
				else if (currentAge < 29)//2 years of normal growth
					dailyPPThisYear = avgPPPerDay;
				else if (currentAge < 33)//4 years of half growth
					dailyPPThisYear = avgPPPerDay * 0.5;
				else 					//No more basic growth after 32
					dailyPPThisYear = 0;
				break;


				//2: general - standard development which is good development early on and less development later on
			case 2:
				if (currentAge < 18) //4 years of normal growth
					dailyPPThisYear = avgPPPerDay;
				else if (currentAge < 21)//3 years of quick growth
					dailyPPThisYear = avgPPPerDay * 2;
				else if (currentAge < 25)//4 years of good development
					dailyPPThisYear = avgPPPerDay * 1.5;
				else if (currentAge < 30)//5 years of normal growth
					dailyPPThisYear = avgPPPerDay;
				else if (currentAge < 32)//2 years of slow growth
					dailyPPThisYear = avgPPPerDay * 0.5;
				else 					//No more basic growth after 31
					dailyPPThisYear = 0;
				break;


				//3: failed wonderkid - develops quickly early on but burns out and loses his ability early in his career (negative basic pp)
			case 3:
				if (currentAge < 16) //2 years of very quick growth
					dailyPPThisYear = avgPPPerDay * 3;
				else if (currentAge < 19)//3 years of extreme growth
					dailyPPThisYear = avgPPPerDay * 4;
				else if (currentAge < 22)//3 years of quick growth
					dailyPPThisYear = avgPPPerDay * 2;
				else if (currentAge < 31)//9 years of negative growth
					dailyPPThisYear = avgPPPerDay * -1;
				else 					//No more basic growth after 30
					dailyPPThisYear = 0;
				break;
			}

			//Apply random %
			dailyPPThisYear *= 1.0 + (r.nextDouble() * (double)random / 100.0); 

			//Round to two decimals
			dailyPPThisYear = Math.round(dailyPPThisYear * 100) / 100d;

			//Do not use more than the total
			if (totalDailyPPUsed + dailyPPThisYear > totalPP)
				dailyPPThisYear = totalPP - totalDailyPPUsed;

			//Add total given so far
			totalDailyPPUsed += dailyPPThisYear;

			//Register as potential for this age
			ppPotentials[i] = dailyPPThisYear;
		}

		Player player = new Player();

		player.setFirstname(firstname);
		player.setLastname(lastname);
		player.setAge(age);
		player.setHeight(currentHeight);
		player.agentLoyalty = agentLoyalty;
		player.countryId = countryId;

		//Short players get the most starting pp on average (because they are faster and more agile). 
		//Larger players are stronger but slower. A short outfield player gets on average 515 pp.

		//Create basic stats
		player.topSpeed = 40 + r.nextInt(11);
		player.reaction = 30 + r.nextInt(11);
		player.stamina = 30 + r.nextInt(11);
		player.tackling = 15 + r.nextInt(11);
		player.marking = 15 + r.nextInt(11);
		player.dribbling = 15 + r.nextInt(11);
		player.vision = 15 + r.nextInt(11);
		player.heading = 15 + r.nextInt(11);
		player.shooting = 15 + r.nextInt(11);
		player.shotpower = 15 + r.nextInt(11);
		player.passing = 15 + r.nextInt(11);
		player.technique = 15 + r.nextInt(11);
		player.jumping = 30 + r.nextInt(11);

		if (keeper){
			player.handling = 15 + r.nextInt(11);
			player.commandOfArea = 15 + r.nextInt(11);
			player.shotstopping = 15 + r.nextInt(11);
			player.rushingout = 15 + r.nextInt(11);
		}
		else{
			player.handling = 5 + r.nextInt(11);
			player.commandOfArea = 5 + r.nextInt(11);
			player.shotstopping = 5 + r.nextInt(11);
			player.rushingout = 5 + r.nextInt(11);
		}

		//Acceleration, agility and strength are dependant on height 
		if (finalHeight < 175){
			player.acceleration = 45 + r.nextInt(11);
			player.agility = 45 + r.nextInt(11);
			player.strength = 25 + r.nextInt(11);
		}
		else if (currentHeight < 190){
			player.acceleration = 35 + r.nextInt(11);
			player.agility = 35 + r.nextInt(11);
			player.strength = 35 + r.nextInt(11);
		}
		else{
			player.acceleration = 30 + r.nextInt(11);
			player.agility = 25 + r.nextInt(11);
			player.strength = 45 + r.nextInt(11);
		}

		result = DAOCake.createPlayer(player, finalHeight, ppPotentials, keeper);
		DAOCake.decreaseCountryKnowledge(countryId);
		
		return result;
	}
}
