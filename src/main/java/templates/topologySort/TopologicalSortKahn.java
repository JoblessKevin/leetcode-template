package templates.topologySort;

import java.util.*;

public class TopologicalSortKahn {

    public List<Integer> sort(int numTasks, List<List<Integer>> adj) {
        int[] inDegree = new int[numTasks];
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < numTasks; i++) {
            for (int neighbor : adj.get(i)) {
                inDegree[neighbor]++;
            }
        }

        Deque<Integer> queue = new ArrayDeque<>();
        for (int i = 0; i < numTasks; i++) {
            if (inDegree[i] == 0) {
                queue.offer(i);
            }
        }

        while (!queue.isEmpty()) {
            int curr = queue.poll();
            result.add(curr);

            for (int neighbor : adj.get(curr)) {
                inDegree[neighbor]--;

                if (inDegree[neighbor] == 0) {
                    queue.offer(neighbor);
                }
            }
        }

        if (result.size() != numTasks) {
            return new ArrayList<>();
        }

        return result;
    }
}
