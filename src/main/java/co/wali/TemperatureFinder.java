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
temperatureFromMultiFile();
    }

    private WeatherStats processWeatherData(CSVParser parser) {
        double maxTemp = Double.MIN_VALUE;
        String maxTempDate = "";
        double lowestTemp = Double.MAX_VALUE;
        String lowestTempDate = "";
        double lowestHumidity = Double.MAX_VALUE;
        double heightHumidity = Double.MIN_VALUE;
        String lowestHumidityDate = "";
        String heightHumidityDate = "";
        int totalRows = 0;
        double tempSum = 0;

        try {
            for (CSVRecord record : parser.getRecords()) {
                totalRows++;
                String date = record.get("DateUTC");
                double temp = Double.parseDouble(record.get("TemperatureF"));
                double humidity = Double.parseDouble(record.get("Humidity"));

                tempSum += temp;

                if (temp > maxTemp) {
                    maxTemp = temp;
                    maxTempDate = date;
                }

                if (temp < lowestTemp) {
                    lowestTemp = temp;
                    lowestTempDate = date;
                }

                if (humidity < lowestHumidity) {
                    lowestHumidity = humidity;
                    lowestHumidityDate = date;
                }

                if (humidity > heightHumidity){
                    heightHumidity = humidity;
                    heightHumidityDate = date;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        double avgTemp = (totalRows > 0) ? tempSum / totalRows : 0.0;

        return new WeatherStats(maxTemp, maxTempDate, lowestTemp, lowestTempDate, lowestHumidity, heightHumidity, lowestHumidityDate, heightHumidityDate ,avgTemp, totalRows);
    }

    // Method to process multiple files
    private void temperatureFromMultiFile() {
        DirectoryResource directoryResource = new DirectoryResource();

        double maxTemp = Double.MIN_VALUE;
        String maxTempDate = "";
        double lowestTemp = Double.MAX_VALUE;
        String lowestTempDate = "";
        double lowestHumidity = Double.MAX_VALUE;
        double heightHumidity = Double.MIN_VALUE;
        String lowestHumidityDate = "";
        String heightHumidityDate = "";
        double totalTempSum = 0;
        int totalRecords = 0;

        for (File file : directoryResource.selectedFiles()) {
            FileResource fr = new FileResource(file);
            WeatherStats stats = processWeatherData(fr.getCSVParser());

            // Aggregate results across multiple files
            if (stats.getMaxTemp() > maxTemp) {
                maxTemp = stats.getMaxTemp();
                maxTempDate = stats.getMaxTempDate();
            }
            if (stats.getLowestTemp() < lowestTemp) {
                lowestTemp = stats.getLowestTemp();
                lowestTempDate = stats.getLowestTempDate();
            }
            if (stats.getLowestHumidity() < lowestHumidity) {
                lowestHumidity = stats.getLowestHumidity();
                lowestHumidityDate = stats.getLowestHumidityDate();
            }

            if (stats.getHeightHumidity() > heightHumidity){
                heightHumidity = stats.getHeightHumidity();
                heightHumidityDate = stats.getHeightHumidityDate();
            }


            totalTempSum += stats.getAvgTemp() * stats.getTotalRecords();
            totalRecords += stats.getTotalRecords();
        }

        double avgTemperature = (totalRecords > 0) ? totalTempSum / totalRecords : 0.0;

        System.out.println("Max Temperature: " + maxTemp + " ==> " + maxTempDate);
        System.out.println("Lowest Temperature: " + lowestTemp + " ==> " + lowestTempDate);
        System.out.println("Lowest Humidity: " + lowestHumidity + " ==> " + lowestHumidityDate);
        System.out.println("Height Humidity: " + heightHumidity + "==> " + heightHumidityDate);
        System.out.println("Overall Average Temperature: " + avgTemperature);
    }

    // Method to process a single file
    private void temperatureFromSingleFile(String filePath) {
        if (Files.exists(Path.of(filePath))) {
            FileResource fr = new FileResource(filePath);
            WeatherStats stats = processWeatherData(fr.getCSVParser());

            System.out.println("Max Temperature: " + stats.getMaxTemp() + " ==> " + stats.getMaxTempDate());
            System.out.println("Lowest Temperature: " + stats.getLowestTemp() + " ==> " + stats.getLowestTempDate());
            System.out.println("Lowest Humidity: " + stats.getLowestHumidity() + " ==> " + stats.getLowestHumidityDate());
            System.out.println("Total Records: " + stats.getTotalRecords());
            System.out.println("Average Temperature: " + stats.getAvgTemp());
        } else {
            System.out.println("File not found.");
        }
    }


    private static void listCSVFiles(File directory) {
        try (Stream<Path> paths = Files.walk(directory.toPath())) {
            paths.filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".csv"))
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
