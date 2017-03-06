/**
 * 
 */
package raspyMeteo;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>Message Class<h1>
 * 
 * <p>This class provides a way to send message through blocking queue for data
 * exchange between threads.<p>
 *
 */
public class Message {
	// data to be transferred in the queue
	private double[] _data;
	
	/**
	 * <h3>Default Constructor<h3>
	 * 
	 * <p>Default constructor for the Message class, it sets the data to be stored.<p>
	 * 
	 * @param double temp temperature data
	 * @param double hum humidity data
	 */
	public Message(double temp, double hum) {
		_data = new double[2];
		_data[0] = temp;
		_data[1] = hum;
	}
	
	/**
	 * <h3>getMessage Method<h3>
	 * 
	 * <p>Method that returs the message.<p>
	 * 
	 * @param none
	 * @return array of double containing the data of the message
	 */
	public double[] getMessage(){
		return _data;
	}

}
