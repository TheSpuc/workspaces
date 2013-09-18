package recreatesqlserver;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
public class RecreateDatabase {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			//used to get the input
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));

			//User information to MySQL
			System.out.println("u");
			String u = inLine.readLine(); //username
			System.out.println("p");
			String p = inLine.readLine(); //password

			//creating the connection to MySQL db, using username and password from input.
			Class.forName ("com.mysql.jdbc.Driver");
			Connection minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/", u, p);
			Statement stmt = minConnection.createStatement();
			stmt.executeUpdate("drop database Cars");
			stmt.executeUpdate("create database Cars");

			System.out.println("Database recreated!");
		} catch (Exception e) {
			System.out.println("fejl:  " + e.getMessage());
		}

	}

}
