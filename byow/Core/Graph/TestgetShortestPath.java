package byow.Core.Graph;

import byow.TileEngine.TETile;
import byow.TileEngine.Tileset;
import org.junit.Test;

import java.util.*;
public class TestgetShortestPath {

    @Test
    public void TestShortestPath() {

        // Node Class: Test for update method
        char c = 'a';
        Node n = new Node(0, 0, 0);
        Node newNode = n.update(1, 1, 1, c);
        newNode.updateVisited('c');

        for (char ch : newNode.getVisited()) {
            System.out.println(ch);
        }

        // Create sample board
        TETile[][] sample = new TETile[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                sample[i][j] = Tileset.SAND;
            }
        }

        // Test Shortest Path
        getShortestPath sp = new getShortestPath(sample);
        char nextMove = sp.getNextMove(2,2,1,2);
        System.out.println(nextMove);

    }
}
