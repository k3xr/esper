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
		example3(args);
	}
	
	/**
	 * Simple example
	 * @param args
	 */
	public static void example1(String[] args)
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
	
	/**
	 * Given a stream reporting the temperature of a room, fire an alarm any time the temperature
	 * of the room overcomes 100 or the average temperature on the last 30 seconds is greater than 70
	 * @param args
	 */
	public static void example2(String[] args)
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
	
	/**
	 * Monitor - Tells average temperature every 10 seconds 
	 * Warning - Tells if 2 consecutive temperatures are above a threshold (100)
	 * Critial - Tells if 4 consecutive events:
	 *     first above a threshold (100)
	 *     each sobsequent greater than the last
	 *     the last one being 1.5 times greater than the first
	 * @param args
	 */
	public static void example3(String[] args)
	{
		Configuration cepConfig = new Configuration();
		cepConfig.addEventType("TemperatureEventStream", TemperatureSensorEvent.class.getName());

		EPServiceProvider cep = EPServiceProviderManager.getProvider("myCEPEngine", cepConfig);
		EPRuntime cepRT = cep.getEPRuntime();
		EPAdministrator cepAdm = cep.getEPAdministrator();
		
		EPStatement cepStatement1 = cepAdm.createEPL("select avg(temperature) from TemperatureEventStream.win:time_batch(10 sec)");
		cepStatement1.setSubscriber(new MonitorSubscriber("Temperature Monitor"));
		
		EPStatement cepStatement2 = cepAdm.createEPL(
				"select * from TemperatureEventStream "
				+ "match_recognize ("
				+ "measures A as temp1, B as temp2 "
				+ "pattern (A B) "
				+ "define "
				+ "A as A.temperature > 100, "
				+ "B as B.temperature > 100"
				+ ")");
		cepStatement2.setSubscriber(new WarningSubscriber("Temperature Warning"));
		
		EPStatement cepStatement3 = cepAdm.createEPL(
				"select * from TemperatureEventStream "
				+ "match_recognize ("
				+ "measures A as temp1, B as temp2, C as temp3, D as temp4 "
				+ "pattern (A B C D) "
				+ "define "
				+ "A as A.temperature > 100, "
				+ "B as B.temperature > A.temperature, "
				+ "C as C.temperature > B.temperature, "
				+ "D as D.temperature > C.temperature and "
				+ "D.temperature > 1.5*A.temperature"
				+ ")");
		cepStatement3.setSubscriber(new CriticalSubscriber("Temperature Critical"));
		
		SimpleTemperatureEventGenerator gen = new SimpleTemperatureEventGenerator(cepRT, 1000, 100);
		
		gen.startSendingTemperatureReadings();
	}
}
