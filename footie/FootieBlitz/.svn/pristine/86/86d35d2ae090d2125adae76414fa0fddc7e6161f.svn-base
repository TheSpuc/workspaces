package data;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Random;

import data.PositionScoreCalculator.Position;
import data.teamSetup.SetupPositions;

import tasks.Agents;
import tasks.Clubs;
import tasks.Players;
import tasks.Scouting;

import model.Hjaelper;
import model.Player;
import model.Team;
import model.TransferTarget;


public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-gted method stub

		try {

//			DAOCake.openConnection("jdbc:postgresql://localhost/test_cake_footiesite?user=postgres&password=Lommen");
			
			DAOCake.openConnection();
			DAO.openConnection();
			
			//Team team = DAOCake.loadTeam(37, null, false, false);
			//Clubs.BuyPlayersIfMissing(team);

			ArrayList<Team> teams = DAOCake.loadAllTeams();
			
			for (Team team : teams){
				DAOCake.removeOldFailedNegotiations(73*2);
				
				Clubs.withdrawUnansweredOffers(team);
				System.out.println("Unanswered offers withdrawn");
				Clubs.negotiateIncomingOffers(team);
				System.out.println("Incoming offers negotiated");
				Clubs.negotiateSentOffers(team);
				System.out.println("Sent offers negotiated");
				Clubs.extendContracts(team);
				System.out.println("Contracts extensions offered");
				Clubs.transferListPlayer(team);
				System.out.println("Players transfer listed");
				
			}
			
			//Players.UpdatePlayerRatings();
			
			
//			for (int i = 0; i < 70; i++){
//				DAOCake.addPlayerPoints(); //PP til gamle spillere uden potentiale
//				Players.addPlayerPoints(); //PP til nye spillere med potentiale
//				DAOCake.addPlayerAge();
//				Players.growPlayers();
//				DAOCake.DailyPPSpend();	
//				System.out.println(i);
//			}
			
//			DAOold.createTablesFromScratch();
//			DAOold.createBasicData();
//			DAOold.createArsenalManUtd();
//			DAOcake.closeMatch(7, 2, 2, DAOcake.loadTeam(1, null), DAOcake.loadTeam(4, null));
//			DAOcake.genFixtureList(3, 2011, 7, 15, 18, 30);
//			DAOCake.createPlayers();
//			DAOcake.genFixtureList(3, 2012, 3, 13, 18, 30);
//			DAOcake.fullEnergy();
//			DAOcake.NPCReactToContracts();
//			DAOcake.finishConstructions();
//			DAOCake.switchDatabases();
			
//			DAOCake.updateMoraleAndOpinion();
//			Hjaelper.loadNames("c:\\fornavne.txt", "c:\\efternavne.txt");
//			DAOCake.updateCompetitions();
			
//			for (int i = 0; i < 73; i++){
//				Players.generateNewPlayers();
//				DAOCake.addPlayerAge();
//				Players.addPlayerPoints();
//				Players.growPlayers();
//				//DAOCake.updateMoraleAndOpinion();
//				Agents.reactToLastLogon();
//			}
			
//			Scouting.scout();

//			System.out.println(DAOCake.percentMatchesStarted(1204, 12, 30));
			
			//Hjaelper.calcAttendance(5, 8500, 8500, 17, 12, 29335, 29515);
		
			
//			Random r = new Random();
//			int avgTalent = 60;
//			final int POTENTIAL_GAUSSIAN_VARIANCE = 7;
//			int runs = 10000;
//			int above80 = 0;
//			
//			int sum1 = 0, avg1, max1 = 0, min1 = 100;
//			for (int i = 0; i < runs; i++){
//				int potential = (int)(avgTalent + r.nextGaussian() * POTENTIAL_GAUSSIAN_VARIANCE);
//				sum1 += potential;
//				if (potential < min1) min1 = potential;
//				if (potential > max1) max1 = potential;
//				if (potential > 80) above80++;
//
//			}
//			avg1 = sum1 / runs;
//
//			System.out.println(avg1 + ", " + min1 + ", " + max1 + ", " + above80);
			
//			System.out.println(DAOCake.getPlayerAvgWage(DAOCake.getPlayerAvgRating(1186)));
			
//			DAOCake.NPCReactToContracts();
			
//			DAOCake.contractAlerts();
//			DAOCake.lineupAlerts();
			
//			DAOCake.updateMorale();
//			DAOCake.NPCReactToContracts();
//			DAOCake.payLeagueSponsorshipMoney();
//			DAOold.closeConnection();
			DAOCake.closeConnection();
			DAO.closeConnection();
//			System.out.println(Hjaelper.GetRandFirstName() + " " + Hjaelper.GetRandLastName());
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
