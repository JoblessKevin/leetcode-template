package problems.advanceGraph;

import java.util.ArrayList;
/**
 * @formatter:off
 * 787. Cheapest Flights Within K Stops
 * 題目描述：
 * 你有一個 n 個城市的圖，城市由一組有向邊表示，每條邊都有一個價格。
 * 你需要從城市 src 飛行到城市 dst，但你的飛行次數不能超過 k 次（即最多可以轉機 k 次，總共經過 k+1 個城市）。
 * 你的目標是找到從 src 到 dst 的最便宜價格。
 * 如果無法在 k 次轉機內到達，則回傳 -1。
 * * 核心概念：
 * 這是一個「帶權重的最短路徑」問題，但它有「步數限制 (k)」。
 * Dijkstra 演算法可以找到最便宜的路徑，但它無法處理「步數限制」。
 * Bellman-Ford 演算法可以處理「負權重」，並且它天然具有「迭代次數」的概念，非常適合用來解決步數限制的問題。
 * * 演算法 (Bellman-Ford 變形)：
 * 1. 初始化一個陣列 dist[]，記錄到達每個城市的最小花費，初始值設為無窮大，src 為 0。
 * 2. 進行 k+1 次迭代（因為允許 k 次轉機，總共經過 k+1 個城市）。
 * 3. 在每一次迭代中，我們使用一個暫存陣列 tempDist[] 來儲存當次迭代的結果（防止當次迭代的更新影響到同一次迭代中對其他邊的計算）。
 * 4. 遍歷所有邊 (u, v, price)：
 *    如果從 u 出發到達 v 的價格 (dist[u] + price) 比目前記錄的 tempDist[v] 更便宜，則更新 tempDist[v]。
 * 5. 迭代結束後，將 tempDist 複製回 dist。
 * 6. 最終結果就是 dist[dst]。如果它仍然是無窮大，表示無法到達。
 * * 時間複雜度：O(E * K)。其中 E 是航班（邊）的數量，K 是允許的轉機次數。
 * 空間複雜度：O(V)。其中 V 是城市的數量。
 * @formatter:on
 */
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/**
 * Bellman-Ford
 */
public class CheapestFlights {
    public int findCheapestPrice(int n, int[][] flights, int src, int dst, int k) {
        // prices 陣列記錄從 src 到各個城市的最便宜機票價錢
        int[] prices = new int[n];
        Arrays.fill(prices, Integer.MAX_VALUE);
        prices[src] = 0; // 起點到自己不用錢

        // 🌟 核心：最多轉機 K 次，代表最多可以搭 K + 1 趟飛機
        // 所以我們精準地只跑 K + 1 輪掃描
        for (int i = 0; i <= k; i++) {
            // ⚠️ 致命陷阱防禦：我們必須備份一個 tempPrices！
            // 這是為了避免在「同一輪」裡面，連續搭了超過 1 段飛機。
            // 例如：A->B 更新了 B，然後 B->C 接著又馬上用剛更新的 B 去更新 C，
            // 這樣在「一輪」之內就搭了兩趟飛機，會破壞 K 的限制！
            int[] tempPrices = Arrays.copyOf(prices, n);

            // 暴力掃描所有的航班 (Edges)
            for (int[] flight : flights) {
                int u = flight[0]; // 出發地
                int v = flight[1]; // 目的地
                int price = flight[2]; // 票價

                // 如果出發地 u 是「可以到達的」(不是無限大)
                if (prices[u] != Integer.MAX_VALUE) {
                    // 看看從 u 飛到 v，會不會比目前到達 v 的價錢更便宜
                    // 注意：我們是用上一輪確定的 prices[u] 去更新這一輪的 tempPrices[v]
                    if (prices[u] + price < tempPrices[v]) {
                        tempPrices[v] = prices[u] + price;
                    }
                }
            }

            // 這輪結束，把備份的陣列變回正式陣列，準備下一輪
            prices = tempPrices;
        }

        // 如果目的地還是無限大，代表轉機 K 次以內根本飛不到
        return prices[dst] == Integer.MAX_VALUE ? -1 : prices[dst];
    }

    public int findCheapestPrice_bfs(int n, int[][] flights, int src, int dst, int k) {
        Map<Integer, List<int[]>> adj = new HashMap<>();
        for (int[] flight : flights) {
            adj.computeIfAbsent(flight[0], key -> new ArrayList<>())
                                            .add(new int[] {flight[1], flight[2]});
        }

        Queue<int[]> q = new LinkedList<>();
        q.offer(new int[] {src, 0});

        int[] minCost = new int[n];
        Arrays.fill(minCost, Integer.MAX_VALUE);
        minCost[src] = 0;

        int stops = 0;

        while (!q.isEmpty() && stops <= k) {
            int size = q.size();

            for (int i = 0; i < size; i++) {
                int[] curr = q.poll();
                int node = curr[0];
                int cost = curr[1];

                if (!adj.containsKey(node))
                    continue;

                for (int[] nextFlight : adj.get(node)) {
                    int nextNode = nextFlight[0];
                    int price = nextFlight[1];

                    if (cost + price < minCost[nextNode]) {
                        minCost[nextNode] = cost + price;
                        q.offer(new int[] {nextNode, cost + price});
                    }
                }
            }
            stops++;
        }

        return minCost[dst] == Integer.MAX_VALUE ? -1 : minCost[dst];
    }

    public static void main(String[] args) {
        CheapestFlights solution = new CheapestFlights();

        System.out.println("=== 測試案例 1：標準情況 (LeetCode 經典範例) ===");
        // 說明：有三條航線 0->1(100), 1->2(100), 0->2(500)
        // 條件：最多轉機 1 次 (k = 1)。
        // 路線 0 -> 1 -> 2 轉機 1 次，花費 200。比直飛的 500 便宜。
        int n1 = 3;
        int[][] flights1 = {{0, 1, 100}, {1, 2, 100}, {0, 2, 500}};
        System.out.println("K = 1 的最便宜花費: " + solution.findCheapestPrice(n1, flights1, 0, 2, 1)
                                        + " (預期: 200)\n");

        System.out.println("=== 測試案例 2：Dijkstra 貪心陷阱 (步數限制的威力) ===");
        // 說明：
        // 超便宜路線：0 -> 1 -> 2 -> 3 (花費 10+10+10 = 30，但要轉機 2 次)
        // 直飛路線：0 -> 3 (花費 100，轉機 0 次)
        // 條件：最多轉機 1 次 (k = 1)。
        // 雖然 30 元很吸引人，但轉機次數超標了！我們只能選擇 100 元的直飛航班。
        int n2 = 4;
        int[][] flights2 = {{0, 1, 10}, {1, 2, 10}, {2, 3, 10}, {0, 3, 100}};
        System.out.println("K = 1 的最便宜花費: " + solution.findCheapestPrice(n2, flights2, 0, 3, 1)
                                        + " (預期: 100)\n");

        System.out.println("=== 測試案例 3：tempPrices 防禦機制測試 ===");
        // 說明：0 -> 1 (100), 1 -> 2 (100)
        // 條件：最多轉機 0 次 (k = 0)，也就是只能直飛！
        // 因為 0 不能直飛到 2，所以應該回傳 -1。
        // 💡 實驗：你可以試著把程式碼裡的 tempPrices 拿掉，直接原地修改 prices。
        // 你會發現程式碼在第一輪就順著 0->1, 1->2 偷渡成功，錯誤地回傳了 200。這就是 tempPrices 的價值！
        int n3 = 3;
        int[][] flights3 = {{0, 1, 100}, {1, 2, 100}};
        System.out.println("K = 0 的最便宜花費: " + solution.findCheapestPrice(n3, flights3, 0, 2, 0)
                                        + " (預期: -1)\n");
    }
}
