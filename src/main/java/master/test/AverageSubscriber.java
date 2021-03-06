package master.test;

import java.util.Map;

public class AverageSubscriber {

	private String name;

	public AverageSubscriber(String name) {
		this.name = name;
	}

	public void update(Map<String, Double> eventMap){
		
		Double avg = (Double) eventMap.get("avgtemp");
		
		StringBuilder sb = new StringBuilder();
		sb.append("--- Subscriber " + name + " - Average Temp = " + avg);
		
		System.out.println(sb.toString());

	}
}
