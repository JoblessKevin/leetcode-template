package templates.dp.knapsack;

import java.util.Arrays;

/**
 * 背包 DP (Knapsack DP) 模板
 * -------------------------------------------------------
 * 核心思想：在有限的資源 (Capacity) 下，如何選擇物品 (Items) 以達到目標 (最大價值、剛好填滿、組合數)。
 * * 空間優化：
 * 標準二維 DP 狀態為 dp[i][w] (前 i 個物品，容量 w)。
 * 透過滾動數組優化，通常壓縮為一維 dp[w]。
 * * 複雜度：
 * - 時間：O(N * C) (N=物品數, C=容量)
 * - 空間：O(C)
 */
public class KnapsackDp {

    /**
     * Type A: 0/1 背包 (0/1 Knapsack)
     * 規則：每個物品只能選 1 次 (選 或 不選)。
     * 適用：分割等和子集 (Partition Equal Subset Sum)、目標和 (Target Sum)。
     * * @param weights 物品重量
     * @param values  物品價值
     * @param capacity 背包總容量
     * @return 最大價值
     */
    public int zeroOneKnapsack(int[] weights, int[] values, int capacity) {
        // dp[w] = 容量為 w 時能裝入的最大價值
        int[] dp = new int[capacity + 1];

        // 1. 外層枚舉物品 (Items)
        for (int i = 0; i < weights.length; i++) {
            int wgt = weights[i];
            int val = values[i];

            // 2. 內層枚舉容量 (Capacity)
            // ★★★ Key: 必須「倒序 (Reverse)」遍歷 ★★★
            // 原因：計算 dp[w] 時，我們需要用到「上一輪(i-1)」的 dp[w-weight]。
            // 如果正序遍歷，dp[w-weight] 會先被更新成「這一輪(i)」的數據，導致同一個物品被重複使用。
            for (int w = capacity; w >= wgt; w--) {
                dp[w] = Math.max(dp[w], dp[w - wgt] + val);
            }
        }
        return dp[capacity];
    }

    /**
     * Type B: 完全背包 (Unbounded Knapsack)
     * 規則：每個物品可以選無限次。
     * 適用：零錢兌換 (Coin Change)、完全平方數 (Perfect Squares)。
     * * @param weights 物品重量
     * @param values  物品價值
     * @param capacity 背包總容量
     * @return 最大價值
     */
    public int unboundedKnapsack(int[] weights, int[] values, int capacity) {
        int[] dp = new int[capacity + 1];

        // 1. 外層枚舉物品
        for (int i = 0; i < weights.length; i++) {
            int wgt = weights[i];
            int val = values[i];

            // 2. 內層枚舉容量
            // ★★★ Key: 必須「正序 (Forward)」遍歷 ★★★
            // 原因：計算 dp[w] 時，我們希望用到「這一輪(i)」已經更新過的 dp[w-weight]。
            // 這意味著：剛放入一個物品 i 後，容量變大了，但我還可以繼續再放入物品 i。
            for (int w = wgt; w <= capacity; w++) {
                dp[w] = Math.max(dp[w], dp[w - wgt] + val);
            }
        }
        return dp[capacity];
    }

    // ==========================================
    // 常見變體補充 (Variations)
    // ==========================================

    /**
     * Variation 1: 求「填滿背包的方法數」 (Combination Count)
     * 適用：Coin Change 2 (LeetCode 518)
     * * @param nums 物品(硬幣)數值
     * @param target 目標總和
     * @return 方法數
     */
    public int combinationCount(int[] nums, int target) {
        int[] dp = new int[target + 1];
        dp[0] = 1; // Base case: 容量為 0 有 1 種方法 (什麼都不選)

        for (int num : nums) {
            // 完全背包邏輯 (正序)，若每個數字只能用一次則改倒序
            for (int w = num; w <= target; w++) {
                dp[w] += dp[w - num]; // 累加方法數
            }
        }
        return dp[target];
    }

    /**
     * Variation 2: 求「填滿背包的最少物品數」 (Min Items)
     * 適用：Coin Change (LeetCode 322)
     */
    public int minItems(int[] nums, int target) {
        int[] dp = new int[target + 1];
        Arrays.fill(dp, target + 1); // 初始化為無效大值
        dp[0] = 0;

        for (int num : nums) {
            for (int w = num; w <= target; w++) {
                dp[w] = Math.min(dp[w], dp[w - num] + 1);
            }
        }
        return dp[target] > target ? -1 : dp[target];
    }

    // ==========================================
    // Test Cases
    // ==========================================
    public static void main(String[] args) {
        KnapsackDp solver = new KnapsackDp();

        int[] weights = {1, 3, 4};
        int[] values = {15, 20, 30};
        int capacity = 4;

        System.out.println("--- 0/1 Knapsack ---");
        // 只能選 {1 (15)} + {3 (20)} = 35. (選 {4} 只有 30)
        System.out.println("Max Value: " + solver.zeroOneKnapsack(weights, values, capacity)); // Expected: 35

        System.out.println("\n--- Unbounded Knapsack ---");
        // 可以選 {1} 四次 = 15*4 = 60.
        System.out.println("Max Value: " + solver.unboundedKnapsack(weights, values, capacity)); // Expected: 60
        
        System.out.println("\n--- Combination Count (Coin Change 2) ---");
        // coins = [1, 2, 5], amount = 5
        // 1+1+1+1+1, 1+1+1+2, 1+2+2, 5 -> 4 ways
        int[] coins = {1, 2, 5};
        System.out.println("Ways to make 5: " + solver.combinationCount(coins, 5)); // Expected: 4
    }
}