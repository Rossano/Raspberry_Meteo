package raspyMeteo;

import java.util.*;
import java.text.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.*;
import sensor.*;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>Main Meteo Station program<h1>
 * 
 * <p>This program reads the data from the temperature and humidity sensors
 * and stores them into the mysql database<p>
 *  
 */
public class raspyMeteo {
	
	private static final boolean _target_PC = false;
	
	/**
	 * <h3>Main Java program<h3>
	 * 
	 * @param args none
	 * @throws InterruptedException from the GPIO methods
	 */
	public static void main (String [] args) throws InterruptedException
	{
		try {
			String desc;
			//DHT11_HumidityTemperatureSensor ghs = new DHT11_HumidityTemperatureSensor();
			
			//desc = ghs.getDescription();
			//System.out.println(desc);
			//ghs.sendCommand();
			//ghs.getData();
			
			/*
			 *  Defines the ds18B20 object and its reading function
			 */
			Dallas_ds18B20_TemperatureSensor gts = new Dallas_ds18B20_TemperatureSensor();
			// Based on the target device, chose which read method to implement on the 
			// polymorph sensor class
			if (!_target_PC) {
				gts.setDataBehavior(new ds18B20_Read());			// Raspberry Pi
			}
			else {
				gts.setDataBehavior(new ds18B20_Read_PC());			// PC
			}
			
			/*
			 * Defines the mysql database object (Raspberry Pi only)
			 */
			if (!_target_PC) {
				dbConnection db = new dbConnection("HomeWheatherStation");
			}
			double temperature;
			/*
			 * Main program loop
			 */			
			for (;;)
			{
				// reads the temperature and print it on the screen
				temperature = gts.getTemperature();
				System.out.printf("Temperature = %.3f\n", temperature );
				
				// Get the date for the timestamp, take care on the format to be readable by mysql
				Date _date = new Date();
				SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
				// Creates the sql writing string
				String sql = "insert into sensorMeas (tdate, ttime, tTempCh, tHumCh, tTemp, tHum) values (date('" + 
						datef.format(_date) + "'), time('" + timef.format(_date) + "'), 0, 0, " + temperature + ", 0.0);";
				/*
				 * Write the data to the mysql database (Raspberry Pi only)
				 */
				if(!_target_PC) {
					db.dbUpdate(sql);
				} 
//			System.out.println(rs.toString());
//			try {
//				while (rs.next()) {
//					//System.out.println(rs.g)
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
				/*
				 * Waits 5 minutes
				 */
				Thread.sleep(300000);
			}
		} catch (Exception e) {		
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
