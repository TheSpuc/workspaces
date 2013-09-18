package msqldb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class selectmedparm {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
			// indl?sning
			System.out.println("u");
			String u = inLine.readLine();
			System.out.println("p");
			String p = inLine.readLine();
			System.out.println("Indtast delstreng");
			String in=inLine.readLine();
			
			// generel ops?tning
			Connection minConnection;
			Class.forName ("com.mysql.jdbc.Driver");
			minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/cycledb", u, p);
			Statement stmt = minConnection.createStatement();
			
			// send sql-s?tningen som en streng til db-serveren
			ResultSet res=stmt.executeQuery("SELECT init, aarstal, plac FROM placering WHERE init LIKE '" + in + "%' ");
			
			//genneml?ber svaret
			while (res.next()) {
				String place = res.getString(3);
				if(place.equals("0")){
					System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + "UDGAEET");
				}else System.out.println(res.getString(1) + "\t" + res.getString(2) + "\t" + place);
			}
			
			// p?n lukning
			if (!minConnection.isClosed()) minConnection.close();
		}
		catch (Exception e) {
			System.out.println("fejl:  "+e.getMessage());
		}
	}

}
