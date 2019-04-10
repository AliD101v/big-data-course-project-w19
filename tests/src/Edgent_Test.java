import static org.apache.edgent.function.Functions.identity;

import java.util.concurrent.TimeUnit;

import org.apache.edgent.analytics.sensors.Filters;
import org.apache.edgent.analytics.sensors.Range;
import org.apache.edgent.analytics.sensors.Ranges;
//import org.apache.edgent.samples.utils.sensor.SimulatedTemperatureSensor;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

public class Edgent_Test {

    /**
     * Optimal temperature range (in Fahrenheit)
     */
    static double OPTIMAL_TEMP_LOW = 77.0;
    static double OPTIMAL_TEMP_HIGH = 91.0;
    static Range<Double> optimalTempRange = Ranges.closed(OPTIMAL_TEMP_LOW, OPTIMAL_TEMP_HIGH);

    DirectProvider dp = new DirectProvider();

    Topology top = dp.newTopology("TemperatureSensor");

    // The rest of the code pieces belong here

    public static void main(String[] args)
    {
        System.out.println();
    }

}
