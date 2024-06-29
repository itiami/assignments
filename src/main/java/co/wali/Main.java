package co.wali;

public class Main {
    public static void main(String[] args) {
        JavaFun1 javaFunctions = new JavaFun1();
//        System.out.println(javaFunctions.myFunction(3,7));
//        System.out.println(javaFunctions.f(6));
//        System.out.println(javaFunctions.g());
//
//        JavaFun2 javaFun2 = new JavaFun2();
//        System.out.println(javaFun2.h());
//        System.out.println(javaFun2.g(5));
//        System.out.println(javaFun2.k(6,6));
//
//        Point p1 = new Point(3,4);
//        Point p2 = new Point(6,8);
//        System.out.println("Point Distance: " + p1.distance(p2));
//        System.out.println(Double.parseDouble(" 003.02"));

        PerimeterRunner perimeterRunner = new PerimeterRunner();
        perimeterRunner.testFileWithLargestPerimeter();

    }
}