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

        Queue<Integer> queue = new ArrayDeque<>();
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

    public static void main(String[] args) {
        TopologicalSortKahn sorter = new TopologicalSortKahn();

        // ==========================================
        // 測試案例 1：標準的 DAG (有向無環圖)
        // ==========================================
        System.out.println("--- 測試案例 1：標準 DAG ---");
        int numTasks1 = 6;
        List<List<Integer>> adj1 = new ArrayList<>();
        for (int i = 0; i < numTasks1; i++) {
            adj1.add(new ArrayList<>());
        }

        // 建構圖的相依關係 (Edges)
        // 任務 5 是 2 和 0 的前置作業 (5 -> 2, 5 -> 0)
        adj1.get(5).add(2);
        adj1.get(5).add(0);
        // 任務 4 是 0 和 1 的前置作業 (4 -> 0, 4 -> 1)
        adj1.get(4).add(0);
        adj1.get(4).add(1);
        // 任務 2 是 3 的前置作業 (2 -> 3)
        adj1.get(2).add(3);
        // 任務 3 是 1 的前置作業 (3 -> 1)
        adj1.get(3).add(1);

        List<Integer> result1 = sorter.sort(numTasks1, adj1);
        System.out.println("拓撲排序結果: " + result1);
        // 預期輸出通常是 或 等等


        // ==========================================
        // 測試案例 2：包含循環依賴 (Cycle) 的圖
        // ==========================================
        System.out.println("\n--- 測試案例 2：包含循環 (Cycle) ---");
        int numTasks2 = 3;
        List<List<Integer>> adj2 = new ArrayList<>();
        for (int i = 0; i < numTasks2; i++) {
            adj2.add(new ArrayList<>());
        }

        // 0 -> 1 -> 2 -> 0 (形成死結)
        adj2.get(0).add(1);
        adj2.get(1).add(2);
        adj2.get(2).add(0);

        List<Integer> result2 = sorter.sort(numTasks2, adj2);
        System.out.println("拓撲排序結果: " + (result2.isEmpty() ? "檢測到循環，回傳空陣列 []" : result2));
    }
}
