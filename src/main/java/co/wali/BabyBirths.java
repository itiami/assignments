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
//        whatIsNameInYear("Claud", "M");
//        getNameBasedOnRank(35, "M");
        getAverageRank("Jacob", "M");
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

    private int regexYear(String fileName) {
        int year = 0;
        Pattern pattern = Pattern.compile("\\d+");
        Matcher matcher = pattern.matcher(fileName);
        if (matcher.find()) {
            year = Integer.parseInt(matcher.group());
        }
        return year;
    }


    private int getRankOfAPerson(String babyName, String babyGender) {
        Pattern pattern = Pattern.compile("\\d+");
        List<File> listOfFiles = selectFile(null, null);
        // to get the Rank from a single File..
        if (listOfFiles.size() == 1) {
            String fileName = "";
            int countGirls = 0;
            int countBoys = 0;
            int countTotalName = 0;
            int sumOfBirth = 0;
            int sumBirthHigher = 0;
            int foundBabyName = -1;
            int selectedBabyRank = 1; // Rank starts at 1 (not 0)
            String highestRankedPerson = "";
            String highestGender = "";
            int highestCount = -1;
            for (File file : listOfFiles) {
                Matcher matcher = pattern.matcher(file.getName());
                if (matcher.find()) {
                    fileName = matcher.group();
                }

                List<CSVRecord> records = getData(file);
                for (CSVRecord record : records) {
                    String name = record.get(0); // to store the name of each cell
                    String gender = record.get(1); // to store gender of each cell
                    int numInBirthRow = Integer.parseInt(record.get(2)); // to store the birth's number in each cell

                    // to count total number of row. i.g 1+1+1... etc..
                    countTotalName++;

                    // to sum each cell. i.g 10+9+8+...
                    sumOfBirth += numInBirthRow;
                    /*
                    10+0=10
                    10+9=19
                    19+8=27
                    27+..... etc..
                    */

                    if (numInBirthRow > highestCount) {
                        highestCount = numInBirthRow;
                        highestRankedPerson = name;
                        highestGender = gender;
                    }

                    if (name.equalsIgnoreCase(babyName)) {
                        foundBabyName = numInBirthRow;
                    }

                    if (record.get(1).equalsIgnoreCase("F")) {
                        countGirls++; // count girl's name. i.g. 1+1+1.. etc
                    }
                    if (record.get(1).equalsIgnoreCase("M")) {
                        countBoys++;  // count boy's name. i.g. 1+1+1.. etc
                    }

                }


                // Now determine Baby's rank (among same gender)
                if (foundBabyName != -1) {
                    for (CSVRecord record : records) {
                        String gender = record.get(1);
                        int numInBirthRow = Integer.parseInt(record.get(2));

                        if (gender.equalsIgnoreCase(babyGender)) {
                            if (numInBirthRow > foundBabyName) {
                                sumBirthHigher += numInBirthRow;
                                selectedBabyRank++;
                            }
                        }
                    }
                } else {
                    selectedBabyRank = -1; // Not found
                }


            }
            System.out.println("........Result's From File.." + fileName);
            System.out.println("Rank of " + babyName + " : " + selectedBabyRank);
            System.out.println("Number of time " + babyName + " used: " + foundBabyName);
            System.out.println("Height Count Person is : " + highestRankedPerson + ", " + highestGender + " - " + highestCount);
            System.out.println("Total number of Girl's Name: " + countGirls);
            System.out.println("Total number of Boys's Name: " + countBoys);
            System.out.println("Total number of Name: " + countTotalName);
            System.out.println("Total Births  : " + sumOfBirth);
            System.out.println("Total Births heigher then " + babyName + " : " + sumBirthHigher);
        }

//        if Multiple File selected..
        if (listOfFiles.size() > 1) {
            List<Integer> fileName = new ArrayList<>();
            double rank = 0.0;

            for (File file : listOfFiles) {
                Matcher matcher = pattern.matcher(file.getName());
                int currentRank = getRankFromFile(file, babyName, babyGender);

                if (currentRank != -1) {
                    rank += currentRank;
                }
            }
            System.out.println("AverageRank is: " + rank / listOfFiles.size());
        }
        return 0;
    }

    private void getAverageRank(String babyName, String babyGender) {
        List<File> listOfFiles = selectFile(null, null);

        if (listOfFiles.isEmpty()) {
            System.out.println("No files selected.");
            return;
        }

        double totalRank = 0.0;
        int foundInFiles = 0;

        for (File file : listOfFiles) {
            List<CSVRecord> records = getData(file);

            // Find all records matching the gender
            List<CSVRecord> filteredData = new ArrayList<>();
            for (CSVRecord record : records) {
                if (record.get(1).equalsIgnoreCase(babyGender)) {
                    filteredData.add(record);
                }
            }

            // Sort by count descending to assign ranks
            filteredData.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));

            // Now, assign rank
            int rank = 1;
            for (CSVRecord record : filteredData) {
                if (record.get(0).equalsIgnoreCase(babyName)) {
                    totalRank += rank;
                    foundInFiles++;
                    break; // No need to keep looking
                }
                rank++;
            }
        }

        if (foundInFiles > 0) {
            double averageRank = totalRank / foundInFiles;
            System.out.println("Average rank of " + babyName + " (" + babyGender + "): " + averageRank);
        } else {
            System.out.println("Name " + babyName + " (" + babyGender + ") not found in any file.");
        }
    }


    private int getRankFromFile(File file, String name, String gender) {
        List<CSVRecord> data = new ArrayList<>();

        try (Reader reader = new FileReader(file)) {
            Iterable<CSVRecord> records = CSVFormat.DEFAULT.builder()
                    .setSkipHeaderRecord(true)
                    .get()
                    .parse(reader);

            for (CSVRecord record : records) {
                if (record.get(1).equalsIgnoreCase(gender)) {
                    data.add(record);
                }
            }

            // Sorting by birth count (Descending)
            data.sort((a, b) -> Integer.compare(
                    Integer.parseInt(b.get(2)),
                    Integer.parseInt(a.get(2))
            ));

            // Now search for the name in sorted data
            int rank = 1;
            for (CSVRecord record : data) {
                if (record.get(0).equalsIgnoreCase(name)) {
                    return rank;
                }
                rank++;
            }

        } catch (Exception e) {
            CustomLogger.logError("Error Found", e);
        }
        return -1; // Name not found
    }


    //    Get Name by a given rank, gender from selected year..
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
                System.out.println("In Year " + regexYear(file.getName()) +
                        " The Rank: " + rank + " is hold by: " + targetRecord.get(0));
            } else {
                System.out.println("The Rank does not found in year: " + regexYear(file.getName()));
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
