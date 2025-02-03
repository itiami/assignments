package co.wali;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class GeneCounter {
    public void run() {
        Path path = Paths.get("src/main/resources/dna/GRch38dnapart.fa");
        if (!Files.exists(path)){
            System.out.println(path.isAbsolute());
        }else {
        /*String dna = "ATGAAATAAGGATGCCCTAAATGTAAATGAAACTAA"; // Replace with your DNA string
        Path path = Paths.get("src/main/resources/dna/GRch38dnapart.txt");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (String lines : Files.readAllLines(path)) {
                stringBuilder.append(lines.toUpperCase());
            }
        } catch (Error | IOException error) {

        }
        int geneCount = countGenes(stringBuilder.toString(), "ATG", "TGA");
        int geneCount_2 = countDNASequences(stringBuilder.toString());

        System.out.println("Number of genes: " + geneCount);
        System.out.println("Number of genes_2: " + geneCount_2);

        occuranceTest();*/

            String readAsString = readFile(path.toString());
            System.out.println(readAsString);
        }
    }


    public static String readFile(String filename) {
        StringBuilder dna = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                // Skip header lines or lines starting with ">"
                if (!line.startsWith(">")) {
                    dna.append(line.trim()); // Append sequence to the DNA string
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return dna.toString();
    }



    public static int countGenes(String dna, String startCodon, String stopCodon) {
        int count = 0;
        int startIndex = 0;

        while (true) {
            startIndex = dna.indexOf(startCodon, startIndex);
            if (startIndex == -1) {
                break;
            }
            int stopIndex = dna.indexOf(stopCodon, startIndex + 3);
            if (stopIndex == -1) {
                break;
            }
            // Ensure the length of the gene is a multiple of 3
            if ((stopIndex - startIndex) % 3 == 0) {
                count++;
                startIndex = stopIndex + 3;
            } else {
                startIndex = startIndex + 3;
            }
        }

        return count;
    }


    private int countDNASequences(String dna) {
        int count = 0;
        int i = 0;

        while (i < dna.length() - 2) { // We stop before the last 2 characters, because a codon needs at least 3 characters
            // Find the start codon (ATG)
            int startIndex = dna.indexOf("ATG", i);

            // If no more start codon is found, break the loop
            if (startIndex == -1) {
                break;
            }

            // Find the stop codon (TAA) after the start codon
            int stopIndex = dna.indexOf("TAA", startIndex + 3); // Ensure it comes after the start codon

            // Check if the stop codon exists and the sequence length is a multiple of 3
            if (stopIndex != -1 && (stopIndex - startIndex) % 3 == 0) {
                count++; // Found a valid sequence
                i = stopIndex + 3; // Move index past the stop codon for non-overlapping sequences
            } else {
                i = startIndex + 3; // Move past the start codon and look for the next one
            }
        }

        return count;
    }


    private void occuranceTest(){
        String myString = "This is a test string for testing the test project.";
        String targetString = "test";
        int index = myString.indexOf(targetString);
        while (index != -1) {
            System.out.println("Found at index: " + index);
            index = myString.indexOf(targetString, index + 1); // Move to the next index
        }
    }
}

