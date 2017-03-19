/**
 * 
 */
package database;

import java.sql.*;
import java.util.Date;
import java.text.SimpleDateFormat;
//import java.math.*;

/**
 * @author rpantale
 *
 */
public class fillTable {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		dbConnection db = new dbConnection("HomeWheatherStation");
		
		for(int i=1; i<101; i++) {
			Date _date = new Date();
			
			SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
			
			double hum = 10 * Math.sin(0.314*i) / (0.314 * i);
			double temp = 10 * (1 - Math.exp(-0.01*i) * Math.sin(0.314*i));
			
			String sql = "insert into sensorMeas (tdate, ttime, tTempCh, tHumCh, tTempData, tHumData) values (date('" +
					datef.format(_date) + "'), time('" + timef.format(_date) + "'), 0, 0, " + temp + ", " + + hum + ");";
			db.dbUpdate(sql);
		}

	}

}
