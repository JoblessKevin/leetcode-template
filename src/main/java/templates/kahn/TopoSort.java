package templates.kahn;

/** Return a topological sort, or an empty array if the graph contains a cycle. */
public class TopoSort {
    public static int[] topoOrder(java.util.List<java.util.List<Integer>> g) {
        int n = g.size();
        int[] indeg = new int[n];
        for (int u = 0; u < n; u++) for (int v : g.get(u)) indeg[v]++;
        java.util.ArrayDeque<Integer> q = new java.util.ArrayDeque<>();
        for (int i = 0; i < n; i++) if (indeg[i] == 0) q.offer(i);
        int[] order = new int[n]; int idx = 0;
        while (!q.isEmpty()) {
            int u = q.poll(); order[idx++] = u;
            for (int v : g.get(u)) if (--indeg[v] == 0) q.offer(v);
        }
        return idx == n ? order : new int[0];
    }
}
