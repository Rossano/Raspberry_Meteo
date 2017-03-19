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
 * <h1>ds18B20_Read_PC Class<h1>
 * <p>Class to implements the dataBehavior interface to be used on a PC.<p>
 * 
 */
public class ds18B20_Read_PC implements dataBehavior {

	/*public ds18B20_Read_PC() {
		
	}*/
	
	/**
	 * @see sensor.dataBehavior#transfer()
	 * 
	 * @param none
	 * @return boolean always false
	 */
	@Override
	public boolean transfer() {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * <h3>read Method<h3>
	 * <p>Method to implements the reading on the PC before porting to
	 * Raspberry Pi.<p>
	 * @see sensor.dataBehavior#read()
	 * 
	 * @param none
	 * @return array of integer with the dummy sensor data.
	 */
	@Override
	public int[] read() {
		int[] foo = new int[5];
		
		/*foo[0] = 2;
		foo[1] = (int)Math.round(Math.random() + 0.5) + 2;
		foo[2] = (int)Math.round(Math.random() * 1);
		foo[3] = (int)Math.round(Math.random() * 1);
		foo[4] = (int)Math.round(Math.random() * 1); */
		foo[0] = 2 + 0x30;
		foo[1] = 3 + 0x30;
		foo[2] = 0 + 0x30;
		foo[3] = 2 + 0x30;
		foo[4] = 6 + 0x30;
		
		return foo;
	}

}
