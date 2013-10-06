package data;

import java.sql.SQLException;
import java.util.Random;

public class GeneratePlayers {

	public static void main(String[] args) {

		int numberOfPlayers = 1000;
		Random r = new Random();
		
		try {
			DAOCake.openConnection();
			
			for (int i = 0; i < numberOfPlayers; i++){
				switch(r.nextInt(5)){
				
				case 0://maalmand
//					String sql = "INSERT INTO person (firstName, lastName, age, acceleration, topSpeed, " +
//						"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
//						"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('Pla', 'yer " + i + "', 26, 50, 50, 50, 50, 50, 50, " +
//						"50, 50, 50, 50, 50, 50, 180, 50, 50, " + i + ", 50, 99, 60);";
//						
//
//						sql = "INSERT INTO contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
//						"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values (1, " + a + ", '2009-01-01', '2011-12-31', 10000, " +
//						" 500, 250, null, 'true', 'true', 1);";
//						
//					DAO.executeSimpleStatement(sql);
					break;
					
				}
			}
			
			DAOCake.closeConnection();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
	}
}
