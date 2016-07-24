package sensor;

public class DHT11_HumidityTemperatureSensor extends sensor {
	
	DHT11_HumidityTemperatureSensor() {
		String desc = "1-wire humidity and temperature sensor, 3.3V, 0..100% relative humidity";
		super.setDescription(desc);
		String name = "DHT11 sensore umidità nel salone";
		super.setName(name);
	}
	
//	sensorPackage pkg = new sensorPackage(0.28, 1.2, 0.1, 2.4);
//	
//	public double[] getPackageInfo() {
//		return pkg.getPackage();
//	}
	
	public void getData() {
		System.out.println("DHT11 sends data to the raspy Pi");
	}
	
	public void sendCommand() {
		System.out.println("DHT11 receives data from the raspy Pi");
	}
}
