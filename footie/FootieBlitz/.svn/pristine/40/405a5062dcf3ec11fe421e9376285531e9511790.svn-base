package tasks;

import java.sql.SQLException;
import java.util.Date;

import data.DAO;
import data.DAOCake;

public class Weekly {

	/**
	 * Tasks to be run every night
	 */
	public static void main(String[] args) {
		
		try {

			DAOCake.openConnection();
			DAO.openConnection();
			Date d = new Date();
			System.out.println("Weekly start " + d.toString());
			
			//Clubs
			DAOCake.removeOldFailedNegotiations(73*2);
			
			//Players
			DAOCake.createNewWageStats();
			Players.UpdatePlayerRatings();
			
			//Log
			d = new Date();
			System.out.println("Weekly done " + d.toString());
			System.out.println();
			
			DAO.closeConnection();
			DAOCake.closeConnection();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

