package origins;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * @formatter:off
 * 這是由理論推導過來的程式碼，可以參考，不要用。
 * 排序所有的邊、從小到大挑選、用 Union-Find 檢查迴圈並加入樹中。
 * 
 * 實作：
 * 1. 邊 (Edge)
 * 2. EdgeWeightedGraph
 * 3. Union-Find
 * 4. KruskalAlgo
 * 5. 主程式 (KruskalTheory)
 * @formatter:on
 */

// ==========================================
// 1. 邊 (Edge)
// ==========================================
class Edge implements Comparable<Edge> {
    private final int v;
    private final int w;
    private final double weight;

    public Edge(int v, int w, double weight) {
        this.v = v;
        this.w = w;
        this.weight = weight;
    }

    public double weight() {
        return weight;
    }

    public int either() {
        return v;
    }

    public int other(int vertex) {
        if (vertex == v)
            return w;
        if (vertex == w)
            return v;
        throw new IllegalArgumentException("Illegal endpoint");
    }

    @Override
    public int compareTo(Edge that) {
        return Double.compare(this.weight, that.weight);
    }

    @Override
    public String toString() {
        return String.format("%d-%d %.2f", v, w, weight);
    }
}


// ==========================================
// 2. EdgeWeightedGraph
// ==========================================
class EdgeWeightedGraph {

    private final int V;
    private int E;
    private List<Edge>[] adj;

    @SuppressWarnings("unchecked")
    public EdgeWeightedGraph(int V) {
        this.V = V;
        this.E = 0;

        adj = (List<Edge>[]) new LinkedList[V];

        for (int v = 0; v < V; v++) {
            adj[v] = new LinkedList<>();
        }
    }

    public int V() {
        return V;
    }

    public int E() {
        return E;
    }

    public void addEdge(Edge e) {

        int v = e.either();
        int w = e.other(v);

        adj[v].add(e);
        adj[w].add(e);

        E++;
    }

    public Iterable<Edge> adj(int v) {
        return adj[v];
    }

    public Iterable<Edge> edges() {

        List<Edge> list = new ArrayList<>();

        for (int v = 0; v < V; v++) {
            for (Edge e : adj(v)) {

                if (e.other(v) > v) {
                    list.add(e);
                }

            }
        }

        return list;
    }
}


// ==========================================
// 3. Union-Find
// ==========================================
class UF {

    private int[] parent;
    private int[] size;

    public UF(int V) {

        parent = new int[V];
        size = new int[V];

        for (int i = 0; i < V; i++) {
            parent[i] = i;
            size[i] = 1;
        }
    }

    public int find(int p) {

        int root = p;

        while (root != parent[root]) {
            root = parent[root];
        }

        while (p != root) {
            int next = parent[p];
            parent[p] = root;
            p = next;
        }

        return root;
    }

    public boolean connected(int p, int q) {
        return find(p) == find(q);
    }

    public void union(int p, int q) {

        int rootP = find(p);
        int rootQ = find(q);

        if (rootP == rootQ)
            return;

        if (size[rootP] < size[rootQ]) {
            parent[rootP] = rootQ;
            size[rootQ] += size[rootP];
        } else {
            parent[rootQ] = rootP;
            size[rootP] += size[rootQ];
        }
    }
}


// ==========================================
// 4. Kruskal MST
// ==========================================
class KruskalAlgo {

    private Queue<Edge> mst;
    private double weight;

    public KruskalAlgo(EdgeWeightedGraph G) {

        mst = new LinkedList<>();
        weight = 0.0;

        PriorityQueue<Edge> pq = new PriorityQueue<>();

        for (Edge e : G.edges()) {
            pq.add(e);
        }

        UF uf = new UF(G.V());

        while (!pq.isEmpty() && mst.size() < G.V() - 1) {

            Edge e = pq.poll();

            int v = e.either();
            int w = e.other(v);

            if (uf.connected(v, w))
                continue;

            uf.union(v, w);
            mst.add(e);
            weight += e.weight();
        }
    }

    public Iterable<Edge> edges() {
        return mst;
    }

    public double weight() {
        return weight;
    }
}


// ==========================================
// 5. 主程式 (KruskalTheory)
// ==========================================
public class KruskalTheory {

    public static void main(String[] args) {

        int V = 8;

        EdgeWeightedGraph G = new EdgeWeightedGraph(V);

        G.addEdge(new Edge(4, 5, 0.35));
        G.addEdge(new Edge(4, 7, 0.37));
        G.addEdge(new Edge(5, 7, 0.28));
        G.addEdge(new Edge(0, 7, 0.16));
        G.addEdge(new Edge(1, 5, 0.32));
        G.addEdge(new Edge(0, 4, 0.38));
        G.addEdge(new Edge(2, 3, 0.17));
        G.addEdge(new Edge(1, 7, 0.19));
        G.addEdge(new Edge(0, 2, 0.26));
        G.addEdge(new Edge(1, 2, 0.36));
        G.addEdge(new Edge(1, 3, 0.29));
        G.addEdge(new Edge(2, 7, 0.34));
        G.addEdge(new Edge(6, 2, 0.40));
        G.addEdge(new Edge(3, 6, 0.52));
        G.addEdge(new Edge(6, 0, 0.58));
        G.addEdge(new Edge(6, 4, 0.93));

        System.out.println("Start Kruskal Algorithm\n");

        KruskalAlgo mst = new KruskalAlgo(G);

        System.out.println("--- MST Edges ---");

        for (Edge e : mst.edges()) {
            System.out.println(e);
        }

        System.out.printf("\nTotal Weight: %.5f\n", mst.weight());
    }
}
