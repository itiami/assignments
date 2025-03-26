package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileReader;
import java.io.Reader;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;

public class BabyBirths {
    public void run() {
//        printNames();
//        selectFile(0.7, 0.6);
//        System.out.println(getRank(1880, "Minnie", ""));
//        whatIsNameInYear("Isabella", 2012, 2014, "F");
//        whatIsNameInYear("Isabella", 2012, 2014, "F");
//        yearOfHighestRank("Mason", "M");
//        selectFile(0.5, 0.5);

//        getAverageRank("Jacob", "M");
//        getAverageRank("Mason", "M");
        getTotalBirthsRankedHigher("Ethan", "M");
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

    private List<File> selectFile(Double widthPercent, Double heightPercent) {
        // Create a List to store the selected files
        final List<File> selectedFilesList = new ArrayList<>();

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        // a default values if parameters are null
        int width = (int) (screenSize.width * (widthPercent != null ? widthPercent : 0.4)); // Default to 50% if null
        int height = (int) (screenSize.height * (heightPercent != null ? heightPercent : 0.5)); // Default to 50% if null

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Use invokeAndWait instead of invokeLater to wait for user selection
        try {
            SwingUtilities.invokeAndWait(() -> {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setMultiSelectionEnabled(true);
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setDialogTitle("Select CSV File(s)");
                fileChooser.setPreferredSize(new Dimension(width, height));

                String resourcesPath = Paths.get("src", "main", "resources/module_5").toAbsolutePath().toString();
                File resourcesDir = new File(resourcesPath);
                if (resourcesDir.exists() && resourcesDir.isDirectory()) {
                    fileChooser.setCurrentDirectory(resourcesDir);
                }

                int result = fileChooser.showOpenDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Convert File[] to List<File>
                    File[] selectedFiles = fileChooser.getSelectedFiles();
                    selectedFilesList.addAll(Arrays.asList(selectedFiles));
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (selectedFilesList !=null){
            System.out.println("No File Selected..");
        }

        return selectedFilesList;
    }

    private List<CSVRecord> getData(File file, String gender) {
        List<CSVRecord> data = new ArrayList<>();

        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);

            for (CSVRecord record : records) {
                if (record.get(1).equalsIgnoreCase(gender)) {
                    data.add(record);
                }
            }

            data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }


    private int getRank(File file, String name, String gender) {
//        File file = new File("src/main/resources/module_5/us_babynames_by_year/yob" + year + ".csv");
//        File file = new File("src/main/resources/module_5/testing/yob2012short.csv");
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
                if (record.get(1).equalsIgnoreCase(gender)) {
                    data.add(record);
                }
            }

            // Sorting data by birth count in descending order
            data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)), // Sorting in descending order
                    Integer.parseInt(a.get(2))
            ));


            for (CSVRecord record : data) {
                if (record.get(0).equalsIgnoreCase(name)) {
                    System.out.println("Rank of " + name + ": " + rank);
                    break;
                }
                rank++;
            }


        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }
        return rank;
    }

    private String getName(File file, int rank, String gender) {
        List<CSVRecord> data = getData(file, gender);

        // If rank is valid, return the name at that rank; otherwise return "NO NAME"
        if (rank > 0 && rank <= data.size()) {
            return data.get(rank - 1).get(0); // Rank 1 corresponds to index 0
        }
        return "NO NAME";
    }


    private void whatIsNameInYear(String name, int year, int newYear, String gender) {
        int rank = -1;
        int currentRank = 1;
        File yearOneFile = new File("src/main/resources/module_5/us_babynames_by_year/yob" + year + ".csv");
        File yearTowFile = new File("src/main/resources/module_5/us_babynames_by_year/yob" + newYear + ".csv");
        List<CSVRecord> yearOneData = new ArrayList<>();
        List<CSVRecord> yearTwoData = new ArrayList<>();

        String yearOneName = null;
        String yearTwoName = null;

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
                if (record.get(1).equalsIgnoreCase(gender)) {
                    yearOneData.add(record);
                }
            }


            for (CSVRecord record : yearTwoRecords) {
                if (record.get(1).equalsIgnoreCase(gender)) {
                    yearTwoData.add(record);
                }
            }


            // Sort both lists by number of births in descending order
            yearOneData.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));

            yearTwoData.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));

            for (CSVRecord record : yearOneData) {
                if (record.get(0).equalsIgnoreCase(name)) {
                    rank = currentRank;
                    yearOneName = record.get(0);
                    break;
                }
                currentRank++;
            }

            // If name not found in the original year, handle it
            if (rank == -1) {
                System.out.println(name + " not found in year " + year);
                return;
            }

            // Find the name at the same rank in the new year
            if (rank <= yearTwoData.size()) {
                yearTwoName = yearTwoData.get(rank - 1).get(0);
            }

            if (yearTwoName != null) {
                String pronoun = gender.equalsIgnoreCase("M") ? "he" : "she";
                System.out.println(yearOneName + " born in " + year + " would be " + yearTwoName +
                        " if " + pronoun + " was born in " + newYear);
            } else {
                System.out.println("No name found at rank " + rank + " in year " + newYear);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private int yearOfHighestRank(String name, String gender) {
        String directoryPath = "src/main/resources/module_5/testing";
        File folder = new File(directoryPath);
        List<File> listOfFiles = selectFile(null, null);
        int highestRank = Integer.MAX_VALUE;  // Initialize with max value to find minimum rank
        int yearOfHighest = 0;

        try {
            if (listOfFiles != null) {
                for (File file : listOfFiles) {
                    int currentYear = Integer.parseInt(file.getName().substring(3, 7));
                    List<CSVRecord> data = new ArrayList<>();

                    Reader reader = new FileReader(file);
                    Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                            .setSkipHeaderRecord(true)
                            .get()
                            .parse(reader);

                    // Collect records for the specified gender
                    for (CSVRecord record : records) {
                        if (record.get(1).equalsIgnoreCase(gender)) {
                            data.add(record);
                        }
                    }

                    // Sort by birth count in descending order
                    data.sort((a, b) -> Integer.compare(
                            Integer.parseInt(b.get(2)),
                            Integer.parseInt(a.get(2))
                    ));

                    // Find rank of the name in current year
                    int rank = 1;
                    boolean found = false;
                    for (CSVRecord record : data) {
                        if (record.get(0).equalsIgnoreCase(name)) {
                            found = true;
                            if (rank < highestRank) {
                                highestRank = rank;
                                yearOfHighest = currentYear;

                                System.out.println(name + "found in File:  " + file.getName() + ", And his rank is: " + highestRank);
                            }
                            break;
                        }
                        rank++;
                    }

                    if (!found) {
                        System.out.println(name + " does not exist in file " + file.getName());
                    }
                }
            }
        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }

        return yearOfHighest;  // Returns the year where the name had its highest rank
    }

    private double getAverageRank(String name, String gender) {
        List<File> listOfFiles = selectFile(null, null);
        double totalRank = 0.0;
        int filesWithName = 0;

        for (File file : listOfFiles) {
            int currentRank = getRank(file, name, gender);
            if (currentRank != -1){
                totalRank += currentRank;
                filesWithName++;
            }
        }

        double average = totalRank / filesWithName;

        System.out.println("Total Files with name: " + filesWithName);
        System.out.println("Total Rank of " + name + " is: " + totalRank);
        System.out.println("Average is: " + average);

        return average;
    }


    private int getTotalBirthsRankedHigher(String name, String gender){
        List<File> listofFiles = selectFile(0.5, 0.8);

        for (File file : listofFiles) {
            List<CSVRecord> data = getData(file, gender);
            for (CSVRecord record : data) {
                if (record.get(0).equalsIgnoreCase(name)) {
                    System.out.println(record.get(0));
                }
            }
        }
        return 0;
    }


}
