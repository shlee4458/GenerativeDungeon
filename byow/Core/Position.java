package byow.Core;

public class Position {
    int x;
    int y;
    int dist;

    /* Constructor */
    public Position(int x, int y) {
        this.x = x;
        this.y = y;
        this.dist = x*x + y*y;
    }

    /* get x Position */
    public int getX() {
        return x;
    }

    /* get y Position */
    public int getY() {
        return y;
    }

    /* Return distance of two given positions */
    public static int weight(Position start, Position end) {
        return Math.abs(start.getX() - end.getX()) + Math.abs(start.getY() - end.getY());
    }

    /* Change the x and y value of the instance */
    /* TODO, is changing an instance against encapsulation?
        If so, what is the solution to the case? */
    public void changePos(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    /* Return the middle position of the given two positions */
    public static Position midPos(Position start, Position end) {
        int avgX = Math.floorDiv(start.getX() + end.getX(), 2);
        int avgY = Math.floorDiv(start.getY() + end.getY(), 2);
        Position mid = new Position(avgX, avgY);
        return mid;
    }

    /* TODO, are below method efficient? */
    /* Return the maximum or minimum row or col of the two positions */
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

    /* Return a position with a smaller X position */
    public static Position minXPos(Position start, Position end) {
        if (start.getX() > end.getX()) { return end; }
        else { return start; }
    }

    /* Return a position with a smaller Y position */
    public static Position minYPos(Position start, Position end) {
        if (start.getY() > end.getY()) { return end; }
        else { return start; }
    }

    /* Return a position with a bigger X position */
    public static Position maxXPos(Position start, Position end) {
        if (start.getX() >= end.getX()) { return start; }
        else { return end; }
    }

    /* Return a position with a bigger Y position */
    public static Position maxYPos(Position start, Position end) {
        if (start.getY() >= end.getY()) { return start; }
        else { return end; }
    }

    /* Return 4 directions of positions */
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
        }
        return pos;
    }
}
