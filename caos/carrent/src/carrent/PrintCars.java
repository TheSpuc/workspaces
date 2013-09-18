package carrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class PrintCars {

	private static BufferedReader inLine;

	public static void main(String[] args){
		try {
			//used to get the input
			inLine = new BufferedReader(new InputStreamReader(System.in));

			//User information to MySQL
			System.out.println("u");
			String u = inLine.readLine();
			//username
			System.out.println("p");
			String p = inLine.readLine(); //password

			//creating the connection to MySQL db, using username and password from input.
			Class.forName ("com.mysql.jdbc.Driver");
			Connection minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/carrent", u, p);

			//			printBooking(minConnection);
			insertNewCar(minConnection);


			if(!minConnection.isClosed()){
				minConnection.close();
			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void printBooking(Connection minConnection){
		try {
			String sql = "SELECT bookid, customerid, regno FROM booking WHERE regno = ?";
			PreparedStatement preStmt = minConnection.prepareStatement(sql);

			System.out.println("Input regno:");
			String input  = inLine.readLine();


			preStmt.clearParameters();

			preStmt.setString(1, input);
			ResultSet res = preStmt.executeQuery();

			while(res.next()){
				System.out.println(res.getInt(1) + ", " + res.getInt(2) + ", " + res.getString(3));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void insertNewCar(Connection minConnection){
		try {
			
			//Regno
			String regno = "";
			
			ResultSet res = minConnection.createStatement().executeQuery("SELECT regno FROM car");
			List<String> regnonumbers = new ArrayList<>();
			while(res.next()){
				regnonumbers.add(res.getString(1));
			}
			
			boolean alreadythere = true;
			while(alreadythere){
				System.out.println("Input regno:");
				regno = inLine.readLine();
				if(!regnonumbers.contains(regno)){
					alreadythere = false;
				} else System.err.println("regno is not valid!");
			}
			
			
			//Cartype 
			String cartype = "";
			
			res = minConnection.createStatement().executeQuery("SELECT cartype FROM cartype;"); 
			List<String> cartypes = new ArrayList<>();
			while(res.next()){
				cartypes.add(res.getString(1));
			}
			
			boolean foundcartype = false;
			while(!foundcartype){
				System.out.println("Input cartype");
				cartype = inLine.readLine();
				if(cartypes.contains(cartype)){
					foundcartype = true;
				} else System.err.println("Cartype is not valid!");
			}

			
			//Insertion statement
			String sql = "INSERT INTO car (regno, cartype, active) VALUES(?,?,1);";
			PreparedStatement preStmt = minConnection.prepareStatement(sql);

			preStmt.clearParameters();
			preStmt.setString(1, regno);
			preStmt.setString(2, cartype);
			preStmt.execute();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
