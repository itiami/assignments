package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.text.NumberFormat;
import java.util.*;

public class ReadFileByApacheLib {
    public void run(){

//        JFileChooser fileChooser = new JFileChooser();
//        int returnValue = fileChooser.showOpenDialog(null);
//
//        if (returnValue == JFileChooser.APPROVE_OPTION) {
//            File selectedFile = fileChooser.getSelectedFile();
//            System.out.println("Selected file: " + selectedFile.getAbsolutePath());
//
//            try (BufferedReader br = new BufferedReader(new FileReader(selectedFile))) {
//                String line;
//                while ((line = br.readLine()) != null) {
//                    System.out.println(line);
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }


//        byJavaIoReader();
        byJavaIoReader(List.of("cotton","flowers"));
    }

    private void byJavaIoReader(){
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        List<CountryExportModel> countries = new ArrayList<>();
        String csvFile_1 = "src/main/resources/exports_small.csv";
        String csvFile_2 = "src/main/resources/exportdata.csv";
        int idx = 1;
        try {
            Reader reader = new FileReader(csvFile_2);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader("Country", "Exports", "Value (dollars)")
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);

            for (CSVRecord record: records){
                String country = record.get("Country");
                String exports = record.get("Exports").toLowerCase();
                String value = record.get("Value (dollars)");

                // to check if the value is a number before parsing..
                if (!value.matches(".*\\d.*")){
                    continue;
                }

                long amount = Long.parseLong(value.replace("$", "").replace(",", ""));
                countries.add(new CountryExportModel(country, exports, amount));
            }

//            System.out.println("Parsed Countries: " + countries);
//            countries.sort(Comparator.comparing(CountryExportModel::getValue).reversed());
            for (CountryExportModel data: countries){
                if (data.getValue() >= 1_000_000_000_000L){
                    System.out.println(idx++ + ": " +
                            data.getCountry() +
                            ", " + currency.format(data.getValue()));
                }
                /*
                1: China, $2,252,000,000,000.00
                2: European Union, $2,173,000,000,000.00
                3: Germany, $1,547,000,000,000.00
                4: United States, $1,610,000,000,000.00
                */

            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void byJavaIoReader(List<String> searchFileter){
        NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);
        List<CountryExportModel> countries = new ArrayList<>();
        String csvFile_1 = "src/main/resources/exports_small.csv";
        String csvFile_2 = "src/main/resources/exportdata.csv";
        int idx = 1;
        try {
            Reader reader = new FileReader(csvFile_2);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setHeader("Country", "Exports", "Value (dollars)")
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);

            for (CSVRecord record: records){
                String country = record.get("Country");
                String exports = record.get("Exports").toLowerCase();
                String value = record.get("Value (dollars)");

                // to check if the value is a number before parsing..
                if (!value.matches(".*\\d.*")){
                    continue;
                }

                long amount = Long.parseLong(value.replace("$", "").replace(",", ""));
                countries.add(new CountryExportModel(country, exports, amount));
            }

//            System.out.println("Parsed Countries: " + countries);
//            countries.sort(Comparator.comparing(CountryExportModel::getValue).reversed());
            for (CountryExportModel data: countries){
                /*if (data.getValue() >= 1_000_000_000_000L){
                    System.out.println(idx++ + ": " +
                            data.getCountry() +
                            ", " + currency.format(data.getValue()));
                }*/
                /*
                1: China, $2,252,000,000,000.00
                2: European Union, $2,173,000,000,000.00
                3: Germany, $1,547,000,000,000.00
                4: United States, $1,610,000,000,000.00
                */

//                Data using SearchFilter..
                boolean matchData = searchFileter.stream().allMatch(data.getExports()::contains);
                if (matchData){
                    System.out.println(idx++ + ": " +
                            data.getCountry() +
                            ", " + currency.format(data.getValue()));
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }


    public void findPath() {
        ClassLoader classLoader = ReadFileByApacheLib.class.getClassLoader();
        URL resource = classLoader.getResource("config.properties");

        if (resource != null) {
            System.out.println(resource.getPath());
        } else {
            System.out.println("Resource not found!");
        }

    }

}


