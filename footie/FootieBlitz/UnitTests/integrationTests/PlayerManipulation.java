package integrationTests;

import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.DAO;
import data.DAOCake;

public class PlayerManipulation extends TestCase{
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
	public void testCreatePlayerAttributeSnapshots(){
		//First set last snapshot of a player to 2 in all attributes
		int playerId = 1069;
		DAO.updateDBWithQuery("UPDATE person_attribute_histories SET age = 2, acceleration = 2, topspeed = 2" +
				", dribbling = 2, marking = 2, energy = 2, strength = 2, tackling = 2, agility = 2, " +
				"reaction = 2, handling = 2, shooting = 2, shotpower = 2, passing = 2, technique = 2, " +
				"jumping = 2, stamina = 2, heading = 2, commandofarea = 2, shotstopping = 2, rushingout = 2, " +
				"vision = 2 WHERE person_id = " + playerId);
		ArrayList<LinkedHashMap<String,Object>> res;
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT * FROM person_attribute_histories WHERE person_id = " + playerId);
		
		//Assert that snapshot has been changed
		assertTrue((Integer)res.get(res.size()-1).get("age") == 2);
		assertTrue((Float)res.get(res.size()-1).get("acceleration") == 2);
		assertTrue((Float)res.get(res.size()-1).get("topspeed") == 2);
		assertTrue((Float)res.get(res.size()-1).get("dribbling") == 2);
		assertTrue((Float)res.get(res.size()-1).get("marking") == 2);
		assertTrue((Float)res.get(res.size()-1).get("energy") == 2);
		assertTrue((Float)res.get(res.size()-1).get("strength") == 2);
		assertTrue((Float)res.get(res.size()-1).get("tackling") == 2);
		assertTrue((Float)res.get(res.size()-1).get("agility") == 2);
		assertTrue((Float)res.get(res.size()-1).get("reaction") == 2);
		assertTrue((Float)res.get(res.size()-1).get("handling") == 2);
		assertTrue((Float)res.get(res.size()-1).get("shooting") == 2);
		assertTrue((Float)res.get(res.size()-1).get("shotpower") == 2);
		assertTrue((Float)res.get(res.size()-1).get("passing") == 2);
		assertTrue((Float)res.get(res.size()-1).get("technique") == 2);
		assertTrue((Float)res.get(res.size()-1).get("jumping") == 2);
		assertTrue((Float)res.get(res.size()-1).get("stamina") == 2);
		assertTrue((Float)res.get(res.size()-1).get("heading") == 2);
		assertTrue((Float)res.get(res.size()-1).get("commandofarea") == 2);
		assertTrue((Float)res.get(res.size()-1).get("shotstopping") == 2);
		assertTrue((Float)res.get(res.size()-1).get("rushingout") == 2);
		assertTrue((Float)res.get(res.size()-1).get("vision") == 2);
		
		//Run createPlayerAttributeSnapshots
		DAOCake.createPlayerAttributeSnapshots();
		
		//Assert that last snapshot has been changed back
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT * from person_attribute_histories WHERE person_id = " + playerId);
		assertTrue((Integer)res.get(res.size()-1).get("age") > 2);
		assertTrue((Float)res.get(res.size()-1).get("acceleration") > 2);
		assertTrue((Float)res.get(res.size()-1).get("topspeed") > 2);
		assertTrue((Float)res.get(res.size()-1).get("dribbling") > 2);
		assertTrue((Float)res.get(res.size()-1).get("marking") > 2);
		assertTrue((Float)res.get(res.size()-1).get("energy") > 2);
		assertTrue((Float)res.get(res.size()-1).get("strength") > 2);
		assertTrue((Float)res.get(res.size()-1).get("tackling") > 2);
		assertTrue((Float)res.get(res.size()-1).get("agility") > 2);
		assertTrue((Float)res.get(res.size()-1).get("reaction") > 2);
		assertTrue((Float)res.get(res.size()-1).get("handling") > 2);
		assertTrue((Float)res.get(res.size()-1).get("shooting") > 2);
		assertTrue((Float)res.get(res.size()-1).get("shotpower") > 2);
		assertTrue((Float)res.get(res.size()-1).get("passing") > 2);
		assertTrue((Float)res.get(res.size()-1).get("technique") > 2);
		assertTrue((Float)res.get(res.size()-1).get("jumping") > 2);
		assertTrue((Float)res.get(res.size()-1).get("stamina") > 2);
		assertTrue((Float)res.get(res.size()-1).get("heading") > 2);
		assertTrue((Float)res.get(res.size()-1).get("commandofarea") > 2);
		assertTrue((Float)res.get(res.size()-1).get("shotstopping") > 2);
		assertTrue((Float)res.get(res.size()-1).get("rushingout") > 2);
		assertTrue((Float)res.get(res.size()-1).get("vision") > 2);		
	}
	
	@Test
	public void testAddPlayerAge(){
		//First get the age of a player
		ArrayList<LinkedHashMap<String,Object>> res;
		int personId = 1416;
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT age FROM persons WHERE id = "+ personId + ";");
		int age = (Integer)res.get(0).get("age");
		
		//Run addPlayerAge
		DAOCake.addPlayerAge();
		
		//Assert the 5 days has been added to the players age
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT age FROM persons WHERE id = "+ personId + ";");
		assertTrue(age + 5 == (Integer)res.get(0).get("age"));
	}
	
	@Test
	public void testAddPlayerEnergy(){		
		ArrayList<LinkedHashMap<String,Object>> res;
		//First set the energy of a player
		int personId = 1416;
		DAO.updateDBWithQuery("UPDATE persons SET energy = 50 WHERE id = "+ personId + ";");
		
		//Then get the energy of a player and assert that it is set to 50
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT energy FROM persons WHERE id = "+ personId + ";");
		double age = (Float)res.get(0).get("energy");
		assertTrue(age == 50);
		
		//Run addPlayerEnergy
		DAOCake.addPlayerEnergy();
		
		//Assert that 20 has been added to the players energy
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT energy FROM persons WHERE id = "+ personId + ";");
		assertTrue(age + 20 == (Float)res.get(0).get("energy"));
	}
	
	@Test
	public void testGetPlayerAverageRating(){
		assertTrue(1.0 == DAOCake.getPlayerAvgRating(1416, true));		
	}
	
	@Test
	public void testPayWagesPlayers(){
		//a player that has an active contract
		int playerId = 1260;
		ArrayList<LinkedHashMap<String,Object>> res;
		
		//Find the weeklywage and money of the player being tested
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT weeklywage, money  FROM contracts c LEFT JOIN persons p ON c.person_id = p.id WHERE person_id = " + playerId + "AND enddate>now() AND acceptedbyplayer='t'");
		int playerWage = (Integer) res.get(0).get("weeklywage");
		Long money = (Long) res.get(0).get("money");
		
		//Pay the players wages
		DAOCake.payWagesPlayers();
		
		//Check that the players money has increased by his weeklywage
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT weeklywage, money  FROM contracts c LEFT JOIN persons p ON c.person_id = p.id WHERE person_id = " + playerId + "AND enddate>now() AND acceptedbyplayer='t'");
		Long newMoney = (Long) res.get(0).get("money");
		assertTrue(money + playerWage == newMoney);
	}
	
	@Test
	public void testFindPlayersOnContractIsNotEmpty(){
		ArrayList<LinkedHashMap<String,Object>> res;
		res = DAOCake.findPlayersOnContract();
		assertTrue(!res.isEmpty());
	}
}
