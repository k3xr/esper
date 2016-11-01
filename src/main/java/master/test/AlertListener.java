package master.test;

import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class AlertListener implements UpdateListener {
	
	private EPRuntime cepRT;
	
	public AlertListener(EPRuntime cepRT){
		this.cepRT = cepRT;
	}
	
	public void update(EventBean[] newData, EventBean[] oldData){
		if (newData != null) {
			for (int i = 0; i < newData.length; i++) {
				System.out.println("Event new received: " + newData[i].getUnderlying());
				AverageTemperature a = new AverageTemperature((Double) newData[i].get("avg(temperature)"));
				cepRT.route(a);
			}
		}
		if (oldData != null) {
			return;
		}
	}
}
