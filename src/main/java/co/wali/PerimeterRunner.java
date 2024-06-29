package co.wali;

import edu.duke.*;
import edu.duke.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PerimeterRunner {

    public double getPerimeter (NShape s) {
        // Start with totalPerim = 0
        double totalPerim = 0.0;
        // Start wth prevPt = the last point
        edu.duke.Point prevPt = s.getLastPoint();
        // For each point currPt in the shape,
        for (Point currPt : s.getPoints()) {
            // Find distance from prevPt point to currPt 
            double currDist = prevPt.distance(currPt);
            // Update totalPerim by currDist
            totalPerim = totalPerim + currDist;
            // Update prevPt to be currPt
            prevPt = currPt;
        }
        // totalPerim is the answer
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

    public Point getLargestSide(NShape s){
        Point largest = s.getLastPoint();
        for (Point point : s.getPoints()) {
            if (comparePoints(point, largest) > 0) {
                largest = point;
            }
        }
        return largest;
    }


    public Point getLargestX(NShape s){
        Point largest = s.getLastPoint();
        for (Point point : s.getPoints()) {
            if (compareX(point, largest) > 0) {
                largest = point;
            }
        }
        return largest;
    }


    public  int getNumPoints(NShape shape){
        Iterable<Point> numPoints = shape.getPoints();
        int counter = 0;
        for (Point i : numPoints){
            counter++;
        }
        return counter;
    }





    public void testFileWithLargestPerimeter() {
        FileResource fr = new FileResource("src/main/resources/shape.txt");
        NShape s = new NShape(fr);
        double length = getPerimeter(s);
        int num = getNumPoints(s);
//        System.out.println("Perimeter = " + Math.round(length));
//        System.out.println("Counter = " + num);
//        System.out.println("Average Length of " + num +" is: "+ Math.round(length/num));
        System.out.println("The getLargestSide: " + getLargestSide(s));
        System.out.println("The getLargestX: " + getLargestX(s).getX());
    }

}