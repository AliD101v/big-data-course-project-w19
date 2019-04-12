import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.edgent.function.Supplier;

public class EEGSensor implements Supplier<Integer> {
    double currentTemp = 65.0;
    String fileName;
    CSVParser csvParser;
    Iterator<CSVRecord> csvRecordIterator;
//    CSVRecord prevRecord;
    int prevDiff;

    EEGSensor(){

    }

    EEGSensor(String fName)
    {
        fileName = fName;

        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withFirstRecordAsHeader()
                    .withIgnoreHeaderCase()
                    .withTrim());
            csvRecordIterator = csvParser.iterator();
        }
        catch (Exception e){}

        prevDiff = 0;
    }

    //Returns a new temperature reading randomly, this temp is based off of old temp added to a random modifier
    @Override
    public Integer get() {

        CSVRecord csvRecord;
        int value = 0;
        int prevValue = 0;
        int diff = 0;

        for (int i = 0; i < 178; i++) {
            if (csvRecordIterator.hasNext()) {
                csvRecord = csvRecordIterator.next();
//                prevRecord = csvRecord;

//                String timestamp = csvRecord.get("timestamp");
                value = Integer.parseInt(csvRecord.get("value"));

                diff += Math.pow(value - prevValue, 2);

                prevValue = value;
            }
            else // return prevRecord
            {
//            value = prevVal;
                if (diff != 0)
                    return diff;
                else
                    return prevDiff;
            }
        }

        diff = (int)Math.sqrt(diff);

        prevDiff = diff;

        return diff;
    }

    private void readCSV() throws IOException
    {
        try (
                Reader reader = Files.newBufferedReader(Paths.get(fileName));
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase()
                        .withTrim());
        ) {
            for (CSVRecord csvRecord : csvParser) {
                // Accessing values by Header names
                String timestamp = csvRecord.get("timestamp");
                String value = csvRecord.get("value");

                System.out.println("Record No - " + csvRecord.getRecordNumber());
                System.out.println("---------------");
                System.out.println("timestamp : " + timestamp);
                System.out.println("value : " + value);
                System.out.println("---------------\n\n");
            }
        }
    }

}
