package master.test;

import java.util.Map;

public class CriticalSubscriber {
	
	private String name;

	public CriticalSubscriber(String name) {
		this.name = name;
	}

	public void update(Map<String, Double> eventMap){

		StringBuilder sb = new StringBuilder();
		sb.append("--- Subscriber " + name);

		System.out.println(sb.toString());

	}

}
