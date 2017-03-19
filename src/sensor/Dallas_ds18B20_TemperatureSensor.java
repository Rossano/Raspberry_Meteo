package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * @see sensor
 * 
 * <h1> Class Dallas_ds18B20_TemperatureSensor<h1>
 * <p>
 * Class that implements the Dallas DS18B20 temperature sensor driver.
 * 
 *  This class extends the sensor abstract class. It implements the abstract methods defined in sensor class
 *  and adds a new method to read the temperature from the sensor.
 *  <p>
 *  
 */
public class Dallas_ds18B20_TemperatureSensor extends sensor {

	/**
	 * <h3>Default Dallas_ds18B20_TemperatureSensor Constructor<h3>
	 * <p>Default constructor. It sets the sensor description, the sensor name.<p>
	 * 
	 *  @param none
	 */
	public Dallas_ds18B20_TemperatureSensor() {
		String desc = "1-wire temperature sensor, 5V, -25 .. +125°C";
		super.setDescription(desc);
		String name = "Temperature Sensor nel salone";
		super.setName(name);
	}
	
//	sensorPackage pkg = new sensorPackage (0.1, 0.1, 0.1, 0.5);
//	
//	public double[] getPackageInfo() {
//		return pkg.getPackage();
//	}
	
	/* (non-Javadoc)
	 * <h3>getData Dallas_ds18B20_TemperatureSensor method<h3>
	 * <p>getData implementation of the abstract method.
	 * Not really useful...<p>
	 * 
	 * @see sensor.sensor#getData()
	 * @param none
	 * @return nothing
	 */
	public void getData() {
		System.out.println("ds18B20 sends data to raspy Pi");
	}
	
	/* (non-Javadoc)
	 * <h3>sendCommand Dallas_ds18B20_TemperatureSensor method<h3>
	 * <p>sendCommand implementation of the abstract method.
	 * Not really useful....<p>
	 * 
	 * @see sensor.sensor#sendCommand()
	 * @param none
	 * @return nothing
	 */
	public void sendCommand() {
		System.out.println("ds18B20 receives commands from Raspy Pi");
	}
	
	/**
	 * <h3>getTemperature Method<h3>
	 * <p>Method to read the temperature from the sensor, and convert it into a double readable number<p>
	 * 
	 * @param none
	 * @return double the temperature read by the sensor, already converted in °C
	 */
	public double getTemperature() {
		int [] data = new int[5];
		/*
		 * Calls the read method (implemented in the dataBehavior class) through the standard interface _dataBehavior
		 */
		data = getDataBehavior().read();
		// Dummy debug read of the int values retrieved from the sensor
//		for (int i=0; i<5; i++) System.out.printf("%02x ", data[i]);
		int temp = 0;
		int pow = 10000;
		/*
		 * Trick: the values are BCD coded and presented as char, therefore they have to be read digit by digit
		 * and converted in number using the scale factor pow. 
		 */
		for (int i=0; i<5; i++) {
			// convert the data[i] from char to digit (substract '0') and scale it 
			temp += (data[i]-0x30) * pow;
			// adjust new scaling factor
			pow /= 10;
		}
		// Adjust for the comma value and return the converted value
		double foo = (double)temp / 1000;		
		return foo; 
	}
	
}
