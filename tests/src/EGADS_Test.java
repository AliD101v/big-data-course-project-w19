import java.util.ArrayList;
import java.util.Properties;
import java.io.FileInputStream;
import java.io.InputStream;

import com.yahoo.egads.data.TimeSeries;
import com.yahoo.egads.utilities.*;
import com.yahoo.egads.models.adm.*;
import com.yahoo.egads.models.tsmm.*;
import com.yahoo.egads.data.Anomaly.IntervalSequence;
import com.yahoo.egads.data.Anomaly.Interval;
import java.io.File;

public class EGADS_Test
{
    public static void main(String[] args)
    {System.out.println("Working Directory = " +
            System.getProperty("user.dir"));
//        if (args.length == 0) {
//            System.err.println("Usage: java Egads config.ini (input [STDIN,CSV])");
//            System.exit(1);
//        }
        // for now it is assumed it's a static file.
        Properties p = new Properties();
//        String config = args[0];
        String trainDataFile = "data/train_data.csv";
        String testDataFile = "data/test_data.csv";
        String config = "data/sample_config.ini";
        File f = new File(config);
        boolean isRegularFile = f.exists();

        try {
            if (isRegularFile) {
                InputStream is = new FileInputStream(config);
                p.load(is);
            } else {
                FileUtils.initProperties(config, p);
            }

            // Set the input type.
//            InputProcessor ip = null;
//            if (p.getProperty("INPUT") == null || p.getProperty("INPUT").equals("CSV")) {
//                ip = new FileInputProcessor(dataFile);
//            } else {
//                ip = new StdinProcessor();
//            }

            // Process the input the we received (either STDIN or as a file).
//            ip.processInput(p);

            p.setProperty("NUM_WEEKS", "5");
            p.setProperty("NUM_TO_DROP", "0");
            p.setProperty("THRESHOLD", "mapee#100,mase#10");

            // Parse the input timeseries.
            ArrayList<TimeSeries> trainData = FileUtils
                    .createTimeSeries(trainDataFile, p);

            ArrayList<TimeSeries> testData = FileUtils
                    .createTimeSeries(testDataFile, p);

            TimeSeries.DataSequence ds = new TimeSeries.DataSequence();
//            ds.addAll(data);

            OlympicModel tsmodel = new OlympicModel(p);
            tsmodel.train(trainData.get(0).data);

            TimeSeries.DataSequence sequence = new TimeSeries.DataSequence(trainData.get(0).startTime(), trainData.get(0).lastTime(), 3600);
            sequence.setLogicalIndices(trainData.get(0).startTime(), 3600);
            tsmodel.predict(sequence);
//            tsmodel.predict(data.get(0).data);





            ExtremeLowDensityModel admodel = new ExtremeLowDensityModel(p);

            IntervalSequence anomalies = admodel.detect(testData.get(0).data, sequence);


            // Initialize the DBScan anomaly detector.
            DBScanModel dbs = new DBScanModel(p);
            dbs.tune(trainData.get(0).data, sequence, null);
            IntervalSequence anomaliesdb = dbs.detect(testData.get(0).data, sequence);

            // Initialize the SimpleThreshold anomaly detector.
            SimpleThresholdModel stm = new SimpleThresholdModel(p);
            stm.tune(trainData.get(0).data, sequence, null);
            IntervalSequence anomaliesstm = stm.detect(testData.get(0).data, sequence);


            System.out.println(anomalies.size());
            System.out.println(anomaliesdb.size());
            System.out.println(anomaliesstm.size());
        }
        catch (Exception e){}
    }
}