import org.apache.edgent.function.Function;

import java.util.ArrayList;

public class EEGMapper  implements Function<Double, Double> {
    double prevValue;
    double diff;
    int windowCounter;
    int recordsCount;
    int totalRecords;
//    ArrayList<Double> results;

    public EEGMapper(int cap)
    {
//        results = new ArrayList<>(cap);
        totalRecords = cap;
    }

    @Override
    public Double apply(Double value) {
        if(recordsCount > totalRecords) {
            return null;
        }

        diff += Math.pow(value - prevValue, 2);

        prevValue = value;

        windowCounter++;

        // a full one second has passed, perform the analytics
        if (windowCounter >= 178)
        {
            diff = Math.sqrt(diff);
//            results.add(diff);
            recordsCount++;
            windowCounter = 0;
            prevValue = 0.0;

//            System.out.println("apply: " + recordsCount);

            return diff;
        }
        else
            return null;
    }
}
