package co.wali;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.*;

public class Part2 {

    private String findSimpleGene(String dna) {
        int start = dna.indexOf("ATG");
        int stop = dna.indexOf("TAA", start + 3);

        if (start == -1) {
            System.out.println("No `ATG` has found");
            return "";
        } else if (stop == -1) {
            System.out.println("No `TAA` has found");
            return "";
        } else if ((start != -1) && (stop != -1) && (stop - start) % 3 == 0 ) {
            System.out.println("Found ATG & TAA: " + dna);
            System.out.println("DNA Length: " + dna.length());
            return "Gene is: " + dna.substring(start, stop + 3);
        } else if ((start != -1) && (stop != -1) && (stop - start) % 3 != 0 ) {
            System.out.println("Found ATG & TAA: " + dna);
            System.out.println("DNA Length: " + dna.length());
            System.out.println("But DNA is not divisable by 3");
            return "";
        } else {
            System.out.println("No `ATG` nor `TAA` has found");
            return "";
        }

    }


    public void findGene() {
        Path path = Paths.get("src/main/resources/dna/brca1.txt");
        StringBuilder stringBuilder = new StringBuilder();

        try {
            for (String lines : Files.readAllLines(path)) {
                stringBuilder.append(lines.toUpperCase());
            }
        } catch (Error | IOException error) {

        }

        int start = stringBuilder.indexOf("ATG");
        int stop = stringBuilder.indexOf("TGA", start + 3);

        System.out.println("index position of the start codon “ATG” - " + findSimpleGene(stringBuilder.toString()));
        System.out.println("index position of the first stop codon “TAA” - " + stop);
        String dna = stringBuilder.toString().substring(start, stop + 3);
        int isDivisable = ((stop - start) % 3);
        System.out.println(dna);
        System.out.println(isDivisable);
        System.out.println("Is Divisable by 3 - " + (stop - start));
    }


    public void testSimpleGene() {
        String dna1 = "AATGCGTAATATGGT";
        System.out.println("\nString_1: ");
        System.out.println(findSimpleGene(dna1.toUpperCase()));

        String dna2 = "AATGCTAGGGTAATATGGT";
        System.out.println("\nString_2: ");
        System.out.println(findSimpleGene(dna2.toUpperCase()));

        String dna3 = "AATACTAGGGTAATATGGT";
        System.out.println("\nString_3: ");
        System.out.println(findSimpleGene(dna3.toUpperCase()));

        String dna4 = "ATGTAA";
        System.out.println("\nString_4: ");
        System.out.println(findSimpleGene(dna4.toUpperCase()));

        String dna5 = "gatgctataat";
        System.out.println("\nString_5: ");
        System.out.println(findSimpleGene(dna5.toUpperCase()));
    }


}
