package byow.Core.Graph;

import byow.Core.BackGround.Hallway;
import byow.Core.BackGround.Room;
import byow.Core.Graph.Edge;
import byow.Core.Graph.Graph;
import byow.Core.Graph.KruskalMST;
import byow.Core.Position;
import byow.TileEngine.TETile;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

public class TestGraph {

    @Test
    public void TestEdges() {

        final int WIDTH = 50;
        final int HEIGHT = 30;
        final int numOfRooms = 4;

        /* Declare variables */
        TETile[][] tiles = new TETile[WIDTH][HEIGHT];
        Random random = new Random(1000);

        /* Create Room */
        Room room = new Room(WIDTH, HEIGHT, tiles, numOfRooms, random);
        room.drawAll();

        /* Graph Instantiate */
        System.out.println("Vertex Starts");
        ArrayList<Position> Vertex = room.getVertex();

        for (Position v : Vertex) {
            System.out.println(v.getX());
            System.out.println(v.getY());
        }

        Graph g = new Graph(Vertex);
        Assert.assertEquals(g.V(), numOfRooms);

        /* Test adj */
        System.out.println("Adj Test Starts");
        for (Edge e : g.adj[2]) {
            System.out.println(e.other(2));
        }

        /* Test edges method */
        System.out.println("EdgeWeight Starts");
        ArrayList<Edge> edges = new ArrayList<>();
        for (Edge e : g.edges()) {
            System.out.println(e.weight());
        }

        /* Test Kruskal */
        System.out.println("kruskal starts");
        KruskalMST kruskal = new KruskalMST(g);
        for (Edge e : kruskal.MinimumSpanningTree()) {
            System.out.println(e.weight());
        }

        /* Check Kruskal Edges */
        System.out.println("kruskal edges");
        for (Edge e : kruskal.MinimumSpanningTree()) {
            int v = e.either();
            int w = e.other(v);
            System.out.println(v);
            System.out.println(w);
        }

        /* Test Hallway */
        System.out.println("Hallway Starts");
        Hallway hw = new Hallway(tiles, room, kruskal);
        HashMap<Position, ArrayList<Position>> positionPair = hw.getClosestPositionPairs();

        for (Map.Entry<Position, ArrayList<Position>> entry : positionPair.entrySet()) {
            Position start = entry.getKey();
            ArrayList<Position> ends = entry.getValue();

            System.out.println(start.getX());
            System.out.println(start.getY());
            for (Position end : ends) {
                System.out.println(end.getX());
                System.out.println(end.getY());
            }
        }

        System.out.println(positionPair.size());

        /* Test fromTo */
        System.out.println("fromTo Start");
        for (Map.Entry<Position, Position> entry : hw.getFromTo().entrySet()) {
            Position start = entry.getKey();
            Position end = entry.getValue();
            System.out.println(start.getX());
            System.out.println(start.getY());
            System.out.println(end.getX());
            System.out.println(end.getY());
        }
    }
}
