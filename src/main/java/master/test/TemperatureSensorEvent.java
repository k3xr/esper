package master.test;

import java.util.Date;

public class TemperatureSensorEvent {

	private String sensor;
	private double temperature;
	private Date timestamp;

	public TemperatureSensorEvent(String sensor, double temperature, Date timestamp) {
		super();
		this.sensor = sensor;
		this.temperature = temperature;
		this.timestamp = timestamp;
	}

	public String getSensor() {
		return sensor;
	}

	public double getTemperature() {
		return temperature;
	}

	public Date getTimestamp() {
		return timestamp;
	}
}
