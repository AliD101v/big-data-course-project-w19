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
        String dataFile = "sample_input.csv";
        String config = "sample_config.ini";
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

            // Parse the input timeseries.
            ArrayList<TimeSeries> data = com.yahoo.egads.utilities.FileUtils
                    .createTimeSeries(dataFile, p);

            TimeSeries.DataSequence ds = new TimeSeries.DataSequence();
//            ds.addAll(data);

            OlympicModel tsmodel = new OlympicModel(p);
            tsmodel.train(data.get(0).data);
//            tsmodel.predict(data.get(0).data);





            ExtremeLowDensityModel admodel = new ExtremeLowDensityModel(p);
            IntervalSequence results = admodel.detect(data.get(0).data, data.get(0).data);

//            for (anomaly :) {
//
//            }


            System.out.println(results.size());
        }
        catch (Exception e){}
    }
}