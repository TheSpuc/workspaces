package integrationTests;

import static org.junit.Assert.assertFalse;
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

public class MatchTest extends TestCase{
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
	public void testUpdateShootOut(){
		ArrayList<LinkedHashMap<String,Object>> res;
		int matchId = 3840;
		int expectedGoalsA = 3;
		int expectedGoalsB = 4;
		int actualGoalsA = 0;
		int actualGoalsB = 0;
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT shootoutGoalsHome, shootoutGoalsAway FROM matches WHERE id = " + matchId);
		if(res.get(0).get("shootoutgoalshome") != null){
			actualGoalsA = (Integer) res.get(0).get("shootoutgoalshome");
		}
		if(res.get(0).get("shootoutgoalsaway") != null){
			actualGoalsB = (Integer) res.get(0).get("shootoutgoalsaway");
		}
		if(actualGoalsA == 3 || actualGoalsB == 4){
			DAO.updateDBWithQuery("UPDATE matches SET shootoutGoalsHome=" + 1 + 
				", shootoutGoalsAway=" + 1 + " WHERE id=" + matchId + ";");
		}
		
		DAOCake.updateShootout(matchId, expectedGoalsA, expectedGoalsB);
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT shootoutGoalsHome, shootoutGoalsAway FROM matches WHERE id = " + matchId);
		if(res.get(0).get("shootoutgoalshome") != null){
			actualGoalsA = (Integer) res.get(0).get("shootoutgoalshome");
		}
		if(res.get(0).get("shootoutgoalsaway") != null){
			actualGoalsB = (Integer) res.get(0).get("shootoutgoalsaway");
		}
		
		assertTrue(actualGoalsA == expectedGoalsA);
		assertTrue(actualGoalsB == expectedGoalsB);
	}
	
	@Test
	public void testSetET(){
		int matchId = 2840;		
		DAO.updateDBWithQuery("UPDATE matches SET ET='f' WHERE id=" + matchId + ";");
		ArrayList<LinkedHashMap<String,Object>> res;
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT ET FROM matches WHERE id = " + matchId + ";");
		boolean et = (Boolean)res.get(0).get("et");
		assertFalse(et);
		
		DAOCake.setET(matchId);
		res = (ArrayList<LinkedHashMap<String,Object>>)DAO.selectFromDBWithQuery("SELECT ET FROM matches WHERE id = " + matchId + ";");
		et = (Boolean)res.get(0).get("et");
		assertTrue(et);
	}
	
	
}
