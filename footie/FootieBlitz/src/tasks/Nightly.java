package tasks;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;

import model.Team;

import data.DAO;
import data.DAOCake;

public class Nightly {

	/**
	 * Tasks to be run every night
	 */
	public static void main(String[] args) {
		
		try {

			DAOCake.openConnection();
			DAO.openConnection();
			Date d = new Date();
			System.out.println("Nightly start " + d.toString());
			
			//Players
			DAOCake.addPlayerPoints(); //PP til gamle spillere uden potentiale
			Players.addPlayerPoints(); //PP til nye spillere med potentiale
			DAOCake.addPlayerAge();
			Players.growPlayers();
			DAOCake.addPlayerEnergy();
			DAOCake.updateMorale();
			DAOCake.payWages();
			DAOCake.DailyPPSpend();
			Players.generateNewPlayers();
			DAOCake.NPCReactToContracts();
			DAOCake.createPlayerAttributeSnapshotsForNewPlayers();
			
			//Clubs
			DAOCake.cleanLineups();
			DAOCake.updateInterest();
			DAOCake.payClubMaintenance();
			DAOCake.writeClubGraphs();
			DAOCake.kickClubOwners();
			DAOCake.removeOldFailedNegotiations(73*2);
			
			ArrayList<Team> teams = DAOCake.loadAllTeams(true);
			
			for (Team team : teams) Clubs.withdrawUnansweredOffers(team);
			System.out.println("Unanswered offers withdrawn");
			
			for (Team team : teams) Clubs.negotiateIncomingOffers(team);
			System.out.println("Incoming offers negotiated");
			
			for (Team team : teams) Clubs.negotiateSentOffers(team);
			System.out.println("Sent offers negotiated");

			for (Team team : teams) Clubs.extendContracts(team);
			System.out.println("Contracts extensions offered");

			for (Team team : teams) Clubs.transferListPlayer(team);
			System.out.println("Players transfer listed");
			
			for (Team team : teams)	Clubs.BuyPlayersIfMissing(team);
			System.out.println("Clubs sent offers for new players");
			
			
			//Agent opinions and reputation
			DAOCake.clearAllAgentOpinionThoughts();
			Agents.playersReactToContract();
			Agents.playersReactToLastLogon();
			Agents.playersReactToAgentNumberOfPlayers();
			DAOCake.limitAgentReputation();
			DAOCake.limitAgentOpinion();
			Agents.fireAgents();
			DAOCake.playersReactToAgentOffers();
			
			DAOCake.decreaseScoutingAbility();
			
			//Alerts
			DAOCake.contractAlerts();
			DAOCake.lineupAlerts();
			
			DAOCake.updateCompetitions();
			DAOCake.finishConstructions();
			
			//Log
			d = new Date();
			System.out.println("Nightly done " + d.toString());
			System.out.println();
			
			DAO.closeConnection();
			DAOCake.closeConnection();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

