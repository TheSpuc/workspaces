package msqldb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class selectudenparm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("u");
			String u = inLine.readLine();
			System.out.println("p");
			String p = inLine.readLine();
		
			//generel ops?tning
			Connection minConnection;
			Class.forName ("com.mysql.jdbc.Driver");
			minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/cycledb", u, p);
			Statement stmt = minConnection.createStatement();
			
			// send sql-s?tningen som en streng til db-serveren
			ResultSet res=stmt.executeQuery("select init, aarstal, placering from placering group by aarstal");
			
			//genneml?ber svaret
			while (res.next()) {
				String s = res.getString("aarstal");
				System.out.println(s + "    " + res.getString(2));
			}
			
			// p?n lukning
	 		if (!minConnection.isClosed()) minConnection.close();
		}
		catch (Exception e) {
			System.out.println("fejl:  "+e.getMessage());
		}
	}
	

}
