package sensor;

import java.io.*;

public class ds18B20_Read implements dataBehavior {
	
	private int[] _data = new int[5];
	private String fileName = "/sys/bus/w1/devices/28-0214661b30ff/w1_slave";
	private FileInputStream file;
	public ds18B20_Read() {
		//_data = new int[5];
	}

	public int[] read() {
		try {
			file = new FileInputStream(fileName);
			if (file == null) throw new FileNotFoundException("28-0214661b30ff device not found");
			byte[] buf = new byte[100];
			int total = 0;
			int nRead = 0;
			
			while ((nRead = file.read(buf)) != -1) {
				total += nRead;
			}
			
			for (int i=0; i<total; i++) {
				if(buf[i] == 't') {
					for (int j=0; j<_data.length; j++) _data[j] = buf[i+2+j];
				}
			}
			file.close();
			
			return _data;
		}
		catch (FileNotFoundException ex) {
			System.out.println("device 0214661b30ff not found");
			return null;
		}
		catch (IOException ex) {
			System.out.println("File error");
			return null;
		}
	}
	
	@Override
	public boolean transfer() {
		// TODO Auto-generated method stub
		return false;
	}
}
