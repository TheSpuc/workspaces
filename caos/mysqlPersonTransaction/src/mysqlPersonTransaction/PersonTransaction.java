package mysqlPersonTransaction;

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


public class PersonTransaction {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try{
			//used to get the input
			BufferedReader inLine = new BufferedReader(new InputStreamReader(System.in));

			//User information to MySQL
			System.out.println("u");
			String u = inLine.readLine(); //username
			System.out.println("p");
			String p = inLine.readLine(); //password

			//creating the connection to MySQL db, using username and password from input.
			Class.forName ("com.mysql.jdbc.Driver");
			Connection minConnection = DriverManager.getConnection("jdbc:mysql://mmb.hopto.org:25900/eksempeldb", u, p);

			minConnection.setAutoCommit(false);
			minConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);
			
			Statement stmt = minConnection.createStatement();
			
			stmt.execute("START TRANSACTION;");
			
			ResultSet result = stmt.executeQuery("SELECT cpr, navn, stilling, loen FROM person FOR UPDATE;");

			List<Person> persons = new ArrayList<>();

			while(result.next()){
				Person newp = new PersonTransaction.Person(result.getString(1), result.getString(2), result.getString(3), Integer.parseInt(result.getString(4)));
				persons.add(newp);
			}

			System.out.println(persons);

			for(PersonTransaction.Person per : persons){
				System.out.println("Input new loen for " + per.getCpr() + ", " + per.getNavn());
				int newLoen = Integer.parseInt(inLine.readLine());
				per.setLoen(newLoen);
				stmt.execute("UPDATE person SET loen = " + per.getLoen() + " WHERE cpr = " + per.getCpr() + ";");
			}
			stmt.execute("COMMIT;");

			persons.clear();
			result = stmt.executeQuery("SELECT cpr, navn, stilling, loen FROM person;");
			while(result.next()){
				Person newp = new PersonTransaction.Person(result.getString(1), result.getString(2), result.getString(3), Integer.parseInt(result.getString(4)));
				persons.add(newp);
			}
			System.out.println(persons);

			//Close connection
			if(!minConnection.isClosed()) minConnection.close();

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
		}

	}

	private static class Person {

		private String cpr;
		private String navn;
		private String stilling;
		private int loen;

		public Person(String cpr, String navn, String stilling, int loen){
			this.cpr = cpr;
			this.navn = navn;
			this.stilling = stilling;
			this.loen = loen;
		}

		public String getCpr(){
			return cpr;
		}

		public int getLoen(){
			return loen;
		}

		public void setLoen(int loen){
			this.loen = loen;
		}

		public String getNavn(){
			return navn;
		}

		@Override
		public String toString(){
			return "(" +cpr + ", " + navn + ", " + stilling + ", " + loen + ")";
		}
	}

}


