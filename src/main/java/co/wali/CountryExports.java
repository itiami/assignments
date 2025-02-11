package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class CountryExports {

    public void run() {
//        DirectoryResource directoryResource = new DirectoryResource();
//        List<String> listString = Arrays.asList("coffee", "cotton");
//        for (File file : directoryResource.selectedFiles()) {
//            FileResource fr = new FileResource(file);
//            CSVParser parser = fr.getCSVParser();
//            System.out.println(countryInfo(parser, "Germany"));
//        }

        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
//        System.out.println(countryInfo(parser, "Germany"));
//        listExportersTwoProducts(parser, "gold", "diamonds");
//        fetchData_filterAsArray(parser, Arrays.asList("gold"));
        bigExporters(parser, "$999,999,999,");
    }

    private String countryInfo(CSVParser parser, String country) {
        for (CSVRecord record : parser.getRecords()) {
            String countryCol = record.get("Country").trim();
            if (countryCol.contains(country)) {
                String exportsCol = record.get("Exports");
                String valueCol = record.get("Value (dollars)");
                return countryCol + ": " + exportsCol + ": " + valueCol;
            }
        }
        return "NOT FOUND";
    }

    private void listExportersTwoProducts (CSVParser parser, String exportItem1, String exportItem2 ) {
        try {
            int idx = 1;
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                if (record.get("Exports").contains(exportItem1) && record.get("Exports").contains(exportItem2 )) {
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


    private void bigExporters  (CSVParser parser, String amount ) {
        try {
            int idx = 1;
            for (CSVRecord record : parser) {
                String country = record.get("Country");
                String exports = record.get("Exports");
                long value = Long.parseLong(record.get("Value (dollars)").replace("$","").replace(",",""));

                long parseAmount = Long.parseLong(amount.replace("$","").replace(",",""));

                if (value > parseAmount) {
                    System.out.println((idx++) + ". " + country + ": " + record.get("Value (dollars)"));
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
//                    System.out.println("SN_" + (idx++) + ", RowNum: " + record.getRecordNumber() + ". " + country + ": " + exports + ": " + value);
                    System.out.println(record.size());
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}