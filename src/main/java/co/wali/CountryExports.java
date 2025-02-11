package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CountryExports {

    public void run() {
        DirectoryResource directoryResource = new DirectoryResource();
        List<String> listString = Arrays.asList("coffee", "cotton");
        for (File file : directoryResource.selectedFiles()) {
            FileResource fr = new FileResource(file);
            CSVParser parser = fr.getCSVParser();
            fetchData_filterAsArray(parser, listString);
        }

//        fetchData_filterAsString("src/main/resources/exportdata.csv", "coffee", "cotton");


    }

    private void fetchData_filterAsString(String filePath, String filter_1, String filter_2) {
        try {
            FileResource fr = new FileResource(filePath);
            CSVParser parser = fr.getCSVParser();
            int idx = 1;
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                if (record.get("Exports").contains(filter_1) && record.get("Exports").contains(filter_2)) {
                    country = record.get("Country");
                    exports = record.get("Exports");
                    value = record.get("Value (dollars)");
                    System.out.println((idx++) + "." + country + ": " + exports + ": " + value);
                }
            }

        } catch (Exception e) {
//            e.printStackTrace();
            CustomLogger.logError("Error Message: ", e);
        }
    }

    private void fetchData_filterAsArray(CSVParser parser, List<String> filterSearch) {
        try {


            int idx = 1;
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                country = record.get("Country");
                exports = record.get("Exports");
                value = record.get("Value (dollars)");
                boolean matches = filterSearch.stream().allMatch(exports::contains);

                if (matches) {
                    String strRegex = value.replace("$", "").replace(",", "");
                    long amount = Long.parseLong(strRegex);
//                    System.out.println((idx++) + "." + country + ": " + exports + ": " + value);
                    System.out.println("SN_" + (idx++) + ", RowNum: " + record.getRecordNumber() + ". " + country + ": " + exports + ": " + value);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}