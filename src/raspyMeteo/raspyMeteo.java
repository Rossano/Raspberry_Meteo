package raspyMeteo;

import java.util.*;
import java.text.*;
import java.sql.ResultSet;
import java.sql.SQLException;

import database.*;
import sensor.*;

public class raspyMeteo {
	
	public static void main (String [] args) throws InterruptedException
	{
		try {
			String desc;
			//DHT11_HumidityTemperatureSensor ghs = new DHT11_HumidityTemperatureSensor();
			
			//desc = ghs.getDescription();
			//System.out.println(desc);
			//ghs.sendCommand();
			//ghs.getData();
			
			Dallas_ds18B20_TempeatureSensor gts = new Dallas_ds18B20_TempeatureSensor();
			gts.setDataBehavior(new ds18B20_Read());
			
			dbConnection db = new dbConnection("HomeWheatherStation");
			
			double temperature;
			//for (int i=0; i<10; i++)
			for (;;)
			{
				temperature = gts.getTemperature();
				System.out.printf("Temperature = %.3f\n", temperature );
				
				Date _date = new Date();
				SimpleDateFormat datef = new SimpleDateFormat("yyyy-MM-dd");
				SimpleDateFormat timef = new SimpleDateFormat("HH:mm:ss");
				String sql = "insert into sensorMeas (tdate, ttime, tTempCh, tHumCh, tTemp, tHum) values (date('" + 
						datef.format(_date) + "'), time('" + timef.format(_date) + "'), 0, 0, " + temperature + ", 0.0);";
				db.dbUpdate(sql); 
//			System.out.println(rs.toString());
//			try {
//				while (rs.next()) {
//					//System.out.println(rs.g)
//				}
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
				Thread.sleep(300000);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println(e.toString());
		}
	}
}
