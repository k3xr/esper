package master.test;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class App 
{
	public static void main( String[] args )
	{
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("TemperatureEventStream", TemperatureSensorEvent.class.getName());

		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();

		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement = cepAdm.createEPL("select irstream temperature from TemperatureEventStream.win:time(2 sec)");

		cepStatement.addListener(new SimpleListener());

		SimpleTemperatureEventGenerator gen = new SimpleTemperatureEventGenerator(cepRT, 10000, 10);

		gen.startSendingTemperatureReadings();
	}
}
