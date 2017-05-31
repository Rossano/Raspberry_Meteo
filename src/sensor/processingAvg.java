package sensor;

import java.util.*;
//import java.util.Queue;

public class processingAvg implements dataReadingMode {

	@Override
	public double[] getValues(Queue<Double> temperatures, Queue<Double> humidities) {
		double sumT = 0; 
		double sumH = 0;
		int countT = 0;
		int countH = 0;
		
		Iterator<Double> tempIterator = temperatures.iterator();
		Iterator<Double> humIterator = humidities.iterator();
		
		while (tempIterator.hasNext()) {
			sumT += (double)tempIterator.next();
			countT++;
		}
		while (humIterator.hasNext()) {
			sumH += (double)humIterator.next();
			countH++;
		}
		
		return new double[] {sumT / countT, sumH / countH};
	}

}
