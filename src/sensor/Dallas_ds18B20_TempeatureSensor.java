package sensor;

public class Dallas_ds18B20_TempeatureSensor extends sensor {

	public Dallas_ds18B20_TempeatureSensor() {
		String desc = "1-wire temperature sensor, 5V, -25 .. +125°C";
		super.setDescription(desc);
		String name = "Temperature Sensor nel salone";
		super.setName(name);
	}
	
//	sensorPackage pkg = new sensorPackage (0.1, 0.1, 0.1, 0.5);
//	
//	public double[] getPackageInfo() {
//		return pkg.getPackage();
//	}
	
	public void getData() {
		System.out.println("ds18B20 sends data to raspy Pi");
	}
	
	public void sendCommand() {
		System.out.println("ds18B20 receives commands from Raspy Pi");
	}
	
	public double getTemperature() {
		int [] data = new int[5];
		data = getDataBehavior().read();
		for (int i=0; i<5; i++) System.out.printf("%02x ", data[i]);
		int temp = 0;
		int pow = 10000;
		for (int i=0; i<5; i++) {
			temp += (data[i]-0x30) * pow;
			pow /= 10;
		}
		double foo = (double)temp / 1000;		
		return foo; 
	}
	
}
