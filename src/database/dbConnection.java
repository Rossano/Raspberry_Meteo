package database;

import java.sql.*;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>dbConnection Class<h1>
 * 
 * <p>Class to connect to a mysql database for the meteo station. This could be improved to be a 
 * more generic abstract class to be declined to each final database.<p>
 *
 */
public class dbConnection {

	// Database main credentials, mysql is stored on the localhost
	private final String JDBC_Connection = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/";
	
	// DB Credentials
	private String _user = "meteoUser";
	private String _passwd = "ciccio";
	private Connection conn = null;
	private Statement stmt = null;
	
	/**
	 * <h3>dbConnection Constructor<h3>
	 * <p>Default constructor.
	 * It register the database, and tries to connect on it with the given credentials, which are
	 * by default for the time being.
	 * If it fails it throws an exception. <p>
	 * 
	 * @param none
	 */
	public dbConnection() {
		
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open a connection
			System.out.println("Crea la connessione");
			conn = DriverManager.getConnection(DB_URL, _user, _passwd);
		}
		/*
		 * Exception to throw in case of errors
		 */
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <h3>dbConnection Construction<h3>
	 * <p>Construction.
	 * It register the database specified in @param, and tries to connect on it with the given credentials, which are
	 * by default for the time being.
	 * If it fails it throws an exception. <p>
	 * 
	 * @param dbName string with the database name.
	 */
	public dbConnection(String dbName) {
		
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open a connection
			System.out.println("Crea la connessione");
			conn = DriverManager.getConnection(DB_URL + dbName, _user, _passwd);
		}
		/*
		 * Exceptions to throw in case of errors.
		 */
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * <h3>dbUpdate Method<h3>
	 * <p>Method to make a query on the mysql database. The query is specified in the sql parameter.
	 * In case of error an exception is thrown.
	 * 
	 * @param sql string containing the query for the database.
	 * @return nothing
	 */
	public void dbUpdate(String sql) {
		
		/*
		 * The query is inside a try/catch block to throw exception in case of errors
		 */
		try {
			System.out.println("Execute \"" + sql + "\" command");
			/*
			 * Create the mysql query statement and execute the query
			 */
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);	
		}		
		/*
		 * Throw exception in case of error
		 */
		catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	
	/**
	 * <h3>dbQuery Method<h3>
	 * <p>Method to perform the mysql query specified in the string sql, and returns the result set
	 * with the records of the query.<p>
	 * 
	 * @param sql string implementing the query.
	 * @return ResultSet with the record resulting from the query.
	 */
	public ResultSet dbQuery (String sql) {
		
		/*
		 * Query is inside a try/catch block to throw exception.
		 */
		try {
			System.out.println("Execute \"" + sql + "\" command");
			/*
			 * Create the mysql query statement and execute the query. 
			 */
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			// Return the ResultSet
			ResultSet rs = stmt.executeQuery(sql);
			
			return rs;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	
	/**
	 * <h3>dbClose Method<h3>
	 * <p>Method to close the mysql connection.<p>
	 * 
	 * @param none
	 * @return nothing
	 */
	public void dbClose() {
		try {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	/**
	 * <h3>setUser Method<h3>
	 * <p>Method to set the private member _user which stores the mysql user.<p>
	 * 
	 * @param user string containing the user of the database
	 * @return none
	 */
	public void setUser (String user) {
		_user = user;
	}
	
	/**
	 * <h3>getUser Method<h3>
	 * <p>Method to return the user stored in the private member _user.<p>
	 * 
	 * @param none
	 * @return String containing the _user
	 */
	public String getUser () {
		return _user;
	}
	
	/**
	 * <h3>setPasswd Method<h3>
	 * <p>Method to set the private member _passwd containing the database password.
	 * 
	 * @param passwd string containing the database password to set
	 * @return nothing
	 */
	public void setPasswd (String passwd) {
		_passwd = passwd;
	}
	
	/**
	 * <h3>getPasswd Method<h3>
	 * <p>Method to return the content of the _passwd private member which stores the mysql 
	 * database password.<p>
	 * 
	 * @param none
	 * @return string with the content of the _passwd
	 */
	public String getPasswd() {
		return _passwd;
	}
}
