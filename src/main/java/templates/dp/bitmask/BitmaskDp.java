package templates.dp.bitmask;

import java.util.Arrays;

/**
 * Bitmask DP 模板大全
 * 核心思想：用 Integer 的二進制位 (Bit) 來表示集合狀態 (1=在集合內, 0=不在)。
 * 適用場景：N <= 20 (因為 2^20 約為 10^6，剛好在運算極限內)
 */
public class BitmaskDp {

    private static final int INF = 1_000_000_000;

    /**
     * Type A: 旅行推銷員類型 (TSP) - 重視「順序」與「當前位置」
     * ---------------------------------------------------------
     * 狀態定義：dp[mask][u] -> 走過的城市集合為 mask，且最後停在 u 的最小成本。
     * 複雜度：O(2^N * N^2)
     */
    public int tsp(int[][] dist) {
        int n = dist.length;
        int limit = 1 << n; // 2^n
        int[][] dp = new int[limit][n];

        // 1. 初始化
        for (int[] row : dp) Arrays.fill(row, INF);
        dp[1][0] = 0; // 假設從 node 0 出發，mask 只有第 0 位是 1

        // 2. 第一層：枚舉所有狀態 mask
        for (int mask = 1; mask < limit; mask++) {
            // 3. 第二層：枚舉 mask 中包含的「最後一個節點」 u
            for (int u = 0; u < n; u++) {
                // 如果 mask 裡根本沒有 u，這狀態不合法
                if (((mask >> u) & 1) == 0) continue;

                int prevMask = mask ^ (1 << u); // 移除 u，回推上一個狀態
                if (prevMask == 0) continue;    // 邊界：prevMask 為 0 代表是起點，無需轉移

                // 4. 第三層：枚舉「上一個節點」 v
                for (int v = 0; v < n; v++) {
                    if (((prevMask >> v) & 1) == 1) {
                        // 狀態轉移：從 v 走到 u
                        dp[mask][u] = Math.min(dp[mask][u], dp[prevMask][v] + dist[v][u]);
                    }
                }
            }
        }

        // 5. 取得結果 (這裡示範：遍歷完所有點，最後停在任意點的最小值)
        int minCost = INF;
        int fullMask = limit - 1;
        for (int i = 1; i < n; i++) {
            minCost = Math.min(minCost, dp[fullMask][i]); 
            // 若題目要求回到原點，則改為: Math.min(minCost, dp[fullMask][i] + dist[i][0]);
        }
        return minCost;
    }

    /**
     * Type B: 集合合併類型 (Merge / Partition) - 重視「組合結構」
     * ---------------------------------------------------------
     * 狀態定義：dp[mask] -> 將 mask 代表的集合合併成一個整體的最小成本。
     * 為了讓測試有意義，這裡傳入 int[] values 來計算真實成本。
     * * 範例情境：石子合併 (任意兩堆可合併，成本為總和)
     */
    public long subsetMerge(int[] values) {
        int n = values.length;
        int limit = 1 << n;
        long[] dp = new long[limit];
        Arrays.fill(dp, Long.MAX_VALUE);

        // 1. 初始化 Base Cases (只有單個元素的 mask)
        // 這裡我們預處理 mask 的總和，方便 calculateCost 使用
        long[] maskSum = new long[limit];
        for (int mask = 1; mask < limit; mask++) {
            for (int i = 0; i < n; i++) {
                if (((mask >> i) & 1) == 1) {
                    maskSum[mask] += values[i];
                }
            }
        }

        for (int i = 0; i < n; i++) {
            dp[1 << i] = 0; // 單個元素不需要合併，成本為 0
        }

        // 2. 第一層：枚舉所有狀態 mask (從小到大)
        for (int mask = 1; mask < limit; mask++) {
            // 優化：如果 mask 只有 1 個 bit，跳過 (已初始化)
            if ((mask & (mask - 1)) == 0) continue;

            // 用於去重的優化技巧：找出 mask 的最高位 bit
            int highBit = Integer.highestOneBit(mask);

            // 3. 第二層：枚舉 mask 的所有真子集 sub
            for (int sub = (mask - 1) & mask; sub > 0; sub = (sub - 1) & mask) {
                
                // 去重優化：強制 sub 必須包含 mask 的最高位
                if ((sub & highBit) == 0) continue;

                int other = mask ^ sub;

                // 狀態轉移：左半堆 + 右半堆 + 合併這兩堆的當下代價
                // 這裡假設合併成本就是兩堆的總和 (Classic Stone Merge)
                long currentMergeCost = maskSum[mask]; 
                
                long total = dp[sub] + dp[other] + currentMergeCost;
                
                if (total < dp[mask]) {
                    dp[mask] = total;
                }
            }
        }

        return dp[limit - 1];
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        BitmaskDp solver = new BitmaskDp();

        System.out.println("========= Test Case 1: TSP =========");
        // 4 個城市，0 是起點
        // 目標：0 -> 1 -> 3 -> 2 (Cost 10+25+30 = 65) 是這張圖的一條路徑
        int[][] dist = {
            {0, 10, 15, 20},
            {10, 0, 35, 25},
            {15, 35, 0, 30},
            {20, 25, 30, 0}
        };
        // 最佳路徑推演:
        // 0 -> 1 (10)
        // 1 -> 3 (25)
        // 3 -> 2 (30)
        // Total: 65
        int tspResult = solver.tsp(dist);
        System.out.println("TSP Min Cost: " + tspResult); // Expected: 65


        System.out.println("\n========= Test Case 2: Subset Merge =========");
        // 模擬石子合併：每次合併成本為兩堆之和
        // [1, 2, 3]
        // 1. 合併 {1, 2} -> Cost 3, 新集合 {3, 3}
        // 2. 合併 {3, 3} -> Cost 6
        // 總成本: 3 + 6 = 9
        int[] values = {1, 2, 3};
        long mergeResult = solver.subsetMerge(values);
        System.out.println("Subset Merge Cost: " + mergeResult); // Expected: 9
        
        // 測試反例順序是否影響 (不應該影響，因為是集合合併)
        int[] values2 = {3, 1, 2};
        System.out.println("Subset Merge Cost (Reordered): " + solver.subsetMerge(values2)); // Expected: 9
    }
}