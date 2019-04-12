import static org.apache.edgent.function.Functions.identity;

import java.rmi.server.ExportException;
import java.util.concurrent.TimeUnit;

import org.apache.edgent.analytics.sensors.Filters;
import org.apache.edgent.analytics.sensors.Range;
import org.apache.edgent.analytics.sensors.Ranges;
//import org.apache.edgent.samples.utils.sensor.SimulatedTemperatureSensor;
import org.apache.edgent.oplet.functional.Filter;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Edgent_Test {

    /**
     * Optimal temperature range (in Fahrenheit)
     */
    static double OPTIMAL_TEMP_LOW = 77.0;
    static double OPTIMAL_TEMP_HIGH = 91.0;
    static Range<Double> optimalTempRange = Ranges.closed(OPTIMAL_TEMP_LOW, OPTIMAL_TEMP_HIGH);
    private static final String DATA_FILE = "./data/test_data.csv";

    public static void main(String[] args) throws Exception {

        try {

        }
        catch (Exception e){}

        DirectProvider dp = new DirectProvider();

//        Topology topology = dp.newTopology("TemperatureSensor");
        Topology topology = dp.newTopology("EEGSensor");

        // Generate a stream of temperature sensor readings
//        TempSensor sensor = new TempSensor();
//        TStream<Double> temp = topology.poll(sensor, 1, TimeUnit.SECONDS);
        EEGSensor sensor = new EEGSensor(DATA_FILE);
        TStream<Integer> timeSeriesTStream = topology.poll(sensor, 1, TimeUnit.SECONDS);

        // Simple filter: Perform analytics on sensor readings to
        // detect when the temperature is completely out of the
        // optimal range and generate warnings
//        TStream<Double> simpleFiltered = temp.filter(tuple ->
//                !optimalTempRange.contains(tuple));
//        simpleFiltered.sink(tuple -> System.out.println("Temperature is out of range! "
//                + "It is " + tuple + "\u00b0F!"));

        // Deadband filter: Perform analytics on sensor readings to
        // output the first temperature, and to generate warnings
        // when the temperature is out of the optimal range and
        // when it returns to normal
//        TStream<Double> deadbandFiltered = Filters.deadband(temp,
//                identity(), optimalTempRange);
//        deadbandFiltered.sink(tuple -> System.out.println("Temperature may not be "
//                + "optimal! It is " + tuple + "\u00b0F!"));

//        TStream<Double> deadbandFiltered = Filters.deadband(timeSeriesTStream, identity(), optimalTempRange);

        // See what the timeSeries look like
        timeSeriesTStream.print();

        dp.submit(topology);

    }
}
