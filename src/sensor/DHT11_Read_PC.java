/**
 * 
 */
package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * @see dataBehavior
 * 
 * <h1>DH11_Read_PC Class<h1>
 * 
 * <p>Class to implements the dataBehavior interface for the use of PC.<p>
 *
 */
public class DHT11_Read_PC implements dataBehavior {

	/**
	 * <h3>trasfer Method<h3>
	 * 
	 * <p>Dummy class.<p>
	 * @see sensor.dataBehavior#transfer()
	 * 
	 * @param none
	 * @return boolean always false
	 */
	@Override
	public boolean transfer() {
		return false;
	}

	/**
	 * <h3>read Method<h3>
	 * 
	 * <p>Method to read data from a dummy sensor on a PC.<p>
	 * @see sensor.dataBehavior#read()
	 * 
	 * @param none
	 * @return array of integer with the sensor data
	 */
	@Override
	public int[] read() {
		int[] foo = new int [2];
		
		foo[0] = (int) Math.round(Math.random() * 100);
		foo[1] = (int)0;
		
		return foo;
	}

}
