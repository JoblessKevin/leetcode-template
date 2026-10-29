package problems.graph;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class ShortestPath {
    public int shortestPath(int n, int[][] edges) {
        List<List<Integer>> graph = new ArrayList<>();
        for (int i=0; i<n; i++) graph.add(new ArrayList<>());
        for (int[] e : edges) {
            graph.get(e[0]).add(e[1]);
            graph.get(e[1]).add(e[0]);
        }

        Queue<Integer> q = new LinkedList<>();
        boolean[] visited = new boolean[n];
        q.offer(0); visited[0] = true;
        int steps = 0;

        while (!q.isEmpty()) {
            int size = q.size();
            while (size-- > 0) {
                int node = q.poll();
                if (node == n-1) return steps;
                for (int nei : graph.get(node)) {
                    if (!visited[nei]) {
                        visited[nei] = true;
                        q.offer(nei);
                    }
                }
            }
            steps++;
        }
        return -1;
    }
}
