package templates.topologySort;

import java.util.*;

public class TopologicalSortDFS {

    public List<Integer> sort(int numTasks, List<List<Integer>> adj) {
        int[] visited = new int[numTasks];

        Deque<Integer> stack = new ArrayDeque<>();

        for (int i = 0; i < numTasks; i++) {
            if (visited[i] == 0) {
                if (hasCycle(i, adj, visited, stack)) {
                    return new ArrayList<>();
                }
            }
        }

        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()) {
            result.add(stack.pop());
        }
        return result;
    }

    private boolean hasCycle(int curr, List<List<Integer>> adj, int[] visited,
                                    Deque<Integer> stack) {
        visited[curr] = 1;

        for (int neighbor : adj.get(curr)) {
            if (visited[neighbor] == 1) {
                return true;
            }
            if (visited[neighbor] == 0) {
                if (hasCycle(neighbor, adj, visited, stack)) {
                    return true;
                }
            }
        }

        visited[curr] = 2;
        stack.push(curr);
        return false;
    }
}
