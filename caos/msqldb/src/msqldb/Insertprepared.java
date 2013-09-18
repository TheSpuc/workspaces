package msqldb;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class insertprepared {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			// Indl?sning
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
			
			// Generel ops?tning
			Connection minConnection;
			Class.forName ("com.mysql.jdbc.Driver");
			minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/cycledb", u, p);
			
			// Anvendelse af prepared statement
			String sql = "INSERT INTO placering VALUES (?,?,?)";
			PreparedStatement prestmt = minConnection.prepareStatement(sql);
			prestmt.clearParameters();
			prestmt.setInt(1,Integer.parseInt(aarstal));
			prestmt.setString(2, initial);
			prestmt.setString(3, placering);
			
			// Udf?rer s?tningen
			prestmt.execute();
			
			// P?nt svar
			System.out.println("Placering er nu registreret");
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
