package databases;

import java.sql.*;

/**
 * This is the class that houses all the methods relating to users.  
 */
public abstract class UserDatabase extends Database{
	
	/**
	 * Method checks if the database is empty.
	 * @return
	 */
	public static boolean emptyDatabase() {
		String query = "SELECT COUNT(*) AS count FROM "+ userTable;
		try{
			ResultSet rs = statement.executeQuery(query);
			return !rs.next() || rs.getInt("count") == 0;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This is the method that allows the admin to change the role of an user
	 * @param username
	 */
	public static void changeRole(boolean admin, String username) {
		String query = "UPDATE "+userTable+" SET isAdmin = ? WHERE username = ?";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setBoolean(1, admin);
			pstmt.setString(2, username);
			pstmt.executeUpdate();
			}
		catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * This is the method that deletes the user from the database
	 */
	public static boolean deleteUser(String user) {
		String query = "DELETE FROM "+userTable+" WHERE username = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setString(1, user);
			pstmt.executeUpdate();
			return true;
		}
		catch(SQLException e) {
			System.out.println("Something wrong happened in UserDatabase.deletUser()");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This is the method that registers a user into a database. 
	 * @param username
	 * @param password1
	 * @param password2
	 * @return
	 */
	public static boolean register(String username, String password1) {
		String query = "INSERT INTO "+userTable+" (username, password, isAdmin) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password1);
			pstmt.setBoolean(3, emptyDatabase());
			
			pstmt.executeUpdate();
			currentUsername = username;
			currentId = getId(username);
			System.out.println("Account created: "+currentId);
			return true;
		}
		catch(SQLException e) {
			System.out.println("Something wrong happened in UserDatabase.register()");
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * This is the method that checks if the user is able to login or not
	 * @param username
	 * @param password
	 * @return
	 */
	public static boolean login(String username, String password) {
		String query = "SELECT username, password FROM "+ userTable + 
				" WHERE username = ? AND password = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)) {
			pstmt.setString(1, username);
			pstmt.setString(2, password);
			
			ResultSet rs = pstmt.executeQuery();
			currentUsername = username;
			currentId = getId(username);
			return rs.next();
		}
		catch(SQLException e){
			System.out.println("Something wrong happened in UserDatabase.login()");
			e.printStackTrace();
			return false;
		}
	}
	
}
