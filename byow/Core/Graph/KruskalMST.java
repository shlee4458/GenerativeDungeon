package byow.Core.Graph;

import edu.princeton.cs.algs4.*;

import java.util.*;

public class KruskalMST {
    private ArrayList<Edge> mst = new ArrayList<>();
    public KruskalMST(Graph G) {

        // Initiate Priority Queue
        PriorityQueue<Edge> pq = new PriorityQueue<>();
        for (Edge e : G.edges()) {
            pq.add(e);
        }

        // Initiate Weighted Quick Union
        WeightedQuickUnionUF uf = new WeightedQuickUnionUF(G.V());
        while (!pq.isEmpty() && mst.size() < G.V() - 1) {
            Edge e = pq.poll();
            int v = e.either();
            int w = e.other(v);
            if (!uf.connected(v, w)) {
                uf.union(v, w);
                mst.add(e);
            }
        }
    }

    /* Get Minimum Spanning Tree */
    public ArrayList<Edge> MinimumSpanningTree() {
        return mst;
    }
}
