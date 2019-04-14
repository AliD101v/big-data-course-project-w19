//import org.apache.edgent.samples.utils.sensor.SimulatedTemperatureSensor;
import org.apache.edgent.execution.Job;
import org.apache.edgent.providers.direct.DirectProvider;
import org.apache.edgent.topology.TStream;
import org.apache.edgent.topology.Topology;

import java.util.concurrent.Future;

public class Edgent_Test {
    private static final double MIN_SQ_SUM_DIFF_TRAIN = 550;
    private static final String TRAIN_DATA_FILE = "./data/train_data.csv";
    private static final String TEST_DATA_FILE = "./data/test_data.csv";
    private static final String CLASS_TRAIN_DATA_FILE = "./data/y_train_data.csv";
    private static final String CLASS_TEST_DATA_FILE = "./data/y_test_data.csv";
    private static final int TRAIN_DATA_SIZE = 7000;
    private static final int TEST_DATA_SIZE = 4500;

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

        // Analyze the training data
//        EEGSensor sensorTrain = new EEGSensor(TRAIN_DATA_FILE);
//        TStream<Double> trainStream = topology.generate(sensorTrain);
//
//        EEGMapper trainMapper = new EEGMapper(TRAIN_DATA_SIZE);
//        TStream<Double> trainMapStream = trainStream.map(trainMapper);
//
//        EEGAnalyzer trainAnalyzer = new EEGAnalyzer(TRAIN_DATA_SIZE, CLASS_TRAIN_DATA_FILE, MIN_SQ_SUM_DIFF_TRAIN, "training");
//        trainMapStream.sink(trainAnalyzer);


        // Analyze the test data
        EEGSensor sensorTest = new EEGSensor(TEST_DATA_FILE);
        TStream<Double> testStream = topology.generate(sensorTest);

        EEGMapper testMapper = new EEGMapper(TEST_DATA_SIZE);
        TStream<Double> testMapStream = testStream.map(testMapper);

        EEGAnalyzer testAnalyzer = new EEGAnalyzer(TEST_DATA_SIZE, CLASS_TEST_DATA_FILE, MIN_SQ_SUM_DIFF_TRAIN, "test");
        testMapStream.sink(testAnalyzer);


        // Minimum squared sum of consecutive pairwise differences for class 1 (train_data.csv)
        // is 185.15897934245874
//        EEGTrainer trainer = new EEGTrainer(DATA_SIZE, CLASS_DATA_FILE);
//        mapStream.sink(trainer);



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
//        timeSeriesTStream.print();

        Future<Job> job = dp.submit(topology);

    }
}
