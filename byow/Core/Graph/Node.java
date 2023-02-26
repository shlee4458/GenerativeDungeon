package byow.Core.Graph;

import java.util.*;

public class Node {
    int x;
    int y;
    int dist;
    ArrayList<Character> visited;

    /** Constructor */
    public Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.visited = new ArrayList<>(); // Stores characters of directions visited
    }

    /** Create a new Node and return that node with the updated instance variables
     * @Param int x: marginal increase in the new x coordinate
     * @Param int y: marignal increase in the new y coordinate
     * @Param int dist: marginal increase in the distance
     * @Param char direction: additional directional input */
    public Node update(int x, int y, int dist, char direction) {

        /* Declare new instance variable values */
        int newX = this.x + x;
        int newY = this.y + y;
        int newDist = this.dist + dist;

        /* Create a new node */
        Node newNode = new Node(newX, newY, newDist);
        newNode.getVisited().addAll(getVisited());
        newNode.updateVisited(direction);

        return newNode;
    }

    /** Update ArrayList of directions visited */
    public void updateVisited(char c) {
        visited.add(c);
    }

    /** Get X coordinate of the Node object */
    public int getX() {
        return x;
    }

    /** Get Y coordinate of the Node object */
    public int getY() {
        return y;
    }

    /** Get distance of the Node object */
    public int getDist() {
        return dist;
    }

    /** Get visited directional input of the Node object */
    public ArrayList<Character> getVisited() {
        return visited;
    }
}
