package database;

import java.sql.*;

public class testDB {

	public static void main(String [] args) {
		dbConnection db = new dbConnection();
		
		String sql = "use HomeWheatherStation";
		ResultSet rs = db.dbQuery(sql);
		System.out.println(rs.toString());
		try {
			while (rs.next()) {
				//System.out.println(rs.g)
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
