package sensor;


public class testSensor {

	public static void main(String [] args) throws InterruptedException {
		String desc;
		DHT11_HumidityTemperatureSensor ghs = new DHT11_HumidityTemperatureSensor();
		ghs.setDataBehavior(new DHT11_Read());
		
		desc = ghs.getDescription();
		System.out.println(desc);
		ghs.sendCommand();
		ghs.getData();
		
		Dallas_ds18B20_TempeatureSensor gts = new Dallas_ds18B20_TempeatureSensor();
		gts.setDataBehavior(new ds18B20_Read());
		
		double temperature;
		//for (int i=0; i<10; i++)
		for (;;)
		{
			temperature = gts.getTemperature();
			System.out.printf("Temperature = %.3f\t", temperature );
			int[] data = new int[5];
			do {				
				data = ghs.transferData();
				if (data != null) System.out.printf("Humidity: %d.%d\n", data[0], data[1]);
			}
			while (data == null);
			Thread.sleep(300000);
		}
	}
}
