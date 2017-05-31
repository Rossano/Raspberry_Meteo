package sensor;

import java.util.Queue;

public interface dataReadingMode {

	//public void processing(Queue el, int depth, int delay_ms);
	public double[] getValues(Queue<Double> temperatures, Queue<Double> humidities);
}
