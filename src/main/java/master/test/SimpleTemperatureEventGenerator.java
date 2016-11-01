package master.test;

import java.util.Date;
import java.util.Random;

import com.espertech.esper.client.EPRuntime;

public class SimpleTemperatureEventGenerator {

	private int numberOfEvents;
	private EPRuntime cepRT;
	private long sleepTime;

	public SimpleTemperatureEventGenerator(EPRuntime cepRT, long sleepTime, int numberOfEvents) {
		this.numberOfEvents = numberOfEvents;
		this.cepRT = cepRT;
		this.sleepTime = sleepTime;
	}

	/**
	 * Creates simple random Temperature events and lets the implementation class handle them
	 */
	public void startSendingTemperatureReadings() {
		int count = 0;

		while (count < numberOfEvents) {
			TemperatureSensorEvent event = new TemperatureSensorEvent("sensorX", new Random().nextInt(200), new Date());
			System.out.println("sending " + event.getTemperature() + " at " + event.getTimestamp());

			cepRT.sendEvent(event);
			count++;

			try {
				Thread.sleep(sleepTime);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}

