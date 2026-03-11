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

    public static void main(String[] args) {
        TopologicalSortDFS sorter = new TopologicalSortDFS();

        // ==========================================
        // 測試案例 1：標準的 DAG (有向無環圖)
        // 這是最經典的教材範例，擁有兩個獨立的起點
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
        // 預期輸出可能是 或是
        // DFS 的特性是：只要是合法的拓撲順序皆可，結果不唯一。


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
