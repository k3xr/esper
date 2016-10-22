package master.test;

import java.util.Date;
import java.util.Random;

import com.espertech.esper.client.EPRuntime;

public class SimpleTemperatureEventGenerator {

	private int noe;
	private EPRuntime cepRT;
	private long sleepTime;

	public SimpleTemperatureEventGenerator(EPRuntime cepRT, long sleepTime, int noe) {
		this.noe = noe;
		this.cepRT = cepRT;
		this.sleepTime = sleepTime;
	}

	/**
	 * Creates simple random Temperature events and lets the implementation class handle them
	 */
	public void startSendingTemperatureReadings() {
		int count = 0;

		while (count < noe) {
			TemperatureSensorEvent event = new TemperatureSensorEvent("sensorX", new Random().nextInt(100), new Date());
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

