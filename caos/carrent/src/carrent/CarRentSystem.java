package carrent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;


public class CarRentSystem {

	public static void main(String[] args){
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

			Connection minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/carrent", u, p);

			//Setting the transaction on the db
			minConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

			//Auto Commit should be off
			minConnection.setAutoCommit(false);

			//Old car information, set the specific car to inactive
			System.out.println("Input the regno for the old car:");
			String oldRegNo = inLine.readLine();

			String oldCarSql = "SELECT carno, active, cartype FROM car WHERE regno = ?;";
			PreparedStatement preStat = minConnection.prepareStatement(oldCarSql);

			preStat.clearParameters();
			preStat.setString(1, oldRegNo);

			String oldCarType = "";
			int oldCarno = 0;
			ResultSet res = preStat.executeQuery();
			if(res.next() && res.getBoolean(2)){
				//making the car inactive
				minConnection.createStatement().execute("UPDATE car SET active = 0 WHERE regno = '" + oldRegNo + "';");
				oldCarType = res.getString(3);
				oldCarno = res.getInt(1);
			}else {
				minConnection.rollback();
				minConnection.close();
				throw new RuntimeException("A car with " + oldRegNo + " dosen't exist");
			}

			System.out.println("The car " + oldRegNo + " is no longer active");


			String insertSql = "INSERT INTO car (regno, cartype, active) VALUES(?,?,?);";
			preStat = minConnection.prepareStatement(insertSql);

			//getting the information
			System.out.println("Please input regno of new car:");
			String regno = inLine.readLine();
			if(regno.length() != 7){
				minConnection.rollback();
				minConnection.close();
				throw new RuntimeException("Please input a proper regno");
			}

			preStat.clearParameters();
			preStat.setString(1, regno);
			preStat.setString(2, oldCarType);
			preStat.setBoolean(3, true);

			//execute insert of new car
			preStat.execute();
			System.out.println("The new car " + regno + " has been added to the db");

			System.out.println("Now updating all booking information, based on new car");

			String updateBooking = "UPDATE booking SET carno = (SELECT carno FROM car WHERE regno = ?) WHERE fromdate >= CURDATE() AND carno = ?;";
			preStat = minConnection.prepareStatement(updateBooking);

			preStat.clearParameters();
			preStat.setString(1, regno);
			preStat.setInt(2, oldCarno);
			preStat.execute();
			
			minConnection.commit();
			System.out.println("SUCCES");

			if(!minConnection.isClosed()){
				minConnection.close();
			}

		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}	
