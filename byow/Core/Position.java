package byow.Core;

import byow.Core.FixedObject.FixedObject;

public class Position {
    int x;
    int y;

    /** Constructor */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /** Get x coordinate */
    public int getX() {
        return x;
    }

    /** Get y coordinate */
    public int getY() {
        return y;
    }

    /** Return the distance of two given Position objects */
    public static int weight(Position start, Position end) {
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

    /** Modify the x and y value of the instance */
    public void changePos(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /** Return the middle position of the given two positions. Middle position of the given two
     * positions is calculated by averaging x and y coordinate rounded down to the closest integer.
     * For example, if (x, y) coordinate of the start Position is (5, 10) and end Position is (7, 11),
     * Position object with (6, 10) as (x, y) coordinate will be returned. */
    public static Position midPos(Position start, Position end) {
        int avgX = Math.floorDiv(start.getX() + end.getX(), 2);
        int avgY = Math.floorDiv(start.getY() + end.getY(), 2);
        Position mid = new Position(avgX, avgY);
        return mid;
    }

    /** Return the maximum or minimum row or col of the two positions */
    public static int maxRow(Position a, Position b) { return Math.max(a.getY(), b.getY()); }
    public static int minRow(Position a, Position b) {
        return Math.min(a.getY(), b.getY());
    }
    public static int maxCol(Position a, Position b) {
        return Math.max(a.getX(), b.getX());
    }
    public static int minCol(Position a, Position b) {
        return Math.min(a.getX(), b.getX());
    }

    /** Return a position with a smaller X position */
    public static Position minXPos(Position start, Position end) {
        if (start.getX() > end.getX()) { return end; }
        else { return start; }
    }

    /** Return a position with a smaller Y position */
    public static Position minYPos(Position start, Position end) {
        if (start.getY() > end.getY()) { return end; }
        else { return start; }
    }

    /** Takes player`s directional input as a value, and returns the Position object
     * that reflects the marginal x, y coordinate changes as an output */
    public static Position direction(char c) {
        Position pos = new Position(0, 0);

        if (c == 'w') {
            pos.changePos(0,1);
        } else if (c == 's') {
            pos.changePos(0,-1);
        } else if (c == 'a') {
            pos.changePos(-1,0);
        } else if (c == 'd') {
            pos.changePos(1,0);
        } else {
            pos.changePos(0, 0);
        }
        return pos;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + ")";
    }

    /** Returns whether two positions have the same x, y coordinate */
    public static boolean samePosition(Position a, Position b) {
        return a.getX() == b.getX() && a.getY() == b.getY();
    }
}
