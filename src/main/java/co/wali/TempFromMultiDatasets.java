package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class TempFromMultiDatasets {
    public void run() {
        Path rootPath = Paths.get("D:\\JavaApp\\Java_Duke_IntelliJ\\src\\main\\resources\\nc_weather");
//        String filePath = "D:\\JavaApp\\Java_Duke_IntelliJ\\src\\main\\resources\\nc_weather\\2015\\weather-2015-09-08.csv";
        String filePath = rootPath.toAbsolutePath() + "\\2015\\weather-2015-09-01.csv";

        List<Path> allFiles = new ArrayList<>();
        readDirforCsv(rootPath, allFiles);
        System.out.println("Found files:");
//        allFiles.forEach(System.out::println);
        // or

        AtomicInteger idx = new AtomicInteger(1);
        /*for (Path files : allFiles) {
            System.out.println((idx.getAndIncrement()) + " : " + files.toString());
        }*/

//        the ForEach Function using Lambda Expression
//        allFiles.forEach(System.out::println);
// or
        allFiles.forEach(x -> System.out.println((idx.getAndIncrement()) + " : " + x));
        System.out.println("Highest Temp: " + readCsvByCSVPerser(filePath).get("TemperatureF"));
    }


//    read csv files from directory recursively ..

    private void readDirforCsv(Path currentPath, List<Path> allFiles) {
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(currentPath)) {
            for (Path entry : stream) {
                if (Files.isDirectory(entry)) {
                    readDirforCsv(entry, allFiles);
                } else {
                    if (entry.toString().contains("csv")) {
                        allFiles.add(entry);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private CSVRecord readCsvByCSVPerser(String csvFile) {
        CSVRecord largestSoFar = null;
        try (Reader reader = new FileReader(csvFile)) {
            CSVParser csvParser = new CSVParser(reader, CSVFormat.RFC4180.withFirstRecordAsHeader());
            for (CSVRecord currentRow : csvParser) {
                if (largestSoFar == null) {
                    largestSoFar = currentRow;
                    System.out.println("Current Temp: " + largestSoFar.get("TemperatureF"));
                } else {
                    double currentTemp = Double.parseDouble(currentRow.get("TemperatureF"));
                    double largestTemp = Double.parseDouble(largestSoFar.get("TemperatureF"));
                    if (currentTemp > largestTemp) {
                        largestSoFar = currentRow;
                    }

                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return largestSoFar;
    }

}
