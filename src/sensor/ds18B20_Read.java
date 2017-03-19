package sensor;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedByInterruptException;
import java.nio.channels.InterruptedByTimeoutException;
import java.util.logging.Level;

import raspyMeteo.sensorThread;

import com.sun.istack.internal.logging.Logger;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * @see dataBehavior
 * 
 * <h1>ds18B208Read Class<h1>
 * <p> Class to implements the dataBehavior interface used by the sensor class. This is the real
 * low level driver of the ds18B20 temperature sensor.
 * For information ds18B20 1-wire driver is implemented in Raspberry Pi as a file in the system
 * folders, therefore the driver is a simple read in a file.<p>
 *
 */
public class ds18B20_Read implements dataBehavior {
	
	// Storage for the raw data
	private int[] _data = new int[5];
	// Name of the file where are stored the 1-wire data, and the member implementing the file
	private final String fileName = "/sys/bus/w1/devices/28-0214661b30ff/w1_slave";	
	private FileInputStream file;
//	private ByteBuffer buf;
	private byte[] buf;
	public int _timeout = 500;
	
	
	/**
	 * <h3>ds18B20_Read Default Constructor<h3>
	 * <p>Default constructor for the ds18B20_Read class, it sets the timeout variable value and allocate the 
	 * read buffer.<p>
	 * 
	 * @param none
	 */
	public ds18B20_Read() {
		//_data = new int[5];
		_timeout = 500;
//		buf = ByteBuffer.allocate(100);
		buf = new byte[100];
	}
	
	/**
	 * <h3>ds18B20_Read Default Constructor<h3>
	 * <p>Constructor for the ds18B20_Read class, it set the timeout to the parameter passed and
	 * allocate the read buffer.<p>
	 * 
	 * @param timeout: 
	 */
	public ds18B20_Read(int timeout) {
		//_data = new int[5];
		_timeout = timeout;
//		buf = ByteBuffer.allocate(100);
		buf = new byte[100];
	}
	
	/** 
	 * <h3>read Method<h3>
	 * <p>This is a method to implements the low level read on the sensor. As said, it is  a read in a file
	 * on the Raspberry Pi system folders.<p>
	 * @see sensor.dataBehavior#read()
	 * 
	 * @param none
	 * @return array of int, the raw data from the ds18B20 sensor, they are coded as char.
	 */
	public int[] read() {
		/*
		 * All the method will be able to throw excpetions
		 */
		try {
			// Open the 1-wire system file
			file = new FileInputStream(fileName);
			// Check if the file exists, else throw an exception
			if (file == null) throw new FileNotFoundException("28-0214661b30ff device not found");
//			byte[] buf = new byte[100];
			int total = 0;
			int nRead = 0;
			
			/*
			 * Read all the content of the 1-wire system file, and stores it in buf[]
			 * Find the total number of read byte at the same time
			 */
			while ((nRead = file.read(buf)) != -1) {
				total += nRead;
			}
			
			/*
			 * Parse the buffer and seeks for the 't' marker.
			 * Once the marker is found, store the data adapting for the actual raw buffer offsets
			 * _data[j] = buf[i+2+j]
			 */
			for (int i=0; i<total; i++) {
				if(buf[i] == 't') {
					for (int j=0; j<_data.length; j++) _data[j] = buf[i+2+j];
				}
			}
			// Close the 1-wire file
			file.close();
			
			/*
			 * Returns the raw data read.
			 */
			return _data;
		}
		/*
		 * Exception for the 1-wire file not found
		 */
		catch (FileNotFoundException ex) {
			System.out.println("device 0214661b30ff not found");
			return null;
		}
		/*
		 * Generic exception for an I/O error
		 */
		catch (IOException ex) {
			System.out.println("File error");
			return null;
		}
	}
	
	/** 
	 * <h3>transfer Method<h3>
	 * <p>Dummy method just to implements the interface, it does nothing<p>
	 * @see sensor.dataBehavior#transfer()
	 * 
	 * @param none
	 * @return boolean always false
	 */
	@Override
	public boolean transfer() {
//		return false;
		try {
			if((file = new FileInputStream(fileName)) == null) {
				throw new RuntimeException("Temperature sensor system file not found!");
			}
			try {
				int ret = readTimeout(file, _timeout);				
			}
			finally {
				file.close();
			}
		}		
		catch (Exception e) {
			throw new RuntimeException(e);
		}
		return true;
	}
	
	private int readTimeout(final FileInputStream file, int timeout) throws IOException, InterruptedException {
		final int[] dataReady = {0};
		IOException[] exceptions = {null};
		
		final Thread reader = new Thread() {
			public void run() {
				try {
					byte[] _buf = new byte[100];
					dataReady[0] = file.read(buf);
					//for(int i = 0; i < 100; i++) buf.[i] = _buf[i];
				}
				catch(ClosedByInterruptException e) {					
					Logger.getLogger(sensorThread.class.getName(),null).log(Level.SEVERE, "Timeout when reading to the sensor system file");
					try {
						throw new InterruptedByTimeoutException();
					} catch (InterruptedByTimeoutException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
				catch(IOException e) {
					Logger.getLogger(sensorThread.class.getName(),null).log(Level.SEVERE, "IO Error");
					throw new RuntimeException(e);
				}
			}
		};
		
		Thread interruptor = new Thread() {
			public void run() {
				reader.interrupt();
			}
		};
		
		reader.start();
		
		for (;;) {
			reader.join(timeout);
			if(reader.isAlive()) break;
			interruptor.start();
			interruptor.join(100);
			reader.join(100);
			if(!reader.isAlive()) break;
			Logger.getLogger(sensorThread.class.getName(), null).log(Level.SEVERE, "Deadlock reading on file");
		}
		
		if(exceptions[0] != null) {
			throw exceptions[0];
		}
		
		return dataReady[0];
	}
}
