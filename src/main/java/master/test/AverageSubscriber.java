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
		sb.append("-------Subscriber " + name + "--------");
		sb.append("\n- Average Temp = " + avg);
		sb.append("\n----------------------");
		
		System.out.println(sb.toString());
	}
}
