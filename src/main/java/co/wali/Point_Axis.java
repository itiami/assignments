package co.wali;

public class Point_Axis {
    private int x;
    private int y;

    public Point_Axis(int startX, int startY){
        x = startX;
        y = startY;
    }

    public int getX(){return x;}
    public int getY(){return y;}

    public double distance(Point_Axis anObjectOfPoint){
        int dx = x - anObjectOfPoint.getX();
        int dy = y - anObjectOfPoint.getY();
        return Math.sqrt(dx * dx + dy * dy);
    }
}
