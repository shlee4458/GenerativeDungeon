package byow.Core.Graph;

public class Edge implements Comparable<Edge> {
    private int v;
    private int w;
    private int weight;

    public Edge(int v, int w, int weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    /* Returns instance variable weight */
    public int weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v) return w;
        else if (vertex == w) return v;
        else throw new IllegalArgumentException("Illegal endpoint");
    }

    /* Compare this weight to that weight */
    @Override
    public int compareTo(Edge that) {
        return Integer.compare(this.weight(), that.weight());
    }
}
