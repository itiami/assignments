package co.wali;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {

    private void isExists() {
        Path path = Paths.get("src/main/resources/dna/");
        if (Files.exists(path)) {
            for (File f : path.toFile().listFiles()) {
                System.out.println(f);
            }
        } else {
            System.out.println("is null");
        }
    }

    public static void main(String[] args) {
        Part1 part1 = new Part1();
        Part2 part2 = new Part2();
        Part3 part3 = new Part3();
        Part4 part4 = new Part4();
        TemperatureFinder temperatureFinder = new TemperatureFinder();

//        part1.findGene();
//        part1.testSimpleGene();
//        part2.testSimpleGene();
//        part2.findGene();
//        part3.testing();
//        part4.run();
        temperatureFinder.run();
    }
}

