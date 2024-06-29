package co.wali;

import edu.duke.FileResource;
import edu.duke.Point;

import java.util.ArrayList;

public class NShape {
    private ArrayList<edu.duke.Point> points;

    /**
     * Create an empty <code>Shape</code> object, one with no points.
     */
    public NShape () {
        points = new ArrayList<edu.duke.Point>();
    }

    /**
     * Create a <code>Shape</code> object from a file with x,y coordinates
     * of points, in order, one pair of x,y per line.
     *
     * Each x,y are comma-separated, e.g.,
     * <pre>
     *   3,4
     *  -2,5
     * </pre>
     * whitespace on line is skipped.
     *
     * @param file is the FileResource accessible and bound to a file
     * with one pair of points per line
     */
    public NShape (FileResource file) {
        this();
        for (String line : file.lines()) {
            double commaloc = line.indexOf(",");
            double x = Double.parseDouble(line.substring(0, (int) commaloc).trim());
            double y = Double.parseDouble(line.substring((int) (commaloc + 1)).trim());
            addPoint(new edu.duke.Point((int) x, (int) y));
        }
    }

    /**
     * Add a point to this shape/polygon.
     *
     * The order in which points are added defines the order in which points are
     * accessed.
     *
     * @param p is the Point added to this shape
     */
    public void addPoint (edu.duke.Point p) {
        points.add(p);
    }

    /**
     * Return the last point added to this shape/polygon
     *
     * @return the last Point added
     */
    public edu.duke.Point getLastPoint () {
        return points.get(points.size() - 1);
    }

    /**
     * Allow access to this shape one point at a time.
     *
     * @return an Iterable that allows access to points
     */
    public Iterable<Point> getPoints () {
        return points;
    }
}
