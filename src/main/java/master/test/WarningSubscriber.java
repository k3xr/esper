package master.test;

import java.util.Map;

public class WarningSubscriber {
	
	private String name;

	public WarningSubscriber(String name) {
		this.name = name;
	}

	public void update(Map<String, Double> eventMap){

		StringBuilder sb = new StringBuilder();
		sb.append("--- Subscriber " + name);

		System.out.println(sb.toString());

	}
}
