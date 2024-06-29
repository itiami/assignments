package co.wali;

import edu.duke.*;
import edu.duke.Point;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    public Point getLargestSide(Shape s){
        Point largest = s.getLastPoint();
        for (Point point : s.getPoints()) {
            if (comparePoints(point, largest) > 0) {
                largest = point;
            }
        }
        return largest;
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


    public  int getNumPoints(Shape shape){
        Iterable<Point> numPoints = shape.getPoints();
        int counter = 0;
        for (Point i : numPoints){
            counter++;
        }
        return counter;
    }


    public void testPerimeter() {
        FileResource fr = new FileResource("src/main/resources/shape.txt");
        Shape s = new Shape(fr);
        double length = getPerimeter(s);
        int num = getNumPoints(s);
//        System.out.println("Perimeter = " + Math.round(length));
//        System.out.println("Counter = " + num);
//        System.out.println("Average Length of " + num +" is: "+ Math.round(length/num));
        System.out.println("The getLargestSide: " + getLargestSide(s));
        System.out.println("The getLargestX: " + getLargestX(s).getX());
    }




    public double getLargestPerimeterMultipleFiles() {
        DirectoryResource dr = new DirectoryResource();
        double largestPerimeter = 0.0;

        for (File f : dr.selectedFiles()) {
            FileResource fr = new FileResource(f);
            Shape s = new Shape(fr);
            double currPerimeter = getPerimeter(s);
            if (currPerimeter > largestPerimeter) {
                largestPerimeter = currPerimeter;
            }
        }

        return largestPerimeter;
    }

    public void testPerimeterMultipleFiles() {
        double largestPerimeter = getLargestPerimeterMultipleFiles();
        System.out.println("Largest perimeter among multiple files: " + largestPerimeter);
    }

    public String getFileWithLargestPerimeter() {
        DirectoryResource dr = new DirectoryResource();
        double largestPerimeter = 0.0;
        File temp = null;

        for (File f : dr.selectedFiles()) {
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

    public void testFileWithLargestPerimeter() {
        String fileName = getFileWithLargestPerimeter();
        System.out.println("File with largest perimeter: " + fileName);
    }

    public void printFileNames() {
        DirectoryResource dr = new DirectoryResource();
        for (File f : dr.selectedFiles()) {
            System.out.println(f.getName());
        }
    }

}