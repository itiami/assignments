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
//       getAverageRank("Mason", "M");
//        getNumberOfPerson();
//        getRank("Emily","F");
//        getRankOfAPerson("Mason", "M");
        yearOfHighestRank("Mason", "M");
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

        return selectedFilesList;
    }

    private int getRank(String name, String gender) {
//        File file = new File("src/main/resources/module_5/us_babynames_by_year/yob" + year + ".csv");
        File file = new File("src/main/resources/module_5/us_babynames_by_year/yob1900.csv");
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
                    break;
                }
                rank++;
            }


        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }


        System.out.println("Rank of " + name + ": " + rank);
        return rank;
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

/*    private double getAverageRank(String name, String gender) {
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
    }*/

    //    ...................................................................................
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

            data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    private int getRankOfAPerson(String babyName, String babyGender) {
        Pattern pattern = Pattern.compile("\\d+");
        List<File> listOfFiles = selectFile(null, null);

        int countFile = 0;
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
            countFile++;
            Matcher matcher = pattern.matcher(file.getName());
            if (matcher.find()){
                System.out.println("Yar Selected: " + matcher.group());
            }
        }

        if (countFile ==1){
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

            System.out.println("Number of File Selected: " + countFile);
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


}
