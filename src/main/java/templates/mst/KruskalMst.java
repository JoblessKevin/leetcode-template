package templates.mst;

import java.util.Arrays;

public class KruskalMst {
    // 1. 全域的 parent 陣列，用來記錄每個節點的老大
    static int[] parent;

    // 2. 極簡版 Find (包含路徑壓縮，短短2行搞定！)
    static int find(int i) {
        if (parent[i] == i)
            return i;
        // 遞迴尋找老大，找到後順便把整條路徑上的人都直接指向老大
        return parent[i] = find(parent[i]);
    }

    // 3. Kruskal 主邏輯：傳入頂點數量 n，以及二維陣列 edges [u, v, weight]
    public static int kruskal(int n, int[][] edges) {
        // 初始化 Union-Find，一開始大家的老大都是自己
        parent = new int[n];
        for (int i = 0; i < n; i++)
            parent[i] = i;

        // 依照「權重 (index 2)」由小到大排序所有的邊
        Arrays.sort(edges, (a, b) -> Integer.compare(a[2], b[2]));

        int mstWeight = 0; // 記錄最小生成樹的總權重
        int edgeCount = 0; // 記錄目前挑了幾條邊

        // 貪婪挑選：從小到大遍歷每一條邊
        for (int[] edge : edges) {
            int u = edge[0];
            int v = edge[1];
            int weight = edge[2];

            int rootU = find(u);
            int rootV = find(v);

            // 如果老大不同，代表不會形成迴圈 (可以連起來！)
            if (rootU != rootV) {
                parent[rootU] = rootV; // 合併 (Union)：讓 U 的老大認 V 的老大當老大
                mstWeight += weight; // 累加權重
                edgeCount++; // 邊數 + 1

                // 如果已經挑了 n - 1 條邊，代表樹已經完成了，提早收工！
                if (edgeCount == n - 1)
                    break;
            }
        }

        // 如果圖原本就是不連通的，可能湊不滿 n-1 條邊
        return edgeCount == n - 1 ? mstWeight : -1;
    }

    // --- 測試區 ---
    public static void main(String[] args) {
        int n = 4; // 4 個頂點 (0, 1, 2, 3)
        int[][] edges = {{0, 1, 10}, {0, 2, 6}, {0, 3, 5}, {1, 3, 15}, {2, 3, 4}};

        System.out.println("最小生成樹的總權重: " + kruskal(n, edges));
        // 預期輸出: 19
    }
}
