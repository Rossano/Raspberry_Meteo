package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * 
 * <h1>Sensor Class<h1>
 * 
 * <p>
 * Abstract class to create the java driver for the sensors.
 * 
 * This class is not directly usable in a program, in order to be as generic as possible, some methods have 
 * to be implemented and specified in other interfaces and classes. 
 * This class provides the basic operation to interface sensors, what has to be specified elsewhere (and it is
 * left abstract) is the way the data and the commands have to be consumed.
 *<p>
 *
 */
public abstract class sensor {
	// Name of the sensor
	private String _name;
	// Description of the sensor
	private String _description;
	/*
	 * These two classes defines how to handle 
	 */
	private commandBehavior _commandBehavior;
	private dataBehavior _dataBehavior;
	
	/**
	 * Abstract method to be implemented by every child
	 * getData() to read data from the sensor
	 * sendCommand() to send a command or configuration to the sensor
	 */
	/**
	 * <h3>getData Method<h3>
	 * <p> Abstract method to read data from sensor. To be implemented each derived class
	 * 
	 * @param none
	 * @return nothing
	 */
	abstract public void getData();
	/**
	 * <h3>sendCommand Method<h3>
	 * <p>Abstract method to send command or configurations to the sensor. To be 
	 * implemented each derived class<p>
	 * 
	 * @param none
	 * @return nothing
	 */
	abstract public void sendCommand();
	
	/**
	 * <h3>getName Method<h3>
	 * <p>Method to return the private member _name content<p>
	 * 
	 * @param none
	 * @return return String containing the Name of the sensor
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * <h3>setName Method<h3>
	 * <p>Method to set the private member _name content<p>
	 * 
	 * @param name: sets the name of the sensor
	 * @return nothing
	 */
	public void setName(String name) {
		_name = name;
	}
	
	/**
	 * <h3>getDescription Method<h3>
	 * <p>Method to return the content of the private member _description<p>
	 * 
	 * @param none
	 * @return returns a string containing the description of the sensor 
	 */
	public String getDescription() {
		return _description;
	}
	
	/**
	 * <h3>setDecription Method<h3>
	 * <p>Method to set the content of the private member _description<p>
	 * 
	 * @param desc sets the description of the sensor
	 * @return nothing
	 */
	public void setDescription (String desc) {
		_description = desc;
	}
	
	/**
	 * <h3>getCommandBehavior Method<h3>
	 * <p>Method to return the commandBehavior class, used as generic interface to send command and
	 * configurations to the sensor<P>
	 * 
	 * @param none
	 * @return returns a class for the commandBehavior, which represent how to manage the sensor
	 */
	public commandBehavior getCommandBehavior() {
		return _commandBehavior;
	}
	
	/**
	 * <h3>setCommandBehavior Method<h3>
	 * <p>Method to set the commandBehavior class in the _commandBehavior private member. Since this
	 * member is private it is needed to access it.<P>
	 * 
	 * @param cb sets a class representing the commandBehavior which represent how to manage the sensor
	 * @return nothing 
	 */
	public void setCommandBehavior (commandBehavior cb) {
		_commandBehavior = cb;
	}
	
	/**
	 * <h3>getDataBehavior Method<h3>
	 * <p>Method to return the dataBehavior class, used as generic interface to read data from the
	 * sensor.<p>
	 * 
	 *  @param none
	 * @return returns a class for the dataBehavior, which represent how to read data from the sensor
	 */
	public dataBehavior getDataBehavior() {
		return _dataBehavior;
	}
	
	/**
	 * <h3>setDataBehavior Method<h3>
	 * <p>Method to set the dataBehavior class in the _dataBehavior private member. Since this memeber is 
	 * private it is needed to access it.<p>
	 * 
	 * @param db sets a class representing the dataBehavior, which represent how to read data from the sensor
	 * @return nothing
	 */
	public void setDataBehavior(dataBehavior db) {
		_dataBehavior = db;
	}
	
	/**
	 * <h3>executeCommand Method<h3>
	 * <p>This method start the sensor using a generic command interface<p>
	 * 
	 * @param none
	 * @return nothing.
	 */
	public void executeCommand() {
		_commandBehavior.start();
	}
	
	/**
	 * <h3>transferData Method<h3>
	 * <p>This method transfers raw data from the sensor. It is used as generic interface to
	 * access the sensor to get data<p>
	 * 
	 * @param none
	 * @return nothing
	 */
	public int [] transferData() {
		int [] data;
		data = _dataBehavior.read(); //transfer();
		return data;
	}
	
}
