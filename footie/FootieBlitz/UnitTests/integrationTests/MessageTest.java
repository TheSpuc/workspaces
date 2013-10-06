package integrationTests;

import static org.junit.Assert.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.DAO;
import data.DAOCake;

public class MessageTest extends TestCase{
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
	public void testSendMessage(){
		String subject = "Her en testbesked";
		String text = "Sendt " + System.currentTimeMillis();
		DAOCake.sendMessage(1, 2, subject, text);
		ArrayList<LinkedHashMap<String,Object>> res;
		res = (ArrayList<LinkedHashMap<String, Object>>) DAO.selectFromDBWithQuery("SELECT text FROM messages WHERE text = '" + text + "';");
		assertTrue(res.get(0).get("text").equals(text));
	}
}
