package main;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import model.MatchEngine;
import model.Settings;

import data.DAOCake;

public class FB_NoGraphics{

	private static boolean close = false;
	private static MatchEngine bgMatch;

	public static void main(String[] args) {

		int nextMatchId = 0, leagueId = 0;
		String nextMatchTime = "";
		GregorianCalendar matchCal = (GregorianCalendar) GregorianCalendar.getInstance();
		Settings settings = Settings.getInstance();
		
		try {
			DAOCake.openConnection(settings.getSqlConn());
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		try {
			nextMatchId = DAOCake.getNextMatchId();
			nextMatchTime = DAOCake.getNextMatchTime(nextMatchId);
			leagueId = DAOCake.getLeagueId(nextMatchId);

			if (nextMatchTime.length() > 0){
				if (Settings.SO) System.out.println(DateFormat.getInstance().format(new Date()) + " ... next match (" + nextMatchId + ") starting at: " + nextMatchTime);
				DAOCake.executeSimpleStatement("UPDATE status SET lastFixtureCheck=now(), statustext='Next match starting at: " + nextMatchTime + "';");

				matchCal.set(GregorianCalendar.YEAR, Integer.parseInt(nextMatchTime.substring(0, 4)));
				matchCal.set(GregorianCalendar.MONTH, Integer.parseInt(nextMatchTime.substring(5, 7)) - 1);
				matchCal.set(GregorianCalendar.DATE, Integer.parseInt(nextMatchTime.substring(8, 10)));
				matchCal.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(nextMatchTime.substring(11, 13)));
				matchCal.set(GregorianCalendar.MINUTE, Integer.parseInt(nextMatchTime.substring(14, 16)));
				matchCal.set(GregorianCalendar.SECOND, Integer.parseInt(nextMatchTime.substring(17, 19)));
			}
			else{
				System.out.println(DateFormat.getInstance().format(new Date()) + " ... no more matches scheduled.");
				DAOCake.executeSimpleStatement("UPDATE status SET lastFixtureCheck=now(), statustext='No more matches scheduled.';");
			}
			//				DAOCake.closeConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (nextMatchTime.length() > 0){
			if (matchCal.getTimeInMillis() - System.currentTimeMillis() < 15000){

				try {
					bgMatch = new MatchEngine(nextMatchId, leagueId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} else
			try {
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

