package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.util.Arrays;
import java.util.List;

public class CountryExports {

    public void run() {
//        fetchData_filterAsString("src/main/resources/exportdata.csv", "coffee", "cotton");
        List<String> listString = Arrays.asList("coffee", "cotton");
        fetchData_filterAsArray("src/main/resources/exportdata.csv", listString);
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
            CustomLogger.logError("Error Message: ",e);
        }
    }

    private void fetchData_filterAsArray(String filePath, List<String> filterSearch) {
        try {
            FileResource fr = new FileResource(filePath);
            CSVParser parser = fr.getCSVParser();

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
                    System.out.println("SN_" + (idx++)+ ", RowNum: " + record.getRecordNumber() + ". " + country + ": " + exports + ": " + value);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}