
package mysqldbKonto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


public class KontoTransfer {


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
			//sets that DBMS cant autocommit, so a transaction is possible.
			minConnection.setAutoCommit(false);
			//sets the isolation level, so lost updates is excluded, but if TRANSACTION_REPEATABLE_READ, 
			//is used it wont work, because we are using a shared lock, so other reads are allowed,
			//serializable is also a solution, but a deadlock would arise
			minConnection.setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);


			//preparedStatement for checking account
			//making it easy to select from and to account
			//and getting information needed about the amount of money
			//on both accounts
			String sql = "SELECT saldo FROM konto WHERE kontonr = ?";
			PreparedStatement preStat = minConnection.prepareStatement(sql);

			//Input account to transfer from, thereby checking if the account 
			//actually exists, if not exit system.
			System.out.println("Please input account you want to transfer from: ");
			int fromAccount = Integer.parseInt(inLine.readLine());

			preStat.clearParameters();
			preStat.setInt(1, fromAccount);
			preStat.execute();

			ResultSet res = preStat.getResultSet();
			if(!res.next()){ //if the from account dosen't exist, ResultSet will not have a next
				minConnection.rollback();
				throw new RuntimeException("FromAccount dosen't exist"); //then throw exception				
			}

			//getting the correct amount on account fromAccount
			int moneyFromAccount = res.getInt(1); 

			//Input account to transfer to, thereby checking if the account 
			//actually exists, if not exit system.
			System.out.println("Please input account you want to transfer to: ");
			int toAccount = Integer.parseInt(inLine.readLine());

			preStat.clearParameters();
			preStat.setInt(1, toAccount);
			preStat.execute();

			res = preStat.getResultSet();
			if(!res.next()){ //if the from account dosen't exist, ResultSet will not have a next
				minConnection.rollback();
				throw new RuntimeException("toAccount dosen't exist"); //then throw exception
			}

			//getting the correct amount on account toAccount
			int moneyToAccount = res.getInt(1); 

			//Input of amount of money to transfer, thereby checking if the is enough 
			//money on account from where the money should be transfered
			System.out.println("Please input the amount you wish to transfer: ");
			int amount = Integer.parseInt(inLine.readLine());

			if(moneyFromAccount < amount){ //if amount to transfer bigger then money on the account, System.exit
				minConnection.rollback();
				throw new RuntimeException("Not enough money on account"); //then throw exception
			}

			//For crashing the assignment
			String temp = inLine.readLine();


			//Doing transfer

			//calculating new current amount on from and to account
			moneyFromAccount -= amount; 
			moneyToAccount += amount;

			//creating preparedStatement for updating information
			//for both from and to account, based on amount to transfer
			String transsql = "UPDATE konto SET saldo = ? WHERE kontonr = ?";
			PreparedStatement transPreStat = minConnection.prepareStatement(transsql);

			//To account being changed based on transfer amount
			transPreStat.clearParameters();
			transPreStat.setInt(1, moneyFromAccount);
			transPreStat.setInt(2, fromAccount);
			transPreStat.execute();

			//From account being changed based on transfer amount
			transPreStat.clearParameters();
			transPreStat.setInt(1, moneyToAccount);
			transPreStat.setInt(2, toAccount);
			transPreStat.execute();

			//COMMIT for the transaction
			minConnection.commit();
			System.out.println("SUCCESS");


			//close of connection
			if (!minConnection.isClosed()){
				minConnection.close();
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
