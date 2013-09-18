package msqldb;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class MySQLJavaArrayList {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));
			System.out.println("u");
			String u = inLine.readLine();
			System.out.println("p");
			String p = inLine.readLine();
			
			Connection minConnection;
			Class.forName ("com.mysql.jdbc.Driver");
			minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/cycledb", u, p);
			Statement stat = minConnection.createStatement();
			
			ResultSet re = stat.executeQuery("SELECT aarstal, init, plac FROM placering;");
			
			List<String> result = new ArrayList<>();
			while(re.next()){
				result.add(re.getString(1) + " " + re.getString(2) + " " + re.getString(3));
			}
			
			for(String s : result){
				System.out.println(s);
			}
			if (!minConnection.isClosed()) minConnection.close();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
