package databases;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * This is the general database superclass.  
 */
public class Database {

	//Information required for the database to login
	protected static final String JDBC_DRIVER = "org.h2.Driver";
	protected static String DB_URL = "jdbc:h2:databases./HappinessProject";
	protected static final String DB_USER = "sa";
	protected static final String DB_PASS = "";
	
	//Variables required for database interaction
	protected static Connection connection = null;
	protected static Statement statement = null;
	
	//names of the database tables 
	protected static String userTable = "users";
	protected static String infoTable = "personalInfo";
	
	//data of the current user
	protected static String currentUsername = "";
	protected static int currentId = -1;
	protected static boolean admin = false;
	
	protected static Date today = null;
	
	/**
	 * This is the constructor that would be making the database object. It attempts to make a 
	 * connection with the database and tells the user if it is working or not.
	 * It is the superclass that has the 
	 */
	public Database()
	{
		//attempts to make a connection to the database
		openConnection();
		
		//creates the queries
		String query = getUserQuery();
		executeQuery(query);
		query = getInfoQuery();
		executeQuery(query);
		today = Date.valueOf(LocalDate.now());
		
	}
	
	/**
	 * Sets up the date that the database is dealing with
	 * @param day
	 */
	private void setDay(Date day) {
		today = day;
	}
	
	/**
	 * returns the query that allows us to delete a table we wish. 
	 * @param table
	 * @return
	 */
	private String getDeleteQuery(String table) {
		return "DROP TABLE "+table;
	}
	
	/**
	 * This is the method that is going to try to run the query that creates the databases.
	 * It would require the query as a parameter. 
	 * @param query
	 */
	protected static void executeQuery(String query){
		try {
			statement.execute(query);
		}
		catch(SQLException e) {
			e.printStackTrace();
	}}
	
	/**
	 * This returns the set that is returned after 
	 * @param query
	 * @return
	 * @throws SQLException
	 */
	protected static ResultSet getSet(String query){
		try {
			return statement.executeQuery(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Something happened in Database.getSet");
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * This is the method that would create database that houses all the users' 
	 * information. Such information include their happiness rating, sleeping, exercise,
	 * socialization info, ect.
	 */
	private String getInfoQuery() {
		//String query = this.getDeleteQuery(infoTable);
		//executeQuery(query);
		
		return "CREATE TABLE IF NOT EXISTS "+infoTable+"("
				+ "id INT, "
				+ "date DATE ,"
				+ "happinessRating INT, "
				+ "sleepInfo INT,"
				+ "exerciseInfo INT, "
				+ "meditationInfo INT, "
				+ "socializeInfo INT,"
				+ "journalEntry CLOB(10K),"
				+ "FOREIGN KEY(id) REFERENCES users(id))";
	}
	
	/**
	 * This is the method that creates the database for the users.
	 */
	private String getUserQuery() {
		return  "CREATE TABLE IF NOT EXISTS "+ userTable +" ("
				+ "id INT AUTO_INCREMENT PRIMARY KEY, "
				+ "username VARCHAR(255) UNIQUE NOT NULL,"
				+ "password VARCHAR(255), "
				+ "isAdmin BOOLEAN"
				+ ")";
	}
	
	/**
	 * This returns the id that is associated with the username
	 * @param username
	 * @return
	 */
	protected static int getId(String username) {
		String query = "SELECT id FROM "+userTable+" WHERE username = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1 , username);
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			return rs.getInt("id");
		}
		catch(SQLException e) {
			System.out.println("Something happened in Database.getId");
			e.printStackTrace();
			return -1;
		}
	}
	
	/**
	 * This is the method that attempts to make a connection to the databse
	 */
	public void openConnection() {
		try
		{
			Class.forName(JDBC_DRIVER); // Load the JDBC driver
			System.out.println("Connecting to database...");
			connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
			statement = connection.createStatement();
			System.out.println("Connection Established Successfully!");
		}
		catch (ClassNotFoundException e)
		{
			// Failed to get JDBC Driver
			System.err.println("JDBC Driver not found: " + e.getMessage());
		}
		catch (SQLException e)
		{
			// Failed to load database
			System.err.println(
					"Failed to establish a connection to database! Error thrown on Database.constructor\n"
							+ e.getMessage());
		}
	}
	
	/**
	 * This is the method that closes the connection between the user and database.
	 */
	public void closeConnection() {
		try{ 
			if(statement!=null) statement.close(); 
		} catch(SQLException se2) { 
			se2.printStackTrace();
		} 
		try { 
			if(connection!=null) connection.close(); 
		} catch(SQLException se){ 
			se.printStackTrace(); 
		} 
	}
}
