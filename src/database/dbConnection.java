package database;

import java.sql.*;

public class dbConnection {

	private final String JDBC_Connection = "com.mysql.jdbc.Driver";
	private final String DB_URL = "jdbc:mysql://localhost/";
	
	// DB Credentials
	private String _user = "meteoUser";
	private String _passwd = "ciccio";
	private Connection conn = null;
	private Statement stmt = null;
	
	public dbConnection() {
		
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open a connection
			System.out.println("Crea la connessione");
			conn = DriverManager.getConnection(DB_URL, _user, _passwd);
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public dbConnection(String dbName) {
		
		try {
			// Register JDBC driver
			Class.forName("com.mysql.jdbc.Driver");
			
			// Open a connection
			System.out.println("Crea la connessione");
			conn = DriverManager.getConnection(DB_URL + dbName, _user, _passwd);
		}
		catch(SQLException se) {
			se.printStackTrace();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void dbUpdate(String sql) {
		
		try {
			System.out.println("Execute \"" + sql + "\" command");
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);	
		}		
		catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	
	
	public ResultSet dbQuery (String sql) {
		
		try {
			System.out.println("Execute \"" + sql + "\" command");
			stmt = conn.createStatement();
			stmt.executeUpdate(sql);
			
			ResultSet rs = stmt.executeQuery(sql);
			
			return rs;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		
	}
	
	public void dbClose() {
		try {
			if (stmt != null) stmt.close();
			if (conn != null) conn.close();
		}
		catch (SQLException se) {
			se.printStackTrace();
		}
	}
	
	public void setUser (String user) {
		_user = user;
	}
	
	public String getUser () {
		return _user;
	}
	
	public void setPasswd (String passwd) {
		_passwd = passwd;
	}
	
	public String getPasswd() {
		return _passwd;
	}
}
