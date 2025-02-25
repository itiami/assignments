package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class BabyBirths {
    public void run() {
//        printNames();
//        selectFile(0.7, 0.6);
//        System.out.println(getRank(1880, "Minnie", ""));
        getRank(2012, "", "F");

        whatIsNameInYear("Isabella", 2012, 2014, "F");
    }


    private void whatIsNameInYear(String name, int year, int newYear, String gender) {
        File yearOneFile = new File("src/main/resources/module_5/us_babynames_by_year/yob" + year + ".csv");
        File yearTowFile = new File("src/main/resources/module_5/us_babynames_by_year/yob" + newYear + ".csv");
        int rankOne = 0;
        int rankTwo = 0;
        List<String> yearOneData = new ArrayList<>();
        List<String> yearTworData = new ArrayList<>();

        try {
            Iterable<CSVRecord> yearOneRecords = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(new FileReader(yearOneFile));

            Iterable<CSVRecord> yearTwoRecords = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(new FileReader(yearTowFile));


            for (CSVRecord record : yearOneRecords) {
                if (record.get(0).equals(name) && record.get(1).equals(gender)) {
                    yearOneData.add(Arrays.toString(record.values()));
                }
            }


            for (CSVRecord record : yearTwoRecords) {
                if (record.get(0).equals(name) && record.get(1).equals(gender)) {
                    yearTworData.add(Arrays.toString(record.values()));
                }
            }

            System.out.println(yearOneData);
            System.out.println(yearTworData);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void getRank(int year, String name, String gender) {
//        File file = new File("src/main/resources/module_5/us_babynames_by_year/yob" + year + ".csv");
        File file = new File("src/main/resources/module_5/testing/yob" + year + "short.csv");
        int rank = 1;
        int totalBorn = 0;
        List<CSVRecord> data = new ArrayList<>();

        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);
            for (CSVRecord record : records) {
                int born = Integer.parseInt(record.get(2));
                totalBorn += born;


                if (record.get(1).equalsIgnoreCase(gender)){
                    data.add(record);
                }
            }

            // Sorting data by birth count in descending order
            data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)), // Sorting in descending order
                    Integer.parseInt(a.get(2))
            ));

            for (CSVRecord record : data) {
                System.out.println(rank + ". " + record.get(0) + " - " + record.get(2));
                if (record.get(0).equalsIgnoreCase(name)) {
                    System.out.println("Rank of " + name + " in " + year + ": " + rank);
                    break;
                }
                rank++;
            }

            data.forEach(System.out::println);

        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }
    }

    private void selectFile(double widthPercent, double heightPercent) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) (screenSize.width * widthPercent);
        int height = (int) (screenSize.height * heightPercent);

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); // Windows Look and Feel
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setMultiSelectionEnabled(true);
            fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            fileChooser.setDialogTitle("Select CSV File(s)");
            fileChooser.setPreferredSize(new Dimension(width, height));

            String resourcesPath = Paths.get("src", "main", "resources").toAbsolutePath().toString();
            File resourcesDir = new File(resourcesPath);
            if (resourcesDir.exists() && resourcesDir.isDirectory()) {
                fileChooser.setCurrentDirectory(resourcesDir);
            }

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                File[] selectedFiles = fileChooser.getSelectedFiles();

                for (File file : selectedFiles) {
                    printNames(file);
                }
            }
        });
    }

    private void readFile(File file) {
        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder().setSkipHeaderRecord(true).get().parse(reader);

            for (CSVRecord record : records) {
                System.out.println(record);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void printNames(File file) {
//        String csvFile_1 = "src/main/resources/module_5/us_babynames_test/example-small.csv";

        int idx = 1;
        List<String> girls = new ArrayList<>();
        List<String> boys = new ArrayList<>();
        int totalBoys = 0;
        int totalGirls = 0;
        int totalBorn = 0;

        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);
            for (CSVRecord record : records) {
                Integer intBorn = Integer.parseInt(record.get(2));
                totalBorn += intBorn;

                if (record.get(1).equals("M")) {
                    boys.add(idx++ + ": " + record.get(0));
                    totalBoys += intBorn;

                }
                if (record.get(1).equals("F")) {
                    girls.add(idx++ + ": " + record.get(0));
                    totalGirls += intBorn;
                }
            }
            System.out.println("total BoysName: " + totalBoys + " total GirlsName: " + totalGirls + " total Name: " + totalBorn);
        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }
    }
}
