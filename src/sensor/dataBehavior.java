package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>dataBehavior Interface<h1>
 * <p>Generic interface for the dataBehavior object.
 * This interface is meant to act as a generic interface to read data from the sensor.<p>
 *
 */
public interface dataBehavior {

	/**
	 * <h3>transfer Method<h3>
	 * <p>Method to transfer data from the sensor.<p>
	 * 
	 * @param none
	 * @return boolean, transfer result, true OK, false FAILED.
	 */
	public boolean transfer();
	
	/**
	 * <h3>read Method<h3>
	 * <p>Method to read raw data from the sensor.<p>
	 * 
	 * @param none
	 * @return array of int representing the raw data read from the sensor.
	 */
	public int[] read();
	
}
