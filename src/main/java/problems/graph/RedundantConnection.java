package problems.graph;

import java.util.Arrays;

public class RedundantConnection {
    class DSU {
        int[] parent;
        int[] rank;

        public DSU(int n) {
            parent = new int[n];
            rank = new int[n];
            for (int i = 0; i < n; i++) {
                parent[i] = i;
                rank[i] = 1;
            }
        }

        public int find(int i) {
            if (parent[i] != i) {
                parent[i] = find(parent[i]);
            }
            return parent[i];
        }

        public boolean union(int i, int j) {
            int rootI = find(i);
            int rootJ = find(j);

            if (rootI == rootJ)
                return false;

            if (rank[rootI] > rank[rootJ]) {
                parent[rootJ] = rootI;
            } else if (rank[rootI] < rank[rootJ]) {
                parent[rootI] = rootJ;
            } else {
                parent[rootJ] = rootI;
                rank[rootI]++;
            }
            return true;
        }
    }

    public int[] findRedundantConnection(int[][] edges) {
        int n = edges.length;

        DSU dsu = new DSU(n + 1);

        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];

            if (!dsu.union(u, v))
                return edge;
        }

        return new int[] {};
    }

    public static void main(String[] args) {

        RedundantConnection sol = new RedundantConnection();

        // Test Case 1
        int[][] edges1 = {{1, 2}, {1, 3}, {2, 3}};
        System.out.println("Test1: " + Arrays.toString(sol.findRedundantConnection(edges1)));
        // Expected: [2, 3]


        // Test Case 2
        int[][] edges2 = {{1, 2}, {2, 3}, {3, 4}, {1, 4}, {1, 5}};
        System.out.println("Test2: " + Arrays.toString(sol.findRedundantConnection(edges2)));
        // Expected: [1, 4]


        // Test Case 3
        int[][] edges3 = {{1, 5}, {3, 4}, {3, 5}, {4, 5}, {2, 4}};
        System.out.println("Test3: " + Arrays.toString(sol.findRedundantConnection(edges3)));
        // Expected: [4, 5]
    }
}
