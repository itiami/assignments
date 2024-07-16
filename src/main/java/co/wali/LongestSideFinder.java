package co.wali;

import java.io.*;
import java.util.*;
import edu.duke.*;
import edu.duke.Point;

public class LongestSideFinder {
    public double res() {
        List<Point> points = new ArrayList<>();

        // Read points from the file
        try (BufferedReader br = new BufferedReader(new FileReader("src/main/resources/datatest1.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] coords = line.split(",");
                double x = Double.parseDouble(coords[0]);
                double y = Double.parseDouble(coords[1]);
                points.add(new Point((int) x, (int) y));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        double longestSide = findLongestSide(points);
        System.out.printf("The longest side is: %.2f%n", longestSide);
        return  longestSide;
    }

    public static double findLongestSide(List<Point> points) {
        double maxDistance = 0;

        // Calculate distances between each pair of points
        for (int i = 0; i < points.size(); i++) {
            for (int j = i + 1; j < points.size(); j++) {
                double distance = distance(points.get(i), points.get(j));
                if (distance > maxDistance) {
                    maxDistance = distance;
                }
            }
        }

        return maxDistance;
    }

    public static double distance(Point p1, Point p2) {
        return Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
    }
}


