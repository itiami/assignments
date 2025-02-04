package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

public class HotestTemperatureFinder {

    public void run(){
        String filename = "src/main/resources/nc_weather/2012/weather-2012-01-01.csv";
        ReadFromCSV readFromCSV = new ReadFromCSV();
        Path path = Paths.get(filename);

        double maxTemperature = Double.MIN_VALUE;
        String maxTempDate = "";
        String maxTempFile = "";

        DirectoryResource directoryResource = new DirectoryResource();
        for (File file: directoryResource.selectedFiles()){
            FileResource fr = new FileResource(file.getAbsolutePath());
            CSVParser parser = fr.getCSVParser();

            try {
                for (CSVRecord currentRecords: parser.getRecords()) {
                    String date = currentRecords.get("DateUTC");
                    double temp = Double.parseDouble(currentRecords.get("TemperatureF"));
                    if (temp > maxTemperature) {
                        maxTemperature = temp;
                        maxTempDate = date;
                        maxTempFile = file.getName();
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        };

        if (maxTemperature != Double.MIN_VALUE) {
            System.out.println(maxTempFile + "==> " + maxTempDate + "==> "+ maxTemperature);
        } else {
            System.out.println("No valid temperature data found.");
        }



        try {
            FileResource fr = new FileResource(filename);
            CSVParser parser = fr.getCSVParser();
            double maxTemp = 0.0;
            String maxTemp_Date = "";

            for (CSVRecord currentRecords: parser.getRecords()) {
                String date = currentRecords.get("DateUTC");
                double temp = Double.parseDouble(currentRecords.get("TemperatureF"));
                if (temp > maxTemp) {
                    maxTemp = temp;
                    maxTemp_Date = date;
                }
            }
            System.out.println(maxTemp + "==>" + maxTemp_Date);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    private static void listCSVFiles(File directory) {
        try (Stream<Path> paths = Files.walk(directory.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv")) // Only select CSV files
                    .forEach(path -> System.out.println("Found CSV: " + path.toAbsolutePath()));
        } catch (IOException e) {
            System.err.println("Error reading directory: " + directory.getAbsolutePath());
            e.printStackTrace();
        }
    }


    private CSVRecord hottestHour(CSVParser parser){
        CSVRecord largestSoFar = null;
        for ( CSVRecord currentRow : parser ) {
            if (largestSoFar == null){
                largestSoFar = currentRow;
            }else {
                //
            }
        }
        return largestSoFar;
    }

}
