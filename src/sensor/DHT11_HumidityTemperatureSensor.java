package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * @see sensor
 * 
 * <h1>DHT11_HumidityTemperatureSensor Method<h1>
 * <p>Class for the DHT11 Humidity and Temperature sensor
 * 
 * This class extends the sensor abstract class. It implements the abstract methods defined in sensor class
 * and adds a new method to read the temperature from the sensor.<p>
 *
 */
public class DHT11_HumidityTemperatureSensor extends sensor {
	
	/**
	 * <h3>DHT11_HumidityTemperatureSensor Constructor<h3>
	 * <p>This is the Default constructor.
	 * It sets the name and description strings.<p>
	 *
	 * @param none
	 */
	public DHT11_HumidityTemperatureSensor() {
		String desc = "1-wire humidity and temperature sensor, 3.3V, 0..100% relative humidity";
		super.setDescription(desc);
		String name = "DHT11 sensore umidità nel salone";
		super.setName(name);
	}
	
//	sensorPackage pkg = new sensorPackage(0.28, 1.2, 0.1, 2.4);
//	
//	public double[] getPackageInfo() {
//		return pkg.getPackage();
//	}
	
	/**
	 * <h3>getData Method<h3>
	 * <p>Implementation of the getData abstract method. It does nothing really...<p>
	 * @see sensor.sensor#getData()
	 * 
	 * @param none
	 * @return nothing
	 */
	public void getData() {
		System.out.println("DHT11 sends data to the raspy Pi");
	}
	
	/**
	 * <h3>sendCommand Method<h3>
	 * <p>Implementation of the sendCommand abstract method. It does really nothing ...<p> 
	 * @see sensor.sensor#sendCommand()
	 * 
	 * @param none
	 * @return nothing
	 */
	public void sendCommand() {
		System.out.println("DHT11 receives data from the raspy Pi");
	}
}
