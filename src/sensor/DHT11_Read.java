package sensor;

/*import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.GpioPinDigitalOutput;*/
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.Pin;
/*import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.component.ObserveableComponentBase; */
import com.pi4j.wiringpi.*;

//package sensor;

/**
 * @author Rossano Pantaleoni
 * @version 1.0
 * @since 1.0
 * @see dataBehavior
 * 
 * <h1>DHT11_Read Class<h1>
 * <p>Class to implements the dataBehavior interface used by the sensor class. This is the real
 * low level driver of the DHT11 humidity and temperature sensor.
 * For information DHT11 1-wire driver is implemented in Raspberry Pi as a software implementation
 * therefore it is based on the wiringPi libraries.<p>
 *
 */
public class DHT11_Read implements dataBehavior {
	
	// Raw data storage
	private int[] _data = new int[5];
	private int MAXTIMINGS = 85;
	// Default Pin used by the sensor 
	private final Pin DEFAULT_PIN = RaspiPin.GPIO_07;
	//private GpioPinDigitalMultipurpose dht11Pin;
	// Pin object for the DHT11 readout
	private final int dht11Pin = 7;
	
	/**
	 * <h3>DHT11_Read Constructor<h3>
	 * <p>This is the default constructor for the DHT11_Read class. It basically checks if the wiringPi is up & running.<p>
	 * 
	 * @param none
	 */
	public DHT11_Read() {
		//final GpioController gpio = GpioFactory.getInstance();
		//dht11Pin = gpio.provisionDigitalMultipurposePin(DEFAULT_PIN, PinMode.DIGITAL_INPUT, PinPullResistance.PULL_UP);
		// Setup wiringPi
		if (Gpio.wiringPiSetup() == -1) {
			System.out.println("Error: GPIO setup failed");
			return;
		}
	}
	
/*	public DHT11_Read(Pin pin) {
		final GpioController gpio = GpioFactory.getInstance();
		dht11Pin = gpio.provisionDigitalMultipurposePin(pin, PinMode.DIGITAL_INPUT, PinPullResistance.PULL_UP);
	}*/
	
	/**
	 * <h3>read Method<h3>
	 * <p>Method to read raw data from the DHT11 sensor. It configures the sensor for the readout
	 * and reads the bit one by one and stores them into a data storage.<p>
	 * @see sensor.dataBehavior#read()
	 * 
	 * @param none
	 * @return array of int representing the raw data.
	 */
	public int[] read() {
		//PinState lastState = PinState.HIGH;
		// variable to store the last known state
		int lastState = Gpio.HIGH;
		// counter for the bit read
		int counter = 0;
		int j = 0, i;
		StringBuilder value = new StringBuilder();
		
		// Fill return array with 0
		for (i=0; i<5; i++) _data[i] = 0;
		
//		final GpioController gpio = GpioFactory.getInstance();
//		if (true) {
//			GpioPinDigitalOutput dhtpin_out = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_07, "DHT11-Signal",PinState.LOW);
//			dhtpin_out.low();
//			Gpio.delayMicroseconds(18);
//			
//			dhtpin_out.high();
//			Gpio.delay(40);
//		}
//		
//		final GpioPinDigitalInput dhtpin_in = gpio.provisionDigitalInputPin(RaspiPin.GPIO_07);
//		
//		for (i=0; i<MAXTIMINGS; i++) {
//			counter = 0;
//			while (gpioRead(dhtpin_in) == lastState) {
//				counter++;
//				Gpio.delayMicroseconds(1);
//				if (counter == 255) break;
//			}
//			
//			lastState = gpioRead(dhtpin_in);
//			
//			if (counter == 255) break;
//			
//			// Ignore first 3 transitions
//			if((i >= 4) && (i%2 == 0)) {
//				// shove each bit into the storage bytes
//				_data[j/8] <<= i;
//				if (counter > 16) _data[j/8] |= i;
//				j++;
//			}
//			
//			// check we read 40 bits (8bit x 5) + verify checksum
//			if ((j >= 40) && verifyChecksum()) return _data;
//			else return null;
//		}
//		return null;
		
		/*
		 * Performs all the reading in a try-catch block
		 */
		try {
			/*
			 * Sets DHT11 pin to output, and set it LOW for 18ms
			 */
			Gpio.pinMode(dht11Pin, Gpio.OUTPUT);
			Gpio.digitalWrite(dht11Pin, Gpio.LOW);
			Gpio.delay(18);
			
			/*
			 * Sets the DHT11 pin to HIGH for 40µs, then configure the pin in INPUT
			 */
			Gpio.digitalWrite(dht11Pin, Gpio.HIGH);
			Gpio.delayMicroseconds(40);
			Gpio.pinMode(dht11Pin, Gpio.INPUT);			
			
			/*
			 * Reading block. Reads the state for a max of MAXTIMINGS, and for each cycle read the pin state.
			 * Then store the value read in a buffer
			 */
			for (i=0; i<MAXTIMINGS; i++) {
				counter = 0;
				// Loop until a new state is found, or if there is a timeout (2 bits of the same value)
				while(Gpio.digitalRead(dht11Pin) == lastState) {
					counter ++;
					Gpio.delayMicroseconds(1);
					if (counter == 255) break;
				}
				
				lastState = Gpio.digitalRead(dht11Pin);
				
				if (counter == 255) {
					break;
				}
				
				// Ignore first 3 transitions
				if ((i >= 4) && (i % 2 == 0)) {
					// shove each nit into storage bytes
					_data[j/8] <<= 1;
					if (counter > 16) {
						_data[j/8] |= 1;
					}
					j++;
				}
			}
						
			// Check we read 40 bits (8bit x 5) + verify checksum in the last byte
			if ((j >= 40) && verifyChecksum()) {
				return _data;
			}
			return null;
		}
		catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * <h3>sensorOutput Method<h3>
	 * <p>Empty class.<p>
	 * 
	 * @param none
	 * @return nothing
	 */
	private void sensorOutput() {
		
	}
	
	/** 
	 * <h3>transfer Metod<h3>
	 * <p>Dummy method to match the dataBehavior interface.<p>
	 * @see sensor.dataBehavior#transfer()
	 * 
	 * @param none
	 * @return boolean, true if transfer OK, false if transfer FAILED.
	 */
	@Override
	public boolean transfer() {		
		return false;
	}
	
	/**
	 * <h3>gpioRead Method<h3>
	 * <p>Method to read the state of a given gpio<p>
	 * 
	 * @param pin the Raspberry gpio pin to read the state
	 * @return PinState with the state of the pin (HIGH/LOW)
	 */
	private PinState gpioRead(GpioPinDigitalInput pin) {
		if (pin.isHigh()) return PinState.HIGH;
		else if (pin.isLow()) return PinState.LOW;
		else return null;
	}
	
	/**
	 * <h3>verifyChecksum Method<h3>
	 * <p>Method to verify the DHT11 communication checksum.<p>
	 * 
	 * @param none
	 * @return boolean representing the checksum state, true if OK, false if FAILED
	 */
	private boolean verifyChecksum() {
		return (_data[4] == ((_data[0] + _data[1] + _data[2] + _data[3]) & 0xFF));
	}
}
