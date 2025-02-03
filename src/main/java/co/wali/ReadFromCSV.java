package co.wali;

import edu.duke.FileResource;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

public class ReadFromCSV {
    public void run() {
//        readCsvByScanner("src/main/resources/foods.csv");
//        readCsvByBufferReader("src/main/resources/foods.csv");
//        readCsvByCSVPerser("src/main/resources/foods.csv");
//        tester("src/main/resources/exports_small.csv", "", "");
        String csvFile = "src/main/resources/exports_small.csv"; // Path to your CSV file
        List<String> filterSearch = Arrays.asList("gold","diamonds");
        searchFilter(csvFile, filterSearch);
//        countryInfo();
        FileResource fr = new FileResource("src/main/resources/exports_small.csv");
        CSVParser parser = fr.getCSVParser();
//        bigExporters(parser, "$999,999,999");
    }


    public void searchFilter(String csvFile, List<String> filterSearch) {
        String line;
        String csvSplitBy = ","; // Split by comma, although exports may contain commas, handle quotes

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Read header
            br.readLine(); // Skip the header line

            // Iterate through each line in the CSV file
            while ((line = br.readLine()) != null) {
                // Parse the line, handling quotes properly
                String[] data = parseCSVLine(line);
                //System.out.println(Arrays.toString(data));

                if (data != null && data.length == 3) {
                    String country = data[0].trim();
                    String exports = data[1].trim();
                    String value = data[2].trim();

                    // Check if all filterSearch strings are in the exports column
                    /*if (filterSearch.stream().allMatch(x->exports.contains(x))){
                        // Print the matching record
                        System.out.println("Country: " + country
                                + ", Exports: " + exports
                                + ", Value: " + value);
                    }*/
//                    or can be expressed as below..

                    if (filterSearch.stream().allMatch(exports::contains)) {
                        // Print the matching record
                        System.out.println("Country: " + country
                                + ", Exports: " + exports
                                + ", Value: " + value);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    // Helper method to parse CSV lines considering the possibility of commas within quotes
    private static String[] parseCSVLine(String line) {
        // Split by comma but ignore commas inside double quotes
        String[] tokens = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
        return Arrays.stream(tokens).map(s -> s.replaceAll("\"", "")).toArray(String[]::new);
    }


    private void tester(String filePath, String filter_1, String filter_2){
        try  {
            FileResource fr = new FileResource(filePath);
            CSVParser parser = fr.getCSVParser();
            int idx = 1;
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                if (record.get("Exports").contains(filter_1) && record.get("Exports").contains(filter_2)){
                    country = record.get("Country");
                    exports = record.get("Exports");
                    value =record.get("Value (dollars)");
                    System.out.println((idx ++) + "."+ country + ": " +exports + ": " + value );
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void countryInfo(){
        try  {
            FileResource fr = new FileResource("src/main/resources/exportdata.csv");
            CSVParser parser = fr.getCSVParser();
            for (CSVRecord record : parser) {
                String country;
                String exports;
                String value;
                if (record.get("Country").contains("Nauru")){
                    country = record.get("Country");
                    exports = record.get("Exports");
                    value =record.get("Value (dollars)");
                    System.out.println(country + ": " +exports + ": " + value );
                }
                /*else {
                    System.out.println("NOT FOUND");
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void bigExporters(CSVParser parser, String amount){
        try  {
            for (CSVRecord record : parser) {
                String country = record.get("Country");
                String value =record.get("Value (dollars)");
                if (value.length() > amount.length()){
                    System.out.println(country + " " + value + " length: " + value.length() );
                }
                /*else {
                    System.out.println("NOT FOUND");
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public String readCsvByCSVPerser(String csvFile){
        StringBuilder stringBuilder = new StringBuilder();
        try (Reader reader = new FileReader(csvFile)) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());

            for (CSVRecord record : csvParser) {
                String name = record.get("Name");
                String favoriteFood = record.get("Favorite Food");
                String favoriteColor = record.get("Favorite Color");
                System.out.println(name);

//                System.out.println("Name: " + name +
//                        ", Favorite Food: " + favoriteFood +
//                        ", Favorite Color: " + favoriteColor);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        stringBuilder.toString();
        return stringBuilder.toString();
    }


    private String readCsvByBufferReader(String filePath){
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(filePath))) {
            List<List<String>> records = reader.lines()
                    .map(line -> Arrays.asList(line.split(",")))
                    .collect(Collectors.toList());
            for (List<String> x : records) {
                System.out.println(x.get(0));
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        return "";
    }


    private String readCsvByScanner(String fileContent) {
        List<List<String>> records = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File("src/main/resources/foods.csv"))) {
            while (scanner.hasNextLine()) {
                records.add(getRecordFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        for (List<String> record : records) {
            System.out.println(record.get(0));
        }
        return "";
    }

    private List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private String readFile(String filePath) {
        Path path = Paths.get(filePath);
        if (!Files.exists(path)) {
            return null;
        } else {
            StringBuilder stringBuilder = new StringBuilder();
            try (BufferedReader bufferedReader = new BufferedReader(new FileReader(filePath))) {
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    stringBuilder.append(line.trim());
                }
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
            return stringBuilder.toString();
        }
    }
}
