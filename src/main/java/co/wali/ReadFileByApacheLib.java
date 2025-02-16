package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

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


        byJavaIoReader();
    }

    private void byJavaIoReader(){
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
            countries.sort(Comparator.comparing(CountryExportModel::getValue).reversed());
            for (CountryExportModel data: countries){
                System.out.println((idx++) + ": " + data.getCountry() + " ===> " + data.getValue());
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


