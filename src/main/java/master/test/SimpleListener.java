package master.test;

import java.util.Date;

import com.espertech.esper.client.EventBean;
import com.espertech.esper.client.UpdateListener;

public class SimpleListener implements UpdateListener {

	public void update(EventBean[] newData, EventBean[] oldData) {

		System.out.println("Listener called at: " + new Date());
		if (newData != null) {
			for (int i = 0; i < newData.length; i++) {
				System.out.println("Event new received: " + newData[i].getUnderlying());
			}
		}

		if (oldData != null) {
			for(int j = 0; j < oldData.length; j++){
				System.out.println("Event Old received: " + oldData[j].getUnderlying());
			}
		}

	}

}
