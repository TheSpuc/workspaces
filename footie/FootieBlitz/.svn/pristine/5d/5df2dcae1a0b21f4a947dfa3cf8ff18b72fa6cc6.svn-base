package tasks;

import java.sql.SQLException;
import java.util.Date;

import data.DAOCake;

public class Hourly {

	/**
	 * Tasks to be run every hour
	 */
	public static void main(String[] args) {
		
		try {

			DAOCake.openConnection();
			Date d = new Date();
			System.out.println("Hourly start " + d.toString());
			
			//Update scouting assignments and generate scout reports
			Scouting.scout();
			
			//Log
			d = new Date();
			System.out.println("Hourly done " + d.toString());
			System.out.println();
			
			
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

