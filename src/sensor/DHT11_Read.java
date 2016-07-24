package sensor;

import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalMultipurpose;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.RaspiPin;
import com.pi4j.io.gpio.Pin;
import com.pi4j.io.gpio.PinMode;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.component.ObserveableComponentBase;
import com.pi4j.wiringpi.*;

//package sensor;

public class DHT11_Read implements dataBehavior {
	
	private int[] _data = new int[5];
	private int MAXTIMINGS = 85;
	private final Pin DEFAULT_PIN = RaspiPin.GPIO_07;
	//private GpioPinDigitalMultipurpose dht11Pin;
	private final int dht11Pin = 7;
	
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
	
	public int[] read() {
		//PinState lastState = PinState.HIGH;
		int lastState = Gpio.HIGH;
		int counter = 0;
		int j = 0, i;
		StringBuilder value = new StringBuilder();
		
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
		
		try {
			Gpio.pinMode(dht11Pin, Gpio.OUTPUT);
			Gpio.digitalWrite(dht11Pin, Gpio.LOW);
			Gpio.delay(18);
			
			Gpio.digitalWrite(dht11Pin, Gpio.HIGH);
			Gpio.delayMicroseconds(40);
			Gpio.pinMode(dht11Pin, Gpio.INPUT);			
			
			for (i=0; i<MAXTIMINGS; i++) {
				counter = 0;
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

	private void sensorOutput() {
		
	}
	
	@Override
	public boolean transfer() {
		// TODO Auto-generated method stub
		return false;
	}
	
	private PinState gpioRead(GpioPinDigitalInput pin) {
		if (pin.isHigh()) return PinState.HIGH;
		else if (pin.isLow()) return PinState.LOW;
		else return null;
	}
	
	private boolean verifyChecksum() {
		return (_data[4] == ((_data[0] + _data[1] + _data[2] + _data[3]) & 0xFF));
	}
}
