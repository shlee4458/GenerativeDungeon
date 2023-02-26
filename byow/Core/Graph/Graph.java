package byow.Core.Graph;

import byow.Core.Position;
import edu.princeton.cs.algs4.*;

import java.util.*;

public class Graph {
    int V;
    int E;
    Bag<Edge>[] adj;

    /** Constructor */
    public Graph(ArrayList<Position> Vertex) {

        /* Create containers for; vertices and edges */
        V = Vertex.size();
        adj = (Bag<Edge>[]) new Bag[V];
        for (int v = 0; v < V; v++) {
            adj[v] = new Bag<>();
        }

        /* Connect all vertices by Edges */
        for (int i = 0; i < V; i++) {
            for (int j = i; j < V; j++) {
                if (i != j) {
                    // Create Edge
                    Edge e = new Edge(i, j, Position.weight(Vertex.get(i), Vertex.get(j)));

                    // Store the Edge
                    addEdge(e);
                }
            }
        }
    }

    /* Return number of vertices */
    public int V() {
        return V;
    }

    /* Add edge to the graph */
    public void addEdge(Edge e) {
        int v = e.either();
        adj[v].add(e);
        E++;
    }

    /* Return iterable edges */
    public Iterable<Edge> edges() {
        Bag<Edge> list = new Bag<>();
        for (int v = 0; v < V; v++) {
            int selfloops = 0;
            for (Edge e : adj(v)) {
                if (e.other(v) > v) {
                    list.add(e);
                }
                else if (e.other(v) == v) {
                    if (selfloops % 2 == 0) list.add(e);
                    selfloops++;
                }
            }
        }
        return list;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }
}
