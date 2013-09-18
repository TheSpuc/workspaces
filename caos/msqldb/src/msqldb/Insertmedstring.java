package msqldb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class insertmedstring {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try {
			// indl?sning
			System.out.println("Vi vil nu oprette et ny placering");
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("u");
			String u = inLine.readLine();
			System.out.println("p");
			String p = inLine.readLine();
			System.out.println("Indtast aarstal: ");
			String aarstal=inLine.readLine();
			System.out.println("Indtast initialer (rytter skal vaere oprettet, med initialer)");
			String initial=inLine.readLine();
			System.out.println("Indtast placering (0 for udgaet)");
			String placering=inLine.readLine();
		
			// generel ops?tning
			Connection minConnection;
			Class.forName ("com.mysql.jdbc.Driver");
			minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/cycledb", u, p);
			Statement stmt = minConnection.createStatement();
			
			// sender insert'en til db-serveren
			stmt.execute("insert into placering values (" + aarstal + ",'" + initial + "'," + placering + ");" );
			
			// p?nt svar til brugeren
			System.out.println("Placeringen er nu registreret");
			if (!minConnection.isClosed()) minConnection.close();
		}
		catch (SQLException e) {
			switch (e.getErrorCode())
			// fejl-kode 1452 svarer til en foreign key fejl
			{ case 1452 : {if (e.getMessage().indexOf("rytterinit")!= -1)
				System.out.println("Foreign key fejl ved rytterinit");
			if (e.getMessage().indexOf("aarstal")!= -1)
				System.out.println("Foreign key fejl ved aarstal");
			break;
			}
			// fejl-kode 1062 svarer til primary key fejl
			case 1062: {System.out.println("Primary key fejl");
			break;
			}
			default: System.out.println("fejlSQL:  "+e.getMessage());
			};
		}
		catch (Exception e) {
			System.out.println("fejl:  "+e.getMessage());
		}
	}

}
