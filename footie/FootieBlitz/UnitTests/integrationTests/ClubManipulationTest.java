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
import data.TransferList;

public class ClubManipulationTest extends TestCase{
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
	public void testCleanLineups(){
		DAO.updateDBWithQuery("UPDATE lineups l SET pl18id = 922 WHERE l.id = 5");		
		DAOCake.cleanLineups();
		ArrayList<LinkedHashMap<String,Object>> res;
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT pl18id FROM lineups WHERE id = 5");
		assertTrue(res.get(0).get("pl18id").equals(-1));
	}
	
	@Test
	public void testPayInterest(){
		ArrayList<LinkedHashMap<String,Object>> res;
		DAO.updateDBWithQuery("UPDATE clubs c SET money = -10000 WHERE id = 8");
		DAOCake.updateInterest();		
		res = (ArrayList<LinkedHashMap<String, Object>>) DAO.selectFromDBWithQuery("SELECT money FROM clubs WHERE id = 8");
		System.out.println("Show me the money " + res.get(0).get("money"));
		assertTrue(res.get(0).get("money").equals(-10007));
		
		//Check negative return (that it doesn't return true every time)
		assertTrue(!res.get(0).get("money").equals(-10037));
	}
	
	@Test
	public void testReceiveInterest(){
		ArrayList<LinkedHashMap<String,Object>> res;
		DAO.updateDBWithQuery("UPDATE clubs c SET money = 10000 WHERE id = 8");
		DAOCake.updateInterest();		
		res = (ArrayList<LinkedHashMap<String, Object>>) DAO.selectFromDBWithQuery("SELECT money FROM clubs WHERE id = 8");
		assertTrue(res.get(0).get("money").equals(10001));
		
		//Check negative return (that it doesn't return true every time)
		assertTrue(!res.get(0).get("money").equals(10401));
	}
	
	@Test
	public void testPayWagesClubs(){
		ArrayList<LinkedHashMap<String,Object>> res;
		int clubId = 8;
		int amount = 40000;
		
		//Find the current sum of expenses for the club
		res = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT sum(amount) FROM club_expenses WHERE club_id = " + clubId + ";");
		Long clubAmount = (Long) res.get(0).get("sum");
		
		//Make a club expense for the club
		ArrayList<LinkedHashMap<String,Object>> players = new ArrayList<LinkedHashMap<String, Object>>();
		LinkedHashMap<String, Object> c1 = new LinkedHashMap<String, Object>();
		c1.put("id", clubId);
		c1.put("sum", amount);
		players.add(c1);
		
		//Assert that the amount matches what was inserted as expenses
		DAOCake.payWagesClubs(players);
		res = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT sum(amount) FROM club_expenses WHERE club_id = " + clubId + ";");
		System.out.println("Amount before: " + clubAmount + ", Amount after: " + (Long)res.get(0).get("sum"));
		assertTrue((Long) res.get(0).get("sum") == clubAmount + amount);
	}
	
	@Test
	public void testDeleteListingsWithExpiredContracts(){
		ArrayList<LinkedHashMap<String,Object>> res;
		
		//Insert a test listing with expired contract
		DAO.updateDBWithQuery("INSERT INTO transferlist (person_id, contract_id, playerPrice, message, dateListed, dateUpdated) " +
				"VALUES (944, 1082, 5500, 'Ikke så god', now() - interval '200 days', now())"); 
		
		//Run deleteListingsWithExpiredContracts
		TransferList.deleteListingsWithExpiredContracts();
		
		//Assert that the listing has been deleted
		res = (ArrayList<LinkedHashMap<String,Object>>) DAO.selectFromDBWithQuery("SELECT * FROM transferlist WHERE person_id = 944 AND contract_id = 1082;");
		
		assertTrue(res.isEmpty());
		
	}
}
