package master.test;

import com.espertech.esper.client.Configuration;
import com.espertech.esper.client.EPAdministrator;
import com.espertech.esper.client.EPRuntime;
import com.espertech.esper.client.EPServiceProvider;
import com.espertech.esper.client.EPServiceProviderManager;
import com.espertech.esper.client.EPStatement;

public class App 
{
	public static void main(String[] args)
	{
		
		main2(args);
		
//		Configuration cepConfig = new Configuration();
//		cepConfig.addEventType("TemperatureEventStream", TemperatureSensorEvent.class.getName());
//
//		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
//		EPRuntime cepRT = cep.getEPRuntime();
//
//		EPAdministrator cepAdm = cep.getEPAdministrator();
//		EPStatement cepStatement = cepAdm.createEPL("select irstream temperature from TemperatureEventStream.win:time(2 sec)");
//
//		cepStatement.addListener(new SimpleListener());
//
//		SimpleTemperatureEventGenerator gen = new SimpleTemperatureEventGenerator(cepRT, 10000, 10);
//
//		gen.startSendingTemperatureReadings();
	}
	
	public static void main2(String[] args)
	{
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("TemperatureEventStream", TemperatureSensorEvent.class.getName());
		cepConfig.addEventType("TemperatureAlertStream", AverageTemperature.class.getName());

		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();
		
		EPAdministrator cepAdm = cep.getEPAdministrator();
		EPStatement cepStatement1 = cepAdm.createEPL("select avg(temperature) from TemperatureEventStream.win:time(30 sec)");

		cepStatement1.addListener(new AlertListener(cepRT));	
		
		EPStatement cepStatement2 = cepAdm.createEPL("select temperature from TemperatureEventStream where temperature > 100");

		cepStatement2.setSubscriber(new TemperatureSubscriber("High Temperature"));
		
		EPStatement cepStatement3 = cepAdm.createEPL("select avgtemp from TemperatureAlertStream where avgtemp > 70");

		cepStatement3.setSubscriber(new AverageSubscriber("Average Temperature"));
		SimpleTemperatureEventGenerator gen = new SimpleTemperatureEventGenerator(cepRT, 10000, 100);
		
		gen.startSendingTemperatureReadings();
	}
}
