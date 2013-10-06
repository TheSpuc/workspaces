package data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import model.Settings;
import model.Sysout;

public class DAO {

	private static Connection con;
	private static Statement stmt;

	private DAO(){

	}
	
	public static void closeConnection() throws ClassNotFoundException, SQLException{
		if (!stmt.isClosed()) 
			stmt.close();
		if (!con.isClosed())
			con.close();
	}

	public static void openConnection(String conn) throws ClassNotFoundException, SQLException{
		Class.forName("org.postgresql.Driver");

		if(con == null || con.isClosed()){
			con = DriverManager.getConnection(conn);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
		}
	}
	public static void openConnection() throws ClassNotFoundException, SQLException{

		if(con == null || con.isClosed()){
			Settings s = Settings.getInstance();

			openConnection(s.getSqlConn());

		}
	}
	
	public static void updateDBWithQuery(String sql){
		Statement stmt;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.execute(sql);
			stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static List<LinkedHashMap<String,Object>> updateDBWithQueryReturnGeneratedKeys(String sql){
		Statement stmt;
		ResultSet res = null;
		List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
	    int columns;
	    ResultSetMetaData metadata;
		try {
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);
			stmt.execute(sql, Statement.RETURN_GENERATED_KEYS);
			res = stmt.getGeneratedKeys();
					
			metadata = res.getMetaData();
			columns = metadata.getColumnCount();			
		    while (res.next()) {
		        LinkedHashMap<String,Object> row = new LinkedHashMap<String, Object>(columns);
		        for(int i=1; i<=columns; ++i) {
		            row.put(metadata.getColumnName(i),res.getObject(i));
		        }
		        list.add(row);
		    }
		    stmt.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
	
	public static List<LinkedHashMap<String,Object>> selectFromDBWithQuery(String sql){
		Statement stmt;
		ResultSet res = null;
		try {
			System.out.println("test " + con.toString());
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_UPDATABLE);	
			System.out.println("Statement: ");
			System.out.println(sql);
			res = stmt.executeQuery(sql);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("DAO error: " + e.getMessage());
		}
		
		List<LinkedHashMap<String,Object>> list = new ArrayList<LinkedHashMap<String,Object>>();
	    int columns;
	    ResultSetMetaData metadata;
		try {
			System.out.println("Yes ");
			System.out.println("? :  " + res.toString());
			metadata = res.getMetaData();
			columns = metadata.getColumnCount();			
		    while (res.next()) {
		        LinkedHashMap<String,Object> row = new LinkedHashMap<String, Object>(columns);
		        for(int i=1; i<=columns; ++i) {
		            row.put(metadata.getColumnName(i),res.getObject(i));
		        }
		        list.add(row);
		    }
		    return list;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
}
