package co.wali;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Part2Extended {
    private String findSimpleGene(String dna, String startCodon, String stopCodon) {
        String result = "";

        int startIndex = dna.toUpperCase().indexOf(startCodon);
        int stopIndex = dna.toUpperCase().indexOf(stopCodon, startIndex + 3);

        if (startIndex == -1 || stopIndex == -1) {
            return result;
        }

        if ((stopIndex - startIndex) % 3 == 0) {
            result = dna.substring(startIndex, stopIndex + 3);
        }

        return result;
    }

    private void testSimpleGene() {
        String startCodon = "ATG";
        String stopCodon = "TAA";

        // DNA with no ATG
        String dna = "ATCTAACATC";
        System.out.println("DNA_1 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon,stopCodon));

        // DNA with no TAA
        dna = "ATTATCATGTTA";
        System.out.println("DNA_2 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));

        // DNA with no “ATG” nor “TAA”
        dna = "ATTAGTGTA";
        System.out.println("DNA_3 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));

        // DNA with ATG, TAA and the substring between them is not a multiple of 3
        dna = "GAAATGGATAGTAA";
        System.out.println("DNA_4 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));

        // DNA with ATG, TAA and the substring between them is a multiple of 3 (a gene)
        dna = "TAAGATATGGTGAAGTAA";
        System.out.println("DNA_5 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));

        dna = "ATGGGTTAAGTC";
        System.out.println("DNA_6 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));

        dna = "gatgctataat";
        System.out.println("DNA_7 strand is " + dna);
        System.out.println("Gene is " + findSimpleGene(dna, startCodon, stopCodon));
    }

    private void testGenesFromFile(){
        String startCodon = "ATG";
        String stopCodon = "TAA";
        Path path = Paths.get("src/main/resources/dna/GRch38dnapart.fa");
        StringBuilder stringBuilder = new StringBuilder();
        try {
            for (String lines : Files.readAllLines(path)) {
                stringBuilder.append(lines.toUpperCase());
            }
        } catch (Error | IOException error) {

        }

        System.out.println("Gene is " + findSimpleGene(stringBuilder.toString(), startCodon, stopCodon));
    }

    public void run(){
        // test gen from a file
        testGenesFromFile();

    }
}

