package integrationTests;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import model.Sysout;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.DAO;
import data.DAOCake;

public class LeagueManipulationTest extends TestCase{

	@Before
	public final void setUp() throws Exception { 
		try {
			DAO.openConnection();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}
	
	@After
	public final void tearDown(){
		try {
			DAO.closeConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testGetAllLeaguesWithRepLargerThan0(){
		ArrayList<LinkedHashMap<String,Object>> res = DAOCake.getAllLeaguesWithRepLargerThan0();		
		assertTrue(!res.isEmpty());
	}
	
	@Test
	public void testCreateNewSeason(){
		ArrayList<LinkedHashMap<String,Object>> season = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT id, number FROM seasons ORDER BY number DESC");
		int seasonNumber = (Integer) season.get(0).get("number");
		int seasonId = (Integer) season.get(0).get("id");
		
		DAOCake.createNewSeason();
		
		ArrayList<LinkedHashMap<String,Object>> league = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT id, season_id FROM league_tables WHERE season_id = " + seasonId + ";");
		ArrayList<LinkedHashMap<String,Object>> seasonAfter = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT id, number FROM seasons ORDER BY number DESC");
		int seasonAfterNumber = (Integer) seasonAfter.get(0).get("number");
		
		assertTrue(seasonNumber + 1 == seasonAfterNumber);
		assertTrue(!league.isEmpty());
	}
	
	@Test
	public void testPayLeagueSponsorshipMoney(){
		int clubId = 12;
		ArrayList<LinkedHashMap<String,Object>> league = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT leaguereputation FROM leagues l LEFT JOIN clubs c ON l.id = c.league_id WHERE c.id = " + clubId + ";");
		
		int leagueRep = (Integer) league.get(0).get("leaguereputation");
		double amount = 1.0 * leagueRep;
		amount = Math.pow(amount, 3.0) * 4000;
		
		DAOCake.payLeagueSponsorshipMoney();
		
		ArrayList<LinkedHashMap<String,Object>> clubIncome = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT id, club_id, amount FROM club_incomes WHERE club_id = " + clubId + " ORDER BY id DESC;");
		int amountAfter = (Integer) clubIncome.get(0).get("amount");
		
		assertTrue(amount == amountAfter);
	}

}
