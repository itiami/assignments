package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.*;

public class CountryExports {

    public void run() {
        FileResource fr = new FileResource();
        CSVParser parser = fr.getCSVParser();
//        System.out.println(countryInfo(parser, "Germany"));
//        listExportersTwoProducts(parser, "cocoa");
//        fetchData_filterAsArray(parser, Arrays.asList());
//        fetchData_filterAsArray(parser, Arrays.asList("cocoa"));
        bigExporters(parser, "$999,999,999,");
    }




    private static long parseValue(String value) {
        value = value.replace("$", "").replace(",", ""); // Remove dollar sign and commas
        return Long.parseLong(value);
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

    private void listExportersTwoProducts(CSVParser parser, String exportItem1) {
        try {
            int idx = 1;
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                if (record.get("Exports").contains(exportItem1)) {
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


    private void bigExporters(CSVParser parser, String amount) {
        try {
            List<CSVRecord> records = new ArrayList<>();
            long parseAmount = Long.parseLong(amount.replace("$", "").replace(",", ""));
            for (CSVRecord record : parser) {
                long value = Long.parseLong(record.get("Value (dollars)").replace("$", "").replace(",", ""));
                if (value > parseAmount) {
                    records.add(record);
                }
            }

//          Data Sorting............
//          Method_1. using - (Minus sign)
//          records.sort(Comparator.comparingLong(record -> -Long.parseLong(record.get("Value (dollars)").replace("$", "").replace(",", ""))));
//          Method_2. using Comparator.reverOrder()
            records.sort(Comparator.comparing(record -> Long.parseLong(record.get("Value (dollars)").replace("$", "").replace(",", "")), Comparator.reverseOrder()));
          records.sort(Comparator.comparing(record -> Long.parseLong(record.get("Value (dollars)").replace("$", "").replace(",", "")), Comparator.reverseOrder()));
//          Method_3. Collections.sort(records, (a, b) ->
            /*records.sort((a, b) -> Long.compare(
                    Long.parseLong(b.get("Value (dollars)").replace("$", "").replace(",", "")),
                    Long.parseLong(a.get("Value (dollars)").replace("$", "").replace(",", ""))));*/


            int idx = 1;
            for (CSVRecord record : records) {
                System.out.println(idx++ + ". " + "S/N: " + record.getRecordNumber()  + record.get("Country") + ": " + record.get("Value (dollars)"));
            }

        } catch (Exception e) {
//            e.printStackTrace();
            CustomLogger.logError("Error Message: ", e);
        }
    }


    private void fetchData_filterAsArray(CSVParser parser, List<String> filterSearch) {
        try {
            List<CSVRecord> records = new ArrayList<>();
            int idx = 1;
            for (CSVRecord record : parser) {
                boolean matches = filterSearch.stream().allMatch(record.get("Exports")::contains);
                long value = Long.parseLong(record.get("Value (dollars)").replace("$", "").replace(",", ""));
                if (matches) {
                    records.add(record);
                }
            }


//            records.sort((a,b)-> Long.compare(
//                    Long.parseLong(b.get("Value (dollars)").replace("$", "").replace(",", "")),
//                    Long.parseLong(a.get("Value (dollars)").replace("$", "").replace(",", ""))
//            ));
            

            for (CSVRecord record: records){
                System.out.println(idx++ + ". " + record.get("Country") + ": " + record.get("Value (dollars)"));
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}