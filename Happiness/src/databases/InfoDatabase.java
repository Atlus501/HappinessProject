package databases;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.chart.XYChart;

/**
 * This is the abstract class that holds the methods to manipulate the InfoDatabase.
 */
public abstract class InfoDatabase extends Database{

	/**
	 * This is the method that deletes all user data is one chooses to do so. 
	 */
	public static void deleteAllUserData() {
		String query = "DELETE FROM "+ infoTable+" AS i WHERE i.id = "
				+ "(SELECT u.id FROM "+userTable+" AS u WHERE username = "+
				currentUsername+")";
	
			executeQuery(query);
	}
	
	/**
	 * This is the method that checks if the database contains the information that the user inputs 
	 * today.
	 */
	public static boolean containsInfo() {
		String query = "SELECT * FROM "+infoTable+" WHERE id = ? AND date = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, currentId);
			pstmt.setDate(2, today);
			ResultSet rs = pstmt.executeQuery();
			
			return rs.next();
			
		}catch(SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	
	
	/**
	 * This is the methods that updates a section of the 
	 * @param fl
	 */
	public static void updateInfo(int[] fl) {
		String query = "UPDATE "+ infoTable+" AS i SET (happinessRating = ?, "
				+ "sleepInfo = ?, exerciseInfo = ?,"
				+ "meditationInfo = ?, socializeInfo = ?) WHERE (i.id = "+currentId+""
				+ " AND i.date = "+today+")";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, fl[0]);//sets happiness rating
			pstmt.setInt(2, fl[1]);//sets sleepinfo
			pstmt.setInt(3, fl[2]);//sets exerciseinfo
			pstmt.setInt(4, fl[3]);//sets mediationinfo
			pstmt.setInt(5, fl[4]);//sets socializationinfo
			
			int affectedRows = pstmt.executeUpdate();
			
			if(affectedRows == 0)
				insertInfo(fl);
		}
		catch(SQLException e) {
			insertInfo(fl);
		}
	}
	
	/**
	 * This is the method that inserts data into the database
	 * @param fl
	 * @param entry
	 */
	public static void insertInfo(int[] fl) {
		String query = "INSERT INTO "+ infoTable+" (id, date, happinessRating, "
				+ "sleepInfo, exerciseInfo,"
				+ "meditationInfo, socializeInfo) VALUES (?, ?, ?, ?, ?, ?"
				+ ", ?)";
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, getId(currentUsername));
			pstmt.setDate(2, today);
			pstmt.setInt(3, fl[0]);//sets happiness rating
			pstmt.setInt(4, fl[1]);//sets sleepinfo
			pstmt.setInt(5, fl[2]);//sets exerciseinfo
			pstmt.setInt(6, fl[3]);//sets mediationinfo
			pstmt.setInt(7, fl[4]);//sets socializationinfo
			
			pstmt.executeUpdate();
		}
		catch(SQLException e) {
			System.out.println("Something happened in InfoDatabase.insertInfo");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * This is the method that retrieves the avaliable dates that one can pull data from
	 * @return
	 */
	public static ArrayList<Date> retrieveDates() {
		String query = "SELECT date FROM "+infoTable+" WHERE id = ?";
		ArrayList<Date> dates = new ArrayList<Date>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, currentId);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				dates.add(rs.getDate("date"));
			}
			System.out.println(dates);
			return dates;
		}
		catch(SQLException e) {
			System.out.println("Error in InfoDatabase.retrieveDates()");
			e.printStackTrace();
			return dates;
		}
	}

	/**
	 * This is the method that inserts a journal entry into the database
	 * @param entry
	 */
	public static void insertEntry(Date date, String entry) {
		String query = "UPDATE "+infoTable+" SET journalEntry = ? WHERE id = ? AND date = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setClob(1, new StringReader(entry));
			pstmt.setInt(2, currentId);
			pstmt.setDate(3, date);
			
			int affectedRows = pstmt.executeUpdate();
	        
	        if (affectedRows > 0) {
	            System.out.println("|"+date+"|");
	        } else {
	            System.out.println("No entry found with the given id and date");
	            insertBackup(date, entry);
	        }
		}
		catch(SQLException e) {
			e.printStackTrace();
			insertBackup(date, entry);
		}
	}
	
	/**
	 * This is the backup method that triggers in case that updating the entry doesn't work
	 * @param date
	 * @param entry
	 */
	private static void insertBackup(Date date, String entry) {
		String query = "INSERT INTO "+infoTable+" (id, date, journalEntry) VALUES (?, ?, ?)";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, currentId);
			pstmt.setDate(2, date);
			pstmt.setClob(3, new StringReader(entry));
			
			int affectedRows = pstmt.executeUpdate();
	        
	        if (affectedRows > 0) {
	            System.out.println("Entry " + entry + " has been saved");
	            System.out.println(date+"|"+entry);
	        } else {
	            System.out.println("No entry found with the given id and date");
	        }
		}
		catch(SQLException e) {
			System.out.println("Error in InfoDatabase.insertBackup");
			e.printStackTrace();
		}
	}
	
	/**
	 * this is the method that gets the journal entries from a specific date
	 * @param date
	 * @return
	 */
	public static String getEntry(Date date) {
		String query = "SELECT journalEntry FROM "+infoTable+" WHERE id = ? AND date = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, currentId);
			pstmt.setDate(2, date);
			
			ResultSet rs = pstmt.executeQuery();
			rs.next();
			Clob clob = rs.getClob("journalEntry");
			
			if(clob != null)
				return clob.getSubString(1, (int) clob.length());
			else
				return "";
			
		}
		catch(SQLException e) {
			System.out.println("No entry is found in date \n |"+date+"| and user "+currentId);
		//	e.printStackTrace();
			return "";
		}
	}
	
	/**
	 * This is the method that retrieves info from the database
	 * @param date
	 * @return
	 */
	public static ArrayList<Integer> retrieveData(Date date) {
		ArrayList<Integer> loadedAnswers = new ArrayList<Integer>();
		String query = "SELECT * FROM "+ infoTable+" WHERE id = ? AND date = ?";
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setInt(1, currentId);
			pstmt.setDate(2, today);
			
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				loadedAnswers.add(rs.getInt("happinessRating"));
				loadedAnswers.add(rs.getInt("sleepInfo"));
				loadedAnswers.add(rs.getInt("exerciseInfo"));
				loadedAnswers.add(rs.getInt("meditationInfo"));
				loadedAnswers.add(rs.getInt("socializeInfo"));
			}
			
			return loadedAnswers;
		}
		catch(SQLException e) {
			System.out.println("Something happened in InfoDatabase.retrieveData");
			e.printStackTrace();
			return loadedAnswers;
		}
	}
	
	/**
	 * This is the method that supplies the chart with information
	 * @param field
	 * @param date
	 * @param series
	 * @return
	 */
	public static List<String> returnHistory(String field, Date date, 
			XYChart.Series<String, Number> series) {
		String query = "SELECT "+field+", date FROM "+infoTable+" WHERE date >= ? " +
				"AND id = ? ORDER BY date ASC";
		
		List<String> dates = new ArrayList<String>();
		
		try(PreparedStatement pstmt = connection.prepareStatement(query)){
			pstmt.setDate(1, date);
			pstmt.setInt(2, currentId);
			
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
				
				 String thisDate = formatter.format(rs.getDate("date"));
				 
				series.getData().add(new XYChart.Data<String, Number>(
						thisDate, rs.getInt(field)));
				
				dates.add(thisDate);
			}
			
			return dates;
			
		}
		catch(SQLException e) {
			e.printStackTrace();
			return dates;
		}
	}
}
