/**
 * 
 */
package raspyMeteo;

import java.lang.Runnable;
import java.util.concurrent.BlockingQueue;

import sensor.DHT11_HumidityTemperatureSensor;
import sensor.Dallas_ds18B20_TemperatureSensor;
import sensor.ds18B20_Read;
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
public class sensorThread implements Runnable {

	private boolean _target_PC;
	protected BlockingQueue<Message> queue = null;
	private Dallas_ds18B20_TemperatureSensor gts;
	private DHT11_HumidityTemperatureSensor ghs;
	
	public sensorThread(BlockingQueue<Message> queue, boolean target_PC) {
		_target_PC = target_PC;
		this.queue = queue;
		gts = new Dallas_ds18B20_TemperatureSensor();
		ghs = new DHT11_HumidityTemperatureSensor();
		if (_target_PC) {
			gts.setDataBehavior(new ds18B20_Read_PC());			// PC
			ghs.setDataBehavior(new DHT11_Read_PC());			// PC
		}
		else {
			gts.setDataBehavior(new ds18B20_Read());			// Raspberry Pi
			ghs.setDataBehavior(new DHT11_Read_PC()); 			// Raspberry Pi
		}
	}
	
	public void run() {
		try {
			double temperature;
			double humidity;
			temperature = gts.getTemperature();
			humidity = 0;
			
			Message msg = new Message(temperature, humidity);
			
			queue.put(msg);
			
			Thread.sleep(1000);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public BlockingQueue<Message> getQueue() {
		return queue;
	}
	
	public boolean getTarget_PC() {
		return _target_PC;
	}
	
}
