import org.apache.commons.csv.CSVRecord;
import org.apache.edgent.function.Consumer;

import java.util.ArrayList;

public class EEGAnalyzer implements Consumer<Double> {
    double prevValue = 0.0;
    double diff = 0.0;
    int windowCounter = 0;
    ArrayList<Double> results;

    public EEGAnalyzer()
    {
        results = new ArrayList<>(11500);
    }

    @Override
    public void accept(Double value) {
        diff += Math.pow(value - prevValue, 2);

        prevValue = value;

        windowCounter++;

        // a full one second has passed, perform the analytics
        if (windowCounter >= 178)
        {
            diff = Math.sqrt(diff);
            results.add(diff);
            windowCounter = 0;
            prevValue = 0.0;
            diff = 0.0;
        }
    }
}
