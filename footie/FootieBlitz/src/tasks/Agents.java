package tasks;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Hjaelper;
import model.Player;

import data.AgentOpinion;
import data.DAOCake;
import data.PlayerMorale;

public class Agents {

	/**
	 * Method that checks if players are too unhappy with their agents and fires them if they are. 
	 */
	public static void fireAgents(){
		
		Random r = new Random();

		ArrayList<Player> players = DAOCake.getAgentOpinions();

		for (Player p : players){

			//For now players will fire their agents if agent_opinion drops below 35 - (agent_loyalty / 4) 
			if (p.agentOpinion  < 35 - (p.agentLoyalty  / 4)){

				//still a 65% chance that the player will give his agent another day to fix issues so we get a little random in as well
				if (r.nextDouble() < 0.35){
					DAOCake.fireAgent(p.userId, p.getId(), p.getFirstname(), p.getLastname());
					System.out.println(p.getId() + " has fired " + p.userId);
				}
			}
		}
	}
	
	/**
	 * Players react to their current contract
	 */
	public static void playersReactToContract(){
		ArrayList<Player> players = DAOCake.getAllPlayersOnContracts();
		
		for (Player p : players){
			
			double wageChange = DAOCake.wageDifferenceToMorale(p.wage, p.expectedWage);
			String opinionThoughts = getWageOpinionThoughts(wageChange);
			
			p.agentOpinion = AgentOpinion.calcNewOpinion(p.agentOpinion, wageChange);
			
			//Update agent reputation based on how happy the player is with his wages
			double repChange = AgentOpinion.calculateAgentReputation(p.wage, wageChange);
			DAOCake.updateAgentReputation(repChange, p.agentId);

			//We update agent opinions if the person has an agent. User_id 99 means the person does not have an agent. 
			DAOCake.updateAgentOpinion(p.agentId, p.getId(), p.agentOpinion, opinionThoughts, true);
		}
	}
	
	/**
	 * Players react to agents not having logged on for a while
	 */
	public static void playersReactToLastLogon(){
		
		try {
			HashMap<Integer, Integer> rows = DAOCake.getAgentLastLogon();
		
			for (int id : rows.keySet()){

				//All players will fire their agent if he hasn't logged on for 73 days
				if (rows.get(id) > 73){
					DAOCake.fireAgentFromAllPlayers(id);
				}
				//All players who are represented by an agent who hasn't logged in for more than 7 days lose 0.2 points in agent_opinion for each week the agent hasn't logged on
				else if (rows.get(id) > 7){

					double drop = -0.2 * ((double)rows.get(id) / 7.0);
					DAOCake.updateAgentAllPlayersOpinions(id, drop, "I need to have an agent who checks in on me regularly.", true);
					
					//Update agent reputation. Every time this method is run (each night) the agent's reputation drops if he hasn't been online in the last 7 days
					DAOCake.updateAgentReputation(-1, id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Players have reacted to agents not logging on.");
	}

	/**
	 * Players react to agents having too many players
	 */
	public static void playersReactToAgentNumberOfPlayers(){
		
		try {
			HashMap<Integer, Integer> rows = DAOCake.getAgentNumberOfPlayers();
		
			for (int id : rows.keySet()){

				//All players who are represented by an agent who represents more than 8 players will not like it
				if (rows.get(id) > 8){

					double drop = Hjaelper.round(-0.2 * ((double)rows.get(id) - 8.0), 2);
					DAOCake.updateAgentAllPlayersOpinions(id, drop, "I need to have an agent who has time for me. You have too many clients to pay attention to my career.", true);
					
					//Update agent reputation. Every time this method is run (each night) the agent's reputation drops if he has too many clients
					DAOCake.updateAgentReputation(drop, id);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Players have reacted to agents having too many clients.");
	}
	
	/**
	 * @param wageChange (double) a measure of how much the wage differs from what the player thinks he warrants. The higher the better the player will react
	 * @return an opinion on what the player thinks of his current wage
	 */
	public static String getWageOpinionThoughts(double wageChange){
		String opinionThoughts = "";
		if (wageChange > 3){
			opinionThoughts = "I''m really excited to receive such generous wages.";
		}
		else if (wageChange > 1){
			opinionThoughts = "I''m happy with my current wages.";
		}
		else if (wageChange < -3){
			opinionThoughts = "My wages are a disgrace and it''s killing my motivation to play.";
		}
		else if (wageChange < -1){
			opinionThoughts = "I think i should receive higher wages.";
		}
		
		return opinionThoughts;		
	}
}
