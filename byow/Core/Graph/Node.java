package byow.Core.Graph;

import java.util.*;

public class Node {
    int x;
    int y;
    int dist;
    ArrayList<Character> visited;

    /* Constructor */
    public Node(int x, int y, int dist) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.visited = new ArrayList<>(); // Stores characters of directions visited
    }

    /* Create a new Node and return that node with the updated instance variables */
    public Node update(int x, int y, int dist, char direction) {
        // Declare new instance variable values
        int newX = this.x + x;
        int newY = this.y + y;
        int newDist = this.dist + dist;

        // Create a new node
        Node newNode = new Node(newX, newY, newDist);
        newNode.getVisited().addAll(getVisited());
        newNode.updateVisited(direction);

        return newNode;
    }

    /* Update ArrayList of visited directions */
    public void updateVisited(char c) {
        visited.add(c);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDist() {
        return dist;
    }

    public ArrayList<Character> getVisited() {
        return visited;
    }
}
