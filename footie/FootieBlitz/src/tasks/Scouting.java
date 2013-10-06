package tasks;

import java.util.ArrayList;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Player;
import model.ScoutAssignment;
import data.DAOCake;

public class Scouting {

	/*
	 * Create scout reports and update assignments
	 */
	public static void scout(){
		ArrayList<ScoutAssignment> assignments = DAOCake.getCurrentScoutingAssignments();
		Random r = new Random();
		
		for (ScoutAssignment assignment : assignments){

			System.out.println("checking assignment: " + assignment.id);
			
			//scouting countries
			if (assignment.countryId > 0){
				//The chance of generating a report is based on country knowledge
				int knowledge = DAOCake.getCountryKnowledge(assignment.userId, assignment.countryId);
				
				int percentChanceOfReport = 10 + (int)(knowledge * 0.5) + r.nextInt(10);
				
				if (r.nextInt(100) > percentChanceOfReport){
					
					int totalActivePlayers = DAOCake.getActivePlayersFromCountry(assignment.countryId);
					
					//Get list of players from that country that hasn't been scouted yet in this assignment
					ArrayList<Player> players = DAOCake.getPersonsForCountryAssignment(assignment.countryId, assignment.id, true);
					
					//If there's not a lot of players left to scout maybe this assignment is finished - 
					//Depending on the users knowledge of the country he wont find all players
					int minPercentPlayersNotFound = 5 + (100 - knowledge) / 3;
					
					int percentPlayersNotFound = minPercentPlayersNotFound + r.nextInt(5);
							
					//Find a random player and scout him
					Player player = players.get(r.nextInt(players.size()));
					
					int scoutingAbility = DAOCake.getUserScoutingAbility(assignment.userId);
//					int detailLevel = 1 + scoutingAbility / 20;
//					if (detailLevel > 4) detailLevel = 4;
//					if (knowledge > 80) detailLevel++; 
					
					generateScoutReport(assignment.id, player.getId(), scoutingAbility, assignment.detailLevel, assignment.userId);
					DAOCake.increaseScoutingAbilityAndCountryKnowledge(assignment.userId, assignment.countryId);
					
					System.out.println("players.size(): " + players.size());
					System.out.println("totalActivePlayers: " + totalActivePlayers);
					System.out.println("percentPlayersNotFound: " + percentPlayersNotFound);
					
					//If we've found the number of players we're going to find for this assignment finish it
					if (players.size() - 1 < totalActivePlayers * percentPlayersNotFound / 100){
						DAOCake.finishScoutAssignment(assignment.id);
						//DAOCake.sendMessage(assignment.userId, 99, "Scouting assignment finished", ")
					}
				}
			}
			//scouting specific players
			else if(assignment.personId > 0){
				//The report will be finished depending on the user's knowledge of the player's country
				//and the amount of time since starting the report
				Map<Integer, Integer> countryKnowledge = DAOCake.getCountryKnowledgeFromPersonID(assignment.userId, assignment.personId);
				int countryId = -1;
				int knowledge = -1;
				for (int i : countryKnowledge.keySet()){
					countryId = i;
					knowledge = countryKnowledge.get(i);
				}
				
				int minHoursRequired = 11 / (1 + knowledge / 10);
				
				if (assignment.hoursSinceStart >= minHoursRequired && r.nextDouble() > 0.33){
					int scoutingAbility = DAOCake.getUserScoutingAbility(assignment.userId);
//					int detailLevel = scoutingAbility / 20;
							
					generateScoutReport(assignment.id, assignment.personId, scoutingAbility, assignment.detailLevel, assignment.userId);
					DAOCake.increaseScoutingAbilityAndCountryKnowledge(assignment.userId, countryId);
					DAOCake.finishScoutAssignment(assignment.id);
				}
			}

		}
		
		//Start new scout assignments for those who have something waiting and nothing active
		DAOCake.startNewScoutAssignments();
		
		System.out.println(new Date().toString() + " - scouting done. " );
	}
	
	/*
	 * @param detailLevel: Represents how much detail the report will include (1-5). A comment about the players potential is always included.
	 * 					1: A rating of 1-5 is shown on the page by showing totalPotential (1-100) divided by 20.
	 * 					2: A comment about match experience, traning received and how much of his natural development the player has gone through already is included. 
	 * 					3: A rating of 1-20 is shown on the page by showing totalPotential (1-100) divided by 5.
	 * 	  				4: A rating of 1-100 is shown on the page (totalPotential)
	 * 					5: A comment about whether the player will develop early or late in his career is included   
	 * @param percentAccurate: How accurate the report should be. An low value does not necessarily mean that the report wil be inaccurate, but the risk is higher (1-100)
	 */
	public static String generateScoutReport(int scoutAssignmentId, int personId, int percentAccurate, int detailLevel, int userId){

		String result= "";
		Random r = new Random();
		
		//Load player and his potentials
		Player player = DAOCake.getPlayer(personId);
		double[] potentials = DAOCake.getPlayerPotentials(personId);
		double totalPotential = 0;
		double earlyPotential = 0;
		double midPotential = 0;
		double latePotential = 0;
		double remainingPotential = 0;
		
		//The maximum possible sum of all 22 basic_pp in player potentials. This needs to be updated to match maximum potential in createNewPlayer() - currently 600 / 73
		final double MAXPOTENTIAL = 8.22;
		
		//The actual max potential for a player of this age
		double actualMaxPotential = MAXPOTENTIAL / 22.0 * (double)(player.getAge() - 14) / 365.0;
		
		//How much error there is in this report in percent
		double errorPercent = r.nextInt(101 - percentAccurate);
		
		//Actual error in this report is based on the remaining potential a player of this age could have
		double error = actualMaxPotential * errorPercent / 100.0;
		error *= r.nextDouble();
		//The error is more likely to be minus 
		if (r.nextDouble() > 0.33)
			error *= -1;
		
		//Sum of the players remaining potential
		for (int i = 0; i < potentials.length; i++){
			totalPotential += potentials[i];
			
			//Early late and mid potentials
			if (14 + i < 20) earlyPotential += potentials[i];
			else if (14 + i < 25) midPotential += potentials[i];
			else latePotential += potentials[i];
			
			if (player.getAge() / 365 - 14 < i)
				remainingPotential += potentials[i];
		}
		
		//totalPotential == 0 means this player is one of the old user generated players without potential
		if (totalPotential == 0){
			result = "I cannot judge this player's potential. There's something off about him...";
			result = result.replace("'", "''");
			DAOCake.saveScoutReport(scoutAssignmentId, personId, result, 0, 1);
		}
		else{

			totalPotential += error;
			remainingPotential += error;

			if (totalPotential > MAXPOTENTIAL) totalPotential = MAXPOTENTIAL;
			if (remainingPotential > actualMaxPotential) remainingPotential = actualMaxPotential;
			if (totalPotential < 0) totalPotential = 0;
			if (remainingPotential < 0) remainingPotential = 0;

			//Check his overall potential and how much potential there is left from this age on and write a report based on that
			double numberOfDescriptions = 6;
			double step = MAXPOTENTIAL / numberOfDescriptions;
			
			//The steps to match descriptions. 
			double steps[] = new double[6];
			
			//Only the top 5 percent are special talents
			steps[0] = MAXPOTENTIAL * 0.95;
			steps[1] = MAXPOTENTIAL * 0.85;
			steps[2] = MAXPOTENTIAL * 0.75;
			steps[3] = MAXPOTENTIAL * 0.6;
			steps[4] = MAXPOTENTIAL * 0.5;
			steps[5] = MAXPOTENTIAL * 0.3;
			
			if (totalPotential > steps[0]){
				result = "This player is a unique talent. His overall potential is among the highest in the world.";
			}
			else if(totalPotential > steps[1]){
				result = "This is a very talented player. His overall potential could make him a top player.";
			}
			else if(totalPotential > steps[2]){
				result = "This is a player with good potential. He could become a solid player.";
			}
			else if(totalPotential > steps[3]){
				result = "This player's overall potential is decent. He will never be world class, but with hard work and a lot of match experience he can become a good utility player.";
			}
			else if(totalPotential > steps[4]){
				result = "The potential here is limited. Don't expect a lot of natural development from this player.";
			}
			else if(totalPotential > steps[5]){
				result = "This player has minimal natural potential and will have to work hard for most of his skills.";
			}
			else{
				result = "This person was in the right place at the right time to get into professional football, but doesn't really posess a lot of talent - if any.";
			}
			
			
			//detailLevel 1: A rating of 1-5 is shown on the page by showing totalPotential (1-100) divided by 20.
			
			//detailLevel 2: A comment about match experience, traning received and how much of his natural development the player has gone through already is included.
			if (detailLevel >= 2){
				Player p = DAOCake.getPlayerTotalXPAndTraining(personId);
				
				//Remaining natural development
				if (remainingPotential < 0){
					result += "<br /><br />He is past his best and his ability might soon deteriorate.";
				}
				else if (remainingPotential / totalPotential < 0.25){
					result += "<br /><br />He is almost playing at his full natural potential.";
				}
				else if(remainingPotential / totalPotential < 0.5){
					result += "<br /><br />He still has some natural growth left.";
				}
				else if(remainingPotential / totalPotential < 0.75){
					result += "<br /><br />He still has most of his natural development left in him.";
				}
				else{
					result += "<br /><br />He has only just started developing and his potential will start to show once he gets older.";
				}
				
				//Training received
				if (p.totalPPFromFacc / (double)Player.maxCareerPPFromFacc < 0.25){
					result += " Training hasn't contributed much to the player's current ability so there's a lot to gain in that area.";
				}
				else if (p.totalPPFromFacc / (double)Player.maxCareerPPFromFacc < 0.5){
					result += " The benefits of training so far in his career have been limited and decent training should improve his abilities considerably.";
				}
				else if (p.totalPPFromFacc / (double)Player.maxCareerPPFromFacc < 0.75){
					result += " The player has had decent training and will not improve dramatically in that respect.";				
				}
				else{
					result += " The player has trained well in his career and will not improve much more on the training ground.";
				}
				
				//Xp gained
				if (p.totalPPFromXP / (double)Player.maxCareerPPFromXP < 0.25){
					result += " He hasn't gained a lot of match experience so if you bring him in, try to give him some matches to aid his development.";
				}
				else if (p.totalPPFromXP / (double)Player.maxCareerPPFromXP < 0.5){
					result += " He has got a few matches under his belt but will still develop well from match experience.";
				}
				else if (p.totalPPFromXP / (double)Player.maxCareerPPFromXP < 0.75){
					result += " He is already an experienced player, but will still improve by playing matches.";		
				}
				else{
					result += " He has played in many matches and won't develop a lot more in that respect.";
				}
			}
			
			int potentialPercent = (int)(totalPotential / MAXPOTENTIAL * 100.0);
			
			//detailLevel 3: A rating of 1-20 is shown on the page by showing totalPotential (1-100) divided by 5.
			if (detailLevel == 3){
				result += "<br /><br />I rate this players overall potential at " + (potentialPercent / 5) + " out of 20.";
			}
		
			//detailLevel 4: A rating of 1-100 is shown on the page (totalPotential)   
			if (detailLevel >= 4){
				result += "<br /><br />I rate this players overall potential at " + potentialPercent + " out of 100.";
			}

			//detailLevel 5: A comment about whether the player will develop early or late in his career is included
			if (detailLevel >= 5){
				//There's a risk that the scout will get this wrong (percentAccurate)
				boolean early = false;
				if (earlyPotential > totalPotential / 2)
					early = true;
				
				if (percentAccurate > r.nextInt(100))
					early = !early;
				
				if (early)
					result += "<br /><br />It looks like this player is developing very quickly early in his career.";
				else
					result += "<br /><br />This player is not developing very quickly at the moment and may reach his full potential late in his career.";
			
			}
			
			result = result.replaceAll("'", "''");

			DAOCake.saveScoutReport(scoutAssignmentId, personId, result, potentialPercent, detailLevel);
			
			//Pay for the scout report
			//DAOCake.createUserExpense(100 + (detailLevel * detailLevel) / 2 * 500, 12, "Scout report", userId);
		}

		return result;
	}
}
