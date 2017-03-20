/**
 * 
 */
package raspyMeteo;

import java.lang.Runnable;
import java.util.*;
//import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Logger;
import java.util.logging.Handler;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;


//import sensor.ds18B20_Read;
import sensor.*;


/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @param <DHT11_HumidityTemperatureSensor>
 * @since 1.0
 * 
 * <h1>sensorThread Class<h1>
 * 
 * <p>Class that implements the thread of reading from the sensors.
 * This  thread is performing the reading operations on the sensors, and 
 * it makes an average on the data.
 * Then it sends the data through a blocking queue.<p>
 *  
 */
/**
 * @author rpantaleoni
 *
 */
/**
 * @author rpantaleoni
 *
 */
/**
 * @author rpantaleoni
 *
 */
/**
 * @author rpantaleoni
 *
 */
public class sensorThread implements Runnable {

	private boolean _target_PC;
	private boolean _exit = false;
	public boolean read_humidity = true;
	protected BlockingQueue<Message> queue = null;
	private int delay_ms = 1000;
	private LinkedList<Double> _TempQueue = null;
	private LinkedList<Double> _HumQueue = null;
//	private Dallas_ds18B20_TemperatureSensor gts;
	private DHT11_HumidityTemperatureSensor ghs;
	private String _name;
	private Thread _sensorThread;
	private dataReadingMode _mode;
	private static Logger LOG = Logger.getLogger(sensorThread.class.getName());
	private static Handler consoleHandler = null;
	
	/**
	 * <h3>sensorThread Constructor</h3>
	 * 
	 * <p>Constructor to create all the data structure for the sensors drivers
	 * as well as the thread to schedule the reading. At the same time it will 
	 * implement the reading processing algorithm</p>
	 * 
	 * @param queue, BLocking queue of Message elements to exchange data with the main thread
	 * @param target_PC true it means that target is a PC, false it is the Raspberry Pi
	 */
	public sensorThread(BlockingQueue<Message> queue, boolean target_PC) {
		// Initialize the data structures
		_target_PC = target_PC;
		this.queue = queue;
//		gts = new Dallas_ds18B20_TemperatureSensor();
		ghs = new DHT11_HumidityTemperatureSensor();
		//if (_target_PC) {
		//gts.setDataBehavior(new ds18B20_Read_PC());			// PC
		//ghs.setDataBehavior(new DHT11_Read_PC());				// PC
		//}
		//else {
//		gts.setDataBehavior(new ds18B20_Read());				// Raspberry Pi
		ghs.setDataBehavior(new DHT11_Read()); 					// Raspberry Pi
		//}
		
		// Initialize sensor reading scheduler thread
		_name = "Sensor Thread";		
		// Data processing mode : averaging
		_mode = new processingAvg();
		
		//	Logger
		LOG = LOG.getParent();
		consoleHandler = new ConsoleHandler();
		LOG.addHandler(consoleHandler);
		consoleHandler.setLevel(Level.ALL);
		LOG.config("Configuration Done");
	}
	
	/**
	 * <h3>start method</h3>
	 * 
	 * <p>Default thread starting method. It simply starts the scheduler thread.</p>
	 * 
	 * @param none
	 * @return nothing
	 */
	public void start() {
		//System.out.println("Starting Sensor Thread");
		LOG.log(Level.INFO, "Starting Sensor scheduler thread.");
		// if not existing, create the thread and starts it
		if (_sensorThread == null) {
			_sensorThread = new Thread(this, _name);
			_sensorThread.start();
		}
		// exit member set to false for activating the scheduler
		_exit = false;
		LOG.info("Sensor Tread started");
	}
	

	/** 
	 * <h3>run method</h3>
	 * 
	 * <p>Scheduler thread main loop. It reads the data from the sensors and it posts
	 * it into the messaging queue when available.</p>
	 * 
	 * @param none
	 * @return nothing
	 * 
	 * (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// Everything is under try - catch to catch all exceptions
		try {	
			// Variable initialization
			double temperature = 0;
			double humidity = 0;
			// Message msg = new Message(0,0);

			int delay = delay_ms / 1;
			int[] _humidity = new int[5];			
			// Repeat the loop until it is not received a message to quit
			while (!_exit) {
				// repeat the loop until some data are not avalable
				do {
					// temperature = gts.getTemperature();
					_humidity = ghs.getDataBehavior().read();
					// If data from humidity sensor is available it stores and logs it
					if (_humidity != null) {
						temperature = _humidity[2] + 0.1 * _humidity[3];
						humidity = _humidity[0] + 0.1 * _humidity[1];
						String s1 = String.format("Humidity: %d.%d\n", _humidity[0], _humidity[1]);
						String s2 = String.format("Temperature: %d.%d\n", _humidity[2], _humidity[3]);
						Logger.getLogger(raspyMeteo.class.getName()).log(Level.FINE, s1);
						Logger.getLogger(raspyMeteo.class.getName()).log(Level.FINE, s2);
					}
				} while (_humidity == null);
				// Post the message with temperature/himidity values into the communication queue
				Message msg = new Message(temperature, humidity);

				// msg = processing(delay, 1);
				LOG.log(Level.FINE,
						"Message = ({0}, {1})",
						new Object[] { msg.getMessage()[0], msg.getMessage()[1] });
				// Post the message
				queue.put(msg);
				// Thread.sleep(1000);
				// String sb =
				// String.format("Temperature = %f.3\tHumidity = %f",
				// msg.getMessage()[0], msg.getMessage()[1]);
				// LOG.log(Level.INFO, "Temperature = {0}\tHumidity = {1}", new
				// Object [] {msg.getMessage()[0], msg.getMessage()[1]}); //sb);
				Thread.sleep(delay_ms);
			}
		} catch (InterruptedException e) {
			// e.printStackTrace();
			LOG.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	/**
	 * <h3>getQueue method</h3>
	 * <p>Method to return the synchronization queue</p>
	 * 
	 * @param none
	 * @return the synchronization queue as BlockingQueue<Message>
	 */
	public BlockingQueue<Message> getQueue() {
		return queue;
	}
	
	/**
	 * <h3>getTarget_PC method</h3>
	 * <p>Method that returns the value of the _target_PC member</p>
	 * 
	 * @param none
	 * @return _target_PC as boolean
	 */
	public boolean getTarget_PC() {
		return _target_PC;
	}
	
	/**
	 * <h3>exit_thread method</h3>
	 * <p>Method to set the exit thread to true to end the thread</p>
	 * 
	 * @param none
	 * @return nothing
	 */
	public void exit_thread() {
		_exit = true;
	}
	
	/**
	 * <h3>setDelay_s method</h3>
	 * <p>Set the thread delay in seconds</p>
	 * 
	 * @param sec delay in seconds as int
	 * @return nothing
	 */
	public void setDelay_s(int sec) {
		delay_ms = sec * 1000;
	}
		
	/**
	 * <h3>getDelay_s method</h3>
	 * <p>Get the thread sensor reading delay</p>
	 * 
	 * @param none
	 * @return delay_ms in seconds as int
	 */
	public int getDelay_s() {
		return delay_ms / 1000;
	}
	
	/**
	 * <h3>setDelay_ms method</h3>
	 * <p>Set the thread delay in milli-seconds</p>
	 * 
	 * @param sec delay in ms as int
	 * @return nothing
	 */
	public void setDelay_ms(int ms) {
		delay_ms = ms;
	}
	
	/**
	 * <h3>getDelay_s method</h3>
	 * <p>Get the thread sensor reading delay</p>
	 * 
	 * @param none
	 * @return delay_ms in seconds as int
	 */
	public int getDelay_ms() {
		return delay_ms;
	}
	
	/**
	 * <h3>processing methods</h3>
	 * <p>Method implementing the reading from sensors and the consolidation into 2 values
	 * to be sent over the synchronization queue.
	 * The methods uses 2 queues where all read data from the sensors are stored, and are they
	 * then consumed using a given algorithm</p>
	 * 
	 * @param delay, reading delay as int
	 * @param num, number of reading points as int
	 * @return The data to be sent over the synchronization queue as Message
	 * @throws InterruptedException
	 */
	private Message processing(int delay, int num) throws InterruptedException {
		// Initialize the data structures
		double temperature;
		int[] _humidity;
		double humidity = 0;
		_TempQueue = new LinkedList<Double>();
		if (read_humidity) {
			_HumQueue = new LinkedList<Double>();
		}
		// Reading from sensor cycle
		for (int i = 0; i < num; i++) {
			// Reads the data from the sensor
//			temperature = gts.getTemperature();
//			temperature = 0.0;
//			_TempQueue.add(temperature);
			if (read_humidity) {
				// Performs the reading until there is a new data available
				do {
					//_humidity = ghs.transferData();
					_humidity = ghs.getDataBehavior().read();
					// If data are available add them to the temperature and humidity queues
					if (_humidity != null) {
						humidity = _humidity[0] + 0.1 * _humidity[1];
						temperature = _humidity[2] + 0.1 * _humidity[3];
						_TempQueue.add(temperature);
						_HumQueue.add(humidity);
					}
				} while (_humidity == null);				
			}
			// Wait for new scheduled reading
			Thread.sleep(delay);
		}
		/*double sumT = 0; 
		double sumH = 0;
		for (int i = 0; i < num; i++) {
			sumT += (double)_TempQueue.remove();
			if(read_humidity) sumH += (double)_HumQueue.remove();
		}
		*/
		//Message m = new Message(sumT / num, sumH / num);
		
		// Get the data from the message queues based on the chosen processing algorithm
		// and create a new Message data to return it
		double[] data = _mode.getValues(_TempQueue, _HumQueue);
		Message m = new Message(data[0], data[1]);
		return m;
	}
}
