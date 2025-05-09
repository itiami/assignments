package co.wali;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

public class Main {
    // Configurable path as a constant for better maintainability
    private static final String RESOURCE_DIR = "src/main/resources/dna/";

    private void listDirectoryContents() {
        Path path = Paths.get(RESOURCE_DIR);
        try (Stream<Path> files = Files.list(path)) {
            files.forEach(System.out::println);
        } catch (IOException e) {
            System.out.println("Directory does not exist or cannot be accessed: " + path);
            // Log the exception if a logging framework is available, e.g., log.error("Error accessing directory", e);
        }
    }

    // Modular method to run tasks, reducing main method clutter
    private static void runTask(Runnable task, String taskName) {
        System.out.println("Running " + taskName + "...");
        task.run();
    }

    public static void main(String[] args) {
        long startTime = System.nanoTime(); // Higher precision than currentTimeMillis
        ExecutorService executorService = Executors.newFixedThreadPool(4);


//        Main main = new Main();
//        main.listDirectoryContents(); // Optimized directory listing

        // Example of running a single task (uncomment and adjust as needed)
        BabyBirths babyBirths = new BabyBirths();
        babyBirths.run();
//        runTask(babyBirths::run, "BabyBirths");
//        executorService.execute(babyBirths::run);
//        executorService.shutdown();
//        long endTime = System.nanoTime();
//        double executionTimeMs = (endTime - startTime) / 1_000_000.0; // Convert nanoseconds to milliseconds
//        System.out.printf("Execution time: %.3f ms%n", executionTimeMs);
    }
}