import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.edgent.function.Consumer;
import org.apache.edgent.function.Function;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

public class EEGTrainer implements Consumer<Double>{
    int recordsCount;
    int totalRecords;

    String fileName;
    CSVParser csvParser;
    Iterator<CSVRecord> csvRecordIterator;

    double minValue;

    public EEGTrainer(int cap, String classFile)
    {
        minValue = Double.MAX_VALUE;
        totalRecords = cap;

        fileName = classFile;

        try {
            Reader reader = Files.newBufferedReader(Paths.get(fileName));
            csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                    .withHeader("y")
                    .withIgnoreHeaderCase()
                    .withTrim());
            csvRecordIterator = csvParser.iterator();
        }
        catch (Exception e){}

    }

    @Override
    public void accept(Double value) {
        // find the smallest value in the stream such that it passes the class
        // read the next line

        if (csvRecordIterator.hasNext() && recordsCount < totalRecords) {
            CSVRecord csvRecord = csvRecordIterator.next();
            recordsCount++;
            int yvalue = Integer.valueOf(csvRecord.get("y"));
            if (yvalue == 1)
            {
                if (value < minValue)
                    minValue = value;
            }
            System.out.println("train: " + recordsCount);
        }
        else
            System.out.println("min value: " + minValue);
    }
}
