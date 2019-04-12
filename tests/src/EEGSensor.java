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
    Random rand;
    String fileName;
    CSVParser csvParser;
    Iterator<CSVRecord> csvRecordIterator;
    CSVRecord prevRecord;

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
    }

    //Returns a new temperature reading randomly, this temp is based off of old temp added to a random modifier
    @Override
    public Integer get() {

        CSVRecord csvRecord;
        int value = 0;

        if (csvRecordIterator.hasNext()) {
            csvRecord = csvRecordIterator.next();
            prevRecord = csvRecord;

            String timestamp = csvRecord.get("timestamp");
            value = Integer.parseInt(csvRecord.get("value"));
        }
        else // return prevRecord
        {
            String timestamp = prevRecord.get("timestamp");
            value = Integer.parseInt(prevRecord.get("value"));
        }

        return value;
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
