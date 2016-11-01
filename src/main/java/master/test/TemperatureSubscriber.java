package master.test;

import java.util.Map;

public class TemperatureSubscriber {

	private String name;

	public TemperatureSubscriber(String name) {
		this.name = name;
	}

	public void update(Map<String, Double> eventMap){

		Double temp = (Double) eventMap.get("temperature");
		
		StringBuilder sb = new StringBuilder();
		sb.append("--- Subscriber " + name + " - Temp = " + temp);
		
		System.out.println(sb.toString());

	}

}
