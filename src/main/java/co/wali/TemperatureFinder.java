package co.wali;

import edu.duke.*;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class TemperatureFinder {

    public void run(){
//        temperatureFromSingleFile("src/main/resources/nc_weather/2014/weather-2014-01-20.csv");
    temperateFromMultiFile();
    }
    private void temperateFromMultiFile(){
        double maxTemp = 0.0;
        String maxTemp_Date = "";
        double lowestTemp = Double.MAX_VALUE;
        String lowestTemp_date = "";

        double lowestHumidity = Double.MAX_VALUE;
        String loestHumidityDate = "";

        DirectoryResource directoryResource = new DirectoryResource();
        for (File file: directoryResource.selectedFiles()){
            FileResource fr = new FileResource(file.getAbsolutePath());
            CSVParser parser = fr.getCSVParser();
            int totalRow = 0;
            double tempCount = 0;

            try {
                for (CSVRecord currentRecords: parser.getRecords()) {
                    totalRow++;
                    String date = currentRecords.get("DateUTC");
                    double temp = Double.parseDouble(currentRecords.get("TemperatureF"));
                    tempCount += Double.parseDouble(currentRecords.get("TemperatureF"));
                    double humidity = Double.parseDouble(currentRecords.get("Humidity"));
                    if (temp > maxTemp) {
                        maxTemp = temp;
                        maxTemp_Date = date;
                    }

                    if (temp < lowestTemp){
                        lowestTemp = temp;
                        lowestTemp_date = date;
                    }

                    if (humidity < lowestHumidity){
                        lowestHumidity = humidity;
                        loestHumidityDate = date;
                    }
                }
                System.out.println("Average temperature: " + tempCount/totalRow);
            }catch (Exception e){
                e.printStackTrace();
            }
        };

//        if (temperature != Double.MIN_VALUE) {
//            System.out.println(tempFile + "==> " + tempDate + "==> "+ temperature);
//        } else {
//            System.out.println("No valid temperature data found.");
//        }
        System.out.println("Max Temperature: " + maxTemp + "==>" + maxTemp_Date);
        System.out.println("Lowest Temperature: " + lowestTemp + "==>" + lowestTemp_date);
        System.out.println("Lowest Humidity: " + lowestHumidity + ": "+ loestHumidityDate);
    }

    private void temperatureFromSingleFile(String file){
        if (Files.exists(Path.of(file))){
            try {
                FileResource fr = new FileResource(file);
                CSVParser parser = fr.getCSVParser();
                double maxTemp = 0.0;
                String maxTemp_Date = "";
                double lowestTemp = Double.MAX_VALUE;
                String lowestTemp_date = "";
                int totalRow = 0;
                double tempCount = 0;
                double lowestHumidity = Double.MAX_VALUE;
                String loestHumidityDate = "";

                for (CSVRecord currentRecords: parser.getRecords()) {
                    totalRow++;
                    String date = currentRecords.get("DateUTC");
                    double temp = Double.parseDouble(currentRecords.get("TemperatureF"));
                    tempCount += Double.parseDouble(currentRecords.get("TemperatureF"));
                    double humidity = Double.parseDouble(currentRecords.get("Humidity"));

                    if (temp > maxTemp) {
                        maxTemp = temp;
                        maxTemp_Date = date;
                    }

                    if (temp < lowestTemp){
                        lowestTemp = temp;
                        lowestTemp_date = date;
                    }

                    if (humidity < lowestHumidity){
                        lowestHumidity = humidity;
                        loestHumidityDate = date;
                    }
                }
                System.out.println("Max Temperature: " + maxTemp + "==>" + maxTemp_Date);
                System.out.println("Lowest Temperature: " + lowestTemp + "==>" + lowestTemp_date);
                System.out.println("Total records: " + totalRow + "Total Temperature: " + tempCount);
                System.out.println("Average temperature: " + tempCount/totalRow);
                System.out.println("Lowest Humidity: " + lowestHumidity + ": "+ loestHumidityDate);
            }catch (Exception e){
                e.printStackTrace();
            }
        }else {
            System.out.println("File not Found");
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
