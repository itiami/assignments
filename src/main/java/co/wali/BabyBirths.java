package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.Reader;

public class BabyBirths {
    public void run() {
        printNames();
    }

    private void printNames() {
        String csvFile_1 = "src/main/resources/module_5/example-small.csv";
        int idx = 1;
        try {
            Reader reader = new FileReader(csvFile_1);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);
            for (CSVRecord record : records) {
                if (record.get(1).equals("M")){
                    System.out.println(idx++ + ": " + record.get(0));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
