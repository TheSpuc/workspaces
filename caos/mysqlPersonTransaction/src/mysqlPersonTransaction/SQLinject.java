package mysqlPersonTransaction;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLinject {

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
			Connection minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/kontodb", u, p);


			//preparedStatement for checking account
			//making it easy to select from and to account
			//and getting information needed about the amount of money
			//on both accounts
			String sql = "SELECT saldo FROM konto WHERE kontonr = 100 OR 1 = 1;";
			Statement stat = minConnection.createStatement();
			

			//Input account to transfer from, thereby checking if the account 
			//actually exists, if not exit system.
			String sqlinject = inLine.readLine();

			ResultSet res = stat.executeQuery("SELECT saldo FROM konto WHERE kontonr = " + sqlinject);
			while(res.next()){ //if the from account dosen't exist, ResultSet will not have a next
				System.out.println(res.getInt(1));
			}


		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.err.println("ERROR");
		} catch (NumberFormatException e){
			e.printStackTrace();
			System.err.println("Please input a number");
		}
	}

}
