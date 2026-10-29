package templates.dijkstra;

public class Dijkstra {
    static class Edge { int to, w; Edge(int t,int w){this.to=t;this.w=w;} }
    /** 鄰接表 0..n-1；回傳從 s 到各點最短距離（int 正權） */
    public static int[] dijkstra(java.util.List<java.util.List<Edge>> g, int s) {
        int n = g.size(); int INF = 1_000_000_000;
        int[] dist = new int[n]; java.util.Arrays.fill(dist, INF); dist[s] = 0;
        var pq = new java.util.PriorityQueue<int[]>((a,b)->Integer.compare(a[1], b[1]));
        pq.offer(new int[]{s, 0});
        boolean[] vis = new boolean[n];
        while (!pq.isEmpty()) {
            int[] cur = pq.poll(); int u = cur[0];
            if (vis[u]) continue; vis[u] = true;
            for (var e : g.get(u)) {
                if (dist[e.to] > dist[u] + e.w) {
                    dist[e.to] = dist[u] + e.w;
                    pq.offer(new int[]{e.to, dist[e.to]});
                }
            }
        }
        return dist;
    }
}
