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
        Part4 part4 = new Part4();
        part4.run();
    }
}

