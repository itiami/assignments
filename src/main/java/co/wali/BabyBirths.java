package co.wali;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BabyBirths {
    public void run() {

//        getRankOfAPerson("Mason", "M");
//        whatIsNameInYear("Mason", "M");
        getNameBasedOnRank(2, "M");

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

    //    Java File(s) Selector


    //    Get Rank of a person
    private int getRank(String name, String gender) {
        List<File> files = selectFile(null, null);
        int rank = 1;
        int totalBorn = 0;
        List<CSVRecord> data = new ArrayList<>();

        for (File file : files) {
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
                        break;
                    }
                    rank++;
                }


            } catch (Exception e) {
                CustomLogger.logError("Error Found", e);
            }
        }
        System.out.println("Rank of " + name + ": " + rank);
        return rank;
    }

    private int getRankOfName(List<CSVRecord> records, String name, String gender) {
        int rank = 1;
        for (CSVRecord record : records) {
            String recordName = record.get(0);
            String recordGender = record.get(1);
            if (recordGender.equals(gender)) {
                if (recordName.equals(name)) {
                    return rank;
                }
                rank++;
            }
        }
        return -1; // Not found
    }


    private int yearOfHighestRank(String name, String gender) {
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
            int currentRank = getRank(name, gender);
            if (currentRank != -1) {
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

    //    ...................................................................................

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

        return selectedFilesList;
    }

    private List<CSVRecord> getData(File file) {
        List<CSVRecord> data = new ArrayList<>();

        try {
            Reader reader = new FileReader(file);
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);

            for (CSVRecord record : records) {
                data.add(record);
            }

            /*data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    //    totalBirths, getRank
    private int getRankOfAPerson(String babyName, String babyGender) {
        Pattern pattern = Pattern.compile("\\d+");
        List<File> listOfFiles = selectFile(null, null);
        int countGirls = 0;
        int countBoys = 0;
        int countTotal = 0;
        int totalBirths = 0;

        int countName = -1;
        int emilyRank = 1; // Rank starts at 1 (not 0)
        String highestRankedPerson = "";
        String highestGender = "";
        int highestCount = -1;


        for (File file : listOfFiles) {

            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()) {
                System.out.println("Yar Selected: " + matcher.group());
            }
        }

        if (listOfFiles.size() == 1) {
            for (File file : listOfFiles) {
                List<CSVRecord> records = getData(file);
                for (CSVRecord record : records) {
                    String name = record.get(0);
                    String gender = record.get(1);
                    int count = Integer.parseInt(record.get(2));

                    countTotal++;
                    totalBirths += count;

                    if (record.get(1).equalsIgnoreCase("F")) {
                        countGirls++;
                    }
                    if (record.get(1).equalsIgnoreCase("M")) {
                        countBoys++;
                    }

                    if (count > highestCount) {
                        highestCount = count;
                        highestRankedPerson = name;
                        highestGender = gender;
                    }

                    if (name.equalsIgnoreCase(babyName)) {
                        countName = count;
                    }
                }


                // Now determine Emily's rank (among same gender)
                if (countName != -1) {
                    for (CSVRecord record : records) {
                        int count = Integer.parseInt(record.get(2));
                        if (record.get(1).equalsIgnoreCase(babyGender) && count > countName) {
                            emilyRank++;
                        }
                    }
                } else {
                    emilyRank = -1; // Not found
                }
            }

            System.out.println("Number of File Selected: " + listOfFiles.size());
            System.out.println("Rank of " + babyName + " : " + emilyRank);
            System.out.println("Number of time " + babyName + " used: " + countName);
            System.out.println("Height Count Person is : " + highestRankedPerson + ", " + highestGender + " - " + highestCount);
            System.out.println("Total number of Girl's Name: " + countGirls);
            System.out.println("Total number of Boys's Name: " + countBoys);
            System.out.println("Total number of Name: " + countTotal);
            System.out.println("Total totalBirths  : " + totalBirths);

        }

        return 0;
    }

    private void getNameBasedOnRank(int rank, String gender) {
        List<File> files = selectFile(null, null); // select file/files
        if (files.isEmpty()) {
            System.out.println("No File Selected.. ");
        }

        for (File file : files) {
            List<CSVRecord> records = getData(file); // capture records using FileReader and CSVFormate

            List<CSVRecord> formatData = new ArrayList<>();

            for (CSVRecord record : records) {
                if (record.get(1).equalsIgnoreCase(gender)) {
                    formatData.add(record);
                }
            }

            formatData.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));


            if (rank > 0 && rank <= formatData.size()) {
                CSVRecord targetRecord = formatData.get(rank - 1); // Rank 1 is index 0
                System.out.println(targetRecord);
                System.out.println(rank);
                System.out.println(rank - 1);
            }
        }
    }

    //    Compare Name between 2 year...............
    private void whatIsNameInYear(String name, String gender) {
        List<File> files = selectFile(null, null); // Assuming this gets the two CSV files
        Pattern pattern = Pattern.compile("\\d+");

        if (files.size() == 2) {
            for (File file : files) {
                String year = "";
                Matcher matcher = pattern.matcher(file.getName());

                if (matcher.find()) {
                    year = matcher.group();
                }

                // Filter and collect matching gender records
                List<CSVRecord> filteredRecords = new ArrayList<>();
                for (CSVRecord data : getData(file)) {
                    if (data.get(1).equalsIgnoreCase(gender)) {
                        filteredRecords.add(data);
                    }
                }

                // Sort filteredRecords by usage count descending
                filteredRecords.sort((a, b) -> Integer.compare(
                        Integer.parseInt(b.get(2)),
                        Integer.parseInt(a.get(2))
                ));

                // Find rank of the target name
                boolean found = false;
                int rank = 0;
                int prevCount = -1;
                int actualRank = 0; // rank taking ties into account

                for (CSVRecord record : filteredRecords) {
                    int currentCount = Integer.parseInt(record.get(2));
                    actualRank++;

                    if (currentCount != prevCount) {
                        rank = actualRank;
                        prevCount = currentCount;
                    }

                    if (record.get(0).equalsIgnoreCase(name)) {
                        System.out.println("Year: " + year + " , " + record.get(0) + " total usages: " + record.get(2)
                                + ", Rank: " + rank);
                        found = true;
                        break;
                    }
                }

                if (!found) {
                    System.out.println(name + " not found in year " + year);
                }
            }
        } else {
            System.out.println("Please Select 2 File Only, Total File selected are: " + files.size());
        }
    }


}
