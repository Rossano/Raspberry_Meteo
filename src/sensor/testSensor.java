package sensor;

import java.util.logging.*;


public class testSensor {

	public static void main(String [] args) throws InterruptedException {
		String desc;
		DHT11_HumidityTemperatureSensor ghs = new DHT11_HumidityTemperatureSensor();
		ghs.setDataBehavior(new DHT11_Read());			// Raspberry Pi
		//ghs.setDataBehavior(new DHT11_Read_PC());		// PC
		
		Logger _log = Logger.getLogger(testSensor.class.getName());
		
		desc = ghs.getDescription();
		System.out.println(desc);
		ghs.sendCommand();
		ghs.getData();
		
//		Dallas_ds18B20_TemperatureSensor gts = new Dallas_ds18B20_TemperatureSensor();
//		gts.setDataBehavior(new ds18B20_Read());		// Raspberry Pi
		//gts.setDataBehavior(new ds18B20_Read_PC());		// PC
				
		double temperature;
		//for (int i=0; i<10; i++)
		for (;;)
		{
//			temperature = gts.getTemperature();
//			System.out.printf("Temperature = %.3f\t", temperature );
			int[] data = new int[5];
			do {				
				data = ghs.getDataBehavior().read(); //ghs.transferData();
				if (data != null) {
					String s1 = String.format("Humidity: %d.%d\n", data[0], data[1]);
					String s2 = String.format("Temperature: %d.%d\n", data[2], data[3]);
//					System.out.printf("Humidity: %d.%d\n", data[0], data[1]);
//					System.out.printf("Temperature: %d.%d\n", data[2], data[3]);
					_log.log(Level.INFO, s1);
					_log.log(Level.INFO, s2);
				}
			}
			while (data == null);
			Thread.sleep(1000);
			//Thread.sleep(300000);
		}
	}
}
