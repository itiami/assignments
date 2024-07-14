package co.wali;

import edu.duke.*;
import edu.duke.Point;
import edu.duke.Shape;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PerimeterAssignmentRunner  {

    public double getPerimeter (Shape s) {
        double totalPerim = 0.0;
        edu.duke.Point prevPt = s.getLastPoint();
        for (Point currPt : s.getPoints()) {
            double currDist = prevPt.distance(currPt);
            totalPerim = totalPerim + currDist;
            prevPt = currPt;
        }

        return totalPerim;
    }

    public  int getNumPoints(Shape shape){
        Iterable<Point> numPoints = shape.getPoints();
        int counter = 0;
        for (Point i : numPoints){
            counter++;
        }
        return counter;
    }

    public static int comparePoints(Point p1, Point p2) {
        if (p1.getX() + p1.getY() > p2.getX() + p2.getY()){
            return 1;
        }else {
            return -1;
        }
    }

    public static int compareX(Point p1, Point p2) {
        if (p1.getX() > p2.getX() ){
            return 1;
        }else {
            return -1;
        }
    }

    public Point getLargestPoint(Shape s){
        Point largest = s.getLastPoint();
        for (Point point : s.getPoints()) {
            if (comparePoints(point, largest) > 0) {
                largest = point;
            }
        }
        return largest;
    }


    public static double getDistance(Point p1, Point p2) {
        double dx = p1.getX() - p2.getX();
        double dy = p1.getY() - p2.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    public static double getLongestSide(Shape shape) {
        double maxLength = 0.0;
        Point prevPt = shape.getLastPoint();
        for (Point currPt : shape.getPoints()) {
            double currDist = getDistance(prevPt, currPt);
            if (currDist > maxLength) {
                maxLength = currDist;
            }
            prevPt = currPt;
        }
        return maxLength;
    }


    public Point getLargestX(Shape s){
        Point largest = s.getLastPoint();
        for (Point point : s.getPoints()) {
            if (compareX(point, largest) > 0) {
                largest = point;
            }
        }
        return largest;
    }



    private double truncate2Dec(double d){
        return Math.floor(d*100)/100;
    }



    public double getLargestPerimeterMultipleFiles() {
        Path path = Paths.get("src/main/resources/example");
        DirectoryResource dr = new DirectoryResource();
        double largestPerimeter = 0.0;

        for (File f : path.toFile().listFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerimeter = getPerimeter(s);
            if (currPerimeter > largestPerimeter) {
                largestPerimeter = currPerimeter;
            }
        }
        System.out.println("largest perimeter from the four files: " + largestPerimeter); // 62.65
        return largestPerimeter;
    }

    public void testPerimeterMultipleFiles() {
        double largestPerimeter = getLargestPerimeterMultipleFiles();
        System.out.println("Largest perimeter among multiple files: " + largestPerimeter);
    }

    public String getFileWithLargestPerimeter() {
        Path path = Paths.get("src/main/resources/example");
        DirectoryResource dr = new DirectoryResource();
        double largestPerimeter = 0.0;
        File temp = null;

        for (File f : path.toFile().listFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerimeter = getPerimeter(s);
            if (currPerimeter > largestPerimeter) {
                largestPerimeter = currPerimeter;
                temp = f;
            }
        }

        return temp.getName();
    }


    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f.getName());
        }
    }


    public Set<String> listFilesUsingFileWalk(String dir, int depth) throws IOException {
        try (Stream<Path> stream = Files.walk(Paths.get(dir), depth)) {
            return stream
                    .filter(file -> !Files.isDirectory(file))
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toSet());
        }
    }

    public void readDir() {
        Path dir = Paths.get("src/main/resources/datatest");

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public double mysteryShape (Shape s) {
        double tmp = 0;
        for (Point p : s.getPoints()) {

            if (p.getX() > 0) {

                if (p.getY() < 0) {
                    tmp = tmp + 1;
                }
            }
        }
        return tmp / getNumPoints(s);
    }


    public void run() {
        FileResource fr = new FileResource("src/main/resources/datatest/datatest1.txt");
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        int num = getNumPoints(s);
        System.out.println("Perimeter = " + truncate2Dec(length));
        System.out.println("Counter = " + num);
        System.out.println("Average Length of " + num +" is: "+ truncate2Dec(length/num));
        System.out.println("The getLargestPoint: " + getLargestPoint(s));
        System.out.println("The getLongestSide: " + truncate2Dec(getLongestSide(s)));
        System.out.println("The getLargestX: " + getLargestX(s).getX());

        System.out.println("Positive or Negative: "+ mysteryShape(s));
        System.out.println("File with largest perimeter: " + getFileWithLargestPerimeter());

        testPerimeterMultipleFiles();
    }

}