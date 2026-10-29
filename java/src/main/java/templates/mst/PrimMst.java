package templates.mst;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class PrimMst {
    // Prim 主邏輯：傳入頂點數量 n，以及二維陣列 edges [u, v, weight]
    public static int prim(int n, int[][] edges) {

        // 1. 建圖 (Adjacency List)：用 List 陣列記錄每個頂點的 {鄰居, 權重}
        List<int[]>[] adj = new ArrayList[n];
        for (int i = 0; i < n; i++)
            adj[i] = new ArrayList<>();

        for (int[] edge : edges) {
            int u = edge[0], v = edge[1], weight = edge[2];
            adj[u].add(new int[] {v, weight}); // 無向圖，兩邊都要加
            adj[v].add(new int[] {u, weight});
        }

        // 2. 準備核心工具：
        // visited 陣列：記錄哪些頂點已經被納入最小生成樹 (避免走回頭路)
        boolean[] visited = new boolean[n];

        // 優先佇列 (PQ)：存放 int[]{抵達的頂點, 走到該頂點的權重}，依照「權重」從小到大排序
        PriorityQueue<int[]> pq = new PriorityQueue<>((a, b) -> Integer.compare(a[1], b[1]));

        // 3. 從起點 (通常選 0) 開始發芽：抵達頂點 0 的成本是 0
        pq.add(new int[] {0, 0});

        int mstWeight = 0; // 記錄最小生成樹的總權重
        int visitedCount = 0; // 記錄目前已經有幾個頂點加入樹中

        // 4. 不斷向外擴張，直到所有頂點都加入，或是沒路可走
        while (!pq.isEmpty() && visitedCount < n) {
            int[] curr = pq.poll(); // 拿出目前能走到最便宜的頂點
            int u = curr[0];
            int weight = curr[1];

            // 如果這個頂點已經在樹裡面了，就跳過 (這就是 Prim 避免迴圈的方法！)
            if (visited[u])
                continue;

            // 正式將頂點 u 納入最小生成樹
            visited[u] = true;
            mstWeight += weight;
            visitedCount++;

            // 站在剛加入的頂點 u 上，把所有「相鄰且還沒去過」的鄰居丟進 PQ 裡排隊
            for (int[] neighbor : adj[u]) {
                int nextNode = neighbor[0];
                if (!visited[nextNode]) {
                    // 注意：這裡直接把整條路徑的成本丟進去
                    pq.add(new int[] {nextNode, neighbor[1]});
                }
            }
        }

        // 檢查是否所有頂點都連通了 (圖有可能一開始就是斷開的)
        return visitedCount == n ? mstWeight : -1;
    }

    // --- 測試區 ---
    public static void main(String[] args) {
        int n = 4; // 4 個頂點 (0, 1, 2, 3)
        int[][] edges = {{0, 1, 10}, {0, 2, 6}, {0, 3, 5}, {1, 3, 15}, {2, 3, 4}};

        System.out.println("最小生成樹的總權重: " + prim(n, edges));
        // 預期輸出: 19
    }
}
