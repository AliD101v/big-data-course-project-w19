import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.edgent.function.Consumer;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

public class EEGAnalyzer implements Consumer<Double> {
    int recordsCount;
    int totalRecords;
    int tpCount;
    int fpCount;

    String fileName;
    CSVParser csvParser;
    Iterator<CSVRecord> csvRecordIterator;

    double minValue;

    String description;

    public EEGAnalyzer(int cap, String classFile, double threshold, String desc)
    {
        minValue = threshold;
        totalRecords = cap;
        tpCount = 0;
        fpCount = 0;

        fileName = classFile;
        description = desc;

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
        if (csvRecordIterator.hasNext() && recordsCount < totalRecords) {
            CSVRecord csvRecord = csvRecordIterator.next();
            recordsCount++;
            int yvalue = Integer.valueOf(csvRecord.get("y"));

            // classify
            if (value >= minValue) // it's positive
            {
                if (yvalue == 1) // it's true positive
                    tpCount++;
                else
                    fpCount++; // oops, it's a false positive
            }


//            System.out.println("analyze: " + recordsCount);

            if (recordsCount >= totalRecords)
            {
                System.out.println("=================================");
                System.out.println(description);
                System.out.println("True positives: " + tpCount);
                System.out.println("False positives: " + fpCount);
                System.exit(0);
            }
        }
        else
        {
            System.out.println("=================================");
            System.out.println(description);
            System.out.println("True positives: " + tpCount);
            System.out.println("False positives: " + fpCount);
            System.exit(0);

        }
    }
}
