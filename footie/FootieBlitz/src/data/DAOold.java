package data;

import java.awt.Color;
import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.postgresql.util.PSQLException;

import model.Match;
import model.PlayerRole;
import model.Hjaelper;
import model.MatchEngine;
import model.Player;
import model.Settings;
import model.Team;

public class DAOold {

	private static Connection con;
	private static Statement stmt;
	private static int tacticsChangeNumber = 0;	

	private DAOold(){

	}

	public static void closeConnection() throws ClassNotFoundException, SQLException{
		if (!stmt.isClosed()) 
			stmt.close();
		if (!con.isClosed())
			con.close();
	}
	private static void openConnection(String host, String user, String pw) throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");

		String url = "jdbc:postgresql://" + host + "/fb?user=" + user + "&password=" + pw;
		if(con == null || con.isClosed()){
			con = DriverManager.getConnection(url);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		}
	}
	public static void openConnection(String conn) throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");

		if(con == null || con.isClosed()){
			con = DriverManager.getConnection(conn);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		}
	}
	public static void openConnection() throws ClassNotFoundException, SQLException{
		
		if(con == null || con.isClosed()){
			Settings s = Settings.getInstance();
			
		}
	}

	public static void updateShootout(Match match) {
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "UPDATE me_match SET shootoutGoalsHome=" + match.getShootoutGoalsA() + ", shootoutGoalsAway=" + match.getShootoutGoalsB() + " WHERE matchid=" + match.getMatchId() + ";";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void setET(Match match) {
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "UPDATE me_match SET ET='t' WHERE matchid=" + match.getMatchId() + ";";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createArsenalManUtd()throws SQLException{
		
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "";
		//United 1-26
		sql = "UPDATE me_person SET firstName='Edwin', lastName='Van der Sar', acceleration=65, topSpeed=65, " +
		"dribbling=38, strength=84, tackling=65, agility=65, reaction=89, handling=93, " +
		"shooting=35, shotpower=84, passing=50, technique=40, vision=50, marking=50, stamina=50, shotstopping=92 WHERE personId = 1;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Gary', lastName='Neville', acceleration=60, topSpeed=66, " +
		"dribbling=70, strength=76, tackling=78, agility=65, reaction=83, handling=20, " +
		"shooting=50, shotpower=75, passing=69, technique=70, vision=70, marking=85, stamina=65 WHERE personId = 2;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Nemanja', lastName='Vidic', acceleration=71, topSpeed=75, " +
		"dribbling=60, strength=94, tackling=90, agility=68, reaction=82, handling=20, " +
		"shooting=30, shotpower=77, passing=57, technique=68, vision=68, marking=92, stamina=75 WHERE personId = 3;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Rio', lastName='Ferdinand', acceleration=70, topSpeed=75, " +
		"dribbling=75, strength=89, tackling=92, agility=72, reaction=81, handling=20, " +
		"shooting=66, shotpower=80, passing=74, technique=74, vision=72, marking=86, stamina=73 WHERE personId = 4;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Patrice', lastName='Evra', acceleration=89, topSpeed=87, " +
		"dribbling=84, strength=84, tackling=78, agility=82, reaction=87, handling=20, " +
		"shooting=50, shotpower=82, passing=73, technique=81, vision=70, marking=85, stamina=93 WHERE personId = 5;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Ryan', lastName='Giggs', acceleration=88, topSpeed=83, " +
		"dribbling=86, strength=80, tackling=49, agility=82, reaction=73, handling=20, " +
		"shooting=74, shotpower=79, passing=86, technique=88, vision=89, marking=60, stamina=75 WHERE personId = 6;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Michael', lastName='Carrick', acceleration=60, topSpeed=72, " +
		"dribbling=70, strength=80, tackling=60, agility=66, reaction=50, handling=20, " +
		"shooting=69, shotpower=79, passing=84, technique=74, vision=78, marking=60, stamina=72 WHERE personId = 7;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Paul', lastName='Scholes', acceleration=65, topSpeed=68, " +
		"dribbling=76, strength=80, tackling=50, agility=70, reaction=58, handling=20, " +
		"shooting=65, shotpower=86, passing=94, technique=76, vision=92, marking=60, stamina=60 WHERE personId = 8;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Antonio', lastName='Valencia', acceleration=86, topSpeed=92, " +
		"dribbling=78, strength=79, tackling=55, agility=76, reaction=60, handling=20, " +
		"shooting=63, shotpower=69, passing=66, technique=66, vision=74, marking=58, stamina=77 WHERE personId = 9;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Park', lastName='Ji-Sung', acceleration=74, topSpeed=78, " +
		"dribbling=73, strength=72, tackling=62, agility=78, reaction=67, handling=20, " +
		"shooting=57, shotpower=68, passing=64, technique=66, vision=68, marking=62, stamina=96 WHERE personId = 10;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Wayne', lastName='Rooney', acceleration=78, topSpeed=84, " +
		"dribbling=70, strength=86, tackling=56, agility=79, reaction=73, handling=30, " +
		"shooting=75, shotpower=79, passing=75, technique=78, vision=80, marking=58, stamina=78 WHERE personId = 11;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Dimitar', lastName='Berbatov', acceleration=70, topSpeed=76, " +
		"dribbling=78, strength=84, tackling=50, agility=66, reaction=74, handling=20, " +
		"shooting=70, shotpower=74, passing=70, technique=90, vision=87, marking=38, stamina=68 WHERE personId = 12;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Michael', lastName='Owen', acceleration=70, topSpeed=73, " +
		"dribbling=68, strength=65, tackling=35, agility=55, reaction=79, handling=20, " +
		"shooting=70, shotpower=70, passing=52, technique=73, vision=60, marking=47, stamina=72 WHERE personId = 13;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Luis', lastName='Nani', acceleration=93, topSpeed=88, " +
		"dribbling=89, strength=78, tackling=36, agility=88, reaction=73, handling=20, " +
		"shooting=70, shotpower=90, passing=64, technique=70, vision=60, marking=41, stamina=80 WHERE personId = 14;";
		stmt.execute(sql);

		sql = "UPDATE me_person SET firstName='Danny', lastName='Welbeck', acceleration=76, topSpeed=77, " +
		"dribbling=66, strength=72, tackling=40, agility=62, reaction=54, handling=20, " +
		"shooting=68, shotpower=72, passing=50, technique=65, vision=62, marking=42, stamina=72 WHERE personId = 15;";
		stmt.execute(sql);		
		
		sql = "UPDATE me_person SET firstName='M', lastName='Anderson', acceleration=86, topSpeed=85, " +
		"dribbling=78, strength=82, tackling=65, agility=74, reaction=65, handling=20, " +
		"shooting=45, shotpower=60, passing=75, technique=73, vision=75, marking=60, stamina=74 WHERE personId = 16;";
		stmt.execute(sql);

		
		//Arsenal 27-50
		sql = "UPDATE me_person SET firstName='Manuel', lastName='Almunia', acceleration=72, topSpeed=70, " +
		"dribbling=50, strength=88, tackling=50, agility=70, reaction=76, handling=83, " +
		"shooting=50, shotpower=86, passing=60, technique=50, vision=40, marking=50, stamina=50, shotstopping=85 WHERE personId = 27;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Bacary', lastName='Sagna', acceleration=70, topSpeed=80, " +
		"dribbling=74, strength=76, tackling=75, agility=72, reaction=71, handling=50, " +
		"shooting=55, shotpower=70, passing=68, technique=65, vision=60, marking=68, stamina=80 WHERE personId = 28;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Thomas', lastName='Vermaelen', acceleration=65, topSpeed=70, " +
		"dribbling=50, strength=80, tackling=85, agility=65, reaction=68, handling=50, " +
		"shooting=50, shotpower=70, passing=68, technique=62, vision=66, marking=76, stamina=75 WHERE personId = 29;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='William', lastName='Gallas', acceleration=78, topSpeed=72, " +
		"dribbling=60, strength=80, tackling=80, agility=75, reaction=78, handling=50, " +
		"shooting=64, shotpower=75, passing=50, technique=60, vision=70, marking=73, stamina=69 WHERE personId = 30;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Gael', lastName='Clichy', acceleration=84, topSpeed=85, " +
		"dribbling=75, strength=70, tackling=80, agility=78, reaction=75, handling=50, " +
		"shooting=68, shotpower=72, passing=75, technique=68, vision=70, marking=68, stamina=76 WHERE personId = 31;";
		stmt.execute(sql);
		
		
		sql = "UPDATE me_person SET firstName='Philippe', lastName='Senderos', acceleration=70, topSpeed=78, " +
		"dribbling=50, strength=84, tackling=82, agility=74, reaction=78, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=60, marking=74, stamina=70 WHERE personId = 32;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Arsene', lastName='Denilson', acceleration=75, topSpeed=75, " +
		"dribbling=77, strength=72, tackling=78, agility=74, reaction=70, handling=50, " +
		"shooting=72, shotpower=75, passing=80, technique=76, vision=70, marking=68, stamina=72 WHERE personId = 33;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Cesc', lastName='Fabregas', acceleration=72, topSpeed=75, " +
		"dribbling=76, strength=68, tackling=70, agility=76, reaction=74, handling=50, " +
		"shooting=75, shotpower=70, passing=90, technique=88, vision=89, marking=65, stamina=72 WHERE personId = 34;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Samir', lastName='Nasri', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=83, marking=58, stamina=70 WHERE personId = 35;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Andrey', lastName='Arshavin', acceleration=80, topSpeed=78, " +
		"dribbling=88, strength=64, tackling=60, agility=86, reaction=78, handling=50, " +
		"shooting=78, shotpower=85, passing=84, technique=85, vision=74, marking=40, stamina=72 WHERE personId = 36;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Abou', lastName='Diaby', acceleration=80, topSpeed=80, " +
		"dribbling=80, strength=75, tackling=70, agility=70, reaction=70, handling=50, " +
		"shooting=68, shotpower=80, passing=70, technique=75, vision=76, marking=65, stamina=75 WHERE personId = 37;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Thomas', lastName='Rosicky', acceleration=86, topSpeed=84, " +
		"dribbling=88, strength=76, tackling=60, agility=82, reaction=70, handling=50, " +
		"shooting=81, shotpower=88, passing=82, technique=78, vision=75, marking=48, stamina=68 WHERE personId = 38;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='C', lastName='Eduardo', acceleration=80, topSpeed=78, " +
		"dribbling=78, strength=76, tackling=64, agility=78, reaction=80, handling=50, " +
		"shooting=90, shotpower=78, passing=74, technique=80, vision=60, marking=38, stamina=68 WHERE personId = 39;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Robin', lastName='Van Persie', acceleration=75, topSpeed=80, " +
		"dribbling=78, strength=78, tackling=65, agility=74, reaction=75, handling=50, " +
		"shooting=83, shotpower=89, passing=78, technique=80, vision=72, marking=44, stamina=68 WHERE personId = 40;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Theo', lastName='Walcott', acceleration=95, topSpeed=94, " +
		"dribbling=84, strength=68, tackling=65, agility=88, reaction=90, handling=50, " +
		"shooting=70, shotpower=78, passing=70, technique=75, vision=70, marking=40, stamina=75 WHERE personId = 41;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Nicklas', lastName='Bendtner', acceleration=75, topSpeed=83, " +
		"dribbling=50, strength=89, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=82, shotpower=83, passing=50, technique=83, vision=78, marking=41, stamina=69 WHERE personId = 42;";
		stmt.execute(sql);
		
// FC København
		sql = "UPDATE me_person SET firstName='Jesper', lastName='Christiansen', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=84, tackling=50, agility=50, reaction=80, handling=80, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=50, marking=50, stamina=50, shotstopping=80 WHERE personId = 50;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Zdenek', lastName='Pospech', acceleration=75, topSpeed=78, " +
		"dribbling=70, strength=76, tackling=75, agility=67, reaction=72, handling=50, " +
		"shooting=50, shotpower=80, passing=66, technique=68, vision=66, marking=65, stamina=70 WHERE personId = 51;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Ulrik', lastName='Laursen', acceleration=70, topSpeed=70, " +
		"dribbling=50, strength=83, tackling=81, agility=60, reaction=60, handling=50, " +
		"shooting=30, shotpower=78, passing=60, technique=60, vision=60, marking=70, stamina=68, shotstopping=60 WHERE personId = 52;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Peter', lastName='Larsson', acceleration=70, topSpeed=75, " +
		"dribbling=50, strength=80, tackling=72, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=60, marking=70, stamina=68 WHERE personId = 53;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Mikael', lastName='Antonsson', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=60, marking=69, stamina=65 WHERE personId = 54;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Oscar', lastName='Wendt', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=66, marking=65, stamina=74 WHERE personId = 55;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Mathias', lastName='Zanka Jørgensen', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=60, marking=70, stamina=66 WHERE personId = 56;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Hjalte', lastName='Nørregaard', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=68, marking=62, stamina=74 WHERE personId = 57;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='William', lastName='Kvist', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=70, marking=63, stamina=80 WHERE personId = 58;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Jesper', lastName='Grønkjær', acceleration=76, topSpeed=75, " +
		"dribbling=75, strength=68, tackling=50, agility=70, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=72, marking=48, stamina=60 WHERE personId = 59;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Atiba', lastName='Hutchinson', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=68, marking=50, stamina=72 WHERE personId = 60;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Martin', lastName='Vingaard', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=68, marking=58, stamina=74 WHERE personId = 61;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Ailton', lastName='Almeida', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=70, marking=40, stamina=70 WHERE personId = 62;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='César', lastName='Santin', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=50, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=50, passing=50, technique=50, vision=70, marking=38, stamina=74 WHERE personId = 63;";
		stmt.execute(sql);
		
		//	AGF	
		sql = "UPDATE me_person SET firstName='Steffen', lastName='Rasmussen', acceleration=50, topSpeed=50, " +
		"dribbling=50, strength=76, tackling=50, agility=76, reaction=77, handling=74, " +
		"shooting=20, shotpower=75, passing=45, technique=50, vision=40, marking=50, stamina=50, shotstopping=76 WHERE personId = 70;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Frederik', lastName='Krabbe', acceleration=76, topSpeed=77, " +
		"dribbling=74, strength=73, tackling=76, agility=72, reaction=60, handling=50, " +
		"shooting=50, shotpower=70, passing=60, technique=68, vision=65, marking=73, stamina=70 WHERE personId = 71;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Michael', lastName='Lumb', acceleration=72, topSpeed=70, " +
		"dribbling=75, strength=81, tackling=76, agility=70, reaction=67, handling=50, " +
		"shooting=50, shotpower=80, passing=64, technique=67, vision=65, marking=66, stamina=72 WHERE personId = 72;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Mark', lastName='Howard', acceleration=72, topSpeed=75, " +
		"dribbling=65, strength=82, tackling=75, agility=72, reaction=70, handling=50, " +
		"shooting=50, shotpower=80, passing=66, technique=69, vision=67, marking=78, stamina=67 WHERE personId = 73;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Dennis', lastName='Cagara', acceleration=80, topSpeed=75, " +
		"dribbling=82, strength=75, tackling=70, agility=76, reaction=80, handling=50, " +
		"shooting=50, shotpower=75, passing=64, technique=75, vision=60, marking=60, stamina=83 WHERE personId = 74;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Martin', lastName='Jørgensen', acceleration=70, topSpeed=74, " +
		"dribbling=80, strength=75, tackling=67, agility=72, reaction=78, handling=50, " +
		"shooting=60, shotpower=79, passing=78, technique=80, vision=84, marking=60, stamina=60 WHERE personId = 75;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Jacob', lastName='Poulsen', acceleration=74, topSpeed=76, " +
		"dribbling=70, strength=80, tackling=68, agility=60, reaction=60, handling=50, " +
		"shooting=74, shotpower=85, passing=60, technique=60, vision=68, marking=60, stamina=75 WHERE personId = 76;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Benny', lastName='Feilhaber', acceleration=70, topSpeed=75, " +
		"dribbling=60, strength=70, tackling=70, agility=60, reaction=70, handling=50, " +
		"shooting=60, shotpower=70, passing=70, technique=60, vision=70, marking=60, stamina=70 WHERE personId = 77;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Jerry', lastName='Lucena', acceleration=65, topSpeed=68, " +
		"dribbling=50, strength=80, tackling=50, agility=50, reaction=50, handling=50, " +
		"shooting=50, shotpower=70, passing=50, technique=50, vision=60, marking=50, stamina=60 WHERE personId = 78;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Dennis', lastName='Høegh', acceleration=85, topSpeed=80, " +
		"dribbling=70, strength=50, tackling=30, agility=75, reaction=60, handling=50, " +
		"shooting=64, shotpower=72, passing=50, technique=60, vision=57, marking=30, stamina=64 WHERE personId = 79;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Peter', lastName='Graulund', acceleration=68, topSpeed=72, " +
		"dribbling=64, strength=84, tackling=40, agility=63, reaction=76, handling=50, " +
		"shooting=76, shotpower=84, passing=50, technique=60, vision=62, marking=40, stamina=69 WHERE personId = 80;";
		stmt.execute(sql);
		
		sql = "UPDATE me_person SET firstName='Dioh', lastName='Williams', acceleration=80, topSpeed=80, " +
		"dribbling=75, strength=74, tackling=30, agility=70, reaction=66, handling=50, " +
		"shooting=70, shotpower=80, passing=50, technique=75, vision=60, marking=34, stamina=65 WHERE personId = 81;";
		stmt.execute(sql);
		
		System.out.println("Teams done...");
		stmt.close();
	}
	
	public static void createBasicData() throws SQLException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "INSERT INTO me_contract_role (roleid, rolename) values (1, 'Player');";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_contract_role (roleid, rolename) values (2, 'Manager');";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_contract_role (roleid, rolename) values (3, 'Club owner');";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_contract_role (roleid, rolename) values (4, 'Scout');";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_colors (description) values ('red');";
		stmt.execute(sql);

		sql = "INSERT INTO me_colors (description) values ('white');";
		stmt.execute(sql);

		sql = "INSERT INTO me_colors (description) values ('blue');";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_colors (description) values ('black');";
		stmt.execute(sql);

		sql = "INSERT INTO me_league (leagueName, leagueReputation) values (";
		sql += "'Premier League', 9);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_league (leagueName, leagueReputation) values (";
		sql += "'SAS Ligaen', 4);";
		stmt.execute(sql);

		sql = "INSERT INTO me_stadium (stadiumName, seats, terraces) values " +
		"('Old Trafford', 79000, 0);";
		stmt.execute(sql);

		sql = "INSERT INTO me_stadium (stadiumName, seats, terraces) values " +
		"('The Emirates', 60000, 0);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_stadium (stadiumName, seats, terraces) values " +
		"('NRGi Park', 20000, 0);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_stadium (stadiumName, seats, terraces) values " +
		"('Parken', 40000, 0);";
		stmt.execute(sql);

		sql = "INSERT INTO me_club (clubName, leagueId, firstColor, secondColor, stadiumId, money, ownerid) values " +
		"('Manchester United', 1, 1, 2, 1, 20000000, 1);";
		stmt.execute(sql);

		sql = "INSERT INTO me_club (clubName, leagueId, firstColor, secondColor, stadiumId, money, ownerid) values " +
		"('Arsenal', 1, 1, 2, 2, 20000000, 2);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_club (clubName, leagueId, firstColor, secondColor, stadiumId, money, ownerid) values " +
		"('FCK', 2, 2, 4, 3, 5000000, 6);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_club (clubName, leagueId, firstColor, secondColor, stadiumId, money, ownerid) values " +
		"('AGF', 2, 2, 3, 4, 5000000, 4);";
		stmt.execute(sql);
		
		for (int i = 1; i < 101; i++){
			String sp = "'f'";
			if (i % 2 == 0) sp = "'t'";
			sql = "INSERT INTO me_playertactics (personId, dribble, throughballs,";
			sql += " runs, longshots, aggression, mentality,";
			sql += " closingdown, crossball, tightmarking, forwardOnSetpieces) values (" + i + ", 50, 50, 50, 50, 50, 50, 50, 50, 50, " + sp + ");";
			stmt.execute(sql);
		}
		
		final String[] formation532 = {"7,250", "165,468", "155,58", "120,176", "110,342", "100,256", "250,346", "305,266", "405,182", "405,318", "250,168"};
		final int[] roles532 = {0, 4, 4, 2, 2, 3, 8, 8, 7, 10, 10};

		final String[] formation442D = {"7,250", "220,468", "210,58", "174,176", "172,342", "275,256", "323,416", "351,266", "414,182", "412,318", "325,88"};
		final int[] roles442D = {0, 4, 4, 2, 2, 3, 8, 8, 7, 10, 10};
		
		sql = "INSERT INTO me_lineup (clubId, ";
		for (int i = 1; i < 12; i++)
			sql += "pos" + i + ", ";
		for (int i = 1; i < 11; i++)
			sql += "role" + i + ", ";
		sql += "role11) values (1, ";

		for (int i = 0; i < 11; i++)
			sql += "'" + formation532[i] + "', ";
		for (int i = 0; i < 10; i++)
			sql += roles532[i] + ", ";
		sql += roles532[10] + ");";

		stmt.execute(sql);

		sql = "INSERT INTO me_lineup (clubId, ";
		for (int i = 1; i < 12; i++)
			sql += "pos" + i + ", ";
		for (int i = 1; i < 11; i++)
			sql += "role" + i + ", ";
		sql += "role11) values (2, ";

		for (int i = 0; i < 11; i++)
			sql += "'" + formation442D[i] + "', ";
		for (int i = 0; i < 10; i++)
			sql += roles442D[i] + ", ";
		sql += roles442D[10] + ");";

		stmt.execute(sql);
		
		sql = "INSERT INTO me_lineup (clubId, ";
		for (int i = 1; i < 12; i++)
			sql += "pos" + i + ", ";
		for (int i = 1; i < 11; i++)
			sql += "role" + i + ", ";
		sql += "role11) values (3, ";

		for (int i = 0; i < 11; i++)
			sql += "'" + formation532[i] + "', ";
		for (int i = 0; i < 10; i++)
			sql += roles532[i] + ", ";
		sql += roles532[10] + ");";

		stmt.execute(sql);
		
		sql = "INSERT INTO me_lineup (clubId, ";
		for (int i = 1; i < 12; i++)
			sql += "pos" + i + ", ";
		for (int i = 1; i < 11; i++)
			sql += "role" + i + ", ";
		sql += "role11) values (4, ";

		for (int i = 0; i < 11; i++)
			sql += "'" + formation442D[i] + "', ";
		for (int i = 0; i < 10; i++)
			sql += roles442D[i] + ", ";
		sql += roles442D[10] + ");";

		stmt.execute(sql);
		
		for (int i = 1; i < 25; i++){

			sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('Pla', 'yer " + i + "', 26, 50, 50, 50, 50, 50, 50, " +
			"50, 50, 50, 50, 50, 50, 180, 50, 50, " + i + ", 50, 99, 60);";
			stmt.execute(sql);
			
			int a = i;

			sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
			"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values (1, " + a + ", '2009-01-01', '2011-12-31', 10000, " +
			" 500, 250, null, 'true', 'true', 1);";
			stmt.execute(sql);

		}

		for (int i = 25; i < 50; i++){

			sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('Pla', 'yer " + i + "', 26, 50, 50, 50, 50, 50, 50, " +
			"50, 50, 50, 50, 50, 50, 180, 50, 50, " + i + ", 50, 99, 60);";
			stmt.execute(sql);

			int a = i+2;

			sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
			"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values (2, " + a + ", '2009-01-01', '2011-12-31', 10000, " +
			" 500, 250, null, 'true', 'true', 1);";
			stmt.execute(sql);
		}
		for (int i = 50; i < 68; i++){

			sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('Pla', 'yer " + i + "', 26, 50, 50, 50, 50, 50, 50, " +
			"50, 50, 50, 50, 50, 50, 180, 50, 50, " + i + ", 50, 99, 60);";
			stmt.execute(sql);

			int a = i+2;

			sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
			"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values (3, " + a + ", '2009-01-01', '2011-12-31', 10000, " +
			" 500, 250, null, 'true', 'true', 1);";
			stmt.execute(sql);
		}
		for (int i = 68; i < 98; i++){

			sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('Pla', 'yer " + i + "', 26, 50, 50, 50, 50, 50, 50, " +
			"50, 50, 50, 50, 50, 50, 180, 50, 50, " + i + ", 50, 99, 60);";
			stmt.execute(sql);

			int a = i+2;

			sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
			"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values (4, " + a + ", '2009-01-01', '2011-12-31', 10000, " +
			" 500, 250, null, 'true', 'true', 1);";
			stmt.execute(sql);
		}
		
		
		int i = 0;
		sql = "SELECT personId FROM me_contract WHERE clubId=1;";
		ResultSet res = stmt.executeQuery(sql);

		int[] list = new int[19];

		while (res.next() && i < 19){
			list[i] = res.getInt(1);
			i++;
		}

		for (int h = 0; h < 18; h++){
			stmt.execute("UPDATE me_lineup SET pl" + (h+1) + "Id=" + list[h] + " WHERE clubId=1;");
			//if (h < 11)
				//stmt.execute("UPDATE lineup SET pos" + (h+1) + "='" + (50 + h * 20) + ",10' WHERE clubId=1;");
			if (h == 5)
				stmt.execute("INSERT INTO me_teamtactics VALUES (1, " + list[h] + ", " + list[h] + ", " + list[h] + ", " +
						 list[h] + ", "  + list[h] + ", " + list[h] + ", " + list[h] + ", " + list[h] + ", -1);");
		}
		i = 0;
		sql = "SELECT personId FROM me_contract WHERE clubId=2;";
		res = stmt.executeQuery(sql);

		while (res.next() && i < 19){
			list[i] = res.getInt(1);
			i++;
		}

		for (int h = 0; h < 18; h++){
			stmt.execute("UPDATE me_lineup SET pl" + (h+1) + "Id=" + list[h] + " WHERE clubId=2;");
			//if (h < 11)
				//stmt.execute("UPDATE lineup SET pos" + (h+1) + "='" + (50 + h * 20) + ",10' WHERE clubId=2;");
			if (h == 5)
				stmt.execute("INSERT INTO me_teamtactics VALUES (2, " + list[h] + ", " + list[h] + ", " + list[h] + ", " +
						 list[h] + ", "  + list[h] + ", " + list[h] + ", " + list[h] + ", " + list[h] + ", -1);");
		}
		
		i = 0;
		sql = "SELECT personId FROM me_contract WHERE clubId=3;";
		res = stmt.executeQuery(sql);

		while (res.next() && i < 19){
			list[i] = res.getInt(1);
			i++;
		}

		for (int h = 0; h < 18; h++){
			stmt.execute("UPDATE me_lineup SET pl" + (h+1) + "Id=" + list[h] + " WHERE clubId=3;");
			//if (h < 11)
				//stmt.execute("UPDATE lineup SET pos" + (h+1) + "='" + (50 + h * 20) + ",10' WHERE clubId=2;");
			if (h == 5)
				stmt.execute("INSERT INTO me_teamtactics VALUES (3, " + list[h] + ", " + list[h] + ", " + list[h] + ", " +
						 list[h] + ", "  + list[h] + ", " + list[h] + ", " + list[h] + ", " + list[h] + ", -1);");
		}
		stmt.execute("UPDATE me_lineup SET pl1Id=50 WHERE clubId=3;");
		
		i = 0;
		sql = "SELECT personId FROM me_contract WHERE clubId=4;";
		res = stmt.executeQuery(sql);

		while (res.next() && i < 19){
			list[i] = res.getInt(1);
			i++;
		}

		for (int h = 0; h < 18; h++){
			stmt.execute("UPDATE me_lineup SET pl" + (h+1) + "Id=" + list[h] + " WHERE clubId=4;");
			//if (h < 11)
				//stmt.execute("UPDATE lineup SET pos" + (h+1) + "='" + (50 + h * 20) + ",10' WHERE clubId=2;");
			if (h == 5)
				stmt.execute("INSERT INTO me_teamtactics VALUES (4, " + list[h] + ", " + list[h] + ", " + list[h] + ", " +
						 list[h] + ", "  + list[h] + ", " + list[h] + ", " + list[h] + ", " + list[h] + ", -1);");
		}
		stmt.execute("UPDATE me_lineup SET pl1Id=70 WHERE clubId=4;");
		
		System.out.println("Basic data created...");
		stmt.close();
	}
	
	public static void createPlayers() throws SQLException{
		
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
		String sql = "";
		Hjaelper.loadNames("fornavne.txt", "efternavne.txt");
		Random r = new Random();
		
		//Målmand 1
		for (int j = 0; j < 16; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 19, " + (34 + r.nextInt(10)) + ", " + (42 + r.nextInt(10)) + ", " + (18 + r.nextInt(10)) + 
			", " + (72 + r.nextInt(10)) + ", " + (25 + r.nextInt(10)) + ", " + (50 + r.nextInt(10)) + 
			", " + (56 + r.nextInt(10)) + ", " + (60 + r.nextInt(10)) + ", " + (9 + r.nextInt(7)) + 
			", " + (25 + r.nextInt(10)) + ", " + (22 + r.nextInt(10)) + ", " + (30 + r.nextInt(10)) + 
			", " + (175 + r.nextInt(28)) + ", " + (30 + r.nextInt(10)) + ", " + (59 + r.nextInt(10)) + 
			", 10, " + (10 + r.nextInt(10)) + ", 99, " + (18 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (j+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 350, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 'f');";
		stmt.execute(sql);
		}
		
		//Målmand 2
		for (int j = 0; j < 16; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 15, " + (33 + r.nextInt(10)) + ", " + (40 + r.nextInt(10)) + ", " + (12 + r.nextInt(10)) + 
			", " + (45 + r.nextInt(10)) + ", " + (16 + r.nextInt(10)) + ", " + (35 + r.nextInt(10)) + 
			", " + (28 + r.nextInt(10)) + ", " + (48 + r.nextInt(10)) + ", " + (9 + r.nextInt(7)) + 
			", " + (18 + r.nextInt(10)) + ", " + (17 + r.nextInt(10)) + ", " + (15 + r.nextInt(10)) + 
			", " + (170 + r.nextInt(20)) + ", " + (15 + r.nextInt(10)) + ", " + (28 + r.nextInt(10)) + 
			", 10, " + (12 + r.nextInt(10)) + ", 99, " + (10 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (j+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 50, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 'f');";
		stmt.execute(sql);
		}
		
		
		
		//Forsvar 1
		for (int j = 0; j < 16; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 19, " + (44 + r.nextInt(10)) + ", " + (46 + r.nextInt(10)) + ", " + (38 + r.nextInt(10)) + 
			", " + (50 + r.nextInt(10)) + ", " + (48 + r.nextInt(10)) + ", " + (45 + r.nextInt(10)) + 
			", " + (46 + r.nextInt(10)) + ", " + (10 + r.nextInt(10)) + ", " + (10 + r.nextInt(7)) + 
			", " + (25 + r.nextInt(10)) + ", " + (35 + r.nextInt(10)) + ", " + (38 + r.nextInt(10)) + 
			", " + (170 + r.nextInt(28)) + ", " + (40 + r.nextInt(10)) + ", " + (46 + r.nextInt(10)) + 
			", 10, " + (44 + r.nextInt(10)) + ", 99, " + (47 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (j+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 350, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 'f');";
		stmt.execute(sql);
		}
		
		//Forsvar 2
		for (int j = 0; j < 64; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 19, " + (37 + r.nextInt(10)) + ", " + (37 + r.nextInt(10)) + ", " + (24 + r.nextInt(10)) + 
			", " + (31 + r.nextInt(10)) + ", " + (34 + r.nextInt(10)) + ", " + (28 + r.nextInt(10)) + 
			", " + (30 + r.nextInt(10)) + ", " + (10 + r.nextInt(5)) + ", " + (10 + r.nextInt(7)) + 
			", " + (18 + r.nextInt(10)) + ", " + (20 + r.nextInt(10)) + ", " + (21 + r.nextInt(10)) + 
			", " + (160 + r.nextInt(28)) + ", " + (22 + r.nextInt(10)) + ", " + (18 + r.nextInt(10)) + 
			", 10, " + (15 + r.nextInt(15)) + ", 99, " + (25 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		int q = j / 4;
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (q+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 50, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 'f');";
		stmt.execute(sql);
		}
		
		//Midtbane 1
		for (int j = 0; j < 16; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 19, " + (49 + r.nextInt(10)) + ", " + (40 + r.nextInt(10)) + ", " + (42 + r.nextInt(10)) + 
			", " + (39 + r.nextInt(10)) + ", " + (46 + r.nextInt(10)) + ", " + (62 + r.nextInt(10)) + 
			", " + (39 + r.nextInt(10)) + ", " + (10 + r.nextInt(10)) + ", " + (28 + r.nextInt(10)) + 
			", " + (52 + r.nextInt(10)) + ", " + (43 + r.nextInt(10)) + ", " + (36 + r.nextInt(10)) + 
			", " + (162 + r.nextInt(40)) + ", " + (32 + r.nextInt(10)) + ", " + (28 + r.nextInt(10)) + 
			", 10, " + (34 + r.nextInt(10)) + ", 99, " + (24 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}

		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (j+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 350, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 't');";
		stmt.execute(sql);
		}
		
		//Midtbane 2
		for (int j = 0; j < 64; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 15, " + (30 + r.nextInt(10)) + ", " + (28 + r.nextInt(10)) + ", " + (29 + r.nextInt(10)) + 
			", " + (22 + r.nextInt(10)) + ", " + (20 + r.nextInt(10)) + ", " + (31 + r.nextInt(10)) + 
			", " + (25 + r.nextInt(10)) + ", " + (9 + r.nextInt(10)) + ", " + (15 + r.nextInt(10)) + 
			", " + (19 + r.nextInt(10)) + ", " + (37 + r.nextInt(10)) + ", " + (36 + r.nextInt(10)) + 
			", " + (159 + r.nextInt(25)) + ", " + (28 + r.nextInt(10)) + ", " + (15 + r.nextInt(10)) + 
			", 10, " + (17 + r.nextInt(10)) + ", 99, " + (13 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		int q = j / 4;
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (q+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 50, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 't');";
		stmt.execute(sql);
		}
		
		//Angreb 1
		for (int j = 0; j < 16; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 19, " + (54 + r.nextInt(10)) + ", " + (50 + r.nextInt(10)) + ", " + (56 + r.nextInt(10)) + 
			", " + (30 + r.nextInt(10)) + ", " + (14 + r.nextInt(10)) + ", " + (49 + r.nextInt(10)) + 
			", " + (36 + r.nextInt(10)) + ", " + (9 + r.nextInt(10)) + ", " + (50 + r.nextInt(10)) + 
			", " + (46 + r.nextInt(10)) + ", " + (27 + r.nextInt(10)) + ", " + (43 + r.nextInt(10)) + 
			", " + (160 + r.nextInt(40)) + ", " + (47 + r.nextInt(10)) + ", " + (46 + r.nextInt(10)) + 
			", 10, " + (48 + r.nextInt(10)) + ", 99, " + (36 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}

		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (j+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 350, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 't');";
		stmt.execute(sql);
		}
		
		//Angreb 2
		for (int j = 0; j < 32; j++ ){
		sql = "INSERT INTO me_person (firstName, lastName, age, acceleration, topSpeed, " +
			"dribbling, strength, tackling, agility, reaction, handling, shooting, shotpower, " +
			"passing, technique, height, vision, jumping, shirtnumber, heading, energy, marking) values ('" 
			+ Hjaelper.GetRandFirstName() +"', '" + Hjaelper.GetRandLastName() + 
			"', 15, " + (41 + r.nextInt(10)) + ", " + (33 + r.nextInt(10)) + ", " + (27 + r.nextInt(10)) + 
			", " + (22 + r.nextInt(10)) + ", " + (10 + r.nextInt(10)) + ", " + (41 + r.nextInt(10)) + 
			", " + (33 + r.nextInt(10)) + ", " + (10 + r.nextInt(10)) + ", " + (30 + r.nextInt(10)) + 
			", " + (25 + r.nextInt(10)) + ", " + (17 + r.nextInt(10)) + ", " + (36 + r.nextInt(10)) + 
			", " + (159 + r.nextInt(25)) + ", " + (26 + r.nextInt(10)) + ", " + (15 + r.nextInt(10)) + 
			", 10, " + (15 + r.nextInt(10)) + ", 99, " + (10 + r.nextInt(10)) + ");";
		stmt.execute(sql);
		
		int a = 0;
		sql = "SELECT MAX(personID) FROM me_person";
		ResultSet rs = stmt.executeQuery(sql);
		if (rs.next()){
			a = rs.getInt(1);			
		}
		int q = j / 4;
		sql = "INSERT INTO me_contract (clubId, personId, startDate, endDate, weeklyWage, goalBonus, " +
		"assistBonus, minimumReleaseClause, acceptedbyclub, acceptedbyplayer, role) values ("+ (q+7) + ", " 
		+ a + ", '2009-01-01', '2011-12-31', 50, " + " 50, 25, -1, 'true', 'true', 1);";
		stmt.execute(sql);
		
		sql = "INSERT INTO me_playertactics (personid, forwardonsetpieces) values ("+ a + ", 't');";
		stmt.execute(sql);
		}
	}
	
	public static void addPlayerAge(){
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_person SET age = age + 5;";
			stmt.execute(sql);
			stmt.close();
			System.out.println("Age added");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void finishConstructions(){
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM me_construction co INNER JOIN me_club cl ON co.clubid=cl.clubid WHERE finished < now() AND status = 0";
			ResultSet done = stmt.executeQuery(sql);
			while (done.next()){
				System.out.println("ID: " + done.getString("constructionid"));
				
				String object = "training facilities";
				if (done.getInt("type") == 2) object = "stadium";
									
				String msg = "insert into pm_message (author, subject, body, format, timestamp, has_tokens) values (99, 'Construction finished.', 'Construction on your " + object + " has finished and your new facilities are ready to use.', 'filtered_html', date_part('epoch', now())::int, 0);";
				stmt2.execute(msg, Statement.RETURN_GENERATED_KEYS);
				ResultSet resmid = stmt2.getGeneratedKeys();
				if (resmid.next()){
					int mid = resmid.getInt(1);

					msg = "insert into pm_index (mid, thread_id, recipient, is_new, deleted, type) values (" + mid + ", " + mid + ", " + done.getInt("ownerid") + ", 1, 0, 'user');";
					stmt2.execute(msg);
				}
				
				msg = "UPDATE me_construction SET status=1 WHERE constructionid=" + done.getInt("constructionid");
				stmt2.execute(msg);
				
				msg = "UPDATE me_club SET trainingfacc=trainingfacc+1 WHERE clubid=" + done.getInt("clubid");
				stmt2.execute(msg);
				resmid.close();
			}
			
			done.close();
			stmt.close();
			System.out.println("Constructions checked");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void NPCReactToContracts(){
		
		Statement stmt;
		Statement stmt2;
		Statement stmt3;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt3 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "select firstname, lastname, transferfee, age, p.personid, weeklywage, c.signonfee, c.contractid, c.clubid, extract(days from enddate-now()) as days, (SELECT ownerid FROM me_club WHERE clubid=c.clubid) as ownerid from me_person p INNER JOIN me_contract c ON p.personid=c.personid WHERE p.personid NOT IN (SELECT personid FROM me_user_person) AND c.enddate > now() AND c.acceptedbyplayer='f' AND c.acceptedbyclub='t';";
			ResultSet offer = stmt.executeQuery(sql);
			
			while (offer.next()){
				sql = "SELECT clubid, weeklywage, extract(days from enddate-now()) as days FROM me_contract WHERE personid=" + offer.getInt("personid") + " AND acceptedbyplayer='t' AND enddate>now();";
				ResultSet currCon = stmt2.executeQuery(sql);
				
				boolean ok = true;
				String problem = "";
				int currClub = -1;

				if (currCon.next()){ //Spilleren har en nuværende klub
					
					currClub = currCon.getInt("clubid");
					
					//Hvis det er den nuværende klub der tilbyder en ny kontrakt accepteres den baseret på wage
					if (offer.getInt("clubid") == currCon.getInt("clubid")){
						if (offer.getInt("weeklywage") < currCon.getInt("weeklywage") * 1.1 - 2){
							problem = "The offered wage is unacceptable. I would only consider offers with a wage of at least " + Math.round(currCon.getInt("weeklywage") * 1.1) + ". ";
							ok = false;
						}
						if (offer.getInt("days") < currCon.getInt("days") + 73){
							if (problem.length() > 0)
								problem += "I also expect the contract to extend my current contract by at least a season. ";
							else
								problem += "I expect the contract to extend my current contract by at least a season. ";
							
							ok = false;
						}
					}
					else{//Andre klubber
						
						//Accepter udspil der er bedre end nuværende
						if (offer.getInt("weeklywage") < currCon.getInt("weeklywage") * 1.1 - 2){
							problem = "The offered wage is unacceptable. I would only consider offers with a wage of at least " + Math.round(currCon.getInt("weeklywage") * 1.1) + ". ";
							ok = false;
						}
						if (offer.getInt("days") < 73){
							if (problem.length() > 0)
								problem += "I also expect the contract to last at least a season. ";
							else
								problem += "I expect the contract to last at least a season. ";
							
							ok = false;
						}
					}
				}
				else{ //Spilleren har ikke nogen nuværende klub.
					//Kontrakten accepteres hvis den er nogenlunde ok
					
					if (offer.getInt("weeklywage") < offer.getInt("age") / 20 - 2){
						problem = "The offered wage is unacceptable. I would only consider offers with a wage of at least " + Math.round(offer.getInt("age") / 20) + ". ";
						ok = false;
					}
					if (offer.getInt("days") < 73){
						if (problem.length() > 0)
							problem += "I also expect the contract to last at least a season. ";
						else
							problem += "I expect the contract to last at least a season. ";
						
						ok = false;
					}
				}
				
				System.out.println("Kontrakt " + offer.getInt("contractid") + " - ok: " + ok + ", problem: " + problem);

				try {

					con.setAutoCommit(false);

					if(ok){
						String accept = "UPDATE me_contract SET enddate = now() WHERE enddate > now() AND acceptedbyplayer='t' AND personid = " + offer.getInt("personid");
						stmt3.execute(accept);

						accept = "UPDATE me_contract SET acceptedbyplayer='t', startdate=now() WHERE contractid = " + offer.getInt("contractid");
						stmt3.execute(accept);

						if (offer.getInt("transferfee") > 0){
							accept = "UPDATE me_club SET money = money - " + offer.getInt("transferfee") + " WHERE clubid = " + offer.getInt("clubid") + ";";
							stmt3.execute(accept);

							accept = "INSERT INTO me_club_expense (amount, type, date, description, clubid) VALUES (" + offer.getInt("transferfee") + ", 4, now(), 'Transfer fee', " + offer.getInt("clubid") + ");";
							stmt3.execute(accept);

							accept = "UPDATE me_club SET money = money + " + offer.getInt("transferfee") + " WHERE clubid = " + currClub + ";";
							stmt3.execute(accept);

							accept = "INSERT INTO me_club_income (amount, type, date, description, clubid) VALUES (" + offer.getInt("transferfee") + ", 4, now(), 'Transfer fee', " + currClub + ");";
							stmt3.execute(accept);
						}

						if (offer.getInt("signonfee") > 0){
							accept = "UPDATE me_person SET money = money + " + offer.getInt("signonfee") + " WHERE personid = " + offer.getInt("personid") + ";";
							stmt3.execute(accept);

							accept = "UPDATE me_club SET money = money - " + offer.getInt("signonfee") + " WHERE clubid = " + offer.getInt("clubid") + ";";
							stmt3.execute(accept);

							accept = "INSERT INTO me_club_expense (amount, type, date, description, clubid) VALUES (" + offer.getInt("signonfee") + ", 5, now(), 'Sign on fee', " + offer.getInt("clubid") + ");";
							stmt3.execute(accept);
						}

						accept = "DELETE FROM me_contract WHERE acceptedbyplayer='f' AND personid = " + offer.getInt("personid") + ";";
						stmt3.execute(accept);

						String shno = "select COALESCE(min(p.shirtnumber+1), 1) as shn from me_person p inner join me_contract c on c.personid=p.personid where enddate>now() and clubid=" + offer.getInt("clubid") + " and (select count(*) from me_person q inner join me_contract c on c.personid=q.personid where enddate>now() and clubid=" + offer.getInt("clubid") + " and shirtnumber=p.shirtnumber+1)=0 and p.shirtnumber+1<100";
						ResultSet resShNo = stmt3.executeQuery(shno);

						if (resShNo.next()){
							accept = "UPDATE me_person SET shirtnumber=" + resShNo.getInt("shn") + " WHERE personid = " + offer.getInt("personid") + ";";
							stmt3.execute(accept);
						}

						accept = "insert into pm_message (author, subject, body, format, timestamp, has_tokens) values (99, '" + offer.getString("firstname") + " " + offer.getString("lastname") + " accepts contract offer.', '" + offer.getString("firstname") + " " + offer.getString("lastname") + " has accepted your contract offer. He has joined your squad and is available for immediate selection.', 'filtered_html', date_part('epoch', now())::int, 0);";
						stmt3.execute(accept, Statement.RETURN_GENERATED_KEYS);
						ResultSet resmid = stmt3.getGeneratedKeys();
						if (resmid.next()){
							int mid = resmid.getInt(1);

							accept = "insert into pm_index (mid, thread_id, recipient, is_new, deleted, type) values (" + mid + ", " + mid + ", " + offer.getInt("ownerid") + ", 1, 0, 'user');";
							stmt3.execute(accept);
						}
						resmid.close();
						currCon.close();
					}
					else{

						String accept = "insert into pm_message (author, subject, body, format, timestamp, has_tokens) values (99, '" + offer.getString("firstname") + " " + offer.getString("lastname") + " rejects contract offer.', '" + offer.getString("firstname") + " " + offer.getString("lastname") + " has rejected your contract offer adding the following comment: " + problem + "', 'filtered_html', date_part('epoch', now())::int, 0);";
						stmt3.execute(accept, Statement.RETURN_GENERATED_KEYS);
						ResultSet resmid = stmt3.getGeneratedKeys();
						if (resmid.next()){
							int mid = resmid.getInt(1);

							accept = "insert into pm_index (mid, thread_id, recipient, is_new, deleted, type) values (" + mid + ", " + mid + ", " + offer.getInt("ownerid") + ", 1, 0, 'hidden');";
							stmt3.execute(accept);
						}

						accept = "DELETE FROM me_contract WHERE contractid=" + offer.getInt("contractid") + ";";
						stmt3.execute(accept);

						resmid.close();
						currCon.close();
					}

					con.commit();
					con.setAutoCommit(true);

				} catch (SQLException e) {
					try {
						con.rollback();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}

			offer.close();
			stmt.close();
			stmt2.close();
			stmt3.close();
			System.out.println("NPC-contracts done");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void payWages(){
		
		Statement stmt;
		Statement stmt2;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_club c SET money = money - COALESCE((SELECT sum(weeklywage) FROM me_contract WHERE clubid = c.clubid AND enddate>now() AND acceptedbyplayer='t'), 0);";
			stmt.execute(sql);
			sql = "UPDATE me_person p SET money = money + COALESCE((SELECT weeklywage FROM me_contract WHERE personid = p.personid AND enddate>now() AND acceptedbyplayer='t'), 0);";
			stmt.execute(sql);
			
			sql = "SELECT clubid, COALESCE((SELECT sum(weeklywage) FROM me_contract WHERE clubid = c.clubid AND enddate>now() AND acceptedbyplayer='t'), 0) FROM me_club c;";
			ResultSet rs = stmt.executeQuery(sql);
			while (rs.next()){
				sql = "INSERT INTO me_club_expense (clubid, amount, type, date, description) VALUES (" + rs.getInt(1) + ", " + rs.getInt(2) + ", 2, now(), 'Player wages');";
				stmt2.execute(sql);
			}
			stmt.close();
			System.out.println("wages paid");
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getStackTrace());
		}
	
	}
	
	public static void addPlayerEnergy(){
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_person SET energy = energy + 26 WHERE energy < 100;";
			stmt.execute(sql);
			sql = "UPDATE me_person SET energy = 100 WHERE energy > 100;";
			stmt.execute(sql);
			System.out.println("Energy added");
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void payClubMaintenance(){
		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_club c SET money = money - (trainingfacc*trainingfacc*100+(random()*5*trainingfacc)::int + (SELECT ((|/ terraces) + random() * |/terraces * 0.05) * 2 + ((|/ seats) + random() * |/seats * 0.05) * 3 FROM me_stadium WHERE stadiumid = c.stadiumid)::int);";
			stmt.execute(sql);
			sql = "insert into me_club_expense (amount, type, date, description, clubid) (SELECT trainingfacc*trainingfacc*100+(random()*5*trainingfacc)::int, 6, now(),'Training facilities maintenance', clubid FROM me_club);";
			stmt.execute(sql);
			sql = "insert into me_club_expense (amount, type, date, description, clubid) (SELECT ((|/ terraces) + random() * |/terraces * 0.05) * 2 + ((|/ seats) + random() * |/seats * 0.05) * 3, 6, now(),'Stadium maintenance', clubid FROM me_club c INNER JOIN me_stadium s ON c.stadiumid=s.stadiumid);";
			stmt.execute(sql);
			System.out.println("Club maintenance paid");
			stmt.close();
			con.commit();
			System.out.println("Maintenance paid");
			con.setAutoCommit(true);
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
	}
	
	public static void addPlayerPoints(){
		int id = 0;
		try {
			con.setAutoCommit(false);
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String sql = "select clubid, trainingfacc from me_club";
			ResultSet facc = stmt.executeQuery(sql);
			
			HashMap<Integer, Integer> clubFacc = new HashMap<Integer, Integer>();
			while (facc.next()){
				clubFacc.put(facc.getInt("clubid"), facc.getInt("trainingfacc"));
			}
			
			sql = "select *, (select clubid from me_contract where enddate > now() and personid=p.personid and acceptedbyplayer='t') as clubid, (select count(*) from me_person_trait where personid=p.personid and traitid=1) as yg, (select count(*) from me_person_trait where personid=p.personid and traitid=2) as lb from me_person p order by personid asc;";
			ResultSet res = stmt.executeQuery(sql);
			
			double pp = 0;
			int alder = 0;
			
			double pppre = 0;//spillerens pp før udregningen
			Random ran = new Random();
			while (res.next()){ //GÅ GENNEM HVER SPILLER
				
				boolean youngGun = false;
				if (res.getInt("yg") > 0) youngGun = true;
				boolean lateBloomer = false;
				if (res.getInt("lb") > 0) lateBloomer = true;
				int clubid = res.getInt("clubid");
				
				alder = res.getInt(4);
				alder /= 365;
				id = res.getInt(1);
				pppre = res.getDouble(27);
				pp = 0;

				
//				if (youngGun)
//					System.out.println(id + " - yg.");
//				else if (lateBloomer)
//					System.out.println(id + " - lb.");
				
				//Der er flere trin vi skal gennem for at finde det antal PP spilleren skal have
				//1: NATURLIG UDVIKLING (SPILLERENS ALDER) 
				if (alder < 17){
					pp += 0.8;
					if (youngGun){
						pp += 0.5;
					}
					else if (lateBloomer){
						pp -= 0.5;
					}
				}
				else if (alder < 20){
					pp += 0.6;
				}
				else if (alder < 24){
					pp += 0.2;	
					if (youngGun){
						pp -= 0.7;
					}
					else if (lateBloomer){
						pp += 0.7;
					}
				}
				else if (alder < 28){
					pp += 0;
				}
				else if (alder < 32){
					pp -= 1;
				}
				else if (alder < 36){
					pp -= 1.5;
				}
				else{
					pp -= 2.5;
				}

				//FJERN ATTRIBUTE POINTS FRA SPILLEREN HVIS HANS NATURLIGE UDVIKLING ER I MINUS
//				if(pp < 0){
//					pp = Math.round(pp * 100 / 2) / 100.0;
//					String att1 = ""; 
//					String att2 = ""; 
//					boolean fin = false;
//					while(!fin){
//						String att = "";
//						int nr = ran.nextInt(100);
//						if(nr < 15) if(/* spillerens acceleration er større end 10 + pp*/)  att = "acceleration";
//						else if(nr < 30) att = "topspeed";
//						else if(nr < 45) att = "agility";
//						else if(nr < 50) att = "strength";
//						else if(nr < 55) att = "jumping";
//						else if(nr < 60) att = "reaction";
//						else if(nr < 87) att = "stamina";
//						else if(nr < 88) att = "dribbling";
//						else if(nr < 89) att = "shooting";
//						else if(nr < 90) att = "shotpower";
//						else if(nr < 91) att = "passing";			
//						else if(nr < 92) att = "technique";
//						else if(nr < 93) att = "vision";
//						else if(nr < 94) att = "marking";
//						else if(nr < 95) att = "tackling";
//						else if(nr < 96) att = "heading";
//						else if(nr < 97) att = "commandofarea";
//						else if(nr < 98) att = "handling";
//						else if(nr < 99) att = "rushingout";
//						else if(nr <= 100) att = "shotstopping";
//						
//					if(att1 != ""){
//						att2 = att;
//						fin = true;
//					}
//					else att1 = att;
//					}	
//				Her skal spillerens attributes opdateres i databasen
//				
//				
//				pp = 0; Når vi har taget de PP fra atributterne som vi skal, så skal de ikke tages igen.
//				}
				
//				if (youngGun || lateBloomer)
//					System.out.println(pp + " efter alder.");
				//2: TRÆNINGSFACILITETER  (Skal udvides) Indtil videre går vi ud fra et mellemgodt træningsanlæg (5 ud af 10)

				int r;
				
//				r = ran.nextInt(4) + 1;
//				pp += (double)r / 10.0; 
				
				int lvl = 0;
				
				if (clubid > 0)
					lvl = clubFacc.get(clubid);
				
				//level 1:
				if (lvl == 1){
					r = ran.nextInt(4) +1;
					pp += (double)r / 10.0; 
				}				
				//level 2:
				if (lvl == 2){
					r = ran.nextInt(5) +1;
					pp += (double)r / 10.0; 
				}				
				//level 3:
				if (lvl == 3){
					r = ran.nextInt(6) +1;
					pp += (double)r / 10.0; 
				}
				//level 4:
				if (lvl == 4){
					r = ran.nextInt(7) +1;
					if (r>6) r = 6;
					pp += (double)r / 10.0; 
				}
				//level 5:
				if (lvl == 5){
					r = ran.nextInt(8) +1;
					if (r>6) r = 6;
					pp += (double)r / 10.0; 
				}
				//level 6:
				if (lvl == 6){
					r = ran.nextInt(9) +1;
					if (r>6) r = 6;
					pp += (double)r / 10.0; 
				}
				//level 7:
				if (lvl == 7){
					r = ran.nextInt(10) +1;
					if (r>6) r = 6;
					pp += (double)r / 10.0;
				}
				//level 8:
				if (lvl == 8){
					r = ran.nextInt(10) +1;
					if (r>7) r = 7;
					pp += (double)r / 10.0;
				}
				//level 9:
				if (lvl == 9){
					r = ran.nextInt(11) +1;
					if (r>7) r = 7;
					pp += (double)r / 10.0; 
				}
				//level 10:
				if (lvl == 10){
					r = ran.nextInt(12) +1;
					if (r>7) r = 7;
					pp += (double)r / 10.0; 
				}
				r = ran.nextInt(3)+1;//var først ment som en bonus til dem der ikke spillede kampe. men nu blot en bonus til alle
				pp += (double)r / 15.0; 

//				if (youngGun || lateBloomer)
//					System.out.println(pp + " efter træning.");
				
				//3: Training boost....todo (Enkelte spillere vælges ud til at få et træningsboost hvor de får markant flere pp hver dag i et tidsinterval)
				
				
				//4: OPDATER SPILLEREN med de nye pp
				
				//hvis pp er negativ, skal vi have en algoritme der selv skærer ned på spillerens egenskaber
				if (pp < 0){
					
				}
				else{
					pp = Round(pp, 2);
					pppre += pp;
					Statement stmt2 = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
					String sql2 = "UPDATE me_person SET playerpoints = " + pppre + "WHERE personId = " + id + ";";
					stmt2.execute(sql2);
					stmt2.close();
				}
				
			}
			System.out.println("PP added");

			con.commit();
			stmt.close();
			con.setAutoCommit(true);
			res.close();
		} catch (SQLException e) {
			try {
				con.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			System.out.println(e.getStackTrace());
			System.out.println(e.getMessage());
			System.out.println("Last id: " + id);
		}
	}
	
	public static double Round(double Rval, int Rpl) {
		  double p = (double)Math.pow(10,Rpl);
		  Rval = Rval * p;
		  double tmp = Math.round(Rval);
		  return (double)tmp/p;
	}

	public static void deleteAllMatches(){
		try {
			openConnection();

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "DELETE FROM me_match";
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void createMatch(int minsFromNow){

		try {
			openConnection();

			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			Date d = new Date();

			String date = (d.getYear() + 1900) + "-";

			if (d.getMonth() < 9)
				date += "0" + (d.getMonth() + 1);
			else
				date += "" + (d.getMonth() + 1);

			if (d.getDate() < 10)
				date += "-0" + d.getDate();
			else
				date += "-" + d.getDate();

			if (d.getHours() < 10)
				date += " 0" + d.getHours();
			else
				date += " " + d.getHours();	

			if (d.getMinutes() + minsFromNow < 10)
				date += ":0" + (d.getMinutes() + minsFromNow);
			else
				date += ":" + (d.getMinutes() + minsFromNow);	

			if (d.getSeconds() < 10)
				date += ":0" + d.getSeconds();
			else
				date += ":" + d.getSeconds();			

			String sql = "INSERT INTO me_match (status, homeTeamId, awayTeamId, matchDate, stadiumId, homeTeamGoals, " +
			"awayTeamGoals, leagueId) values (0, 3, 4, '" + date + "', 0, 0, 0, 1);";

			stmt.execute(sql);
			stmt.close();

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void fullEnergy(){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_person SET energy=99;";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void clearSetPieceTakers(){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_teamtactics SET freekicklong=-1, freekickshort=-1;";
			stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static boolean findWinner(Match match) throws SQLException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "SELECT findWinner, firstleg, (SELECT hometeamGoals FROM me_match WHERE matchid=m.firstleg) as firstleghome, (SELECT awayteamGoals FROM me_match WHERE matchid=m.firstleg) as firstlegaway FROM me_match m WHERE matchid=" + match.getMatchId()+ ";";
		ResultSet res = stmt.executeQuery(sql);

		boolean result = false;
		
		if (res.next())
			if (res.getString("findWinner").equals("t"))
				match.setMustFindWinner(true);
			else
				match.setMustFindWinner(false);
		
		System.out.println("findwinner: " + match.isMustFindWinner());
		
		if (match.isMustFindWinner() && res.getInt("firstleg") > 0){
			System.out.println(res.getInt("firstleghome"));
			System.out.println(res.getInt("firstlegaway"));
			match.setFirstMatchGoalsA(res.getInt("firstlegaway"));
			match.setFirstMatchGoalsB(res.getInt("firstleghome"));
		}
		
		res.close();
		stmt.close();
		
		return result;
	}
	
	public static int getNextMatchId() throws SQLException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "SELECT matchId, status FROM me_match WHERE status=0 ORDER BY matchDate ASC;";
		ResultSet res = stmt.executeQuery(sql);

		int result = 0;
		
		if (res.next())
			result = res.getInt("matchId");
		
		res.close();
		stmt.close();
		
		return result;
	}

	public static int getLeagueId(int matchId) {

		Statement stmt;
		int result = 0;

		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "SELECT leagueId FROM me_match WHERE matchId=" + matchId + ";";
			ResultSet res = stmt.executeQuery(sql);

			if (res.next())
				result = res.getInt(1);
			else 
				result = 0;
			
			res.close();
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static int getMatchRep(int matchId){
		int matchRep = 0;
		
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "SELECT leaguereputation FROM me_league WHERE leagueid=(SELECT leagueid FROM me_match WHERE matchid=" + matchId + ")";
			ResultSet res = stmt.executeQuery(sql);

			if (res.next())
				matchRep = res.getInt(1);
			
			res.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return matchRep;
	}
	
	public static void closeMatch(Match match, int home, int away, Team homeTeam, Team awayTeam){

		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "UPDATE me_match SET status=2, homeTeamGoals=" + 
			home + ", awayTeamGoals=" + away + " WHERE matchId=" + match.getMatchId() + ";";
			stmt.execute(sql);
			
			
			if (match.getMatchrep() > 0){
				if (away > home){
					sql = "UPDATE me_club SET fame = fame + " + (away * 5 * awayTeam.getLeagueReputation()) + " + ((SELECT fame FROM me_club WHERE clubid=" + homeTeam.getId() + ") / fame * 20 * " + awayTeam.getLeagueReputation() + ") WHERE clubid=" + awayTeam.getId() + ";";
					stmt.execute(sql);
					sql = "UPDATE me_club SET fame = fame + " + (home * 5 * homeTeam.getLeagueReputation()) + " + (fame / (SELECT fame FROM me_club WHERE clubid=" + awayTeam.getId() + ") * -20 * " + homeTeam.getLeagueReputation() + ") WHERE clubid=" + homeTeam.getId() + ";";
					stmt.execute(sql);
				}
				else if (home > away){
					sql = "UPDATE me_club SET fame = fame + " + (away * 5 * awayTeam.getLeagueReputation()) + " + (fame / (SELECT fame FROM me_club WHERE clubid=" + homeTeam.getId() + ") * -20 * " + awayTeam.getLeagueReputation() + ") WHERE clubid=" + awayTeam.getId() + ";";
					stmt.execute(sql);
					sql = "UPDATE me_club SET fame = fame + " + (home * 5 * homeTeam.getLeagueReputation()) + " + ((SELECT fame FROM me_club WHERE clubid=" + awayTeam.getId() + ") / fame * 20 * " + homeTeam.getLeagueReputation() + ") WHERE clubid=" + homeTeam.getId() + ";";
					stmt.execute(sql);
				}
				else{
					sql = "UPDATE me_club SET fame = fame + " + (away * 5 * awayTeam.getLeagueReputation()) + " + ((SELECT fame FROM me_club WHERE clubid=" + homeTeam.getId() + ") / fame * 5 * " + awayTeam.getLeagueReputation() + ") WHERE clubid=" + awayTeam.getId() + ";";
					stmt.execute(sql);
					sql = "UPDATE me_club SET fame = fame + " + (home * 5 * homeTeam.getLeagueReputation()) + " + ((SELECT fame FROM me_club WHERE clubid=" + awayTeam.getId() + ") / fame * 5 * " + homeTeam.getLeagueReputation() + ") WHERE clubid=" + homeTeam.getId() + ";";
					stmt.execute(sql);
				}

				for (Player p : homeTeam.getPlayers()){
					sql = "UPDATE me_person SET energy =" + Math.round(p.getEnergy()) + " WHERE personid=" + p.getId() + ";";
					stmt.execute(sql);
				}
				for (Player p : homeTeam.getUsedSubs()){
					sql = "UPDATE me_person SET energy =" + Math.round(p.getEnergy()) + " WHERE personid=" + p.getId() + ";";
					stmt.execute(sql);
				}
				for (Player p : awayTeam.getPlayers()){
					sql = "UPDATE me_person SET energy =" + Math.round(p.getEnergy()) + " WHERE personid=" + p.getId() + ";";
					stmt.execute(sql);
				}
				for (Player p : awayTeam.getUsedSubs()){
					sql = "UPDATE me_person SET energy =" + Math.round(p.getEnergy()) + " WHERE personid=" + p.getId() + ";";
					stmt.execute(sql);
				}
			}
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void updatePlayerTactics(Player p){
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM me_match_playertactics WHERE personid=" + p.getId();
			ResultSet res = stmt.executeQuery(sql);
			  
			if (res.next()){
				p.setAggression(res.getByte("aggression"));
				p.setClosingdown(res.getByte("closingdown"));
				p.setMentality(res.getByte("mentality"));
				p.setLongshots(res.getByte("longshots"));
				p.setThroughballs(res.getByte("throughballs"));
				p.setDribble(res.getByte("dribble"));
				p.setRuns(res.getByte("runs"));
				p.setCrossball(res.getByte("crossball"));
				p.setPressing(res.getByte("tightmarking"));
				p.setShortLongPassing(res.getByte("passing")); 
				p.setForwardOnSetpieces(res.getBoolean("forwardOnSetPieces"));
			}
			
			res.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Player tactics updated for " + p.getLastname() + ". Mentality: " + p.getMentality());
	}
	
	public static void updateTeamRoles(Team team){
		try {
			Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM me_match_teamtactics WHERE clubId=" + team.getId();
			ResultSet res = stmt.executeQuery(sql);
			
			team.setpCaptain(null);
			team.setpThrowinRight(null);
			team.setpThrowinLeft(null);
			team.setpPenalty(null);
			team.setpFreekickShort(null);
			team.setpFreekickLong(null);
			team.setpCornerRight(null);
			team.setpCornerLeft(null);
			team.setpTargetMan(null);
			
			int captain = 0;
			int throwinright = 0; 
			int throwinleft = 0; 
			int penaltytaker = 0; 
			int freekickshort = 0; 
			int freekicklong = 0; 
			int cornerright = 0; 
			int cornerleft = 0;
			int targetMan = 0;
			  
			if (res.next()){
				captain = res.getInt("captain");
				throwinright = res.getInt("throwinright");
				throwinleft = res.getInt("throwinleft");
				penaltytaker = res.getInt("penaltytaker");
				freekickshort = res.getInt("freekickshort");
				freekicklong = res.getInt("freekicklong");
				cornerright = res.getInt("cornerright");
				cornerleft = res.getInt("cornerleft");
				targetMan = res.getInt("targetman");
			}

			for (Player p : team.getPlayers()){
				if (p.getId() == captain) team.setpCaptain(p);
				if (p.getId() == throwinright) team.setpThrowinRight(p);
				if (p.getId() == throwinleft) team.setpThrowinLeft(p);
				if (p.getId() == penaltytaker) team.setpPenalty(p);
				if (p.getId() == freekickshort) team.setpFreekickShort(p);
				if (p.getId() == freekicklong) team.setpFreekickLong(p);
				if (p.getId() == cornerright) team.setpCornerRight(p);
				if (p.getId() == cornerleft) team.setpCornerLeft(p);
				if (p.getId() == targetMan) team.setpTargetMan(p);
			}
			for (Player p : team.getSubs()){
				if (p.getId() == captain) team.setpCaptain(p);
				if (p.getId() == throwinright) team.setpThrowinRight(p);
				if (p.getId() == throwinleft) team.setpThrowinLeft(p);
				if (p.getId() == penaltytaker) team.setpPenalty(p);
				if (p.getId() == freekickshort) team.setpFreekickShort(p);
				if (p.getId() == freekicklong) team.setpFreekickLong(p);
				if (p.getId() == cornerright) team.setpCornerRight(p);
				if (p.getId() == cornerleft) team.setpCornerLeft(p);
				if (p.getId() == targetMan) team.setpTargetMan(p);
			}
			
			res.close();
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println("Team roles updated. Throw in right: " + team.getpThrowinRight().getLastname());
	}
	
	public static ArrayList<String> getManagerCommands(int matchId, String matchtime){
		ArrayList<String> result = new ArrayList<String>();
		Statement stmt;
		
		String sql = "SELECT * FROM me_manager_commands WHERE matchid=" + matchId + " AND status = 0";
		
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			ResultSet res = stmt.executeQuery(sql);

			while (res.next()){
				result.add(res.getString("command"));
				System.out.println();
				System.out.println("managercommand: " + (new Date()).toString() + " - " + res.getString("command"));
			}
			res.close();
			
			stmt.execute("UPDATE me_manager_commands SET status = 1, statusmsg = 'Collected by match engine', matchtimeused = '" + matchtime + "' WHERE matchId=" + matchId + " AND status = 0");
			
			tacticsChangeNumber--;
			
			stmt.close();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	public static void setMatchStatus(int matchId, int status){

		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "UPDATE me_match SET status=" + status + " WHERE matchId=" + matchId + ";";
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static int[] getTeams(int matchId){
		int result[] = new int[2];

		Statement stmt;
		try {
			openConnection();
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT homeTeamId, awayTeamId FROM me_match WHERE matchId=" + matchId + ";";
			ResultSet res = stmt.executeQuery(sql);
			if (res.next()){
				result[0] = res.getInt(1);
				result[1] = res.getInt(2);
			}
			res.close();
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

	public static void addComment(String comment, int matchId){

		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

			String sql = "INSERT INTO me_comments (matchId, description) VALUES (" + matchId + ", '" + comment + "');";
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


	}

	public static String getNextMatchTime(int id) throws SQLException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "SELECT matchDate FROM me_match WHERE matchId=" + id;
		ResultSet res = stmt.executeQuery(sql);

		String result = "";
		
		if (res.next())
			result = res.getString("matchDate");

		res.close();
		stmt.close();
		
		return result;
	}

	public static void updateMatchStat(int matchId, int clubId, String stat, int value){
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
			String sql = "UPDATE me_matchstats SET " + stat + "=" + value + " WHERE ";
			sql += "matchId=" + matchId + " AND clubId=" + clubId + ";";
			stmt.execute(sql);
//			System.out.println(sql);
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public static void createMatchStats(int matchId, int clubId){
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		
			String sql = "INSERT INTO me_matchstats (matchId, clubId, shots,";
			sql += " ontarget, possession, corners, freekicks,";
			sql += " throwins, fouls, offsides, yellowcards,";
			sql += " redcards) VALUES (" + matchId + ", " + clubId + ", 0,";
			sql += " 0, 0, 0, 0, 0, 0, 0, 0, 0);";
			stmt.execute(sql);
			
			stmt.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

	}
	
	public static void writeEnergyToDB(ArrayList<Player> players){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "";

			for (Player p : players){
				sql += "UPDATE me_person SET energy=" + (int)p.getEnergy() + " WHERE personid=" + p.getId() + "; ";
			}
			
			stmt.execute(sql);
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void dropDB() throws SQLException{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "";

		sql = "DROP DATABASE fb;";
		
		try {
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 
		
		sql = "CREATE DATABASE fb;";
		stmt.execute(sql);
		
		System.out.println("Database deleted and recreated...");

		stmt.close();
	}
	public static void createTablesFromScratch() throws SQLException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "";
		
		try {
			sql = "DROP TABLE me_xp;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_xp (matchid integer, personid integer, matchrating real" +
		", acceleration real, topspeed real, agility real, strength real, jumping real, dribbling real " +
		", tackling real, technique real, heading real, commandofarea real, rushingout real, shotstopping real " + 
		", marking real, handling real, reaction real " +  
		", shotpower real, shooting real, passing real, vision real, stamina real, playerpoints real);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_manager_commands;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_manager_commands (matchid integer, created timestamp, command varchar(1024), status integer, statusmsg varchar(1024), matchtimeused varchar(10), userid integer);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_club_income;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_club_income (id serial, amount int, type int, date timestamp, description varchar(256),";
		sql += " clubid int)";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_club_expense;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_club_expense (id serial, amount int, type int, date timestamp, description varchar(256),";
		sql += " clubid int)";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_teamtactics;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_teamtactics (clubid int, captain int, throwinright int, throwinleft int,";
		sql += " penaltytaker int, freekickshort int, freekicklong int,";
		sql += " cornerright int, cornerleft int, targetman int)";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_match_teamtactics;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_match_teamtactics (clubid int, captain int, throwinright int, throwinleft int,";
		sql += " penaltytaker int, freekickshort int, freekicklong int,";
		sql += " cornerright int, cornerleft int, targetman int)";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_match_playerstats;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_match_playerstats (matchId int, personId int, passattempts int, passessucceeded int, ";
		sql += "dribbles int, dribbleslost int, interceptions int, tackles int, successfultackles int, ";
		sql += "shots int, shotsontarget int, goals int, assists int, crosses int, headers int, clearances int, metersrun int, ";
		sql += "freekickscom int, saveattempts int, saves int, rating int);";
		stmt.execute(sql);
		
		
		try {
			sql = "DROP TABLE me_matchstats;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_matchstats (matchId int, clubId int, shots int,";
		sql += " ontarget int, possession int, corners int, freekicks int,";
		sql += " throwins int, fouls int, offsides int, yellowcards int,";
		sql += " redcards int);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_livematch;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_livematch (id integer NOT NULL, frame text, matchid integer NOT NULL, CONSTRAINT \"pkId\" PRIMARY KEY (id))";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_status;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_status (lastFixtureCheck timestamp, statustext varchar(1024))";
		stmt.execute(sql);
		sql = "INSERT INTO me_status VALUES (now(), 'No input')";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_playertactics;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_playertactics (personId int, dribble int DEFAULT 50, throughballs int DEFAULT 50,";
		sql += " runs int DEFAULT 50, longshots int DEFAULT 50, aggression int DEFAULT 50, mentality int DEFAULT 50,";
		sql += " closingdown int DEFAULT 50, crossball int DEFAULT 50, tightmarking int DEFAULT 50, passing int DEFAULT 50,";
		sql += " forwardOnSetPieces boolean DEFAULT FALSE);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_match_playertactics;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_match_playertactics (personId int, dribble int DEFAULT 50, throughballs int DEFAULT 50,";
		sql += " runs int DEFAULT 50, longshots int DEFAULT 50, aggression int DEFAULT 50, mentality int DEFAULT 50,";
		sql += " closingdown int DEFAULT 50, crossball int DEFAULT 50, tightmarking int DEFAULT 50, passing int DEFAULT 50,";
		sql += " forwardOnSetPieces boolean DEFAULT FALSE);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_person;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		} 

		sql = "CREATE TABLE me_person (personId bigserial, firstName varchar(20), lastName varchar(20),";
		sql += " age int default 15, acceleration real default 15, topSpeed real default 15, dribbling real default 15, marking real default 15, energy real default 100,";
		sql += " strength real default 15, tackling real default 15, agility real default 15, reaction real default 15, handling real default 15,";
		sql += " shooting real default 15, shotpower real default 15, passing real default 15, technique real default 15, height real default 180, vision real default 15,";
		sql += " jumping real default 15, stamina real default 15, shirtnumber real, heading real default 15, money bigint default 0, retired boolean default 'false',";
		sql += " playerpoints real default 0, commandofarea real default 15, shotstopping real default 15, rushingout real default 15);";
		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_comments;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_comments (commentId serial, matchId int, description varchar(1024));";
		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_user_person;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_user_person (id serial, userid int, personid int);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_user_info;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_user_info (userid int, userpoints int default 100);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_colors;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_colors (colorId serial, description varchar(20));";
		stmt.execute(sql);

		
		try {
			sql = "DROP TABLE me_contract_role;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_contract_role (roleid int, rolename varchar(20));";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_contract;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		
		sql = "CREATE TABLE me_contract ";
		sql += "( ";
		sql += "  contractid bigserial NOT NULL, ";
		sql += "  clubid integer, ";
		sql += "  personid integer, ";
		sql += "  startdate date, ";
		sql += "  enddate date, ";
		sql += "  weeklywage integer, ";
		sql += "  goalbonus integer, ";
		sql += "  assistbonus integer, ";
		sql += "  minimumreleaseclause integer DEFAULT (-1), ";
		sql += "  role integer NOT NULL DEFAULT 0, ";
		sql += "  transferfee integer DEFAULT 0, ";
		sql += "  signonfee integer DEFAULT 0, ";
		sql += "  acceptedbyclub boolean DEFAULT false, ";
		sql += "  acceptedbyplayer boolean DEFAULT false ";
		sql += ") ";
		sql += "WITH ( ";
		sql += " OIDS=FALSE ";
		sql += "); ";
		
		try {
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		
		try {
			sql = "DROP TABLE me_club;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_club (clubId serial, clubName varchar(50), leagueid int, seatPrice int default 5, standPrice int default 5," +
		"firstColor int, secondColor int, stadiumId int, money bigint, ownerid bigint, managerid bigint, trainingfacc int DEFAULT 1, fame int default 2000)";
		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_club_league;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_club_league (clubId int, leagueid int)";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_league;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_league (leagueId serial, leagueName varchar(50), leagueReputation int default 1);";
		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_stadium;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_stadium (stadiumId serial, stadiumName varchar(50), seats int, terraces int);";
		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_match;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		sql = "CREATE TABLE me_match (matchId bigserial, status int, homeTeamId int, awayTeamId int,";
		sql += " matchDate timestamp, stadiumId int, homeTeamGoals int, awayTeamGoals int, leagueId int, attendance int, findWinner boolean default false, ";
		sql += "firstLeg int, ET boolean default false, shootoutGoalsHome int, shootoutGoalsAway int);";
		stmt.execute(sql);
		
		try {
			sql = "DROP TABLE me_lineup;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		
		sql = "CREATE TABLE me_lineup (lineupId serial, clubId serial, ";

		for (int i = 1; i < 19; i++)
			sql += "pl" + i + "Id int, ";

		for (int i = 1; i < 12; i++)
			sql += "role" + i + " int, ";

		for (int i = 1; i < 11; i++)
			sql += "pos" + i + " varchar(10), ";

		sql += "pos11 varchar(10));";

		stmt.execute(sql);

		try {
			sql = "DROP TABLE me_match_lineup;";
			stmt.execute(sql);
		} catch (PSQLException psql){
			System.out.println(psql.getMessage()); 
		}
		
		sql = "CREATE TABLE me_match_lineup (matchId int, clubId int, ";

		for (int i = 1; i < 19; i++)
			sql += "pl" + i + "Id int, ";

		for (int i = 1; i < 4; i++)
			sql += "subTime" + i + " int, ";
		
		for (int i = 1; i < 4; i++)
			sql += "subOff" + i + " int, ";

		for (int i = 1; i < 3; i++)
			sql += "subOn" + i + " int, ";

		sql += "subOn3 int);";

		stmt.execute(sql);
		
		System.out.println("Tables created...");

		stmt.close();
	}
	
	public static ArrayList<Player> loadPlayers(Team team) throws SQLException, ClassNotFoundException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "SELECT * FROM me_lineup WHERE clubId=" + team.getId();
		ResultSet res = stmt.executeQuery(sql);

		int[] lineup = new int[16];
		String strLineup = "";
		int[] roles = new int[11];
		String[] positions = new String[11];

		if (res.next()){
			for (int i = 0; i < 16; i++){
				strLineup += res.getInt(i+3) + ",";
				lineup[i] = res.getInt(i+3);
				if (i < 11) {
					roles[i] = res.getInt("role" + (i+1));
					positions[i] = res.getString("pos" + (i+1));
				}
			}
		}
		while (strLineup.endsWith(",") || strLineup.endsWith("-"))
			strLineup = strLineup.substring(0, strLineup.length() - 2);
		
		try{
			String q = "INSERT INTO me_match_playertactics (SELECT * FROM me_playertactics WHERE personid IN (" + strLineup + "));";
//			System.out.println(q);
			executeSimpleStatement(q);
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		Team.validateLineup(positions);
		
		sql = "SELECT * FROM me_teamtactics WHERE clubId=" + team.getId();
		res = stmt.executeQuery(sql);
		
		int captain = 0;
		int throwinright = 0; 
		int throwinleft = 0; 
		int penaltytaker = 0; 
		int freekickshort = 0; 
		int freekicklong = 0; 
		int cornerright = 0; 
		int cornerleft = 0;
		int targetMan = 0;
		  
		if (res.next()){
			captain = res.getInt("captain");
			throwinright = res.getInt("throwinright");
			throwinleft = res.getInt("throwinleft");
			penaltytaker = res.getInt("penaltytaker");
			freekickshort = res.getInt("freekickshort");
			freekicklong = res.getInt("freekicklong");
			cornerright = res.getInt("cornerright");
			cornerleft = res.getInt("cornerleft");
			targetMan = res.getInt("targetman");
		}
		
		ArrayList<Player> players = new ArrayList<Player>();
		//		
		for (int i = 0; i < 16; i++){
			sql = "SELECT * FROM me_person WHERE personId=" + lineup[i];
			res = stmt.executeQuery(sql);

			if (res.next()){
				
				Player p = new Player();
				p.setId(res.getInt("personId"));
				p.setFirstname(res.getString("firstName"));
				p.setLastname(res.getString("lastName"));
				p.setAcceleration(res.getDouble("acceleration"));
				p.setTopSpeed((res.getDouble("topSpeed")*0.75) + 96);				
				p.setDribbling(res.getDouble("dribbling"));
				p.setStrength(res.getDouble("strength"));
				p.setTackling(res.getDouble("tackling"));
				p.setAgility(res.getDouble("agility"));
				p.setReaction(res.getDouble("reaction"));
				p.setHandling(res.getDouble("handling"));
				p.setShooting(res.getDouble("shooting"));
				p.setShotpower(res.getDouble("shotPower"));
				p.setPassing(res.getDouble("passing"));
				p.setTechnique(res.getDouble("technique"));
				p.setHeight(res.getDouble("height"));
				p.setVision(res.getDouble("vision"));
				p.setJumping(res.getDouble("jumping"));
				p.setStamina(res.getDouble("stamina"));
				p.setShirtNumber(res.getInt("shirtnumber"));
				p.setHeading(res.getDouble("heading")); 
				p.setMarking(res.getDouble("marking"));
				p.setEnergy(res.getDouble("energy")); 
				p.setCommandOfArea(res.getDouble("commandofarea"));
				p.setShotstopping(res.getDouble("shotstopping"));
				
				//taktik
				sql = "SELECT * FROM me_playertactics WHERE personId=" + res.getInt("personId");
				ResultSet tactics = stmt.executeQuery(sql);
				
				p.setStart_acceleration(p.getAcceleration());
				p.setStart_agility(p.getAgility());
				p.setStart_dribbling(p.getDribbling());
				p.setStart_Energy(p.getEnergy());
				p.setStart_handling(p.getHandling());
				p.setStart_heading(p.getHeading());
				p.setStart_passing(p.getPassing());
				p.setStart_jumping(p.getJumping());
				p.setStart_marking(p.getMarking());
				p.setStart_reaction(p.getReaction());
				p.setStart_shooting(p.getShooting());
				p.setStart_shotpower(p.getShotpower());
				p.setStart_strength(p.getStrength());
				p.setStart_tackling(p.getTackling());
				p.setStart_technique(p.getTechnique());
				p.setStart_topSpeed(p.getTopSpeed());
				p.setStart_vision(p.getVision());
				p.setStart_commandofarea(p.getCommandOfArea());
				
				
				if (tactics.next()){
					p.setAggression(tactics.getByte("aggression"));
					p.setClosingdown(tactics.getByte("closingdown"));
					p.setMentality(tactics.getByte("mentality"));
					p.setLongshots(tactics.getByte("longshots"));
					p.setThroughballs(tactics.getByte("throughballs"));
					p.setDribble(tactics.getByte("dribble"));
					p.setRuns(tactics.getByte("runs"));
					p.setCrossball(tactics.getByte("crossball"));
					p.setPressing(tactics.getByte("tightmarking"));
					p.setShortLongPassing(tactics.getByte("passing")); 
					p.setForwardOnSetpieces(tactics.getBoolean("forwardOnSetPieces"));
				}
				
				if (i < 11){
					String s = positions[i];
					String pos[] = s.split(",");

					p.setStartPosX(Integer.parseInt(pos[0]));
					p.setStartPosY(Integer.parseInt(pos[1]));

					if (i==0)
						p.setRole(PlayerRole.GK);
					else
						p.setRole(PlayerRole.CM);
				}
				else{
//					System.out.println(p.getShirtNumber());
					p.setStartPosX(250 + i * 10);
					p.setStartPosY(-20);
					p.setRole(PlayerRole.SUB);
				}
				
				if (p.getId() == captain) team.setpCaptain(p);
				if (p.getId() == throwinright) team.setpThrowinRight(p);
				if (p.getId() == throwinleft) team.setpThrowinLeft(p);
				if (p.getId() == penaltytaker) team.setpPenalty(p);
				if (p.getId() == freekickshort) team.setpFreekickShort(p);
				if (p.getId() == freekicklong) team.setpFreekickLong(p);
				if (p.getId() == cornerright) team.setpCornerRight(p);
				if (p.getId() == cornerleft) team.setpCornerLeft(p);
				if (p.getId() == targetMan) team.setpTargetMan(p);
				
				p.setMyTeam(team);
				players.add(p);
				
				if (tactics != null) tactics.close();
			}
		}
		
		if (res != null) res.close();
		if (stmt != null) stmt.close();

		return players;
	}

	public static void executeSimpleStatement(String statement) throws SQLException{
		
		if (stmt.isClosed())
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		try {
			stmt.execute(statement);
		} catch (PSQLException psql){
			psql.printStackTrace();
		} 
		
//		stmt.close();
	}
	
	public static int[] getStadiumAndTicketInfo(int clubid){
		int[] result = new int[4];
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String sql = "SELECT * FROM me_club c INNER JOIN me_stadium s ON s.stadiumid=c.stadiumid WHERE clubId=" + clubid;
			ResultSet res = stmt.executeQuery(sql);
			
			if (res.next()){
				result[0] = res.getInt("seatPrice");
				result[1] = res.getInt("standPrice");
				result[2] = res.getInt("seats");
				result[3] = res.getInt("terraces");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}
	
	public static int[] getBonus(int personid){
		int[] result = new int[2];
		
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String sql = "SELECT * FROM me_contract WHERE personid=" + personid + " AND enddate>now() AND acceptedbyplayer='t'";
			ResultSet res = stmt.executeQuery(sql);
			
			if (res.next()){
				result[0] = res.getInt("goalbonus");
				result[1] = res.getInt("assistbonus");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return result;
	}
	
	public static void startSeason() throws SQLException, ClassNotFoundException{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "UPDATE me_club SET money = money + 40000;";
		stmt.execute(sql);
		stmt.close();
	}
	
	public static void startCompetition(int id) throws SQLException, ClassNotFoundException{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "UPDATE me_club SET fame = fame + (SELECT leagueReputation*leagueReputation*100 FROM me_league WHERE leagueid=" + id + ") WHERE clubid IN (SELECT clubid FROM me_club_league WHERE leagueid=" + id + ";";
		stmt.execute(sql);
		stmt.close();
	}
	
	public static void endCompetition(int id, int winner) throws SQLException, ClassNotFoundException{
		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "UPDATE me_club SET fame = fame + (SELECT leagueReputation*leagueReputation*200 FROM me_league WHERE leagueid=" + id + ") WHERE clubid=" + winner + ";";
		stmt.execute(sql);
		stmt.close();
	}
	
	public static Team loadTeam(int id, MatchEngine m) throws SQLException, ClassNotFoundException{

		Statement stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);

		String sql = "SELECT * FROM me_club c INNER JOIN me_league l ON l.leagueid=c.leagueid WHERE clubId=" + id;
		ResultSet res = stmt.executeQuery(sql);

		String navn = "";
		Color color1 = Color.BLACK;
		Color color2 = Color.BLACK;
		int rep;
		int fame;
		int userId;
		
		Team result = null;
		
		if (res.next()) {
			navn = res.getString("clubName");
			rep = res.getInt("leagueReputation");
			color1 = getColor(res.getInt("firstColor"));
			color2 = getColor(res.getInt("secondColor"));
			fame = res.getInt("fame");
			if (res != null) res.close();
			if (stmt != null) stmt.close();

			result = new Team(navn, color1, color2, id, m, rep, fame);
		}
		if (res != null) res.close();
		if (stmt != null) stmt.close();
		
		executeSimpleStatement("INSERT INTO me_match_teamtactics (SELECT * FROM me_teamtactics WHERE clubid = " + id + ");");
		
		return result;
	}

	public static Color getColor(int i){

		Color result = Color.BLACK;

		switch (i){

		case 1:
			result = Color.red;
			break;

		case 2:
			result = Color.white;
			break;

		case 3:
			result = Color.blue;
			break;

		}
		return result;
	}
	
	private static String getStringFromDate(Date d){
		String date = (d.getYear() + 1900) + "-";

		if (d.getMonth() < 9)
			date += "0" + (d.getMonth() + 1);
		else
			date += "" + (d.getMonth() + 1);

		if (d.getDate() < 10)
			date += "-0" + d.getDate();
		else
			date += "-" + d.getDate();

		if (d.getHours() < 10)
			date += " 0" + d.getHours();
		else
			date += " " + d.getHours();	

		if (d.getMinutes() < 10)
			date += ":0" + (d.getMinutes());
		else
			date += ":" + (d.getMinutes());	

		if (d.getSeconds() < 10)
			date += ":0" + d.getSeconds();
		else
			date += ":" + d.getSeconds();	
		
		return date;
	}
	
	public static void genFixtureList(int leagueId, int startYear, int startMonth, int startDay, int startHour, int minutesInterval){
		Statement stmt;
		
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			String sql = "SELECT * FROM me_club WHERE leagueID=" + leagueId;
			ResultSet res = stmt.executeQuery(sql);
			Map<Integer, Integer> clubStadium = new HashMap<Integer, Integer>();
			Map<Integer, String> clubName = new HashMap<Integer, String>();
			ArrayList<Integer> index = new ArrayList<Integer>();
			
			while (res.next()) {
				clubName.put(res.getInt("clubID"), res.getString("clubname"));
				clubStadium.put(res.getInt("clubID"), res.getInt("stadiumID"));
				index.add(res.getInt("clubID"));
				System.out.println(res.getInt("clubID"));
			}
			res.close();
			
			//Byt random mellem alle klubber så vi har tilfældig startrækkefølge
			Random r = new Random();
			for (Integer i = 0; i < index.size(); i++){
				int q = r.nextInt(index.size());
				Integer temp = index.get(i);
				index.set(i, index.get(q));
				index.set(q, temp);
			}
			
//			debug
//			index.clear();
//			for (int i = 1; i < 17; i++){
//				index.add(i);
//				clubName.put(i, "Klub" + i);
//			}
			
			boolean swap = false;
			
			for (Integer j = 0; j < index.size() - 1; j++){

				ArrayList<Integer> min1 = new ArrayList<Integer>();
				ArrayList<Integer> min2 = new ArrayList<Integer>();
				
				for (int q = 0; q < index.size() / 2; q++){
					min1.add(q * minutesInterval);
					min2.add(q * minutesInterval);
				}
				
				for (Integer i = 0; i < index.size() / 2; i++){

					swap = !swap;
					
					if ((i == 0 || i == 1) && (j%2 == 0)){ //hvis det er første kamp i runden byttes den kun hver anden runde
						swap = !swap;
					}

//					if (swap) System.out.println("runde " + (j+1) + " kamp " + (i+1) + " byttes.");
					
					int size = min1.size();
					Date d = new Date(startYear - 1900, startMonth - 1, startDay + j * 2, startHour, min1.remove(r.nextInt(size)));
					int home = index.get(i);
					int away = index.get(index.size() - 1 - i);
					
					if (!swap){
//						System.out.println(d.toString() + ":\t" + clubName.get(index.get(i)) + " - " + clubName.get(index.get(index.size() - 1 - i)));
					}
					else {
//						System.out.println(d.toString() + ":\t" + clubName.get(index.get(index.size() - 1 - i)) + " - " + clubName.get(index.get(i)));
						home = index.get(index.size() - 1 - i);
						away = index.get(i);
					}
					
					sql = "INSERT INTO me_match (status, homeTeamId, awayTeamId, matchDate, stadiumId, homeTeamGoals, " +
					"awayTeamGoals, leagueId) values (0, " + home + ", " + away + ", '" + getStringFromDate(d) + "', " + clubStadium.get(home) + ", 0, 0, " + leagueId + ");";

					stmt.execute(sql);
					
					System.out.println(d.toString() + ":\t" + clubName.get(index.get(i)) + " - " + clubName.get(index.get(index.size() - 1 - i)));
					size = min2.size();
					d = new Date(startYear - 1900, startMonth - 1, startDay + j * 2 + (index.size() - 1) * 2 + 7, startHour, min2.remove(r.nextInt(size)));
					System.out.println(d.toString() + ":\t" + clubName.get(index.get(index.size() - 1 - i)) + " - " + clubName.get(index.get(i)));
					
					sql = "INSERT INTO me_match (status, homeTeamId, awayTeamId, matchDate, stadiumId, homeTeamGoals, " +
					"awayTeamGoals, leagueId) values (0, " + away + ", " + home + ", '" + getStringFromDate(d) + "', " + clubStadium.get(away) + ", 0, 0, " + leagueId + ");";

					stmt.execute(sql);
				}
				System.out.println();
				index.add(1, index.remove(index.size() - 1));
			}
			
			stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void switchDatabases(){
		HashMap<String, String> aendredeKolonner = new HashMap<String, String>();
		HashMap<String, String> nyeKolonner = new HashMap<String, String>(); 
		
		//club_expenses
		aendredeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_club_expense", "club_expenses", "id", aendredeKolonner, nyeKolonner);		
		System.out.println("me_club_expense done");
		
		//club_incomes
		aendredeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_club_income", "club_incomes", "id", aendredeKolonner, nyeKolonner);		
		System.out.println("me_club_income done");
				
		//finance_type		
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_finance_type", "finance_types", "type", aendredeKolonner, nyeKolonner);		
		System.out.println("me_finance_type done");
		
		//leagues
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_league", "leagues", "leagueid", aendredeKolonner, nyeKolonner);
		System.out.println("me_league done");
		
		//colors
		aendredeKolonner.clear();
//		switchTables("me_colors", "colors", "colorid", aendredeKolonner, nyeKolonner);
		System.out.println("me_colors done");
		
		//users
		aendredeKolonner.clear();
		aendredeKolonner.put("name", "username");
		aendredeKolonner.put("pass", "password");
		aendredeKolonner.put("mail", "email");
		switchTables("users", "users", "uid", aendredeKolonner, nyeKolonner);
		System.out.println("users done");
		
		//clubs
		aendredeKolonner.clear();
		nyeKolonner.clear();
		nyeKolonner.put("created", "now()");
		nyeKolonner.put("user_id", "1");
		nyeKolonner.put("shortname", "'FCK'");
//		switchTables("me_club", "clubs", "clubid", aendredeKolonner, nyeKolonner);
		System.out.println("me_club done");
		
		//person
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_person", "persons", "personid", aendredeKolonner, nyeKolonner);
		System.out.println("me_person done");
		
		//contract_roles
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_contract_role", "contract_roles", "roleid", aendredeKolonner, nyeKolonner);
		System.out.println("me_contract_role done");
		
		//contract
		aendredeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
		aendredeKolonner.put("personid", "person_id");
		nyeKolonner.clear();
//		switchTables("me_contract", "contracts", "contractid", aendredeKolonner, nyeKolonner);
		System.out.println("me_contract done");
		
		//constructions
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_construction", "constructions", "constructionid", aendredeKolonner, nyeKolonner);
		System.out.println("me_construction done");
		
		//friendly_inv
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_friendly_inv", "friendly_inv", "finvid", aendredeKolonner, nyeKolonner);
		System.out.println("me_friendly_inv done");
		
		//lineups
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_lineup", "lineups", "lineupid", aendredeKolonner, nyeKolonner);
		System.out.println("me_lineup done");
		
		//stadium
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_stadium", "stadiums", "stadiumid", aendredeKolonner, nyeKolonner);
		System.out.println("me_stadium done");
		
		//match
		aendredeKolonner.clear();
		nyeKolonner.clear();
		nyeKolonner.put("shootoutGoalsHome", "0");
		nyeKolonner.put("shootoutGoalsAway", "0");
		nyeKolonner.put("findwinner", "f");
//		nyeKolonner.put("firstleg", "t");
		nyeKolonner.put("et", "f");
		aendredeKolonner.put("stadiumid", "stadium_id");
		aendredeKolonner.put("leagueid", "league_id");
//		switchTables("me_match", "matches", "matchid", aendredeKolonner, nyeKolonner);
		System.out.println("me_match done");
		
		//matchlineups
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
		aendredeKolonner.put("matchid", "match_id");
//		switchTables("me_match_lineup", "match_lineups", "mlid", aendredeKolonner, nyeKolonner);
		System.out.println("me_lineup done");
		
		//traits
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_trait", "traits", "traitid", aendredeKolonner, nyeKolonner);
		System.out.println("me_trait done");
		
		//teamtactics
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_teamtactics", "teamtactics", "ttid", aendredeKolonner, nyeKolonner);
		System.out.println("me_teamtactics done");
		
		//seasons
		aendredeKolonner.clear();
		nyeKolonner.clear();
//		switchTables("me_season", "seasons", "seasonid", aendredeKolonner, nyeKolonner);
		System.out.println("me_season done");
		
		//playertactics
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("personid", "person_id");
//		switchTables("me_playertactics", "playertactics", "ptid", aendredeKolonner, nyeKolonner);
		System.out.println("me_playertactics done");
		
		//playertactics
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("personid", "person_id");
		aendredeKolonner.put("traitid", "trait_id");
//		switchTables("me_person_trait", "person_trait", "ptid", aendredeKolonner, nyeKolonner);
		System.out.println("me_person_trait done");
		
		//matchstats
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
		aendredeKolonner.put("matchid", "match_id");
//		switchTables("me_matchstats", "matchstats", "msid", aendredeKolonner, nyeKolonner);
		System.out.println("me_matchstats done");
		
		//match_playerstats
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("personid", "person_id");
		aendredeKolonner.put("matchid", "match_id");
//		switchTables("me_match_playerstats", "match_playerstats", "mpid", aendredeKolonner, nyeKolonner);
		System.out.println("me_match_playerstats done");
		
		//match_teamtactics
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("clubid", "club_id");
//		switchTables("me_match_teamtactics", "match_teamtactics", "ttid", aendredeKolonner, nyeKolonner);
		System.out.println("me_match_teamtactics done");
		
		//match_playertactics
		aendredeKolonner.clear();
		nyeKolonner.clear();
		aendredeKolonner.put("personid", "person_id");
//		switchTables("me_match_playertactics", "match_playertactics", "mpid", aendredeKolonner, nyeKolonner);
		System.out.println("me_match_playertactics done");

		System.out.println("All done.");
	}
	
	public static void switchTables(String gammelTabel, String nyTabel, String IDKolonne, HashMap<String, String> AendredeKolonner, HashMap<String, String> NKolonner){
		Statement stmt;
		Statement stmt2 = null;
		Statement stmt3 = null;
//		String url = "jdbc:postgresql://localhost/cake_footiesite?user=footieman&password=Lommen";
		String url = "jdbc:postgresql://localhost/cake_footiesite?user=postgres&password=Lommen";
		try{
		Connection concake = DriverManager.getConnection(url);
		stmt2 = concake.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		stmt3 = concake.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
		}catch (SQLException e) {
			e.printStackTrace();
		}
		try{
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			
			String sql = "";
//			String sql = "TRUNCATE " + nyTabel + " CASCADE;";
//			stmt2.execute(sql);
			
			sql = "SELECT * FROM " + gammelTabel + ";";
			ResultSet res = stmt.executeQuery(sql);
			ResultSet resKtjek = stmt3.executeQuery("SELECT * FROM " + nyTabel + ";");
			ResultSetMetaData rmd2 = resKtjek.getMetaData();
			ArrayList<String> nyeKOlonner = new ArrayList<String>();
			for (int i = 1; i <= rmd2.getColumnCount(); i++)
				nyeKOlonner.add(rmd2.getColumnName(i));
			
			ResultSetMetaData rmd = res.getMetaData();
			int cols = rmd.getColumnCount();

			while (res.next()) {

				try{
					sql = "INSERT INTO " + nyTabel + " (id, ";
					for (int i = 1; i <= cols; i++){
						if (nyeKOlonner.contains(rmd.getColumnName(i)) && !rmd.getColumnName(i).toLowerCase().equals("id")){
							sql += rmd.getColumnName(i);
							sql += ", ";
						}
					}

					for(String s : AendredeKolonner.values()){
						sql += s + ", ";
					}
					for(String s : NKolonner.keySet()){
						sql += s + ", ";
					}
					
					sql = sql.substring(0, sql.length() - 2);
					sql += ") values (" + res.getString(IDKolonne) + ", ";

					for (int i = 1; i <= cols; i++){
						if (nyeKOlonner.contains(rmd.getColumnName(i)) && !rmd.getColumnName(i).toLowerCase().equals("id") && rmd.getColumnName(i).toLowerCase() != IDKolonne){
							
							res.getString(i);
							if (rmd.getColumnType(i) == java.sql.Types.VARCHAR || rmd.getColumnType(i) == java.sql.Types.DATE || rmd.getColumnType(i) == java.sql.Types.TIMESTAMP || rmd.getColumnType(i) == -7)
								if (!res.wasNull()) sql += "'";
							
							sql += res.getString(i);
							
							if (rmd.getColumnType(i) == java.sql.Types.VARCHAR || rmd.getColumnType(i) == java.sql.Types.DATE || rmd.getColumnType(i) == java.sql.Types.TIMESTAMP || rmd.getColumnType(i) == -7)
								if (!res.wasNull()) sql += "'";
							
							sql += ", ";
						}
					}

					for(String s : AendredeKolonner.keySet()){
						int columnID = -1;
						for (int i = 1; i <= cols; i++){
							if (rmd.getColumnName(i).toLowerCase().equals(s.toLowerCase())){
								columnID = i;
							}
						}
						if (columnID == -1)
							System.out.println(s + " kunne ikke findes");
						
						
						res.getString(s);
						if (rmd.getColumnType(columnID) == java.sql.Types.VARCHAR || rmd.getColumnType(columnID) == java.sql.Types.DATE || rmd.getColumnType(columnID) == java.sql.Types.TIMESTAMP || rmd.getColumnType(columnID) == -7
								&& !res.getString(s).equals("null"))
							if (!res.wasNull()) sql += "'";
						
						sql += res.getString(s);
						
						if (rmd.getColumnType(columnID) == java.sql.Types.VARCHAR || rmd.getColumnType(columnID) == java.sql.Types.DATE || rmd.getColumnType(columnID) == java.sql.Types.TIMESTAMP || rmd.getColumnType(columnID) == -7
								&& !res.getString(s).equals("null")){
							if (!res.wasNull()) sql += "'";
						}

						sql += ", ";
					}
					for(String s : NKolonner.values()){
						sql += s + ", ";
					}

					sql = sql.substring(0, sql.length() - 2);
					sql += ");";

					System.out.println(sql);
					stmt2.execute(sql);
				}
				catch (Exception e){
					e.printStackTrace();
				}
			}
			res.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

}
