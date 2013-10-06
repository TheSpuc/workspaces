package main;

import java.sql.SQLException;
import java.util.GregorianCalendar;

import model.Sysout;
import data.DAO;
import data.DAOCake;

public class FB{

	private static boolean close = false;
	private static MatchFrame matchFrame;
	
	public static void main(String[] args) {

		int nextMatchId = 0, leagueId = 0;
		String nextMatchTime = "";
		GregorianCalendar matchCal = (GregorianCalendar) GregorianCalendar.getInstance();
		
		try {
			DAOCake.openConnection();
			DAO.openConnection();
			DAOCake.addPlayerEnergy();
			DAOCake.addPlayerEnergy();
			DAOCake.addPlayerEnergy();
			DAOCake.addPlayerEnergy();
//			DAOCake.addPlayerEnergy();
//			DAOCake.createMatch(0);
//			DAOCake.setMatchStatus(155, 0);
			DAOCake.closeConnection();
			DAO.closeConnection();
		}
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		while (!close){
			try {
				DAOCake.openConnection();
				nextMatchId = DAOCake.getNextMatchId();
				nextMatchTime = DAOCake.getNextMatchTime(nextMatchId);
				leagueId = DAOCake.getLeagueId(nextMatchId);
				
				if (nextMatchTime.length() > 0){
					System.out.println("Next macth starting at: " + nextMatchTime);
					DAOCake.executeSimpleStatement("UPDATE status SET lastFixtureCheck=now(), statustext='Next match starting at: " + nextMatchTime + "';");
					
					matchCal.set(GregorianCalendar.YEAR, Integer.parseInt(nextMatchTime.substring(0, 4)));
					matchCal.set(GregorianCalendar.MONTH, Integer.parseInt(nextMatchTime.substring(5, 7)) - 1);
					matchCal.set(GregorianCalendar.DATE, Integer.parseInt(nextMatchTime.substring(8, 10)));
					matchCal.set(GregorianCalendar.HOUR_OF_DAY, Integer.parseInt(nextMatchTime.substring(11, 13)));
					matchCal.set(GregorianCalendar.MINUTE, Integer.parseInt(nextMatchTime.substring(14, 16)));
					matchCal.set(GregorianCalendar.SECOND, Integer.parseInt(nextMatchTime.substring(17, 19)));
				}
				else{
					System.out.println("No more matches scheduled");
				}
//				DAOCake.closeConnection();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (nextMatchTime.length() > 0){
				while (matchCal.getTimeInMillis() - System.currentTimeMillis() > 0){

					//vent til kampen starter
					if (((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) > 60){
						if (((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) % 60 == 0){
							System.out.println((((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) / 60) + " minutter til n¾ste kamp");
						}
					}
					else if (((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) % 60 == 0){
						System.out.println("1 minut til n¾ste kamp");
					}
					else if (((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) < 11){
						System.out.println(((matchCal.getTimeInMillis() - System.currentTimeMillis()) / 1000) + " sekunder til n¾ste kamp");
					}
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				try {
					matchCal.add(GregorianCalendar.MONTH, 1);
					matchFrame = new MatchFrame(nextMatchId, leagueId);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			try {
				Thread.sleep(15000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
//		}
	}

}

