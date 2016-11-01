package master.test;

import java.util.Map;

public class MonitorSubscriber {

	private String name;

	public MonitorSubscriber(String name) {
		this.name = name;
	}

	public void update(Map<String, Double> eventMap){

		Double temp = (Double) eventMap.get("avg(temperature)");

		StringBuilder sb = new StringBuilder();
		sb.append("--- Subscriber " + name + " - AverageTemp = " + temp);

		System.out.println(sb.toString());

	}

}
