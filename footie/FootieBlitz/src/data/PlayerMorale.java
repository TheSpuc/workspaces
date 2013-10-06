package data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import model.Team;

public class PlayerMorale {

	
	/**Get wage statistics. Statistics are saved in a map with key=rating and value wage		
	 * @param stmt
	 * @return A map with key=rating and value wage
	 */
	public static Map<Double, Integer> getWageAverage(){
		ResultSet resStats = DAOCake.getWageStatistics();
		
		Map<Double, Integer> leagueWageStats = new HashMap<Double, Integer>();
		
		try {
			while (resStats.next()){
				leagueWageStats.put(resStats.getDouble("rating"), resStats.getInt("wage"));
			}
			resStats.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return leagueWageStats;
	}
	
	/**
	 * Method that calculates end morale from the amount of change
	 * @param morale (double) the players morale
	 * @param change (double) the amount of "moralepoints" the morale will change
	 * @return  the calculated new morale
	 */
	public static double calcNewMorale(double morale, double change){
		
		//Buy morale. Can't buy for more than 5 morale points at once.
		double moraleRate = getMoraleRate(morale);
//		System.out.println("Morale Rate: " + moraleRate);
		if(change > 0){
			while(change > 3){
				morale += 3 / moraleRate;
				change -= 3;
				moraleRate = getMoraleRate(morale);	
//				System.out.println("morale/dailyChange/moraleRate: " + morale + "/" + change + "/" + moraleRate);
			}
			morale += change / moraleRate;
			change = 0;
		}
		else if(change < 0){
			while(change < -3){
				morale += -3 * moraleRate;
				change += 3;
				moraleRate = getMoraleRate(morale);		
//				System.out.println("morale/dailyChange/moraleRate: " + morale + "/" + change + "/" + moraleRate);
			}
			morale += change * moraleRate;
			change = 0;
		}
		if (morale > 100) morale = 100;
		else if(morale < 1) morale = 1;
		
		morale = Math.round(morale * 100) / 100d;
		
		return morale;
	}
	
	/**
	 * Method that gets the rate morale is bought at
	 * @param morale (double) 
	 */
	public static double getMoraleRate(double morale){
		double moraleRate = 0;
		moraleRate = Math.pow((((double)morale/100)*2), 2.7);
		if(moraleRate < 0.21) moraleRate = 0.21;
		
		moraleRate = Math.round(moraleRate * 100) / 100d;
		
		return moraleRate;
	}
	

	/**
	 * @param id (int) the person_id
	 * @param randomChange (int) how the day has been, from higher than 5 to lower than -5
	 * @return an sql string
	 */
	public static String getRandomMoraleMessage(int id, int randomChange){
		String sql = "";
		if (randomChange > 5){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 1, true, 'My personal life is just perfect at the moment!')";
		}
		else if (randomChange < -5){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 1, true, 'I''ve had some serious problems in my personal life and it''s affecting my motivation to play')";
		}
		else if (randomChange > 2){
			String day = "";
			Random ran = new Random();
			int i = ran.nextInt(14);
			day = getPositiveRandomMoraleMessage(i);
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 1, false, '" + day + "')";
		}
		else if (randomChange < -2){
			String day = "";
			Random ran = new Random();
			int i = ran.nextInt(17);
			day = getNegativeRandomMoraleMessage(i);
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 1, true, '" + day + "')";
		}
		
		return sql;
		
	}
	
	/**
	 * @param wageChange (double) a measure of how much the wage differs from what the player thinks he warrants. The higher the better the player will react
	 * @return an opinion on what the player thinks of his current wage
	 */
	public static String getWageMoraleThoughts(double wageChange){
		String result = "' '";
		if (wageChange > 3){
			result = "'I''m really excited to receive such generous wages.'";
		}
		else if (wageChange > 1){
			result = "'I''m happy with my current wages.'";
		}
		else if (wageChange < -3){
			result = "'My wages are a disgrace and it''s killing my motivation to play.'";
		}
		else if (wageChange < -1){
			result = "'I think i should receive higher wages.'";
		}
		
		return result;		
	}
	
	/**
	 * @param id (int) the players id
	 * @param wageChange (double) a measure of how much the wage differs from what the player thinks he warrants. The higher the better the player will react
	 * @param opinionThoughts (String) an opinion on what the player thinks of his current wage
	 * @return
	 */
	public static String getWageMoraleMessageSql(int id, double wageChange, String opinionThoughts){
		String sql = "";
		if (wageChange > 3){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 2, false, " + opinionThoughts + ")";
		}
		else if (wageChange > 1){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 2, false, " + opinionThoughts + ")";
		}
		else if (wageChange < -3){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 2, true, " + opinionThoughts + ")";
		}
		else if (wageChange < -1){
			sql = "INSERT INTO person_thoughts (person_id, type, negative, msg) VALUES (" + id + ", 2, true, " + opinionThoughts + ")";
		}
		
		return sql;
	}
	

	
	
	/**
	 * @param i (int) a random number 0-12
	 * @return a message about what happened that day
	 */
	private static String getPositiveRandomMoraleMessage(int i){
		String day = "";
		switch(i){
		case 0:
			day = "Bought a new car";
			break;
		case 1:
			day = "The pollen season is over soon";
			break;
		case 2:
			day = "Looks like fine weather today";
			break;
		case 3:
			day = "Got a new pair of cleats";
			break;
		case 4:
			day = "Won at ludo yesterday";
			break;
		case 5: 
			day = "Great hair day";
			break;
		case 6:
			day = "Bought a new pair of earrings";
			break;
		case 7:
			day = "Got a new tattoo";
			break;
		case 8:
			day = "Invincible at training";
			break;
		case 9:
			day = "Bought som dope fireworks. Can''t wait to set them off";
			break;
		case 10:
			day = "Hodor";
			break;
		case 11:
			day = "Had a great party yesteday. Feeling hip!";
			break;
		case 12:
			day = "Grease never gets old";
			break;
		case 13:
			day = "MTV''s Teen Moms is just great, exciting and informative television";
			break;
		}
		return day;
	}
	
	/**
	 * @param i (int) a random number 0-15
	 * @return a message about what happened that day
	 */
	private static String getNegativeRandomMoraleMessage(int i){
		String day = "";
		switch(i){
		case 0:
			day = "The car has been scratched";
			break;
		case 1:
			day = "The dog chewed up my cleats";
			break;
		case 2:
			day = "Ruined my laces just before practice";
			break;
		case 3:
			day = "Had a fight with the missus";
			break;
		case 4:
			day = "Lost on all bets yesterday";
			break;
		case 5: 
			day = "Bad hair day";
			break;
		case 6:
			day = "Ran out of energy drink";
			break;
		case 7:
			day = "Had a stinker of a trainingmatch";
			break;
		case 8:
			day = "The house help just quit";
			break;
		case 9:
			day = "Set the house on fire";
			break;
		case 10:
			day = "Ran out of toothpaste";
			break;
		case 11:
			day = "Wore out my Britney CD";
			break;
		case 12:
			day = "Got stuck in traffic";
			break;
		case 13:
			day = "Feeling a little queasy. Must be something I ate";
			break;
		case 14:
			day = "Joffrey is still alive. So unfair";
			break;
		case 15:
			day = "Lost my phone. Again";
			break;
		case 16:
			day = "Bored as hell last night";
			break;
		}
		return day;
	}
}
